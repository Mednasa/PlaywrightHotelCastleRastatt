package testEx;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Screen;
import utilities.ReusableMethods;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static utilities.Hooks.page;

@Listeners(utilities.Hooks.class)
public class PomTest {
    ReusableMethods rm = new ReusableMethods();
    private Screen screen;
    LocalDate today = LocalDate.now();
    YearMonth currentMonth = YearMonth.now();
    int dayOfMonth = today.getDayOfMonth();
    String dayOfMont = String.valueOf(dayOfMonth);
    String year = String.valueOf(today.getYear());
    String month = String.valueOf(today.getMonth());
    List<Integer> checkInPreviousDay = new ArrayList<>();
    List<String> previousDayStr = new ArrayList<>();
    int lastDayOfMonth = currentMonth.lengthOfMonth();
    int randomCheckInDate = (int) (Math.random() * (lastDayOfMonth - dayOfMonth + 1)) + dayOfMonth;
    List<Integer> checkOutPreviousDays = new ArrayList<>();
    int randomCheckOutDate = (int) (Math.random() * (lastDayOfMonth - randomCheckInDate + 1)) + randomCheckInDate;
    String randomCheckInDateStr = String.valueOf(randomCheckInDate);
    String randomCheckOutDateStr = String.valueOf(randomCheckOutDate);
    String checkInDate = randomCheckInDateStr + "/" + String.format("%02d", today.getMonthValue()) + "/" + year;
    String checkOutDate = randomCheckOutDateStr + "/" + String.format("%02d", today.getMonthValue()) + "/" + year;


    @Epic("User Interface Tests")
    @Feature("Login Feature")
    @Story("Successful Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test for successful login with valid credentials")

    @Test
    void checkInCheckOutTest() {

        screen = new Screen(page);


        Allure.step("Navigate to the Hotel Castle Rastatt", () -> {
            screen.dialogContent().acceptCookies.click();
        });

        Allure.step("The customer clicks on the English language icon", () -> {
            screen.dialogContent().englishIcon.click();
        });

        Allure.step("The customer clicks on the check-in calendar icon", () -> {
            screen.dialogContent().checkInWidget.click();
        });

        Allure.step("Verify that today's date, month, and previous days cannot be selected", () -> {
            dayOfMonth = today.getDayOfMonth();
            dayOfMont = String.valueOf(dayOfMonth);
            year = String.valueOf(today.getYear());
            month = String.valueOf(today.getMonth());
            checkInPreviousDay = new ArrayList<>();
            previousDayStr = new ArrayList<>();

            for (int i = dayOfMonth; i > 0; i--) {
                checkInPreviousDay.add(i);
            }


            for (int i = 0; i < screen.dialogContent().checkIndateList.count(); i++) {

                if (checkInPreviousDay.get(i) < dayOfMonth) {
                    Assert.assertEquals(screen.dialogContent().checkIndateList.nth(i).getAttribute("class"), "is-disabled");
                    break;
                }
            }

            for (int i = 0; i < screen.dialogContent().checkIndateList.count(); i++) {
                if (screen.dialogContent().checkIndateList.nth(i).innerText().equals(dayOfMont)) {
                    Assert.assertEquals(screen.dialogContent().checkIndateList.nth(i).getAttribute("class"), "is-today is-selected");
                    break;
                }
            }

            for (int i = 0; i < screen.dialogContent().checkInMonthNames.count(); i++) {
                if (screen.dialogContent().checkInMonthNames.nth(i).innerText().equals(month)) {
                    Assert.assertEquals(screen.dialogContent().checkInMonthNames.nth(i).innerText(), month);
                    Assert.assertEquals(screen.dialogContent().checkInPrevButton.nth(i).getAttribute("class"), "pika-prev is-disabled");
                    break;
                }
            }

            for (int i = 0; i < screen.dialogContent().checkInYearNames.count(); i++) {
                if (screen.dialogContent().checkInYearNames.nth(i).innerText().equals(year)) {
                    boolean isSelected = (boolean) screen.dialogContent().checkInYearNames.nth(i).evaluate("el => el.selected");
                    Assert.assertTrue(isSelected);
                    break;
                }
            }
        });

        Allure.step("The customer enters the check-in date", () -> {
            System.out.println(randomCheckInDate);
            screen.dialogContent().checkIndateList.nth(randomCheckInDate - 1).click();

        });

        Allure.step("The customer clicks on the check-out calendar icon", () -> {
            screen.dialogContent().checkOutWidget.click();

        });

        Allure.step("Verify that today's date, month, previous days, and entry date cannot be selected", () -> {

            for (int i = randomCheckInDate; i > 0; i--) {
                checkOutPreviousDays.add(i);
            }

            for (int i = 0; i < screen.dialogContent().checkOutdateList.count(); i++) {

                if (checkOutPreviousDays.get(i) < randomCheckInDate) {
                    Assert.assertEquals(screen.dialogContent().checkOutdateList.nth(i).getAttribute("class"), "is-disabled");
                    break;
                }
            }

            for (int i = 0; i < screen.dialogContent().checkoutYearNames.count(); i++) {
                if (screen.dialogContent().checkoutYearNames.nth(i).innerText().equals(year)) {
                    boolean isSelected2 = (boolean) screen.dialogContent().checkoutYearNames.nth(i).evaluate("el => el.selected");
                    Assert.assertTrue(isSelected2);
                    break;
                }

            }
        });

        Allure.step("The customer enters the check-out date", () -> {
            if (screen.dialogContent().checkIndateList.nth(randomCheckInDate - 1).innerText().equals(String.valueOf(lastDayOfMonth))) {
                System.out.println(screen.dialogContent().checkIndateList.nth(randomCheckInDate - 1).innerText());
                screen.dialogContent().checkOutdateList.nth((int) (Math.random() * (lastDayOfMonth)) + 1).click();

            } else {
                System.out.println(randomCheckOutDate);
                screen.dialogContent().checkOutdateList.nth(randomCheckOutDate - 1).click();
            }

        });

        Allure.step("Verify check-in and check-out dates", () -> {

//            System.out.println(screen.dialogContent().checkInWidget.inputValue());
//            System.out.println(screen.dialogContent().checkOutWidget.inputValue());
            String checkInWidgetValue =screen.dialogContent().checkInWidget.evaluate("el => el.value").toString();
            System.out.println(checkInWidgetValue);
            String  chekOutWidgetValue=screen.dialogContent().checkOutWidget.evaluate("el => el.value").toString();
            System.out.println(chekOutWidgetValue);
            System.out.println(checkInDate);
            System.out.println(checkOutDate);

            Assert.assertEquals(checkInWidgetValue, checkInDate);
            Assert.assertEquals(chekOutWidgetValue, checkOutDate);
        });

        Allure.step("The customer clicks the GO button", () -> {
            rm.newPage(screen.dialogContent().goButton);
        });

        Allure.step("Verify redirection to the reservation page", () -> {

        });

    }
}
