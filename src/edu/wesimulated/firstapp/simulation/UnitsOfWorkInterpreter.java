package edu.wesimulated.firstapp.simulation;

public class UnitsOfWorkInterpreter {

	public static float uowToMilis(Integer unitsOfWork) {
		return unitsOfWork /* workToDo */ * 60 /* costInMinutes/workToDoUnit */ * 60  /* second/minute */ * 1000 /* Millisecond/second */;
	}

	public static float milisToUow(float milis) {
		return milis /* millisecond */ / 1000 /* seconds/millisecond */ / 60 /* minute/second */ / 60 /* workToDoUnit/minute */;
	}
}
