package com.cristiano.alife.world;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.instructions.arithmetic.AddInstruction;
import com.cristiano.alife.instructions.arithmetic.BitInstruction;
import com.cristiano.alife.instructions.arithmetic.DecInstruction;
import com.cristiano.alife.instructions.arithmetic.IncInstruction;
import com.cristiano.alife.instructions.arithmetic.SetBitInstruction;
import com.cristiano.alife.instructions.arithmetic.ShiftLeftInstruction;
import com.cristiano.alife.instructions.arithmetic.ShiftRightInstruction;
import com.cristiano.alife.instructions.arithmetic.SubInstruction;
import com.cristiano.alife.instructions.arithmetic.ZeroInstruction;
import com.cristiano.alife.instructions.branch.AdrInstruction;
import com.cristiano.alife.instructions.branch.CallByteInstruction;
import com.cristiano.alife.instructions.branch.CallInstruction;
import com.cristiano.alife.instructions.branch.CallRegInstruction;
import com.cristiano.alife.instructions.branch.JumpByteInstruction;
import com.cristiano.alife.instructions.branch.JumpInstruction;
import com.cristiano.alife.instructions.branch.JumpRegInstruction;
import com.cristiano.alife.instructions.branch.RetInstruction;
import com.cristiano.alife.instructions.io.ConnectInstruction;
import com.cristiano.alife.instructions.io.DisconnectInstruction;
import com.cristiano.alife.instructions.io.DivideInstruction;
import com.cristiano.alife.instructions.io.ScanInstruction;
import com.cristiano.alife.instructions.io.TransferInstruction;
import com.cristiano.alife.instructions.logic.AndInstruction;
import com.cristiano.alife.instructions.logic.IFDifInstruction;
import com.cristiano.alife.instructions.logic.IFLessInstruction;
import com.cristiano.alife.instructions.logic.IFZeroInstruction;
import com.cristiano.alife.instructions.logic.NandInstruction;
import com.cristiano.alife.instructions.logic.NorInstruction;
import com.cristiano.alife.instructions.logic.OrInstruction;
import com.cristiano.alife.instructions.logic.XorInstruction;
import com.cristiano.alife.instructions.misc.NopInstruction;
import com.cristiano.alife.instructions.stack.ChangeStackInstruction;
import com.cristiano.alife.instructions.stack.PopInstruction;
import com.cristiano.alife.instructions.stack.PushInstruction;
import com.cristiano.alife.instructions.transfers.LoadFromMemoryInstruction;
import com.cristiano.alife.instructions.transfers.LoadFromRegisterInstruction;
import com.cristiano.alife.instructions.transfers.MovByteInstruction;
import com.cristiano.alife.instructions.transfers.MovInstruction;
import com.cristiano.alife.instructions.transfers.MovToBytePositionInstruction;
import com.cristiano.alife.instructions.transfers.MovToRegisterInstruction;
import com.cristiano.alife.instructions.transfers.SaveToBytePositionInstruction;
import com.cristiano.alife.instructions.transfers.SaveToRegisterPositionInstruction;
import com.cristiano.alife.instructions.transfers.SwapInstruction;

public class InstructionBuilderBase implements IManageInstructions {
	public static int instructionCount = -1;
	
	// arithmetic
	public static final String INSTR_SHL = "SHL";
	public static final String INSTR_SHR = "SHR";
	public static final String INSTR_BIT_TRANSFER = "BIT"; // reg1[regPos] <- reg2[regPos] (3 regs)
	public static final String INSTR_SET_BIT= "SET_BIT"; // reg1[regPos] <- reg2 (3 regs)
	// logic
	// io
	public static final String INSTR_DIVIDE = "DIVIDE";
	public static final String INSTR_MAL = "MAL";
	public static final String INSTR_INPUT = "INPUT";
	public static final String INSTR_OUTPUT = "OUTPUT";
	
