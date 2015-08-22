package com.cristiano.javida.organismos;

import java.awt.Point;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.consts.TierraConsts;
import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;
import com.cristiano.alife.world.OrganismoBase;
import com.cristiano.utils.Log;

public class OrganismoAvida extends OrganismoBase implements IOrganismo {
	static Point pt = new Point();
	private int[] memory;
	int x, y;

	public OrganismoAvida(IWorld mundo, int memSize, int sp) {
		super(mundo, memSize, sp);
	}

	@Override
	protected void initMemory() {
		memory = new int[AvidaConsts.WORD_SIZE];
		for (int i = 0; i < AvidaConsts.WORD_SIZE; i++) {
			memory[i] = 0;
		}
	}

	@Override
	public int getMemory(int index) {
		child();
		index = ALifeConsts.calcIndex(index, AvidaConsts.WORD_SIZE
				* (memoryPoolCount + AvidaConsts.MEMORY_POOL_SIZE));
		if (index < AvidaConsts.WORD_SIZE) {
			return memory[index];
		} else if (index < AvidaConsts.WORD_SIZE * 2) {
			if (child != null) {
				return child.getMemory(index - AvidaConsts.WORD_SIZE);
			}
		} else {
			int nIndex = index / AvidaConsts.WORD_SIZE - 2;
			index = ALifeConsts.calcIndex(index, AvidaConsts.WORD_SIZE);
			IOrganismo neighbourAt = getNeighbourAt(nIndex);
			if (neighbourAt==null){
				criticalError();
				return 0;
			}
			return neighbourAt.getMemory(index);
		}
		return 0;
	}

	@Override
	public boolean setMemory(int index, int v, boolean punish) {
		child();

		index = ALifeConsts.calcIndex(index, AvidaConsts.WORD_SIZE
				* (memoryPoolCount + AvidaConsts.MEMORY_POOL_SIZE));
		if (index < AvidaConsts.WORD_SIZE) {
			memory[index] = v;
		} else if (index < AvidaConsts.WORD_SIZE * 2) {
			if (child != null) {
				child.setMemory(index - AvidaConsts.WORD_SIZE, v);
				return true;
			}
			return false;
		} else {
			int nIndex = index / AvidaConsts.WORD_SIZE - 2;
			index = ALifeConsts.calcIndex(index, AvidaConsts.WORD_SIZE);
			IOrganismo neighbourAt = getNeighbourAt(nIndex);
			if (neighbourAt==null){
				criticalError();
				return false;
			}
			neighbourAt.setMemory(index, v);
		}
		return true;
	}

	@Override
	public void reset(int memSize, int sp) {
		/*
		 * int memorySize = this.getMemorySize(); for (int i = 0; i <
		 * memorySize+AvidaConsts.STARTING_POINT; i++) { setMemory(i, 0); }
		 */
		super.reset(memSize, sp);
		x = -1;
		y = -1;

		for (int i = sp; i < AvidaConsts.WORD_SIZE; i++) {
			setMemory(i, 0);
		}
	}

	@Override
	public boolean validate() {
		return validate(this);
	}

	@Override
	protected void initRegs() {
		for (int i = 0; i < ALifeConsts.REGISTRADORES; i++) {
			setReg(i, 0);
		}
	}

	protected String listInstruction(int i, Instruction inst, int currentMemory) {
		return super.listInstruction(i, (i < AvidaConsts.STARTING_POINT) ? null
				: inst, currentMemory);
	}

	@Override
	public void setReg(int i, int v) {
		i = i % ALifeConsts.REGISTRADORES;
		setMemory(AvidaConsts.POSITION_REGS + i, v);
	}

	protected void setMemorySize(int memSize) {
		setMemory(AvidaConsts.POSITION_MEMSIZE, memSize);
	}

	@Override
	public int ip() {
		return getMemory(AvidaConsts.POSITION_IP);
	}

	public void setIp(int i) {
		if (i < 0 || i > AvidaConsts.WORD_SIZE * memoryPoolCount) {
			fatalError();
		}

		setMemory(AvidaConsts.POSITION_IP, i);

	}

	public void setStartPoint(int i) {
		setMemory(AvidaConsts.POSITION_SP, i);
	}

	@Override
	public int sp() {
		int memory = getMemory(AvidaConsts.POSITION_SP);
		return memory;
	}

	@Override
	public int getReg(int i) {
		i = i % ALifeConsts.REGISTRADORES;
		return getMemory(AvidaConsts.POSITION_REGS + i);
	}

	@Override
	public int getMemorySize() {
		int memory = getMemory(AvidaConsts.POSITION_MEMSIZE);
		if (memory < 0 || memory > AvidaConsts.WORD_SIZE) {
			fatalError();
		}
		return memory;
	}

	@Override
	protected void punishSimilarity() {
		// Verifica similaridade com ancestral
		int seqIguais = 0;
		IOrganismo parent = parent();
		if (parent == null) {
			return;
		}

		int memorySize = getMemorySize();
		for (int i = 0; i < memorySize; i++) {
			Instruction inst = getInstructionAt(sp() + i);
			// int instId = inst.getId();

			int index = i;
			while (i + index < parent.getMemorySize()
					&& i + index < memorySize
					&& getInstructionAt(sp() + i + index) == parent
							.getInstructionAt(parent.sp() + i + index)) {
				seqIguais++;
				index++;
			}
		}
		tickError = seqIguais * TierraConsts.NORMAL_ERROR * 0.4f;
	}

	public String dump() {
		String ret = listInstructions(AvidaConsts.WORD_SIZE, false);
		return ret;
	}

	@Override
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public IOrganismo getNeighbourAt(int index) {
		pt = calcIndexPosition(index);

		return mundo.getOrganismoAt(pt.x, pt.y);
	}

	private Point calcIndexPosition(int index) {
		pt.x = getX();
		pt.y = getY();
		index = ALifeConsts.calcIndex(index, AvidaConsts.MAX_NEIGHBOURS);
		if (index == 0) {
			pt.x--;
			pt.y--;
		} else if (index == 1) {
			pt.y--;
		} else if (index == 2) {
			pt.x++;
			pt.y--;
		} else if (index == 3) {
			pt.x--;
		} else if (index == 4) {
			pt.x++;
		} else if (index == 5) {
			pt.x--;
			pt.y++;
		} else if (index == 6) {
			pt.y++;
		} else if (index == 7) {
			pt.x++;
			pt.y++;
		} else {
			Log.fatal("Index invalido:" + index);
		}
		return pt;
	}

	@Override
	public void trasnferToIndex(int index) {
		pt = calcIndexPosition(index);
		mundo.transferOrganismo(this, pt.x, pt.y);
	}

	@Override
	public String getCode() {
		return listInstructions(AvidaConsts.WORD_SIZE - sp(), true);
	}
}
