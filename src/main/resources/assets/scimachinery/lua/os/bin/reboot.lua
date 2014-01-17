local tArgs = { ... }
local sleepTime = 1

if type(tArgs[1]) == "number" then
	sleepTime = tonumber(tArgs[1])
end

os.sleep(sleepTime)
os.reboot()