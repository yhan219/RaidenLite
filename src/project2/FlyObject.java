package project2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FlyObject {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected BufferedImage image;

	public FlyObject(int x, int y, int width, int height, BufferedImage image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
	}

	public boolean collision(FlyObject other) {
		if ((this.x >= other.x && this.x <= other.x + other.width
				&& this.y >= other.y && this.y <= other.y + other.height)
				|| (other.x >= this.x && other.x <= this.x + this.width
						&& other.y >= this.y && other.y <= this.y + this.height)
				|| (this.x >= other.x && this.x <= other.x + other.width
						&& other.y >= this.y && other.y <= this.y + this.height)
				|| (other.x >= this.x && other.x <= this.x + this.width
						&& this.y >= other.y && this.y <= other.y
						+ other.height))
			return true;
		else
			return false;
	}

}
