package com.cristiano.javida;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.consts.TierraConsts;
import com.cristiano.alife.world.MundoBase;
import com.cristiano.alife.world.ICPU;
import com.cristiano.alife.world.IManageInstructions;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;
import com.cristiano.alife.world.WorldSettings;
import com.cristiano.jTierra.instructions.InstructionBuilderTierra;
import com.cristiano.javida.organismos.OrganismoTierra;
import com.cristiano.utils.CRMathUtils;
import com.cristiano.utils.Log;

public class MundoTierra extends MundoBase implements IWorld {

	public final List<IOrganismo> allocated = new ArrayList<IOrganismo>();

	public CPU cpu;
	

	public MundoTierra(int memorySize) {
		super();
		cpu = new CPU(this, memorySize);
	}

	public MundoTierra() {
		this(TierraConsts.MEMORY_SIZE);
	}
	
	@Override
	protected void initSettings() {
		settings=new WorldSettings("tierra.ini",TierraConsts.MAX_ORGANISMOS);
	}
	
	@Override
	protected IManageInstructions initInstructions() {
		return new InstructionBuilderTierra(this);
	}

	@Override
	protected IOrganismo instantiateOrganismo(int memSize, int sp) {
		IOrganismo o = getFromRecycle(memSize, sp);
		if (o == null) {
			o = new OrganismoTierra(this, memSize, sp);
		}
		return o;
	}

	@Override
	protected int getValidStartingPoint(int memSize, IOrganismo parent) {
		return cpu.getValidMemory(memSize);
	}

	
	public void addOrganismo(int memSize, int sp, IOrganismo o) {
		super.addOrganismo(o);
		cpu.allocate(sp, memSize, o);
	}

	

	@Override
	public IOrganismo alloc(int memSize, IOrganismo parent) {
		int sp = -1;
		while (sp < 0) {
			sp = cpu.getValidMemory(memSize);
			if (sp < 0) {
				killAllocatedOrphans();
				killWorst(true);
			}
		}
		IOrganismo o = instantiateOrganismo(memSize, sp);
		cpu.allocate(sp, memSize, o);
		allocated.add(o);
		o.setParent(parent);
		return o;
	}

	@Override
	public void dealloc(IOrganismo o) {
		super.dealloc(o);
		if (o == null) {
			return;
		}
		cpu.deallocate(o);
		allocated.remove(o);
	}

	@Override
	public boolean start(IOrganismo o) {
		if (!super.start(o)){
			return false;
		}
		allocated.remove(o);
		cpu.start(o);
		addOrganismo(o);
		return true;
	}

	

	protected void checkTick(float error, float totalMemory, int maxMemory,
			int minMemory) {
		super.checkTick(error, totalMemory, maxMemory, minMemory);
		float perc = getMemoryUsePerc();
		perc = (float) CRMathUtils.round(perc, 2);
		int avg = (int) (totalMemory / lastSize);

		error = (float) CRMathUtils.round(error / lastSize, 2);

		Log.info(tick + ":> Orgs:" + lastSize + " Alloc:" + allocated.size()
				+ " [MEM:" + perc + "% Avg:" + avg + " Max:" + maxMemory
				+ " Min:" + minMemory + "] E:" + error + " Freed:" + killCount
				);
		killCount = 0;
		// killFirstAllocated();

		if (lastSize <= 1) {
			criaOrganismo(TierraConsts.DEFAULT_ANCESTOR);
		}
	}
	
	public float getMemoryUsePerc() {
		return cpu.getMemoryUsePerc();
	}
	
	@Override
	public void run() {
		super.run();
	}
	
	private void killAllocatedOrphans() {
		IOrganismo choosen = null;
		int i = 0;
		while (i < allocated.size()) {
			choosen = allocated.get(i);
			if (!choosen.parent().isAlive()) {
				dealloc(choosen);
				i--;
			}
			i++;
		}
	}

	public void desenha(Graphics graphics) {
		cpu.graphics = graphics;
		run();
	}

	
	@Override
	public ICPU cpu() {
		return cpu;
	}

	
	@Override
	public int getMemory(int pos) {
		return cpu.getMemory(pos);
	}

	@Override
	public void click(int x, int y) {
		x -= TierraConsts.GRAPH_OFFSET;
		y -= TierraConsts.GRAPH_OFFSET;
		x/=TierraConsts.GRAPH_SIZE;
		y/=TierraConsts.GRAPH_SIZE;
		int k = 5;
		for (int i = x - k; i <= x + k; i++) {
			for (int j = y - k; j <= y + k; j++) {
				randomizePosition(i, j);
			}
		}
		
	}
	
	private void randomizePosition(int x, int y) {
		int memoryPos = y * ALifeConsts.GRAPH_WIDTH 
				+ x ;
		randomize(memoryPos);
	}

	

}
