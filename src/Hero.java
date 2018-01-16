import java.lang.Math;
import java.util.ArrayList;
/**
 * @author Tinghe Zhang
 *This class represents the player's character which is a fire fighter who is 
 *able to spray water that extinguishes Fires and Fireballs. They must save 
 *as many Pants from burning as possible, and without colliding into any 
 *Fireballs in the process. 
 *This class should contain and use at least the following private fields:
 *private Graphic graphic;
 *private float speed;
 *private int controlType;
 */
public class Hero {
	private Graphic graphic;//define new graphic object
	private float   speed; //speed of hero
	private int     controlType; //control type of game
	private float distance; // the distance between hero and mouse cursor
	/**
	 * This constructor initializes a new instance of Hero at the appropriate
	 * location and using the appropriate controlType. This Hero should move 
	 * with a speed of 0.12 pixels per millisecond.
	 * @param x - the x-coordinate of this new Hero's position
	 * @param y - the y-coordinate of this new Hero's position
	 * @param controlType - specifies which control scheme should be used by 
	 * the player to move this hero around: 1, 2, or 3.
	 */
	public Hero(float x, float y, int controlType) {
		graphic = new Graphic("HERO");
		speed = 0.12f;
		this.controlType = controlType;
		graphic.setPosition(x,y);
	}
	/**
	 * This is a simple accessor for this object's Graphic, which may be 
	 * used by other objects to check for collisions.
	 * @return a reference to this Hero's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	/**
	 * This method is called repeated by the Level to draw and move (based on 
	 * the current controlType) the Hero, as well as to spray new Water in the 
	 * direction that this Hero is currently facing.
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 * @param waterArray - the array of Water that the Hero has sprayed in 
	 * the past, and if there is an empty (null) element in this array, they 
	 * can can add a new Water object to this array by pressing the appropriate 
	 * controls.
	 */
	public void update(int time, Water []waterArray) {
		if (controlType == 1) {
			ControlType1(time);
		}
		else if (controlType == 2) {
			ControlType2(time);
		}
		else if (controlType == 3) {
			ControlType3(time);
		}
		// when player hit mouse or space then hero spray water
		if (GameEngine.isKeyHeld("MOUSE") || GameEngine.isKeyHeld("SPACE")) {
			for (int i = 0; i < waterArray.length; i++) {
				if ((waterArray[i] == null)) {
					waterArray[i] = new Water(graphic.getX(), graphic.getY(), 
							graphic.getDirection());
					break;
				}
			}
		}
		graphic.draw();
	}
	/**
	 * This method detects an handles collisions between any active Fireball 
	 * objects, and the current Hero. When a collision is found, this method 
	 * returns true to indicate that the player has lost the Game.
	 * @param fireballs - the ArrayList of Fireballs that should be checked 
	 * against the current Hero's position for collisions.
	 * @return true when a Fireball collision is detected, otherwise false.
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		boolean isCollide = false; 
		//temporary variable to store status that whether hero 
		//is hitted by fireball
		for (int i = 0;i < fireballs.size(); i++) {
			if (graphic.isCollidingWith(fireballs.get(i).getGraphic()) == true) {
				isCollide = true;
				break;
			}
		}
		if (isCollide == true) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * This method is Control Type 1 
	 * Keyboard Control BOTH direction(WASD) and movement
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 */
	private void ControlType1(int time) {
		if (GameEngine.isKeyHeld("D")) {
			graphic.setX(graphic.getX() + speed * time);
			graphic.setDirection(0);
		}
		if (GameEngine.isKeyHeld("A")) {
			graphic.setX(graphic.getX() - speed * time);
			graphic.setDirection((float)Math.PI);
		}
		if (GameEngine.isKeyHeld("S")) {
			graphic.setY(graphic.getY() + speed * time);
			graphic.setDirection((float)(Math.PI / 2));
		}
		if (GameEngine.isKeyHeld("W")) {
			graphic.setY(graphic.getY() - speed * time);
			graphic.setDirection((float)((Math.PI * 3) / 2));
		}
		//SET HERO facing WASD direction
	}
	/**
	 * This method is Control Type 2 
	 * Keyboard Control movement WASD    Mouse Control direction
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 */
	private void ControlType2(int time) {
		if (GameEngine.isKeyHeld("D")) {
			graphic.setX(graphic.getX() + speed * time);
		}
		if (GameEngine.isKeyHeld("A")) {
			graphic.setX(graphic.getX() - speed * time);
		}
		if (GameEngine.isKeyHeld("S")) {
			graphic.setY(graphic.getY() + speed * time);
		}
		if (GameEngine.isKeyHeld("W")) {
			graphic.setY(graphic.getY() - speed * time);
		}
		graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
		//set hero facing cursor
	}
	/**
	 * This method is Control Type 3 
	 * Mouse Control BOTH direction(WASD) and movement
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * since the last time this update was called.
	 */
	private void ControlType3(int time) {
		graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
		//set hero facing cursor
		// distance of two points is square root of (x1-x2)^2 + (y1-y2)^2
		distance = (float) Math.sqrt(Math.pow(GameEngine.getMouseX() 
				- graphic.getX(), 2) + Math.pow(GameEngine.getMouseY() - graphic.getY(), 2));
		if (distance >= 20) { 
			//hero only travels when it is farther than 20 pixels from cursor
			graphic.setX(graphic.getX() + graphic.getDirectionX() * speed * time);
			graphic.setY(graphic.getY() + graphic.getDirectionY() * speed * time);
		}
	}
}