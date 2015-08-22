package com.cristiano.javida.viewer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cristiano.alife.consts.TierraConsts;
import com.cristiano.alife.viewer.BaseViewer;
import com.cristiano.alife.viewer.Worker;
import com.cristiano.javida.MundoTierra;

public class TierraViewer extends BaseViewer {
	protected MundoTierra mundo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TierraViewer canvasApplication = new TierraViewer();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TierraViewer() {
		super();
		initMundo();
		initActions();
		new Worker(mundo, drawPanel).execute();
	}

	private void initActions() {
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mundo.save();
			}

		});

		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.console(txtConsole.getText());
			}

		});

	}

	protected void initMundo() {
		mundo = new MundoTierra();
		mundo.criaOrganismo("ancestor.txt");
	}

	@Override
	protected void click(int x, int y) {
		x -= TierraConsts.GRAPH_OFFSET;
		y -= TierraConsts.GRAPH_OFFSET;
		x/=TierraConsts.GRAPH_SIZE;
		y/=TierraConsts.GRAPH_SIZE;
		int k = 5;
		for (int i = x - k; i <= x + k; i++) {
			for (int j = y - k; j <= y + k; j++) {
				randomizePosition(i, j);
			}
		}
	}

	private void randomizePosition(int x, int y) {
		int memoryPos = y * TierraConsts.GRAPH_WIDTH 
				+ x ;
		mundo.randomize(memoryPos);
	}

	
	

}
