// **********************************************
// Title:  Library Of Reigh
// Author: Alengta Jernigan
// Course Section: CMIS202-ONL1 (Seidel) Spring 2024
// File: Reigh.java
// Description: Arraylist,megasort
//Small collection of books sort by author's last name classify genere or number of pages (library display)
//file output arraylist javafx
// **********************************************
package Lproject;



import Lproject.DisplayBL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


//Class to view books
public class ViewLibraryOfReigh {//reference eventmain.java
	private static DisplayBL<String> outline = new DisplayBL<String>();
	private static DisplayBL<Integer> intoutline = new DisplayBL<Integer>();
        private static String bookPath = "src\\libraryofreigh.txt";//reference to file path
	private static ArrayList<Book> bookArray;

    //Hash map to connect a description to its title
    public static MyHashMap<String, Book> descriptionMap = new MyHashMap<>();

    //build the scene for displaying books
    public static Scene buildLibraryScene() throws FileNotFoundException {
        //create the arraylist of type Book to store books from file
        try {
            cleanDuplicates(); //clean the file of duplicates before building ui
            bookArray = assembleLibrary(); //build array of books from file

            //Build hash map for displaying descriptions
            buildDescriptionMap();
        } catch (NullPointerException e) {
            outline.DisplayErrorMessage(e.toString());
        }

        //sort the array list based on author last name
        bookArray = MyMergeSort.threadedMergeSort(bookArray);

        //build ScrollPane so that if large enough the user can scroll through their books
        ScrollPane sPane = new ScrollPane(buildLibrary());
        sPane.setFitToHeight(true);
        sPane.setFitToWidth(true);

        //create borderpane
        BorderPane bPane = new BorderPane();
        bPane.setTop(outline.DisplaySimpleHBox("Click on The Title To See Description"));
        bPane.setCenter(sPane); //put scroll pane in center
        bPane.setBottom(eventBox()); //put buttons on bottom

        //create scene using borderpane
        Scene scene = new Scene(bPane);
        return scene;
    }

