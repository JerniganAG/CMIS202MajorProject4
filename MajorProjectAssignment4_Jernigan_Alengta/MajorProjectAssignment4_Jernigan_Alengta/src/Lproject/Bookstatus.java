// **********************************************
// Title:  Library Of Reigh
// Author: Alengta Jernigan
// Course Section: CMIS202-ONL1 (Seidel) Spring 2024
// File: Bookstatus.java
//getters and setters for book status
// **********************************************
package Lproject;

public class Bookstatus {
    //Declaring variables
    private String status; //book status
    
   //Constructors 
    public Bookstatus(String status){
       this.status = status;
        
    }
    
    public Bookstatus(){
        status = "";
    }
    //Mutators
    public void setStatus(String status) {//set book status
		this.status = status;
    }
    //Accessors
    public String getStatus() {
		return status;
	}
    //put into file format
    public String recordBook() {
        String recordStatus = getStatus();
    
    //make record to be added to file
    String record = ( recordStatus);
        return record;
       
}
}


