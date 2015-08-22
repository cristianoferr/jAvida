package com.cristiano.alife.instructions.logic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class NorInstruction extends BaseInstruction  {
	public NorInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int a=o.getReg(getByteOrganismo(o,2));
		int b=o.getReg(getByteOrganismo(o,1));
		o.setReg(getByteOrganismo(o,1), ~(a|b));
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)),ALifeConsts.getLetter(o.getMemory(ip+1))+" <- !("+ALifeConsts.getLetter(o.getMemory(ip+1))+" || "+ALifeConsts.getLetter(o.getMemory(ip+2))+") ");
	}
	
	@Override
	public int getStep() {
		return 3;
	}
	
}
