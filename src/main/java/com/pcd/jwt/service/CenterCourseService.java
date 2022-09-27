package com.pcd.jwt.service;

import com.pcd.jwt.entity.CenterCourses;
import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.entity.User;
import com.pcd.jwt.repository.CenterCourseRepository;
import com.pcd.jwt.repository.CourseRepository;
import com.pcd.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CenterCourseService {
    private  final CenterCourseRepository centerCourseRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public CenterCourseService(CenterCourseRepository centerCourseRepository) {
        this.centerCourseRepository = centerCourseRepository;
    }
    public CenterCourses create(CenterCourses centerCourses) {
        return centerCourseRepository.save(centerCourses);
    }

    public  void CenterCourseUser(Long centerCourse_id,String user_id){
        CenterCourses centerCourses= centerCourseRepository.findById(centerCourse_id).get();

        User user =userRepository.findById(user_id).get();
        centerCourses.getUsersC().add(user);
        user.getCenterCourses().add(centerCourses);
        this.userRepository.save(user);
    }
    public void delete(Long id) {
        Optional<CenterCourses> course = centerCourseRepository.findById(id);
        course.ifPresent(centerCourseRepository::delete);
    }
    public List<CenterCourses> read() {
        return (List<CenterCourses>) centerCourseRepository.findAll();
    }





}
