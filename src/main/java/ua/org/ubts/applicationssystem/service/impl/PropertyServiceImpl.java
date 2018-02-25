package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.Property;
import ua.org.ubts.applicationssystem.repository.PropertyRepository;
import ua.org.ubts.applicationssystem.service.PropertyService;

import javax.transaction.Transactional;
import java.util.List;

@Service("propertyService")
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Property findByKey(String key) {
        return propertyRepository.findByKey(key);
    }

    @Override
    public Property findById(Integer id) {
        return propertyRepository.findOne(id);
    }

    @Override
    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    @Override
    public void save(Property element) {
        Property property = findByData(element);
        if (property != null) {
            element.setId(property.getId());
        }
        propertyRepository.save(element);
    }

    @Override
    public void deleteById(Integer id) {
        propertyRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        propertyRepository.deleteAll();
    }

    @Override
    public Property findByData(Property element) {
        return findByKey(element.getKey());
    }

    @Override
    public boolean isExist(Property element) {
        return findByData(element) != null;
    }

}
