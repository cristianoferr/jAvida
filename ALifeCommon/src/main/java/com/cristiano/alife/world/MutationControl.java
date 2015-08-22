package com.cristiano.alife.world;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.utils.CRJavaUtils;

public class MutationControl {

	private IWorld mundo;
	private WorldSettings settings;
	private int midPt;

	public MutationControl(IWorld mundo) {
		this.mundo = mundo;
		settings = mundo.settings();
		
		midPt=AvidaConsts.ORGS_AXIS_Y/2;
	}

	// chamado cada vez que uma instrução é gravada em memoria
	public int mutateInstruction(int inst, IOrganismo o) {
		float rnd = CRJavaUtils.random();
		float mutationChance = calcMutationChance(settings.writeInstructionMutationChance,o);
		if (rnd < mutationChance) {
			inst = changeInstruction(inst);
		}

		return inst;
	}

	public float calcMutationChance(float mutationChance, IOrganismo o) {
		if (settings.mutationType == ALifeConsts.MUTTYPE_OCCUPATION_RATIO) {
			float ratio = ((float) mundo.size())
					/ (settings.occupationRatio * settings.maxOrganismos);
			/*
			 * if (ratio<0.01f){ ratio=0.01f; }
			 */
			// return mutationChance2;
			return ratio * mutationChance;
		}
		
		if (settings.mutationType == ALifeConsts.MUTTYPE_POSITIONAL_CENTER) {
			if (o==null){
				return 0;
			}
			float dif=Math.abs(midPt-o.getY());
			float ratio = dif/midPt* mutationChance;
			return ratio;
		}
		return 0;
	}

	// Esse método é chamado quando o organismo gera um filho (podendo mudar o
	// organismo, representando decaimento, quanto mais divisões maiores as
	// chances)
	public void divisionMutation(IOrganismo o) {
		float rnd = CRJavaUtils.random();
		float mutationChance = calcMutationChance(settings.getDivisionMutationChance,o);
		if (rnd < mutationChance) {
			/*
			 * if (mundo.size()<AvidaConsts.DIVISION_MIN_AMOUNT){ return; }
			 */
			randomizeInstruction(o);
			// Log.info("Mutation index:"+index+" inst:"+inst+" id:"+o.id());
		}

	}

	// chamado a cada tick
	public void randomMutation(IOrganismo o) {
		double random = Math.random();
		float mutationChance = calcMutationChance(settings.getRandomMutation,o);
		if (random < mutationChance) {
			/*
			 * if (mundo.size()<AvidaConsts.DIVISION_MIN_AMOUNT){ return; }
			 */
			randomizeInstruction(o);
		}

	}

	private void randomizeInstruction(IOrganismo o) {

		int memPos = CRJavaUtils.randomInt(-10, 10);

		int index = o.ip() + memPos;
		int inst = o.getMemory(index);
		inst = changeInstruction(inst);
		o.setMemory(index, inst);
	}

	private int changeInstruction(int inst) {
		int instChange = CRJavaUtils.randomInt(-10, 10);
		inst += instChange;
		if (inst < 0) {
			inst = CRJavaUtils.randomInt(2, 10);
		}
		if (inst >= settings.getInstructionCount) {
			inst -= CRJavaUtils.randomInt(2, 10);
		}
		return inst;
	}

	public void randomPosition(int memoryPos) {
		int rnd = CRJavaUtils.randomInt(0, settings.getInstructionCount);
		mundo.cpu().setMemory(memoryPos, rnd);

	}

	// chamado quando o erro do organismo excedeu o limite definido
	public void errorLimitAction(IOrganismo o) {
		float rnd = CRJavaUtils.random();
		float error = o.getError();
		float ratio = error / settings.getErrorLimit;
		float mutationChance = calcMutationChance(settings.errorKillChance
				* ratio,o);
		if (rnd < mutationChance) {
			o.fatalError();
		}

	}

}
