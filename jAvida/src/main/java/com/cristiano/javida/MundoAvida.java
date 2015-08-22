package com.cristiano.javida;

import java.awt.Color;
import java.awt.Graphics;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.consts.AvidaConsts;
import com.cristiano.alife.consts.TierraConsts;
import com.cristiano.alife.instructions.InstructionBuilderAvida;
import com.cristiano.alife.viewer.IViewLife;
import com.cristiano.alife.world.MundoBase;
import com.cristiano.alife.world.IManageInstructions;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.TaskControl;
import com.cristiano.alife.world.WorldSettings;
import com.cristiano.javida.organismos.OrganismoAvida;
import com.cristiano.utils.CRJavaUtils;
import com.cristiano.utils.CRMathUtils;
import com.cristiano.utils.Log;

public class MundoAvida extends MundoBase {

	// private Graphics graphics;

	IOrganismo[][] grid = new IOrganismo[AvidaConsts.ORGS_AXIS_X][AvidaConsts.ORGS_AXIS_Y];
	private int yInicial;
	private int xInicial;

	public MundoAvida() {
		xInicial = AvidaConsts.ORGS_AXIS_X / 2;
		yInicial = AvidaConsts.ORGS_AXIS_Y / 2;

		for (int i = 0; i < AvidaConsts.ORGS_AXIS_X; i++) {
			for (int j = 0; j < AvidaConsts.ORGS_AXIS_Y; j++) {
				grid[i][j] = null;
			}
		}
	}

	@Override
	protected void initSettings() {
		settings = new WorldSettings("avida.ini", AvidaConsts.MAX_ORGANISMOS);
	}

	@Override
	protected IManageInstructions initInstructions() {
		return new InstructionBuilderAvida(this);
	}

	protected IOrganismo instantiateOrganismo(int memSize, int sp) {
		IOrganismo o = getFromRecycle(memSize, sp);
		if (o == null) {
			o = new OrganismoAvida(this, memSize, sp);
		}
		return o;
	}

	protected int getValidStartingPoint(int memSize, IOrganismo parent) {
		return AvidaConsts.STARTING_POINT;
	}

	@Override
	public void dealloc(IOrganismo o) {
		if (o == null) {
			return;
		}
		if (o.id() == viewer.selectedOrgId()) {
			Log.info("Selected deallocked:"
					+ CRJavaUtils.getMethodDescriptionAt(1) + "  "
					+ CRJavaUtils.getMethodDescriptionAt(2));
		}

		if (o.getX() >= 0 && o.getX() >= 0) {
			draw(o, Color.black);
			grid[o.getX()][o.getY()] = null;
		}
		super.dealloc(o);
		o.setPos(-1, -1);
	}

	@Override
	/*
	 * Called on DIVIDE
	 */
	public boolean start(IOrganismo o) {
		if (!super.start(o)) {
			return false;
		}

		// placeNearParent(o);

		if (o.isAlive()) {
			startParentPosition(o);
			// split(o);
			o.setIp(AvidaConsts.STARTING_POINT);
			draw(o, o.getColor());
		} else {
			return false;
		}
		o.setStarted();
		addOrganismo(o);
		return true;
	}

	@Override
	public IOrganismo alloc(int memSize, IOrganismo parent) {
		IOrganismo o = instantiateOrganismo(memSize,
				getValidStartingPoint(memSize, parent));

		o.setParent(parent);

		placeNearParent(o);

		return o;
	}

	private void placeNearParent(IOrganismo o) {
		if (o.parent().id() == viewer.selectedOrgId()) {
			Log.info("Selected replicated");
		}

		int xp = o.parent().getX();
		int yp = o.parent().getY();

		int p = 1;
		do {
			if (checkXAxis(o, xp, yp, p)) {
				return;
			}
			p++;
		} while (p < AvidaConsts.ALLOC_SEARCH_LIMIT);

		if (AvidaConsts.ALLOC_KILL_WORST) {

			Log.warn("No Empty Spot found... killing an existing one...");
			IOrganismo killed = killWorst(true);
			if (killed.getX() >= 0 && killed.getY() >= 0) {
				grid[killed.getX()][killed.getY()] = o;
				o.setPos(killed.getX(), killed.getY());
			}
			killed.setPos(-1, -1);
		} else {
			dealloc(o);
		}

	}

