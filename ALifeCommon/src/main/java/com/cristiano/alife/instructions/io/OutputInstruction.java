package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;
import com.cristiano.utils.Log;

public class OutputInstruction extends BaseInstruction {

	public OutputInstruction(IWorld mundo) {
		super(mundo);
	}

	@Override
	public void executa(IOrganismo o) {

		int vlrGerado = o.getReg(getByteOrganismo(o,1));
		if (!o.hasCompletedTask()) {
			mundo.io().verifyTask(o, vlrGerado);
		} 
		o.incTask();
		o.addFitness();
	}

	@Override
	public int getStep() {
		return 2;
	}
	
	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)), ALifeConsts.getLetter(o.getMemory(ip+1)) + " <- taskValue");
	}

}
