package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//MOV: to <- from
public class MovInstruction extends BaseInstruction  {
	public MovInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int v=o.getReg(getByteOrganismo(o,1));
		o.setReg(getByteOrganismo(o,2), v);
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)),ALifeConsts.getLetter(o.getMemory(ip+2))+" <- "+ALifeConsts.getLetter(o.getMemory(ip+1))+"");
	}

	@Override
	public int getStep() {
		return 3;
	}
}
