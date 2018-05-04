using System;

namespace netAvida.backend
{
    public class Log
    {

        static readonly log4net.ILog log =
            log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public static void Info(string msg)
        {
            Console.WriteLine("[INFO] " + msg);
            log.Info(msg);
        }

        public static void fatal(string msg)
        {
            Console.WriteLine("[ERROR] " + msg);
            log.Fatal(msg);

        }

        public static void error(string msg)
        {
            Console.WriteLine("[ERROR] " + msg);
            log.Error(msg);
        }

        
    }
}