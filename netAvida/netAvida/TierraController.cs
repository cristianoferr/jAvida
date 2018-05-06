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
            singleton.setRefer(frmPrincipal);
            drawer.SetMundo(singleton);
            singleton.runLoop();
        }

        public void Roda()
        {
            GC.Collect();
            singleton = new MundoTierra();
            drawer.SetMundo(singleton);
            singleton.setViewer(drawer);
            singleton.setRefer(frmPrincipal);

            //frmPrincipal.ClearRows("dataGridRuns");
            Thread t = new Thread(staticRoda);
            t.Name = "BacktestRunner";
            t.Start();
            while (t.IsAlive)
            {
                Thread.Sleep(100);
                 UpdateThreadTick();
            }
        }

        static void staticRoda()
        {
            singleton.runLoop();
        }

        private void UpdateThreadTick()
        {
            Application.DoEvents();
            frmPrincipal.Update();
            drawer.Update();
        }

        private IViewLife drawer;

    }
    }