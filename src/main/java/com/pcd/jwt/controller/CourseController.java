package com.pcd.jwt.controller;

import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.repository.CourseRepository;
import com.pcd.jwt.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import java.awt.print.Pageable;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
public class CourseController {

    private final CourseService courseService ;

    private byte[] bytes;
    @Autowired
    ServletContext context;

    @Autowired
    private CourseRepository courseRepository ;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @PostMapping("/addCourses")
    public Courses addC(@RequestParam("price")Long price,@RequestParam("city")String city,
                        @RequestParam("phoneNumber")int phoneNumber, @RequestParam("courseName") String courseName,
                        @RequestParam("category") String category, @RequestParam("description") String description,
                        @RequestParam("formerName") String formerName, @RequestParam("formerEmail") String formerEmail,
                        @RequestParam("picture") MultipartFile file, MultipartHttpServletRequest request) throws IOException, ParseException {
        Courses courses = new Courses();
        courses.setPrice(price);
        courses.setCourseName(courseName);
        courses.setCategory(category);
        courses.setDescription(description);
        courses.setFormerEmail(formerEmail);
        courses.setFormerName(formerName);
        courses.setCity(city);
        courses.setPhoneNumber(phoneNumber);
        byte[] picture = file.getBytes();
        courses.setPicture(picture);
        log.info("The picture file has " + picture.length + " bytes");
        return courseService.create(courses);
    }
   /* @PostMapping("/addisFavorite/{Id}")
    public Courses addF(@RequestParam("isFavorite") Boolean isFavorite,@PathVariable Long Id)throws IOException, ParseException {

    }*/

    @GetMapping("/courses")
    public List<Courses> getAllC() {
        return courseService.read();
    }
    @GetMapping("/course/{id}")
    public java.util.Optional<Courses> getCourses(@PathVariable Long id) {
        return courseRepository.findById(id);
    }
    @GetMapping("/category/{category}")
    public List<Courses> getCourseByCategory(@PathVariable String category) {

        return courseRepository.findByCategory(category) ;
    }
    @GetMapping("/city/{city}")
    public List<Courses> getCourseByCity(@PathVariable String city ) {

        return courseRepository.findByCity(city) ;
    }
    @GetMapping("/email/{formerEmail}")
    public List<Courses> getCourseByEmail(@PathVariable String formerEmail) {

        return courseRepository.findCoursesByEmail(formerEmail) ;
    }
    @GetMapping("/favoriteCourses/{isFavorite}")
    public List<Courses> getCourseByEmail(@PathVariable Boolean isFavorite) {

        return courseRepository.findByFavorite(isFavorite) ;
    }
    @RequestMapping(value = "/delete-course/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            courseService.delete(id);
            return ResponseEntity.ok().body("delete done");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update-course/{id}")
    public  ResponseEntity<Courses> updateCourses(@RequestBody Courses course) {
        Courses updateCourses = courseService.updateCourses(course);
        return new ResponseEntity<>(updateCourses, HttpStatus.OK);
    }
    @PutMapping("/Favorite/{id}/{user}")
    public  void updateF(@PathVariable Long id,@PathVariable String user) {



       courseRepository.setIsFavoriteById(id,user);
    }
    @PostMapping("/Favorite/{id}/{user}/{isFavorite}")
    public  void updateF(@PathVariable Long id,@PathVariable String user,@PathVariable boolean isFavorite) {



        courseRepository.post(id,user,isFavorite);
    }
    @PutMapping("/isConfirmed/{id}")
    public  void updateF(@PathVariable String userName) {



        courseRepository.setIsConfirmed(userName);
    }
   /* @PostMapping("/insert/{id}/{user}/{isFavorite}")
    public  void insert(@PathVariable boolean isFavorite,@PathVariable String user,@PathVariable Long id) {



        courseRepository.insert(isFavorite,user,id);
    }*/
    @GetMapping("/courses/count")
    public Long getCourseCount() {
        return courseRepository.count();
    }

    @PutMapping("/updateCourses")
    public Courses modifyCourse(@RequestParam("price")Long price,@RequestParam("city")String city,
                              @RequestParam("phoneNumber")int phoneNumber, @RequestParam("courseName") String courseName,
                              @RequestParam("category") String category, @RequestParam("description") String description,
                              @RequestParam("formerName") String formerName, @RequestParam("formerEmail") String formerEmail,
                              @RequestParam("picture") MultipartFile file, MultipartHttpServletRequest request) throws IOException, ParseException {
        Courses courses=new Courses();
        courses.setPrice(price);
        courses.setCourseName(courseName);
        courses.setCategory(category);
        courses.setDescription(description);
        courses.setFormerEmail(formerEmail);
        courses.setFormerName(formerName);
        courses.setCity(city);
        courses.setPhoneNumber(phoneNumber);
        byte[] picture = file.getBytes();
        courses.setPicture(picture);
        return courseService.update(courses);
    }
    @GetMapping("/distinct-former")
    public List getDistinctCoursesByEmail( ) {

        return courseRepository.findDistinctCoursesByEmail() ;
    }
    @GetMapping("/picture/{formerEmail}")
    public List getPictureByEmail(@PathVariable String formerEmail ) {

        return courseRepository.findPictureByEmail(formerEmail) ;
    }
    @GetMapping("/allFavorite")
    public List getAllFavorite( ) {

        return courseRepository.findAllFavorite() ;
    }
    @GetMapping("/first5Favorite/{first5Favorite}")

        public List<Courses> getFirst5ByIsFavorite(@PathVariable boolean first5Favorite ){
        return courseRepository.findFirst5ByIsFavorite(first5Favorite) ;
    }
    @GetMapping("/CourseName/{CourseName}/{formerEmail}")
    public List<Courses> getFormerCourseName(@PathVariable String CourseName ,@PathVariable String formerEmail ) {

        return courseRepository.findByCourseAndFormer(CourseName,formerEmail) ;
    }

    @PostMapping("/courseUser/{course_id}/{user_id}")
    public void cu(@PathVariable Long course_id,@PathVariable String user_id){
        courseService.CourseUser(course_id,user_id);
    }
    @GetMapping("/courseByUser/{user_id}")
    public  List<Courses> getCoursesByUser(@PathVariable String user_id ){
        return  courseRepository.findCoursesByUser(user_id);
    }


}
