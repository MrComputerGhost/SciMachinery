function sleep(time)
	local timer = os.startTimer(time)
	repeat
		local evt, param = os.pullEvent("timer")
	until param == timer
end

local nativeShutdown = os.shutdown
function os.shutdown()
	nativeShutdown()
	while true do
		coroutine.yield()
	end
end

local nativeReboot = os.reboot
function os.reboot()
	nativeReboot()
	while true do
		coroutine.yield()
	end
end

---------------------------------------------------------------------------------------------------------

sleep(10)

os.shutdown()