package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.Country;
import ua.org.ubts.applicationssystem.entity.Residence;
import ua.org.ubts.applicationssystem.repository.ResidenceRepository;
import ua.org.ubts.applicationssystem.service.CountryService;
import ua.org.ubts.applicationssystem.service.ResidenceService;

import javax.transaction.Transactional;
import java.util.List;

@Service("residenceService")
@Transactional
public class ResidenceServiceImpl implements ResidenceService {

    @Autowired
    private ResidenceRepository residenceRepository;

    @Autowired
    private CountryService countryService;

    @Override
    public Residence findById(Long id) {
        return residenceRepository.findOne(id);
    }

    @Override
    public void save(Residence residence) {
        Country country = countryService.findByData(residence.getCountry());
        if (country != null) {
            residence.setCountry(country);
        }
        residenceRepository.save(residence);
    }

    @Override
    public void deleteById(Long id) {
        residenceRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        residenceRepository.deleteAll();
    }

    @Override
    public List<Residence> findAll() {
        return residenceRepository.findAll();
    }

}
