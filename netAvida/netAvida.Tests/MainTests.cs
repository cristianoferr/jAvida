using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using netAvida.backend.interfaces;
using netAvida.Tierra;
using netAvida.backend;
using System.Collections.Generic;

namespace netAvida.Tests
{
    [TestClass]
    public class MainTests
    {
        MundoTierra world;
        MockReferView mockReferView;
        CPU cpu;
        const int R_A= 0;
        const int R_B = 1;
        const int R_C = 2;
        const int R_D = 3;
        const int R_E = 4;
        const int R_F = 5;
        const int R_G = 6;
        const int R_H = 7;
        const int ORG_SIZE = 110;


        private void InitWorldTierra()
        {
            world = new MundoTierra();
            mockReferView = new MockReferView();
            world.setRefer(mockReferView);
            world.setViewer(mockReferView);
        }

        [TestMethod]
        public void TestMundoStartingTierra()
        {
            InitWorldTierra();

        }
        int startP;

        [TestMethod]
        public void TestIterativo()
        {
            startP = 1000;
            InitWorldTierra();
            cpu = world.cpu() as CPU;
            cpu.lastDeallocate = startP;
            IOrganismo org = world.criaOrganismo("ancestor_test.txt");
            Assert.IsTrue(org.getMemorySize() == ORG_SIZE, org.getMemorySize() + " <> " + ORG_SIZE);

            org.setReg(R_C, 10);
            org.setReg(R_A, 15);
            Assert.IsTrue(org.getReg(R_C) == 10);
            Assert.IsTrue(org.getReg(R_A) == 15);

            int ip = startP;

            IList<string> instrunctionsRun=new List<string>();

            //jmp depois do divide
            while (org.ip != 1050)
            {
                ip = org.ip-startP;
                PreTests(ip, org);
                Instruction inst=org.run();
                instrunctionsRun.Add(ip + " :: "+inst.getDescription(org,startP+ ip));

               PostTests(ip, org);
            }

        }
        int tmp1=0;int tmp2=0;

        private void PreTests(int ip, IOrganismo org)
        {
            switch (ip)
            {
                case 31://INC A
                    tmp1 = org.getReg(R_A);
                    break;
                case 74://DEC C
                    tmp1 = org.getReg(R_C);
                    break;
                case 87://JMPB  --volta pro inicio do loop de cópia
                    break;
            }
        }
        private void PostTests(int ip, IOrganismo org)
        {
            Pilha p = org.GetPilha();
            int sizeStack = p.Size();
            int val;
            switch (ip)
            {
                case 4://zero c
                    Assert.IsTrue(org.getReg(R_C) == 0);
                    ValidateIP(ip, org.ip,2);
                    break;
                case 6://or c
                    Assert.IsTrue(org.getReg(R_C) == 1);
                    ValidateIP(ip, org.ip, 2);
                    break;
                case 8://SHL C
                    Assert.IsTrue(org.getReg(R_C) == 2);
                    ValidateIP(ip, org.ip, 2);
                    break;
                case 10://SHL C
                    Assert.IsTrue(org.getReg(R_C) == 4);
                    ValidateIP(ip, org.ip, 2);
                    break;
                case 12://ADRB A
                    Assert.IsTrue(org.getReg(R_C) == 4);
                    ValidateIP(ip, org.ip, 2);
                    break;
                case 18://SUB A,C,A  - pega a posição inicial do programa e coloca em C
                    ValidateIP(ip, org.ip, 4);
                    Assert.IsTrue(org.getReg(R_A) == startP + 0, org.getReg(R_A) + "<>" + startP + 0);
                    break;
                case 22://MOV A,B  - B = A
                    ValidateIP(ip, org.ip, 3);
                    Assert.IsTrue(org.getReg(R_A) == org.getReg(R_B), org.getReg(R_A) + "<>" + org.getReg(R_B));
                    break;
                case 25://ADRF A
                    ValidateIP(ip, org.ip, 2);
                    Assert.IsTrue(org.getReg(R_A) == startP + 109);
                    break;
                case 31://INC A
                    ValidateIP(ip, org.ip, 2);
                    Assert.IsTrue(org.getReg(R_A) == tmp1+1);
                    break;
                case 33://SUB A,B,C
                    ValidateIP(ip, org.ip, 4);
                    Assert.IsTrue(org.getReg(R_C) == (org.getReg(R_A) - org.getReg(R_B)));
                    Assert.IsTrue(org.getReg(R_C) == ORG_SIZE, org.getReg(R_C) + " <> " + ORG_SIZE);
                    break;
                case 41://MAL C,A
                    ValidateIP(ip, org.ip, 3);
                    Assert.IsTrue(org.child!=null);
                    Assert.IsTrue(org.getReg(R_C)== ORG_SIZE, org.getReg(R_C) +" <> "+ ORG_SIZE);
                    break;
                case 44://CALL
                    Assert.IsTrue(sizeStack > 0);
                    Assert.IsTrue(p.GetAtPos(sizeStack - 1)==ip+1+startP, p.GetAtPos(sizeStack - 1) +"<>"+ ip+1);
                    Assert.IsTrue(org.ip == 61 + startP);
                    break;
                case 50://JMP
                    Assert.IsTrue(org.ip == 41 + startP, org.ip +" <> "+ (41 + startP));

                    break;
                case 61://PUSH A
                    ValidateIP(ip, org.ip, 2);
                    val = p.GetAtPos(sizeStack - 1);
                    Assert.IsTrue(val == org.getReg(R_A));
                    break;
                case 63://PUSH B
                    ValidateIP(ip, org.ip, 2);
                    val = p.GetAtPos(sizeStack - 1);
                    Assert.IsTrue(val == org.getReg(R_B));
                    break;
                case 65://PUSH C
                    ValidateIP(ip, org.ip, 2);
                    val = p.GetAtPos(sizeStack - 1);
                    Assert.IsTrue(val == org.getReg(R_C));
                    break;
                case 71://MOVI B,A
                    ValidateIP(ip, org.ip, 3);
                    var mA = cpu.getMemory(org.getReg(R_A));
                    var mB = cpu.getMemory(org.getReg(R_A));
                    Assert.IsTrue(mA == mB);
                    break;
                case 74://DEC C
                    ValidateIP(ip, org.ip, 2);
                    Assert.IsTrue(org.getReg(R_C) == tmp1-1);
                    break;
                case 76://IFZ C
                    tmp1 = org.getReg(R_C);
                    if (tmp1 == 0)
                    {
                        ValidateIP(ip, org.ip, 2);
                    } else
                    {
                        //pulou
                        ValidateIP(ip, org.ip, 3);
                    }
                    break;
                case 78://JMP
                    Assert.IsTrue(org.ip == 98 + startP, org.ip + " <> " + (98 + startP));
                    break;
                case 87://JMPB  --volta pro inicio do loop de cópia
                    Assert.IsTrue(org.ip == 71 + startP, org.ip + " <> " + (71 + startP));
                    break;

            }
        }

