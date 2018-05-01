using netAvida.backend.interfaces;
using netAvida.Backend.interfaces;
using System;
using System.Collections.Generic;

namespace netAvida.backend
{
    public class MundoBase : IWorld
    {
        private IList<IOrganismo> organismos = new List<IOrganismo>();
        private Dictionary<int, IOrganismo> organismosMap = new Dictionary<int, IOrganismo>();
        private IList<IOrganismo> recycleBin = new List<IOrganismo>();
        private IOrganismo ancestor;
        protected int lastSize = 0;
        protected long tick = 0;
        protected WorldSettings settings = null;

        protected int killCount;

        private Dictionary<int, Instruction> instructions = new Dictionary<int, Instruction>();
        private Dictionary<string, Instruction> instructionsNames = new Dictionary<string, Instruction>();
        IManageInstructions instructionManager = null;

        protected MutationControl mutation;
        protected IViewLife viewer;
        private float totalMemory;
        private int maxMemory;
        private int minMemory;

        public MundoBase()
        {
            initSettings();
            taskControl = new TaskControl(this);
            mutation = new MutationControl(this);
            instructionManager = initInstructions();
            Log.info("Instructions: " + instructions.size());
            settings.getInstructionCount = instructions.size();

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

        protected abstract void initSettings();

        public void addInstruction(int id, string name, Instruction inst)
        {
            instructions.put(id, inst);
            instructionsNames.put(name, inst);
        }

        @Override
    public void setViewer(IViewLife avidaViewer)
        {
            this.viewer = avidaViewer;
        }

        @Override
    public List<IOrganismo> getOrganismos()
        {
            return organismos;
        }

        protected abstract IManageInstructions initInstructions();

        @Override
    public Map<int, Instruction> getInstructions()
        {
            return instructions;
        }

        @Override
    public IOrganismo getAncestor()
        {
            return ancestor;
        }

        @Override
    public IOrganismo criaOrganismo(int memSize)
        {
            int sp = getValidStartingPoint(memSize, null);
            if (sp >= 0)
            {
                IOrganismo o = instantiateOrganismo(memSize, sp);
                // addOrganismo(memSize, sp, o);
                return o;
            }
            return null;
        }

        @Override
    public IOrganismo criaOrganismo(string fileName)
        {
            IOrganismo o = null;
            try
            {
                o = ALifeIO.loadFromFile(fileName, this);
                o.setStarted();
                start(o);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            ancestor = o;
            return o;
        }

        protected int getValidStartingPoint(int memSize, IOrganismo parent)
        {
            Log.fatal("No getValidStartingPoint defined.");
            return 0;
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
                    dealloc(o);
                }
            }
            else
            {
                Log.error("Program already present:" + o.oid());
                dealloc(o);
            }
        }

        public IOrganismo getOrganismo(int id)
        {
            return organismosMap.get(id);
        }

        protected IOrganismo instantiateOrganismo(int memSize, int sp)
        {
            Log.fatal("No instantiateOrganismo defined.");
            return null;
        }

        @Override
    public MutationControl getMutation()
        {
            return mutation;
        }

        @Override
    public boolean start(IOrganismo o)
        {
            if (!o.validate())
            {
                if (o.parent() != null)
                {
                    o.parent().criticalError();
                }
                dealloc(o);
                return false;
            }

            return true;
        }

        @Override
    public void dealloc(IOrganismo o)
        {
            if (o == null)
            {
                return;
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

        }

        private float runOrganismo(float error, boolean checkTick, IOrganismo o)
        {
            if (isRunnable(o))
            {
                o.run();

                int memorySize = o.getMemorySize();
                totalMemory += memorySize;
                if (memorySize > maxMemory)
                {
                    maxMemory = (int)memorySize;
                }
                if (memorySize < minMemory || minMemory == 0)
                {
                    minMemory = (int)memorySize;
                }

                error += o.getError();
                if (checkTick)
                {
                    o.checkTick();
                }
            }
            return error;
        }

        protected boolean isRunnable(IOrganismo o)
        {
            return o != null && o.hasStarted();
        }

        @Override
    public void run()
        {
            tick++;
            lastSize = size();
            totalMemory = 0;
            maxMemory = 0;
            minMemory = 0;
            float error = 0;
            boolean checkTick = (tick % 500 == 0);

            for (int i = lastSize - 1; i >= 0; i--)
            {
                try
                {
                    if (i < organismos.size())
                    {
                        IOrganismo o = organismos.get(i);
                        error = runOrganismo(error, checkTick, o);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            while (getMemoryUsePerc() > settings.memoryUseLimitPerc)
            {
                killWorst(true);
                // lastSize = size();
            }

            if (checkTick)
            {
                checkTick(error, totalMemory, maxMemory, minMemory);
            }

            if (viewer != null)
            {
                viewer.repaint();
            }
        }

        protected void checkTick(float error, float totalMemory, int maxMemory,
                int minMemory)
        {
            if (viewer != null)
            {
                viewer.checkTick();
            }
            /*if (tick%5==0) {
                ALifeIO.cleanUpGenebank(this);
            }*/
        }

        public IOrganismo killWorst(boolean killPositive)
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

        public int size()
        {
            return lastSize;
        }

        public void randomize(int memoryPos)
        {
            mutation.randomPosition(memoryPos);
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

        @Override
    public IOrganismo alloc(int memSize, IOrganismo o)
        {
            Log.fatal("alloc is undefined");
            return null;
        }

        @Override
    public WorldSettings settings()
        {
            return settings;
        }

        @Override
    public Instruction getInstruction(string id)
        {
            return instructionsNames.get(id);
        }

        @Override
    public Instruction getInstruction(int id)
        {
            return instructions.get(id);
        }

        @Override
    public int getMemory(int ip)
        {
            Log.fatal("getMemory is undefined");
            return 0;
        }

        @Override
    public ICPU cpu()
        {
            Log.fatal("No CPU defined");
            return null;
        }

        public boolean isValidInstruction(int inst)
        {
            return instructions.containsKey(inst);
        }

        @Override
    public boolean contains(IOrganismo org)
        {
            return organismos.contains(org);
        }

        public IViewLife getViewer()
        {
            return viewer;
        }

        @Override
    public TaskControl io()
        {
            return taskControl;
        }

        @Override
    public void markProgram(IOrganismo o, Color red)
        {

        }

        @Override
    public IOrganismo getOrganismoAt(int x, int y)
        {
            Log.fatal("getNeighbourAt not implemented");
            return null;
        }

        @Override
    public void drawLink(IOrganismo o, IOrganismo child)
        {
            Log.fatal("drawLink not implemented");

        }

        @Override
    public void drawUnLink(IOrganismo o, IOrganismo child)
        {
            Log.fatal("drawUnLink not implemented");

        }

        public void removeOrganismoAt(int x, int y)
        {

        }

        @Override
    public void transferOrganismo(IOrganismo org, int x, int y)
        {
            Log.fatal("transferOrganismo not implemented");
        }

    }


}
