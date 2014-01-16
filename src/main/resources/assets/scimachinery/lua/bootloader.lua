--bootloader essential functions

local function sleep(time)
        local timer = os.startTimer(time)
        repeat
                local evt, param = os.pullEvent("timer")
        until param == timer
end

local function write(x, y, string)
	for i = 1, #string do
		local c = string:sub(i, i)
		term.setCharacter(x + i, y, c)
	end
end

local function clear()
	for i=0, 60 do
		for j = 0, 29 do
			term.setCharacter(i-1, j-1, ' ')
		end
	end
end	

---------------------------------------------------------------------------------------------------------

write(0, 0, "Could not locate OS!")
write(0, 1, "Shutting down in 10 seconds!")

sleep(10)

clear()

os.shutdown()
while true do
	coroutine.yield()
end