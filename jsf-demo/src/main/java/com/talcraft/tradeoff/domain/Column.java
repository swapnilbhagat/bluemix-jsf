package com.talcraft.tradeoff.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {

	private String key;

	private String type;

	private String goal;

	@JsonProperty(value = "full_name")
	private String fullName;

	@JsonProperty(value = "is_objective")
	private Boolean isObjective;

	private String format;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsObjective() {
		return isObjective;
	}

	public void setIsObjective(Boolean isObjective) {
		this.isObjective = isObjective;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
