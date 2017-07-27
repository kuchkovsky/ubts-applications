package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.MaritalData;
import ua.org.ubts.applicationssystem.entity.MaritalStatus;
import ua.org.ubts.applicationssystem.repository.MaritalDataRepository;
import ua.org.ubts.applicationssystem.service.MaritalDataService;
import ua.org.ubts.applicationssystem.service.MaritalStatusService;

import javax.transaction.Transactional;
import java.util.List;

@Service("maritalDataService")
@Transactional
public class MaritalDataServiceImpl implements MaritalDataService {

    @Autowired
    private MaritalDataRepository maritalDataRepository;

    @Autowired
    private MaritalStatusService maritalStatusService;

    @Override
    public MaritalData findById(Integer id) {
        return maritalDataRepository.findOne(id);
    }

    @Override
    public List<MaritalData> findAll() {
        return maritalDataRepository.findAll();
    }

    @Override
    public void save(MaritalData maritalData) {
        MaritalStatus status = maritalStatusService.findByData(maritalData.getStatus());
        if (status != null) {
            maritalData.setStatus(status);
        }
        maritalDataRepository.save(maritalData);
    }

    @Override
    public void deleteById(Integer id) {
        maritalDataRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        maritalDataRepository.deleteAll();
    }

}
