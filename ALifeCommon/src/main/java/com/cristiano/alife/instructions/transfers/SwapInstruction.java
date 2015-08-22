package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class SwapInstruction extends BaseInstruction  {
	public SwapInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int a=o.getReg(getByteOrganismo(o,1));
		int b=o.getReg(getByteOrganismo(o,2));
		o.setReg(getByteOrganismo(o,2), a);
		o.setReg(getByteOrganismo(o,1), b);
	}
	
	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)),ALifeConsts.getLetter(o.getMemory(ip+1))+" <-> "+ALifeConsts.getLetter(o.getMemory(ip+2))+"");
	}
	
}
