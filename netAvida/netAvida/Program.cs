using netAvida.backend;
using System;
using System.Windows.Forms;


namespace netAvida
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Log.Info("Inicializando...");
            Application.Run(new TierraViewer());
        }
    }
}
