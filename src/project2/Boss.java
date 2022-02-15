package project2;

import java.awt.image.BufferedImage;

public class Boss extends FlyObject{
	private int id;
	private int blood=100;
	private int v=2;
	private int d=2;
	public Boss(int x,int y,int width,int height,BufferedImage image){
		super(x,y,width,height,image);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBlood() {
		return blood;
	}
	public void setBlood(int blood) {
		this.blood = blood;
	}
	public void bloodLose(int x){
		this.blood-=x;
		if(blood<=0)
			blood=0;
	}
	public Bullet fire(int x,int y,int width,int height,int v,BufferedImage image){
		Bullet b = new Bullet(x,y,width,height, image);
		b.setBulletSpeed(v);
		return b;
	}
	public void moveDown(){
		y+=2;
		int demo=0;
		if(this.id==1)
			demo=50;
		if(this.id==2)
			demo=80;
		if(this.id==3)
			demo=60;
		if(y>=demo){
			y=demo;
		}
	}
	public void move(){
		int demo=0;
		if(this.id==1)
			demo=50;
		if(this.id==2)
			demo=80;
		if(this.id==3)
			demo=60;
		if(y==demo){
			x+=v;
			if(x==400-this.width)
				v=-d;
			if(x==0)
				v=d;
		}
	}
}
