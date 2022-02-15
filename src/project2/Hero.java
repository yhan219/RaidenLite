package project2;

import java.awt.image.BufferedImage;

import project2.Resources;

public class Hero extends FlyObject {
	private int blood = 100;

	public Hero(int x, int y) {
		super(x, y, 40, 40, Resources.heroPNG);
	}

	public Bullet fire(BufferedImage image, int x, int y, int v) {// x设置子弹位置
		Bullet b = new Bullet(x, y, 6, 15, image);
		b.setBulletSpeed(-8);
		b.setV(v);
		b.move();
		return b;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public void bloodLose() {
		this.blood -= 20;
		if (blood < 0)
			this.blood = 0;
		if (blood > 100)
			this.blood = 100;
	}

	public void bloodAdd() {
		this.blood += 20;
		if (blood < 0)
			this.blood = 0;
		if (blood > 100)
			this.blood = 100;
	}

	public void moveUp(int d) {
		y -= d;
		if (y < 0)
			y = 0;
	}

	public void moveDown(int d) {
		y += d;
		if (y > 535)
			y = 535;
	}

	public void moveRight(int d) {
		x += d;
		if (x > 355)
			x = 355;
	}

	public void moveLeft(int d) {
		x -= d;
		if (x < 0)
			x = 0;
	}

}
