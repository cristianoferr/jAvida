using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class SubInstruction : BaseInstruction
    {
        public SubInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int v = o.getReg(getByteOrganismo(o, 1)) - o.getReg(getByteOrganismo(o, 2));
            o.setReg(getByteOrganismo(o, 3), v);
        }

    public override int getStep()
        {
            return 4;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 3)), ALifeConsts.getLetter(o.getMemory(ip + 3)) + " <- " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "-" + ALifeConsts.getLetter(o.getMemory(ip + 2)));
        }
    }
}
