package project2;

import java.awt.image.BufferedImage;

public class Gift extends FlyObject {
	private int id;

	public Gift(int x, int y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	public void move() {
		y += 1;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
