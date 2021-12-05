package com.wind.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wind.enums.CategoryType;
import com.wind.model.Card;
import com.wind.model.Project;

/**
 * Singleton
 * @author follow
 *
 */
public class DbManager {
	
	private static DbManager db;
	
	private final Map<String, String> itemCategory = new HashMap<>();
	
	private final Map<String, Integer> itemCount = new HashMap<>();
	
	private final Map<String, Double> itemPrice = new HashMap<>();
	
	private final List<Project> items = new ArrayList<>();
	
	private final List<Card> cards = new ArrayList<>();
	
	private final String TXT_FILE = "src/data/output.txt";
	
	private final String CSV_FILE = "src/data/output.csv";
	
	private DbManager() {
		this.initItem();
	}
	
	/**
	 * get db
	 * @return
	 */
	public static DbManager getDb() {
		if(db == null) {
			db = new DbManager();
		}
		return db;
	}
	
	
	public void initItem() {
		List<List<String>> list = CsvUtil.readCsv("src/data/Dataset.csv");
		list.forEach(line -> {
			try {
				if(line.contains("Category")) {
					return;
				}
				Project project = new Project();
				project.setCategory(line.get(0));
				project.setItem(line.get(1));
				project.setQuantity(Integer.parseInt(line.get(2)));
				project.setPrice(Double.parseDouble(line.get(3)));
				items.add(project);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
		items.forEach(p -> {
			String item = p.getItem();
			itemCategory.put(item, p.getCategory());
			itemCount.put(item, p.getQuantity());
			itemPrice.put(item, p.getPrice());
		});
	}
	
	public void addCart(String filename) {
		List<List<String>> list = CsvUtil.readCsv(filename);
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		double total = 0.0;
		for(int i = 1; i < list.size(); i++) {
			List<String> itemList = list.get(i);
			if(itemList.size() != 3) {
				CsvUtil.wirteTxt(TXT_FILE, "Please correct data,  items, quantity, payment card number");
				return;
			}
			String item = itemList.get(0);
			String category = itemCategory.get(item);
			if(category == null) {
				CsvUtil.wirteTxt(TXT_FILE, "Please correct Items, data:" + String.join(",", itemList));
				return;
			}
			if(CategoryType.ESSENTIALS.equalName(category)) {
				count1 ++;
			}else if(CategoryType.LUXURY.equalName(category)) {
				count2 ++;
			}else {
				count3 ++;
			}
			Integer count = itemCount.get(item);
			Double price = itemPrice.get(item);
			try {
				Integer quantity = Integer.parseInt(itemList.get(1));
				if(quantity > count) {
					CsvUtil.wirteTxt(TXT_FILE, "Please correct Quantity, data:" + String.join(",", itemList));
					return;
				}
				total += price * quantity;
			} catch (NumberFormatException e) {
				CsvUtil.wirteTxt(TXT_FILE, "Please correct Quantity, data:" + String.join(",", itemList));
				return;
			}
			String no = itemList.get(2);
			boolean sign = cards.stream().noneMatch(c -> c.equals(no));
			if(sign) {
				Card card = new Card();
				card.setNo(no);
				cards.add(card);
			}
		}
		if(CategoryType.ESSENTIALS.getLimit() < count1) {
			CsvUtil.wirteTxt(TXT_FILE, "restrict Essentials to a maximum of 3");
			return;
		}
		if(CategoryType.LUXURY.getLimit() < count2) {
			CsvUtil.wirteTxt(TXT_FILE, "restrict Luxury to a maximum of 4");
			return;
		}
		if(CategoryType.MISC.getLimit() < count3) {
			CsvUtil.wirteTxt(TXT_FILE, "restrict Misc to a maximum of 6");
			return;
		}
		list.add(Arrays.asList("total", String.valueOf(total)));
		CsvUtil.wirteCsv(CSV_FILE, list);
		
	}
}
