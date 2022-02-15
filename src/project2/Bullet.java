package project2;

import java.awt.image.BufferedImage;

public class Bullet extends FlyObject{
	private int d = 0;//子弹速度
	private int v=0;
	
	public Bullet(int x,int y,int width,int height,BufferedImage image){
		super(x,y,width,height,image);
	}
	public void setBulletSpeed(int d){
		this.d = d;
	}
	public int getBulletSpeed(){
		return d;
	}
	public void setV(int v){
		this.v=v;
	}
	
	public void move(){
		y+=d;
		x+=this.v;
	}
}
