package com.univer.topics.repository;

import com.univer.topics.entities.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Integer>, JpaSpecificationExecutor<Topic> {
    Page<Topic> findAllBygroupId(Pageable pageable, int groupId);
    long countAllByGroupId(int groupId);
    long countAllByStudentEquals(String student);
}
