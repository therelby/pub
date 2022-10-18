package framework.run.debug

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class Interview extends RunWeb {

    void test() {


        WebDriver driver = new ChromeDriver()

        driver.get("https://www.webstaurantstore.com/")

        String text = driver.findElement(By.xpath("//div[@id='title']")).getText()



    }


}
