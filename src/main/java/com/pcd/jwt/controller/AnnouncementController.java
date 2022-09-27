package com.pcd.jwt.controller;

import com.pcd.jwt.entity.Announcement;
import com.pcd.jwt.entity.CenterCourses;
import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.repository.AnnoucementRepository;
import com.pcd.jwt.service.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
public class AnnouncementController {
    private final AnnouncementService announcementService;
    @Autowired
    ServletContext context;

    @Autowired
    private AnnoucementRepository annoucementRepository;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @PostMapping("/addAnnouncement")
    public Announcement addA(@RequestParam("salary")Long salary, @RequestParam("city")String city,
                              @RequestParam("phoneNumber")int phoneNumber, @RequestParam("address") String address,
                              @RequestParam("jobTitle") String jobTitle, @RequestParam("description") String description,
                              @RequestParam("language") String language, @RequestParam("centerEmail") String centerEmail,
                              @RequestParam("experience") String experience,@RequestParam("centerName") String centerName,
                              @RequestParam("domain") String domain,
                              MultipartHttpServletRequest request) throws IOException, ParseException {
        Announcement announcement = new Announcement();
        announcement.setSalary(salary);
        announcement.setCity(city);
        announcement.setPhoneNumber(phoneNumber);
        announcement.setDescription(description);
        announcement.setCenterEmail(centerEmail);
        announcement.setJobTitle(jobTitle);
        announcement.setLanguage(language);
        announcement.setCenterEmail(centerEmail);
        announcement.setCenterName(centerName);
        announcement.setAddress(address);
        announcement.setExperience(experience);



        return announcementService.create(announcement);
    }
    @GetMapping("/announcements")
    public List<Announcement> getAllA() {
        return announcementService.read();
    }
    @RequestMapping(value = "/delete-announcement/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            announcementService.delete(id);
            return ResponseEntity.ok().body("delete done");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

