while true do
	term.writeLine("stuffs are heppens")
end

term.writeLine("Computer ID: " .. os.getComputerID() .. " shutting down!")
os.shutdown()