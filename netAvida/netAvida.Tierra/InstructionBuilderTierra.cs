using netAvida.backend.interfaces;
using netAvida.Backend.instructions;
using netAvida.Backend.instructions.impl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Tierra
{
    public class InstructionBuilderTierra: InstructionBuilderBase
    {
        public InstructionBuilderTierra(IWorld m):base(m)
        {
        }

        public override void build(IWorld m)
        {
            base.build(m);
            addInstruction(INSTR_MAL, new MalInstruction(mundo));
        }
    }
}
