local tArgs = { ... }
if #tArgs < 1 then
	term.print("Usage: rm <file>)
	return
end

fs.delete(tArgs[1])