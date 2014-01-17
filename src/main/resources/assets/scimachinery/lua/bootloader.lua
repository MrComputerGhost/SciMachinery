--bootloader essential functions

local function write(x, y, string)
	for i = 1, #string do
		local c = string:sub(i, i)
		gpu.setCharacter(x + i, y, c)
	end
end

local function clear()
	for i=0, 39 do
		for j=0, 28 do
			gpu.setCharacter(i, j, ' ')
		end
	end	
end

---------------------------------------------------------------------------------------------------------

local loadKernel = function()
	kernelFile = fs.open("kernel.lua", "r")
	if kernelFile then
		kernel = loadstring(kernelFile.readAll(), "kernel")
		kernelFile.close()
		kernel()
	else
		error("kernel not found")
	end
end

local success, err = pcall(loadKernel)
if not success then	
	gpu.debug(err)
	write(0, 0, "Bootloader Error!")
	write(0, 1, "An error occured while loading kernel")
	write(0, 2, "Press any key to continue...")
end

while true do
	local evt = os.pullEvent("key")
	if evt == "key" then
		break
	end
end

clear()

os.shutdown()	
while true do
	coroutine.yield()
end