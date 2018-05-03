using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class OrInstruction : BaseInstruction
    {
        public OrInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int v = o.getReg(getByteOrganismo(o, 1));
            v ^= 1;
            o.setReg(getByteOrganismo(o, 1), v);
        }

    public override int getStep()
        {
            return 2;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)), ALifeConsts.getLetter(o.getMemory(ip + 1)) + " <- or(" + ALifeConsts.getLetter(o.getMemory(ip + 1)) + ") ");
        }
    }
}
