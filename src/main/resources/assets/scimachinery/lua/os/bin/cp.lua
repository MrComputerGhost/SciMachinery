local tArgs = { ... }
if #tArgs < 2 then
	term.print("Usage: cp <src> <dest>)
	return
end

fs.copy(tArgs[1], tArgs[2])