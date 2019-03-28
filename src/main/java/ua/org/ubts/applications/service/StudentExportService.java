package ua.org.ubts.applications.service;

import java.util.List;
import java.util.Optional;

public interface StudentExportService {

    void exportStudentsToExcel(Optional<List<Integer>> years);

}
