import java.io.*;
import java.util.*;
public class rubine{
public static void main(String args[]){
		
		System.out.println("Reading File from Java code");
		
		
			//file path
			String filePath = "letters-txt/";
			prepareCSV();
			for(int i=0;i<26;i++){
			filePath = "letters-txt/";
			filePath += (char)('a' + i) + "/";
			for(int j = 1 ; j<21 ; j++){
	       //Name of the file
	       String fileName=(char)('a' + i) + "_" + j ;
	       try{

	          //Create object of FileReader
	          FileReader inputFile = new FileReader(filePath + fileName + ".txt");

	          //Instantiate the BufferedReader Class
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          //Variable to hold the one line data
	          String line;
	          ArrayList<point> points = new ArrayList<point>();
	          // Read file line by line and print on the console
	          while ((line = bufferReader.readLine()) != null)   {
	            System.out.println(line);
	            initializePoint(points,line);
	            
	            
	          }
	          //Close the buffer reader
	          bufferReader.close();
	          
	          extractFeatures exFeatures = new extractFeatures(points);
	          exFeatures.removeDuplicates();
	          exFeatures.execute();
	          exFeatures.writeToCSV(String.valueOf((char)('a' + i)));
	          
	       }catch(Exception e){
	          System.out.println("Error while reading file line by line:" + e.getMessage());                      
	       }
			}
			}
		
		
	}
/*	public static void main(String args[]){
		
		System.out.println("Reading File from Java code");
		
		
			//file path
			String filePAth = "sample-txt/";
			prepareCSV();
			for(int i=1;i<9;i++){
	       //Name of the file
	       String fileName="shape" + i + ".txt";
	       try{

	          //Create object of FileReader
	          FileReader inputFile = new FileReader(filePAth + fileName);

	          //Instantiate the BufferedReader Class
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          //Variable to hold the one line data
	          String line;
	          ArrayList<point> points = new ArrayList<point>();
	          // Read file line by line and print on the console
	          while ((line = bufferReader.readLine()) != null)   {
	            System.out.println(line);
	            initializePoint(points,line);
	            
	            
	          }
	          //Close the buffer reader
	          bufferReader.close();
	          
	          extractFeatures exFeatures = new extractFeatures(points);
	          exFeatures.removeDuplicates();
	          exFeatures.execute();
	          exFeatures.writeToCSV(fileName);
	          
	       }catch(Exception e){
	          System.out.println("Error while reading file line by line:" + e.getMessage());                      
	       }
			}
		
		
	}*/
	
	static void initializePoint(ArrayList<point> points,String line){
		
		String arr[] = line.split(",");
 		int x= Integer.parseInt(arr[0].trim());
 		int y= Integer.parseInt(arr[1].trim());
 		String t[] = arr[2].split(";");
 		long time = Long.parseLong(t[0].trim());
        point p =new point(x,y,time);
        points.add(p);
	}
	
	static void prepareCSV(){
		
		try {
			 PrintWriter pw = new PrintWriter("letters.csv");
		     StringBuilder sb = new StringBuilder();
		     //sb.append("class");
		     for(int i=1 ;i<=13 ; i++){
			     sb.append("f" + i);
			     sb.append(",");
		     }
		     sb.append("class");
		     pw.write(sb.toString());
		     pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}