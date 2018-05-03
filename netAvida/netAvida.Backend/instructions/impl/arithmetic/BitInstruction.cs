using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class BitInstruction : BaseInstruction
    {
        public BitInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int r1 = getByteOrganismo(o, 1);
            int v1 = o.getReg(r1);
            int r2 = getByteOrganismo(o, 2);
            int v2 = o.getReg(r2);
            int rPos = getByteOrganismo(o, 3);
            int vPos = o.getReg(rPos);

            int bit = ALifeConsts.getBit(v2, vPos);

            v1 = ALifeConsts.setBit(v1, vPos, bit);
            o.setReg(r1, v1);
        }

    public override int getStep()
        {
            return 4;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 3)),
                ALifeConsts.getLetter(o.getMemory(ip + 1)) + "[" + ALifeConsts.getLetter(o.getMemory(ip + 3)) + "] <- " + ALifeConsts.getLetter(o.getMemory(ip + 2)) + "[" + ALifeConsts.getLetter(o.getMemory(ip + 3)) + "]");
        }
    }
}
