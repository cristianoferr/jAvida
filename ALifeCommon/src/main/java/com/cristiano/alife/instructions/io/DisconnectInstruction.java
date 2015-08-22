package com.cristiano.alife.instructions.io;

import java.awt.Color;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

//DISCONNECT
public class DisconnectInstruction extends BaseInstruction {

	public DisconnectInstruction(IWorld mundo) {
		super(mundo);
	}
	
	@Override
	public int getStep() {
		return 1;
	}

	@Override
	public void executa(IOrganismo o) {
		mundo.getMutation().divisionMutation(o);
		
		if (o.child() == null) {
			//o.error();
			return;
		}
		/*
		
		//child foi criado via mal...
		if (!o.child().hasStarted()) {
			o.child().clearParent();
			mundo.dealloc(o.child());
			o.clearChild();
			o.fatalError();
			return;
		}
		mundo.markProgram(o.child(), Color.white);
		o.clearChild();
		mundo.drawUnLink(o,o.child());
		o.addChild();
		o.addFitness(2);*/
		
	}

	@Override
	public String getDescription(IOrganismo o, int ip) {
		return comment(name,"release from connected program");
	}

}
