package ua.org.ubts.applications.service.impl;

        import org.springframework.context.annotation.Primary;
        import org.springframework.transaction.annotation.Transactional;
        import ua.org.ubts.applications.entity.QuestionEntity;
        import ua.org.ubts.applications.entity.RoleEntity;
        import ua.org.ubts.applications.entity.UserEntity;
        import ua.org.ubts.applications.repository.QuestionRepository;
        import ua.org.ubts.applications.repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;
        import ua.org.ubts.applications.service.QuestionService;

        import java.util.ArrayList;
        import java.util.List;

@Service
@Transactional
@Primary
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionEntity> getQuestions(){
        return questionRepository.findAll();
    }
}
