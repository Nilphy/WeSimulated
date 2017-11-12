package edu.wesimulated.firstapp.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.domain.ManagementFramework;
import edu.wesimulated.firstapp.simulation.domain.ProjectContract;
import edu.wesimulated.firstapp.simulation.domain.Quality;
import edu.wesimulated.firstapp.simulation.domain.Team;
import edu.wesimulated.firstapp.simulation.domain.Technology;

public class ThingsWithoutAUi {

	public static final int RANDOM_SEED = 20;
	public static final int MIN_ANTIQUITY_IMMESSAGE_IN_MINUTES = 15;
	public static final int MED_ANTIQUITY_IMMESSAGE_IN_MINUTES = 60;
	public static final int MIN_ANTIQUITY_EMAIL_IN_MINUTES = 2 * 60;
	public static final int MED_ANTIQUITY_EMAIL_IN_MINUTES = 24 * 60;
	public static final int MIN_ANTIQUITY_FACE_TO_FACE_QUESTION_IN_HOURS = 1;
	public static final int MED_ANTIQUITY_FACE_TO_FACE_QUESTION_IN_HOURS = 8;
	public static final int MIN_ANTIQUITY_SQUEALER_IN_MINUTES = 15;
	public static final int MED_ANTIQUITY_SQUEALER_IN_MINUTES = 60;
	public static final int MIN_AMOUNT_IMMESAAGE = 5;
	public static final int MED_AMOUNT_IMMESSAGE = 10;
	public static final int MIN_AMOUNT_EMAIL = 10;
	public static final int MED_AMOUNT_EMAIL = 30;
	public static final int MID_AMOUNT_FACE_TO_FACE_QUESTION = 1;
	public static final int MED_AMOUNT_FACE_TO_FACE_QUESTION = 5;
	public static final int MIN_AMOUNT_SQUEALER = 5;
	public static final int MED_AMOUNT_SQUEALER = 15;
	private static Team teamInstance;

	private static Team buildTeam() {
		Team team = new Team();
		team.setName("The team");
		return team;
	}

	public static Team getTeamInstance() {
		if (teamInstance == null) {
			teamInstance = buildTeam();
		}
		return teamInstance;
	}

	public static ProjectContract buildContract(Date initialDate, Date finalDate) {
		ProjectContract contract = new ProjectContract();
		contract.setFinalDate(finalDate);
		contract.setInitialDate(initialDate);
		contract.setQuality(ThingsWithoutAUi.buildQuality());
		contract.setTecnologies(ThingsWithoutAUi.buildTechnologies());
		return contract;
	}

	private static Collection<Technology> buildTechnologies() {
		Collection<Technology> technologies = new ArrayList<>();
		technologies.add(ThingsWithoutAUi.buildPhpTechnology());
		technologies.add(ThingsWithoutAUi.buildJavaTechnology());
		technologies.add(ThingsWithoutAUi.buildJavaScriptTechonology());
		return technologies;
	}

	private static Technology buildJavaScriptTechonology() {
		Technology technology = new Technology();
		technology.setLearningCurveModOne(Technology.JAVASCRIPT_LEARNING_CURVE);
		technology.setMonthsInTheIndustry(DateUtils.calculateDifferenceInMonths(Technology.JAVASCRIPT_START_DATE, new Date()));
		technology.setVerbosityModOne(Technology.JAVASCRIPT_VERBOSITY);
		return technology;
	}

	private static Technology buildJavaTechnology() {
		Technology technology = new Technology();
		technology.setLearningCurveModOne(Technology.JAVA_LEARNING_CURVE);
		technology.setMonthsInTheIndustry(DateUtils.calculateDifferenceInMonths(Technology.JAVA_START_DATE, new Date()));
		technology.setVerbosityModOne(Technology.JAVA_VERBOSITY);
		return technology;
	}

	private static Technology buildPhpTechnology() {
		Technology technology = new Technology();
		technology.setLearningCurveModOne(Technology.PHP_LEARNING_CURVE);
		technology.setMonthsInTheIndustry(DateUtils.calculateDifferenceInMonths(Technology.PHP_START_DATE, new Date()));
		technology.setVerbosityModOne(Technology.PHP_VERBOSITY);
		return technology;
	}

	private static Quality buildQuality() {
		Quality quality = new Quality();
		quality.setCoverage(Quality.COVERAGE);
		quality.setPercentajeOfCriticalBugs(Quality.PERCENTAJE_CRITICAL_BUGS);
		quality.setPercentajeOfMinorBugs(Quality.PERCENTAJE_MINOR_BUGS);
		return quality;
	}

	public static ManagementFramework buildManagementFramework() {
		return ManagementFramework.createScrum();
	}
}
