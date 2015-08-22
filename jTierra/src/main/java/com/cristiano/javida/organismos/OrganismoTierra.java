package com.cristiano.javida.organismos;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.TierraConsts;
import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.world.OrganismoBase;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.javida.MundoTierra;
import com.cristiano.utils.Log;

public class OrganismoTierra extends OrganismoBase implements IOrganismo {
	private int regs[];
	private int memorySize;
	private int startPoint;// readonly

	
	public OrganismoTierra(MundoTierra mundo, int memSize, int sp) {
		super(mundo, memSize, sp);
		setIp(sp);
	}

	@Override
	public int getMemory(int i) {
		return mundo.cpu().getMemory(i);
	}

	@Override
	public boolean setMemory(int index, int v, boolean punish) {
		if (index >= sp() && index <= sp() + memorySize) {
			mundo.cpu().setMemory(index, v);
			return true;
		} else if (child != null) {
			if (child.setMemory(index, v, false)) {
				return true;
			}
		}
		if (punish) {
			fatalError();
		}
		return false;
	}
	
	public void setStartPoint(int i) {
		this.startPoint = i;
	}
	
	@Override
	public int sp() {
		return startPoint;
	}
	
	@Override
	protected void initRegs() {
		regs = new int[ALifeConsts.REGISTRADORES];
		for (int i = 0; i < ALifeConsts.REGISTRADORES; i++) {
			regs[i] = 0;
		}
	}
	
	@Override
	public void setReg(int i, int v) {
		regs[i % ALifeConsts.REGISTRADORES] = v;
	}


	@Override
	public int getReg(int i) {
		return regs[i % ALifeConsts.REGISTRADORES];
	}
	
	@Override
	public int getMemorySize() {
		return memorySize;
		
	}


	@Override
	public void run() {
		super.run();

		int ip = ip();
		int sp = sp();
		if (ip < sp || ip > sp + memorySize) {
			fatalError();
		}
	}
	
	protected void setMemorySize(int memSize) {
		this.memorySize=memSize;
	}

	@Override
	protected void punishSimilarity() {
		// Verifica similaridade com ancestral
		int seqIguais = 0;

		for (int i = 0; i < memorySize; i++) {
			Instruction inst = getInstructionAt(sp() + i);
			int instId = inst.getId();

			int index = i;
			while (i + index < mundo.getAncestor().getMemorySize() && i + index < memorySize
					&& getInstructionAt(sp() + i + index) == mundo.getAncestor().getInstructionAt(mundo.getAncestor().sp() + i + index)) {
				seqIguais++;
				index++;
			}
		}
		tickError = seqIguais * TierraConsts.NORMAL_ERROR * 0.4f;
	}

}
