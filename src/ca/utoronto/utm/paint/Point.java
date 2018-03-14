package ca.utoronto.utm.paint;

public class Point {
	int x, y; // Available to our package
	Point(int x, int y){
		this.x=x; this.y=y;
	}
	
	public String toString(){
		String text = new String();
		text += "(" + this.x + "," + this.y + ")";
		return text;
	}
}
