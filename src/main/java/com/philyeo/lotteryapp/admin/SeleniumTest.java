package com.philyeo.lotteryapp.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class SeleniumTest {

    protected WebDriver driver;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\DevTools\\geckodriver-v0.33.0-win64\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();

        driver.get("https://www.damacai.com.my/past-draw-result/");

        driver.findElement(By.id("datetimepicker1")).click();
        driver.findElement(By.xpath("/html/body/div[8]/div/a[1]")).click();

        WebElement datePicker = driver.findElement(By.className("ui-datepicker-calendar"));
//        List<WebElement> weekends = datePicker.findElements(By.cssSelector("td.ui-datepicker-week-end"));
//
//        for (WebElement element : weekends) {
//            // Perform actions with each element if required
//            System.out.println(element.getText());
//        }

        // td[data-handler='selectDay'] gets both weekends and weekdays
        List<WebElement> days = datePicker.findElements(By.cssSelector("td[data-handler='selectDay']"));

        for (WebElement element : days) {
            System.out.println(element.getText() + element.getAttribute("data-month") + element.getAttribute("data-year"));
        }
    }

}
