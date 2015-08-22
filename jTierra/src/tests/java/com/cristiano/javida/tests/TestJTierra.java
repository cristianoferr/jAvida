package com.cristiano.javida.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.TierraConsts;
import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.instructions.arithmetic.AddInstruction;
import com.cristiano.alife.instructions.arithmetic.IncInstruction;
import com.cristiano.alife.instructions.arithmetic.ShiftLeftInstruction;
import com.cristiano.alife.instructions.arithmetic.SubInstruction;
import com.cristiano.alife.instructions.arithmetic.ZeroInstruction;
import com.cristiano.alife.instructions.branch.AdrInstruction;
import com.cristiano.alife.instructions.branch.CallInstruction;
import com.cristiano.alife.instructions.branch.JumpInstruction;
import com.cristiano.alife.instructions.branch.RetInstruction;
import com.cristiano.alife.instructions.logic.IFDifInstruction;
import com.cristiano.alife.instructions.logic.IFLessInstruction;
import com.cristiano.alife.instructions.logic.IFZeroInstruction;
import com.cristiano.alife.instructions.logic.OrInstruction;
import com.cristiano.alife.instructions.misc.NopInstruction;
import com.cristiano.alife.instructions.stack.ChangeStackInstruction;
import com.cristiano.alife.instructions.stack.PopInstruction;
import com.cristiano.alife.instructions.stack.PushInstruction;
import com.cristiano.alife.instructions.transfers.MovInstruction;
import com.cristiano.alife.instructions.transfers.MovToRegisterInstruction;
import com.cristiano.alife.instructions.transfers.SwapInstruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.Pilha;
import com.cristiano.javida.CPU;
import com.cristiano.javida.MundoTierra;
import com.cristiano.utils.Log;

public class TestJTierra {

	@Test
	public void testLoading() {
		MundoTierra m=new MundoTierra(100000);
		IOrganismo o = m.criaOrganismo("ancestor_test.txt");
		assertNotNull(o);
		
		validaInstrucao(o,NopInstruction.class);
		validaInstrucao(o,NopInstruction.class);
		validaInstrucao(o,NopInstruction.class);
		validaInstrucao(o,NopInstruction.class);
		validaInstrucao(o,ZeroInstruction.class);
		validaInstrucao(o,OrInstruction.class);
		
	}

	private void validaInstrucao(IOrganismo o, Class class1) {
		Instruction i=o.getInstruction();
		assertNotNull(i);
		assertTrue(i.getClass().getSimpleName()+"<>"+class1.getSimpleName(),i.getClass().equals(class1));
		o.next(1);
	}
	
	@Test
	public void testMisc() {
		assertTrue(ALifeConsts.calcIndex(10, 100)==10);
		assertTrue(ALifeConsts.calcIndex(110, 100)==10);
		assertTrue("Res:"+ALifeConsts.calcIndex(-10, 100),ALifeConsts.calcIndex(-10, 100)==90);
	}
	
	@Test
	public void testCPU() {
		MundoTierra m=new MundoTierra(100000);
		
		CPU cpu=m.cpu;
		assertTrue(cpu.getMemoryUsePerc()==0);
		
		IOrganismo o = m.criaOrganismo(10000);
		assertTrue(m.getOrganismos().contains(o));
		m.criaOrganismo(10000);
		m.criaOrganismo(10000);
		m.criaOrganismo(10000);
		assertTrue("Memory in use:"+cpu.getMemoryUsePerc(),cpu.getMemoryUsePerc()==40f);
		assertNotNull(o);
		int sp=o.sp();
		
		int count=0;
		for (int i=0;i<cpu.memorySize;i++){
			if (cpu.getMemoryOwner(i)>=0){
				count++;
			}
		}
		assertTrue(count==40000);
		
		assertTrue(cpu.getMemoryOwner(o.sp()+1)==o.id());
		assertTrue(cpu.getMemoryOwner(o.sp())==o.id());
		assertFalse(cpu.getMemoryOwner(o.sp()-1)==o.id());
		
		assertFalse(cpu.isMemoryFree(sp, 100));
		assertTrue(cpu.isMemoryFree(sp-100, 10));
		
		
		cpu.setMemory(100,10);
		int v=cpu.getMemory(100);
		assertTrue(v==10);
		
		v=o.getMemory(-1);
		o.setMemory(0,101);
		o.setMemory(-1,102);
		assertTrue(cpu.getMemory(o.sp())==101);
		assertTrue(cpu.getMemory(o.sp()-1)==v);
		
		
		
	}
	
