package com.cristiano.alife.instructions;

import java.awt.Color;

import com.cristiano.alife.world.IOrganismo;

public interface Instruction {

	void executa(IOrganismo o);

	void setName(String name);

	void setId(int id);

	int getId();

	String getName();

	Color getColor();

	int getStep();

	String getDescription(IOrganismo o,int ip);

	//se true ent�o � uma instru��o que usa de template (sequencias de nop0 e nop1)
	boolean requiresTemplate();
}
