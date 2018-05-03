using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class SaveToRegisterPositionInstruction : BaseInstruction
    {
        public SaveToRegisterPositionInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int a = o.getReg(getByteOrganismo(o, 1));
            int b = o.getReg(getByteOrganismo(o, 2));
            o.setReg(getByteOrganismo(o, 2), a);
            o.setReg(getByteOrganismo(o, 1), b);
        }

    public override int getStep()
        {
            return 3;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)), ALifeConsts.getLetter(o.getMemory(ip + 1)) + " <-> " + ALifeConsts.getLetter(o.getMemory(ip + 2)) + "");
        }
    }
}
