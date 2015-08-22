package com.cristiano.alife.instructions.io;

import java.awt.Color;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//CONNECT A,B:  A <- sp do vizinho na posição B: B= index de 0 a 7, A recebe SP, child vira o vizinho, erroCritico se falhar
public class ConnectInstruction extends BaseInstruction {

	public ConnectInstruction(IWorld mundo) {
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
		
		IOrganismo child=o.getNeighbourAt(index);
		if (child==null){
			o.criticalError();
			return;
		}
		
		/*
		if (o.child() != null) {
			o.criticalError();
			return;
		}*/
		//o.setChild(child);
		//child.setParent(o);
		int ip = child.sp()+AvidaConsts.WORD_SIZE*(index+2);
		o.setReg(toReg, ip);
		o.addFitness();
		mundo.markProgram(child, Color.BLUE);
		mundo.markProgram(o, Color.yellow);
		mundo.drawLink(o,child);
		
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2)),
				ALifeConsts.getLetter(o.getMemory(ip+1)) + "<- neighBour sp at index " + ALifeConsts.getLetter(o.getMemory(ip+2))+"");
	}

}
