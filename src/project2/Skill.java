package project2;

import java.awt.image.BufferedImage;

public class Skill extends FlyObject {
	public Skill(int x, int y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	public void move() {
		y += -6;
	}

}
