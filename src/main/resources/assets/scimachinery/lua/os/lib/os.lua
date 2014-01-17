local nativeShutdown = os.shutdown
function os.shutdown()
	nativeShutdown()
	while true do
		coroutine.yield()
	end	
end

local nativeReboot os.reboot
function os.reboot()
	nativeReboot()
	while true do
		coroutine.yield()
	end
end