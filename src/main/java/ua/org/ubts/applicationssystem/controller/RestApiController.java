package ua.org.ubts.applicationssystem.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.org.ubts.applicationssystem.dto.StudentListItem;
import ua.org.ubts.applicationssystem.entity.*;
import ua.org.ubts.applicationssystem.model.StudentFilesUploadModel;
import ua.org.ubts.applicationssystem.service.ProgramService;
import ua.org.ubts.applicationssystem.service.StudentService;
import ua.org.ubts.applicationssystem.util.ResponseMessage;
import ua.org.ubts.applicationssystem.util.UserFilesManager;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yaroslav on 14.07.2017.
 */

@RestController
@RequestMapping("/api")
public class RestApiController {

    private static final Logger logger = Logger.getLogger(RestApiController.class);
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProgramService programService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/list")
    public ResponseEntity<List<StudentListItem>> getStudentList() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<StudentListItem> studentList = new ArrayList<>();
        students.forEach(student -> {
            studentList.add(new StudentListItem(student.getId(), student.getFullSlavicName(), student.getProgram()));
        });
        return new ResponseEntity<List<StudentListItem>>(studentList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") Integer id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(new ResponseMessage("User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<ResponseMessage> addStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
        if (studentService.isExist(student)) {
            String errorMessage = "Unable to create. A Student with name " + student.getFullSlavicName()
                    + " already exist";
            logger.info("DB operation error: " + errorMessage);
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.CONFLICT);
        }
        studentService.save(student);
        logger.info("Added to DB: " + student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<>(new ResponseMessage("OK"), headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/students/{id}")
    public ResponseEntity<ResponseMessage> deleteStudent(@PathVariable("id") Integer id) {
        Student student = studentService.findById(id);
        if (student == null) {
            String errorMessage = "Unable to delete. User with id " + id + " not found";
            logger.info("DB operation error: " + errorMessage);
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.NOT_FOUND);
        }
        studentService.deleteById(id);
        logger.info("Deleted from DB: " + student);
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/students")
    public ResponseEntity<ResponseMessage> deleteAllStudents() {
        studentService.deleteAll();
        logger.info("All students deleted from DB");
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/programs")
    public ResponseEntity<List<Program>> getPrograms() {
        List<Program> programs = programService.findAll();
        if (programs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @PostMapping("/students/files")
    public ResponseEntity<ResponseMessage> uploadStudentFiles(@ModelAttribute StudentFilesUploadModel model) {
        Student student = studentService.findByName(model.getFirstName(), model.getMiddleName(), model.getLastName());
        if (student == null) {
            return new ResponseEntity<>(new ResponseMessage("Student not found in database"), HttpStatus.NOT_FOUND);
        }
        if (Boolean.TRUE.equals(student.hasFilesUploaded())) {
            return new ResponseEntity<>(new ResponseMessage("Student files already uploaded"), HttpStatus.CONFLICT);
        }
        if (!model.areMimeTypesCorrect()) {
            return new ResponseEntity<>(new ResponseMessage("Unsupported media type"),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        logger.info("Saving uploaded files... User: " + model.getLastName() + " " + model.getFirstName() + " "
                + model.getMiddleName());
        try {
            UserFilesManager.saveStudentFiles(model);
            student.setFilesUploaded(true);
            studentService.save(student);
            logger.info("Files were saved successfully. User: " + model.getLastName() + " " + model.getFirstName() + " "
                    + model.getMiddleName());
        } catch (IOException e) {
            logger.error(e);
            return new ResponseEntity<>(new ResponseMessage("Files not saved"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Successfully uploaded"), HttpStatus.OK);
    }

    @RequestMapping(value = "/students/files/exist", method = RequestMethod.HEAD)
    public ResponseEntity checkIfStudentFilesExist(@RequestParam("first_name") String firstName,
                                                   @RequestParam("middle_name") String middleName,
                                                   @RequestParam("last_name") String lastName) {
        Student student = studentService.findByName(firstName, middleName, lastName);
        if (student != null && Boolean.TRUE.equals(student.hasFilesUploaded())) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/files/{id}")
    public void getUserFiles(@PathVariable("id") Integer id, HttpServletResponse response) {
        Student student = studentService.findById(id);
        if (student == null) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                logger.error(e);
            }
            return;
        }
        try {
            ByteArrayOutputStream outputStream = UserFilesManager.getStudentFiles(student);
            String filename = UserFilesManager.getStudentDirectory(student) + ".zip";
            filename = URLEncoder.encode(filename,"UTF-8");
            response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
            response.setContentType("application/zip");
            response.getOutputStream().write(outputStream.toByteArray());
            response.flushBuffer();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @GetMapping("/import/BaOdDgUxNvLbAnLmKeAa")
    public ResponseEntity<ResponseMessage> importStudentsFromOldDataBase() {

        Map<Integer, String> educationMap = new HashMap<>();
        educationMap.put(1, "Середня");
        educationMap.put(2, "Середньо-спеціальна");
        educationMap.put(3, "Незакінчена вища");
        educationMap.put(4, "Вища");

        Map<String, String> programMap = new HashMap<>();
        programMap.put("bps", "Бакалавр пасторського служіння");
        programMap.put("bcs-onc", "Бакалавр церковного служіння. Спеціалізація - організація нових церков");
        programMap.put("bcs-otrcs",
                "Бакалавр церковного служіння. Спеціалізація - організація та розвиток церковних служінь");
        programMap.put("bcs-js", "Бакалавр церковного служіння. Спеціалізація - жіноче служіння");
        programMap.put("bm-mm", "Бакалавр місіології. Міжнародне місіонерство");
        programMap.put("bms", "Бакалавр музичного служіння");
        programMap.put("prml", "Програма розвитку молодих лідерів");
        programMap.put("bcs-ho", "Бакалавр церковного служіння. Спеціалізація - християнська освіта");

        Map<String, String> programInfoMap = new HashMap<>();
        programInfoMap.put("2-l", "2 роки - Львів");
        programInfoMap.put("4-l", "4 роки - Львів");
        programInfoMap.put("2-i", "2 роки - Ірпінь");
        programInfoMap.put("4-i", "4 роки - Ірпінь");
        programInfoMap.put("2", "2 роки");
        programInfoMap.put("4", "4 роки");
        programInfoMap.put("lviv", "Львівська область");
        programInfoMap.put("rivne", "Рівненська область");
        programInfoMap.put("hmelnytsk", "Хмельницька область");

        Map<String, String> churchMinistryTypeMap = new HashMap<>();
        churchMinistryTypeMap.put("pastor", "Пастор");
        churchMinistryTypeMap.put("evangelist", "Благовісник");
        churchMinistryTypeMap.put("small-group-head", "Керівник малої групи");
        churchMinistryTypeMap.put("deacon", "Диякон");
        churchMinistryTypeMap.put("music-service", "Музичне служіння");
        churchMinistryTypeMap.put("missionary", "Місіонер");
        churchMinistryTypeMap.put("preacher", "Проповідник");
        churchMinistryTypeMap.put("youth-leader", "Лідер молоді");
        churchMinistryTypeMap.put("child-service", "Дитяче служіння");
        churchMinistryTypeMap.put("other", "Інше");

        Map<Integer, String> maritalStatusMap = new HashMap<>();
        maritalStatusMap.put(1, "Неодружений/незаміжня");
        maritalStatusMap.put(2, "Одружений/заміжня");
        maritalStatusMap.put(3, "Вдівець/вдова");
        maritalStatusMap.put(4, "Розлучений/розлучена");

        Map<String, String> membersNumberMap = new HashMap<>();
        membersNumberMap.put("&lt;=10", "до 10");
        membersNumberMap.put("10-20", "10-20");
        membersNumberMap.put("20-100", "20-100");
        membersNumberMap.put("100-300", "100-300");
        membersNumberMap.put("&gt;300", "більше 300");

        Map<Integer, String> healthStatusMap = new HashMap<>();
        healthStatusMap.put(1, "Незадовільний");
        healthStatusMap.put(2, "Задовільний");
        healthStatusMap.put(3, "Добрий");
        healthStatusMap.put(4, "Відмінний");

        // JDBC URL, username and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/applications";
        String user = "localuser";
        String password = "pwd1488hail";
        // JDBC variables for opening and managing connection

        try {
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            String query = "SELECT * FROM personal_data, applicants, church_data, health_data, marital_data "
                    + "WHERE applicants.id = church_data.a_id AND applicants.id = health_data.a_id "
                    + "AND applicants.id = marital_data.a_id AND applicants.id = personal_data.a_id";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Student student = new Student();
                student.setLastName(rs.getString("last_name"));
                student.setFirstName(rs.getString("first_name"));
                student.setMiddleName(rs.getString("middle_name"));
                String[] birthdate = rs.getString("birthdate").split("\\.");
                student.setBirthDate(birthdate[2] + "-" + birthdate[1] + "-" + birthdate [0]);

                Residence residence = new Residence();
                residence.setStreet(rs.getString("street"));
                residence.setHouse(rs.getString("house"));
                residence.setCityVillage(rs.getString("city_village"));
                String district = rs.getString("district");
                if (district != null) {
                    residence.setDistrict(district);
                }
                String apartment = rs.getString("apartment");
                if (apartment != null) {
                    residence.setApartment(apartment);
                }
                residence.setRegion(rs.getString("region"));
                residence.setIndex(rs.getString("index_"));
                residence.setCountry(new Country(rs.getString("country")));
                student.setResidence(residence);

                student.setPhone1(rs.getString("phone1"));
                String phone2 = rs.getString("phone2");
                if (phone2 != null) {
                    student.setPhone2(phone2);
                }
                student.setEmail(rs.getString("email"));
                student.setDonationAmount(rs.getString("donation_amount"));
                String financeComments = rs.getString("finance_comments");
                if (financeComments != null) {
                    student.setFinanceComments(financeComments);
                }
                if (!rs.getBoolean("not_working")) {
                    student.setJob(rs.getString("position"));
                }
                String addComment1 = rs.getString("add_comment_1");
                String addComment2 = rs.getString("add_comment_2");
                if (addComment1 == null || addComment1.isEmpty()) {
                    addComment1 = " ";
                }
                student.setReasonsToStudy(addComment1);
                if (addComment2 == null || addComment2.isEmpty()) {
                    addComment2 = " ";
                }
                student.setHowCameToGod(addComment2);
                Education education = new Education();
                education.setName(educationMap.get(rs.getInt("secular_education")));
                student.setEducation(education);

                String term = rs.getString("term");
                String js = rs.getString("js");
                String prmlRegion = rs.getString("prml_region");
                String info;
                if (term != null && !term.isEmpty()) {
                    info = programInfoMap.get(term);
                } else if (js != null && !js.isEmpty()) {
                    info = programInfoMap.get(js);
                } else if (prmlRegion != null && !prmlRegion.isEmpty()) {
                    info = programInfoMap.get(prmlRegion);
                } else {
                    info = "";
                }
                Program program = new Program(programMap.get(rs.getString("programm")), info);
                student.setProgram(program);
                student.setFilesUploaded(true);

                HealthData healthData = new HealthData();
                healthData.setDrugAddicted(rs.getBoolean("drug_addiction"));
                healthData.setHealthStatus(healthStatusMap.get(rs.getInt("health_status")));
                healthData.setHasStudyProblems(rs.getBoolean("hs_learning_problems"));
                healthData.setTakingMedicine(rs.getBoolean("take_medicine"));
                student.setHealthData(healthData);

                if (rs.getBoolean("church_member")) {
                    ChurchData churchData = new ChurchData();
                    churchData.setName(rs.getString("church_name"));
                    churchData.setPastorName(rs.getString("church_pastor"));
                    churchData.setUnion(rs.getString("church_union"));
                    churchData.setDenomination(rs.getString("church_denomination"));
                    churchData.setMembersNumber(membersNumberMap.get(rs.getString("church_members")));

                    churchData.setRegion(rs.getString("church_region"));
                    churchData.setCityVillage(rs.getString("church_city_village"));
                    churchData.setIndex(rs.getString("church_index"));
                    String churchDistrict = rs.getString("church_district");
                    if (churchDistrict != null) {
                        churchData.setDistrict(churchDistrict);
                    }
                    churchData.setStreetAndHouseNumber(rs.getString("church_house_number"));
                    String churchPhone = rs.getString("church_phone");
                    if (churchPhone != null) {
                        churchData.setPhone(churchPhone);
                    }
                    student.setChurchData(churchData);

                    ChurchMinistry churchMinistry = new ChurchMinistry();
                    churchMinistry.setType(new ChurchMinistryType(churchMinistryTypeMap.get(
                            rs.getString("church_service_type"))));
                    churchMinistry.setRepentanceDate(rs.getString("repentance_date"));
                    churchMinistry.setBaptismDate(rs.getString("baptism_date"));
                    if (rs.getBoolean("ordained_church_worker")) {
                        churchMinistry.setOrdinationDate(rs.getString("ordination_date"));
                    }
                    churchMinistry.setChurchParticipation(rs.getString("church_participation"));
                    student.setChurchMinistry(churchMinistry);
                }

                MaritalData maritalData = new MaritalData();
                int maritalStatus = rs.getInt("marital_status");
                maritalData.setStatus(new MaritalStatus(maritalStatusMap.get(maritalStatus)));
                if (maritalStatus == 2) {
                    maritalData.setSpouseName(rs.getString("spouse_name"));
                    String[] marriageDate = rs.getString("marriage_date").split("\\.");
                    maritalData.setMarriageDate(marriageDate[2] + "-" + marriageDate[1] + "-" + marriageDate [0]);
                    maritalData.setChildrenNumber(rs.getInt("children"));
                    maritalData.setSpouseApproveSeminary(rs.getBoolean("spouse_seminary_approve"));
                    boolean isSpouseChurchMember = rs.getBoolean("spouse_church_member");
                    maritalData.setSpouseChurchMember(isSpouseChurchMember);
                    if (isSpouseChurchMember) {
                        maritalData.setSpouseChurchMinistry(rs.getString("spouse_church_service"));
                    }
                }
                student.setMaritalData(maritalData);

                studentService.save(student);
                logger.info(student);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
                stmt.close();
                rs.close();
            } catch(SQLException se) {
                logger.error(se);
            }
        }
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }
}
