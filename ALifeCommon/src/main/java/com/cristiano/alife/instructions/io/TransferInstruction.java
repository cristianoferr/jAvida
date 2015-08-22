package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//TRANSFER regIndex: transfer the program to a empty position
public class TransferInstruction extends BaseInstruction {

	public TransferInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public void executa(IOrganismo o) {
		int index = o.getReg(getByteOrganismo(o,1));
		IOrganismo orgAtIndex=o.getNeighbourAt(index);
		if (orgAtIndex!=null){
			o.criticalError();
			return;
		}
		
		o.trasnferToIndex(index);
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1)),
				"Move the program to the index at "+ALifeConsts.getLetter(o.getMemory(ip+1))) ;
	}

}
