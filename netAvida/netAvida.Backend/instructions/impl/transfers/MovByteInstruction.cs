﻿using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public class MovByteInstruction : BaseInstruction
    {
        public MovByteInstruction(IWorld mundo): base(mundo)
        {
        }

    public override void executa(IOrganismo o)
        {
            int inst = getByteOrganismo(o, 1);
            inst = mundo.getMutation().mutateInstruction(inst, o);
            o.setReg(getByteOrganismo(o, 2), inst);
        }

    public override int getStep()
        {
            return 3;
        }

    public override String getDescription(IOrganismo o, int ip)
        {
            return comment(name + " " + o.getMemory(ip + 1) + "," + ALifeConsts.getLetter(o.getMemory(ip + 2)), ALifeConsts.getLetter(o.getMemory(ip + 2)) + " <- " + o.getMemory(ip + 1) + "");
        }
    }
}