        private void ValidateIP(int ipBefore, int ipAfter, int expectedDif)
        {
            ipBefore -= -startP;
            Assert.IsTrue((ipAfter - ipBefore) == expectedDif, ipAfter+" - "+ ipBefore +" <> "+ expectedDif);
        }

        [TestMethod]
        public void TestOrganismoPassoAPasso()
        {
            startP = 1000;
            InitWorldTierra();
            cpu = world.cpu() as CPU;
            cpu.lastDeallocate = startP;
            IOrganismo org = world.criaOrganismo("ancestor_test.txt");
            Assert.IsTrue(org.getMemorySize() == ORG_SIZE, org.getMemorySize() +" <> "+ ORG_SIZE);

            int ipExpected = startP;

            org.setReg(R_C,10);
            org.setReg(R_A, 15);
            Assert.IsTrue(org.getReg(R_C) == 10);
            Assert.IsTrue(org.getReg(R_A) == 15);

            int tmp = 0;

            //#jAvida Size: 110

            //0=NOP_1
            org.run();
            ipExpected++;
            Assert.IsTrue(org.ip == ipExpected);

            //1=NOP_1
            org.run();
            ipExpected++;
            Assert.IsTrue(org.ip == ipExpected);

            //2=NOP_1
            org.run();
            ipExpected++;
            Assert.IsTrue(org.ip == ipExpected);

            //3=NOP_1
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //4=ZERO C
            org.run();
            ipExpected += 2;
            Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_C) == 0);

            //6=OR C
            org.run();
            ipExpected += 2;
            Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_C) == 1);

            //8=SHL C
            org.run();
            ipExpected += 2;
            Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_C) == 2);

            //10=SHL C
            org.run();
            ipExpected += 2;Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_C) == 4);

            //12=ADRB A
            org.run();
            ipExpected += 2; Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_A) == startP+4);

            //14=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //15=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //16=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //17=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //18=SUB A,C,A
            org.run();
            ipExpected += 4; Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_A) == startP+0, org.getReg(R_A) +"<>"+ startP+0);

            //22=MOV A,B
            org.run();
            ipExpected += 3; Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_A) == org.getReg(R_B), org.getReg(R_A) + "<>" + org.getReg(R_B));

            //25=ADRF A
            org.run();
            ipExpected+=2; Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_A)== startP+109);

            //27=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //28=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //29=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //30=NOP_1
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //31=INC A
            tmp = org.getReg(R_A);
            org.run();
            ipExpected+=2; Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(tmp+1== org.getReg(R_A));

            //33=SUB A,B,C
            org.run();
            ipExpected += 4; Assert.IsTrue(org.ip == ipExpected);
            Assert.IsTrue(org.getReg(R_C)==(org.getReg(R_A)- org.getReg(R_B)));

            //37=NOP_1
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //38=NOP_1
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //39=NOP_0
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //40=NOP_1
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == ipExpected);

            //41=MAL C,A
            org.run();
            ipExpected +=3; Assert.IsTrue(org.ip == ipExpected);

            //44=CALL
            org.run();
            ipExpected++; Assert.IsTrue(org.ip == startP + 61);

            //45=NOP_0

            //46=NOP_0

            //47=NOP_1

            //48=NOP_1

            //49=DIVIDE

            //50=JMP

            //51=NOP_0

            //52=NOP_0

            //53=NOP_1

            //54=NOP_0

            //55=IFZ C  

            //57=NOP_1

            //58=NOP_1

            //59=NOP_0

            //60=NOP_0

            //61=PUSH A

            //63=PUSH B

            //65=PUSH C

            //67=NOP_1

            //47=NOP_0

            //48=NOP_1

            //49=NOP_0

            //50=MOVI B,A

            //51=DEC C

            //52=IFZ C

            //53=JMP

            //54=NOP_0

            //55=NOP_1

            //56=NOP_0

            //57=NOP_0

            //58=INC A

            //59=INC B

            //60=JMPB 

            //61=NOP_0

            //62=NOP_1

            //63=NOP_0

            //64=NOP_1

            //65=IFZ C

            //66=NOP_1

            //67=NOP_0

            //68=NOP_1

            //69=NOP_1

            //70=POP C

            //71=POP B

            //72=POP A

            //73=RET

            //74=NOP_1

            //75=NOP_1

            //76=NOP_1

            //77=NOP_0

            //78=IFZ C

        }


    }
}
