package com.example.SeleniumTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeleniumTestApplicationTests {


	private WebDriver driver;
	private ExtentReports extentReports;


	@BeforeEach
	void setUp(){
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.consultoriaglobal.com.ar/cgweb/?page_id=370");
		extentReports = new ExtentReports();
	}

	@Test
	void FormContactTest() {
		try{
			ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("Report/spark_report.html");

			extentReports.attachReporter(extentSparkReporter);

			ExtentTest looge = extentReports.createTest("Form Contact Test");


			driver.findElement(By.name("your-name")).sendKeys("Juna Martin");
			driver.findElement(By.name("your-email")).sendKeys("Email-invalido");
			driver.findElement(By.name("your-subject")).sendKeys("Asunto");
			driver.findElement(By.name("your-message")).sendKeys("Mensaje");
			driver.findElement(By.name("captcha-636")).sendKeys("123123123");
			driver.findElement(By.xpath("//input[@type = 'submit' and @value = 'Enviar']")).click();

			Thread.sleep(4000);


			if(driver.getPageSource().contains("La dirección e-mail parece inválida.")){
				System.out.println("Dirección de e-mail invalida");
				looge.log(Status.FAIL, "Dirección de e-mail inválida");
			} else {
				System.out.println("Dirección del e-mail valida");
				looge.log(Status.PASS , "Dirección de e-mail valida" );
			}


			System.out.println("****************************************************");
			System.out.println("- Valor cargado en el campo 'Nombre': Juna Martin");
			System.out.println("- Valor cargado en el campo 'E-mail': Email-inválido");
			System.out.println("- Valor cargado en el campo 'Asunto': Asunto");
			System.out.println("- Valor cargado en el campo 'Mensaje': Mensaje");
			System.out.println("- Valor cargado en el campo 'Captcha': 123123123");
			System.out.println("- Se ejecutó la acción de hacer clic en el botón 'Enviar'");
			System.out.println("****************************************************");


			driver.quit();
			extentReports.flush();
		} catch (Exception e){
			e.getMessage();
		}


	}

}
