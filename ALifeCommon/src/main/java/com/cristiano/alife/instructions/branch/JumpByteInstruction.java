package com.cristiano.alife.instructions.branch;

import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//ip<-jmp(byte)
public class JumpByteInstruction extends BaseInstruction  {
	
	public JumpByteInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public void executa(IOrganismo o) {
		int pos=getByteOrganismo(o, 1);
		o.setIp(pos);
	}
	
	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+o.getMemory(ip+1),"ip <- "+o.getMemory(ip+1));
	}

}
