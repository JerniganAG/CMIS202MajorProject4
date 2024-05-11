// Course Section: CMIS202-ONL1 (Seidel) Spring 2024
// File: BookDisplayC.java
// Description: UI for inputing book information
//Small collection of books sort by author's last name classify genere or number of pages
//book display with buttons home, quit, books txt
// **********************************************

package Lproject;


//book display with buttons home, quit, books txt


import Lproject.DisplayBL;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

//class just creates a scene to display the attributes of a book object
public class BookDisplayC {
	//Creating references to generic template class
	private static DisplayBL<String> outline = new DisplayBL<String>();
	private static DisplayBL<Integer> intoutline = new DisplayBL<Integer>();
	
	//Create the scene for displaying book information
	public static Scene buildBookDisplayScene(Book book) throws FileNotFoundException {
		//create main pane
		BorderPane pane = new BorderPane();
		
		ScrollPane sPane = new ScrollPane(buildDisplay(book));
		sPane.setFitToHeight(true);
		sPane.setFitToWidth(true);
		
		//set pane positions for ui
		pane.setCenter(sPane);
		pane.setBottom(eventBox());
		
		//create new scene using the pane
		Scene scene = new Scene(pane);
		return scene;
	}
	
	//Create the pane to showcase the book attributes CLICK SCREEN
	public static BorderPane buildDisplay(Book book) {
		BorderPane bPane = new BorderPane(); //create main Vbox
		
		//Create HBoxes
		HBox titleBox = new HBox();
		HBox descriptionBox = new HBox();
		HBox bottomBox = new HBox(50);
		
		//Inserting book contents
		titleBox.getChildren().add(outline.DisplayHugeLabel(book.getTitle()));//Creating title text
		descriptionBox.getChildren().add(outline.DisplaySmallLabel(book.getDescription()));
		bottomBox.getChildren().addAll(outline.DisplayBigLabel(book.getAuthor()), outline.DisplayBigLabel(book.getGenre()),outline.DisplayBigLabel(book.getStatus()), intoutline.DisplayNumBigLabel(book.getPageCount()));
		
		//Setting alignment & padding
		titleBox.setAlignment(Pos.BOTTOM_CENTER);
		titleBox.setPadding(new Insets(15, 15, 50, 50));
		descriptionBox.setAlignment(Pos.CENTER);
		descriptionBox.setPadding(new Insets(15, 15, 50, 50));
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(15, 15, 50, 50));
		
		//Set spaces on the border pane
		bPane.setTop(titleBox); 
		bPane.setLeft(descriptionBox);
		bPane.setBottom(bottomBox);
		
		return bPane;
	}
	
	//create buttons for ui
	private static HBox eventBox() throws FileNotFoundException {
		//set up hbox
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: gray");
		EventMain eventManager = new EventMain();
				
		//Back to main menu
		Button btHome = outline.DisplayButton("Home");
		btHome.setOnAction(eventManager.buttonHome);
					
		//View Library
		Button btView = outline.DisplayButton("Books");
		btView.setOnAction(eventManager.buttonView);

		//Quit the application
		Button btQuit = outline.DisplayButton("Quit");
		btQuit.setOnAction(eventManager.buttonQuit);
					
		//add buttons
		hBox.getChildren().addAll(btHome, btView, btQuit);
					
		//set alignment
		hBox.setAlignment(Pos.CENTER);
					
		return hBox;
	}
}
