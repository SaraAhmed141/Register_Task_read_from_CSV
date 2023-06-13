package Register_csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import static org.junit.Assert.assertEquals;

public class Steps {

        private WebDriver driver;

    @Given("Open the website login page")
    public void setup() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        this.driver = new FirefoxDriver();
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1500, 1000));

        this.driver.get("https://codenboxautomationlab.com/registration-form/");
        Thread.sleep(5000);
    }

        @When("I enter registration details from the CSV file")
        public void iEnterRegistrationDetailsFromCSV() throws IOException, InterruptedException {
            String csvFile = "src/main/resources/registration_data.csv";
            String line;
            String csvSplitBy = ",";

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(csvSplitBy);

                    String firstName = data[0];
                    firstName = firstName.toUpperCase();
                    String lastName = data[1];
                    lastName = lastName.toUpperCase();
                    String email = data[2];
                    String mobileNumber = data[3];

                    WebElement firstNameInput = driver.findElement(By.cssSelector("#nf-field-17"));
                    WebElement lastNameInput = driver.findElement(By.cssSelector("#nf-field-18"));
                    WebElement emailInput = driver.findElement(By.cssSelector("#nf-field-19"));
                    WebElement mobileNumberInput = driver.findElement(By.cssSelector("#nf-field-20"));


                    firstNameInput.sendKeys(firstName);
                    lastNameInput.sendKeys(lastName);
                    emailInput.sendKeys(email);
                    mobileNumberInput.sendKeys(mobileNumber);
                    WebElement CheckListElement = driver.findElement(By.xpath("//*[@id=\"nf-label-class-field-23-1\"]"));
                    CheckListElement.click();

                    WebElement Registerbutton = driver.findElement(By.xpath("//*[@id=\"nf-field-15\"]"));
                    Registerbutton.click();


                    WebElement section = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[1]/div/div[4]/form/div/div[2]"));
                    File src = section.getScreenshotAs(OutputType.FILE);
                    File file = new File(".\\FWD\\GetGroup_CSV\\Screenshot\\Img3.png");
                    FileUtils.copyFile(src, file);

                }

            }

        }


    @And("I should see on the page")
    public void i_should_see_on_the_page() throws InterruptedException {

        Thread.sleep(5000);

        WebElement element = driver.findElement(By.className("nf-response-msg"));
        String elementText = element.getText();

        System.out.println(elementText);
        assertEquals(elementText, "Your registration is completed. We will contact with you soon. Thank you !");

    }

    @Then("I take a screenshot")
    public void iTakeAScreenshot() throws IOException {


        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destination = Path.of(".\\FWD\\GetGroup_CSV\\Screenshots", "screenshot4.png");
        try {
            Files.createDirectories(destination.getParent());
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}

