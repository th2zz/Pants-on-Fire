

/**
 * @author Tinghe Zhang
 *This class represents a Fireball that is ejected from a burning fire. 
 *When a Fireball hits the Hero, they lose the game. When a Fireball 
 *hits a Pant, those Pants are replaced by a new Fire. 
 *This class should contain and use at least the following private fields:
 *This class should contain and use at least the following private fields:
 *private Graphic graphic;
 *private float speed;
 *private boolean isAlive;
 */
public class Fireball {
	private Graphic graphic;//define new graphic object
	private float speed; //define float variable speed to store fireball speed
	private boolean isAlive; //variable that is used to store status of fireball
	private boolean isbeyondEdge;//variable that is used to store status that 
	//fireball cross edge or not
	/**
	 * This constructor initializes a new instance of Fireball at the specified
	 * location and facing a specific movement direction. This Fireball should
	 * move with a speed of 0.2 pixels per millisecond.
	 * @param x - the x-coordinate of this new Fireball's position
	 * @param y - the y-coordinate of this new Fireball's position
	 * @param directionAngle - the angle (in radians) from 0 to 2pi that this 
	 * new Fireball should be both oriented and moving according to.
	 */
	public Fireball(float x, float y, float directionAngle) {
		speed = 0.2f;
		isAlive = true;
		graphic = new Graphic("FIREBALL");
		graphic.setPosition(x, y);
		graphic.setDirection(directionAngle);
		isbeyondEdge = false; //fireball status initialization
		}
	/**
	 * This is a simple accessor for this object's Graphic, which may be used
	 * by other objects to check for collisions.
	 * @return a reference to this Fireball's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * This method detects and handles collisions between any active (!= null) 
	 * Water objects, and the current Fireball. When a collision is found, the
	 * colliding water should be removed (array reference set to null), and 
	 * this Fireball should also be removed from the game (its shouldRemove() 
	 * should begin to return true when called). When this Fireball's 
	 * shouldRemove method is already returning true, 
	 * this method should not do anything.
	 * @param water - is the Array of Water objects that have been launched by 
	 * the Hero (ignore any null references within this array).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0;i < water.length; i++) {
			if (water[i] != null && graphic.isCollidingWith(water[i].getGraphic
					()) == true) {
				isAlive = false;
				water[i] = null;
			}
		}
	}
	/**
	 * This helper method allows other classes (like Pant) to destroy a Fireball
	 * upon collision. This method should ensure that the shouldRemove() 
	 * methods only returns true after this method (destroy) has been called.
	 */
	public void destroy() {
		isAlive = false;
	}
	/**
	 * This method communicates to the Level whether this Fireball is still in 
	 * use versus ready to be removed from the Levels's ArrayList of Fireballs.
	 * @return true when this Fireball has either gone off the screen or 
	 * collided with a Water or Pant object, and false otherwise.
	 */
	public boolean shouldRemove() {
		//when fireball collides anything it should be removed
		if (isAlive == false) {
			return true;
		}
		else {
			return false;
		}
		
	}
	/**
	 * This method is called repeatedly by the Level to draw and move the 
	 * current Fireball. When a Fireball moves more than 100 pixels beyond 
	 * any edge of the screen, it should be destroyed and its shouldRemove() 
	 * method should begin to return true instead of false.
	 * @param time - is the amount of time in milliseconds that has elapsed
	 * since the last time this update was called.
	 */
	public void update(int time) {
		isbeyondEdge = (graphic.getX() > GameEngine.getWidth() + 100) || 
				(graphic.getX() < -100) || (graphic.getY() > 
				GameEngine.getHeight() + 100) || (graphic.getY() < -100);
		if (!isbeyondEdge) {
			graphic.setX(graphic.getX() 
					+ (graphic.getDirectionX() * speed * time));
			graphic.setY(graphic.getY() 
					+ (graphic.getDirectionY() * speed * time));
		}
		if (isbeyondEdge) {
			isAlive = false;
		}
		if (isAlive == true) { //fireball is only drawed when it is alive
			graphic.draw();
		}
	}
	
}
