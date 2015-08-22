package com.cristiano.alife.instructions;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.instructions.io.MalAvidaInstruction;
import com.cristiano.alife.instructions.io.MalInstruction;
import com.cristiano.alife.world.InstructionBuilderBase;
import com.cristiano.alife.world.IManageInstructions;
import com.cristiano.alife.world.IWorld;

public class InstructionBuilderAvida extends InstructionBuilderBase {

	public InstructionBuilderAvida(IWorld m) {
		super(m);
	}

	public void build(IWorld m) {
		super.build(m);
		addInstruction(INSTR_MAL, new MalAvidaInstruction(mundo));
	}
}
