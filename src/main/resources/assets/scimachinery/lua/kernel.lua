function sleep(time)
	local timer = os.startTimer(time)
	repeat
		local evt, param = os.pullEvent("timer")
	until param == timer
end

while true do
	term.writeLine("Things")
	sleep(1)
end

term.writeLine("Computer ID: " .. os.getComputerID() .. " shutting down!")
os.shutdown()