package com.wind;

import com.wind.util.DbManager;

public class AppMain {
	
	public static void main(String[] args) {
		DbManager db = DbManager.getDb();
		db.addCart("src/data/Input2 - Sheet1.csv");
	}
}
