package it.unical.mat.model;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Artificial {

	    public static void click(Node node) {
	        Event.fireEvent(node, new MouseEvent(MouseEvent.MOUSE_CLICKED, node.getLayoutX()/2, node.getLayoutY()/2, node.getLayoutX()/2, node.getLayoutY()/2, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));
	    }
	
}
