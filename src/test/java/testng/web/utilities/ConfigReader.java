package testng.web.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	 private Properties properties;

	    public ConfigReader() {
	        properties = new Properties();
	        try {
	            FileInputStream inputStream = new FileInputStream("src\\test\\resources\\config.properties");
	            properties.load(inputStream);
	            inputStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public String getProperty(String key) {
	        return properties.getProperty(key);
	    }
}
