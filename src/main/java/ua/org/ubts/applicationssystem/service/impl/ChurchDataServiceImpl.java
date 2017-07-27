package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.ChurchData;
import ua.org.ubts.applicationssystem.repository.ChurchDataRepository;
import ua.org.ubts.applicationssystem.service.ChurchDataService;

import javax.transaction.Transactional;
import java.util.List;

@Service("churchDataService")
@Transactional
public class ChurchDataServiceImpl implements ChurchDataService {

    @Autowired
    private ChurchDataRepository churchDataRepository;

    @Override
    public ChurchData findById(Integer id) {
        return churchDataRepository.findOne(id);
    }

    @Override
    public List<ChurchData> findAll() {
        return churchDataRepository.findAll();
    }

    @Override
    public void save(ChurchData churchData) {
        churchDataRepository.save(churchData);
    }

    @Override
    public void deleteById(Integer id) {
        churchDataRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        churchDataRepository.deleteAll();
    }

}
