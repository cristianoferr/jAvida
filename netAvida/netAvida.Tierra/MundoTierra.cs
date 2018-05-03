using netAvida.backend;
using netAvida.backend.interfaces;
using netAvida.Backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Tierra
{
    public class MundoTierra : MundoBase,IWorld
    {


        public CPU _cpu;

        public MundoTierra(int memorySize):base()
        {
            _cpu = new CPU(this, memorySize);
        }

        public MundoTierra():this(TierraConsts.MEMORY_SIZE)
        {
        }

    protected override void initSettings()
        {
            _settings = new WorldSettings("tierra.ini", TierraConsts.MAX_ORGANISMOS);
        }

    protected override IManageInstructions initInstructions()
        {
            return new InstructionBuilderTierra(this);
        }

    protected override IOrganismo instantiateOrganismo(int memSize, int sp)
        {
            IOrganismo o = getFromRecycle(memSize, sp);
            if (o == null)
            {
                o = new OrganismoTierra(this, memSize, sp);
            }
            return o;
        }

        
    protected override int getValidStartingPoint(int memSize, IOrganismo parent)
        {
            return _cpu.getValidMemory(memSize);
        }


        public void addOrganismo(int memSize, int sp, IOrganismo o)
        {
            base.addOrganismo(o);
            _cpu.allocate(sp, memSize, o);
        }



        
    public override IOrganismo alloc(int memSize, IOrganismo parent)
        {
            int sp = -1;
            while (sp < 0)
            {
                sp = _cpu.getValidMemory(memSize);
                if (sp < 0)
                {
                    killAllocatedOrphans();
                    killWorst(true);
                }
            }
            IOrganismo o = instantiateOrganismo(memSize, sp);
            _cpu.allocate(sp, memSize, o);
            allocated.Add(o);
            o.parent=parent;
            return o;
        }

        
    public void dealloc(IOrganismo o)
        {
            base.dealloc(o);
            if (o == null)
            {
                return;
            }
            _cpu.deallocate(o);
            allocated.Remove(o);
        }

        
    public override bool start(IOrganismo o)
        {
            if (!base.start(o))
            {
                return false;
            }
            allocated.Remove(o);
            _cpu.start(o);
            addOrganismo(o);
            return true;
        }

        protected override void checkTick(float error, float totalMemory, int maxMemory,
                int minMemory)
        {
            base.checkTick(error, totalMemory, maxMemory, minMemory);
            float perc = getMemoryUsePerc();
            perc = (float)Utils.Round(perc, 2);
            int avg = (int)(totalMemory / lastSize);

            error = (float)Utils.Round(error / lastSize, 2);

            Log.info(tick + ":> Orgs:" + lastSize + " Alloc:" + allocated.Count
                    + " [MEM:" + perc + "% Avg:" + avg + " Max:" + maxMemory
                    + " Min:" + minMemory + "] E:" + error + " Freed:" + killCount
                    );
            killCount = 0;
            // killFirstAllocated();

            if (lastSize <= 1)
            {
                criaOrganismo(TierraConsts.DEFAULT_ANCESTOR);
            }
        }

        public float getMemoryUsePerc()
        {
            return _cpu.getMemoryUsePerc();
        }

        
    public void run()
        {
            base.run();
        }

        private void killAllocatedOrphans()
        {
            IOrganismo choosen = null;
            int i = 0;
            while (i < allocated.Count)
            {
                choosen = allocated[i];
                if (!choosen.parent.isAlive())
                {
                    dealloc(choosen);
                    i--;
                }
                i++;
            }
        }

    /*    public void desenha(Graphics graphics)
        {
            //_cpu.graphics = graphics;
            //run();
        }
        */

        
    public override ICPU cpu()
        {
            return _cpu;
        }


        
    public override int getMemory(int pos)
        {
            return _cpu.getMemory(pos);
        }

        
    public void click(int x, int y)
        {
            x -= TierraConsts.GRAPH_OFFSET;
            y -= TierraConsts.GRAPH_OFFSET;
            x /= TierraConsts.GRAPH_SIZE;
            y /= TierraConsts.GRAPH_SIZE;
            int k = 5;
            for (int i = x - k; i <= x + k; i++)
            {
                for (int j = y - k; j <= y + k; j++)
                {
                    randomizePosition(i, j);
                }
            }

        }

        private void randomizePosition(int x, int y)
        {
            int memoryPos = y * ALifeConsts.GRAPH_WIDTH
                    + x;
            randomize(memoryPos);
        }
    }
    }
