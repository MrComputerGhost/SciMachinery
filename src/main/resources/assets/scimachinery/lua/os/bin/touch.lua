local tArgs = { ... }
if #tArgs < 1 then
	term.writeLine("Usage: touch <file>)
	return
end

fs.touch(tArgs[1])