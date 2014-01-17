local tArgs = { ... }
if #tArgs < 2 then
	term.print("Usage: mv <src> <dest>)
	return
end

fs.move(tArgs[1], tArgs[2])