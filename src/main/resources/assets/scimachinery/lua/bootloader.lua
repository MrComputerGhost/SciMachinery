--bootloader essential functions

local function write(x, y, string)
	for i = 1, #string do
		local c = string:sub(i, i)
		gpu.setCharacter(x + i - 1, y, c)
	end
end

local function clear()
	for i=1, 61 do
		for j=1, 29 do
			gpu.setCharacter(i - 1, j - 1, ' ')
		end
	end	
end

---------------------------------------------------------------------------------------------------------

local timer = os.startTimer(1)
repeat 
	local evt, token = os.pullEvent("timer")
until token == timer

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
	local evt, c = os.pullEvent("char")
	if evt == "char" then
		break
	end
end

clear()

os.shutdown()	
while true do
	coroutine.yield()
end