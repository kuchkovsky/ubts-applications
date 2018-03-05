package ua.org.ubts.applicationssystem.util;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.UriUtils;
import ua.org.ubts.applicationssystem.entity.Student;

import java.io.*;

/**
 * Created by Ckpe4 on 31.01.2018.
 */

public class DavManager {

    private static final Logger logger = Logger.getLogger(DavManager.class);

    private String davUrl;
    private Sardine sardine;

    public DavManager(String login, String password, String davUrl) {
        this.davUrl = davUrl;
        sardine = SardineFactory.begin(login, password);
    }

    public void createDirectory(String path) throws IOException {
        sardine.createDirectory(davUrl + UriUtils.encodePath(path, "UTF-8"));
        logger.info("Directory  created: " + path);
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
        logger.info("Pushing file on cloud: " + path);
        sardine.put(davUrl + UriUtils.encodePath(path, "UTF-8"), data);
    }

    public void delete(String item) throws IOException {
        sardine.delete(davUrl + UriUtils.encodePath(item, "UTF-8"));
        logger.info("Deleted file from cloud: " + item);
    }

    public void generatePdf(String url, String nextcloudPath) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("wkhtmltopdf -q --page-size A3 " + url + " -");
        InputStream bis = new BufferedInputStream(p.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = bis.read();
        while (reads != -1) {
            baos.write(reads);
            reads = bis.read();
        }
        p.waitFor();
        put(baos.toByteArray(), nextcloudPath);
    }

    public void exportFolder(File folder, String nextcloudPath) throws IOException {
        logger.info("Exporting files from folder: " + folder.getAbsolutePath());
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            logger.info("Pushing file: " + file.getAbsolutePath());
            byte[] data = FileUtils.readFileToByteArray(file);
            put(data, nextcloudPath + file.getName());
        }
    }

    public void exportStudent(Student student) throws IOException, InterruptedException {
        String folder = "/ApplicationSystem/" + student.getEntryYear().getValue() + "/" + student.getProgram().getName()
                + "/" + student.getFullSlavicName() + "/";
        if (!exists(folder)) {
            logger.info("Exporting student to cloud: " + student.getFullSlavicName());
            createDirectoryRecursive(folder);
            generatePdf("http://[::1]:8080/#!/view/student/" + student.getId().toString() + "/print",
                    folder + "Анкета.pdf");
            File studentFolder = new File(UserFilesManager.getAppFolder() + "students" + File.separator
                    + student.getLastName().toLowerCase() + "_" + student.getFirstName().toLowerCase().substring(0, 1)
                    + "_" + student.getMiddleName().toLowerCase().substring(0, 1));
            exportFolder(studentFolder, folder);
        } else {
            logger.info("Student folder already exists: " + student.getFullSlavicName() + ". Skipping.");
        }
    }

}
