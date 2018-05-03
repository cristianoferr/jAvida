using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class MalInstruction : BaseInstruction
    {
        public MalInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int memSize = o.getReg(getByteOrganismo(o, 1));
            if (o.child != null)
            {
                mundo.dealloc(o.child);
                o.clearChild();
                o.fatalError();
                return;
            }
            memSize = ALifeConsts.validateMemorySize(o, memSize);
            if (memSize == 0)
            {
                o.fatalError();
                return;
            }
            IOrganismo child = mundo.alloc(memSize, o);
            if (child.isAlive())
            {
                o.addFitness();
                o.setChild(child);
                o.setReg(getByteOrganismo(o, 2), child.sp());
            }
            else
            {
                o.error();
                o.setReg(getByteOrganismo(o, 2), 0);
            }
        }

    public override int getStep()
        {
            return 3;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)), "Allocate " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + " Bytes; store position in " + ALifeConsts.getLetter(o.getMemory(ip + 2)));
        }
    }
}
