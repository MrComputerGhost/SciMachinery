loadfile = function(path)
	local file = fs.open(path, "r")
	if file then
		local func, err = loadstring(file.readAll(), fs.getName(path))
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

function os.loadAPI(path)
	local name = fs.getName(path)
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

--for shits and giggles:

local function write(x, y, string)
	for i = 1, #string do
		local c = string:sub(i, i)
		term.setCharacter(x + i, y, c)
	end
end

write(0, 0, "root@smc:~$")
local state = false
while true do
	os.sleep(0.5)
	state = not state
	if state then
		term.setCharacter(12, 0, '_')
	else
		term.setCharacter(12, 0, ' ')
	end
end