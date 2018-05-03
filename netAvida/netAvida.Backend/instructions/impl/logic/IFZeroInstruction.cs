using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class IFZeroInstruction : BaseInstruction
    {
        public IFZeroInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int v = o.getReg(getByteOrganismo(o, 1));
            if (v != 0)
            {
                o.next(1);
            }
        }

    public override int getStep()
        {
            return 2;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)), "if " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + " == 0 then:");
        }
    }
}
