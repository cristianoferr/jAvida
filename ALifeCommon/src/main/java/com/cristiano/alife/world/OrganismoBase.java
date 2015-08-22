package com.cristiano.alife.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.cristiano.alife.ALifeIO;
import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.instructions.io.ConnectInstruction;
import com.cristiano.alife.instructions.io.DisconnectInstruction;
import com.cristiano.alife.instructions.io.DivideInstruction;
import com.cristiano.alife.instructions.io.MalInstruction;
import com.cristiano.alife.instructions.misc.NopInstruction;
import com.cristiano.utils.CRJavaUtils;
import com.cristiano.utils.CRMathUtils;
import com.cristiano.utils.Log;

public class OrganismoBase implements IOrganismo {
	private static int orgsCount = 0;
	private static int objCount = 0;

	// aponta para a pilha atual
	private int currStack;

	private int id = 0, oid = 0;
	private int bonusEnergy = 0;
	protected IWorld mundo;
	private Pilha stacks[];
	protected IOrganismo child = null;
	protected int memoryPoolCount = 1; // Quantidade de pools, default é 1,
										// quando tiver filho é 2

	int currTask;
	protected List<Integer> completedTasks = new ArrayList<Integer>();
	int age;

	// para achar o template
	protected int sizeBuffer;
	protected int[] buffer = new int[ALifeConsts.MAX_BUFFER];
	private float error;

	private IOrganismo parent;
	private int parentId = 0;
	private int colorCode = 0;

	private boolean hasStarted = false;
	private boolean alive;
	private int childCount;
	private int lastChildCount;
	private int childlessCounter = 0;
	private int generation = 0;

	private boolean wroteFlag;
	protected boolean parasitic;
	protected float tickError = 0;

	int somaInsts = 0;
	protected MutationControl mutation;
	protected int childId;

	public OrganismoBase(IWorld mundo, int memSize, int sp) {
		oid = ++objCount;
		this.mundo = mundo;
		initMemory();

		mutation = mundo.getMutation();
		initRegs();
		reset(memSize, sp);
	}

	protected void initMemory() {
	}

	@Override
	public void reset(int memSize, int sp) {
		id = ++orgsCount;
		childId = 0;
		completedTasks.clear();
		colorCode = 0;
		setMemorySize(memSize);
		setStartPoint(sp);
		initBuffer();
		initStacks();
		setIp(sp);
		currStack = 0;
		clearChild();
		parent = null;
		hasStarted = false;
		error = 0;
		age = 0;
		alive = true;
		bonusEnergy = 0;
		parasitic = false;
		wroteFlag = true;
		lastChildCount = 0;
		childlessCounter = 0;
		childCount = 0;
		somaInsts = 0;
		currTask = 0;

	}

	protected void setMemorySize(int memSize) {
		Log.fatal("Undefined setMemorySize");
	}

	public Color getColor() {
		Color cor = null;

		cor = new Color(getColorCode());

		return cor;
	}

	public int getColorCode() {
		if (colorCode != 0) {
			return colorCode;
		}
		// int memorySize = getMemorySize();
		// if (parent == null) {
		colorCode = somaInstructions() * 10;
		// } else {
		// int dif = difFromFather(memorySize) * memorySize /10;
		// addFitness(dif * mundo.settings().diffFromFatherReward);
		// colorCode = parent.getColorCode() + CRJavaUtils.randomInt(0, dif);
		// }
		/*
		 * if (colorCode < 0) { colorCode = -colorCode; }
		 */
		return colorCode;
	}

	private int difFromFather(int memorySize) {

		int code = somaInstructions() / memorySize;
		int parentSize = parent.getMemorySize();
		if (parentSize < ALifeConsts.MIN_MEMORY_CHILD * memorySize) {
			parentSize = (int) (ALifeConsts.MIN_MEMORY_CHILD * memorySize);
		}
		int parentCode = parent.somaInstructions() / parentSize;
		int dif = code - parentCode;
		return dif;
	}

	public void setStartPoint(int i) {
		Log.fatal("Undefined setMemorySize");
	}

