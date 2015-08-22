package com.cristiano.alife.instructions.branch;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class RetInstruction extends BaseInstruction  {
	
	public RetInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public void executa(IOrganismo o) {
		int pos=o.pop();
		o.setIp(pos-1);
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name,"ip <- pop()");
	}


}
