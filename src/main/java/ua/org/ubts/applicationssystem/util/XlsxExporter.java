package ua.org.ubts.applicationssystem.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.org.ubts.applicationssystem.entity.Student;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XlsxExporter {

    private static final Logger logger = Logger.getLogger(XlsxExporter.class);

    private Workbook book;
    private Map<String, String> abbreviationMap;

    public XlsxExporter() {
        abbreviationMap = new HashMap<>();
    }

    public byte[] generateXlsx(List<Student> students) throws IOException {
        book = new XSSFWorkbook(getClass().getResourceAsStream("/static/template.xlsx"));
        for (Student student : students) {
            String programName = student.getProgram().getName();
            programName += student.getProgram().getInfo() != null ? " " + student.getProgram().getInfo() : "";
            String sheetName = getAbbreviation(programName);
            Sheet currentSheet = book.getSheet(sheetName);
            if (currentSheet == null) {
                logger.info("Creating new sheet for: " + programName);
                currentSheet = createNewSheet(programName);
            }
            logger.info("Writing student to row: " + student.getFullSlavicName() + " in sheet "
                    + currentSheet.getSheetName());
            addStudentToRow(student, currentSheet.createRow(currentSheet.getLastRowNum() + 1));
        }
        logger.info("Removing template sheet.");
        book.removeSheetAt(book.getSheetIndex("template"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        book.write(baos);
        book.close();
        return baos.toByteArray();
    }

    private Sheet createNewSheet(String name) {
        System.out.println("creating sheet for: " + name);
        Sheet sheet = book.cloneSheet(book.getSheetIndex("template"));
        book.setSheetName(book.getSheetIndex(sheet), abbreviationMap.get(name));
        Row firstRow = sheet.createRow(0);
        firstRow.createCell(5).setCellValue("Група: " + name + ";");
        return sheet;
    }

    private void addStudentToRow(Student student, Row row) {
        row.createCell(0).setCellValue(student.getLastName());
        row.createCell(1).setCellValue(student.getFirstName());
        row.createCell(2).setCellValue(student.getMiddleName());
        row.createCell(3).setCellValue(student.getEmail());
        row.createCell(4).setCellValue(student.getPhone1()
                + (!StringUtils.isEmpty(student.getPhone2()) ? ", " + student.getPhone2() : ""));
        Cell birthdate = row.createCell(5);
        birthdate.setCellValue(parseDate(student.getBirthDate()));
        row.createCell(6).setCellValue(student.getResidence().getCountry().getName());
        row.createCell(7).setCellValue(student.getResidence().getRegion());
        row.createCell(8).setCellValue(student.getResidence().getCityVillage());
        row.createCell(9).setCellValue(student.getResidence().getDistrict());
        row.createCell(10).setCellValue(student.getResidence().getStreet());
        row.createCell(11).setCellValue(student.getResidence().getHouse());
        row.createCell(12).setCellValue(student.getResidence().getApartment());
        row.createCell(13).setCellValue(student.getResidence().getIndex());
    }

    private String parseDate(String date) {
        Matcher matcher = Pattern.compile("^(\\d+)-(\\d+)-(\\d+)$").matcher(date);
        matcher.find();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(matcher.group(3));
        stringBuilder.append(".");
        stringBuilder.append(matcher.group(2));
        stringBuilder.append(".");
        stringBuilder.append(matcher.group(1));
        return  stringBuilder.toString();
    }

    private String getAbbreviation(String name) {
        String abbreviation = new String();
        if (abbreviationMap.containsKey(name)) {
            abbreviation = abbreviationMap.get(name);
        } else {
            Matcher matcher = Pattern.compile("\\b(?:\\w)", Pattern.UNICODE_CHARACTER_CLASS).matcher(name);
            while (matcher.find()) {
                abbreviation += matcher.group().toUpperCase();
            }
            abbreviationMap.put(name, abbreviation);
        }
        return abbreviation;
    }
}
