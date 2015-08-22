package com.cristiano.jTierra.instructions;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.instructions.io.MalInstruction;
import com.cristiano.alife.world.InstructionBuilderBase;
import com.cristiano.alife.world.IWorld;

public class InstructionBuilderTierra extends InstructionBuilderBase {

	public InstructionBuilderTierra(IWorld m) {
		super(m);
	}

	public void build(IWorld m) {
		super.build(m);
		addInstruction(INSTR_MAL, new MalInstruction(mundo));
	}

}
