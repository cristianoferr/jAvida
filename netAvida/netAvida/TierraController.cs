using netAvida.Backend.interfaces;
using netAvida.interfaces;
using netAvida.Tierra;
using System;
using System.Collections.Generic;
using System.Threading;
using System.Windows.Forms;

namespace netAvida
{
    internal class TierraController
    {
        private IReferView frmPrincipal;
        static MundoTierra singleton;

        public TierraController(IReferView frmPrincipal, IViewLife drawer)
        {
            this.frmPrincipal = frmPrincipal;
            this.drawer = drawer;
        }

        public void RodaSingleThread()
        {
            GC.Collect();
            singleton = new MundoTierra();
            singleton.setViewer(drawer);
            drawer.SetMundo(singleton);
            singleton.runLoop();
        }

        public void Roda()
        {
            GC.Collect();
            singleton = new MundoTierra();
            drawer.SetMundo(singleton);
            singleton.setViewer(drawer);

            //frmPrincipal.ClearRows("dataGridRuns");
            Thread t = new Thread(staticRoda);
            t.Name = "BacktestRunner";
            t.Start();
            int runs = 0;
            while (t.IsAlive)
            {
                Thread.Sleep(100);
                runs = UpdateThreadTick(runs);
            }
        }

        static void staticRoda()
        {
            singleton.runLoop();
        }

        private int UpdateThreadTick(int runs)
        {
            Application.DoEvents();
            if (updatesToAdd.Count > 0)
            {
                int count = updatesToAdd.Count;
                for (int i = 0; i < count; i++)
                {
                    UpdatesToAdd updt = updatesToAdd[i];
                    runs++;
                    if (updt != null)
                        UpdateApplication(runs, updt.totalLoops);
                }
                updatesToAdd.Clear();
            }
            return runs;
        }

        List<UpdatesToAdd> updatesToAdd = new List<UpdatesToAdd>();
        private IViewLife drawer;

        public void UpdateApplication( int countLoops, int totalLoops)
        {
            UpdatesToAdd updt = new UpdatesToAdd();
            updt.countLoops = countLoops;
            updatesToAdd.Add(updt);

        }

        class UpdatesToAdd
        {

            public int countLoops { get; set; }

            public int totalLoops { get; set; }
        }
    }
    }