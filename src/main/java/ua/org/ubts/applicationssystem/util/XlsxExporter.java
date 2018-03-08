package ua.org.ubts.applicationssystem.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.org.ubts.applicationssystem.entity.Program;
import ua.org.ubts.applicationssystem.entity.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XlsxExporter {

    private static final Logger logger = Logger.getLogger(XlsxExporter.class);

    private Workbook book;

    public byte[] generateXlsx(List<Student> students) throws IOException {
        book = new XSSFWorkbook(getClass().getResourceAsStream("/static/template.xlsx"));
        for (Student student : students) {
            String sheetName = getAbbreviation(student);
            Sheet currentSheet = book.getSheet(sheetName);
            if (currentSheet == null) {
                logger.info("Creating new sheet for: " + student.getProgram().toString());
                currentSheet = createNewSheet(student.getProgram(), sheetName);
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

    private Sheet createNewSheet(Program program, String name) {
        Sheet sheet = book.cloneSheet(book.getSheetIndex("template"));
        book.setSheetName(book.getSheetIndex(sheet), name);
        Row firstRow = sheet.createRow(0);
        firstRow.createCell(5).setCellValue("Група: " + program.getName() + " " + program.getInfo());
        return sheet;
    }

    private void addStudentToRow(Student student, Row row) {
        row.createCell(0).setCellValue(student.getLastName());
        row.createCell(1).setCellValue(student.getFirstName());
        row.createCell(2).setCellValue(student.getMiddleName());
        row.createCell(3).setCellValue(student.getEmail());
        row.createCell(4).setCellValue(student.getPhone1()
                + (!StringUtils.isEmpty(student.getPhone2()) ? ", " + student.getPhone2() : ""));
        try {
            Cell birthdate = row.createCell(5);
            CreationHelper createHelper = book.getCreationHelper();
            CellStyle cellStyle = book.createCellStyle();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.mm.yyyy"));
            birthdate.setCellValue(parseDate(student.getBirthDate()));
            birthdate.setCellStyle(cellStyle);
        } catch (ParseException e) {
            logger.error(e);
        }
        row.createCell(6).setCellValue(student.getResidence().getCountry().getName());
        row.createCell(7).setCellValue(student.getResidence().getRegion());
        row.createCell(8).setCellValue(student.getResidence().getCityVillage());
        row.createCell(9).setCellValue(student.getResidence().getDistrict());
        row.createCell(10).setCellValue(student.getResidence().getStreet());
        row.createCell(11).setCellValue(student.getResidence().getHouse());
        row.createCell(12).setCellValue(student.getResidence().getApartment());
        row.createCell(13).setCellValue(student.getResidence().getIndex());
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }

    private String getAbbreviation(Student student) {
        return student.getProgram().getAbbreviation() + (student.getEntryYear().getValue() % 100);
    }
}
