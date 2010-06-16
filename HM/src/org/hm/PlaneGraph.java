package org.hm;
public class PlaneGraph extends Graph{
	
	public PlaneGraph (Points p){
		super(p.size());
		for (int i = 0; i < p.size(); i++)
			for (int j=0; j < p.size(); j++)
				connect(i, j, Math.sqrt(Math.pow(p.getCities().get(i).getx()-p.getCities().get(j).getx(), 2)+Math.pow(p.getCities().get(i).gety()-p.getCities().get(j).gety(), 2)));
	}		
}
