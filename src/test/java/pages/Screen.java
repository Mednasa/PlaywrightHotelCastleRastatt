package pages;

import com.microsoft.playwright.Page;
import com.sun.net.httpserver.Headers;
import pages.getir.DialogContent;

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


