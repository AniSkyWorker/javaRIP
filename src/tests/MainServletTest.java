package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * MainServlet tests object.
 */
public class MainServletTest {

  /**
   * Testing-browser driver.
   */
  private final transient ChromeDriver webDriver = new ChromeDriver();

  /**
   * Start MainServlet tests.
   * @param args Not used
   */
  public static void main(final String[] args) {
    final MainServletTest mainServletTests = new MainServletTest();
    mainServletTests.testNameField();
    mainServletTests.testSoundingBoardStuffField();
    mainServletTests.testPriceField();
    mainServletTests.testDataField();

    mainServletTests.testCantAddWithoutRequired();
    mainServletTests.canAddGuitarWhenHasAllRequiredFields();
  }

  private void testCantAddWithoutRequired() {
    checkAddGuitar("Gibson", "Plastic", "", "10.05.2012", false);
    checkAddGuitar("", "Plastic", "1234", "10.05.2015", false);
    checkAddGuitar("Gibson", "Plastic", "123", "", false);
  }

  private void canAddGuitarWhenHasAllRequiredFields() {
    checkAddGuitar("Gibson", "Plastic", "123", "10.05.2012", true);
    checkAddGuitar("Gibson", "", "123", "", true);
  }

  private void testNameField() {
    openAddForm();
    setInputInfo("name", "Gibson", "Gibson");
    setInputInfo("name", "1233213", "1233213");
    setInputInfo("name", "20.10.2017", "20.10.2017");
    setInputInfo("name", "", "");
  }

  private void testSoundingBoardStuffField() {
    openAddForm();
    setInputInfo("board_stuff", "Mahagoni", "Mahagoni");
    setInputInfo("board_stuff", "Purple plastic by McGilan 1997",
        "Purple plastic by McGilan 1997");
    setInputInfo("board_stuff", "20.10.2017", "20.10.2017");
    setInputInfo("board_stuff", "", "");
  }

  private void testPriceField() {
    openAddForm();
    setInputInfo("price", "Mahagoni", "");
    setInputInfo("price", "Purple plastic by McGilan 1997",
        "");
    setInputInfo("price", "12312312", "12312312");
    setInputInfo("price", "1234567891011112", "123456789");
    setInputInfo("price", "-5", "");
    setInputInfo("price", "0", "0");
    setInputInfo("price", "20.10.2012", "");
  }

  private void testDataField() {
    openAddForm();
    setInputInfo("manufactureDate", "Mahagoni", "");
    setInputInfo("manufactureDate", "Purple plastic by McGilan 1997",
        "");
    setInputInfo("manufactureDate", "12312312", "");
    setInputInfo("manufactureDate", "1234567891011112", "");
    setInputInfo("manufactureDate", "20.10.2012", "20.10.2012");
  }

  private void openAddForm() {
    webDriver.get("http://localhost:8080/MainServlet");
  }

  private void checkAddGuitar(final String name, final String soundStuff, final String price,
                              final String manufactureDate, final Boolean isCorrect) {
    setInputInfo("name", name, name);
    setInputInfo("board_stuff", soundStuff, soundStuff);
    setInputInfo("price", price, price);
    setInputInfo("manufactureDate", manufactureDate, manufactureDate);

    final WebElement addButton = webDriver.findElement(By.id("addButton"));
    addButton.click();

    if (webDriver.findElements(By.cssSelector("input:invalid")).isEmpty() && !isCorrect) {
      throw new Error("Has invalid argument!");
    }
  }

  private void setInputInfo(final String elementId, final String info, final String excpectedInfo) {
    final WebElement inputElem = webDriver.findElement(
        By.xpath("//input[@name='" + elementId + "']"));
    inputElem.sendKeys(info);
    final String currentText = inputElem.getAttribute("value");
    if (!currentText.equals(excpectedInfo)) {
      final WebElement addButton = webDriver.findElement(By.id("addButton"));
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