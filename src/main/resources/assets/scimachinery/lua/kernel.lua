os.queueEvent("test", "test parameter")
event, param1 = os.pullEvent("test")

term.writeLine(event .. " " .. param1)

term.writeLine("Computer ID: " .. os.getComputerID() .. " shutting down!")
os.shutdown()