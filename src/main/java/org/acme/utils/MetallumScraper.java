package org.acme.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Metallum;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@ApplicationScoped
public class MetallumScraper {

    public Metallum getMetallumInfo(Metallum Metallum) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\augus\\minerae\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        Metallum metallumInfo = new Metallum();
        String url = Metallum.getDescription();
        driver.get(url);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement imageElement = driver.findElement(By.cssSelector("img.mw-file-element:first-child"));
            WebElement infoboxLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("th.infobox-label")));
            WebElement infoboxTable = infoboxLabel.findElement(By.xpath("./ancestor::table"));

            List<WebElement> rows = infoboxTable.findElements(By.tagName("tr"));
            for (WebElement row : rows) {
                List<WebElement> headerCells = row.findElements(By.tagName("th"));
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (headerCells.size() > 0 && cells.size() > 0) {
                    for (int i = 0; i < Math.min(headerCells.size(), cells.size()); i++) {
                        String label = headerCells.get(i).getText().trim();
                        String entry = cells.get(i).getText().trim();

                        // Remove the references from the entries
                        if (entry.matches(".*\\[\\d+\\].*")) {
                            entry = entry.replaceAll("\\[\\d+\\]", "").trim();
                        }

                        // Check for subscripts within the cell
                        List<WebElement> subscripts = cells.get(i).findElements(By.tagName("sub"));
                        if (!subscripts.isEmpty()) {
                            entry = cells.get(i).getAttribute("innerHTML").trim().replaceAll("<sup\\s+.*?</sup>", "");;
                        }

                        if (label.toLowerCase().startsWith("formula")) {
                            metallumInfo.setFormula(entry);
                        }
                        switch (label.toLowerCase()) {
                            case "category":
                                metallumInfo.setCategory(entry);
                                break;
                            case "crystal system":
                                metallumInfo.setCrystalSystem(entry);
                                break;
                            case "mohs scale hardness":
                                metallumInfo.setMohsScale(entry);
                                break;
                            case "color":
                                metallumInfo.setColor(entry);
                                break;
                            case "ultraviolet fluorescence":
                                metallumInfo.setUltravioletFluorescence(entry);
                                break;
                            case "other characteristics":
                                metallumInfo.setOtherProperties(entry);
                                break;
                        }

                        System.out.println(label + ": " + entry);
                    }
                }
            }
            metallumInfo.setImageUrl(imageElement.getAttribute("src"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return metallumInfo;
    }

//    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\augus\\minerae\\chromedriver-win64\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        Metallum metallumInfo = new Metallum();
//        String url = "https://en.wikipedia.org/wiki/Abernathyite";
//        driver.get(url);
//
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, 10);
//            WebElement imageElement = driver.findElement(By.cssSelector("img.mw-file-element:first-child"));
//            WebElement infoboxLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("th.infobox-label")));
//            WebElement infoboxTable = infoboxLabel.findElement(By.xpath("./ancestor::table"));
//
//            List<WebElement> rows = infoboxTable.findElements(By.tagName("tr"));
//            for (WebElement row : rows) {
//                List<WebElement> headerCells = row.findElements(By.tagName("th"));
//                List<WebElement> cells = row.findElements(By.tagName("td"));
//
//                if (headerCells.size() > 0 && cells.size() > 0) {
//                    for (int i = 0; i < Math.min(headerCells.size(), cells.size()); i++) {
//                        String label = headerCells.get(i).getText().trim();
//                        String entry = cells.get(i).getText().trim();
//
//                        // Remove the references from the entries
//                        if (entry.matches(".*\\[\\d+\\].*")) {
//                            entry = entry.replaceAll("\\[\\d+\\]", "").trim();
//                        }
//
//                        // Check for subscripts within the cell
//                        List<WebElement> subscripts = cells.get(i).findElements(By.tagName("sub"));
//                        if (!subscripts.isEmpty()) {
//                                entry = cells.get(i).getAttribute("innerHTML").trim().replaceAll("<sup\\s+.*?</sup>", "");;
//                        }
//
//                        if (label.toLowerCase().startsWith("formula")) {
//                            metallumInfo.setFormula(entry);
//                        }
//                        switch (label.toLowerCase()) {
//                            case "category":
//                                metallumInfo.setCategory(entry);
//                                break;
//                            case "crystal system":
//                                metallumInfo.setCrystalSystem(entry);
//                                break;
//                            case "mohs scale hardness":
//                                metallumInfo.setMohsScale(entry);
//                                break;
//                            case "color":
//                                metallumInfo.setColor(entry);
//                                break;
//                            case "ultraviolet fluorescence":
//                                metallumInfo.setUltravioletFluorescence(entry);
//                                break;
//                            case "other characteristics":
//                                metallumInfo.setOtherProperties(entry);
//                                break;
//                        }
//
//                        System.out.println(label + ": " + entry);
//                    }
//                }
//            }
//            metallumInfo.setImageUrl(imageElement.getAttribute("src"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            driver.quit();
//        }
//
//    }

}