	private void initStacks() {

		stacks = new Pilha[ALifeConsts.STACKS];
		for (int i = 0; i < ALifeConsts.STACKS; i++) {
			stacks[i] = new Pilha(this);
		}
	}

	protected void initRegs() {
		Log.fatal("Undefined initRegs");
	}

	private void initBuffer() {
		for (int i = 0; i < ALifeConsts.MAX_BUFFER; i++) {
			buffer[i] = 0;
		}
		sizeBuffer = 0;
	}

	@Override
	public int sp() {
		Log.fatal("Undefined sp()");
		return 0;
	}

	@Override
	public int ip() {
		return getReg(ALifeConsts.IP_REG);
	}

	public void setIp(int i) {
		setReg(ALifeConsts.IP_REG, i);
	}

	@Override
	public boolean setMemory(int index, int v) {
		return setMemory(index, v, true);
	}

	@Override
	public void setReg(int i, int v) {
		Log.fatal("Undefined setReg");
	}

	@Override
	public int getReg(int i) {
		Log.fatal("Undefined getReg");
		return 0;
	}

	@Override
	public void push(int i, int j) {
		stacks[ALifeConsts.calcIndex(i, ALifeConsts.STACKS)].push(j);

	}

	@Override
	public int pop(int i) {
		return stacks[ALifeConsts.calcIndex(i, ALifeConsts.STACKS)].pop();
	}

	@Override
	public void decReg(int to) {
		setReg(to, getReg(to) - 1);
	}

	@Override
	public void incReg(int to) {
		setReg(to, getReg(to) + 1);
	}

	@Override
	public void push(int reg) {
		push(currStack, reg);
	}

