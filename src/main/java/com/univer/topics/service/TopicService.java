package com.univer.topics.service;

import com.univer.topics.entities.Topic;
import com.univer.topics.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findALlByGroupId(int pageNumber, int itemsPerPage, int groupId) {
        ArrayList<Topic> topics = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber - 1, itemsPerPage, Sort.by("id").ascending());
        topicRepository.findAllBygroupId(pageable, groupId).forEach(topics::add);
        return topics;
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic findById(int id) {
        return topicRepository.findById(id).orElseThrow();
    }

    public long countAllByStudentEquals(String studentName) {
        return topicRepository.countAllByStudentEquals(studentName);
    }

    public long count(int groupId) {
        return topicRepository.countAllByGroupId(groupId);
    }
}