	private void startParentPosition(IOrganismo o) {
		if (o.parent() == null) {

			Log.warn("Adicionando program sem pai:" + o.id());
			grid[xInicial][yInicial] = o;
			o.setPos(xInicial, yInicial);
			draw(o, Color.red);
			return;
		}
	}

	private boolean checkXAxis(IOrganismo o, int xp, int yp, int p) {
		int mult = (Math.random() < 0.5f ? 1 : -1);

		int i = xp - p * mult;

		while (i != xp + p * mult) {
			if (checkYAxis(o, yp, p, i)) {
				return true;
			}
			i = i + mult;
		}
		return false;
	}

	private boolean checkYAxis(IOrganismo o, int yp, int p, int i) {

		int mult = (Math.random() < 0.5f ? 1 : -1);
		int j = yp - p * mult;
		while (j != yp + p * mult) {
			if (positionOnGrid(o, i, j)) {
				return true;
			}
			j = j + mult;
		}
		return false;
	}

	private boolean positionOnGrid(IOrganismo o, int i, int j) {
		i = ALifeConsts.calcIndex(i, AvidaConsts.ORGS_AXIS_X);
		j = ALifeConsts.calcIndex(j, AvidaConsts.ORGS_AXIS_Y);

		if (grid[i][j] == null) {
			grid[i][j] = o;
			o.setPos(i, j);
			draw(o, Color.darkGray);
			// line(xp, yp, i, j);
			// transpRect( xp, yp, p);
			return true;
		}
		return false;
	}

	private void draw(IOrganismo o, Color cor) {
		int x = getXView(o);
		int y = getYView(o);
		if (viewer != null) {
			viewer.drawRect(cor, x, y, AvidaConsts.GRAPH_WIDTH,
					AvidaConsts.GRAPH_WIDTH);

		}

	}

	public void addOrganismo(int memSize, int sp, IOrganismo o) {
		super.addOrganismo(o);
		draw(o, o.getColor());
	}

	protected void checkTick(float error, float totalMemory, int maxMemory,
			int minMemory) {
		super.checkTick(error, totalMemory, maxMemory, minMemory);
		float perc = getMemoryUsePerc();
		perc = (float) CRMathUtils.round(perc, 2);
		int avg = (int) (totalMemory / lastSize);

		error = (float) CRMathUtils.round(error / lastSize, 2);

		float mutRatio = mutation.calcMutationChance(1,null);
		mutRatio = (float) CRMathUtils.round(mutRatio, 2);

		Log.info(tick + ":> Orgs:" + lastSize + " [MEM:" + perc + "% Avg:"
				+ avg + " Max:" + maxMemory + " Min:" + minMemory + "] E:"
				+ error + " Freed:" + killCount + " MutationRatio:" + mutRatio);
		killCount = 0;

		/*
		 * if (lastSize == 0) { criaOrganismo(AvidaConsts.DEFAULT_ANCESTOR); }
		 */

	}

	private void removeUnused(int i, int j, IOrganismo o) {
		// Log.error("Removendo sobra: " + o.id());
		o.setPos(i, j);
		draw(o, Color.black);
		grid[i][j] = null;
		o.setPos(-1, -1);
		dealloc(o);
	}

	public float getMemoryUsePerc() {
		lastSize = 0;
		for (int i = 0; i < AvidaConsts.ORGS_AXIS_X; i++) {
			for (int j = 0; j < AvidaConsts.ORGS_AXIS_Y; j++) {
				IOrganismo o = grid[i][j];
				if (o != null) {
					lastSize++;
					if (!o.isAlive()) {
						removeUnused(i, j, o);
					}
					if (!o.hasStarted()) {
						if (o.parent() == null) {
							removeUnused(i, j, o);
						} else if (!o.parent().isAlive()) {
							removeUnused(i, j, o);
						}
					}
				}
			}
		}
		//lastSize=getOrganismos().size();
		return ((float) lastSize) / AvidaConsts.MAX_ORGANISMOS * 100;
	}

