package org.hm.tabuSearch;
public class TSP  {
	final static int LEN = 2000;
	final static int MAX_ROAD = 15;
	final static int MIN_ROAD = 1;
	final static int CHANGE_LEN = 3;
	final static int CHANGE_I = 1;
	final static int CHANGE_J = 2;
	int tspX[];
	double distance[][];
	int s_i;
	int s_j;

	private void swap() {
		int tmp = tspX[s_i];
		tspX[s_i] = tspX[s_j];
		tspX[s_j] = tmp;
	}
	
//	private int toInt(String str){
//		str.trim();
//		int len = str.length();
//		char ch[] = new char[len];
//		ch = str.toCharArray();
//		int result = 0;
//		for(int i=0;i<len;i++){
//			if(ch[i]>'9'||ch[i]<'0'){
//				result = 0;
//				break;
//			}
//			result = (result*10 + ch[i]-'0');
//		}
//		return result;
//	}

	private int rand(int minRand, int maxRand) {
		return (int) (Math.random() * (maxRand - minRand) + minRand);
	}
	
	public double f() {
		// TODO Auto-generated method stub
		double result = 0;
		for (int i = 0; i < LEN; i++) {
			result += (tspX[i] < tspX[(i + 1) % LEN] ? distance[tspX[i]][tspX[(i + 1)
					% LEN]]
					: distance[tspX[(i + 1) % LEN]][tspX[i]]);
		}
		return result;
	}

	public String getChange() {
		return (s_i>s_j) ? ("swap " + s_i + " and " + s_j):
			               ("swap " + s_j + " and " + s_i);
	}

	public void getCopyX(double[] x) {
		// TODO Auto-generated method stub
		for (int i = 0; i < LEN; i++) {
			x[i] = tspX[i];
		}

	}

	public int getLen() {
		// TODO Auto-generated method stub
		return LEN;
	}

	public void getNextX() {
		// TODO Auto-generated method stub
		s_i = rand(0, LEN);
		s_j = rand(0, LEN);
		while (s_j == s_i) {
			s_j = rand(0, LEN);
		}
		swap();

	}

	public void init() {
		// TODO Auto-generated method stub
		/*
		 * tspX = new int[LEN]; distance = new double[LEN][LEN]; int
		 * p[]={7,3,4,8,2,1,5,6}; int q[][]={{0,2,4,6,10,8,2,1},
		 * {0,0,2,5,9,4,8,7}, {0,0,0,1,2,7,12,11}, {0,0,0,0,1,7,14,5},
		 * {0,0,0,0,0,2,4,14}, {0,0,0,0,0,0,2,7}, {0,0,0,0,0,0,0,1},
		 * {0,0,0,0,0,0,0,0}}; for(int i=0;i<LEN;i++){ tspX[i]=p[i]-1; for(int
		 * j=0;j<LEN;j++){ distance[i][j]=q[i][j]; } }
		 */
		tspX = new int[LEN];
		int flagX[] = new int[LEN];
		distance = new double[LEN][LEN];
		for (int i = 0; i < LEN; i++) {
			flagX[i] = 0;
		}
		for (int i = 0; i < LEN; i++) {
			int t = rand(0, LEN);
			while (flagX[t] != 0) {
				t = rand(0, LEN);
			}
			tspX[i] = t;
			flagX[t] = 1;
		}
		for (int i = 0; i < LEN; i++) {
			for (int j = i + 1; j < LEN; j++) {
				distance[i][j] = rand(MIN_ROAD, MAX_ROAD);
			}
		}
	}

	public void setX(double[] x) {
		// TODO Auto-generated method stub
		for (int i = 0; i < LEN; i++) {
			tspX[i] = (int) x[i];
		}
	}

//	@Override
//	public void setChange(String strChange) {
//		// TODO Auto-generated method stub
//		String strChanges[] = new String[CHANGE_LEN];
//		strChanges = strChange.split(strChange,3);
//		s_i = toInt(strChanges[CHANGE_I]);
//		s_j = toInt(strChanges[CHANGE_J]);
//	}

	@Override
	public String toString() {
		String result;
		result = "X:";
		for (int i = 0; i < LEN; i++) {
			result += (" [" + i + "]:" + tspX[i]);
		}
		result += "\nDistance:\n";
		for (int i = 0; i < LEN; i++) {
			result += ("[" + i + "]");
			for (int j = 0; j < LEN; j++) {
				result += (" " + distance[i][j]);
			}
			result += "\n";
		}
		result += " and F:";
		result += f();
		return result;
	}

}
