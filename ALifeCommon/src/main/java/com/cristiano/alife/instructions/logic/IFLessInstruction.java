package com.cristiano.alife.instructions.logic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class IFLessInstruction extends BaseInstruction  {
	public IFLessInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int vf=o.getReg(getByteOrganismo(o,1));
		int vt=o.getReg(getByteOrganismo(o,2));
		if (vt<vf){
			o.next(1);
		}
	}
	
	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)),"if "+ALifeConsts.getLetter(o.getMemory(ip+1))+" < "+ALifeConsts.getLetter(o.getMemory(ip+2))+" then:");
	}
	
}
