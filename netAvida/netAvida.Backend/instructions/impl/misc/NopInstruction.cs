using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{

    public class NopInstruction : BaseInstruction
    {
        int to=0;
        public NopInstruction(IWorld mundo, int i) : base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
        }

        
    }
}
