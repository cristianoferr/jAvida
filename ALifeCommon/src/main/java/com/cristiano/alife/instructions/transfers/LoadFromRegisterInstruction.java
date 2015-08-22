package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

/*
 * Load a byte from the "from" register position to the "to" register: 
 * LOADI: to <- (from)
 * */
public class LoadFromRegisterInstruction extends BaseInstruction  {
	public LoadFromRegisterInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int fromPos = o.getReg(getByteOrganismo(o,2));
		int inst=o.getMemory(fromPos);
		inst=mundo.getMutation().mutateInstruction(inst,o);
		/*if (!mundo.isValidInstruction(inst)){
			o.fatalError();
			return;
		}*/
		o.setReg(getByteOrganismo(o,1),inst);
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2))
				,ALifeConsts.getLetter(o.getMemory(ip+1))+" <- ("+ALifeConsts.getLetter(o.getMemory(ip+2))+")");
	}
	
	@Override
	public int getStep() {
		return 3;
	}
	

}
