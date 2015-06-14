import java.io.*;
import java.util.*;

/**
//	This class creates a list of keywords and a count of ho wmany times that keyword shows up in a csv file
**/
public class csvEventParser {

	String csvFile;
	private int linecount = 0;
	private String[][] events;
	private String[] keywords;	

	/**
	//	Initialize the array to the number of events found inside a csv and update each count ot start at 1
	//	@param N count of unique keywords
	**/
	public csvEventParser(String filename) throws FileNotFoundException {
		csvFile = filename;
	}

	/**
	//	Sums all events parsed from the csv file provided into a HashMap to calculate probabilites of nodes
	//	@param filename the csv file contatining frequency data
	**/
	public void parse() throws FileNotFoundException {
		Scanner sc = IO.createScanner(csvFile);
		linecount = IO.countLines(sc);

		System.out.println("Counted Lines: " + linecount);
		
		sc = IO.createScanner(csvFile);
		String[] split = IO.parseLine(sc.nextLine());
		int n = split.length;
		keywords = new String[n];
		events = new String[linecount - 1][n];

		// fill the keyword array with all keyword names
		for (int i = 0; i < n; i++) {
			addKeyword(split[i], i);
		}

		System.out.println("Starting parsing of data this may take awhile...");		


		int j = 0;
		while (sc.hasNext()){
			events[j] = sc.nextLine().split(",");
			j++;
		}
	}

	/**
	//	@return the index of the given 
	**/
	public int getKeywordIndex(String s) {
		for (int i = 0; i < keywords.length; i++) {
			if (s.equals(keywords[i])) return i;
		}
		return -1;
	}

	/**
	//	@param i index of keyword
	// 	@return the keyword at index i
	**/
	public String getKeywordAtIndex(int i) {
		return keywords[i];
	}

	/**
	//	@param s new keyword to add
	//	@param index typically first empty value in the keywords[]
	**/
	public void addKeyword(String s, int index) {
		keywords[index] = s;
	}

	/**
	//	getters and setters
	**/
	public String[][] getEvents() { return events; }
	public String[] getKeywords() { return keywords; }
	public int getLinecount() { return linecount; }
}