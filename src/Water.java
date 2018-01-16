/**
 * @author Zhang
 *This Water class represents a splash of Water that is sprayed by the Hero to 
 *extinguish Fireballs and Fires, as they attempt to save the Pants. 
 *This class should contain and use at least the following private fields:
 *private Graphic graphic;
 *private float speed;
 *private float distanceTraveled;
 */
public class Water {
	private Graphic graphic;//define new graphic object
	private float speed; // speed of water
	private float distanceTraveled; //distance traveled of water
	/**
	 * This constructor initializes a new instance of Water at the specified 
	 * location and facing a specific movement direction. This Water should 
	 * move with a speed of 0.7 pixels per millisecond.
	 * @param x - the x-coordinate of this new Water's position
	 * @param y - the y-coordinate of this new Water's position
	 * @param direction - the angle (in radians) from 0 to 2pi that this new
	 * Water should be both oriented and moving according to.
	 */
	public Water(float x, float y, float direction) {
		graphic = new Graphic("WATER");
		speed = 0.7f;
		distanceTraveled = 0;
		graphic.setPosition(x, y);
		graphic.setDirection(direction);		
	}
	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * @return a reference to this Water's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * This method is called repeatedly by the Game to draw and move the 
	 * current Water. After this Water has moved a total of 200 pixels or 
	 * further, it should stop displaying itself and this method should return 
	 * null instead of a reference to the current instance of a Water object.
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 * @return a reference to this Water object until this water has traveled 
	 * 200 or more pixels. It should then return null after traveling this far.
	 */
	public Water update(int time) {
		distanceTraveled += speed * time;
		if (distanceTraveled <= 200) {
			//water cannot move more than 200 pixels
			graphic.setX(graphic.getX() 
					+ (graphic.getDirectionX() * speed * time));
			graphic.setY(graphic.getY() 
					+ (graphic.getDirectionY() * speed * time));
			graphic.draw();
			return this;
		}
		else {
			return null; //water cannot move more than 200 pixels
		}
	}
}
