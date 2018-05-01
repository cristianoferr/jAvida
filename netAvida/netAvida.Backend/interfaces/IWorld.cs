using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend.interfaces
{
    public interface IWorld
    {
        void run();
        IOrganismo criaOrganismo(String file);
        IOrganismo criaOrganismo(int memSize);

        MutationControl getMutation();
        List<IOrganismo> getOrganismos();

        bool start(IOrganismo child);

        void dealloc(IOrganismo child);

        IOrganismo alloc(int memSize, IOrganismo o);

        WorldSettings settings();

        ICPU cpu { get; set; }

        Instruction getInstruction(String line);

        int size();

        Instruction getInstruction(int memoryInstr);

        int getMemory(int ip);
        IOrganismo getAncestor();

        void addInstruction(int id, String name, Instruction inst);
        Dictionary<int, Instruction> getInstructions();
        IOrganismo killWorst(bool flagIncludePositive);
        bool isValidInstruction(int inst);

        float getMemoryUsePerc();
        void save();
        bool contains(IOrganismo org);

    }
}
