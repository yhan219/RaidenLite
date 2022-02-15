package project2;

public class Background extends FlyObject{
	public Background(int x,int y){
		super(x,y,400,1200,Resources.backgroundPNG);
	}
	public void move(long rate){
		if(rate%2 == 0){
			y+=1;
			if(y>=0)
				y=-600;
		}
	}
}
