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

function os.sleep(time)
	local timer = os.startTimer(time)
	repeat
		local evt, b = os.pullEvent("timer")
	until b == timer
end