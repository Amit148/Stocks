package CucumberTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public class Common {

	public static   WebDriver driver;
	public static  ArrayList<String> stockNames;
	static Map<String, Object> data = new HashMap <String, Object>();
	
	public static WebDriver getDriver() {
		return driver;
	}
	public static  void setDriver(WebDriver driver) {
		Common.driver = driver;
	}
	public static ArrayList<String> getStockNames() {
		return stockNames;
	}
	public static void setStockNames(ArrayList<String> stockNames) {
		Common.stockNames = stockNames;
	}
	public static Map<String, Object> getData() {
		return data;
	}
	
	public static void setdata(String name, Map map1) {
		// TODO Auto-generated method stub
		data.put(name, map1);
	}
	
	
	
}
