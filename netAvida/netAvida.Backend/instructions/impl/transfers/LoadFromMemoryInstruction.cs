using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class LoadFromMemoryInstruction : BaseInstruction
    {
        public LoadFromMemoryInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int inst = getByteOrganismo(o, 2);
            inst = o.getMemory(inst);
            inst = mundo.getMutation().mutateInstruction(inst, o);
            /*if (!mundo.isValidInstruction(inst)){
                o.fatalError();
                return;
            }*/
            o.setReg(getByteOrganismo(o, 1), inst);
        }

    public override int getStep()
        {
            return 3;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + o.getMemory(ip + 2),
                ALifeConsts.getLetter(o.getMemory(ip + 1)) + " <- (" + o.getMemory(ip + 2) + ")");
        }
    }
}
