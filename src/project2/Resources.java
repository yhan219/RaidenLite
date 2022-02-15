package project2;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {
	public static BufferedImage heroPNG;
	public static BufferedImage backgroundPNG;
	public static BufferedImage enemy1PNG;
	public static BufferedImage enemy2PNG;
	public static BufferedImage enemy3PNG;
	public static BufferedImage bullet1PNG;
	public static BufferedImage bullet2PNG;
	public static BufferedImage bullet3PNG;
	public static BufferedImage herobulletPNG;
	public static BufferedImage diePNG;
	public static BufferedImage skillPNG;
	public static BufferedImage bomb_fPNG;
	public static BufferedImage skill1PNG;
	public static BufferedImage herobullet1PNG;
	public static BufferedImage gift1PNG;
	public static BufferedImage gift2PNG;
	public static BufferedImage boss1PNG;
	public static BufferedImage boss2PNG;
	public static BufferedImage boss3PNG;
	public static BufferedImage boss1BulletPNG;
	public static BufferedImage boss2BulletPNG;
	public static BufferedImage boss3BulletPNG;
	public static BufferedImage boss4BulletPNG;
	public static BufferedImage boss5BulletPNG;
	public static BufferedImage startPNG;
	public static BufferedImage exitPNG;
	public static BufferedImage scorePNG;

	static {
		try {
			exitPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/exit.png"));
			startPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/start.png"));
			boss5BulletPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bossbullet5.png"));
			boss4BulletPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bossbullet4.png"));
			boss3BulletPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bossbullet3.png"));
			boss2BulletPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bossbullet2.png"));
			boss1BulletPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bossbullet1.png"));
			boss1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/boss1.png"));
			boss2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/boss2.png"));
			boss3PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/boss3.png"));
			gift1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/gift.png"));
			gift2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/gift1.png"));
			bomb_fPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bomb_f.png"));
			backgroundPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/background.png"));
			heroPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/hero.png"));
			enemy1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/diji1.png"));
			enemy2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/diji2.png"));
			enemy3PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/diji3.png"));
			bullet1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bullet1.png"));
			bullet2PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bullet2.png"));
			bullet3PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/bullet3.png"));
			herobulletPNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/herobullet.png"));
			herobullet1PNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/herobullet1.png"));
			diePNG = ImageIO.read(Resources.class
					.getResourceAsStream("/image/explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
