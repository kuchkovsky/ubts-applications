package ua.org.ubts.applications.service;

import java.util.List;
import java.util.Optional;

public interface StudentExportService {

    void exportStudentToCloud(Long id);

    void exportStudentsToCloud(Optional<List<Integer>> years);

    void exportStudentsToExcel(Optional<List<Integer>> years);

}
