package com.cristiano.javida.viewer;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.IOrganismo;

public class OrganismDialog extends JDialog {

	private TextField jid;
	private TextField jhash;
	private TextField jgen;
	private TextField jChild;
	private JTextArea code;
	private TextField jErr;
	private TextField jSize;
	
	private TextField[] jReg=new TextField[ALifeConsts.REGISTRADORES];
	private TextField jSP;
	private TextField jAge;
	private TextField jIP;
	private Checkbox jStep;
	private JScrollPane codeScroll;
	private TextField jEnergy;

	public OrganismDialog(JFrame frmEditor) {
		// super(frmEditor);

		initSwing();
	}
	
	public boolean isStep(){
		return jStep.getState();
	}

	private void initSwing() {
		super.setLayout(new FlowLayout());
		getContentPane().setLayout(new FlowLayout());
		JPanel idPane = new JPanel();
		idPane.add(new JLabel("ID:"));
		getContentPane().add(idPane);
		JPanel genSP= createPanel("SP:");
		JPanel genSize= createPanel("Size:");
		JPanel genIP= createPanel("IP:");
		JPanel hashPane = createPanel("Hash:");
		JPanel genPane = createPanel("Gen:");
		JPanel genChild = createPanel("Children:");
		JPanel genErr = createPanel("Err:");
		JPanel genAge= createPanel("Age:");
		JPanel genEnergy= createPanel("Energy:");
		
		
		
		jid = new TextField();
		jid.setPreferredSize(new Dimension(100, 20));
		jhash = new TextField();
		jhash.setPreferredSize(new Dimension(120, 20));
		jgen = new TextField();
		jgen.setPreferredSize(new Dimension(50, 20));
		jChild = new TextField();
		jChild.setPreferredSize(new Dimension(50, 20));
		jErr = new TextField();
		jErr.setPreferredSize(new Dimension(100, 20));
		jSize = new TextField();
		jSize.setPreferredSize(new Dimension(50, 20));
		jSP = new TextField();
		jSP.setPreferredSize(new Dimension(50, 20));
		jAge = new TextField();
		jAge.setPreferredSize(new Dimension(150, 20));
		jIP = new TextField();
		jIP.setPreferredSize(new Dimension(100, 20));
		jEnergy = new TextField();
		jEnergy.setPreferredSize(new Dimension(100, 20));
		
		jStep=new Checkbox("Step?");
		
		code = new JTextArea(200,30);
		code.setBounds(10, 10, 200, 30);
		code.setPreferredSize(new Dimension(100, 300));
		code.setMaximumSize(new Dimension(100, 400));
		
		idPane.add(jid);
		hashPane.add(jhash);
		genPane.add(jgen);
		genChild.add(jChild);
		genErr.add(jErr);
		genSize.add(jSize);
		genSP.add(jSP);
		genAge.add(jAge);
		genIP.add(jIP);
		genEnergy.add(jEnergy);
		getContentPane().add(jStep);
		
		for (int i=0;i<ALifeConsts.REGISTRADORES;i++){
			JPanel genRegs= createPanel(ALifeConsts.getLetter(i)+"X:");
			jReg[i]= new TextField();
			jReg[i].setPreferredSize(new Dimension(50, 20));
			genRegs.add(jReg[i]);
		}
		codeScroll = new JScrollPane(code);
		code.setRows(45);
		codeScroll.setBounds(10,60,100,200);
		codeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(codeScroll);
		
		codeScroll.setViewportView(code);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	//	pack();
		setVisible(true);
		
	}

	private JPanel createPanel(String label) {
		JPanel hashPane  = new JPanel();
		hashPane.add(new JLabel(label));
		getContentPane().add(hashPane);
		return hashPane;
	}

	public void showOrg(IOrganismo o) {
		//getContentPane().removeAll();
		//initSwing();
		if (o==null){
			return;
		}
		String dead="";
		if (!o.isAlive()){
			dead="  (dead)";
		}
		jid.setText(""+o.id());
		jhash.setText(""+o.hash()+dead);
		jgen.setText(""+o.getGeneration());
		jChild.setText(""+o.childCount());
		jErr.setText(""+o.getError());
		jSize.setText(""+o.getMemorySize());
		jEnergy.setText(""+o.getEnergy());
		int posS=code.getSelectionStart();
		int posE=code.getSelectionEnd();
		code.setText(o.dump());
		code.setSelectionStart(posS);
		code.setSelectionEnd(posE);
		
		jSP.setText(""+o.sp());
		jAge.setText(""+o.age());
		jIP.setText(""+o.ip());
		
		for (int i=0;i<ALifeConsts.REGISTRADORES;i++){
			jReg[i].setText(o.getReg(i)+"");
		}
		
		code.setRows(o.getMemorySize()*3);
		//codeScroll.setViewportView(code);
		
		codeScroll.setBounds(10,codeScroll.getBounds().y,400,800);
		//repaint();
		/*
		// set the position of the window
				Point p = new Point(400, 400);
				setLocation(p.x, p.y);

				// Create a message
				JPanel messagePane = new JPanel();
				messagePane.add(new JLabel("aaa"));
				// get content pane, which is usually the
				// Container of all the dialog's components.
				getContentPane().add(messagePane);

				// Create a button
				JPanel buttonPane = new JPanel();
				JButton button = new JButton("Close me");
				buttonPane.add(button);
				// set action listener on the button
				//button.addActionListener(new MyActionListener());
				getContentPane().add(buttonPane, BorderLayout.PAGE_END);
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				pack();
				setVisible(true);*/
		
		/*
		
		super.setLayout(new FlowLayout());
		super.setSize(300, 800);
		super.setVisible(true);
		removeAll();
		JLabel jid = new JLabel("ID: " + o.id() + "");
		JLabel jhash = new JLabel("Hash: " + o.hashCode());
		
		JButton b = new JButton("Exit");
		add(jid);
		add(jhash);
		// add(code);
		add(b);
		b.setBorder(new LineBorder(Color.black));
		b.setBackground(Color.black);
		b.setForeground(Color.white);
		// b.addActionListener(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel messagePane = new JPanel();

		messagePane.add(new JLabel("aaaaa"));
		getContentPane().add(messagePane);
		
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("Close me");
		buttonPane.add(button);


		getContentPane().add(buttonPane, BorderLayout.PAGE_END);


		pack();

		setVisible(true);*/
	}

}
