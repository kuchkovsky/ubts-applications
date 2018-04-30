package ua.org.ubts.applications.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.entity.ProgramEntity;
import ua.org.ubts.applications.repository.ProgramRepository;
import ua.org.ubts.applications.service.ProgramService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepository programRepository;


    @Override
    public List<ProgramEntity> getPrograms() {
        return programRepository.findAll();
    }

}
