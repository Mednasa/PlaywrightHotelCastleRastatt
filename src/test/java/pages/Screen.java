package pages;

import com.microsoft.playwright.Page;
import pages.POM.DialogContent;

public class Screen {
    private Page page;
    private DialogContent dialogContent;


    public Screen() {
    }

    public Screen(Page page) {
        this.page = page;
    }

    public DialogContent dialogContent() {

        if (dialogContent == null) {
            dialogContent = new DialogContent(page);
        }
        return dialogContent;

    }






}


