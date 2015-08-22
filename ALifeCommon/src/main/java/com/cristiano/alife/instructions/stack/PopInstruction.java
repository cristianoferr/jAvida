package com.cristiano.alife.instructions.stack;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class PopInstruction extends BaseInstruction  {
	public PopInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		o.setReg(getByteOrganismo(o,1),o.pop());
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),ALifeConsts.getLetter(o.getMemory(ip+1))+" <- pop()");
	}
	
	@Override
	public int getStep() {
		return 2;
	}
}
