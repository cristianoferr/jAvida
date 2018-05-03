using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class MovToBytePositionInstruction : BaseInstruction
    {
        public MovToBytePositionInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int inst = o.getMemory(o.ip + 1);
            inst = mundo.getMutation().mutateInstruction(inst, o);
            int regVal = o.getReg(getByteOrganismo(o, 2));
            o.setMemory(regVal, inst);
        }

    public override int getStep()
        {
            return 3;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + o.getMemory(ip + 1) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)), "(" + ALifeConsts.getLetter(o.getMemory(ip + 2)) + ")<- (" + o.getMemory(ip + 1) + ")");
        }
    }
}
