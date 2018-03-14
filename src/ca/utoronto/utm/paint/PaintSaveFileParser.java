package ca.utoronto.utm.paint;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about its effort
 * to parse a file. After a successful parse, an instance will have an ArrayList
 * of PaintCommand suitable for rendering. If there is an error in the parse,
 * the instance stores information about the error. For more on the format of
 * Version 1.0 of the paint save file format, see the associated documentation.
 * 
 * @author
 *
 */
public class PaintSaveFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage = ""; // error encountered during parse
	private ArrayList<PaintCommand> commands; // created as a result of the
												// parse

	/**
	 * Below are Patterns used in parsing
	 */
	private Pattern pFileStart = Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd = Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart = Pattern.compile("^Circle$");

	// this string is used in the pcolor and forms the regex that checks if the digits are between 0 - 255
	private String colorNumberCheck = "1?[0-9]{1,2}|2[0-4][0-9]|25[0-5]";
	
	private Pattern pcolor = Pattern.compile(String.format("^color:(%1$s),(%1$s),(%1$s)$", colorNumberCheck));
	
	private Pattern pfilled = Pattern.compile("^filled:(true|false)$");
	private Pattern pCircleEnd = Pattern.compile("^EndCircle$");

	private Pattern pcentre = Pattern.compile("^center:\\((\\d+),(\\d+)\\)$");
	private Pattern pradius = Pattern.compile("^radius:(\\d+)$");

	private Pattern pRectangleStart = Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd = Pattern.compile("^EndRectangle$");

	private Pattern pP1 = Pattern.compile("^p1:\\((\\d+),(\\d+)\\)$");
	private Pattern pP2 = Pattern.compile("^p2:\\((\\d+),(\\d+)\\)$");

	private Pattern pSquiggleStart = Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd = Pattern.compile("^EndSquiggle$");

	private Pattern pSquigglepoint = Pattern.compile("^points$");
	private Pattern pSquiggleEndpoint = Pattern.compile("^endpoints$");

	private Pattern pPoints = Pattern.compile("^point:\\((\\d+),(\\d+)\\)$");
	
	private Pattern pEmptyLine = Pattern.compile("^$");

	/**
	 * Store an appropriate error message in this, including lineNumber where
	 * the error occurred.
	 * 
	 * @param mesg
	 */
	private void error(String mesg) {
		this.errorMessage = "Error in line " + lineNumber + " " + mesg;
	}

	/**
	 * 
	 * @return the PaintCommands resulting from the parse
	 */
	public ArrayList<PaintCommand> getCommands() {
		return this.commands;
	}

	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * Parse the inputStream as a Paint Save File Format file. The result of the
	 * parse is stored as an ArrayList of Paint command. If the parse was not
	 * successful, this.errorMessage is appropriately set, with a useful error
	 * message.
	 * 
	 * @param inputStream
	 *            the open file to parse
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream) {
		this.commands = new ArrayList<PaintCommand>();
		this.errorMessage = "";

		// During the parse, we will be building one of the
		// following shapes. As we parse the file, we modify
		// the appropriate shape.

		Circle circle = null;  // variable pointing to circle as it is being created 
		Rectangle rectangle = null;  // variable pointing to rectangle as it is being created
		Squiggle squiggle = null;  // variable pointing to squiggle as it is being created
		String shape = "";  // stores a string containing the shape currently being formed

		try {
			int state = 0;
			Matcher m, c, r, s, space;
			String l;

			this.lineNumber = 0;
			while ((l = inputStream.readLine()) != null) {
				this.lineNumber++;
				l = l.replaceAll("\\s+", "");
				
				switch (state) {
				case 0:
					m = pFileStart.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (m.matches()) {
						state = 1;
						break;
					}
					else if(space.matches()){  // skips blank lines
						break;
					}
					error("Expected Start of Paint Save File Version 1.0");
					return false;

				case 1: // Looking for the start of a new object or end of the
						// save file
					c = pCircleStart.matcher(l);
					r = pRectangleStart.matcher(l);
					s = pSquiggleStart.matcher(l);
					m = pFileEnd.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (space.matches()) { // skips if there is a blank line
						break;
					} 
					
					else if(c.matches()){
						circle = new Circle();
						state = 2;
						shape = "circle";
					}
					else if (r.matches()) {
						rectangle = new Rectangle();
						state = 2;
						shape = "rectangle";
					} 
					
					else if (s.matches()) {
						squiggle = new Squiggle();
						state = 2;
						shape = "squiggle";
					} 
					
					else if (m.matches()) { // checks for end of save file 
						break;
					} 
					
					else {
						error("Expected Start of shape");
						return false;
					}
					break;

				case 2: // checking the color of the shape
					m = pcolor.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (m.matches()) {

						int red = Integer.parseInt(m.group(1));
						int green = Integer.parseInt(m.group(2));
						int blue = Integer.parseInt(m.group(3));

						Color color = new Color(red, green, blue);
						if (shape == "circle") { // checking color info for circle
							circle.setColor(color);
							state = 3;
						} 
						else if (shape == "rectangle") {  // checking color info for rectangle
							rectangle.setColor(color);
							state = 3;
						} 
						else if (shape == "squiggle") {  //checking color info for squiggle
							squiggle.setColor(color);
							state = 3;
						}
					}
					else if(space.matches()){ // ignores blank lines
						break;
					}
					else{
						error("Expected color details for " + shape);
						return false;
					}
					
					break;

				case 3: // checking if the shape info for filled is present 
					m = pfilled.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (m.matches()) {
						boolean fill = Boolean.parseBoolean(m.group(1));

						if (shape == "circle") { //checking filled info for circle
							circle.setFill(fill);
							state = 4;
						} else if (shape == "rectangle") {  //checking filled info for rectangle
							rectangle.setFill(fill);
							state = 5;
						} else if (shape == "squiggle") { // checking filled info for squiggle
							squiggle.setFill(fill);
							state = 6;
						}
					}
					
					else if(space.matches()){ // ignores blank lines
						break;
					}
					
					else {
						error("Expected filled details for " + shape);
						return false;
					}
					
					break;

				case 4: // Checking for centre of circle
					c = pcentre.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (c.matches()) {
						int one = Integer.parseInt(c.group(1));
						int two = Integer.parseInt(c.group(2));

						Point centre = new Point(one, two);
						circle.setCentre(centre);
						state = 7;
					} 
					
					else if(space.matches()){ // ignores blank lines
						break;
					}
					
					else {
						error("Expected centre info for circle");
						return false;
					}
					break;

				case 5: // checking for p1 of rectangle
					r = pP1.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (r.matches()) {

						int one = Integer.parseInt(r.group(1));
						int two = Integer.parseInt(r.group(2));

						Point p1 = new Point(one, two);
						rectangle.setP1(p1);
						state = 8;
					}
					
					else if(space.matches()){ // ignores blank lines
						break;
					}
					
					else {
						error("Expected p1 info for rectangle");
						return false;
					}
					break;

				case 6: // checking for points for squiggle
					s = pSquigglepoint.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (s.matches()) {
						state = 9;
					}
					else if(space.matches()){ // ignores blank lines
						break;
					}
					
					else {
						error("Expected points (indicating start of points for squiggle)");
						return false;
					}
					break;

				case 7: // checking for radius of circle
					c = pradius.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (shape == "circle" && c.matches()) {
						int radius = Integer.parseInt(c.group(1));
						circle.setRadius(radius);
						state = 10;
					} 
					
					else if(space.matches()){ // ignores blank lines
						break;
					}
					
					else {
						error("Expected radius of circle");
						return false;
					}
					break;

				case 8: // checking for p2 of rectangle
					r = pP2.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (shape == "rectangle" && r.matches()) {

						int one = Integer.parseInt(r.group(1));
						int two = Integer.parseInt(r.group(2));

						Point p2 = new Point(one, two);
						rectangle.setP2(p2);
						state = 11;
					}
					else if(space.matches()){ // ignores blank lines
						break;
					}
					else {
						error("Expected p2 for rectangle");
						return false;
					}
					break;

				case 9: // checking the squiggle points
					s = pPoints.matcher(l);
					m = pSquiggleEndpoint.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (s.matches()) {
						int one = Integer.parseInt(s.group(1));
						int two = Integer.parseInt(s.group(2));
						Point point = new Point(one, two);
						squiggle.add(point);
						state = 9;
					}
					else if (m.matches()) { //checking for EndPoints
						state = 12;
					} 
					else if(space.matches()){ // ignores blank lines
						break;
					}
					else {
						error("Expected a point for squiggle");
						return false;
					}
					break;

				case 10: // checking for EndCircle
					c = pCircleEnd.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (c.matches()) {
						CircleCommand command = new CircleCommand(circle);
						commands.add(command);
						state = 1;
					}
					else if(space.matches()){ // ignores blank lines
						break;
					}
					else {
						error("Expected EndCircle");
						return false;
					}
					break;

				case 11: // checking for EndRectangle
					r = pRectangleEnd.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (r.matches()) {
						RectangleCommand command = new RectangleCommand(rectangle);
						commands.add(command);
						state = 1;
					} 
					else if(space.matches()){ // ignores blank lines
						break;
					}
					else {
						error("Expected EndRectangle");
						return false;
					}
					break;

				case 12: // checking for EndSquiggle
					s = pSquiggleEnd.matcher(l);
					space = pEmptyLine.matcher(l);
					
					if (s.matches()) {
						SquiggleCommand command = new SquiggleCommand(squiggle);
						commands.add(command);
						state = 1;
					}
					else if(space.matches()){ // ignores blank lines
						break;
					}
					else {
						error("Expected EndSquiggle");
						return false;
					}
					break;
				}
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
}
