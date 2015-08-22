package com.cristiano.javida.viewer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.viewer.BaseViewer;
import com.cristiano.alife.viewer.ConfigPanel;
import com.cristiano.alife.viewer.Worker;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.javida.MundoAvida;
import com.cristiano.utils.Log;

public class AvidaViewer extends BaseViewer {

	private OrganismDialog dialog;
	private IOrganismo selectedOrg;
	int selectedOrgId = 0;
	private boolean dialogVisible;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AvidaViewer canvasApplication = new AvidaViewer();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AvidaViewer() {
		super();
		ALifeConsts.IMAGE_WIDTH = AvidaConsts.ORGS_AXIS_X * (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
		ALifeConsts.IMAGE_HEIGTH = AvidaConsts.ORGS_AXIS_Y * (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
		frmEditor.setSize(ALifeConsts.IMAGE_WIDTH + configPanel.getWidth(), ALifeConsts.IMAGE_HEIGTH + 50);
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
		mundo = new MundoAvida();
		mundo.setViewer(this);
		mundo.criaOrganismo(AvidaConsts.DEFAULT_ANCESTOR);
		configPanel.initSwing(mundo);
		reload();
	}

	@Override
	protected void click(int x, int y) {
		mundo.click(x, y);

	}

	protected void rightClick(int x, int y) {
		if (getFileNameToLoad() != null) {
			x = x / (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
			y = y / (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);

			if (currTool.equals(TOOL_LOAD)) {
				Log.info("Posicionando " + getFileNameToLoad() + " na posicao [" + x + "," + y + "]");
				((MundoAvida) mundo).criaOrganismo(ALifeConsts.GENEBANK_PATH + getFileNameToLoad(), x, y);
			}
			if (currTool.equals(TOOL_DELETE)) {
				Log.info("Removendo organismo na posicao [" + x + "," + y + "]");

				int p = 2;
				for (int i = -p; i <= p; i++) {
					for (int j = -p; j <= p; j++) {
						mundo.removeOrganismoAt(x + i, y + j);
					}
				}
			}
		}
	}

	@Override
	public void showDetails(IOrganismo o) {
		selectedOrg = o;
		if (dialog == null) {
			dialog = new OrganismDialog(frmEditor);
		}
		if (o == null) {
			dialog.setAlwaysOnTop(false);
			return;
		}
		dialog.setTitle("Program " + o.id() + "  ");
		selectedOrgId = o.id();
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);

		dialog.showOrg(o);
	}

	@Override
	public void repaint() {
		// super.repaint();
		if (dialogVisible) {
			if (selectedOrg != null) {
				if (selectedOrgId != selectedOrg.id()) {
					selectedOrg = null;
				}
			}
			dialog.showOrg(selectedOrg);
		}
	}

	@Override
	public void checkTick() {
		super.checkTick();
		dialogVisible = false;
		if (dialog != null && selectedOrg != null) {
			if (selectedOrg != null) {
				if (selectedOrgId != selectedOrg.id()) {
					dialog.setTitle(dialog.getTitle() + " Dead");
					selectedOrg = null;
					return;
				}
			}

			if (dialog.isVisible()) {
				dialog.showOrg(selectedOrg);
				boolean b = mundo.contains(selectedOrg);
				if (!b) {
					Log.error("Mundo não contem " + selectedOrg.id());
					selectedOrg = null;
				} else {
					dialogVisible = dialog.isStep();
				}
			}
		}

	}

	@Override
	public int selectedOrgId() {
		if (selectedOrg == null) {
			return 0;
		}
		return selectedOrg.id();
	}

	@Override
	public void setSelected(IOrganismo o) {
		selectedOrg = o;
		if (dialog!=null){
		dialog.showOrg(selectedOrg);
		}
	}

}
