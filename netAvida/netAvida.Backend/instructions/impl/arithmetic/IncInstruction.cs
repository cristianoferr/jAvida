using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class IncInstruction : BaseInstruction
    {
        public IncInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            o.incReg(getByteOrganismo(o, 1));
        }

    public override int getStep()
        {
            return 2;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1))
                , ALifeConsts.getLetter(o.getMemory(ip + 1)) + " <- " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + " + 1");
        }
    }
}
