package com.cristiano.javida.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.cristiano.alife.ALifeIO;
import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.javida.MundoAvida;

public class TestJAvida {

	@Test
	public void testListFiles() {
		MundoAvida m = new MundoAvida();
		File[] listFiles = ALifeIO.listFiles(ALifeConsts.GENEBANK_PATH, m);
		assertNotNull(listFiles);


	}

	
	@Test
	public void testIO() {
		MundoAvida m = new MundoAvida();
		IOrganismo o=m.criaOrganismo(100);
		String line="#REGS:  = AX:4218[08] BX:2[02] CX:10[00] DX:50[00] EX:110[00] FX:0[00] GX:23[03] HX:20493[03] IX:47[07] JX:76[06] SP:23[03] IP:114[04] E:-1941 ID:181498";
		ALifeIO.loadRegs(line, o);
		assertTrue(o.getReg(0)==4218);
		assertTrue(o.getReg(1)==2);
		assertTrue(o.getReg(2)==10);
		assertTrue(o.getReg(3)==50);
		assertTrue(o.getReg(4)==110);
		assertTrue(o.getReg(5)==0);
		assertTrue(o.getReg(6)==23);
		assertTrue(o.getReg(7)==20493);
		assertTrue(o.sp()==23);
		assertTrue(o.ip()==114);
		
	}
	
}
