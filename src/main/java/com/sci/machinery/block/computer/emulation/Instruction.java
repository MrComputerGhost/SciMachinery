package com.sci.machinery.block.computer.emulation;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public enum Instruction
{
	LDA_I(1), LDA_M(2), LDB_I(3), LDB_M(4), LDC_I(5), LDC_M(6), LDD_I(7), LDD_M(8),

	STA_I(9), STA_M(10), STB_I(11), STB_M(12), STC_I(13), STC_M(14), STD_I(15), STD_M(16),

	MOV_I(17),

	TSD_IM(18), TDS_IM(19), PHA_IM(20), PLA_IM(21), PHP_IM(22), PLP_IM(23),

	AND_I(24), AND_M(25), XOR_I(26), XOR_M(27), ORA_I(28), ORA_M(29),

	ADD_I(30), ADD_M(31), SUB_I(32), SUB_M(33), CMA_I(34), CMA_M(35), CMB_I(36), CMB_M(37), CMC_I(38), CMC_M(39), CMD_I(40), CMD_M(41),

	INC_I(42), INC_M(43), INC_A(44), INA_IM(45), INB_IM(46), ICN_IM(47), IND_IM(48), DEC_I(49), DEC_M(50), DEC_A(51), DEA_IM(52), DEB_IM(53), DCE_IM(54), DED_IM(55),

	ASL_I(56), ASL_M(57), ASR_I(58), ASR_M(59), LSR_I(60), LSR_M(61),

	JMP_I(62), JMP_M(63), JMP_A(64), JSR_I(65), JSR_M(66), JSR_A(67), RTS_IM(68),

	BCC_I(69), BCC_M(70), BCC_A(71), BNC_I(72), BNC_M(73), BNC_A(74), BZC_I(75), BZC_M(76), BZC_A(77), BCS_I(78), BCS_M(79), BCS_A(80), BNS_I(81), BNS_M(82), BNS_A(83), BZS_I(84), BZS_M(85), BZS_A(86),

	CLC_IM(87), CLN_IM(88), CLZ_IM(89), CLI_IM(90), SEC_IM(91), SEN_IM(92), SEZ_IM(93), SEI_IM(94),

	BRK_IM(95), RTI_IM(96), NOP_IM(0);

	public final int opcode;

	Instruction(int opcode)
	{
		this.opcode = opcode;
	}

	public static Instruction findByOpcode(int opcode)
	{
		for(Instruction i : values())
		{
			if(i.opcode == opcode)
				return i;
		}
		return NOP_IM;
	}
}