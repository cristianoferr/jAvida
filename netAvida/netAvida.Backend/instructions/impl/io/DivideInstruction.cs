using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class DivideInstruction : BaseInstruction
    {
        public DivideInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            mundo.getMutation().divisionMutation(o);
            if (o.child != null)
            {
                if (!o.child.isAlive())
                {
                    mundo.dealloc(o.child);
                    o.criticalError();
                    o.clearChild();
                    return;
                }

                //child veio de um connect...
                if (o.child.hasStarted)
                {
                    o.child.clearParent();
                    o.clearChild();
                    o.criticalError();
                    return;
                }
                o.child.parent=o;
                if (mundo.start(o.child))
                {
                    if (o.child == null)
                    {
                        o.clearChild();
                        return;
                    }
                    o.addChild();
                    o.child.clearParent();
                    o.clearChild();
                    o.addFitness();

                }
                else
                {
                    o.clearChild();
                    o.criticalError();
                }
            }
            else
            {
                o.fatalError();
            }
        }

    

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name, "divide and start new program");
        }
    }
}
