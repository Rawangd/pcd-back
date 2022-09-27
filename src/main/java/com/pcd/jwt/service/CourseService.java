package com.pcd.jwt.service;

import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.entity.User;
import com.pcd.jwt.repository.CourseRepository;
import com.pcd.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

@Autowired
public UserRepository userRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public List<Courses> read() {
        return (List<Courses>) courseRepository.findAll();
    }
    public void delete(Long id) {
        Optional<Courses> course = courseRepository.findById(id);
        course.ifPresent(courseRepository::delete);
    }
    public Courses updateCourses(Courses course){
        return courseRepository.save(course);
    }

    public Courses create(Courses course) {
        return courseRepository.save(course);
    }

    public List<Courses> findAllCourses(){
        return (List<Courses>) courseRepository.findAll();
    }
    public Courses update(Courses courses) {
        return courseRepository.save(courses);
    }


    public  void CourseUser(Long course_id,String user_id){
        Courses courses = courseRepository.findById(course_id).get();

        User user =userRepository.findById(user_id).get();
        courses.getUsers().add(user);
        user.getCourses().add(courses);
        this.userRepository.save(user);
    }

}