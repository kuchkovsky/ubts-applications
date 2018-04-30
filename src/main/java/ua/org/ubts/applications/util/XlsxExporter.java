package ua.org.ubts.applications.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.org.ubts.applications.entity.ProgramEntity;
import ua.org.ubts.applications.entity.StudentEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class XlsxExporter {

    private Workbook book;

    public byte[] generateXlsx(List<StudentEntity> students) throws IOException {
        book = new XSSFWorkbook(getClass().getResourceAsStream("/template.xlsx"));
        for (StudentEntity student : students) {
            String sheetName = getAbbreviation(student);
            Sheet currentSheet = book.getSheet(sheetName);
            if (currentSheet == null) {
                log.info("Creating new sheet for: " + student.getProgram().toString());
                currentSheet = createNewSheet(student.getProgram(), sheetName);
            }
            log.info("Writing student to row: " + student.getFullSlavicName() + " in sheet "
                    + currentSheet.getSheetName());
            addStudentToRow(student, currentSheet.createRow(currentSheet.getLastRowNum() + 1));
        }
        log.info("Removing template sheet...");
        book.removeSheetAt(book.getSheetIndex("template"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        book.write(baos);
        book.close();
        return baos.toByteArray();
    }

    private Sheet createNewSheet(ProgramEntity programEntity, String name) {
        Sheet sheet = book.cloneSheet(book.getSheetIndex("template"));
        book.setSheetName(book.getSheetIndex(sheet), name);
        Row firstRow = sheet.createRow(0);
        firstRow.createCell(5).setCellValue("Група: " + programEntity.getName() + " " + programEntity.getInfo());
        return sheet;
    }

    private void addStudentToRow(StudentEntity studentEntity, Row row) {
        row.createCell(0).setCellValue(studentEntity.getLastName());
        row.createCell(1).setCellValue(studentEntity.getFirstName());
        row.createCell(2).setCellValue(studentEntity.getMiddleName());
        row.createCell(3).setCellValue(studentEntity.getEmail());
        row.createCell(4).setCellValue(studentEntity.getPhone1()
                + (!StringUtils.isEmpty(studentEntity.getPhone2()) ? ", " + studentEntity.getPhone2() : ""));
        try {
            Cell birthdate = row.createCell(5);
            CreationHelper createHelper = book.getCreationHelper();
            CellStyle cellStyle = book.createCellStyle();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.mm.yyyy"));
            birthdate.setCellValue(parseDate(studentEntity.getBirthDate()));
            birthdate.setCellStyle(cellStyle);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        row.createCell(6).setCellValue(studentEntity.getResidence().getCountry().getName());
        row.createCell(7).setCellValue(studentEntity.getResidence().getRegion());
        row.createCell(8).setCellValue(studentEntity.getResidence().getCityVillage());
        row.createCell(9).setCellValue(studentEntity.getResidence().getDistrict());
        row.createCell(10).setCellValue(studentEntity.getResidence().getStreet());
        row.createCell(11).setCellValue(studentEntity.getResidence().getHouse());
        row.createCell(12).setCellValue(studentEntity.getResidence().getApartment());
        row.createCell(13).setCellValue(studentEntity.getResidence().getIndex());
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }

    private String getAbbreviation(StudentEntity studentEntity) {
        return studentEntity.getProgram().getAbbreviation() + (studentEntity.getEntryYear().getValue() % 100);
    }

}
