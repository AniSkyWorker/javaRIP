package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainServletTest {

    private ChromeDriver webDriver = new ChromeDriver();

    public static void main(String[] args) {
        MainServletTest mainServletTests = new MainServletTest();
        mainServletTests.TestNameField();
        mainServletTests.TestSoundingBoardStuffField();
        mainServletTests.TestPriceField();
        mainServletTests.TestDataField();

        mainServletTests.TestCantAddWithoutRequired();
        mainServletTests.CanAddGuitarWhenHasAllRequiredFields();
    }

    private void TestCantAddWithoutRequired()
    {
        CheckAddGuitar("Gibson", "Plastic", "", "10.05.2012", false);
        CheckAddGuitar("", "Plastic", "1234", "10.05.2015", false);
        CheckAddGuitar("Gibson", "Plastic", "123", "", false);
    }

    private void CanAddGuitarWhenHasAllRequiredFields()
    {
        CheckAddGuitar("Gibson", "Plastic", "123", "10.05.2012", true);
        CheckAddGuitar("Gibson", "", "123", "", true);
    }

    private void TestNameField()
    {
        OpenAddForm();
        SetInputInfo("name", "Gibson", "Gibson");
        SetInputInfo("name", "1233213", "1233213");
        SetInputInfo("name", "20.10.2017", "20.10.2017");
        SetInputInfo("name", "", "");
    }

    private void TestSoundingBoardStuffField()
    {
        OpenAddForm();
        SetInputInfo("board_stuff", "Mahagoni", "Mahagoni");
        SetInputInfo("board_stuff", "Purple plastic by McGilan 1997"
                , "Purple plastic by McGilan 1997");
        SetInputInfo("board_stuff", "20.10.2017", "20.10.2017");
        SetInputInfo("board_stuff", "", "");
    }

    private void TestPriceField()
    {
        OpenAddForm();
        SetInputInfo("price", "Mahagoni", "");
        SetInputInfo("price", "Purple plastic by McGilan 1997"
                , "");
        SetInputInfo("price", "12312312", "12312312");
        SetInputInfo("price", "1234567891011112", "123456789");
        SetInputInfo("price", "-5", "");
        SetInputInfo("price", "0", "0");
        SetInputInfo("price", "20.10.2012", "");
    }

    private void TestDataField()
    {
        OpenAddForm();
        SetInputInfo("manufactureDate", "Mahagoni", "");
        SetInputInfo("manufactureDate", "Purple plastic by McGilan 1997"
                , "");
        SetInputInfo("manufactureDate", "12312312", "");
        SetInputInfo("manufactureDate", "1234567891011112", "");
        SetInputInfo("manufactureDate", "20.10.2012", "20.10.2012");
    }

    private void OpenAddForm()
    {
        webDriver.get("http://localhost:8080/MainServlet");
    }

    private void CheckAddGuitar(String name, String soundStuff, String price, String manufactureDate, Boolean isCorrect)
    {
        SetInputInfo("name", name, name);
        SetInputInfo("board_stuff", soundStuff, soundStuff);
        SetInputInfo("price", price, price);
        SetInputInfo("manufactureDate", manufactureDate, manufactureDate);

        WebElement addButton = webDriver.findElement(By.id("addButton"));
        addButton.click();

        if(webDriver.findElements(By.cssSelector("input:invalid")).isEmpty() && !isCorrect)
        {
            throw new Error("Has invalid argument!");
        }
    }

    private void SetInputInfo(String elementId, String info, String excpectedInfo) {
        WebElement inputElem = webDriver.findElement(By.xpath("//input[@name='" + elementId + "']"));
        inputElem.sendKeys(info);
        String currentText = inputElem.getAttribute("value");
        if (!currentText.equals(excpectedInfo)) {
            WebElement addButton = webDriver.findElement(By.id("addButton"));
            addButton.click();
            if (inputElem.getAttribute("invalid") != null) {
                throw new Error("Expected " + excpectedInfo + "but " + currentText + "given!");
            }
            return;
        }

        if (!currentText.isEmpty()) {
            inputElem.clear();
        }
    }
}