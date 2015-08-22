package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

/*
 * a posição da memória de toReg recebe os inputs
 * */
public class InputInstruction extends BaseInstruction {

	public InputInstruction(IWorld mundo) {
		super(mundo);
	}

	@Override
	public void executa(IOrganismo o) {
		mundo.io().writeInput(o,o.getReg(getByteOrganismo(o,1)));
	}
	
	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),  "("+ALifeConsts.getLetter(o.getMemory(ip+1))+") <- taskValue(s), um byte para cada input mais operando");
	}

}
