﻿using netAvida.backend.instructions;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend
{
    public class Organismo : IOrganismo
    {
        private static int orgsCount = 0;
        private static int objCount = 0;


        private int _oid = 0;
        public int id { get; set; }

        protected IWorld mundo;
        private Pilha stacks;
        protected IOrganismo _child = null;
        protected int memoryPoolCount = 1; // Quantidade de pools, default é 1,
                                           // quando tiver filho é 2
        int currTask;
        int _age;
        public int age { get { return age; } }

        protected int[] buffer = new int[CONSTS.MAX_BUFFER];
        private float _error;

        private IOrganismo _parent;
        private int parentId = 0;
        private int colorCode = 0;

        private bool alive;
        private int _childCount;
        public int childCount { get { return _childCount; } }

        private int lastChildCount;
        private int childlessCounter = 0;
        private int generation = 0;

        private bool wroteFlag;
        protected float tickError = 0;

        int somaInsts = 0;
        protected MutationControl mutation;
        protected int childId;
        bool _wroteFlag = false;


        protected void initMemory()
        {
        }

        


        public void reset(int memSize, int sp)
        {
            id = ++orgsCount;
            childId = 0;
            colorCode = 0;
            setMemorySize(memSize);
            setStartPoint(sp);
            initBuffer();
            initStacks();
            ip = sp;
            clearChild();
            _parent = null;
            hasStarted = false;
            _error = 0;
            _age = 0;
            alive = true;
            _wroteFlag = true;
            lastChildCount = 0;
            childlessCounter = 0;
            _childCount = 0;
            somaInsts = 0;
            currTask = 0;

        }

        protected void setMemorySize(int memSize)
        {
            Log.fatal("Undefined setMemorySize");
        }

        public int getColorCode()
        {
            if (colorCode != 0)
            {
                return colorCode;
            }
            colorCode = somaInstructions() * 10;
            return colorCode;
        }

        private int difFromFather(int memorySize)
        {

            int code = somaInstructions() / memorySize;
            int parentSize = _parent.getMemorySize();
            if (parentSize < CONSTS.MIN_MEMORY_CHILD * memorySize)
            {
                parentSize = (int)(CONSTS.MIN_MEMORY_CHILD * memorySize);
            }
            int parentCode = _parent.somaInstructions() / parentSize;
            int dif = code - parentCode;
            return dif;
        }

        public void setStartPoint(int i)
        {
            Log.fatal("Undefined setMemorySize");
        }

        private void initStacks()
        {

            stacks = new Pilha(this);
        }

        protected void initRegs()
        {
            Log.fatal("Undefined initRegs");
        }

        private void initBuffer()
        {
            for (int i = 0; i < CONSTS.MAX_BUFFER; i++)
            {
                buffer[i] = 0;
            }
            sizeBuffer = 0;
        }


        public int sp()
        {
            Log.fatal("Undefined sp()");
            return 0;
        }


        public int ip
        {
            get
            {
                return getReg(CONSTS.IP_REG);
            }
            set
            {
                setReg(CONSTS.IP_REG, value);
            }
        }


        public bool setMemory(int index, int v)
        {
            return setMemory(index, v, true);
        }


        public void setReg(int i, int v)
        {
            Log.fatal("Undefined setReg");
        }


        public int getReg(int i)
        {
            Log.fatal("Undefined getReg");
            return 0;
        }


        public void push(int j)
        {
            stacks.push(j);

        }


        public int pop()
        {
            return stacks.pop();
        }

        public void decReg(int to)
        {
            setReg(to, getReg(to) - 1);
        }


        public void incReg(int to)
        {
            setReg(to, getReg(to) + 1);
        }


        public int getMemorySize()
        {
            Log.fatal("Undefined getMemorySize");
            return 0;
        }


        public bool setMemory(int index, int v, bool punish)
        {
            return false;
        }


        public Instruction getInstruction(int memoryInstr)
        {
            Instruction i = mundo.getInstruction(memoryInstr);
            return i;
        }


        public Instruction getInstruction()
        {
            int currentMemory = getCurrentMemory();
            return getInstruction(currentMemory);
        }

        protected int getCurrentMemory()
        {
            return getMemory(ip);
        }


        public void fillTemplate(int sp)
        {
            int mem = 0;
            int pos = ip + sp;
            sizeBuffer = 0;
            do
            {
                mem = getMemory(pos);
                if (mem == CONSTS.NOP0 || mem == CONSTS.NOP1)
                {
                    buffer[sizeBuffer] = (mem == CONSTS.NOP0 ? CONSTS.NOP1
                            : CONSTS.NOP0);
                    sizeBuffer++;
                }
                pos++;
            } while ((mem == CONSTS.NOP0 || mem == CONSTS.NOP1)
                    && sizeBuffer < CONSTS.MAX_BUFFER);
        }


        public int searchTemplateFwd()
        {
            if (sizeBuffer == 0)
            {
                return -1;
            }
            int indexBuffer = 0;
            for (int i = 0; i < CONSTS.TEMPLATE_LIMIT; i++)
            {
                int index = ip + i;
                int m = getMemory(index);
                if (m == buffer[indexBuffer])
                {
                    indexBuffer++;
                }
                else
                {
                    indexBuffer = 0;
                }
                if (indexBuffer == sizeBuffer)
                {// && getMemory(index + 1) > 1) {
                    return index + 1;
                }
            }
            return -1;
        }

        public string debugInfo(string prefix)
        {
            string saida = prefix + " = ";

            for (int i = 0; i < CONSTS.REGISTRADORES; i++)
            {
                saida += CONSTS.getLetter(i) + "X:"
                        + CONSTS.numberFormat(getReg(i)) + " ";
            }
            saida += "SP:" + CONSTS.numberFormat(sp()) + " IP:"
                    + CONSTS.numberFormat(ip) + " E:" + ((int)_error)
                    + " ID:" + id;
            if (_child != null)
            {
                saida += " (Child:" + _child.id + " size:"
                        + CONSTS.numberFormat(_child.getMemorySize()) + " )";
            }

            saida += "\n#" + stacks.debugInfo();
            return saida;
        }


        public override string ToString()
        {
            string ret = headerOutput();
            ret += getCode();
            return ret;
        }


        public string getCode()
        {
            return listInstructions(getMemorySize(), true);
        }

        protected string headerOutput()
        {
            string ret = "#jAvida	Id:" + id + " Gen:" + generation + " ChildCount:"
                    + childCount + " Age:" + age + "	Size: "
                    + getMemorySize() + "\n";
            ret += "#REGS: " + debugInfo("") + "\n";
            return ret;
        }

        protected string listInstructions(int limit, bool includeNoop)
        {
            string ret = "";
            int step = 1;
            int sp = this.sp();
            for (int i = sp; i < sp + limit; i += step)
            {
                step = 1;
                int currentMemory = getMemory(i);
                Instruction inst = getInstructionAt(i);

                if (inst != null)
                {
                    if (!(inst is NopInstruction) || includeNoop
                            || i < getMemorySize() * 1.2f)
                    {
                        string s = listInstruction(i, inst, currentMemory);
                        ret += s;
                    }
                    step = inst.getStep();
                }
                else
                {
                    string s = listInstruction(i, inst, currentMemory);
                    ret += s;
                }
            }
            return ret;
        }

        protected string listInstruction(int i, Instruction inst, int currentMemory)
        {
            string name = "";

            if (inst != null)
            {
                name = inst.getDescription(this, i);
            }
            else
            {
                name = "" + currentMemory;
            }
            string s = (i) + ":: [" + currentMemory + "] =" + name + "\n";
            if (ip == i)
            {
                s = ">" + s;
            }
            return s;
        }



        public int somaInstructions()
        {
            if (somaInsts > 0)
            {
                return somaInsts;
            }
            int memorySize = getMemorySize();
            int sp = this.sp();
            for (int i = 0; i < memorySize; i++)
            {
                int mem = getMemory(sp + i);
                somaInsts += mem;
            }
            return somaInsts;
        }


        public void save()
        {
            ALifeIO.saveToFile(this);
        }


        public int searchTemplateBwd()
        {
            if (sizeBuffer == 0)
            {
                return -1;
            }
            int indexBuffer = sizeBuffer;
            for (int i = 0; i < CONSTS.TEMPLATE_LIMIT; i++)
            {
                int index = ip - i;
                int m = getMemory(index);
                if (m == buffer[indexBuffer - 1])
                {
                    indexBuffer--;
                }
                else
                {
                    indexBuffer = sizeBuffer;
                }
                if (indexBuffer == 0)
                {
                    // if (getMemory(index - 1) > 1) {
                    return index + sizeBuffer;
                    // } else {
                    // indexBuffer = sizeBuffer;
                    // }
                }
            }
            return -1;
        }


        public float getError()
        {
            return tickError + _error;
        }


        public void criticalError()
        {
            criticalError(1);

        }

        protected void criticalError(int mult)
        {
            _error += (mundo.settings().errorCritical * mult);
        }


        public void fatalError()
        {
            if (!alive)
            {
                return;
            }
            _error += mundo.settings().errorCritical;
            if (_parent != null)
            {
                _parent.error();
            }
            mundo.dealloc(this);
        }


        public void error()
        {
            _error += mundo.settings().errorNormal;

        }


        public void addFitness(float f)
        {
            _error -= mundo.settings().fitnessNormal * f;

        }


        public void addFitness()
        {
            addFitness(1);
        }


        public bool validate(IOrganismo memoryContainer)
        {

            return true;

        }


        public void addChild()
        {
            _childCount++;
            if (childCount == CONSTS.AUTO_SAVE_PROGRAM_WITH_CHILD_COUNT)
            {
                if (_child != null)
                {
                    _child.save();
                }
            }

        }


        public void kill()
        {
            alive = false;
            clearChild();
            clearParent();

        }


        public bool isAlive()
        {
            return alive;
        }


        public void run()
        {
            if (isAlive())
            {
                tick();
                mutation.errorLimitAction(this);
                mutation.randomMutation(this);
            }
        }

        private void tick()
        {
            Instruction instruction = getInstruction();
            int step = 1;
            if (instruction != null)
            {
                instruction.executa(this);
                step = instruction.getStep();
            }
            else
            {
                criticalError();
            }
            next(step);
        }


        public void checkTick()
        {

            if (lastChildCount == childCount)
            {
                childlessCounter++;
                if (childlessCounter > 3)
                {
                    error();
                }
            }
            else
            {
                childlessCounter = 0;
            }

            if (_error > CONSTS.ERROR_UPPER_LIMIT)
            {
                _error = CONSTS.ERROR_UPPER_LIMIT;
            }
            if (_error < -CONSTS.ERROR_UPPER_LIMIT)
            {
                _error = -CONSTS.ERROR_UPPER_LIMIT;
            }

            float childReward = (childCount - lastChildCount)
                    * mundo.settings().childReward;
            if (childReward > 0)
            {
                addFitness(childReward);
            }

            // addFitness(getMemorySize() * mundo.settings().getSizeReward);
            lastChildCount = childCount;
            // if (getError()>AvidaConsts.ERROR_LIMIT){
            // addErrorCritical();

            // }

        }

        protected void punishSimilarity()
        {

        }


        public int getMemory(int reg)
        {
            Log.fatal("Undefined getMemory");
            return 0;
        }


        public int sizeBuffer { get; set; }


        public IOrganismo child
        {
            get
            {
                if (_child != null && _child.id != childId)
                {
                    clearChild();
                }

                return _child;
            }
        }


        public void clearChild()
        {
            _child = null;
            memoryPoolCount = 1;
        }


        public void clearParent()
        {
            if (_parent != null && _parent.child == this)
            {
                _parent.clearChild();
            }
            _parent = null;
        }


        public void next(int step)
        {
            _age++;
            this.ip = this.ip + step;
        }


        public void setChild(IOrganismo child)
        {

            this._child = child;
            this.childId = child.id;
            memoryPoolCount = (child == null ? 1 : 2);
            if (this.child == this)
            {
                Log.fatal("Erro!!!");
            }
        }

        public int[] getBuffer()
        {
            return buffer;
        }


        public IOrganismo parent
        {
            get
            {
                if (_parent != null && parentId != _parent.id)
                {
                    _parent = null;
                }
                return _parent;
            }
        }



        public Instruction getInstructionAt(int pos)
        {
            int currentMemory = getMemory(pos);
            return getInstruction(currentMemory);
        }


        public int getGeneration()
        {
            return generation;
        }


        public void setParent(IOrganismo parent)
        {
            parentId = parent.id;
            this.generation = parent.getGeneration() + 1;
            this._parent = parent;
        }


        public void setPos(int x, int y)
        {
        }


        public int getX()
        {
            return 0;
        }

        public string dump()
        {
            return ToString();
        }


        public bool validate()
        {
            return validate(this);
        }


        public int oid
        {
            get
            {
                return _oid;
            }
        }

        public bool hasStarted { get; set; }


        public IOrganismo getNeighbourAt(int index)
        {
            Log.fatal("getNeighbourAt not implemented");
            return null;
        }

        public void trasnferToIndex(int index)
        {
            Log.fatal("trasnferToIndex not implemented");
        }
    }
}