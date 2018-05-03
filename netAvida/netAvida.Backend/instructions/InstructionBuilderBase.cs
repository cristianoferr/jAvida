using netAvida.backend;
using netAvida.backend.interfaces;
using netAvida.Backend.instructions.impl;
using netAvida.Backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions
{
    public class InstructionBuilderBase: IManageInstructions
    {

        public static int instructionCount = -1;

        // arithmetic
        public const string INSTR_SHL = "SHL";
        public const string INSTR_SHR = "SHR";
        public const string INSTR_BIT_TRANSFER = "BIT"; // reg1[regPos] <- reg2[regPos] (3 regs)
        public const string INSTR_SET_BIT = "SET_BIT"; // reg1[regPos] <- reg2 (3 regs)
                                                       // logic
                                                       // io
        public const string INSTR_DIVIDE = "DIVIDE";
        public const string INSTR_MAL = "MAL";
        public const string INSTR_INPUT = "INPUT";
        public const string INSTR_OUTPUT = "OUTPUT";

        //usado para conectar
        public const string INSTR_TRANSFER = "TRANSFER"; //TRANSFER regIndex: transfer the program to a empty position
        public const string INSTR_SCAN = "SCAN";  //SCAN A,B:  A <- checkNeighbour(B): B= index de 0 a 7, A recebe 1 se tiver alguém, 0 se não
        public const string INSTR_CONNECT = "CONNECT";//CONNECT A,B:  A <- sp do vizinho na posição B: B= index de 0 a 7, A recebe SP, child vira o vizinho, erroCritico se falhar
        public const string INSTR_DISCONNECT = "DISCONNECT";//DISCONNECT

        // misc
        // stack
        // branch
        public const string INSTR_JUMP_BYTE = "JUMP_BYTE";
        public const string INSTR_JUMP_REG = "JUMP_REG";
        public const string INSTR_JUMPF = "JMPF";
        public const string INSTR_JUMPB = "JMPB";
        public const string INSTR_JUMP = "JMP";
        public const string INSTR_CALL = "CALL";
        public const string INSTR_CALL_BYTE = "CALL_BYTE";
        public const string INSTR_CALL_REG = "CALL_REG";

        public const string INSTR_RET = "RET";
        public const string INSTR_ADRB = "ADRB";
        public const string INSTR_ADR = "ADR";
        public const string INSTR_ADRF = "ADRF";
        public const string INSTR_IFZ = "IFZ";
        public const string INSTR_OR = "OR";
        public const string INSTR_NOP = "NOP";
        public const string INSTR_IFLESS = "IFLESS";
        public const string INSTR_IFDIF = "IFDIF";
        public const string INSTR_ZERO = "ZERO";
        public const string INSTR_ADD = "ADD";
        public const string INSTR_SUB = "SUB";
        public const string INSTR_NOR = "NOR";
        public const string INSTR_XOR = "XOR";
        public const string INSTR_NAND = "NAND";
        public const string INSTR_AND = "AND";
        public const string INSTR_MOV = "MOV";// move reg: to <- from
        public const string INSTR_MVI = "MVI";// Move a byte from memory to a
                                              // register: to <- byte
        public const string INSTR_MOVI = "MOVI";// Move Instruction from
                                                // register position: (to)
                                                // <- (from)
        public const string INSTR_MOVM = "MOVM";// Move Instruction from
                                                // memory position (pega a
                                                // posição +1): to <- (byte)
        public const string INSTR_STAX = "STAX";// STAX:(to) <- from
        public const string INSTR_STA = "STA";// STA:(byte) <- from
        public const string INSTR_CHANGE_STACK = "CHG_ST";
        public const string INSTR_INC = "INC";
        public const string INSTR_DEC = "DEC";
        public const string INSTR_PUSH = "PUSH";
        public const string INSTR_POP = "POP";
        public const string INSTR_SWAP = "SWAP";
        public const string INSTR_LOADI = "LOADI"; // to <- (from)
        public const string INSTR_LOADM = "LOADM";// to <- (word)
        protected IWorld mundo;

        public InstructionBuilderBase(IWorld m)
        {
            build(m);
        }

        public virtual void build(IWorld m)
        {
            this.mundo = m;
            // primeiro sempre
            NopInstruction nop0 = new NopInstruction(mundo, 0);
            addInstruction(INSTR_NOP, nop0);

            addIOInstructions();
            addGoToInstructions();

            addAdrInstructions();
            addMovInstructions();

            addArithmetic();

            addNopInstructions();

        }

        private void addIOInstructions()
        {
            addInstruction(INSTR_DIVIDE, new DivideInstruction(mundo));

            //addInstruction(INSTR_INPUT, new InputInstruction(mundo));
            //addInstruction(INSTR_OUTPUT, new OutputInstruction(mundo));
        }

        private void addGoToInstructions()
        {
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

        private void addAdrInstructions()
        {
            addInstruction(INSTR_ADR, new AdrInstruction(mundo, true, true));
            addInstruction(INSTR_ADRB, new AdrInstruction(mundo, false, true));
            addInstruction(INSTR_ADRF, new AdrInstruction(mundo, true, false));
        }

        private void addNopInstructions()
        {
            NopInstruction nop0 = new NopInstruction(mundo, 0);
            addInstruction(INSTR_NOP + "_0", nop0);
            NopInstruction nop1 = new NopInstruction(mundo, 1);
            addInstruction(INSTR_NOP + "_1", nop1);

            ALifeConsts.NOP0 = nop0.getId();
            ALifeConsts.NOP1 = nop1.getId();
        }

        private void addArithmetic()
        {
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

        private void addMovInstructions()
        {

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

        protected void addInstruction(string name, Instruction inst)
        {
            inst.setName(name);
            int id = ++instructionCount;
            inst.setId(id);
            mundo.addInstruction(id, name, inst);

            // Log.debug("Adding instruction:" + inst);

        }

        public static string getLetter(int i)
        {
            return char.ToString((char)(65 + i));
        }
    }
}
