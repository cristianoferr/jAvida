package com.cristiano.alife.instructions.arithmetic;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//BIT: reg1[regPos] <- reg2[regPos] (3 regs)
public class BitInstruction extends BaseInstruction  {
	public BitInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		int r1 = getByteOrganismo(o,1);
		int v1=o.getReg(r1);
		int r2 = getByteOrganismo(o,2);
		int v2=o.getReg(r2);
		int rPos = getByteOrganismo(o,3);
		int vPos=o.getReg(rPos);
		
		int bit=ALifeConsts.getBit(v2, vPos);
		
		v1=ALifeConsts.setBit(v1,vPos,bit);
		o.setReg(r1, v1);
		
	}

	
	@Override
	public int getStep() {
		return 4;
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name+" "+ALifeConsts.getLetter(o.getMemory(ip+1))+","+ALifeConsts.getLetter(o.getMemory(ip+2))+","+ALifeConsts.getLetter(o.getMemory(ip+3)),
				ALifeConsts.getLetter(o.getMemory(ip+1))+"["+ALifeConsts.getLetter(o.getMemory(ip+3))+"] <- "+ALifeConsts.getLetter(o.getMemory(ip+2))+"["+ALifeConsts.getLetter(o.getMemory(ip+3))+"]");
	}
	
	
	
	
}
