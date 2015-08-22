package com.cristiano.alife.instructions.transfers;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

/*
 * STAX: (to) <- from
 * */
public class SaveToRegisterPositionInstruction extends BaseInstruction {
	public SaveToRegisterPositionInstruction(IWorld mundo) {
		super(mundo);
	}

	@Override
	public void executa(IOrganismo o) {
		int inst = o.getReg(getByteOrganismo(o, 1));
		inst = mundo.getMutation().mutateInstruction(inst,o);
		/*
		 * if (!mundo.isValidInstruction(inst)){ o.fatalError(); return; }
		 */

		int toPos = o.getReg(getByteOrganismo(o, 2));
		o.setMemory(toPos, inst);
		o.wroteFlag();
	}

	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)),
				"(" + ALifeConsts.getLetter(o.getMemory(ip + 2)) + ") <- " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "");
	}

}
