package ca.utoronto.utm.paint;

import javax.swing.*;  
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

class PaintPanel extends JPanel {
	private static final long serialVersionUID = 3277442988868869424L;
	private ArrayList<PaintCommand> commands = new ArrayList<PaintCommand>();
	
	public PaintPanel(){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
	}
	
	public void setCommands(ArrayList<PaintCommand> commands){
		this.commands=commands;
	}
	public void reset(){
		this.commands.clear();
		this.repaint();
	}
	
	public void addCommand(PaintCommand command){
		this.commands.add(command);
	}
	
	/**
	 * This function saves the command in the appropriate format unto specified file
	 * @param writer a PrintWriter which has the file that will be written unto
	 * @throws IOException
	 */
	public void save(PrintWriter writer) throws IOException{
		
		writer.write("Paint Save File Version 1.0\n");
		for(PaintCommand command: commands){
			writer.write(command.toString());
		}
		writer.write("End Paint Save File"); 
		writer.flush();
		writer.close();
		
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        Graphics2D g2d = (Graphics2D) g;		
		for(PaintCommand c: this.commands){
			c.execute(g2d);
		}
		g2d.dispose();
	}
}
