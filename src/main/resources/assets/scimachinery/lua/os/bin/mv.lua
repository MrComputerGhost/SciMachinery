local tArgs = { ... }
if #tArgs < 2 then
	term.writeLine("Usage: mv <src> <dest>)
	return
end

fs.move(tArgs[1], tArgs[2])