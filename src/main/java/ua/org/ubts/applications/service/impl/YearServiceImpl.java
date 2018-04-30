package ua.org.ubts.applications.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.entity.YearEntity;
import ua.org.ubts.applications.repository.YearRepository;
import ua.org.ubts.applications.service.YearService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class YearServiceImpl implements YearService {

    @Autowired
    private YearRepository yearRepository;

    @Override
    public List<YearEntity> getYears() {
        return yearRepository.findAll();
    }

}
