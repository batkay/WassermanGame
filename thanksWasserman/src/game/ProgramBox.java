package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ProgramBox implements ActionListener {
	Player p1;
	public ProgramBox(Player p1) {
		this.p1=p1;
	}
    public void actionPerformed(ActionEvent e) {
    	JComboBox cb = (JComboBox)e.getSource();
        p1.equipItem(new EquippableItem((String)cb.getSelectedItem()));
    }
}