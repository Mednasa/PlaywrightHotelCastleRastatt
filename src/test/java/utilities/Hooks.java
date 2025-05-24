package utilities;

import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks extends TestListenerAdapter {

    private Playwright playwright;
    private Browser browser;
    public static Page page;
    private BrowserContext context;

    @Override
    public void onTestStart(ITestResult result) {

        // ✅ allure-results klasörünü temizle
//        File allureResultsDir = new File("allure-results");
//        if (allureResultsDir.exists() && allureResultsDir.isDirectory()) {
//            File[] files = allureResultsDir.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    if (file.delete()) {
//                        System.out.println("Silindi: " + file.getName());
//                    } else {
//                        System.out.println("Silinemedi: " + file.getName());
//                    }
//                }
//            }
//        }

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        BrowserFactory bf = new BrowserFactory();
        String browserName = ConfigReader.getProperty("browser");
        this.browser = bf.getBrowser(browserName);
        this.context = bf.createPageAndGetContext(this.browser, result);
        this.page = context.newPage();
        page.navigate(ConfigReader.getProperty("url"));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Başarı durumunda trace dosyasını sil
        cleanupOldTraces();
        //Trace kaydını durdur
        try {
            if (context != null) {
                context.tracing().stop();
            }
        } finally {
            cleanup();
        }
    }


    @Override
    public void onTestFailure(ITestResult result) {

        String tracePath = getTraceFilePath(result);

        //Başarısızlık durumunda da trace kaydını durdur ve kaydet
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get(tracePath)));

        cleanup();

    }

    public String getTraceFilePath(ITestResult result) {
        String baseDir = "src/test/java/utilities/traceViewer/";
        String methodName = result.getMethod().getMethodName();
        String date = new SimpleDateFormat("_hh_mm_ss_ddMMyyyy").format(new Date());
        return baseDir + methodName + date + "-trace.zip";
    }

    private void cleanup() {
        // Kaynakları temizle
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private void cleanupOldTraces() {
        final long EXPIRATION_TIME = 86400000; // 24 saat
        File dir = new File("src/test/java/utilities/traceViewer/");
        File[] files = dir.listFiles();
        if (files != null) {
            long now = System.currentTimeMillis();
            for (File file : files) {
                if (now - file.lastModified() > EXPIRATION_TIME) {
                    if (!file.delete()) {
                        System.out.println("Failed to delete old trace file: " + file.getPath());
                    }
                }
            }
        }
    }


}
