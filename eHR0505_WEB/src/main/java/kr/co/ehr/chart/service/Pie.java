package kr.co.ehr.chart.service;

import kr.co.ehr.cmn.DTO;

public class Pie extends DTO {
	private String task;
	private int hoursPerDay;
	
	public Pie() {}

	public Pie(String task, int hoursPerDay) {
		super();
		this.task = task;
		this.hoursPerDay = hoursPerDay;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getHoursPerDay() {
		return hoursPerDay;
	}

	public void setHoursPerDay(int hoursPerDay) {
		this.hoursPerDay = hoursPerDay;
	}

	@Override
	public String toString() {
		return "Pie [task=" + task + ", hoursPerDay=" + hoursPerDay + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
