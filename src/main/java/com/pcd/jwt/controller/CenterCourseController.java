package com.pcd.jwt.controller;

import com.pcd.jwt.entity.CenterCourses;
import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.repository.CenterCourseRepository;
import com.pcd.jwt.service.CenterCourseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin

public class CenterCourseController {
    private final CenterCourseService centerCourseService;
    @Autowired
    ServletContext context;

    @Autowired
    private CenterCourseRepository centerCourseRepository;

    @Autowired
    public CenterCourseController(CenterCourseService centerCourseService) {
        this.centerCourseService = centerCourseService;
    }
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/addCenterCourses")
    public CenterCourses addC(@RequestParam("price")Long price, @RequestParam("city")String city,
                              @RequestParam("phoneNumber")int phoneNumber, @RequestParam("courseName") String courseName, @RequestParam("address") String address,
                              @RequestParam("formerPhoneNumber")int formerPhoneNumber, @RequestParam("formerEmail") String formerEmail,@RequestParam("date") String date,
                              @RequestParam("category") String category, @RequestParam("description") String description,
                              @RequestParam("formerName") String formerName, @RequestParam("centerEmail") String centerEmail,
                              @RequestParam("centerName") String centerName, @RequestParam("picture") MultipartFile file, MultipartHttpServletRequest request) throws IOException, ParseException {
        CenterCourses centerCourses = new CenterCourses();
        centerCourses.setPrice(price);
        centerCourses.setCourseName(courseName);
        centerCourses.setCategory(category);
        centerCourses.setDescription(description);
        centerCourses.setCenterEmail(centerEmail);
        centerCourses.setFormerName(formerName);
        centerCourses.setCity(city);
        centerCourses.setPhoneNumber(phoneNumber);
        centerCourses.setFormerPhoneNumber(formerPhoneNumber);
        centerCourses.setFormerEmail(formerEmail);
        centerCourses.setCenterName(centerName);
        centerCourses.setAddress(address);
        centerCourses.setDate(date);
        byte[] picture = file.getBytes();
        centerCourses.setPicture(picture);
        log.info("The picture file has " + picture.length + " bytes");

        return centerCourseService.create(centerCourses);
    }
    @GetMapping("/centerCourses")
    public List<CenterCourses> getAllC() {
        return centerCourseService.read();
    }
    @GetMapping("/centerCategory/{category}")
    public List<CenterCourses> getCourseByCategory(@PathVariable String category) {

        return centerCourseRepository.findByCenterCategory(category) ;
    }
    @GetMapping("/centerCity/{city}")
    public List<CenterCourses> getCenterCourseByCity(@PathVariable String city ) {

        return centerCourseRepository.findByCity(city) ;
    }
    @PostMapping("/centerCourseUser/{centerCourse_id}/{user_id}")
    public void ccu(@PathVariable Long centerCourse_id,@PathVariable String user_id){
        centerCourseService.CenterCourseUser(centerCourse_id,user_id);
    }
    @GetMapping("/centerCourseByUser/{user_id}")
    public  List<CenterCourses> getCoursesByUser(@PathVariable String user_id ){
        return centerCourseRepository.findCenterCoursesByUser(user_id);
    }
    @GetMapping("/centerFormerEmail/{formerEmail}")
    public List<CenterCourses> getCenterCourseByEmail(@PathVariable String formerEmail) {

        return centerCourseRepository.findCenterCoursesByEmail(formerEmail) ;
    }
    @RequestMapping(value = "/delete-centerCourse/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            centerCourseService.delete(id);
            return ResponseEntity.ok().body("delete done");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
   /* @GetMapping("/centerFormerEmail/{formerEmail}")
    public List<Courses> getCourseByEmail(@PathVariable String formerEmail) {

        return courseRepository.findCoursesByEmail(formerEmail) ;
    }*/
}
