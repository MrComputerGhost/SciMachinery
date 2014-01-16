local function write(x, y, string)
	for i=1, #string do
		local c = string:sub(i, i)
		term.setCharacter(x + i, y, c)
	end
end

write(0, 0, "Hello, Kernel!")