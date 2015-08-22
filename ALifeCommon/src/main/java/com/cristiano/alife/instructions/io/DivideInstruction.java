package com.cristiano.alife.instructions.io;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class DivideInstruction extends BaseInstruction  {
	public DivideInstruction(IWorld mundo) {
		super(mundo);
	}
	@Override
	public void executa(IOrganismo o) {
		
		mundo.getMutation().divisionMutation(o);
		if (o.child()!=null){
			if (!o.child().isAlive()){
				mundo.dealloc(o.child());
				o.criticalError();
				o.clearChild();
				return;
			}
			
			//child veio de um connect...
			if (o.child().hasStarted()){
				o.child().clearParent();
				o.clearChild();
				o.criticalError();
				return;
			}
			o.child().setParent(o);
			if (mundo.start(o.child())){
				if (o.child()==null){
					o.clearChild();
					return;
				}
				o.addChild();
				o.child().clearParent();
				o.clearChild();
				o.addFitness();
				
			} else {
				o.clearChild();
				o.criticalError();
			}
		} else {
			o.fatalError();
		}
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name,"divide and start new program");
	}

}
