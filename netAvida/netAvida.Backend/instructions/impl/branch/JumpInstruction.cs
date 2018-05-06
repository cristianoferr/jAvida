using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class JumpInstruction : BaseInstruction
    {
        bool fwd = true;
        bool bwd = true;
        public JumpInstruction(IWorld mundo, bool fwd, bool bwd) : base(mundo)
        {
            this.fwd = fwd;
            this.bwd = bwd;
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
            if (pos >= 0)
            {
                o.ip=pos - 1;
            }
            else
            {
                o.error();
            }
        }

        public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name, "ip <- template position ");
        }
    }
}
