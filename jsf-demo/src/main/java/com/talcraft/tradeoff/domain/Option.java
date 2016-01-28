package com.talcraft.tradeoff.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {

	private String key;

	private String name;

	private Map<String, Object> values;

	@JsonProperty(value = "description_html")
	private String descriptionHtml;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public String getDescriptionHtml() {
		return descriptionHtml;
	}

	public void setDescriptionHtml(String descriptionHtml) {
		this.descriptionHtml = descriptionHtml;
	}

}
