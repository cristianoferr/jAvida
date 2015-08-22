package com.cristiano.alife.instructions.logic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class OrInstruction extends BaseInstruction  {
	public OrInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int v=o.getReg(getByteOrganismo(o,1));
		v^=1;
		o.setReg(getByteOrganismo(o,1), v);
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),ALifeConsts.getLetter(o.getMemory(ip+1))+" <- or("+ALifeConsts.getLetter(o.getMemory(ip+1))+") ");
	}
	
	@Override
	public int getStep() {
		return 2;
	}

}
