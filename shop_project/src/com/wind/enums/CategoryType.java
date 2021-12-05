package com.wind.enums;

public enum CategoryType {

	ESSENTIALS("Essentials", 3),
	LUXURY("Luxury", 4),
	MISC("Misc", 6),
	;
	
	private String name;
	
	private Integer limit;

	private CategoryType(String name, Integer limit) {
		this.name = name;
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public Integer getLimit() {
		return limit;
	}
	
	public boolean equalName(String name) {
		return this.name.equals(name);
	}
}
