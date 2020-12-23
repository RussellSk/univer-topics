package com.univer.topics.controllers;

import com.univer.topics.entities.Group;
import com.univer.topics.entities.Topic;
import com.univer.topics.models.Student;
import com.univer.topics.service.GroupService;
import com.univer.topics.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {
    private final int ITEMS_PER_PAGE = 35;

    @Value("${site.title}")
    private String title;

    private final GroupService groupService;
    private final TopicService topicService;

    @Autowired
    public GroupController(GroupService groupService, TopicService topicService) {
        this.groupService = groupService;
        this.topicService = topicService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        List<Group> groups = groupService.findAll(1, ITEMS_PER_PAGE);
        model.addAttribute("title", title);
        model.addAttribute("groups", groups);
        return "index";
    }

    @GetMapping(value = "/group/{id}")
    public String group(@PathVariable Integer id, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Group group = groupService.findById(id);
        List<Topic> topics = topicService.findALlByGroupId(page, ITEMS_PER_PAGE, id);
        System.out.println("groupid: " + id);
        long count = topicService.count(id);
        boolean hasPrev = page > 1;
        boolean hasNext = ((long) page * ITEMS_PER_PAGE) < count;
        boolean hasPages = ITEMS_PER_PAGE < count;

        List<PageItem> pages = new ArrayList<>();
        for (int i = 1; i <= (int) Math.ceil((double) count / ITEMS_PER_PAGE); i++) {
            pages.add(new PageItem(i, page == i));
        }

        model.addAttribute("title", title);
        model.addAttribute("id", id);
        model.addAttribute("groupName", group.getName());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasPages", hasPages);
        model.addAttribute("pages", pages);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("topics", topics);
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("nextPage", page + 1);
        return "group";
    }

    @PostMapping(value = "/group/select/{id}")
    public RedirectView saveChanges(@PathVariable Integer id, @ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            Topic topic = topicService.findById(student.getTopicId());

            if (topic.getStudent() != null) {
                throw new Exception("Mavzu boshqa talaba tomonidan tanlangan");
            }

            if (topicService.countAllByStudentEquals(student.getStudentName()) > 0) {
                throw new Exception("Siz ikkita mavzuni ololmaysiz");
            }

            topic.setStudent(student.getStudentName());
            topic.setUpdated_at(new Date(System.currentTimeMillis()));
            topicService.save(topic);
            redirectAttributes.addFlashAttribute("flash-success", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flash-error", true);
            redirectAttributes.addFlashAttribute("flash-message", e.getMessage());
        }
        return new RedirectView("/group/" + id);
    }

    class PageItem {
        private Integer page;
        private boolean current;

        public PageItem(Integer page, boolean currentPage) {
            this.page = page;
            this.current = currentPage;
        }

        public boolean isCurrent() {
            return current;
        }

        public void setCurrent(boolean current) {
            this.current = current;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }
    }
}
