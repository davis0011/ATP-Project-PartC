package Server;

import java.io.*;
import java.security.KeyException;
import java.util.Properties;

/**
 * Configuration class for storing and fetching config data
 */
public class Configurations {
    private static Configurations singletonConfig = null;

    private Properties properties;
    private final String path = "resources/config.properties";


    /**
     * Creates a Singleton Config object for setting and getting properties
     */
    private Configurations(){
        properties = new Properties();
        try{
            InputStream input = new FileInputStream("resources/config.properties");
            properties.load(input);
        }
        catch(IOException e){
            try{
                OutputStream out = new FileOutputStream(path);
                properties.setProperty("server.ThreadPoolSize", "5");
                properties.setProperty("server.mazeGeneratingAlgorithm", "My");
                properties.setProperty("server.mazeSearchingAlgorithm", "Best");
                properties.store(out,null);
            }
            catch (IOException err){
            }
        }
    }

    /**
     * @param key The name of the config field
     * @return the value associated with the key
     */
    public String getConfig(String key){
        return properties.getProperty(key);
    }

    public boolean setConfig(String key, String val){
        try{
            OutputStream out = new FileOutputStream(path);
            properties.setProperty(key,val);
            properties.store(out,null);
        }
        catch (IOException e){
            return false;
        }
        return true;
    }


    /**
     * @return Singleton config instance
     */
    public static Configurations getInstance(){
        if (singletonConfig == null){
            singletonConfig = new Configurations();
        }
        return singletonConfig;
    }
}
