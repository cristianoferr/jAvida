﻿using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class JumpRegInstruction : BaseInstruction
    {
        public JumpRegInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int pos = o.getReg(getByteOrganismo(o, 1));
            o.ip=pos;
        }

    public override int getStep()
        {
            return 2;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + ALifeConsts.getLetter(o.getMemory(ip + 1)), "ip <- " + ALifeConsts.getLetter(o.getMemory(ip + 1)));
        }
    }
}
