package com.cristiano.javida.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.cristiano.alife.ALifeIO;
import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.world.OrganismoBase;
import com.cristiano.alife.world.WorldSettings;

public class TestAlife {
	
	
	@Test
	public void testBinary() {
		//		 			      76543210
		int num=Integer.parseInt("01010101",2);
		assertTrue(num==85);
		
		assertTrue(ALifeConsts.getBit(num,2)==1);
		assertTrue(ALifeConsts.getBit(num,1)==0);
		assertTrue(ALifeConsts.getBit(num,3)==0);
		assertTrue(ALifeConsts.getBit(num,4)==1);
		assertTrue(ALifeConsts.getBit(num,5)==0);
		
		int v=ALifeConsts.setBit(num,3,0);
		assertTrue(v==num);
		v=ALifeConsts.setBit(num,1,1);
		assertTrue(v==87);
		
		v=ALifeConsts.setBit(num,3,1);
		assertTrue(v==93);
	}

	
	
	
}
 