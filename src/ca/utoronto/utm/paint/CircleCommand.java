package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.PrintWriter;

public class CircleCommand implements PaintCommand {
	private Circle circle;
	public CircleCommand(Circle circle){
		this.circle=circle;
	}
	public void execute(Graphics2D g2d){
		g2d.setColor(circle.getColor());
		int x = this.circle.getCentre().x;
		int y = this.circle.getCentre().y;
		int radius = this.circle.getRadius();
		if(circle.isFill()){
			g2d.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		} else {
			g2d.drawOval(x-radius, y-radius, 2*radius, 2*radius);
		}
	}
	
	@Override
	public String toString(){
		String text = new String();
		text += "Circle\n";
		text += circle;
		text += "End Circle\n";
		return text;
	}
}
