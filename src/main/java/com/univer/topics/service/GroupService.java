package com.univer.topics.service;

import com.univer.topics.entities.Group;
import com.univer.topics.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll(int pageNumber, int itemsPerPage) {
        ArrayList<Group> groups = new ArrayList<>();
        Pageable sortedAndPageable = PageRequest.of(pageNumber - 1 , itemsPerPage, Sort.by("id").ascending());
        groupRepository.findAll(sortedAndPageable).forEach(groups::add);
        return groups;
    }

    public Group findById(int id) {
        return groupRepository.findById(id).orElseThrow();
    }
}
