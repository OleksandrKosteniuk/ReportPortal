package hooks;

import driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScreenshootHook {
    private Date currentDate = new Date();
    private String screenshotFileName = currentDate.toString().replace(" ", "-").replace(":", "-");
    private File screenshotFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

    public void getScreenshot() throws IOException {
        FileUtils.copyFile(screenshotFile,new File(".//screenshot//"+screenshotFileName+".png"));
    }
}