using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class CallInstruction : BaseInstruction
    {
        bool fwd = true;
        bool bwd = true;
        public CallInstruction(IWorld mundo): base(mundo)
        {
        }

        public override bool requiresTemplate()
        {
            return true;
        }

        public override void executa(IOrganismo o)
        {
            o.fillTemplate(getStep());
            if (o.sizeBuffer == 0)
            {
                o.criticalError();
                return;
            }
            int posF = -1;
            int posB = -1;
            if (fwd && bwd)
            {
                posF = o.searchTemplateFwd();
                posB = o.searchTemplateBwd();
                if (posB < 0)
                {
                    returnPos(o, posF);
                    return;
                }
                if (posF < 0)
                {
                    returnPos(o, posB);
                    return;
                }
                int difF = posF - o.ip;
                int difB = o.ip - posB;
                returnPos(o, difF < difB ? posF : posB);

            }
            else
            {
                if (fwd)
                {
                    posF = o.searchTemplateFwd();
                    returnPos(o, posF);
                }
                if (bwd)
                {
                    posB = o.searchTemplateBwd();
                    returnPos(o, posB);
                }
            }
        }

        private void returnPos(IOrganismo o, int pos)
        {
            if (pos > 0)
            {
                o.push(o.ip + 1);
                o.ip=pos - 1;
            }
            else
            {
                o.error();
            }
        }

        public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name, "push(ip) ; ip <- templatePosition");
        }
    }
}
