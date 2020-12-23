package com.univer.topics.repository;

import com.univer.topics.entities.Group;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepository extends PagingAndSortingRepository<Group, Integer>, JpaSpecificationExecutor<Group> {

}
