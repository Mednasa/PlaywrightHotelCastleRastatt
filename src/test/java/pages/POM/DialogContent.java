package pages.POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.options.AriaRole.*;



public class DialogContent {

    private Page page;
    public Locator englishIcon;
    public Locator acceptCookies;
    public Locator checkInWidget;
    public Locator checkOutWidget;
    public Locator checkIndateList;
    public Locator checkOutdateList;
    public Locator checkInYearNames;
    public Locator checkoutYearNames;
    public Locator checkInMonthNames;
    public Locator checkInPrevButton;
    public Locator goButton;
    public Locator hotelCastleRastattText;
    public Locator messageContainer;
    public Locator availableRoom;
    public Locator spinnerContainer;
    String staticText="Es tut uns leid, aber für das angegebene Datum sind keine Unterkünfte verfügbar! Bitte ändern Sie  Ihre Eingaben oben im Suchfeld. Für weitere Informationen wenden Sie sich bitte direkt an die Unterkunft unter der Tel.-Nr: +49 72221590750";


    public DialogContent(Page page) {
        this.page = page;
        this.englishIcon = page.getByAltText("Englisch").last();
        this.acceptCookies = page.getByRole(BUTTON,new Page.GetByRoleOptions().setName("OK"));
        this.checkInWidget = page.locator("[name='widget_date']");
        this.checkOutWidget = page.locator("[name='widget_date_to']");
        this.checkIndateList = page.locator("(//*[starts-with(@class,'pika-single')])[1]//td[@data-day]");
        this.checkOutdateList = page.locator("(//*[starts-with(@class,'pika-single')])[2]//td[@data-day]");
        this.checkInYearNames = page.locator("(//select[@class='pika-select pika-select-year'])[1]//option");
        this.checkoutYearNames = page.locator("(//select[@class='pika-select pika-select-year'])[2]//option");
        this.checkInMonthNames = page.locator("(//select[@class='pika-select pika-select-month'])[1]//option");
        this.checkInPrevButton = page.locator("(//button[starts-with(@class,'pika-prev')])[1]");
        this.goButton = page.locator("[class='submit_link']");
        this.hotelCastleRastattText = page.getByRole(HEADING,new Page.GetByRoleOptions().setName("Hotel Castle Rastatt"));
        this.messageContainer = page.getByText(staticText).first();
        this.availableRoom = page.getByText("Mindestaufenthalt").first();
        this.spinnerContainer = page.getByRole(TEXTBOX,new Page.GetByRoleOptions().setName("Telefon "));
    }



}