	@Test
	public void testInstructionBuilder() {
		MundoTierra m=new MundoTierra(5000);
		assertNotNull(m.getInstructions());
		
		assertTrue(ALifeConsts.getLetter(0).equals("A"));
		assertTrue(ALifeConsts.getLetter(1).equals("B"));
		
		assertTrue(m.getInstructions().size()>0);
		
		
		IOrganismo o=m.criaOrganismo(150);
		assertTrue(m.getOrganismos().contains(o));
		Instruction mov=new MovInstruction(m);
		o.setReg(0, 05);
		o.setReg(1, 10);
		o.setReg(2, 20);
		o.setReg(3, 30);
		mov.executa(o);
		assertTrue(o.getReg(2)==10);
		assertTrue(o.getReg(1)==10);
		
		Instruction chg=new ChangeStackInstruction(m);
		assertTrue(o.getCurrStack()==0);
		chg.executa(o);
		assertTrue("Stack:"+o.getCurrStack(),o.getCurrStack()==1);
		
		Log.debug("Instructions:"+m.getInstructions().size());
		
		
		Instruction i=new PushInstruction(m);
		o.setReg(1,123);
		i.executa(o);
		int v=o.pop();
		assertTrue(v==123);
		
		i=new OrInstruction(m);
		o.setReg(1,10);
		i.executa(o);
		v=o.getReg(1);
		assertTrue("Or:"+v,v==11);
		o.setReg(1,0);
		i.executa(o);
		v=o.getReg(1);
		assertTrue("Or:"+v,v==1);
		
		i=new PopInstruction(m);
		o.push(442);
		i.executa(o);
		assertTrue(o.getReg(1)==442);
		
		i=new SwapInstruction(m);
		o.setReg(1, 10);
		o.setReg(2, 20);
		i.executa(o);
		assertTrue(o.getReg(1)==20);
		assertTrue(o.getReg(2)==10);
		
		i=new AddInstruction(m);
		o.setReg(0, 30);
		o.setReg(1, 50);
		o.setReg(2, 40);
		i.executa(o);
		assertTrue(o.getReg(0)==30);
		assertTrue(o.getReg(1)==90);
		assertTrue(o.getReg(2)==40);
		
		i=new SubInstruction(m);
		o.setReg(0, 30);
		o.setReg(1, 50);
		o.setReg(2, 40);
		i.executa(o);
		assertTrue(o.getReg(0)==30);
		assertTrue(o.getReg(1)==10);
		assertTrue(o.getReg(2)==40);
		
		i=new MovToRegisterInstruction(m);
		o.setReg(0, 30);
		o.setReg(1, 50);
		o.setReg(2, 40);
		o.setMemory(50, 123);
		i.executa(o);
		assertTrue(o.getMemory(40)==123);
		
		i=new ShiftLeftInstruction(m);
		o.setReg(1, 2);
		i.executa(o);
		assertTrue("val:"+o.getReg(1),o.getReg(1)==4);
		
		int ip=o.ip();
		i=new IFZeroInstruction(m);
		o.setReg(1, 0);
		i.executa(o);
		assertTrue(o.ip()==ip);
		o.setReg(1, 1);
		i.executa(o);
		assertTrue(o.ip()==ip+1);
		
		ip=o.ip();
		i=new IFLessInstruction(m);
		o.setReg(1, 0);
		o.setReg(2, 1);
		i.executa(o);
		assertTrue(o.ip()==ip);
		o.setReg(1, 2);
		o.setReg(2, 2);
		i.executa(o);
		assertTrue(o.ip()==ip);
		o.setReg(1, 3);
		i.executa(o);
		assertTrue(o.ip()==ip+1);
		
		
		ip=o.ip();
		i=new IFDifInstruction(m);
		o.setReg(1, 0);
		o.setReg(2, 1);
		i.executa(o);
		assertTrue(o.ip()==ip);
		o.setReg(2, 0);
		i.executa(o);
		assertTrue(o.ip()==ip+1);
		
		i=m.getInstruction("NOP_1");
		assertNotNull(i);
		assertTrue(i instanceof NopInstruction);
		
		i=m.getInstruction("NOP_0");
		assertNotNull(i);
		assertTrue(i instanceof NopInstruction);
		
		i=m.getInstruction("INC_A");
		assertNotNull(i);
		assertTrue(i instanceof IncInstruction);
		
	}
	
