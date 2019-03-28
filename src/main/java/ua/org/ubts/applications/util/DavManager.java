package ua.org.ubts.applications.util;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriUtils;

import java.io.*;

/**
 * Created by Ckpe4 on 31.01.2018.
 */

@Slf4j
public class DavManager {

    private String davUrl;
    private Sardine sardine;

    public DavManager(String login, String password, String davUrl) {
        this.davUrl = davUrl;
        sardine = SardineFactory.begin(login, password);
    }

    public void createDirectory(String path) throws IOException {
        sardine.createDirectory(davUrl + UriUtils.encodePath(path, "UTF-8"));
        log.info("Directory created: " + path);
    }

    public boolean exists(String item) throws IOException {
        return sardine.exists(davUrl + UriUtils.encodePath(item, "UTF-8"));
    }

    public void createDirectoryRecursive(String path) throws IOException {
        String[] stringArray = path.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : stringArray) {
            stringBuilder.append(str);
            stringBuilder.append("/");
            String dirPath = stringBuilder.toString();
            if (!exists(dirPath)) {
                createDirectory(dirPath);
            }
        }
    }

    public void put(byte[] data, String path) throws IOException {
        log.info("Pushing file on cloud: " + path);
        sardine.put(davUrl + UriUtils.encodePath(path, "UTF-8"), data);
    }

}
