package Learning.Stocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;








public class Starter {
		
	public static Logger log = LogManager.getLogger(Starter.class.getName());
	
	//String driverpath = "\"C:\\Users\\amiti\\Downloads\\chromedriver_win321.exe\"";
	//String datapath = "C:\\Users\\amiti\\Downloads\\stocks0812.xls";
	
	public static void main(String[] args) {
		//public void start() {
		WebDriver driver = new ChromeDriver();
		HashMap stockmap = new HashMap<String, ArrayList>();
		//String[] stockname = { "SBIN", "TCS", "NOVOCO" };
	/*	ExtentTest test;
		ExtentReports report;
        report = new ExtentReports("C:\\Users\\amiti\\Downloads\\ExtentReportResults.html");*
        test = report.startTest("ExtentDemo");*/
		//DOMConfigurator.configure("log4j2.properties");
		ArrayList <String> stocklist =readexcel(args[1]);
		//log.info("Excel has read");
		ChromeSetup(driver,args[0]);
		stockmap = readData(stocklist,driver,stockmap);
	//	log.info("Started Processing");
	//	print(stockmap, test);
		print(stockmap);
		//log.info("Printed");
		close(driver);
		//log.info("Closedz");
		
		
	}
	public static  void ChromeSetup(WebDriver driver,String driverpathr) {
			
			System.setProperty("WebDriver.chrome.driver", driverpathr);

			driver.manage().window().maximize();
			driver.get("https://www.tickertape.in/");
			
			
	}
		
	
	public static ArrayList readexcel(String datapath) {
		ArrayList array2 = new ArrayList<String>();
		
		try {
			
			File file = new File(datapath);
			FileInputStream fis = new FileInputStream(file);
			Workbook stocklist =null;
			stocklist = new HSSFWorkbook (fis);
			org.apache.poi.ss.usermodel.Sheet sheetname =stocklist.getSheetAt(0);
			int rownum = sheetname.getLastRowNum() - sheetname.getFirstRowNum();
			for(int i =2;i<=rownum;i++) {
				String stockName =(sheetname.getRow(i).getCell(0).getStringCellValue());
				array2.add(stockName);
				
				//log.info("Stock added"+ stockName);
				
			}
		//	System.out.println(array2);
		
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("error ");
		}
		return array2;
		
	}
	
	public static HashMap readData(ArrayList <String> stocklist ,WebDriver driver,HashMap stockmap) {
		

		for (String name : stocklist) {
			// ArrayList array1 = new ArrayList<>();
			Map map1 = new LinkedHashMap<String, String>();
			/// stock search function

			driver.findElement(By.xpath("//*[@id=\"search-stock-input\"]")).sendKeys(name);
			try {
				Thread.sleep(2000);
				WebElement selectbox = driver.findElement(By.xpath("//li[@id ='react-autowhatever-1-section-0-item-0']"));
				
				if(selectbox.isDisplayed())
				{
					driver.findElement(By.xpath("//li[@id ='react-autowhatever-1-section-0-item-0']")).click();
					Thread.sleep(3000);
					String name1 = driver.findElement(By.xpath("//div[contains(@class,'asset-name')]/h1")).getAttribute("innerHTML");
					
					
					
					
					String price = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/div/div[1]/div/div[1]/div[3]/span[1]")).getText();
					map1.put(name1, price);

					String Perf = driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Performance')][1]")).getText();
					String PerfVal = driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Performance')][1]/following-sibling::span")).getText();
					map1.put(Perf, PerfVal);
					String Valuation =driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Valuation')][1]")).getText();
					String ValuationVal =driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Valuation')][1]/following-sibling::span")).getText();
					map1.put(Valuation, ValuationVal);
					String Growth=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Growth')][1]")).getText();
					String GrowthVal=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Growth')][1]/following-sibling::span")).getText();
					map1.put(Growth, GrowthVal);
					String Profitability=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Profitability')][1]")).getText();
					String ProfitabilityVal=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Profitability')][1]/following-sibling::span")).getText();
					map1.put(Profitability, ProfitabilityVal);
					String Entry=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Entry point')][1]")).getText();
					String EntryVal=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Entry point')][1]/following-sibling::span")).getText();
					map1.put(Entry, EntryVal);
					String flags=driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Red flags')][1]")).getText();
					String flagsVal =driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Red flags')][1]/following-sibling::span")).getText();
					map1.put(flags, flagsVal);
					Thread.sleep(1000);
					String stockdetail =(name1+" = "+ price +"|"+Perf+" = "+ PerfVal+"|"+Valuation+" = "+ ValuationVal+"|"+Growth+" = "+ GrowthVal+"|"+Profitability+" = "+ ProfitabilityVal+"|"+Entry+" = "+ EntryVal+"|"+flags+" = "+ flagsVal);
				//System.out.println(stockdetail);
				//System.out.println("heelo");
					log.info(stockdetail);
					stockmap.put(name ,map1);
				}
				
				// array1.clear();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(NoSuchElementException e) {
				e.printStackTrace();
				continue;
			}

		}
		return  stockmap;
	}
		public static void print(HashMap stockmap) {
		Iterator<Map.Entry<String, LinkedHashMap<String,String>>>  itr = stockmap.entrySet().iterator();
		while(itr.hasNext()) {
			
			//Map.Entry pair = (Map.Entry)it.next();
			//System.out.println(itr.next());
			//String value = itr.next().toString();
			//log.info(pair.getKey() +"="+ pair.getValue());
		//	test.log(LogStatus.INFO, itr.next().toString());
		}
		}
		
		

		/*for (Object entry : stockmap.entrySet()) {
			
				System.out.println(entry);
				System.out.println(stockmap.get(entry));
				
			
		}*/
		public static void close(WebDriver driver) {
		driver.quit();
		}
		
	}

