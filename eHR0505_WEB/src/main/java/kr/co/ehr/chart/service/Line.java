package kr.co.ehr.chart.service;

import kr.co.ehr.cmn.DTO;

public class Line extends DTO {

	private String year;
	private double sales;
	private double expenses;
	
	public Line() {}

	public Line(String year, double sales, double expenses) {
		super();
		this.year = year;
		this.sales = sales;
		this.expenses = expenses;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

	public double getExpenses() {
		return expenses;
	}

	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	@Override
	public String toString() {
		return "Line [year=" + year + ", sales=" + sales + ", expenses=" + expenses + ", toString()=" + super.toString()
				+ "]";
	}
	
}
