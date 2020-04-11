package ua.org.ubts.applications.service;

import ua.org.ubts.applications.entity.QuestionEntity;

import java.util.List;

public interface QuestionService {
    List<QuestionEntity> getQuestions();
}