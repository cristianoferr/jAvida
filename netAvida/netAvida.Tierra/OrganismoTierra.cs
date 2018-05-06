using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Tierra
{
    public class OrganismoTierra : Organismo, IOrganismo
    {

        private int[] regs;
        private int memorySize;
        private int startPoint;// readonly


        public OrganismoTierra(MundoTierra mundo, int memSize, int sp) : base(mundo, memSize, sp)
        {
            ip = sp;
        }


        public override int getMemory(int i)
        {
            return mundo.cpu().getMemory(i);
        }


        public override bool setMemory(int index, int v, bool punish)
        {
            if (index >= sp() && index <= sp() + memorySize)
            {
                mundo.cpu().setMemory(index, v);
                return true;
            }
            else if (child != null)
            {
                if (child.setMemory(index, v, false))
                {
                    return true;
                }
            }
            if (punish)
            {
                fatalError();
            }
            return false;
        }

        public override void setStartPoint(int i)
        {
            this.startPoint = i;
        }


        public override int sp()
        {
            return startPoint;
        }


        protected override void initRegs()
        {
            regs = new int[ALifeConsts.REGISTRADORES];
            for (int i = 0; i < ALifeConsts.REGISTRADORES; i++)
            {
                regs[i] = 0;
            }
        }


        public override void setReg(int i, int v)
        {
            regs[i % ALifeConsts.REGISTRADORES] = v;
        }



        public override int getReg(int i)
        {
            return regs[i % ALifeConsts.REGISTRADORES];
        }


        public override int getMemorySize()
        {
            return memorySize;

        }


        protected override void setMemorySize(int memSize)
        {
            this.memorySize = memSize;
        }

    }
}
