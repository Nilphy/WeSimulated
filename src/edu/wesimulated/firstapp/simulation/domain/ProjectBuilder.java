package edu.wesimulated.firstapp.simulation.domain;

import edu.wesimulated.firstapp.model.ProjectData;

public class ProjectBuilder {

	public static Project createFromProjectData(ProjectData projectData, SimulatorFactory factory) {
		Project project = factory.makeProject();
		project.populateFromProjectData(projectData, factory);
		return project;
	}

}
