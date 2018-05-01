using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend.interfaces
{
    public interface IOrganismo
    {

        int getReg(int from);

        int getMemory(int reg);

        bool hasStarted{ get; set; }


    void addFitness(float f);

        bool setMemory(int regVal, int inst);

        void fillTemplate(int sp);

        int sizeBuffer { get; set; }

        void error();

        String dump();

        int somaInstructions();

        int searchTemplateFwd();

        int searchTemplateBwd();

        void setReg(int reg, int i);

        int ip { get; set; }

        IOrganismo child { get; }

        void clearChild();

        void addFitness();

        void addChild();

        void fatalError();

        void push(int i);


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

        int id { get; set; }

        int[] getBuffer();

        void kill();

        bool validate(IOrganismo container);

        float getError();

        void checkTick();

        void run();

        IOrganismo parent { get; }

        bool isAlive();

        void save();

        int childCount { get; }

        Instruction getInstructionAt(int i);

        bool setMemory(int index, int v, bool punish);

        void clearParent();

        int getGeneration();

        void setParent(IOrganismo parent);

        void reset(int memSize, int sp);

        bool validate();

        void setStartPoint(int i);

        int age { get; }

        int oid { get; }

        String getCode();


    }

}
