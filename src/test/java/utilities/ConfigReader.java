package utilities;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();


    static {
        try(FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Error while reading properties file");
        }
    }

    public static String getProperty(String keyword) {
        return properties.getProperty(keyword);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
