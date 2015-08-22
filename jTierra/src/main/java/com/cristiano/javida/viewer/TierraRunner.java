package com.cristiano.javida.viewer;

import com.cristiano.javida.MundoTierra;

public class TierraRunner {

	public static void main(String[] args) {
		MundoTierra m=new MundoTierra();
		m.criaOrganismo("ancestor.txt");
		while (true){
			m.run();
		}

	}

}
