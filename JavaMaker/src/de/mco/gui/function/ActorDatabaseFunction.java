
package de.mco.gui.function;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import de.mco.actor.Actor;
import de.mco.sort.Sort;

/**
 *
 *@Author Marco Hoff
 */

public class ActorDatabaseFunction {

	public DefaultListModel getActorList(){
		DefaultListModel<Actor> listModel = new DefaultListModel();
		for(Actor a : Actor.getAllActor()){
			listModel.addElement(a);
		}
		return listModel;
	}

	public JComboBox getSortList(){
		JComboBox x = new JComboBox();
		for(Sort s : Sort.getAllSort()){
			x.addItem(s);
		}
		return x;
	}

}
