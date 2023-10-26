package io.jacobking.simpleticket.core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Config instance = new Config();

    private final Properties properties;

    private Config() {
        this.properties = new Properties();
        initialize();
    }

    public static Config getInstance() {
        if (instance == null)
            return new Config();
        return instance;
    }

    private void initialize() {
        if (!IOUtil.doesDirectoryExist(Commons.DIRECTORY)) {
            IOUtil.createDirectory(Commons.DIRECTORY);
        }

        if (!IOUtil.doesFileExist(Commons.DATABASE)) {
            IOUtil.createFile(Commons.DATABASE);
        }

        if (!IOUtil.doesFileExist(Commons.PROPERTIES)) {
            IOUtil.createFile(Commons.PROPERTIES);
            storeDefaultProperties();
            return;
        }
        setProperties();
    }

    public String getProperty(final String key) {
        return properties.getProperty(key, null);
    }

    private void setProperties() {
        try {
            properties.load(new FileReader(Commons.PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeDefaultProperties() {
        try {
            properties.setProperty("url", Commons.DATABASE);
            properties.store(new FileWriter(Commons.PROPERTIES), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final class Commons {
        public static final String USER_HOME = System.getProperty("user.home");
        public static final String PATH_SEPARATOR = System.getProperty("file.separator");
        public static final String DIRECTORY = String.format("%s%s%s", USER_HOME, PATH_SEPARATOR, "SimpleTicket");
        public static final String PROPERTIES = String.format("%s%s%s", DIRECTORY, PATH_SEPARATOR, "system.properties");
        public static final String DATABASE = String.format("%s%s%s", DIRECTORY, PATH_SEPARATOR, "database.db");
    }

    private static final class IOUtil {
        private IOUtil() {
        }

        public static boolean doesFileExist(final String path) {
            final File file = new File(path);
            return file.exists() && file.isFile();
        }

        public static boolean doesDirectoryExist(final String path) {
            final File file = new File(path);
            return file.exists() && file.isDirectory();
        }

        public static boolean createFile(final String path) {
            final File file = new File(path);
            try {
                return file.createNewFile();
            } catch (IOException e) {
                return false;
            }
        }

        public static void createDirectory(final String path) {
            new File(path).mkdir();
        }
    }

}
