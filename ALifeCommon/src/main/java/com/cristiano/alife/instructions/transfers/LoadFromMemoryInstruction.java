package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

/*
 * Load a byte from the "from" memory position to the "to" register: 
 * LOADM: to <- (word)
 * */
public class LoadFromMemoryInstruction extends BaseInstruction  {
	public LoadFromMemoryInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int inst = getByteOrganismo(o,2);
		inst=o.getMemory(inst);
		inst=mundo.getMutation().mutateInstruction(inst,o);
		/*if (!mundo.isValidInstruction(inst)){
			o.fatalError();
			return;
		}*/
		o.setReg(getByteOrganismo(o,1),inst);
	}
	
	
	@Override
	public int getStep() {
		return 3;
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+o.getMemory(ip+2),
				ALifeConsts.getLetter(o.getMemory(ip+1))+" <- ("+o.getMemory(ip+2)+")");
	}
	

}
