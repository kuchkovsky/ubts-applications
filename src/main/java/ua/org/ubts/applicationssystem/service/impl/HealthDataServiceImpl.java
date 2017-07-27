package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.HealthData;
import ua.org.ubts.applicationssystem.repository.HealthDataRepository;
import ua.org.ubts.applicationssystem.service.HealthDataService;

import javax.transaction.Transactional;
import java.util.List;

@Service("healthDataService")
@Transactional
public class HealthDataServiceImpl implements HealthDataService {

    @Autowired
    private HealthDataRepository healthDataRepository;

    @Override
    public HealthData findById(Integer id) {
        return healthDataRepository.findOne(id);
    }

    @Override
    public List<HealthData> findAll() {
        return healthDataRepository.findAll();
    }

    @Override
    public void save(HealthData healthData) {
        healthDataRepository.save(healthData);
    }

    @Override
    public void deleteById(Integer id) {
        healthDataRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        healthDataRepository.deleteAll();
    }

}
