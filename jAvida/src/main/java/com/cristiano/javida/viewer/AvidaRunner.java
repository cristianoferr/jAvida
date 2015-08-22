package com.cristiano.javida.viewer;

import com.cristiano.javida.MundoAvida;

public class AvidaRunner {

	public static void main(String[] args) {
		MundoAvida m=new MundoAvida();
		m.criaOrganismo("ancestor.txt");
		while (true){
			m.run();
		}

	}

}
