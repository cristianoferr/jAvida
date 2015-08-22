package com.cristiano.alife.instructions.arithmetic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class DecInstruction extends BaseInstruction  {
	public DecInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		o.decReg(getByteOrganismo(o,1));
	}
	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),ALifeConsts.getLetter(o.getMemory(ip+1))+" <- "+ALifeConsts.getLetter(o.getMemory(ip+1))+" - 1");
	}
}
