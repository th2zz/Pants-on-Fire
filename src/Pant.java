
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Tinghe Zhang
 * This class represents a pair of Pants that the Hero must protect from burning.
 * Whenever a Pant collides with a Fireball, that Pant will be replaced by a Fire. 
 * This class should contain and use at least the following private fields:
 * private Graphic graphic;
 * private Random randGen;
 * private boolean isAlive;
 */

public class Pant {
	private Graphic graphic; //define new graphic object
	private Random randGen; //define new random generator
	private boolean isAlive; //use to find if pant is alive
	/**
	 * This constructor initializes a new instance of Pant at the appropriate 
	 * location. The Random number is only used to create a new Fire, after 
	 * this pant is hit by a Fireball.
	 * @param x - the x-coordinate of this new Pant's position
	 * @param y - the y-coordinate of this new Pant's position
	 * @param randGen - a Random number generator to pass onto any Fire that
	 * is created as a result of this Pant being hit by a Fireball.
	 */
	public Pant(float x, float y, Random randGen) {
		graphic = new Graphic("PANT"); //initialize graphic object
		graphic.setPosition(x, y); //set positions of pant
		isAlive = true; //original pant is alive for sure
		this.randGen = randGen; // assign value to random generator
	}
	/**
	 * This is a simple accessor for this object's Graphic, which may be used by
	 * other objects to check for collisions.
	 * @return a reference to this Pant's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * This method detects an handles collisions between any active Fireball,
	 *  and the current Pant. When a collision is found, the colliding 
	 *  Fireball should be removed from the game (by calling its destroy() 
	 *  method), and the current Pant should also be removed from the game 
	 *  (by ensuring that its shouldRemove() method returns true). 
	 *  A new Fire should be created in the position of the old Pant object 
	 *  and then returned.
	 * @param fireballs the ArrayList of Fireballs that should be checked 
	 * against the current Pant object's Graphic for collisions.
	 * @return a reference to the newly created Fire when a collision is 
	 * found, and null otherwise.
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		boolean isCollide = false; 
		// temporary boolean variable to store information about whether 
		//collision happened,  it is initialized to not happened
		for (int i = 0; i < fireballs.size(); i++) {
			if (graphic.isCollidingWith(fireballs.get(i).getGraphic()) == true) {
				isCollide = true;
				isAlive = false;//
				fireballs.get(i).destroy();//
				break;
			}
		}
		if (isCollide == true) { 
			//when pant is hitted by fireball it should become fire
			return new Fire(graphic.getX(), graphic.getY(), randGen);
		}
		else {
			return null;
		}
		
	}
	/**
	 * This method communicates to the Game whether this Pant is still in use
	 *  versus ready to be removed from the Game's ArrayList of Pants.
	 * @return true when this Pant has been hit by a Fireball, otherwise false.
	 */
	public boolean shouldRemove() { 
		//when pant is not alive then we need to remove
		if (isAlive == false) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * This method is simply responsible for draing the current Pant to the screen.
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 */
	public void update(int time) {
		graphic.draw();
	}
}
