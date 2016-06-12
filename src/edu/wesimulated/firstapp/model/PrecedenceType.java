package edu.wesimulated.firstapp.model;

public enum PrecedenceType {
	FinishedToStart, StartedToFinish, FinishedToFinish, StartedToStart, IndependentTask;

	public String getImageName() {
		switch (this) {
		case FinishedToFinish:
			return "ff";
		case FinishedToStart:
			return "fs";
		case StartedToFinish:
			return "sf";
		case StartedToStart:
			return "ss";
		case IndependentTask:
			return "i";
		}
		throw new RuntimeException("If this happens start beleaving in god");
	}

	public String getTextForNode() {
		switch (this) {
		case FinishedToFinish:
			return "finish to finish: ";
		case FinishedToStart:
			return "finish to start: ";
		case StartedToFinish:
			return "start to finish: ";
		case StartedToStart:
			return "start to start: ";
		case IndependentTask:
			return "";
		}
		throw new RuntimeException("If this happens start beleaving in god");
	}
}
