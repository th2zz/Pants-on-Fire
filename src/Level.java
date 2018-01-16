//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            (Pants on Fire)
// Files:            (Level.java; Pant.java; Fireball.java; Fire.java; 
//						Hero.java; Water.java)
// Semester:         (CS 302) Fall 2016
//
// Author:           (Tinghe Zhang)
// Email:            (tzhang329@wisc.edu)
// CS Login:         (tinghe)
// Lecturer's Name:  (Jim Williams)
// Lab Section:      (314)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 */
public class Level {
	private Random randGen; 
	//The random generator which is used throughout the program
	private Hero hero; //Hero Object, which is the hero player will control
	private Water[] water; 
	//The array of water that will be used by player as weapon
	
	private ArrayList<Pant> pants;
	//ArrayList of pants to store each generated individual pant
	private ArrayList<Fireball> fireballs;
	//ArrayList of fireballs to store each generated individual fireball
	private ArrayList<Fire> fires; 
	//ArrayList of fires to store each generated individual fire
	private Fireball tempFireball; 
	//Temporary variable to store returned thing from fire update method
	//Because we need to recognize if returned thing is null or not
	
	
	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their beginning states.
	 * @param randGen is the only Random number generator that should be used
	 * throughout this level, by the Level itself and all of the Objects within.
	 * @param level is a string that either contains the word "RANDOM", or the 
	 * contents of a level file that should be loaded and played. 
	 */
	public Level(Random randGen, String level) { 
		this.randGen = randGen;//assign value to random generator
		fireballs = new ArrayList<Fireball>();////assign value to arraylist
		water = new Water[8];//initialize each terms of water array to null
		for (int i = 0; i < 8; i++) {
			water[i] = null;
		}
		pants = new ArrayList<Pant>();//assign value to arraylist
		fires = new ArrayList<Fire>();//assign value to arraylist
		
		if (level == "RANDOM") { //"RANDOM" is only passed to update when player
			createRandomLevel(); //finished given three level of games in order
		}
		else {
			loadLevel(level); //load preset level of games(3 level)
		}
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short
	 * 3 second pause and a message indicating that the player has lost.  When
	 * this method returns "ADVANCE", a short pause and win message will be 
	 * followed by the creation of a new level which replaces this one.  When
	 * this method returns anything else (including "CONTINUE"), the GameEngine
	 * will simply continue to call this update() method as usual. 
	 */
	public String update(int time) {
		for (int i = 0; i < 8; i++) {
			if(water[i] != null) water[i] = water[i].update(time);
		} //update water status
		hero.update(time, water); //update hero status
		for (int i = 0; i < pants.size(); i++) { 
			//update pants status & handle pants collisions with fireballs
			pants.get(i).update(time);
			if (pants.get(i).handleFireballCollisions(fireballs) != null) { 
				//if it does not return null it means pant gets fired
				fires.add(pants.get(i).handleFireballCollisions(fireballs));
				//the pant is then replaced with a fire
				if (pants.get(i).shouldRemove() == true) { 
					//we need to remove the fired pants then
					pants.remove(i);
				}
			}
		}
		//update fires status & add returned fireball to arraylist
		for (int i = 0; i < fires.size(); i++) {
			//handle fire water collisions
			fires.get(i).handleWaterCollisions(water);
			tempFireball = fires.get(i).update(time);
			if (tempFireball != null) {
				fireballs.add(tempFireball);
			}
			//remove fires that heat goes to 0
			if (fires.get(i).shouldRemove() == true) {
				fires.remove(i);
			}
		}
		//update fireballs status & handle fireball water collisions
		for (int i = 0; i < fireballs.size(); i++) {
			fireballs.get(i).update(time);
			fireballs.get(i).handleWaterCollisions(water);
			//remove fireballs that collide with other object
			if (fireballs.get(i).shouldRemove() == true) {
				fireballs.remove(i);
			}
		}
		if ((hero.handleFireballCollisions(fireballs) == true) || pants.size() == 0) {
			return "QUIT";
			 //player lose when hero is hitted by fireballs or pants are all fired
		}
		if (fires.size() == 0) {
			return "ADVANCE";
			//player advance to next level when all fires are destroyed
		}
		return "CONTINUE"; 
		
	}	

	/**
	 * This method returns a string of text that will be displayed in the
	 * upper left hand corner of the game window.  Ultimately this text should 
	 * convey the number of unburned pants and fires remaining in the level.  
	 * However, this may also be useful for temporarily displaying messages that 
	 * help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() { 
		String HudMessage = "Pants Left: " + pants.size() + "\nFires Left: " 
				+ fires.size();
		//hudMessage is the message that will show up on top left corner of screen
		return HudMessage; 
		
	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires,
	 * and 20 randomly positioned Pants.
	 */
	public void createRandomLevel() { 
		//create a hero positioned at middle of screen
		hero = new Hero((GameEngine.getWidth() / 2),
				(GameEngine.getHeight() / 2), randGen.nextInt(3) + 1);
		for (int i = 0; i < 20; i++) {
			//create a randomly positioned new pant
			pants.add(i, new Pant(randGen.nextInt(GameEngine.getWidth() + 1),
					randGen.nextInt(GameEngine.getHeight() + 1), randGen));
		}
		for (int i = 0; i < 6; i++) {
			//create a randomly positioned new fire
			fires.add(i, new Fire(randGen.nextInt(GameEngine.getWidth() + 1),
					randGen.nextInt(GameEngine.getHeight() + 1), randGen));
		} 	
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * @param level is a string containing the contents of a custom level file 
	 * that is read in by the GameEngine.  The contents of this file are then 
	 * passed to Level through its Constructor, and then passed from there to 
	 * here when a custom level is loaded.  You can see the text within these 
	 * level files by dragging them onto the code editing view in Eclipse, or 
	 * by printing out the contents of this level parameter.  Try looking 
	 * through a few of the provided level files to see how they are formatted.
	 * The first line is always the "ControlType: #" where # is either 1, 2, or
	 * 3.  Subsequent lines describe an object TYPE, along with an X and Y 
	 * position, formatted as: "TYPE @ X, Y".  This method should instantiate 
	 * and initialize a new object of the correct type and at the correct 
	 * position for each such line in the level String.
	 */
	public void loadLevel(String level) { 
		String[] str = level.split("\n");
		/**the string array is the splitted result of string level,
		each term of array represents each line of string level so that we 
		can handle it easily**/
		int loadControlType = Integer.parseInt(str[0].substring(
				str[0].indexOf(':') + 2));
		// the integer variable to store loaded control type
		/**for a given line if it contains anything, then do according action
		 * like add new fire/pant or create hero
		 * the way to add new object is to use substring to cut each term of 
		 * string array to contain only numbers, the use parseFloat to make to 
		 * number from string, then pass them as x y coordinates parameter
		 * x- first occurrence of number to index of comma
		 * y- index of first number of y to the end
		 */
		for (int i = 0; i < str.length; i++) {
			if (str[i].contains("FIRE")) {
				fires.add(new Fire(Float.parseFloat(str[i].substring(
						str[i].indexOf('@') + 2, str[i].indexOf(','))), 
						Float.parseFloat(str[i].substring(str[i].indexOf(',') 
								+ 2)), randGen));
			}
			else if (str[i].contains("PANT")) { 
				pants.add(new Pant(Float.parseFloat(str[i].substring(
						str[i].indexOf('@') + 2, str[i].indexOf(','))),
						Float.parseFloat(str[i].substring(str[i].indexOf(',') 
								+ 2)), randGen));
			}
			else if (str[i].contains("HERO")) {
				hero = new Hero(Float.parseFloat(str[i].substring(
						str[i].indexOf('@') + 2, str[i].indexOf(','))),
						Float.parseFloat(str[i].substring(str[i].indexOf(',') 
								+ 2)), loadControlType);
			}
		}
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level.  Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * @param args is the sequence of custom level files to play through.
	 */
	public static void main(String[] args) {
		GameEngine.start(null,args);
	}
}