	@Override
	public void click(int x, int y) {

		/*
		 * int x = AvidaConsts.GRAPH_OFFSET + o.getX() (AvidaConsts.GRAPH_WIDTH
		 * + AvidaConsts.GRAPH_OFFSET); int y = AvidaConsts.GRAPH_OFFSET +
		 * o.getY() (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
		 */

		// x -= ALifeConsts.GRAPH_OFFSET;
		// y -= ALifeConsts.GRAPH_OFFSET;
		x = x / (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
		y = y / (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);

		IOrganismo o = grid[x][y];

		viewer.showDetails(o);

		// Log.info("Clicou em "+x+","+y+" \n"+o);

	}

	@Override
	public void markProgram(IOrganismo o, Color cor) {
		int x = getXView(o);
		x += AvidaConsts.GRAPH_WIDTH / 3;
		int y = getYView(o);
		y += AvidaConsts.GRAPH_WIDTH / 3;
		if (viewer != null) {
			viewer.drawCircle(cor, x, y,
					(int) (AvidaConsts.GRAPH_WIDTH * 0.66f),
					(int) (AvidaConsts.GRAPH_WIDTH * 0.66f));
		}
	}

	private int getYView(IOrganismo o) {
		int y = AvidaConsts.GRAPH_OFFSET + o.getY()
				* (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
		return y;
	}

	private int getXView(IOrganismo o) {
		
		int x = AvidaConsts.GRAPH_OFFSET + o.getX()
				* (AvidaConsts.GRAPH_WIDTH + AvidaConsts.GRAPH_OFFSET);
		return x;
	}

	/* Usado para posicionar o organismo na posicao desejada */
	public void criaOrganismo(String fileName, int x, int y) {
		xInicial = x;
		yInicial = y;
		if (grid[x][y] == null) {
			criaOrganismo(fileName);
		}
		if (grid[x][y] != null) {
			viewer.setSelected(grid[x][y]);
		}
		xInicial = AvidaConsts.ORGS_AXIS_X / 2;
		yInicial = AvidaConsts.ORGS_AXIS_Y / 2;
	}

	@Override
	public IOrganismo getOrganismoAt(int x, int y) {
		x = ALifeConsts.calcIndex(x, AvidaConsts.ORGS_AXIS_X);
		y = ALifeConsts.calcIndex(y, AvidaConsts.ORGS_AXIS_Y);
		IOrganismo o = grid[x][y];
		if (o == null) {
			return null;
		}
		return o;
	}

	@Override
	public void drawLink(IOrganismo o, IOrganismo child) {
		/*
		 * if (o == null || child == null) { return; } int x1 = getXView(o); x1
		 * += AvidaConsts.GRAPH_WIDTH / 2; int y1 = getYView(o); y1 +=
		 * AvidaConsts.GRAPH_WIDTH / 2;
		 * 
		 * int x2 = getXView(child); x2 += AvidaConsts.GRAPH_WIDTH / 2; int y2 =
		 * getYView(child); y2 += AvidaConsts.GRAPH_WIDTH / 2;
		 * 
		 * viewer.drawLine(Color.white, x1, y1, x2, y2);
		 */

	}

	@Override
	public void drawUnLink(IOrganismo o, IOrganismo child) {
		/*
		 * if (o == null || child == null) { return; } int x1 = getXView(o); x1
		 * += AvidaConsts.GRAPH_WIDTH / 2; int y1 = getYView(o); y1 +=
		 * AvidaConsts.GRAPH_WIDTH / 2;
		 * 
		 * int x2 = getXView(child); x1 += AvidaConsts.GRAPH_WIDTH / 2; int y2 =
		 * getYView(child); y1 += AvidaConsts.GRAPH_WIDTH / 2;
		 * viewer.drawLine(Color.black, x1, y1, x2, y2);
		 */

	}

	public void removeOrganismoAt(int x, int y) {
		x = ALifeConsts.calcIndex(x, AvidaConsts.ORGS_AXIS_X);
		y = ALifeConsts.calcIndex(y, AvidaConsts.ORGS_AXIS_Y);
		IOrganismo o = grid[x][y];
		if (o != null) {
			dealloc(o);
		}
	}

	@Override
	public void transferOrganismo(IOrganismo org, int x, int y) {
		x = ALifeConsts.calcIndex(x, AvidaConsts.ORGS_AXIS_X);
		y = ALifeConsts.calcIndex(y, AvidaConsts.ORGS_AXIS_Y);
		if (grid[x][y] == null) {
			grid[org.getX()][org.getY()] = null;
			draw(org, Color.black);
			grid[x][y] = org;
			markProgram(org, Color.gray);
			org.setPos(x, y);
			draw(org, org.getColor());
			//markProgram(org, Color.green);
		}
	}
}
