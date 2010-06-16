package org.hm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.hm.dataStructures.City;

public class Points{
	private int numberOfPoints;
	private List<City> cities = new ArrayList<City>();

	public Points (int numberOfPoints, String fileName){
		this.numberOfPoints = numberOfPoints;
		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				String[] split = strLine.split(":");
				City city = new City(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
				cities.add(city);
			}
			in.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public int size () {
		return numberOfPoints; 
	}

	public String toString(){
		String result="";
		for (int i = 0; i< numberOfPoints;i++)
			result += cities.get(i).getx()+":"+ cities.get(i).gety()+System.getProperty("line.separator");  
		return result;
	}
	
	public List<City> getCities() {
		return cities;
	}
}