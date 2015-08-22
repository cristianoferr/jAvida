package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//SCAN A,B:  A <- checkNeighbour(B): B= index de 0 a 7, A recebe 1 se tiver alguém, 0 se não
public class ScanInstruction extends BaseInstruction {

	public ScanInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public void executa(IOrganismo o) {
		int toReg=getByteOrganismo(o,1);
		int index = o.getReg(getByteOrganismo(o,2));
		index=ALifeConsts.calcIndex(index, ALifeConsts.MAX_NEIGHBOURS);
		int scanResult = (o.getNeighbourAt(index))==null?0:1;
		o.setReg(toReg, scanResult);
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)),
				ALifeConsts.getLetter(o.getMemory(ip+1)) + "<- checkNeighbour at Index " + ALifeConsts.getLetter(o.getMemory(ip+2))+": 1=found, 0=not found");
	}

}
