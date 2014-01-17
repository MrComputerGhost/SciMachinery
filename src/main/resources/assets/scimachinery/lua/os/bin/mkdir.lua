local tArgs = { ... }
if #tArgs < 1 then
	term.print("Usage: mkdir <dir>)
	return
end

fs.mkdir(tArgs[1])