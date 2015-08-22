package com.cristiano.alife.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.viewer.IViewLife;



public interface IWorld {

	void run();
	IOrganismo criaOrganismo(String file);
	IOrganismo criaOrganismo(int memSize);

	MutationControl getMutation();
	List<IOrganismo> getOrganismos();

	boolean start(IOrganismo child);

	void dealloc(IOrganismo child);

	IOrganismo alloc(int memSize, IOrganismo o);

	WorldSettings settings();

	ICPU cpu();

	Instruction getInstruction(String line);

	int size();

	Instruction getInstruction(int memoryInstr);

	int getMemory(int ip);
	IOrganismo getAncestor();
	
	void addInstruction(int id, String name, Instruction inst);
	Map<Integer, Instruction> getInstructions();
	IOrganismo killWorst(boolean flagIncludePositive);
	boolean isValidInstruction(int inst);

	float getMemoryUsePerc();
	void click(int x, int y);
	void save();
	void setViewer(IViewLife avidaViewer);
	boolean contains(IOrganismo org);
	IViewLife getViewer();
	TaskControl io();
	IOrganismo getOrganismoAt(int x, int y);
	void markProgram(IOrganismo o, Color red);
	void drawLink(IOrganismo o, IOrganismo child);
	void drawUnLink(IOrganismo o, IOrganismo child);
	void removeOrganismoAt(int x, int y);
	void transferOrganismo(IOrganismo org, int x, int y);
}
