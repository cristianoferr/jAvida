package com.cristiano.alife.instructions.logic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class IFZeroInstruction extends BaseInstruction  {
	public IFZeroInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int v=o.getReg(getByteOrganismo(o,1));
		if (v!=0){
			o.next(1);
		} 
	}
	
	@Override
	public int getStep() {
		return 2;
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),"if "+ALifeConsts.getLetter(o.getMemory(ip+1))+" == 0 then:");
	}

}
