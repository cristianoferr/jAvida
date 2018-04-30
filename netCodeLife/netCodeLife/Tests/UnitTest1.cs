using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Backend;

namespace Tests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestInicial()
        {
            Mnemonic mneSUM = new Mnemonic("SUM");
            World world = new World();
            world.AddMnemonic(mneSUM);

            Program p = new Program(world);
            p.AddCode()
        }
    }
}
