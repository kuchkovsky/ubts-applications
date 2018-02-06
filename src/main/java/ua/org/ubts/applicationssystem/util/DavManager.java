package ua.org.ubts.applicationssystem.util;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ua.org.ubts.applicationssystem.entity.Student;

import java.io.*;

/**
 * Created by Ckpe4 on 31.01.2018.
 */


public class DavManager {

    private final Logger logger = Logger.getLogger(DavManager.class);

    private String login;
    private String password;
    private String davUrl;

    public DavManager(String login, String password, String davUrl) {
        this.login = login;
        this.password = password;
        this.davUrl = davUrl;
    }

    public void createDirectory(String path){
        Sardine sardine = SardineFactory.begin(login, password);
        try {
            sardine.createDirectory(URIUtil.encodeQuery(davUrl + path, "UTF-8"));
            logger.info("Directory  created: " + path);
        } catch (Exception e) {
            logger.info("Failed to create directory: " + path);
            logger.error(e);
        }
    }

    public boolean exists(String item) {
        Sardine sardine = SardineFactory.begin(login, password);
        try {
            return sardine.exists(URIUtil.encodeQuery(davUrl + item, "UTF-8"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void createDirectoryRecursive(String path){
        String[] stringArray = path.split("/");
        String wd = new String();
        for (String str : stringArray) {
            wd += str + "/";
            try {
                if (!exists(wd)) {
                    createDirectory(wd);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void put(byte[] data, String path) {
        Sardine sardine = SardineFactory.begin(login, password);
        try {
            sardine.put(URIUtil.encodeQuery(davUrl + path, "UTF-8"), data);
            logger.info("Pushing file on cloud: " + path);
        } catch (Exception e) {
            logger.info("Failed to push file on cloud: " + path);
            logger.error(e);
        }
    }

    public void delete(String item) {
        Sardine sardine = SardineFactory.begin(login, password);
        try {
            sardine.delete(URIUtil.encodeQuery(davUrl + item, "UTF-8"));
            logger.info("Deleted file from cloud: " + item);
        } catch (Exception e) {
            logger.info("Failed to delete file from cloud: " + item);
            logger.error(e);
        }
    }

    public void generatePdf(String url, String nextcloudPath) {
        try {
            Process p = Runtime.getRuntime().exec("wkhtmltopdf -q --page-size A3 " + url + " -");
            InputStream bis = new BufferedInputStream(p.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int reads = bis.read();
            while(reads != -1) {
                baos.write(reads);
                reads = bis.read();
            }
            p.waitFor();
            put(baos.toByteArray(), nextcloudPath);
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void exportFolder(File folder, String nextcloudPath) {
        logger.info("Exporting files from folder: " + folder.getAbsolutePath());
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            try {
                logger.info("Pushing file: " + file.getAbsolutePath());
                byte[] data = FileUtils.readFileToByteArray(file);
                put(data, nextcloudPath + file.getName());
            } catch (IOException e) {
                logger.info("Failed to push file: " + file.getAbsolutePath());
                logger.error(e);
            }
        }
    }

    public void exportStudent(Student student) {
        String folder = "/ApplicationSystem/" + student.getProgram().getName() + "/" + student.getFullSlavicName() + "/";
        if (!exists(folder)) {
            createDirectoryRecursive(folder);
            generatePdf("http://localhost:8080/#!/view/student/" + student.getId().toString(),
                    folder + "Анкета.pdf");
            File studentFolder = new File(UserFilesManager.getAppFolder() + "students" + File.separator
                    + student.getLastName().toLowerCase() + "_" + student.getFirstName().toLowerCase().substring(0, 1)
                    + "_" + student.getMiddleName().toLowerCase().substring(0, 1));
            exportFolder(studentFolder, folder);
        }
    }



}
