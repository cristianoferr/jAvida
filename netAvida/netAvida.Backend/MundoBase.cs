using netAvida.backend.interfaces;
using netAvida.Backend.interfaces;
using System;
using System.Collections.Generic;
using System.Drawing;

namespace netAvida.backend
{
    public abstract class MundoBase : IWorld
    {
        protected IList<IOrganismo> organismos = new List<IOrganismo>();
        private Dictionary<int, IOrganismo> organismosMap = new Dictionary<int, IOrganismo>();
        private IList<IOrganismo> recycleBin = new List<IOrganismo>();
        public IList<IOrganismo> allocated = new List<IOrganismo>();
        private IOrganismo ancestor;
        protected int lastSize = 0;
        protected long tick = 0;
        protected WorldSettings _settings = null;

        protected int killCount;

        private Dictionary<int, Instruction> _instructions = new Dictionary<int, Instruction>();
        public Dictionary<int, Instruction> instructions { get { return _instructions; } }
        private Dictionary<string, Instruction> instructionsNames = new Dictionary<string, Instruction>();
        IManageInstructions instructionManager = null;

        protected MutationControl mutation;
        protected IViewLife viewer;
        protected float totalMemory;
        protected int maxMemory;
        protected int minMemory;

        public MundoBase()
        {
            initSettings();
            mutation = new MutationControl(this);
            instructionManager = initInstructions();
            Log.info("Instructions: " + instructions.Count);
            settings.getInstructionCount = instructions.Count;

        }

        public IOrganismo getFromRecycle(int memSize, int sp)
        {
            IOrganismo o = null;
            if (recycleBin.Count > 0)
            {
                o = recycleBin[0];
                // Log.debug("Got from recycle:" + o.oid());
                recycleBin.Remove(o);
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
            instructions.Add(id, inst);
            instructionsNames.Add(name, inst);
        }


        public void setViewer(IViewLife avidaViewer)
        {
            this.viewer = avidaViewer;
        }


        public IList<IOrganismo> getOrganismos()
        {
            return organismos;
        }

        protected abstract IManageInstructions initInstructions();


        public Dictionary<int, Instruction> getInstructions()
        {
            return instructions;
        }


        public IOrganismo getAncestor()
        {
            return ancestor;
        }


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


        public IOrganismo criaOrganismo(string fileName)
        {
            IOrganismo o = null;
            try
            {
                o = ALifeIO.loadFromFile(fileName, this);
                o.setStarted();
                start(o);
            }
            catch (Exception e)
            {
                //e.printStackTrace();
                Log.fatal(e.Message);
            }
            ancestor = o;
            return o;
        }

        protected virtual int getValidStartingPoint(int memSize, IOrganismo parent)
        {
            Log.fatal("No getValidStartingPoint defined.");
            return 0;
        }

        public void addOrganismo(IOrganismo o)
        {
            if (!organismos.Contains(o))
            {
                // Log.debug("Adding program:" + o.oid());
                organismos.Add(o);
                organismosMap.Add(o.id, o);
                if (recycleBin.Contains(o))
                {
                    Log.error("Program is present on the recycle bin: " + o.oid);
                    recycleBin.Remove(o);
                    dealloc(o);
                }
            }
            else
            {
                Log.error("Program already present:" + o.oid);
                dealloc(o);
            }
        }

        public IOrganismo getOrganismo(int id)
        {
            return organismosMap[id];
        }

        protected virtual IOrganismo instantiateOrganismo(int memSize, int sp)
        {
            Log.fatal("No instantiateOrganismo defined.");
            return null;
        }


        public MutationControl getMutation()
        {
            return mutation;
        }


        public virtual bool start(IOrganismo o)
        {
            if (!o.validate())
            {
                if (o.parent != null)
                {
                    o.parent.criticalError();
                }
                dealloc(o);
                return false;
            }

            return true;
        }


        public void dealloc(IOrganismo o)
        {
            if (o == null)
            {
                return;
            }
            organismos.Remove(o);
            organismosMap.Remove(o.id);
            if (!o.isAlive())
            {
                return;
            }
            o.clearChild();
            killCount++;
            o.kill();
            if (!recycleBin.Contains(o))
            {
                recycleBin.Add(o);
            }
            else
            {
                Log.error("Program is already present on the recycle bin: "
                        + o.oid);
            }
            cpu().deallocate(o);
            allocated.Remove(o);

        }

        protected float runOrganismo(float error, bool checkTick, IOrganismo o)
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

        protected bool isRunnable(IOrganismo o)
        {
            return o != null && o.hasStarted;
        }


        public virtual void run()
        {
            tick++;

            totalMemory = 0;
            maxMemory = 0;
            minMemory = 0;

            if (viewer != null)
            {
                viewer.repaint();
            }
        }

        protected virtual void checkTick(float error, float totalMemory, int maxMemory,
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

        public IOrganismo killWorst(bool killPositive)
        {
            float maxError = 0;
            IOrganismo choosen = null;
            foreach (IOrganismo o in organismos)
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
            foreach (IOrganismo org in organismos)
            {
                if (org.childCount > 10)
                {
                    org.save();
                }
            }

        }


        public virtual IOrganismo alloc(int memSize, IOrganismo o)
        {
            Log.fatal("alloc is undefined");
            return null;
        }


        public WorldSettings settings
        {
            get
            {
                return _settings;
            }
        }

        public Instruction getInstruction(string id)
        {
            return instructionsNames[id];
        }


        public Instruction getInstruction(int id)
        {
            return instructions[id];
        }


        public virtual int getMemory(int ip)
        {
            Log.fatal("getMemory is undefined");
            return 0;
        }


        public virtual ICPU cpu()
        {
            Log.fatal("No CPU defined");
            return null;
        }

        public bool isValidInstruction(int inst)
        {
            return instructions.ContainsKey(inst);
        }


        public bool contains(IOrganismo org)
        {
            return organismos.Contains(org);
        }

        public IViewLife getViewer()
        {
            return viewer;
        }

        public void markProgram(IOrganismo o, Color red)
        {

        }


    }


}
