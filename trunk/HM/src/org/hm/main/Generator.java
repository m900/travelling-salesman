package org.hm.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

public class Generator {
	public static void main(String[] args) {
		generatePoints(8);
		generatePoints(10);
		generatePoints(50);
		generatePoints(100);
	}
	
	private static void generatePoints(int numberOfPoints) {
		Random r = new Random();
		try {
			File aFile = new File("input"+numberOfPoints+".txt");
			Writer output = new BufferedWriter(new FileWriter(aFile));
			for (int i=0; i<numberOfPoints; i++){
				output.write(r.nextDouble()+":"+r.nextDouble()+System.getProperty("line.separator"));	
			}
			
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Succesfully enerated "+numberOfPoints +" points");
	}
}
