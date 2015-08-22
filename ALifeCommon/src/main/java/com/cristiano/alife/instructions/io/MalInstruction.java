package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class MalInstruction extends BaseInstruction {

	public MalInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public void executa(IOrganismo o) {
		int memSize = o.getReg(getByteOrganismo(o,1));
		if (o.child() != null) {
			mundo.dealloc(o.child());
			o.clearChild();
			o.fatalError();
			return;
		}
		memSize = ALifeConsts.validateMemorySize(o, memSize);
		if (memSize==0){
			o.fatalError();
			return; 
		}
		IOrganismo child = mundo.alloc(memSize, o);
		if (child.isAlive()) {
			o.addFitness();
			o.setChild(child);
			o.setReg(getByteOrganismo(o,2), child.sp());
		} else {
			o.error();
			o.setReg(getByteOrganismo(o,2), 0);
		}
	}

	

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)), "Allocate " + ALifeConsts.getLetter(o.getMemory(ip+1)) + " Bytes; store position in " + ALifeConsts.getLetter(o.getMemory(ip+2)));
	}

}
