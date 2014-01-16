--bootloader essential functions

local function write(x, y, string)
	for i = 1, #string do
		local c = string:sub(i, i)
		term.setCharacter(x + i, y, c)
	end
end

---------------------------------------------------------------------------------------------------------

local loadKernel = function()
	kernelFile = fs.open("kernel.lua", "r")
	if kernelFile then
		kernel = load(kernelFile.readAll(), "kernel")
		kernelFile.close()
		kernel()
	else
		error("kernel not found")
	end
end

local success, err = pcall(loadKernel)
if not success then	
	write(0, 0, "An error occured while loading kernel")
end

os.shutdown()
while true do
	coroutine.yield()
end