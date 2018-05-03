using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class IFDifInstruction : BaseInstruction
    {
        public IFDifInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int vf = o.getReg(getByteOrganismo(o, 1));
            int vt = o.getReg(getByteOrganismo(o, 2));
            if (vt == vf)
            {
                o.next(1);
            }
        }

    public override int getStep()
        {
            return 3;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)), "if " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + " <> " + ALifeConsts.getLetter(o.getMemory(ip + 2)) + " then:");
        }
    }
}
