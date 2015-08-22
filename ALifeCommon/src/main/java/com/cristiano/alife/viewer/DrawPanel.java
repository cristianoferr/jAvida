package com.cristiano.alife.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.world.IWorld;
import com.cristiano.utils.Log;

public class DrawPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public IWorld mundo;
	private Image dbImage;
	
	public DrawPanel(){
		// initImage();
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       
    }

	private void initImage() {
		int width = ALifeConsts.IMAGE_WIDTH;//ALifeConsts.GRAPH_OFFSET+ALifeConsts.GRAPH_WIDTH*ALifeConsts.GRAPH_SIZE;
		int height = ALifeConsts.IMAGE_HEIGTH;//ALifeConsts.GRAPH_OFFSET+mundo.settings().memorySize/ALifeConsts.GRAPH_WIDTH*ALifeConsts.GRAPH_SIZE*2;
		dbImage = createImage (width, height);
		dbImage.getGraphics().setColor(Color.black);
        dbImage.getGraphics().drawRect(0, 0, width, height);
		dbImage.getGraphics().setColor(Color.WHITE);
        dbImage.getGraphics().fillRect(0, 0, width, height);
	}
	
	public Graphics getDrawGraphics(){
		if (dbImage==null){
			initImage();
		}
		return dbImage.getGraphics();
	}
	
	public void paint (Graphics g)
	{
		super.paint(g);
		//Log.info("paint");
		mundo.run();
		//mundo.desenha(dbImage.getGraphics());
		//g.drawImage (dbImage, 0, 0, this);
		g.drawImage (dbImage, 0, 0, this);
		
	}
}
