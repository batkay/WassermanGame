package game;

import java.awt.Point;

public class Utilities {
	public static Point screenToGlobal(double px, double py, double mx, double my, double width, double height) {
		//converts a point on the screen to a point in global xy plane
		
		double newX=px-width/2 +mx;
		double newY= py-height/2+my;
		
		return new Point((int)newX, (int)newY);
	}
	
	public static Point relativeToP(Player p, int x, int y){
        //returns points in relation to the player instead of in relation to global coordinate
        return new Point((int) (x-p.x), (int) (y-p.y));
    }
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.abs(Math.hypot((x1-x2), y1-y2));
	}
	
	public static boolean contains(double x, double y, double x2, double y2, double width, double height) {
		if(x>x2+width/2 || x< x2-width/2) {
			return false;
		}
		if(y>y2+height/2 || y<y2-width/2) {
			return false;
		}
		return true;
	}
	
	public static boolean touches (Entity en1, Entity en2) {
		if(en1.x+en1.width/2 <= en2.x-en2.width/2 || en2.x+en2.width/2 <= en1.x-en1.width/2) {
			return false;
		}
		if(en1.y+en1.height/2<= en2.y-en2.height/2 || en1.y-en1.height/2 >= en2.y+en2.height/2) {
			return false;
		}
		return true;
	}
	
	public static boolean touchesX (Entity en1, Entity en2) {
		return ! (en1.x+en1.width/2 <= en2.x-en2.width/2 || en2.x+en2.width/2 <= en1.x-en1.width/2);
	}
	
	public static boolean touchesY (Entity en1, Entity en2) {
		return ! (en1.y+en1.height/2<= en2.y-en2.height/2 || en1.y-en1.height/2 >= en2.y+en2.height/2);
	}
	
}
