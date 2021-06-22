package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Enemy extends Entity{
	double hp;
	double maxHp;
	Moves[] moveset;
	
	//bad guys are defined by how much hp they have. 
	String name;
	public Enemy(double hp, double x, double y) {
		super(x, y, 50, 50);
		this.maxHp=hp;
		this.hp=hp;
		
		if(hp>=75) {
			name="CollegeBoard";
			
			moveset= new Moves[3];
			moveset[0] = new Moves(22, "Non-profit");
			moveset[1] = new Moves(12, "Grandfather picks up quartz and valuable onyx jewels");
			moveset[2] = new Moves(6, "CollegeBoard is watching");
			
			try {
				this.pic = ImageIO.read(new File("src/extras/CollegeboardBoss.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (hp >= 50){
			name = "Genesis";
			moveset = new Moves[2];
			moveset[0] = new Moves(7, "There has been an update to assignments and grades in Gradebook");
			moveset[1] = new Moves(5, "The gradebook is not available at this time.");
			
			try {
				this.pic = ImageIO.read(new File("src/extras/GenesisBoss.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (hp >= 25) {
			name = "Schoology";
			moveset = new Moves[2];
			moveset[0]= new Moves(4, "Schoology is currently unavailible");
			moveset[1]=new Moves(2, "Lori Perez has liked your post");
			try {
				this.pic = ImageIO.read(new File("src/extras/SchoologyBoss.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			name = "Google Classroom";
			moveset= new Moves[2];
			moveset[0]= new Moves(2, "A new assignment has been posted");
			moveset[1]=new Moves(3, "Overdue assignments: 1");
			try {
				this.pic = ImageIO.read(new File("src/extras/ClassroomBoss.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
