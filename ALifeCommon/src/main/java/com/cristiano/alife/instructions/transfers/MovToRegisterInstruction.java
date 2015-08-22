package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class MovToRegisterInstruction extends BaseInstruction {
	public MovToRegisterInstruction(IWorld mundo) {
		super(mundo);
	}

	@Override
	public void executa(IOrganismo o) {
		int fromPos = o.getReg(getByteOrganismo(o, 1));
		int inst = o.getMemory(fromPos);
		int regVal = o.getReg(getByteOrganismo(o, 2));
		o.wroteFlag();
		inst = mundo.getMutation().mutateInstruction(inst,o);
		o.setMemory(regVal, inst);
	}

	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)),
				"(" + ALifeConsts.getLetter(o.getMemory(ip + 2)) + ") <- (" + ALifeConsts.getLetter(o.getMemory(ip + 1)) + ")");
	}

}
