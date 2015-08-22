package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class MovToBytePositionInstruction extends BaseInstruction  {
	public MovToBytePositionInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int inst=o.getMemory(o.ip()+1);
		inst=mundo.getMutation().mutateInstruction(inst,o);
		int regVal = o.getReg(getByteOrganismo(o,2));
		o.wroteFlag();
		o.setMemory(regVal, inst);
	}
	
	@Override
	public int getStep() {
		return 3;
	}

	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+o.getMemory(ip+1)+","+ALifeConsts.getLetter(o.getMemory(ip+2)),"("+ALifeConsts.getLetter(o.getMemory(ip+2))+")<- ("+o.getMemory(ip+1)+")");
	}

}
