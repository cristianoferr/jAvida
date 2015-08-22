package com.cristiano.alife.instructions.arithmetic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class ShiftRightInstruction extends BaseInstruction  {
	public ShiftRightInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int reg = o.getReg(getByteOrganismo(o,1));
		o.setReg(getByteOrganismo(o,1),reg>>1);
	}
	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),ALifeConsts.getLetter(o.getMemory(ip+1))+" <- shiftRight("+ALifeConsts.getLetter(o.getMemory(ip+1))+")  ");
	}
}