	@Override
	public int pop() {
		return pop(currStack);
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public int getMemorySize() {
		Log.fatal("Undefined getMemorySize");
		return 0;
	}

	@Override
	public boolean setMemory(int index, int v, boolean punish) {
		return false;
	}

	@Override
	public Instruction getInstruction(int memoryInstr) {
		Instruction i = mundo.getInstruction(memoryInstr);
		return i;
	}

	@Override
	public Instruction getInstruction() {
		int currentMemory = getCurrentMemory();
		return getInstruction(currentMemory);
	}

	protected int getCurrentMemory() {
		return getMemory(ip());
	}

	@Override
	public void fillTemplate(int sp) {
		int mem = 0;
		int pos = ip() + sp;
		sizeBuffer = 0;
		do {
			mem = getMemory(pos);
			if (mem == ALifeConsts.NOP0 || mem == ALifeConsts.NOP1) {
				buffer[sizeBuffer] = (mem == ALifeConsts.NOP0 ? ALifeConsts.NOP1
						: ALifeConsts.NOP0);
				sizeBuffer++;
			}
			pos++;
		} while ((mem == ALifeConsts.NOP0 || mem == ALifeConsts.NOP1)
				&& sizeBuffer < ALifeConsts.MAX_BUFFER);
	}

	@Override
	public int searchTemplateFwd() {
		if (sizeBuffer == 0) {
			return -1;
		}
		int indexBuffer = 0;
		for (int i = 0; i < ALifeConsts.TEMPLATE_LIMIT; i++) {
			int index = ip() + i;
			int m = getMemory(index);
			if (m == buffer[indexBuffer]) {
				indexBuffer++;
			} else {
				indexBuffer = 0;
			}
			if (indexBuffer == sizeBuffer) {// && getMemory(index + 1) > 1) {
				return index + 1;
			}
		}
		return -1;
	}

	public String debugInfo(String prefix) {
		String saida = prefix + " = ";

		for (int i = 0; i < ALifeConsts.REGISTRADORES; i++) {
			saida += ALifeConsts.getLetter(i) + "X:"
					+ ALifeConsts.numberFormat(getReg(i)) + " ";
		}
		saida += "SP:" + ALifeConsts.numberFormat(sp()) + " IP:"
				+ ALifeConsts.numberFormat(ip()) + " E:" + ((int) error)
				+ " ID:" + id;
		if (child != null) {
			saida += " (Child:" + child.id() + " size:"
					+ ALifeConsts.numberFormat(child.getMemorySize()) + " )";
		}

		saida += "\n#" + stacks[currStack].debugInfo();
		return saida;
	}

	@Override
	public String toString() {
		String ret = headerOutput();
		ret += getCode();
		return ret;
	}

	@Override
	public String getCode() {
		return listInstructions(getMemorySize(), true);
	}

	protected String headerOutput() {
		String ret = "#jAvida	Id:" + id + " Gen:" + generation + " ChildCount:"
				+ childCount + " Hash:" + hash() + " Age:" + age + "	Size: "
				+ getMemorySize() + "\n";
		ret += "#REGS: " + debugInfo("") + "\n";
		if (parasitic) {
			ret += "# Parasitic \n";
		}
		return ret;
	}

	protected String listInstructions(int limit, boolean includeNoop) {
		String ret = "";
		int step = 1;
		int sp = sp();
		for (int i = sp; i < sp + limit; i += step) {
			step = 1;
			int currentMemory = getMemory(i);
			Instruction inst = getInstructionAt(i);

			if (inst != null) {
				if (!(inst instanceof NopInstruction) || includeNoop
						|| i < getMemorySize() * 1.2f) {
					String s = listInstruction(i, inst, currentMemory);
					// (startPoint + i) + ":"
					ret += s;
				}
				step = inst.getStep();
			} else {
				String s = listInstruction(i, inst, currentMemory);
				ret += s;
			}
		}
		return ret;
	}

	protected String listInstruction(int i, Instruction inst, int currentMemory) {
		String name = "";

		if (inst != null) {
			name = inst.getDescription(this, i);
		} else {
			name = "" + currentMemory;
		}
		String s = (i) + ":: [" + currentMemory + "] =" + name + "\n";
		if (ip() == i) {
			s = ">" + s;
		}
		return s;
	}

	@Override
	public String hash() {
		String saida = getMemorySize() + "_";
		while (saida.length() < 4) {
			saida = "0" + saida;
		}
		int soma = somaInstructions() * getMemorySize();
		saida = saida + CRMathUtils.toHexString(soma);
		if (parasitic) {
			saida = saida + "P";
		}

		return saida;
	}

	public int somaInstructions() {
		if (somaInsts > 0) {
			return somaInsts;
		}
		int memorySize = getMemorySize();
		int sp = sp();
		for (int i = 0; i < memorySize; i++) {
			int mem = getMemory(sp + i);
			somaInsts += mem;
		}
		return somaInsts;
	}

	@Override
	public void save() {
		ALifeIO.saveToFile(this);
	}

	@Override
	public int searchTemplateBwd() {
		if (sizeBuffer == 0) {
			return -1;
		}
		int indexBuffer = sizeBuffer;
		for (int i = 0; i < ALifeConsts.TEMPLATE_LIMIT; i++) {
			int index = ip() - i;
			int m = getMemory(index);
			if (m == buffer[indexBuffer - 1]) {
				indexBuffer--;
			} else {
				indexBuffer = sizeBuffer;
			}
			if (indexBuffer == 0) {
				// if (getMemory(index - 1) > 1) {
				return index + sizeBuffer;
				// } else {
				// indexBuffer = sizeBuffer;
				// }
			}
		}
		return -1;
	}

	@Override
	public float getError() {
		return tickError + error;
	}

	@Override
	public void criticalError() {
		criticalError(1);

	}

	protected void criticalError(int mult) {
		error += (mundo.settings().errorCritical * mult);
		/*
		 * if (id() == mundo.getViewer().selectedOrgId()) {
		 * Log.info("Selected critical Error:" +
		 * CRJavaUtils.getMethodDescriptionAt(1) + "  " +
		 * CRJavaUtils.getMethodDescriptionAt(2)); }
		 */
	}

	@Override
	public void fatalError() {
		if (id() == mundo.getViewer().selectedOrgId()) {
			Log.warn("Alocated fatalError"
					+ CRJavaUtils.getMethodDescriptionAt(1) + "  "
					+ CRJavaUtils.getMethodDescriptionAt(2));
		}
		/*
		 * Log.debug("fatalErr0:" + id() + "  " +
		 * CRJavaUtils.getMethodShortDesc(1) + "   " +
		 * CRJavaUtils.getMethodShortDesc(2));
		 */
		if (!alive) {
			return;
		}
		error += mundo.settings().errorCritical;
		if (parent != null) {
			parent.error();
		}
		mundo.dealloc(this);
	}

	@Override
	public void error() {
		error += mundo.settings().errorNormal;

		if (id() == mundo.getViewer().selectedOrgId()) {
			Log.info("Selected Error:" + CRJavaUtils.getMethodDescriptionAt(1)
					+ "  " + CRJavaUtils.getMethodDescriptionAt(2));
		}

	}

	@Override
	public void addFitness(float f) {
		addEnergy(ALifeConsts.FITNESS_TO_ENERGY_RATIO * f);
		error -= mundo.settings().fitnessNormal * f;
		/*
		 * if (error < -9000) { //Log.warn("err:" + error); }
		 */

		/*
		 * if (id() == mundo.getViewer().selectedOrgId()) {
		 * //Log.info("Selected Fitness:" +
		 * CRJavaUtils.getMethodDescriptionAt(1) + "  " +
		 * CRJavaUtils.getMethodDescriptionAt(2)); }
		 */

	}

	@Override
	public void addFitness() {
		addFitness(1);
	}

	@Override
	public boolean validate(IOrganismo memoryContainer) {
		if (memoryContainer == null) {
			memoryContainer = this;
		}
		int memorySize2 = getMemorySize();
		memorySize2 = ALifeConsts.validateMemorySize(parent(), memorySize2);
		if (memorySize2 == 0) {
			parent().criticalError();
			return false;
		}
		boolean hasMal = false;
		boolean hasDivide = false;

		boolean hasRequireTemplate = false;

		int sp = sp();
		int i = 0;
		int step = 1;
		while (i < memorySize2) {
			Instruction inst = memoryContainer.getInstructionAt(sp + i);
			if (inst != null) {
				step = inst.getStep();
				if ((inst instanceof MalInstruction)
						|| (inst instanceof ConnectInstruction)) {
					hasMal = true;
				}
				if ((inst instanceof DivideInstruction)
						|| (inst instanceof DisconnectInstruction)) {
					hasDivide = true;
				}
				if (inst.requiresTemplate()) {
					hasRequireTemplate = true;
				}
			} else {
				step = 1;
			}
			i += step;
		}

		if (!hasMal || !hasDivide) {
			return false;
		} else {
			addFitness();
			return true;
		}

	}

	@Override
	public void addChild() {
		childCount++;
		if (childCount == ALifeConsts.AUTO_SAVE_PROGRAM_WITH_CHILD_COUNT) {
			if (child != null) {
				child.save();
			}
		}

	}

	@Override
	public void kill() {
		alive = false;
		clearChild();
		clearParent();

	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void run() {
		tick();
		int min = 4 < bonusEnergy ? 4 : bonusEnergy;
		for (int i = 0; i < min; i++) {
			if (isAlive()) {

				tick();
				bonusEnergy--;
			}
		}
		/*
		 * for (int i = 0; i < 1 + bonusEnergy; i++) { if (!isAlive()) { return;
		 * } tick(); } bonusEnergy = 0;
		 */
		if (isAlive()) {
			mutation.errorLimitAction(this);
			mutation.randomMutation(this);
		}
	}

	private void tick() {
		if (id() == mundo.getViewer().selectedOrgId()) {
			Log.trace("tick");
		}
		Instruction instruction = getInstruction();
		int step = 1;
		if (instruction != null) {
			instruction.executa(this);
			step = instruction.getStep();
		} else {
			criticalError();
		}
		next(step);
	}

	@Override
	public void checkTick() {
		/*
		 * if (id()==744658){ Log.info(lastChildCount+"="+childCount); }
		 */

		if (lastChildCount == childCount) {
			childlessCounter++;
			if (childlessCounter > 3) {
				error();
			}
		} else {
			childlessCounter = 0;
		}

		if (!wroteFlag) {
			error();
			return;
		}

		if (error > ALifeConsts.ERROR_UPPER_LIMIT) {
			error = ALifeConsts.ERROR_UPPER_LIMIT;
		}
		if (error < -ALifeConsts.ERROR_UPPER_LIMIT) {
			error = -ALifeConsts.ERROR_UPPER_LIMIT;
		}

		wroteFlag = false;
		float childReward = (childCount - lastChildCount)
				* mundo.settings().childReward;
		if (childReward > 0) {
			addFitness(childReward);
		}

		// addFitness(getMemorySize() * mundo.settings().getSizeReward);
		lastChildCount = childCount;
		// if (getError()>AvidaConsts.ERROR_LIMIT){
		// addErrorCritical();

		// }

	}

	protected void punishSimilarity() {

	}

	@Override
	public void setCurrStack(int to) {
		currStack = ALifeConsts.calcIndex(to, ALifeConsts.STACKS);

	}

	@Override
	public int getMemory(int reg) {
		Log.fatal("Undefined getMemory");
		return 0;
	}

	@Override
	public void wroteFlag() {
		wroteFlag = true;

	}

	@Override
	public int sizeBuffer() {
		return sizeBuffer;
	}

	@Override
	public IOrganismo child() {
		if (child != null && child.id() != childId) {
			clearChild();
		}

		return child;
	}

	@Override
	public void clearChild() {
		child = null;
		memoryPoolCount = 1;
	}

	@Override
	public void clearParent() {
		if (parent != null && parent.child() == this) {
			parent.clearChild();
		}
		parent = null;
	}

	@Override
	public void next(int step) {
		age++;
		setIp(ip() + step);
	}

	@Override
	public void setChild(IOrganismo child) {

		this.child = child;
		this.childId = child.id();
		memoryPoolCount = (child == null ? 1 : 2);
		if (this.child == this) {
			Log.fatal("Erro!!!");
		}
	}

	@Override
	public int getCurrStack() {
		return currStack;
	}

	@Override
	public int[] getBuffer() {
		return buffer;
	}

	@Override
	public IOrganismo parent() {
		if (parent != null && parentId != parent.id()) {
			parent = null;
		}
		return parent;
	}

	@Override
	public int childCount() {
		return childCount;
	}

	@Override
	public Instruction getInstructionAt(int pos) {
		int currentMemory = getMemory(pos);
		return getInstruction(currentMemory);
	}

	@Override
	public int getGeneration() {
		return generation;
	}

	@Override
	public void setParent(IOrganismo parent) {
		parentId = parent.id();
		this.generation = parent.getGeneration() + 1;
		this.parent = parent;
	}

	@Override
	public void setPos(int x, int y) {
	}

	@Override
	public int getX() {
		return 0;
	}

	public String dump() {
		return toString();
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public boolean validate() {
		return validate(this);
	}

	@Override
	public int age() {
		return age;
	}

	@Override
	public int oid() {
		return oid;
	}

	@Override
	public boolean hasStarted() {
		return hasStarted;
	}

	@Override
	public void setStarted() {
		hasStarted = true;

	}

	@Override
	public int incTask() {
		currTask++;
		if (currTask > mundo.io().countTask()) {
			currTask = 0;
		}
		return currTask;
	}

	@Override
	public int getTask() {
		return currTask;
	}

	@Override
	public void addEnergy(float e) {
		bonusEnergy += e;
	}

	@Override
	public void markTaskComplete(int taskId) {
		completedTasks.add(taskId);

	}

	@Override
	public boolean hasCompletedTask() {
		return completedTasks.contains(currTask);
	}

	@Override
	public IOrganismo getNeighbourAt(int index) {
		Log.fatal("getNeighbourAt not implemented");
		return null;
	}

	@Override
	public int getEnergy() {
		return bonusEnergy;
	}

	@Override
	public void trasnferToIndex(int index) {
		Log.fatal("trasnferToIndex not implemented");
	}
}
