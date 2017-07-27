package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.ChurchMinistryType;
import ua.org.ubts.applicationssystem.repository.ChurchMinistryTypeRepository;
import ua.org.ubts.applicationssystem.service.ChurchMinistryTypeService;

import javax.transaction.Transactional;
import java.util.List;

@Service("churchMinistryTypeService")
@Transactional
public class ChurchMinistryTypeServiceImpl implements ChurchMinistryTypeService {

    @Autowired
    private ChurchMinistryTypeRepository churchMinistryTypeRepository;

    @Override
    public ChurchMinistryType findById(Integer id) {
        return churchMinistryTypeRepository.findOne(id);
    }

    @Override
    public ChurchMinistryType findByName(String name) {
        return churchMinistryTypeRepository.findByName(name);
    }

    @Override
    public ChurchMinistryType findByData(ChurchMinistryType churchMinistryType) {
        return findByName(churchMinistryType.getName());
    }

    @Override
    public List<ChurchMinistryType> findAll() {
        return churchMinistryTypeRepository.findAll();
    }

    @Override
    public void save(ChurchMinistryType churchMinistryType) {
        churchMinistryTypeRepository.save(churchMinistryType);
    }

    @Override
    public void deleteById(Integer id) {
        churchMinistryTypeRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        churchMinistryTypeRepository.deleteAll();
    }

    @Override
    public boolean isExist(ChurchMinistryType churchMinistryType) {
        return findByData(churchMinistryType) != null;
    }

}
