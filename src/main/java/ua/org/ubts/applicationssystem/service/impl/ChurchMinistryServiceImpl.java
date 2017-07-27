package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.ChurchMinistry;
import ua.org.ubts.applicationssystem.entity.ChurchMinistryType;
import ua.org.ubts.applicationssystem.repository.ChurchMinistryRepository;
import ua.org.ubts.applicationssystem.service.ChurchMinistryService;
import ua.org.ubts.applicationssystem.service.ChurchMinistryTypeService;

import javax.transaction.Transactional;
import java.util.List;

@Service("churchMinistryService")
@Transactional
public class ChurchMinistryServiceImpl implements ChurchMinistryService {

    @Autowired
    private ChurchMinistryRepository churchMinistryRepository;

    @Autowired
    private ChurchMinistryTypeService churchMinistryTypeService;

    @Override
    public ChurchMinistry findById(Integer id) {
        return churchMinistryRepository.findOne(id);
    }

    @Override
    public List<ChurchMinistry> findAll() {
        return churchMinistryRepository.findAll();
    }

    @Override
    public void save(ChurchMinistry churchMinistry) {
        ChurchMinistryType type = churchMinistryTypeService.findByData(churchMinistry.getType());
        if (type != null) {
            churchMinistry.setType(type);
        }
        churchMinistryRepository.save(churchMinistry);
    }

    @Override
    public void deleteById(Integer id) {
        churchMinistryRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        churchMinistryRepository.deleteAll();
    }

}
