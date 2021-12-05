package com.wind.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtil {
	
	private CsvUtil() {
		
	}

	/**
	 * read csv
	 * @param filename
	 * @return
	 */
	public static List<List<String>> readCsv(String filename){
		List<List<String>> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
			String line = null;
			while((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				result.add(Arrays.asList(arr));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * write message
	 * @param filename
	 * @param message
	 */
	public static void wirteTxt(String filename, String message) {
		try (FileWriter fw = new FileWriter(filename)){
			fw.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * write csv
	 * @param filename
	 * @param data
	 */
	public static void wirteCsv(String filename, List<List<String>> data) {
		if(data == null || data.isEmpty()) {
			return;
		}
		try (FileWriter fw = new FileWriter(filename)){
			for(int i = 0; i < data.size(); i++) {
				fw.append(String.join(",", data.get(i)) + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
