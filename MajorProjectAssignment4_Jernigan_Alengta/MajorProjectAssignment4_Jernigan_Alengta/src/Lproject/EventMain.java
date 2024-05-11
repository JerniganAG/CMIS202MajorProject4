// **********************************************
// Title:  Library Of Reigh
// Author: Alengta Jernigan
// Course Section: CMIS202-ONL1 (Seidel) Spring 2024
// File: EventMain.java
// Description: EventHandler the main menu buttons
//Small collection of books sort by author's last name classify genere or number of pages
//save book or delect infor to library with buttons and display
// **********************************************
package Lproject;



import Lproject.AddBookEntry;
import Lproject.BookDisplayC;
import Lproject.Help;
import Lproject.Reigh;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EventMain {
	
	//references for other classes
	public ButtonView buttonView = new ButtonView();
	public ButtonAdd buttonAdd = new ButtonAdd();
	public ButtonHelp buttonHelp = new ButtonHelp();
	public ButtonHome buttonHome = new ButtonHome();
	public ButtonQuit buttonQuit = new ButtonQuit();
	public ButtonClean buttonClean = new ButtonClean();
	public ButtonQueue buttonQueue = new ButtonQueue();
	public ButtonSave buttonSave = new ButtonSave();
	public ButtonDescription buttonDescription = new ButtonDescription();
	
	//Button the view library buttons
	class ButtonView implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(ViewLibraryOfReigh.buildLibraryScene());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
	
	//Button the add to library buttons maybe problem
	class ButtonAdd implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Scene scene = null;
                    try {
                        scene = AddBookEntry.buildBookScene();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
			Reigh.stage.setScene(scene);
		}
	}
	
	//Button the clean duplicates button
	class ButtonClean implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			ViewLibraryOfReigh.cleanDuplicates();
                    try {
                        Reigh.stage.setScene(ViewLibraryOfReigh.buildLibraryScene());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}

	//Handle the help menu buttons
	class ButtonHelp implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(Help.buildHelpScene());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
	
	//Handle the main menu buttons
	class ButtonHome implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
                    try {
                        Reigh.stage.setScene(Reigh.buildStartScene());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
	}
	
	//Handle the quit buttons
	class ButtonQuit implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}
	}
	
	//Handle the add to queue button
	class ButtonQueue implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			AddBookEntry.saveBookToQueue(AddBookEntry.bPane);
		}
	}
	
	//Handle the save from queue button
	class ButtonSave implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			AddBookEntry.saveBooksInQueue(AddBookEntry.bPane);
		}
	}
		
	//handle clicking on a title to view description
	class ButtonDescription implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			//Get the text from the title label clicked
			Label lbl = ((Label) e.getSource());
			String lblText = lbl.getText();
			
			//find the book that the title is the key to.
			Book book = ViewLibraryOfReigh.descriptionMap.get(lblText);
                    try {
                        Reigh.stage.setScene(BookDisplayC.buildBookDisplayScene(book));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EventMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
			
		}
	}
}
