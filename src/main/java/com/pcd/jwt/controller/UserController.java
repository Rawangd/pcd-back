package com.pcd.jwt.controller;

import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.entity.User;
import com.pcd.jwt.exception.ResourceNotFoundException;
import com.pcd.jwt.repository.UserRepository;
import com.pcd.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@CrossOrigin
@RestController
public class UserController {
    public UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewStudent"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewStudent(user);
    }

    @PostMapping({"/registerNewFormer"})
    public User registerNewFormer(@RequestBody User former) {
        return userService.registerNewFormer(former);
    }

    @PostMapping({"/registerNewCenter"})
    public User registerNewCenter(@RequestBody User center) {
        return userService.registerNewCenter(center);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forStudent"})
    @PreAuthorize("hasRole('Student')")
    public String forUser(){
        return "This URL is only accessible to the student";
    }


    @GetMapping("/list")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/roles/{roleName}")
    public List<User> getUserByRole(@PathVariable String roleName){return  (List<User>) userRepository.findAll(roleName);}

    @GetMapping("/users/count/{roleName}")
    public Long getCountUser(@PathVariable String roleName) {
        return userRepository.CountUser(roleName);
    }

    @PutMapping("/users/update/{userName}")
    public int getUserFullName(@PathVariable String userName) {
        return userRepository.UpdateFullName(userName);
    }



    @GetMapping("/user/{Id}")
    public java.util.Optional<User> getUser(@PathVariable String Id) {
        return userRepository.findById(Id);
    }

    @GetMapping("/userEmail/{userEmail}")
    public List<User> getUserByEmail(@PathVariable String userEmail) {

        return userRepository.findUserByEmail(userEmail) ;
    }
    @RequestMapping(value = "/delete-user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) throws Exception {
        try {
            userService.delete(id);
            return ResponseEntity.ok().body("delete done");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/delete-userByEmail/{userEmail}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByEmail(@PathVariable(value = "userEmail") String userEmail) throws Exception {
        try {
            userService.delete(userEmail);
            return ResponseEntity.ok().body("delete done");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update/{UserId}")
    public User updateUser(@PathVariable String UserId, @Valid @RequestBody User userRequest) {
        return userRepository.findById(UserId).map(user -> {
            user.setUserName(userRequest.getUserName());
            user.setUserFullName(userRequest.getUserFullName());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserAddress(userRequest.getUserAddress());
            user.setUserPassword(userRequest.getUserPassword());
            user.setUserCountry(userRequest.getUserCountry());
            user.setUserTelephoneNumber(userRequest.getUserTelephoneNumber());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("StudentId " + UserId + " not found"));
    }




    @PutMapping("/profile/{userEmail}")
    public User updateUserProfil(@PathVariable String userEmail, @Valid @RequestBody User userRequest) {
        return userRepository.findByEmail(userEmail).map(user -> {
            user.setUserFullName(userRequest.getUserFullName());
            user.setUserName(userRequest.getUserName());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserAddress(userRequest.getUserAddress());
            user.setUserGender(userRequest.getUserGender());
            user.setUserCity(userRequest.getUserCity());
            user.setUserTelephoneNumber(userRequest.getUserTelephoneNumber());

            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("ClientId " + userEmail + " not found"));
    }
    @RequestMapping(value = "/saveImage/{userEmail}", method = RequestMethod.POST)

    public User uploadImage(@RequestParam("image") MultipartFile file, @PathVariable String userEmail) throws IOException, SQLException {
        /*String extension = FilenameUtils.getExtension(file.getOriginalFilename());*/
        System.out.println("Original Image Byte Size - " + file.getBytes().length + " name : "+ file.getOriginalFilename() +
                " type : "+ file.getContentType());
        User user = userRepository.findByEmail(userEmail).get();
        if(file!=null) {
            user.setImage(compressBytes(file.getBytes()));
        }


        return userRepository.save(user);
    }
    @GetMapping(path = { "/getImageByEmail/{userEmail}" })

    public User getImage(@PathVariable String userEmail) throws IOException {


        User user = userRepository.findByEmail(userEmail).get();

        if(user.getImage()!=null){
            user.setImage(decompressBytes(user.getImage()));
        }







        return user;

    }

    // compress the image bytes before storing it in the database

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();

        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }

        return outputStream.toByteArray();
    }
    @GetMapping("/UserByCourses/{id}")
    public  List<User> getUserByCourses(@PathVariable Long id){
        return  userRepository.findUserByCourses(id);
    }
}

