package org.hm.tabuSearch;
public class TS {
	final static int TS_SIZE = 10;
	final static int TS_ITERATIONS = 100000;

	TSP mthd_f;
	double targetFun; 

	double targetX[]; 
	String tsList[]; 
	int listPoint;

	public TS() {
		mthd_f = new TSP();
		if (mthd_f.getLen() == 0)
			mthd_f.init();
		targetX = new double[mthd_f.getLen()];
		tsList = new String[TS_SIZE];
		listPoint = 0;
	}

	public TS(TSP nMthd) {
		mthd_f = nMthd;
		if (mthd_f.getLen() == 0)
			mthd_f.init();
		targetX = new double[mthd_f.getLen()];
		tsList = new String[TS_SIZE];
		listPoint = 0;
	}

	void change() {
		tsList[listPoint] = mthd_f.getChange();
		listPoint = (listPoint + 1) % TS_SIZE;
	}

	Boolean findTaboo(String strChange) {
		Boolean rslt = false;
		for (int i = 0; i < TS_SIZE; i++) {
			if (strChange.equalsIgnoreCase(tsList[i]) == true) {
				rslt = true;
				break;
			}
		}
		return rslt;
	}

	public String toString() {
		String result = "\n==TS==> FBEST:" + targetFun + "\nAnd the BEST X is: ";
		for (int i = 0; i < mthd_f.getLen(); i++) {
			result += (" [" + i + "]=" + targetX[i]);
		}
		return result;
	}

	public void ts() {
		targetFun = mthd_f.f();
		mthd_f.getCopyX(targetX);

		double lastX[] = new double[mthd_f.getLen()];
		mthd_f.getCopyX(lastX);
		double lastFun = mthd_f.f();

		for (int i = 0; i < TS_ITERATIONS; i++) {
			mthd_f.getNextX();
			double tmpFun = mthd_f.f();

			if ( (tmpFun < lastFun && (findTaboo(mthd_f.getChange()) == false)) 
					|| (tmpFun < targetFun)) { 
				if (tmpFun < targetFun) {
					targetFun = tmpFun;
					mthd_f.getCopyX(targetX);
				}
				change();
				mthd_f.getCopyX(lastX);
				lastFun = tmpFun;
			} else {
				mthd_f.setX(lastX);
			}
		}
	}

}
