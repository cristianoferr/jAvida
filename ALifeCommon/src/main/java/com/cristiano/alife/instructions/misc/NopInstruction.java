package com.cristiano.alife.instructions.misc;

import java.awt.Color;

import com.cristiano.alife.world.BaseInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;

public class NopInstruction extends BaseInstruction  {
	int to=0;
	public NopInstruction(IWorld mundo,int i) {
		super(mundo);
		this.to=i;
	}
	@Override
	public void executa(IOrganismo o) {
		
	}

	public void setId(int n){
		super.setId(n);
		if (to==0){
		color=Color.white;
		} else {
			color=Color.gray;
		}
	}
}
