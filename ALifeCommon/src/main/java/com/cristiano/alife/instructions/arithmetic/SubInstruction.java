package com.cristiano.alife.instructions.arithmetic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class SubInstruction extends BaseInstruction  {
	public SubInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int v=o.getReg(getByteOrganismo(o,1))-o.getReg(getByteOrganismo(o,2));
		o.setReg(getByteOrganismo(o,3), v);
	}
	
	@Override
	public int getStep() {
		return 4;
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2))+","+ALifeConsts.getLetter(o.getMemory(ip+3)),ALifeConsts.getLetter(o.getMemory(ip+3))+" <- "+ALifeConsts.getLetter(o.getMemory(ip+1))+"-"+ALifeConsts.getLetter(o.getMemory(ip+2)));
	}

	

}
