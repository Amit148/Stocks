package StepDefination;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import CucumberTest.Common;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import CucumberTest.Common;

public class TestSteps {

@Given ("^Webdriver set up and launching$")
public void WebdriverSetup() {
	WebDriver driver = new ChromeDriver();
	System.setProperty("WebCommon.driver.chrome.Common.driver", "\"C:\\Users\\amiti\\Downloads\\chromeCommon.driver_win321.exe\"");
	driver.manage().window().maximize();
	driver.get("https://www.tickertape.in/");
	
	Common.setDriver(driver);
}
	
	
@SuppressWarnings("unchecked")
@Given("^Fetch records from excel$")
public void fetch_records_from_excel() 
{
	ArrayList array2 = new ArrayList<String>();
	
	try {
		
		File file = new File("C:\\Users\\amiti\\Downloads\\stocks0812.xls");
		FileInputStream fis = new FileInputStream(file);
		//ArrayList stocklist =null;
		Workbook stocklist = new HSSFWorkbook (fis);
		org.apache.poi.ss.usermodel.Sheet sheetname =stocklist.getSheetAt(0);
		int rownum = sheetname.getLastRowNum() - sheetname.getFirstRowNum();
		for(int i =2;i<=rownum;i++) 
		{
			String stockName =(sheetname.getRow(i).getCell(0).getStringCellValue());
			array2.add(stockName);
			
			//log.info("Stock added"+ stockName);
			
		}
		Common.setStockNames(array2);
	}catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
}
@When("^Get latest data$")
public void get_latest_data () {
	 {
		 ArrayList <String> stocklist=Common.getStockNames();

			for (String name : stocklist ) {
				// ArrayList array1 = new ArrayList<>();
				Map map1 = new LinkedHashMap<String, String>();
				/// stock search function
				
				
				Common.driver.findElement(By.xpath("//*[@id=\"search-stock-input\"]")).sendKeys(name);
				try {
					Thread.sleep(2000);
					WebElement selectbox = Common.driver.findElement(By.xpath("//li[@id ='react-autowhatever-1-section-0-item-0']"));
					
					if(selectbox.isDisplayed())
					{
						Common.driver.findElement(By.xpath("//li[@id ='react-autowhatever-1-section-0-item-0']")).click();
						Thread.sleep(3000);
						String name1 = Common.driver.findElement(By.xpath("//div[contains(@class,'asset-name')]/h1")).getAttribute("innerHTML");
						
						
						
						
						String price = Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/div/div[1]/div/div[1]/div[3]/span[1]")).getText();
						map1.put(name1, price);

						String Perf = Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Performance')][1]")).getText();
						String PerfVal = Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Performance')][1]/following-sibling::span")).getText();
						map1.put(Perf, PerfVal);
						String Valuation =Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Valuation')][1]")).getText();
						String ValuationVal =Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Valuation')][1]/following-sibling::span")).getText();
						map1.put(Valuation, ValuationVal);
						String Growth=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Growth')][1]")).getText();
						String GrowthVal=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Growth')][1]/following-sibling::span")).getText();
						map1.put(Growth, GrowthVal);
						String Profitability=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Profitability')][1]")).getText();
						String ProfitabilityVal=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Profitability')][1]/following-sibling::span")).getText();
						map1.put(Profitability, ProfitabilityVal);
						String Entry=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Entry point')][1]")).getText();
						String EntryVal=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Entry point')][1]/following-sibling::span")).getText();
						map1.put(Entry, EntryVal);
						String flags=Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Red flags')][1]")).getText();
						String flagsVal =Common.driver.findElement(By.xpath("//*[@id=\"app-container\"]//p[contains(text(),'Red flags')][1]/following-sibling::span")).getText();
						map1.put(flags, flagsVal);
						Thread.sleep(1000);
						String stockdetail =(name1+" = "+ price +"|"+Perf+" = "+ PerfVal+"|"+Valuation+" = "+ ValuationVal+"|"+Growth+" = "+ GrowthVal+"|"+Profitability+" = "+ ProfitabilityVal+"|"+Entry+" = "+ EntryVal+"|"+flags+" = "+ flagsVal);
					//System.out.println(stockdetail);
					//System.out.println("heelo");
						//log.info(stockdetail);
						Common.setdata(name ,map1);
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
			
		}
    
}
@Then("^print details$")
public void print_details() {
	Map<String, Object> stockmap= Common.getData();
	Iterator<Entry<String, Object>>  itr = stockmap.entrySet().iterator();
	while(itr.hasNext()) {
		
		//Map.Entry pair = (Map.Entry)it.next();
		System.out.println(itr.next());
		//String value = itr.next().toString();
		//log.info(pair.getKey() +"="+ pair.getValue());
	//	test.log(LogStatus.INFO, itr.next().toString());
	}
	
    
}
@Then("^close broser$")
public void close_broser() {
   Common.getDriver().quit();
    
}
	
}
