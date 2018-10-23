package com.lx.demo.readinglist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * 下载最新的chrome驱动， 浏览器也是最新，驱动版本与浏览器版本对应
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChromeWebTest {
    private static ChromeDriver browser;

    @Value("${local.server.port}")
    private int port;
    @BeforeClass
    public static void openBrowser() {
        //if you didn't update the Path system variable to add the full directory path to the executable as above mentioned then doing this directly through code
        //http://chromedriver.storage.googleapis.com/index.html?path=70.0.3538.67/
        System.setProperty("webdriver.chrome.driver", "D:\\applications\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterClass
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void addBookToEmptyList() {
        String baseUrl = "http://localhost:" + port + "/zhangsan";
        browser.get(baseUrl);
        System.out.println(browser.findElementByTagName("div").getText());
        assertEquals("You have no books in your book list",browser.findElementByTagName("div").getText());
        browser.findElementByName("title")
                .sendKeys("BOOK TITLE");
        browser.findElementByName("author")
                .sendKeys("BOOK AUTHOR");
        browser.findElementByName("isbn")
                .sendKeys("1234567890");
        browser.findElementByName("description")
                .sendKeys("DESCRIPTION");
        browser.findElementByTagName("form")
                .submit();
        WebElement dl =
                browser.findElementByCssSelector("dt.bookHeadline");
//        System.out.println(dl.getText());
        assertEquals("BOOK TITLE BOOK AUTHOR (ISBN: 1234567890)",
                dl.getText());
        WebElement dt =
                browser.findElementByCssSelector("dd.bookDescription");
        assertEquals("DESCRIPTION", dt.getText());
    }
}
