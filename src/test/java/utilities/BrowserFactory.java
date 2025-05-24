package utilities;


import com.microsoft.playwright.*;
import org.testng.ITestResult;

import java.util.Arrays;

public class BrowserFactory {

    private Playwright playwright;

    public BrowserFactory() {
        playwright = Playwright.create();
    }

    public Browser getBrowser(String browserName) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(Arrays.asList(new String[]{"--start-maximized"}));
        BrowserType browserType;
        switch (browserName.toLowerCase()) {
            case "chromium":
                browserType = playwright.chromium();
                break;
            case "firefox":
                browserType = playwright.firefox();
                break;
            case "safari":
                browserType = playwright.webkit();
                break;
            case "chrome":
                browserType = playwright.chromium();
                launchOptions.setChannel("chrome");
                break;
            case "edge":
                browserType = playwright.chromium();
                launchOptions.setChannel("msedge");
                break;
            default:
                String message = "Browser Name " + browserName + "specified in Invalid";
                message += "Please specify one of the supported browsers [firefox,safari,chrome].";
                throw new IllegalArgumentException(message);
        }
        return browserType.launch(launchOptions);
    }

    public BrowserContext createPageAndGetContext(Browser browser, ITestResult result) {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(null);

        BrowserContext context = browser.newContext(contextOptions);
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
                .setName(result.getMethod().getMethodName()));
        return context;

    }

}
