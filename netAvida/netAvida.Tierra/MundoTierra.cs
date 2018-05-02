using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Tierra
{
    public class MundoTierra
    {
        protected final List<IOrganismo> organismos = new ArrayList<IOrganismo>();
        private final Map<Integer, IOrganismo> organismosMap = new HashMap<Integer, IOrganismo>();
        private final List<IOrganismo> recycleBin = new ArrayList<IOrganismo>();

        public final List<IOrganismo> allocated = new ArrayList<IOrganismo>();

        public CPU cpu;

        public MundoTierra(int memorySize)
        {
            super();
            cpu = new CPU(this, memorySize);
        }

        public MundoTierra()
        {
            this(TierraConsts.MEMORY_SIZE);
        }

        
    public List<IOrganismo> getOrganismos()
        {
            return organismos;
        }

        
    public void dealloc(IOrganismo o)
        {
            if (o == null)
            {
                return;
            }
            if (o.parent() != null)
            {
                o.parent().criticalError();
            }
            organismos.remove(o);
            organismosMap.remove(o.id());
            if (!o.isAlive())
            {
                return;
            }
            o.clearChild();
            killCount++;
            o.kill();
            if (!recycleBin.contains(o))
            {
                // Log.debug("Add to recycle:" +
                // o.oid()+"  "+CRJavaUtils.getMethodDescriptionAt(3)+"  "+CRJavaUtils.getMethodDescriptionAt(4));
                recycleBin.add(o);
            }
            else
            {
                Log.error("Program is already present on the recycle bin: "
                        + o.oid());
            }

            cpu.deallocate(o);
            allocated.remove(o);
        }

        public IOrganismo getOrganismo(int id)
        {
            return organismosMap.get(id);
        }

        public void addOrganismo(IOrganismo o)
        {
            if (!organismos.contains(o))
            {
                // Log.debug("Adding program:" + o.oid());
                organismos.add(o);
                organismosMap.put(o.id(), o);
                if (recycleBin.contains(o))
                {
                    Log.error("Program is present on the recycle bin: " + o.oid());
                    recycleBin.remove(o);
                    dealloc(o);
                }
            }
            else
            {
                Log.error("Program already present:" + o.oid());
                dealloc(o);
            }
        }

        public IOrganismo getFromRecycle(int memSize, int sp)
        {
            IOrganismo o = null;
            if (recycleBin.size() > 0)
            {
                o = recycleBin.get(0);
                // Log.debug("Got from recycle:" + o.oid());
                recycleBin.remove(o);
                /*
                 * if (contains(o)) {
                 * Log.error("Recycled Item already present: "+o.oid()); return
                 * getFromRecycle(memSize, sp); }
                 */
                o.reset(memSize, sp);
            }
            return o;
        }

        
    protected void initSettings()
        {
            settings = new WorldSettings("tierra.ini", TierraConsts.MAX_ORGANISMOS);
        }

        
    protected IManageInstructions initInstructions()
        {
            return new InstructionBuilderTierra(this);
        }

        
    protected IOrganismo instantiateOrganismo(int memSize, int sp)
        {
            IOrganismo o = getFromRecycle(memSize, sp);
            if (o == null)
            {
                o = new OrganismoTierra(this, memSize, sp);
            }
            return o;
        }

        
    protected int getValidStartingPoint(int memSize, IOrganismo parent)
        {
            return cpu.getValidMemory(memSize);
        }

        
    public IOrganismo createFirstOrganism(int memSize)
        {
            IOrganismo o = super.createFirstOrganism(memSize);
            cpu.allocate(o.sp(), o.getMemorySize(), o);
            return o;
        }

        public void addOrganismo(int memSize, int sp, IOrganismo o)
        {
            cpu.allocate(sp, memSize, o);
        }

        
    public IOrganismo alloc(int memSize, IOrganismo parent)
        {
            memSize += TierraConsts.QTD_VARS;
            int sp = -1;
            while (sp < 0)
            {
                sp = cpu.getValidMemory(memSize);
                if (sp < 0)
                {
                    killAllocatedOrphans();
                    killWorst(true);
                }
            }
            IOrganismo o = instantiateOrganismo(memSize, sp);
            cpu.allocate(sp, memSize, o);
            allocated.add(o);
            o.setParent(parent);
            return o;
        }

        
    public bool start(IOrganismo o)
        {
            if (!super.start(o))
            {
                return false;
            }
            allocated.remove(o);
            cpu.start(o);
            addOrganismo(o);
            return true;
        }

        
    public float occupationRatio()
        {
            return cpu.occupationRatio();
        }

        protected void checkTick(float error, float totalMemory, int maxMemory,
                int minMemory)
        {
            super.checkTick(error, totalMemory, maxMemory, minMemory);
            float perc = occupationRatio() * 100;
            perc = (float)CRMathUtils.round(perc, 2);
            int avg = (int)(totalMemory / lastSize);

            error = (float)CRMathUtils.round(error / lastSize, 2);

            killAllocatedOrphans();
            Log.info(tick + ":> Orgs:" + lastSize + " Alloc:" + allocated.size()
                    + " [MEM:" + perc + "% Avg:" + avg + " Max:" + maxMemory
                    + " Min:" + minMemory + "] E:" + error + " Freed:" + killCount);
            killCount = 0;
            // killFirstAllocated();

            if (lastSize <= 1)
            {
                criaOrganismo(TierraConsts.DEFAULT_ANCESTOR);
            }
        }

        
    public void run()
        {
            lastSize = organismos.size();
            super.run();
            float error = 0;
            bool checkTick = (tick % 500 == 0);

            for (int i = lastSize - 1; i >= 0; i--)
            {
                if (i < organismos.size())
                {
                    try
                    {
                        IOrganismo o = organismos.get(i);
                        error = runOrganismo(error, checkTick, o);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if (checkTick)
            {
                checkTick(error, totalMemory, maxMemory, minMemory);
            }

            while (getMemoryUsePerc() > settings.memoryUseLimitPerc)
            {
                killWorst(true);
                // lastSize = size();
            }
        }

        public IOrganismo killWorst(bool killPositive)
        {
            float maxError = 0;
            IOrganismo choosen = null;
            for (IOrganismo o : organismos)
            {
                float error = o.getError();
                if (error > maxError || maxError == 0)
                {
                    maxError = error;
                    choosen = o;
                }
            }
            if ((killPositive && maxError >= 0) || (maxError < 0))
            {
                dealloc(choosen);

                return choosen;
            }
            return null;
        }

        public void save()
        {
            Log.info("Saving...");
            for (IOrganismo org : organismos)
            {
                if (org.childCount() > 10)
                {
                    org.save();
                }
            }
        }

        
    public bool contains(IOrganismo org)
        {
            return organismos.contains(org);
        }

        private void killAllocatedOrphans()
        {
            IOrganismo choosen = null;
            int i = 0;
            while (i < allocated.size())
            {
                choosen = allocated.get(i);
                if (choosen.parent() == null || !choosen.parent().isAlive())
                {
                    dealloc(choosen);
                    i--;
                }
                i++;
            }
        }

        public void desenha(Graphics graphics)
        {
            cpu.graphics = graphics;
            run();
        }

        
    public ICPU cpu()
        {
            return cpu;
        }

        
    public int getMemory(int pos)
        {
            return cpu.getMemory(pos);
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
            int memoryPos = y * ALifeConsts.GRAPH_WIDTH + x;
            randomize(memoryPos);
        }

        
    public IOrganismo alloc(int memSize, IOrganismo o, int memPos)
        {
            return alloc(memSize, o);
        }

        
    public float getMemoryUsePerc()
        {

            return occupationRatio() * 100;
        }


    }
}
