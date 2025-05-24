package utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class ReusableMethods extends Hooks {

    public void newPage(Locator element) {

        Page newTab = page.context().waitForPage(element::click);
        newTab.waitForLoadState();

    }
}
