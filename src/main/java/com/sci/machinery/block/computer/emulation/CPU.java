package com.sci.machinery.block.computer.emulation;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class CPU
{
	public static final int FLAG_C = 0;
	public static final int FLAG_N = 1;
	public static final int FLAG_Z = 2;
	public static final int FLAG_I = 3;

	public static final int REG_A = 0;
	public static final int REG_B = 1;
	public static final int REG_C = 2;
	public static final int REG_D = 3;
	
	public static final int INTERRUPT_VECTOR = 0xFFFF;

	private Bus bus; // the bus

	private int flags; // cpu flags
	private int regPC; // program counter
	private int regSP; // stack pointer

	private int[] registers; // registers

	public CPU(Bus bus)
	{
		this.bus = bus;

		this.flags = 0;
		this.flags = BitUtils.set(this.flags, FLAG_C, false);
		this.flags = BitUtils.set(this.flags, FLAG_N, false);
		this.flags = BitUtils.set(this.flags, FLAG_Z, false);
		this.flags = BitUtils.set(this.flags, FLAG_I, true);

		this.regPC = 0;
		this.regSP = 0;

		this.registers = new int[4];
		this.registers[REG_A] = 0;
		this.registers[REG_B] = 0;
		this.registers[REG_C] = 0;
		this.registers[REG_D] = 0;
	}

	private int readB() throws MemoryException
	{
		int i = this.bus.read(this.regPC);
		this.regPC++;
		return i;
	}

	private int readBB() throws MemoryException
	{
		return this.bus.read(readB());
	}

	private void updateFlags(int old, int new_)
	{
		this.flags = BitUtils.set(this.flags, FLAG_C, old < new_);
		this.flags = BitUtils.set(this.flags, FLAG_N, old == new_);
		this.flags = BitUtils.set(this.flags, FLAG_Z, old > new_);
	}

	public void cycle() throws MemoryException
	{
		Instruction i = Instruction.findByOpcode(readB());
		
		int tmp;
		switch (i)
		{
		case ADD_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] += readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case ADD_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] += readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case AND_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] &= readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case AND_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] &= readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case ASL_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] <<= readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case ASL_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] <<= readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case ASR_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] >>= readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case ASR_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] >>= readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case BCC_A:
			if(!BitUtils.get(this.flags, FLAG_C))
				this.regPC = this.registers[REG_A];
			break;
		case BCC_I:
			if(!BitUtils.get(this.flags, FLAG_C))
				this.regPC = readB();
			break;
		case BCC_M:
			if(!BitUtils.get(this.flags, FLAG_C))
				this.regPC = readBB();
			break;
		case BCS_A:
			if(BitUtils.get(this.flags, FLAG_C))
				this.regPC = this.registers[REG_A];
			break;
		case BCS_I:
			if(BitUtils.get(this.flags, FLAG_C))
				this.regPC = readB();
			break;
		case BCS_M:
			if(BitUtils.get(this.flags, FLAG_C))
				this.regPC = readBB();
			break;
		case BNC_A:
			if(!BitUtils.get(this.flags, FLAG_N))
				this.regPC = this.registers[REG_A];
			break;
		case BNC_I:
			if(!BitUtils.get(this.flags, FLAG_N))
				this.regPC = readB();
			break;
		case BNC_M:
			if(!BitUtils.get(this.flags, FLAG_N))
				this.regPC = readBB();
			break;
		case BNS_A:
			if(BitUtils.get(this.flags, FLAG_N))
				this.regPC = this.registers[REG_A];
			break;
		case BNS_I:
			if(BitUtils.get(this.flags, FLAG_N))
				this.regPC = readB();
			break;
		case BNS_M:
			if(BitUtils.get(this.flags, FLAG_N))
				this.regPC = readBB();
			break;
		case BRK_IM: 
			interrupt();
			break;
		case BZC_A:
			if(!BitUtils.get(this.flags, FLAG_Z))
				this.regPC = this.registers[REG_A];
			break;
		case BZC_I:
			if(!BitUtils.get(this.flags, FLAG_Z))
				this.regPC = readB();
			break;
		case BZC_M:
			if(!BitUtils.get(this.flags, FLAG_Z))
				this.regPC = readBB();
			break;
		case BZS_A:
			if(BitUtils.get(this.flags, FLAG_Z))
				this.regPC = this.registers[REG_A];
			break;
		case BZS_I:
			if(BitUtils.get(this.flags, FLAG_Z))
				this.regPC = readB();
			break;
		case BZS_M:
			if(BitUtils.get(this.flags, FLAG_Z))
				this.regPC = readBB();
			break;
		case CLC_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_C);
			break;
		case CLI_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_I);
			break;
		case CLN_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_N);
			break;
		case CLZ_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_Z);
			break;
		case CMA_I:
			updateFlags(this.registers[REG_A], readB());
			break;
		case CMA_M:
			updateFlags(this.registers[REG_A], readBB());
			break;
		case CMB_I:
			updateFlags(this.registers[REG_B], readB());
			break;
		case CMB_M:
			updateFlags(this.registers[REG_B], readBB());
			break;
		case CMC_I:
			updateFlags(this.registers[REG_C], readB());
			break;
		case CMC_M:
			updateFlags(this.registers[REG_C], readBB());
			break;
		case CMD_I:
			updateFlags(this.registers[REG_D], readB());
			break;
		case CMD_M:
			updateFlags(this.registers[REG_D], readBB());
			break;
		case DEA_IM:
			tmp = this.registers[REG_A];
			this.registers[REG_A]--;
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case DEB_IM:
			tmp = this.registers[REG_B];
			this.registers[REG_B]--;
			updateFlags(tmp, this.registers[REG_B]);
			break;
		case DEC_A:
			this.bus.write(this.registers[REG_A], this.bus.read(this.registers[REG_A]) - 1);
			break;
		case DEC_I:
			int address = readB();
			this.bus.write(address, this.bus.read(address) + 1);
			break;
		case DCE_IM:
			tmp = this.registers[REG_C];
			this.registers[REG_C]--;
			updateFlags(tmp, this.registers[REG_C]);
			break;
		case DEC_M:
			int a = readBB();
			this.bus.write(a, this.bus.read(a) + 1);
			break;
		case DED_IM:
			tmp = this.registers[REG_D];
			this.registers[REG_D]--;
			updateFlags(tmp, this.registers[REG_D]);
			break;
		case INA_IM:
			tmp = this.registers[REG_A];
			this.registers[REG_A]++;
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case INB_IM:
			tmp = this.registers[REG_B];
			this.registers[REG_B]++;
			updateFlags(tmp, this.registers[REG_B]);
			break;
		case INC_A:
			this.bus.write(this.registers[REG_A], this.bus.read(this.registers[REG_A]) + 1);
			break;
		case INC_I:
			int b = readB();
			this.bus.write(b, this.bus.read(b) + 1);
			break;
		case ICN_IM:
			tmp = this.registers[REG_C];
			this.registers[REG_C]++;
			updateFlags(tmp, this.registers[REG_C]);
			break;
		case INC_M:
			int c = readBB();
			this.bus.write(c, this.bus.read(c) + 1);
			break;
		case IND_IM:
			tmp = this.registers[REG_D];
			this.registers[REG_D]++;
			updateFlags(tmp, this.registers[REG_D]);
			break;
		case JMP_A:
			this.regPC = this.registers[REG_A];
			break;
		case JMP_I:
			this.regPC = readB();
			break;
		case JMP_M:
			this.regPC = readBB();
			break;
		case JSR_A:
			push(this.regPC);
			this.regPC = this.registers[REG_A];
			break;
		case JSR_I:
			push(this.regPC);
			this.regPC = readB();
			break;
		case JSR_M:
			push(this.regPC);
			this.regPC = readBB();
			break;
		case LDA_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] = readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case LDA_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] = readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case LDB_I:
			tmp = this.registers[REG_B];
			this.registers[REG_B] = readB();
			updateFlags(tmp, this.registers[REG_B]);
			break;
		case LDB_M:
			tmp = this.registers[REG_B];
			this.registers[REG_B] = readBB();
			updateFlags(tmp, this.registers[REG_B]);
			break;
		case LDC_I:
			tmp = this.registers[REG_C];
			this.registers[REG_C] = readB();
			updateFlags(tmp, this.registers[REG_C]);
			break;
		case LDC_M:
			tmp = this.registers[REG_C];
			this.registers[REG_C] = readBB();
			updateFlags(tmp, this.registers[REG_C]);
			break;
		case LDD_I:
			tmp = this.registers[REG_D];
			this.registers[REG_D] = readB();
			updateFlags(tmp, this.registers[REG_D]);
			break;
		case LDD_M:
			tmp = this.registers[REG_D];
			this.registers[REG_D] = readBB();
			updateFlags(tmp, this.registers[REG_D]);
			break;
		case LSR_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] >>>= readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case LSR_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] >>>= readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case MOV_I:
			int d = readB();
			tmp = this.registers[d];
			this.registers[d] = this.registers[readB()];
			updateFlags(tmp, this.registers[d]);
			break;
		case ORA_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] = this.registers[REG_A] | readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case ORA_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] = this.registers[REG_A] | readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case PHA_IM:
			push(this.registers[REG_A]);
			break;
		case PHP_IM:
			push(this.flags);
			break;
		case PLA_IM:
			this.registers[REG_A] = pop();
			break;
		case PLP_IM:
			this.flags = pop();
			break;
		case RTI_IM:
			this.flags = pop();
			this.regPC = pop();
			break;
		case RTS_IM:
			this.regPC = pop();
			break;
		case SEC_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_C);
			break;
		case SEI_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_I);
			break;
		case SEN_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_N);
			break;
		case SEZ_IM:
			this.flags = BitUtils.clear(this.flags, FLAG_Z);
			break;
		case STA_I:
			this.bus.write(readB(), this.registers[REG_A]);
			break;
		case STA_M:
			this.bus.write(readBB(), this.registers[REG_A]);
			break;
		case STB_I:
			this.bus.write(readB(), this.registers[REG_B]);
			break;
		case STB_M:
			this.bus.write(readBB(), this.registers[REG_B]);
			break;
		case STC_I:
			this.bus.write(readB(), this.registers[REG_C]);
			break;
		case STC_M:
			this.bus.write(readBB(), this.registers[REG_C]);
			break;
		case STD_I:
			this.bus.write(readB(), this.registers[REG_D]);
			break;
		case STD_M:
			this.bus.write(readBB(), this.registers[REG_D]);
			break;
		case SUB_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] += readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case SUB_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] += readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case TDS_IM:
			this.regSP = this.registers[REG_D];
			break;
		case TSD_IM:
			tmp = this.registers[REG_D];
			this.registers[REG_D] = this.regSP;
			updateFlags(tmp, this.registers[REG_D]);
			break;
		case XOR_I:
			tmp = this.registers[REG_A];
			this.registers[REG_A] = this.registers[REG_A] ^ readB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case XOR_M:
			tmp = this.registers[REG_A];
			this.registers[REG_A] = this.registers[REG_A] ^ readBB();
			updateFlags(tmp, this.registers[REG_A]);
			break;
		case NOP_IM:
			// nop
			break;
		default:
			break;
		}
	}

	public void interrupt() throws MemoryException
	{
		push(this.flags);
		push(this.regPC);
		this.regPC = INTERRUPT_VECTOR;
	}
	
	public void push(int value) throws MemoryException
	{
		this.bus.write(this.regSP, value);
		this.regSP--;
	}

	public int pop() throws MemoryException
	{
		this.regSP++;
		return this.bus.read(this.regSP);
	}

	public Bus getBus()
	{
		return bus;
	}

	public void setBus(Bus bus)
	{
		this.bus = bus;
	}

	public boolean getFlag(int flag)
	{
		return BitUtils.get(this.flags, flag);
	}

	public void setFlag(int flag, boolean value)
	{
		this.flags = BitUtils.set(this.flags, flag, value);
	}

	public int getPC()
	{
		return regPC;
	}

	public void setPC(int regPC)
	{
		this.regPC = regPC;
	}

	public int getSP()
	{
		return regSP;
	}

	public void setSP(int regSP)
	{
		this.regSP = regSP;
	}

	public int getRegister(int reg)
	{
		return this.registers[reg];
	}

	public void setRegister(int reg, int value)
	{
		this.registers[reg] = value;
	}

	public int getFlags()
	{
		return flags;
	}

	public void setFlags(int flags)
	{
		this.flags = flags;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("CPU\n");
		sb.append("{\n");
		sb.append("    PC: " + this.regPC + "\n");
		sb.append("    SP: " + this.regSP + "\n\n");
		sb.append("    A: " + this.registers[REG_A] + "\n");
		sb.append("    B: " + this.registers[REG_B] + "\n");
		sb.append("    C: " + this.registers[REG_C] + "\n");
		sb.append("    D: " + this.registers[REG_D] + "\n\n");
		sb.append("    FLAGS\n");
		sb.append("    {\n");
		sb.append("        C: " + BitUtils.get(this.flags, FLAG_C) + "\n");
		sb.append("        N: " + BitUtils.get(this.flags, FLAG_N) + "\n");
		sb.append("        Z: " + BitUtils.get(this.flags, FLAG_Z) + "\n");
		sb.append("        I: " + BitUtils.get(this.flags, FLAG_I) + "\n");
		sb.append("    }\n");
		sb.append("}");

		return sb.toString();
	}
}