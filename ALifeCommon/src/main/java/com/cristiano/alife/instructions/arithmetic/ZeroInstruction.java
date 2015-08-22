package com.cristiano.alife.instructions.arithmetic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class ZeroInstruction extends BaseInstruction {
	public ZeroInstruction(IWorld mundo) {
		super(mundo);
	}

	@Override
	public void executa(IOrganismo o) {
		o.setReg(getByteOrganismo(o, 1), 0);
	}

	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)), ALifeConsts.getLetter(o.getMemory(ip + 1)) + " <- 0");
	}

}
