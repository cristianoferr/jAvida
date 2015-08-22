package com.cristiano.alife.instructions.branch;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class JumpInstruction extends BaseInstruction  {
	boolean fwd=true;
	boolean bwd=true;
	
	public JumpInstruction(IWorld mundo,boolean fwd,boolean bwd) {
		super(mundo);
		this.fwd=fwd;
		this.bwd=bwd;
	}
	
	@Override
	public boolean requiresTemplate() {
		return true;
	}
	
	@Override
	public void executa(IOrganismo o) {
		o.fillTemplate(getStep());
		if (o.sizeBuffer() == 0) {
			o.criticalError();
			return;
		}
		int posF = -1;
		int posB = -1;
		if (fwd && bwd) {
			posF = o.searchTemplateFwd();
			posB = o.searchTemplateBwd();
			if (posB<0){
				returnPos(o, posF);
				return;
			}
			if (posF<0){
				returnPos(o, posB);
				return;
			}
			int difF=posF-o.ip();
			int difB=o.ip()-posB;
			returnPos(o, difF<difB?posF:posB);
			
		} else {
			if (fwd) {
				posF = o.searchTemplateFwd();
				returnPos(o, posF);
			}
			if (bwd) {
				posB = o.searchTemplateBwd();
				returnPos(o, posB);
			}
		}
	}

	private void returnPos(IOrganismo o, int pos) {
		if (pos>=0){
			o.setIp(pos-1);
		} else {
			o.error();
		}
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return comment(name,"ip <- template position ");
	}
	

}
