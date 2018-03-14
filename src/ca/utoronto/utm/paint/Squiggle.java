package ca.utoronto.utm.paint;

import java.util.ArrayList;

public class Squiggle extends Shape {
	private ArrayList<Point> points=new ArrayList<Point>();
	
	public Squiggle(){
		
	}
	public void add(Point p){ this.points.add(p); }
	public ArrayList<Point> getPoints(){ return this.points; }
	
	@Override
	public String toString(){
		String text = new String();
		text += super.toString();
		text += "\tpoints\n";
		for(Point point: this.getPoints()){
			text += "\t\tpoint:" + point + "\n";
		}
		text += "\tend points\n";
		return text;
	}
}