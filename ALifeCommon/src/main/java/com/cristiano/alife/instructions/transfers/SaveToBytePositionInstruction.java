package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

/*
 * STA: (byte) <- from
 * */
public class SaveToBytePositionInstruction extends BaseInstruction  {
	public SaveToBytePositionInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int toPos=getByteOrganismo(o, 1);
		int inst=o.getReg(getByteOrganismo(o,2));
		inst=mundo.getMutation().mutateInstruction(inst,o);
		/*if (!mundo.isValidInstruction(inst)){
			o.fatalError();
			return;
		}*/
		
		o.setMemory(toPos, inst);
		o.wroteFlag();
	}
	
	@Override
	public int getStep() {
		return 3;
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+o.getMemory(ip+1)+","+ALifeConsts.getLetter(o.getMemory(ip+2)),"("+o.getMemory(ip+1)+") <- "+ALifeConsts.getLetter(o.getMemory(ip+2))+"");
	}

}
