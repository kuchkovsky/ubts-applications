package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.MaritalStatus;
import ua.org.ubts.applicationssystem.repository.MaritalStatusRepository;
import ua.org.ubts.applicationssystem.service.MaritalStatusService;

import javax.transaction.Transactional;
import java.util.List;

@Service("maritalStatusService")
@Transactional
public class MaritalStatusServiceImpl implements MaritalStatusService {

    @Autowired
    private MaritalStatusRepository maritalStatusRepository;

    @Override
    public MaritalStatus findById(Integer id) {
        return maritalStatusRepository.findOne(id);
    }

    @Override
    public MaritalStatus findByName(String name) {
        return maritalStatusRepository.findByName(name);
    }

    @Override
    public MaritalStatus findByData(MaritalStatus maritalStatus) {
        return findByName(maritalStatus.getName());
    }

    @Override
    public List<MaritalStatus> findAll() {
        return maritalStatusRepository.findAll();
    }

    @Override
    public void save(MaritalStatus maritalStatus) {
        maritalStatusRepository.save(maritalStatus);
    }

    @Override
    public void deleteById(Integer id) {
        maritalStatusRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        maritalStatusRepository.deleteAll();
    }

    @Override
    public boolean isExist(MaritalStatus maritalStatus) {
        return findByData(maritalStatus) != null;
    }

}