	//usado para conectar
	public static final String INSTR_TRANSFER = "TRANSFER"; //TRANSFER regIndex: transfer the program to a empty position
	public static final String INSTR_SCAN = "SCAN";  //SCAN A,B:  A <- checkNeighbour(B): B= index de 0 a 7, A recebe 1 se tiver alguém, 0 se não
	public static final String INSTR_CONNECT = "CONNECT";//CONNECT A,B:  A <- sp do vizinho na posição B: B= index de 0 a 7, A recebe SP, child vira o vizinho, erroCritico se falhar
	public static final String INSTR_DISCONNECT = "DISCONNECT";//DISCONNECT
	
	// misc
	// stack
	// branch
	public static final String INSTR_JUMP_BYTE = "JUMP_BYTE";
	public static final String INSTR_JUMP_REG = "JUMP_REG";
	public static final String INSTR_JUMPF = "JMPF";
	public static final String INSTR_JUMPB = "JMPB";
	public static final String INSTR_JUMP = "JMP";
	public static final String INSTR_CALL = "CALL";
	public static final String INSTR_CALL_BYTE = "CALL_BYTE";
	public static final String INSTR_CALL_REG = "CALL_REG";

	public static final String INSTR_RET = "RET";
	public static final String INSTR_ADRB = "ADRB";
	public static final String INSTR_ADR = "ADR";
	public static final String INSTR_ADRF = "ADRF";
	public static final String INSTR_IFZ = "IFZ";
	public static final String INSTR_OR = "OR";
	public static final String INSTR_NOP = "NOP";
	public static final String INSTR_IFLESS = "IFLESS";
	public static final String INSTR_IFDIF = "IFDIF";
	public static final String INSTR_ZERO = "ZERO";
	public static final String INSTR_ADD = "ADD";
	public static final String INSTR_SUB = "SUB";
	public static final String INSTR_NOR = "NOR";
	public static final String INSTR_XOR = "XOR";
	public static final String INSTR_NAND = "NAND";
	public static final String INSTR_AND = "AND";
	public static final String INSTR_MOV = "MOV";// move reg: to <- from
	public static final String INSTR_MVI = "MVI";// Move a byte from memory to a
													// register: to <- byte
	public static final String INSTR_MOVI = "MOVI";// Move Instruction from
													// register position: (to)
													// <- (from)
	public static final String INSTR_MOVM = "MOVM";// Move Instruction from
													// memory position (pega a
													// posição +1): to <- (byte)
	public static final String INSTR_STAX = "STAX";// STAX:(to) <- from
	public static final String INSTR_STA = "STA";// STA:(byte) <- from
	public static final String INSTR_CHANGE_STACK = "CHG_ST";
	public static final String INSTR_INC = "INC";
	public static final String INSTR_DEC = "DEC";
	public static final String INSTR_PUSH = "PUSH";
	public static final String INSTR_POP = "POP";
	public static final String INSTR_SWAP = "SWAP";
	public static final String INSTR_LOADI = "LOADI"; // to <- (from)
	public static final String INSTR_LOADM = "LOADM";// to <- (word)
	protected IWorld mundo;

	public InstructionBuilderBase(IWorld m) {
		build(m);
	}

	public void build(IWorld m) {
		this.mundo = m;
		// primeiro sempre
		NopInstruction nop0 = new NopInstruction(mundo, 0);
		addInstruction(INSTR_NOP, nop0);

		addIOInstructions();
		addGoToInstructions();

		addAdrInstructions();
		addMovInstructions();

		addChangeStack();
		addArithmetic();

		addNopInstructions();

	}

	private void addIOInstructions() {
		addInstruction(INSTR_DIVIDE, new DivideInstruction(mundo));
		addInstruction(INSTR_SCAN, new ScanInstruction(mundo));
		addInstruction(INSTR_TRANSFER, new TransferInstruction(mundo));
		addInstruction(INSTR_CONNECT, new ConnectInstruction(mundo));
		addInstruction(INSTR_DISCONNECT, new DisconnectInstruction(mundo));
		
		//addInstruction(INSTR_INPUT, new InputInstruction(mundo));
		//addInstruction(INSTR_OUTPUT, new OutputInstruction(mundo));
	}

