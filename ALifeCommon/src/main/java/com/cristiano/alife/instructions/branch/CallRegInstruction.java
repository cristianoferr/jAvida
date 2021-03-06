package com.cristiano.alife.instructions.branch;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//push(ip) || ip <- reg  
public class CallRegInstruction extends BaseInstruction  {

	public CallRegInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public void executa(IOrganismo o) {
		int pos=o.getReg(getByteOrganismo(o,1));
		o.push(o.ip()+1);
		o.setIp(pos);
	}
	
	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),"push(ip); ip <- "+ALifeConsts.getLetter(o.getMemory(ip+1))+";");
	}
	
}
