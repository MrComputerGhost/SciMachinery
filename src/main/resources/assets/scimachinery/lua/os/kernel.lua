loadfile = function(path)
	local file = fs.open(path, "r")
	if file then
		local func, err = load(file.readAll(), fs.getName(path))
		file.close()
		return func, err
	end
	return nil, "File not found"
end

dofile = function(path)
	local fnFile, err = loadfile(path)
	if fnFile then
		setfenv(fnFile, getfenv(2))
		return fnFile()
	else
		error(err, 2)
	end
end

local apisLoading = {}
function os.loadAPI(path)
	local name = fs.getName(path)
	if apisLoading[name] == true then
		return false
	end
	apisLoading[name] = true
	
	local tEnv = {}
	setmetatable(tEnv, { __index = _G })
	local fnAPI, err = loadfile(path)
	if fnAPI then
		setfenv(fnAPI, tEnv)
		fnAPI()
	end
	
	local tAPI = {}
	for k, v in pairs(tEnv) do
		tAPI[k] = v
	end
	
	_G[name] = tAPI
	apisLoading[name] = nil
	return true
end

function os.unloadAPI(name)
	if name ~= "_G" and type(_G[name]) == "table" then
		_G[name] = nil
	end
end	

local apis = fs.list("lib")
for n, file in ipairs(apis) do
	if string.sub(file, 1, 1) ~= "." then
		local path = "lib/" .. file
		if not fs.isDirectory(path) then
			os.loadAPI(path)
		end
	end
end

error