	private void addGoToInstructions() {
		addInstruction(INSTR_CALL, new CallInstruction(mundo));
		addInstruction(INSTR_RET, new RetInstruction(mundo));
		addInstruction(INSTR_JUMP, new JumpInstruction(mundo, true, true));
		addInstruction(INSTR_JUMPF, new JumpInstruction(mundo, true, false));
		addInstruction(INSTR_JUMPB, new JumpInstruction(mundo, false, true));

		addInstruction(INSTR_JUMP_BYTE, new JumpByteInstruction(mundo));
		addInstruction(INSTR_CALL_BYTE, new CallByteInstruction(mundo));

		addInstruction(INSTR_JUMP_REG, new JumpRegInstruction(mundo));
		addInstruction(INSTR_CALL_REG, new CallRegInstruction(mundo));
	}

	private void addAdrInstructions() {
		addInstruction(INSTR_ADR, new AdrInstruction(mundo, true, true));
		addInstruction(INSTR_ADRB, new AdrInstruction(mundo, false, true));
		addInstruction(INSTR_ADRF, new AdrInstruction(mundo, true, false));
	}

	private void addNopInstructions() {
		NopInstruction nop0 = new NopInstruction(mundo, 0);
		addInstruction(INSTR_NOP + "_0", nop0);
		NopInstruction nop1 = new NopInstruction(mundo, 1);
		addInstruction(INSTR_NOP + "_1", nop1);

		ALifeConsts.NOP0 = nop0.getId();
		ALifeConsts.NOP1 = nop1.getId();
	}

	private void addArithmetic() {
		addInstruction(INSTR_BIT_TRANSFER, new BitInstruction(mundo));
		addInstruction(INSTR_SET_BIT, new SetBitInstruction(mundo));
		
		
		addInstruction(INSTR_OR, new OrInstruction(mundo));
		addInstruction(INSTR_ZERO, new ZeroInstruction(mundo));

		addInstruction(INSTR_IFZ, new IFZeroInstruction(mundo));

		addInstruction(INSTR_SHL, new ShiftLeftInstruction(mundo));
		addInstruction(INSTR_SHR, new ShiftRightInstruction(mundo));
		addInstruction(INSTR_INC, new IncInstruction(mundo));
		addInstruction(INSTR_DEC, new DecInstruction(mundo));
		addInstruction(INSTR_PUSH, new PushInstruction(mundo));
		addInstruction(INSTR_POP, new PopInstruction(mundo));

		addInstruction(INSTR_MOVM, new MovToBytePositionInstruction(mundo));
		addInstruction(INSTR_LOADM, new LoadFromMemoryInstruction(mundo));
		addInstruction(INSTR_MVI, new MovByteInstruction(mundo));
		addInstruction(INSTR_STA, new SaveToBytePositionInstruction(mundo));

	}

	private void addChangeStack() {
		addInstruction(INSTR_CHANGE_STACK, new ChangeStackInstruction(mundo));

	}

	private void addMovInstructions() {

		addInstruction(INSTR_IFLESS, new IFLessInstruction(mundo));
		addInstruction(INSTR_IFDIF, new IFDifInstruction(mundo));
		addInstruction(INSTR_NAND, new NandInstruction(mundo));
		addInstruction(INSTR_AND, new AndInstruction(mundo));
		addInstruction(INSTR_NOR, new NorInstruction(mundo));
		addInstruction(INSTR_XOR, new XorInstruction(mundo));

		addInstruction(INSTR_SUB, new SubInstruction(mundo));
		addInstruction(INSTR_ADD, new AddInstruction(mundo));
		addInstruction(INSTR_MOV, new MovInstruction(mundo));

		addInstruction(INSTR_MOVI, new MovToRegisterInstruction(mundo));
		addInstruction(INSTR_LOADI, new LoadFromRegisterInstruction(mundo));
		addInstruction(INSTR_STAX, new SaveToRegisterPositionInstruction(mundo));

		addInstruction(INSTR_SWAP, new SwapInstruction(mundo));
	}

	protected void addInstruction(String name, Instruction inst) {
		inst.setName(name);
		int id = ++instructionCount;
		inst.setId(id);
		mundo.addInstruction(id, name, inst);

		// Log.debug("Adding instruction:" + inst);

	}

	public static String getLetter(int i) {
		return Character.toString((char) (65 + i));
	}

}
