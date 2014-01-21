package com.sci.machinery.block.computer.emulation;

public class Test
{
	public static void main(String[] args) throws MemoryException
	{
		Bus bus = new Bus();
		CPU cpu = new CPU(bus);
		Memory ram = new Memory(0x0000, 0x8000);
		bus.addDevice(ram);

		cpu.setPC(1024);
		cpu.setSP(512);

		int[] code = new int[] { Instruction.JMP_I.opcode, 1024 };
		for(int i = 0; i < code.length; i++)
		{
			ram.write(i + 1024, code[i]);
		}
		
		while(true)
		{
			cpu.cycle();
		}
	}
}