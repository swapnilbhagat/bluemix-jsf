package com.talcraft.tradeoff.domain;

public class Problem {

	private String subject;

	private Column[] columns;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

}
