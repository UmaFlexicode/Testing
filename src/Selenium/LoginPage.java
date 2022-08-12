package Selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class LoginPage {

	public static void main(String[] args) {
		public static WebDriver driver;
		String[][] data = null;

		@DataProvider(name = "loginData")
		public String[][] loginDataProvider() throws BiffException, IOException {
			System.out.println("Successfully");
			data = getExcelData();

			return data;
		}

		public String[][] getExcelData() throws BiffException, IOException {
			FileInputStream excel = new FileInputStream("E:\\Uma\\Automation Testing Excel\\TestDataN.xls");
			Workbook workbook = Workbook.getWorkbook(excel);
			Sheet sheet = workbook.getSheet(0);
			int rowCount = sheet.getRows();
			int columnCountSheet = sheet.getColumns();
			String testData[][] = new String[rowCount - 1][columnCountSheet];// 4 cross 2
			for (int i = 1; i < rowCount; i++) {
				for (int j = 0; j < columnCountSheet; j++) {
					System.out.println("First Time success");
					testData[i - 1][j] = sheet.getCell(j, i).getContents();
					System.out.println("Second Time success");
				}
			}

			System.out.println("TestData:" + testData);
			return testData;
		}

		@BeforeSuite
		public void beforeTest() {
			System.setProperty("webdriver.chrome.driver", "E:\\Uma\\seleniumdriver\\chromedriver.exe");
			driver = new ChromeDriver();
			
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			 
		}

		/*
		 * @AfterSuite public void afterTest() {
		 * 
		 * driver.quit(); }
		 */

		@Test(dataProvider = "loginData")

		public void loginWithBothcorrect(String uName, String pWord) throws InterruptedException {
			try {
				driver.get("http://169.38.82.130:8080/NRLM_UATWeb/#/auth");
//				driver.manage().window().maximize();
				//driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);

				WebElement userName = driver.findElement(By.name("username"));
				userName.sendKeys(uName);
				Thread.sleep(3000);
				WebElement passWord = driver.findElement(By.name("password"));
				passWord.sendKeys(pWord);
				Thread.sleep(3000);
				WebElement loginButton = driver.findElement(By.id("btnLogin"));
				loginButton.click();
				Thread.sleep(3000);

				System.out.println("Both correct");
			} catch (Exception e) {
				System.out.println("Error:" + e);
			}

	}

}
