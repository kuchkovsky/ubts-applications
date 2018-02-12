package ua.org.ubts.applicationssystem.util;

import ua.org.ubts.applicationssystem.entity.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    public static class DavProperties {

        private String login;
        private String password;

        public DavProperties(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

    }

    public static DavProperties getDavProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(UserFilesManager.getAppFolder() + "dav.properties")) {
            properties.load(inputStream);
            return new DavProperties(properties.getProperty("login"), properties.getProperty("password"));
        }
    }

}
