package abstractClasses.page;

import abstractClasses.logger.LoggerManager;
import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.WebDriverWaiter;

import java.util.List;

import static driver.DriverManager.getDriver;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public abstract class AbstractPage extends WebDriverWaiter {

    private String pageUrl;
    private String pageUrlPattern;

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String setPageUrlPattern(String pageUrlPattern) {
        return this.pageUrlPattern = pageUrlPattern;
    }

    public String getPageUrlPattern() {
        return pageUrlPattern;
    }

    public boolean checkUrl() {
        boolean result = pageUrl.equals(getDriver().getCurrentUrl());
        if (!result && isNotEmpty(pageUrlPattern)) {
            return getDriver().getCurrentUrl().matches(pageUrlPattern);
        }
        return result;
    }

    public void openWebsiteUrl(String url) {
        DriverManager.getDriver().get(url);
    }    
    
    
}
