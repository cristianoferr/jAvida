using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class RetInstruction : BaseInstruction
    {
        public RetInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int pos = o.pop();
            o.ip=pos - 1;
        }


    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name, "ip <- pop()");
        }
    }
}
