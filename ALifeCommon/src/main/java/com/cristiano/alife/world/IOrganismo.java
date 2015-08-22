package com.cristiano.alife.world;

import java.awt.Color;

import com.cristiano.alife.instructions.Instruction;


public interface IOrganismo {

	void setCurrStack(int to);

	int getReg(int from);

	int getMemory(int reg);

	void wroteFlag();

	void addFitness(float f);

	boolean setMemory(int regVal, int inst);

	void fillTemplate(int sp);

	int sizeBuffer();

	void error();
	
	String dump();
	
	int somaInstructions();

	int searchTemplateFwd();

	int searchTemplateBwd();

	void setReg(int reg, int i);

	int ip();

	IOrganismo child();

	void clearChild();

	void addFitness();

	void addChild();

	void fatalError();

	void push(int i);

	void setIp(int i);

	int pop();

	void decReg(int to);

	void next(int step);

	void incReg(int to);

	void criticalError();

	int getMemorySize();

	int sp();

	void setChild(IOrganismo child);

	Instruction getInstruction();
	Instruction getInstruction(int memoryInstr);

	int id();

	int getCurrStack();

	int[] getBuffer();

	void push(int stack, int val);

	int pop(int stack);

	void kill();

	boolean validate(IOrganismo container);

	float getError();

	void checkTick();

	void run();

	IOrganismo parent();

	boolean isAlive();

	void save();

	int childCount();

	Instruction getInstructionAt(int i);

	String hash();

	boolean setMemory(int index, int v, boolean punish);

	void clearParent();

	int getGeneration();

	void setParent(IOrganismo parent);

	void reset(int memSize, int sp);

	void setPos(int x,int y);
	int getX();
	int getY();

	boolean validate();

	void setStartPoint(int i);

	Color getColor();

	int age();

	int oid();

	String getCode();
	int getColorCode();

	boolean hasStarted();

	void setStarted();

	int incTask();

	int getTask();

	void addEnergy(float energy);

	void markTaskComplete(int taskId);

	boolean hasCompletedTask();

	IOrganismo getNeighbourAt(int index);
	void trasnferToIndex(int index);

	int getEnergy();


}