	@Test
	public void testTemplateFinding() {
		MundoTierra m=new MundoTierra(50000);
		m.criaOrganismo(50);
		
		//template find
		IOrganismo o=m.criaOrganismo(50);
		assertTrue("IP:"+o.ip(),o.ip()==o.sp());
		assertTrue(o.ip()==o.sp());
		
		o.setMemory(o.sp()+0, 10);
		o.setMemory(o.sp()+1, 11);
		o.setMemory(o.sp()+2, 12);
		o.setMemory(o.sp()+3, 13);
		o.setMemory(o.sp()+4, 14);
		o.setMemory(o.sp()+5, 15);
		o.setIp(o.sp()+5);
		
		//template
		o.setMemory(o.sp()+6, 0);
		o.setMemory(o.sp()+7, 0);
		o.setMemory(o.sp()+8, 1);
		o.setMemory(o.sp()+9, 1);
		o.setMemory(o.sp()+10, 0);
		o.setMemory(o.sp()+11, 0);
		
		o.setMemory(o.sp()+12, 10);//resultado bwd
		o.setMemory(13, 10);//inicio bwd
		
		//fwd template
		o.setMemory(o.sp()+14, 1);
		o.setMemory(o.sp()+15, 1);
		o.setMemory(o.sp()+16, 0);
		o.setMemory(o.sp()+17, 0);
		o.setMemory(o.sp()+18, 1);
		o.setMemory(o.sp()+19, 1);
		
		o.setMemory(o.sp()+20, 10);//resultado fwd
		o.fillTemplate(1);	
		assertTrue("SizeBuffer:"+o.sizeBuffer(),o.sizeBuffer()==6);
		assertTrue(o.getBuffer()[0]==1);
		assertTrue(o.getBuffer()[1]==1);
		assertTrue(o.getBuffer()[2]==0);
		assertTrue(o.getBuffer()[3]==0);
		assertTrue(o.getBuffer()[4]==1);
		assertTrue(o.getBuffer()[5]==1);
		
		int posFound=o.searchTemplateFwd();
		assertTrue("Posfound fwd:"+posFound,posFound>0);
		assertTrue(posFound==o.sp()+20);
		
		Instruction i=new AdrInstruction(m,true,true);
		
		i.executa(o);
		assertTrue("Address:"+o.getReg(0),o.getReg(0)==o.sp()+20);
		
		//bwd
		o.setIp(o.sp()+13);
		o.fillTemplate(1);
		posFound=o.searchTemplateBwd();
		assertTrue("Posfound bwd:"+posFound,posFound>=0);
		assertTrue("Posfound bwd:"+posFound,posFound==o.sp()+12);
		
		//Call
		i=new CallInstruction(m);
		o.setIp(o.sp()+13);
		i.executa(o);
		assertTrue("IPLocal:"+(o.ip()-o.sp()),o.ip()==o.sp()+11);
		
		i=new RetInstruction(m);
		//int v=o.pop();
		i.executa(o);
		assertTrue(o.ip()==o.sp()+13);
		
		i=new JumpInstruction(m,true,true);
		o.setIp(o.sp()+13);
		i.executa(o);
		assertTrue("IPLocal:"+(o.ip()-o.sp()),o.ip()==o.sp()+11);
		
	}
	@Test
	public void testJavida() {
		MundoTierra m=new MundoTierra(50);
		
		
		IOrganismo o=m.criaOrganismo(50);
		assertNotNull(o);
		
		o.setReg(0,100);
		assertTrue(o.getReg(0)==100);
		o.setReg(TierraConsts.REGISTRADORES,105);
		assertTrue(o.getReg(TierraConsts.REGISTRADORES)==105);
		o.setReg(TierraConsts.REGISTRADORES+1,110);
		assertTrue(o.getReg(TierraConsts.REGISTRADORES+1)==110);
		assertTrue(o.getReg(1)==110);
		
		o.push(0,10);
		int v=o.pop(0);
		assertTrue(v==10);
		o.push(1,100);
		v=o.pop(1);
		assertTrue(v==100);
		o.push(100,1500);
		v=o.pop(100);
		assertTrue(v==1500);
		
		o.setReg(0, 0);
		assertTrue(o.getReg(0)==0);
		o.incReg(0);
		assertTrue(o.getReg(0)==1);
		o.incReg(0);
		assertTrue(o.getReg(0)==2);
		o.decReg(0);
		assertTrue(o.getReg(0)==1);
		
		
		
	}
	@Test
	public void testPilha() {
		Pilha p=new Pilha(null);
		p.push(10);
		int v=p.pop();
		assertTrue(v==10);
		
		for (int i=0;i<TierraConsts.MAX_STACK;i++){
			p.push(i*10);
		}
		for (int i=TierraConsts.MAX_STACK-1;i>=0;i--){
			v=p.pop();
			assertTrue(v==i*10);
		}
	}
}
