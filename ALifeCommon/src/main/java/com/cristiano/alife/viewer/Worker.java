package com.cristiano.alife.viewer;

import javax.swing.SwingWorker;

import com.cristiano.alife.world.IWorld;
import com.cristiano.utils.CRJavaUtils;
import com.cristiano.utils.Log;

public class Worker extends SwingWorker<String, Object> {

	private IWorld mundo;
	private DrawPanel drawPanel;

	public Worker(IWorld mundo, DrawPanel drawPanel) {
		this.mundo = mundo;
		this.drawPanel = drawPanel;
		drawPanel.mundo = mundo;
	}

	@Override
	protected String doInBackground() throws Exception {

		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			drawPanel.repaint();
			//mundo.run();
			//Log.debug("w2");
		}
	}

	@Override
	protected void done() {
		super.done();
	}
}
