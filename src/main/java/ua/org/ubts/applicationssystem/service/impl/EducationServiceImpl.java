package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.Education;
import ua.org.ubts.applicationssystem.repository.EducationRepository;
import ua.org.ubts.applicationssystem.service.EducationService;

import javax.transaction.Transactional;
import java.util.List;

@Service("educationService")
@Transactional
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Override
    public Education findById(Integer id) {
        return educationRepository.findOne(id);
    }

    @Override
    public Education findByName(String name) {
        return educationRepository.findByName(name);
    }

    @Override
    public Education findByData(Education education) {
        return findByName(education.getName());
    }

    @Override
    public void save(Education education) {
        educationRepository.save(education);
    }

    @Override
    public void deleteById(Integer id) {
        educationRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        educationRepository.deleteAll();
    }

    @Override
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Override
    public boolean isExist(Education education) {
        return findByData(education) != null;
    }

}
