package com.cristiano.alife.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.cristiano.utils.CRJavaUtils;
import com.cristiano.utils.Log;

class SingleTask {

	public int input1;
	public int output;
	public String name;

	public SingleTask(String name, int input1, int output) {
		this.input1 = input1;
		this.output = output;
		if (output==0){
			Log.error("Output 0 para "+name);
		}
		this.name = name;
	}

}

public class TaskControl {

	List<SingleTask> tasks = new ArrayList<SingleTask>();
	private IWorld mundo;

	public TaskControl(IWorld mundo) {
		this.mundo = mundo;
		for (int i = 0; i < 50000; i++) {
			generateRandomTask();
		}
	}
/*
	private void generateRandomTask() {
		float rnd = (float) Math.random();
		int opt = CRJavaUtils.randomInt(1, 8);
		int a = CRJavaUtils.randomInt(10, 100);
		int b = CRJavaUtils.randomInt(1010, 3000);

		switch (opt) {
		case 1:
			addTask("A+B:" + a + "+" + b, a, b, a + b, opt);
			break;
		case 2:
			addTask("AxB:" + a + "x" + b, a, b, a * b, opt);
			break;
		case 3:
			addTask("B-A:" + b + "-" + a, a, b, b - a, opt);
			break;
		case 4:
			addTask("B+A/2:" + b + "+" + a + "/2", a, b, b + a / 2, opt);
			break;
		case 5:
			addTask("(B+A)*3: (" + b + "+" + a + ")/2", a, b, (b + a) * 32, opt);
			break;
		case 6:
			addTask("(shl(B,2)+shr(A,3)): (shl(" + b + ",2)+shr(" + a + ",3))", a, b, (b << 2) + (a >> 3), opt);
			break;
		case 7:
			addTask("(shr(B)+a: (shr(" + b + ",1)+" + a, a, b,( b >> 1)+a, opt);
			break;
		}
	}*/

	private void generateRandomTask() {
		int input = CRJavaUtils.randomInt(10, 256);
		
		// (0 && 1) || (2 && 3)
		
	}

	private void addTask(String name, int input1, int output) {
		SingleTask task = new SingleTask(name, input1, output);
		tasks.add(task);
	}

	public void verifyTask(IOrganismo o, int vlrGerado) {
		int taskId = o.getTask();
		SingleTask task = getTask(taskId);
		int esperado = task.output;
		if (vlrGerado == esperado) {
			String name = task.name;
			Log.info("Sucesso na task[" + taskId + "]: " + name + "=" + vlrGerado + " em: " + o.id());
			o.addFitness(mundo.settings().taskCompleteBonusFitness);
			o.addEnergy(mundo.settings().taskCompleteBonusEnergy);
			o.markTaskComplete(taskId);
			mundo.markProgram(o,Color.red);
		//} else {
			 //o.error();
		}

	}

	public void writeInput(IOrganismo o, int pos) {
		int taskId = o.getTask();
		SingleTask task = getTask(taskId);
		o.setMemory(pos, task.input1);

	}

	private SingleTask getTask(int taskId) {
		taskId = taskId % tasks.size();
		return tasks.get(taskId);
	}

	public int countTask() {
		return tasks.size();
	}

}
