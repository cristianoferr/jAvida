package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class MalAvidaInstruction extends MalInstruction  {
	public MalAvidaInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		super.executa(o);
		int sp=o.getReg(getByteOrganismo(o,2));
		o.setReg(getByteOrganismo(o,2), sp+AvidaConsts.WORD_SIZE);
	}
	
	

}
