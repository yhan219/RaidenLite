package project2;

import java.awt.image.BufferedImage;

public class Enemy extends FlyObject{
	private int id;
	private int d = 0;
	private int blood=0;
	public Enemy(int x,int y,int width,int height,BufferedImage image){
		super(x,y,width,width,image);
	}
	public Bullet fire(BufferedImage image ,int x,int y,int width,int height,int v){
		Bullet b = new Bullet(x,y,width,height, image);
		b.setBulletSpeed(v);
		return b;
	}
	public void setBlood(int blood){
		this.blood=blood;
	}
	public int getBlood(){
		return blood;
	}
	public void bloodLose(){
		this.blood--;
	}
	public void setSpeed(int d){
		this.d = d;
	}
	public void die(){
		this.image = Resources.diePNG;
	}
	public void move(long rate){
		if(rate % 1==0)
		y+=d;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
