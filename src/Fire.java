
import java.util.Random;
import java.lang.Math;

/**
 * @author Zhang
 *This class represents a fire that is burning, which ejects a Fireball in a 
 *random direction every 3-6 seconds. This fire can slowly be extinguished 
 *through repeated collisions with water. 
 *This class should contain and use at least the following private fields:
 *private Graphic graphic;
 *private Random randGen;
 *private int fireballCountdown;
 *private int heat;
 */
public class Fire {
	private Graphic graphic; //define new graphic object
	private Random randGen; //define new random generator
	private int fireballCountdown; 
	//time gap between each generation of fireballs
	private int heat; //heat status of fire, get lowered when hit by water
	/**
	 * This constructor initializes a new instance of Fire at the appropriate
	 * location and with the appropriate amount of heat. The Random number 
	 * generator should be used both to determine how much time remains before
	 * the next Fireball is propelled, and the random direction it is shot in.
	 * @param x - the x-coordinate of this new Fire's position
	 * @param y - the y-coordinate of this new Fire's position
	 * @param randGen - a Random number generator to determine when and in 
	 * which direction new Fireballs are created and launched.
	 */
	public Fire(float x, float y, Random randGen) {
		graphic = new Graphic("FIRE");
		fireballCountdown = randGen.nextInt(3001) + 3000;
		graphic.setPosition(x, y);
		this.randGen = randGen;
		heat = 40;
	}
	/**
	 * This is a simple accessor for this object's Graphic, which may be used
	 * by other objects to check for collisions.
	 * @return a reference to this Fire's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * This method detects and handles collisions between any active (!= null) 
	 * Water objects, and the current Fire. When a collision is found, the 
	 * colliding water should be removed, and this Fire's heat should be 
	 * decremented by 1. If this Fire's heat dips below one, then it should 
	 * no longer be drawn to the screen, eject new Fireballs, or collide with 
	 * Water and its shouldRemove() method should start returning true.
	 * @param water - is the Array of water objects that have been launched 
	 * by the Hero (ignore any null references within this array).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0;i < water.length; i++) {
			if (water[i] != null && graphic.isCollidingWith(water[i].getGraphic())
					== true) {
				//water exits and collisons happen
				heat--;
				water[i] = null;
			}
		}
	}
	/**
	 * This method should return false until this Fire's heat drops down to 
	 * 0 or less. After that it should begin to return true instead.
	 * @return false when this Fire's heat is greater than zero, otherwise true.
	 */
	public boolean shouldRemove() {
		if (heat > 0) { 
			//fire is removed when it's heat get less than or equal to 0
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * This method is called repeatedly by the Level to draw and occasionally 
	 * launch a new Fireball in a random direction.
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 * @return null unless a new Fireball was just created and launched. In 
	 * that case, a reference to that new Fireball is returned instead.
	 */
	public Fireball update(int time) {
		fireballCountdown -= time;
		if (fireballCountdown <= 0) {
			//the newly created fireball should match the position of the fire 
			//that is ejecting it
			fireballCountdown = randGen.nextInt(3001) + 3000;
			if (heat >= 1) {
				graphic.draw();
				return new Fireball(graphic.getX(), graphic.getY(), 
						(float)(randGen.nextDouble() * 2 * Math.PI));
			}
			else {
				return null;
			}
		}
		else {
			if (heat >= 1) { //fire only show up when it has heat
				graphic.draw();
			}
			return null;
		}
	}	
}
