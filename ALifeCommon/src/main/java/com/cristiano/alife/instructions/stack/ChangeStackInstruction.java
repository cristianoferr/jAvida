package com.cristiano.alife.instructions.stack;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class ChangeStackInstruction extends BaseInstruction  {
	public ChangeStackInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		o.setCurrStack(getByteOrganismo(o,1));
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+o.getMemory(ip+1),"changeStack "+o.getMemory(ip+1)+"");
	}

	
	@Override
	public int getStep() {
		return 2;
	}
}
