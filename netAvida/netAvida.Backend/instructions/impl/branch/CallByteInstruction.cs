using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class CallByteInstruction : BaseInstruction
    {
        public CallByteInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int pos = getByteOrganismo(o, 1);

            o.push(o.ip + 1);
            o.ip=pos;
        }

    public override int getStep()
        {
            return 2;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)), "push(ip); ip <- " + o.getMemory(ip + 1) + ";");
        }
    }
}