    //Assemble the arraylist
    public static ArrayList<Book> assembleLibrary() {
        try {
            File file = new File(bookPath);
            Scanner scan = new Scanner(file);

            //book list using generics to make arraylist of type Book
            ArrayList<Book> bookList = new ArrayList<Book>();
            ArrayList<Book> returnList = buildList(scan, bookList);

            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        return new ArrayList<Book>();
    }

    //add each line from file to a spot on the arraylist
    public static ArrayList<Book> buildList(Scanner scan, ArrayList<Book> list) {
        //Scan for next book
        scan.nextLine(); // ignore the first line
        while (scan.hasNextLine()) {
            //Create tokenizer
            String temp = (String) scan.nextLine();
            if (temp.length() < 1) {
                System.out.println("next line empty");
                break;
            }
            StringTokenizer str = new StringTokenizer((String) temp, "|");

            //make a new book
            Book book = new Book();

            //separate tokens
            for (int i = 0; str.hasMoreTokens(); i++) {
                if (i == 0) {
                    book.setTitle(str.nextToken());//get title of book
                } else if (i == 1) {
                    book.setAuthor(str.nextToken());//get author name
                } else if (i == 2) {
                    book.setGenre(str.nextToken());//get genre
                } else if (i == 3) {
                    book.setPageCount(Integer.parseInt(str.nextToken()));//get page count
                } else if (i == 4) {
                    book.setStatus(str.nextToken());// get book status
                } else {
                    book.setDescription(str.nextToken());
                }
            }
            //Add assembled book to list
            list.add(book);
        }
        return list;
    }

    //Build main pane UI
    private static HBox buildLibrary() {
        //establish boxes
        HBox hBox = new HBox();
        VBox titleBox = new VBox(6);
        VBox authorBox = new VBox(6);
        VBox genreBox = new VBox(6);
        VBox pageBox = new VBox(6);
        VBox statusBox = new VBox(6);

        //pad the vboxes
        titleBox.setPadding(new Insets(15, 15, 15, 15));
        authorBox.setPadding(new Insets(15, 15, 15, 15));
        genreBox.setPadding(new Insets(15, 15, 15, 15));
        pageBox.setPadding(new Insets(15, 15, 15, 15));
        statusBox.setPadding(new Insets(15, 15, 15, 15));
        //add labels
        titleBox.getChildren().add(outline.DisplayBigLabel("Title")); //create title header
        genreBox.getChildren().add(outline.DisplayBigLabel("Author")); //create author header
        statusBox.getChildren().add(outline.DisplayBigLabel("Status")); //status header
        authorBox.getChildren().add(outline.DisplayBigLabel("Genre")); //create genre header
        pageBox.getChildren().add(outline.DisplayBigLabel("Pages")); //create page count header

        //set list size variable
        int listSize = bookArray.size();
        //Add each individual book to display
        for (int i = 0; i < listSize; i++) {
            //get the book at the specified spot
            Book book = (Book) bookArray.get(i);
            //add each part of the book to display in order
            titleBox.getChildren().add(outline.DisplaySmallTitleLabel(book.getTitle()));
            authorBox.getChildren().add(outline.DisplaySmallLabel(book.getAuthor()));
            genreBox.getChildren().add(outline.DisplaySmallLabel(book.getGenre()));
            statusBox.getChildren().add(outline.DisplaySmallLabel(book.getStatus()));
            pageBox.getChildren().add(intoutline.DisplayNumSmallLabel(book.getPageCount()));
        }

        //add vboxes to hbox
        hBox.getChildren().addAll(titleBox, genreBox, statusBox, authorBox, pageBox);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    //Method that will build a hash map from the book array
    //Will have the title of the book as the key and the value being the description
    private static void buildDescriptionMap() {
        int listSize = bookArray.size();

        for (int i = 0; i < listSize; i++) {
            Book book = bookArray.get(i);
            String bookTitle = book.getTitle();

            descriptionMap.put(bookTitle, book);
        }
    }

    //Use a binary search tree to check the file for duplicate titles;
    //Improvement over the hash set solution applied before, allowing less code.
    public static void cleanDuplicates() {
        //create sets for the books, and for amount of duplicates
        ArrayList<Book> cleanList = new ArrayList<>();

        //Binary Search Tree(BST) for detecting duplicate Titles
        BST<String> dupeTitleTree = new BST<>();

        //Read from file for duplicate titles.
        try {
            File file = new File(bookPath);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                //Create tokenizer
                String temp = (String) scan.nextLine();
                if (temp.length() < 1) {
                    System.out.println("next line empty");
                    break;
                }
                StringTokenizer str = new StringTokenizer((String) temp, "|");

                //make a new book
                Book book = new Book();

                //get title
                String bookTitle = str.nextToken();

                //check if the BST contains the title already
                if (dupeTitleTree.contains(bookTitle)) {
                    System.out.println("Duplicate Book Title");
                } else { //build the book like normal to be sent to overwrite the file without dupes
                    dupeTitleTree.insert(bookTitle);

                    //building book from file
                    book.setTitle(bookTitle);
                    for (int i = 0; str.hasMoreTokens(); i++) {
                        if (i == 0) {
                            book.setTitle(str.nextToken());//get title of book
                        } else if (i == 1) {
                            book.setAuthor(str.nextToken());//get author name
                        } else if (i == 2) {
                            book.setGenre(str.nextToken());//get genre
                        } else if (i == 3) {
                            book.setPageCount(Integer.parseInt(str.nextToken()));//get page count
                        } else if (i == 4) {
                            book.setStatus(str.nextToken());// get book status
                        } else {
                            book.setDescription(str.nextToken());
                        }
                    }
                }
                //add the book to the array list
                cleanList.add(book);
            }
            scan.close(); //close scanner

            //call method to create list without duplicates
            overwriteFile(cleanList);
        } catch (Exception e) {
            outline.DisplayErrorMessage(e.toString());
        }
    }

    //Overwrite old file with new file
    public static void overwriteFile(ArrayList<Book> list) {
        PrintWriter file;

        for (int i = 0; i < list.size(); i++) {
            //make a new book
            Book book = list.get(i);

            //try to record book to file
            try {
                //if first book then overwrite the file
                if (i == 0) {
                    file = new PrintWriter(new FileOutputStream(new File(bookPath), false));
                    file.println("title, genre, author, status, description");
                    file.println(book.recordBook());
                    file.close();
                } else { //if not first book then don't overwrite, append the file instead
                    file = new PrintWriter(new FileOutputStream(new File(bookPath), true));
                    file.println("title, genre, author, status, description");
                    file.println(book.recordBook());
                    file.close();
                }
            } catch (Exception e) {
                outline.DisplayErrorMessage(e.toString());
            }

        }
    }

    //create buttons for ui
    private static HBox eventBox() throws FileNotFoundException {
        //set up hbox
        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(15, 15, 15, 15));
        hBox.setStyle("-fx-background-color: black");
       EventMain eventManager = new EventMain();

        //create buttons
        Button btHome = outline.DisplayButton("Home");
        btHome.setOnAction(eventManager.buttonHome);

        //Clean the library of duplicates
        Button btClean = outline.DisplayButton("Remove Duplicates");
        btClean.setOnAction(eventManager.buttonClean);

        //add a new book
        Button btAdd = outline.DisplayButton("New Book");
        btAdd.setOnAction(eventManager.buttonAdd);

        //Open the description
        Button btHelp = outline.DisplayButton("Help");
        btHelp.setOnAction(eventManager.buttonHelp);

        //Quit the application
        Button btQuit = outline.DisplayButton("Quit");
        btQuit.setOnAction(eventManager.buttonQuit);

        //add buttons
        hBox.getChildren().addAll(btHome, btClean, btAdd, btHelp, btQuit);

        //set alignment
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }
    //Comments on merge sort efficiency
	/*
	 The way that merge sort works is by taking on a divide and conquer type approach. It recursively breaks down a problem into two or more 
	 sub-problems. After the two halves are sorted, the algorithm then merges them. 
	 
	 The space complexity for merge sort is O(n) but shouldn't be too relevant becuase users will not have large enough library 
	 for this to be relevant.
	 
	 The time complexity is the O(nLogn) in all cases.As the user libraries grow larger the complexity will 
	 not increase and will remain consistent. Eventually the program will slow down as libraries get larges but for small library will be ok.
        If a users library were to be extremely large, merge sort would be more efficient than; for example quick sort. This is because 
	 the worst case for merge sort is O(nLogn), whereas quick sorts is O(n).
	 */
	
}
