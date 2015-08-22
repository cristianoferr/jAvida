package com.cristiano.alife.viewer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.cristiano.alife.ALifeIO;
import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.IWorld;
import com.cristiano.alife.world.WorldSettings;

public class ConfigPanel extends JPanel {


	
	private IWorld mundo;
	private File[] listFiles;
	private String fileNameToLoad;
	JComboBox fileList = null;

	public ConfigPanel(JFrame frmEditor) {
		// super(frmEditor);

		// initSwing();
	}

	public void initSwing(final IWorld mundo) {
		this.mundo = mundo;
		super.setLayout(new FlowLayout());
		setLayout(new FlowLayout());

		adicionaCampo("Division Mutation Chance:", 100, WorldSettings.DIV_MUT_CHANCE);
		adicionaCampo("Random Mutation:", 100, WorldSettings.RANDOM_MUTATION_CHANCE);
		adicionaCampo("Error Kill Chance:", 100, WorldSettings.ERROR_KILL_CHANCE);
		adicionaCampo("Write Mutation Chance:", 100, WorldSettings.WRITE_MUTATION_CHANCE);
		adicionaCampo("Occupation Ratio:", 100, WorldSettings.OCUPPATION_RATIO);

		add(new JSeparator(SwingConstants.HORIZONTAL));
		//initLoadAProgram(mundo);

		
		
		//add(toolPane);

		/**/

		// setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// pack();
		setVisible(true);

	}

	


	private void initLoadAProgram(final IWorld mundo) {
		add(new JLabel("Load a Program:"));
		fileList = new JComboBox();
		reload(mundo);
		add(fileList);
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
		add(btnReload);
		btnReload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reload(mundo);
			}

		});
	}

	private void reload(IWorld mundo) {
		listFiles = ALifeIO.listFiles(ALifeConsts.GENEBANK_PATH, mundo);
		fileList.removeAllItems();
		for (int i = 0; i < listFiles.length; i++) {
			fileList.addItem(listFiles[i].getName());
		}
	}

	private void adicionaCampo(String label, int width, String fieldConst) {
		// JPanel panel= createPanel(label);
		JTextField jDivMutChance = new JTextField();
		jDivMutChance.setPreferredSize(new Dimension(width, 20));
		jDivMutChance.setName(fieldConst);
		addTextEditListener(jDivMutChance);
		WorldSettings settings = mundo.settings();

		NumberFormat formatter = new DecimalFormat("###.######");
		String f = formatter.format(settings.getProperty(fieldConst));

		jDivMutChance.setText(f);
		// panel.add(jDivMutChance);
		add(new JLabel(label));
		add(jDivMutChance);
	}

	private JPanel createPanel(String label) {
		JPanel hashPane = new JPanel();
		hashPane.add(new JLabel(label));
		add(hashPane);
		return hashPane;
	}

	private void addTextEditListener(final JTextField text) {

		text.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				warn(text);
			}
		});

		text.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn(text);
			}

			public void removeUpdate(DocumentEvent e) {
				warn(text);
			}

			public void insertUpdate(DocumentEvent e) {
				warn(text);
			}

		});

	}

	public void warn(JTextField text) {

		String txt = text.getText().replace(",", ".");
		if (Float.parseFloat(txt) > 0) {
			mundo.settings().changeProperty(text.getName(), Float.parseFloat(txt));
		}
	}

	public String getFileNameToLoad() {
		fileNameToLoad = fileList.getSelectedItem().toString();
		return fileNameToLoad;
	}

	

}
