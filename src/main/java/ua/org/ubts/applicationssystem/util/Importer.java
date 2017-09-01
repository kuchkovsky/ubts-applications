package ua.org.ubts.applicationssystem.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import ua.org.ubts.applicationssystem.entity.*;
import ua.org.ubts.applicationssystem.service.StudentService;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Importer {

    private static final Logger logger = Logger.getLogger(Importer.class);

    public static synchronized void importStudents(StudentService studentService) {

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

        String url = "jdbc:mysql://localhost:3306/applications";
        String user = "localuser";
        String password = "pwd1488hail";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
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
                if (StringUtils.isNotEmpty(district)) {
                    residence.setDistrict(district);
                }
                String apartment = rs.getString("apartment");
                if (StringUtils.isNotEmpty(apartment)) {
                    residence.setApartment(apartment);
                }
                residence.setRegion(rs.getString("region"));
                residence.setIndex(rs.getString("index_"));
                residence.setCountry(new Country(rs.getString("country")));
                student.setResidence(residence);

                student.setPhone1(rs.getString("phone1"));
                String phone2 = rs.getString("phone2");
                if (StringUtils.isNotEmpty(phone2)) {
                    student.setPhone2(phone2);
                }
                student.setEmail(rs.getString("email"));
                student.setDonationAmount(rs.getString("donation_amount"));
                String financeComments = rs.getString("finance_comments");
                if (StringUtils.isNotEmpty(financeComments)) {
                    student.setFinanceComments(financeComments);
                }
                if (!rs.getBoolean("not_working")) {
                    student.setJob(rs.getString("position"));
                }
                String addComment1 = rs.getString("add_comment_1");
                String addComment2 = rs.getString("add_comment_2");
                if (StringUtils.isEmpty(addComment1)) {
                    addComment1 = " ";
                }
                student.setReasonsToStudy(addComment1);
                if (StringUtils.isEmpty(addComment2)) {
                    addComment2 = " ";
                }
                student.setHowCameToGod(addComment2);
                Education education = new Education();
                education.setName(educationMap.get(rs.getInt("secular_education")));
                student.setEducation(education);

                String term = rs.getString("term");
                String js = rs.getString("js");
                String prmlRegion = rs.getString("prml_region");
                String info = null;
                if (StringUtils.isNotEmpty(term)) {
                    info = programInfoMap.get(term);
                } else if (StringUtils.isNotEmpty(js)) {
                    info = programInfoMap.get(js);
                } else if (StringUtils.isNotEmpty(prmlRegion)) {
                    info = programInfoMap.get(prmlRegion);
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
                    if (StringUtils.isNotEmpty(churchDistrict)) {
                        churchData.setDistrict(churchDistrict);
                    }
                    churchData.setStreetAndHouseNumber(rs.getString("church_house_number"));
                    String churchPhone = rs.getString("church_phone");
                    if (StringUtils.isNotEmpty(churchPhone)) {
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
                if(studentService.isExist(student)) {
                } else {
                    studentService.save(student);
                }
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        } finally {
            try {
                con.close();
                stmt.close();
                rs.close();
            } catch(SQLException se) {
                logger.error(se);
            }
        }
    }

}
