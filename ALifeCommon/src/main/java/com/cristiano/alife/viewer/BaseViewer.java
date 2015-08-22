package com.cristiano.alife.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;

import com.cristiano.alife.ALifeIO;
import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;
import com.cristiano.utils.CRMathUtils;
import com.cristiano.utils.Log;

public abstract class BaseViewer implements IViewLife {
	public static final String TOOL_LOAD = "Load";
	public static final String TOOL_DELETE = "Delete";
	protected String currTool=TOOL_LOAD;

	
	private Graphics graphics;
	protected IWorld mundo;
	protected JFrame frmEditor;
	private JPanel mainPanel;
	protected DrawPanel drawPanel;
	private int currentX;
	private int currentY;
	private int oldX;
	private int oldY;
	protected JButton btnRun;
	protected JButton btnSalvar;

	private JLabel lblOrgs;

	private JLabel lblRatio;
	protected ConfigPanel configPanel;
	private JComboBox fileList;
	private File[] listFiles;
	protected String fileNameToLoad;

	public BaseViewer() {
		initSwing();
		frmEditor.setVisible(true);
		start();
	}

	private void initSwing() {
		frmEditor = new JFrame("jAvida");
		frmEditor.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// controller.saveIfChanged();
			}
		});

		frmEditor.setBounds(100, 100, 1024, 800);
		frmEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmEditor.getContentPane().setLayout(springLayout);

		JPanel topPanel = createTopPanel(springLayout);
		JPanel configPanel = createLeftPanel(springLayout, topPanel);

		JPanel rightPanel = createRightPanel(springLayout, topPanel,
				configPanel);

		frmEditor.getContentPane().add(configPanel);
		frmEditor.getContentPane().add(topPanel);
		frmEditor.getContentPane().add(rightPanel);

	}

	private JPanel createLeftPanel(SpringLayout springLayout, JPanel topPanel) {
		configPanel = new ConfigPanel(frmEditor);
		springLayout.putConstraint(SpringLayout.NORTH, configPanel, 0,
				SpringLayout.SOUTH, topPanel);
		springLayout.putConstraint(SpringLayout.WEST, configPanel, 0,
				SpringLayout.WEST, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, configPanel, 160,
				SpringLayout.WEST, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, configPanel, 0,
				SpringLayout.SOUTH, frmEditor.getContentPane());
		return configPanel;
	}

	private JPanel createTopPanel(SpringLayout springLayout) {
		JPanel leftPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		leftPanel.setLayout(flowLayout);
		/*
		springLayout.putConstraint(SpringLayout.NORTH, leftPanel, 0,
				SpringLayout.NORTH, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, leftPanel, 0,
				SpringLayout.WEST, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, leftPanel, 0,
				SpringLayout.EAST, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, leftPanel, 30,
				SpringLayout.NORTH, frmEditor.getContentPane());*/

		//SpringLayout sl_leftPanel = new SpringLayout();
		//leftPanel.setLayout(sl_leftPanel);

		btnRun = new JButton("Run");
		/*sl_leftPanel.putConstraint(SpringLayout.NORTH, btnRun, 0,
				SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, btnRun, 0,
				SpringLayout.WEST, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.EAST, btnRun, 80,
				SpringLayout.WEST, leftPanel);*/
		leftPanel.add(btnRun);
		btnRun.setPreferredSize(new Dimension(60, 20));

		btnSalvar = new JButton("Salvar");
		/*sl_leftPanel.putConstraint(SpringLayout.NORTH, btnSalvar, 0,
				SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, btnSalvar, 0,
				SpringLayout.EAST, btnRun);
		sl_leftPanel.putConstraint(SpringLayout.EAST, btnSalvar, 80,
				SpringLayout.EAST, btnRun);*/
		leftPanel.add(btnSalvar);
		btnSalvar.setPreferredSize(new Dimension(60, 20));

		lblOrgs = new JLabel("Orgs");
		/*sl_leftPanel.putConstraint(SpringLayout.NORTH, lblOrgs, 5,
				SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, lblOrgs, 10,
				SpringLayout.EAST, btnSalvar);
		sl_leftPanel.putConstraint(SpringLayout.EAST, lblOrgs, 180,
				SpringLayout.EAST, btnSalvar);*/
		leftPanel.add(lblOrgs);
		lblOrgs.setPreferredSize(new Dimension(80, 20));

		lblRatio = new JLabel("Ratio");
		/*sl_leftPanel.putConstraint(SpringLayout.NORTH, lblRatio, 5,
				SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, lblRatio, 10,
				SpringLayout.EAST, lblOrgs);
		sl_leftPanel.putConstraint(SpringLayout.EAST, lblRatio, 160,
				SpringLayout.EAST, lblOrgs);*/
		leftPanel.add(lblRatio);
		lblRatio.setPreferredSize(new Dimension(90, 20));
		
		JPanel toolPane = initToolChooser();
		leftPanel.add(initLoadAProgram(mundo));
		leftPanel.add(toolPane);
		

		return leftPanel;
	}
	
	public String getFileNameToLoad() {
		fileNameToLoad = fileList.getSelectedItem().toString();
		return fileNameToLoad;
	}
	
	protected void reload() {
		listFiles = ALifeIO.listFiles(ALifeConsts.GENEBANK_PATH, mundo);
		fileList.removeAllItems();
		for (int i = 0; i < listFiles.length; i++) {
			fileList.addItem(listFiles[i].getName());
		}
	}
	
	private JPanel initLoadAProgram(final IWorld mundo) {
		JPanel toolPane  = new JPanel();
		toolPane.setPreferredSize(new Dimension(250, 30));
		
		toolPane.add(new JLabel("Load:"));
		fileList = new JComboBox();
		//reload(mundo);
		toolPane.add(fileList);
		// fileList.setSelectedIndex(4);
		String selectedItem = (String) fileList.getSelectedItem();
		fileList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileNameToLoad = fileList.getSelectedItem().toString();
			}

		});

		if (selectedItem != null) {
			fileNameToLoad = selectedItem.toString();
		}

		JButton btnReload = new JButton("Recarrega");
		toolPane.add(btnReload);
		btnReload.setPreferredSize(new Dimension(50, 30));
		btnReload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reload();
			}

		});
		
		return toolPane;
	}
	
	private JPanel initToolChooser() {
		JRadioButton loadButton = new JRadioButton(TOOL_LOAD);
		loadButton.setMnemonic(KeyEvent.VK_L);
		loadButton.setActionCommand(TOOL_LOAD);
		loadButton.setSelected(true);
		
		loadButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				currTool=TOOL_LOAD;
			}
			
		});

		JRadioButton loadDelete = new JRadioButton(TOOL_DELETE);
		loadDelete.setMnemonic(KeyEvent.VK_D);
		loadDelete.setActionCommand(TOOL_DELETE);
		loadDelete.setSelected(true);
		loadDelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				currTool=TOOL_DELETE;
			}
			
		});

		ButtonGroup toolGroup = new ButtonGroup();
		toolGroup.add(loadButton);
		toolGroup.add(loadDelete);
		
		
		JPanel toolPane  = new JPanel();
		toolPane.setPreferredSize(new Dimension(150, 30));
		toolPane.add(loadButton);
		toolPane.add(loadDelete);
		return toolPane;
	}
	
	

	private JPanel createRightPanel(SpringLayout springLayout,
			JPanel leftPanel, JPanel configPanel) {
		JPanel rightPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, rightPanel, 0,
				SpringLayout.EAST, configPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, rightPanel, 0,
				SpringLayout.SOUTH, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, leftPanel, 0,
				SpringLayout.EAST, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, rightPanel, 0,
				SpringLayout.EAST, frmEditor.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, rightPanel, 0,
				SpringLayout.SOUTH, leftPanel);

		SpringLayout sl_rightPanel = new SpringLayout();
		rightPanel.setLayout(sl_rightPanel);

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		sl_rightPanel.putConstraint(SpringLayout.NORTH, mainPanel, 0,
				SpringLayout.NORTH, rightPanel);
		sl_rightPanel.putConstraint(SpringLayout.SOUTH, mainPanel, 0,
				SpringLayout.SOUTH, rightPanel);
		sl_rightPanel.putConstraint(SpringLayout.EAST, mainPanel, 0,
				SpringLayout.EAST, rightPanel);
		sl_rightPanel.putConstraint(SpringLayout.WEST, mainPanel, 0,
				SpringLayout.WEST, rightPanel);
		rightPanel.add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);

		createDrawPanel();
		return rightPanel;
	}

	private void createDrawPanel() {
		SpringLayout sl_rightPanel = new SpringLayout();
		mainPanel.setLayout(sl_rightPanel);

		drawPanel = new DrawPanel();

		sl_rightPanel.putConstraint(SpringLayout.NORTH, drawPanel, 0,
				SpringLayout.NORTH, mainPanel);
		sl_rightPanel.putConstraint(SpringLayout.SOUTH, drawPanel, 0,
				SpringLayout.SOUTH, mainPanel);
		sl_rightPanel.putConstraint(SpringLayout.EAST, drawPanel, 0,
				SpringLayout.EAST, mainPanel);
		sl_rightPanel.putConstraint(SpringLayout.WEST, drawPanel, 0,
				SpringLayout.WEST, mainPanel);
		drawPanel.setBackground(new java.awt.Color(255, 255, 255));
		drawPanel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		drawPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getButton() == 1) {
					jPanel2MousePressed(evt);
				}
				if (evt.getButton() == 3) {
					rightClick(evt.getX(), evt.getY());
				}
			}

			

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				jPanel2MouseReleased(evt);
			}
		});
		drawPanel
				.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					public void mouseDragged(java.awt.event.MouseEvent evt) {
							rightClick(evt.getX(), evt.getY());
						}
				});
		mainPanel.add(drawPanel);
	}

	protected void start() {
		Log.info("Start");
	}

	protected void update() {
		drawPanel.repaint();
	}

	protected void jPanel2MouseDragged(MouseEvent evt) {
		currentX = evt.getX();
		currentY = evt.getY();
		oldX = currentX;
		oldY = currentY;
		// Log.info(currentX + " " + currentY);
		click(currentX, currentY);
	}

	protected void click(int x, int y) {

	}

	protected void jPanel2MouseReleased(MouseEvent evt) {
	}

	
	protected void rightClick(int x, int y) {
		
	}

	protected void jPanel2MousePressed(MouseEvent evt) {
		oldX = evt.getX();
		oldY = evt.getY();
		click(oldX, oldY);
	}

	@Override
	public void drawRect(Color cor, int x, int y, int width, int height) {
		initGraphics();

		graphics.setColor(cor);
		graphics.fillRect(x, y, width, height);

	}

	private void initGraphics() {
		if (graphics == null) {
			graphics = drawPanel.getDrawGraphics();
			if (graphics == null) {
				return;
			}
		}
	}

	@Override
	public void drawCircle(Color cor, int x, int y, int i, int j) {
		initGraphics();
		graphics.setColor(cor);
		graphics.fillOval(x, y, i, j);
	}

	@Override
	public void transpRect(int x, int y, int w, int h) {
		initGraphics();
		graphics.setColor(Color.white);
		graphics.drawRect(x, y, w, h);

	}

	@Override
	public void line(int xp, int yp, int i, int j) {
		graphics.setColor(Color.white);
		graphics.drawLine(xp, yp, i, j);

	}

	@Override
	public void showDetails(IOrganismo o) {
		Log.fatal("showDetails not implemented");

	}

	@Override
	public void repaint() {
		drawPanel.repaint();
	}

	@Override
	public void checkTick() {
		float perc = mundo.getMemoryUsePerc();
		perc = (float) CRMathUtils.round(perc, 2);
		lblOrgs.setText("#:" + mundo.size() + "  " + perc + "%");
		lblRatio.setText("Mut.Ratio: "
				+ CRMathUtils.round(mundo.getMutation().calcMutationChance(1,null),
						2));
	}

	@Override
	public int selectedOrgId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void drawLine(Color cor, int x1, int y1, int x2, int y2) {
		initGraphics();
		graphics.setColor(cor);
		graphics.drawLine(x1, y1, x2, y2);
		
		
	}
	
	@Override
	public void setSelected(IOrganismo o){
		
	}
}
