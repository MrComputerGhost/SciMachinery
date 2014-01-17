local tArgs = { ... }
if #tArgs < 1 then
	term.writeLine("Usage: rm <file>)
	return
end

fs.delete(tArgs[1])