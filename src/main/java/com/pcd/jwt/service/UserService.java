package com.pcd.jwt.service;

import com.pcd.jwt.entity.Role;
import com.pcd.jwt.repository.RoleRepository;
import com.pcd.jwt.repository.UserRepository;
import com.pcd.jwt.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    MultipartFile file;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   


    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("Student");
        userRole.setRoleDescription("Student role");
        roleRepository.save(userRole);

       Role formerRole = new Role();
        formerRole.setRoleName("Former");
        formerRole.setRoleDescription("Former role");
        roleRepository.save(formerRole);

        Role centerRole =new Role();
        centerRole.setRoleName("Center");
        centerRole.setRoleDescription("Center role");
        roleRepository.save(centerRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFullName("admin");
        adminUser.setUserEmail("admin@gmail.com");
        adminUser.setUserAddress("ruemzali");
        adminUser.setUserCountry("tunisie");
        adminUser.setUserBirthday("30/10/199");
        adminUser.setUserCity("monastir");
        adminUser.setUserGender("female");
        adminUser.setUserPostalCode(5000);
        adminUser.setUserTelephoneNumber(24029262);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);


        User user = new User();
        user.setUserName("raj123");
        user.setUserFullName("raj");

        user.setUserPassword(getEncodedPassword("123"));
        user.setUserAddress("ruemzali");
        user.setUserCountry("tunisie");
        user.setUserEmail("raj123@gmail.com");
        user.setUserBirthday("1999/10/30");
        user.setUserCity("monastir");
        user.setUserGender("female");
        user.setUserPostalCode(5000);
        user.setUserTelephoneNumber(24029262);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userRepository.save(user);

//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public User registerNewStudent(User user)   {
        Role role = roleRepository.findById("Student").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        user.setUserName(user.getUserName());
        user.setUserGender(user.getUserGender());
        user.setUserEmail(user.getUserEmail());
        user.setUserCity(user.getUserCity());
        user.setUserAddress(user.getUserAddress());
        user.setUserTelephoneNumber(user.getUserTelephoneNumber());
        user.setUserCountry(user.getUserCountry());
        user.setUserPostalCode(user.getUserPostalCode());
        user.setUserFullName(user.getUserFullName());
        user.setUserBirthday(user.getUserBirthday());


        return userRepository.save(user);
    }

    public User registerNewFormer(User former){
        Role role = roleRepository.findById("Former").get();
        Set<Role> formerRoles = new HashSet<>();
        formerRoles.add(role);
        former.setRole(formerRoles);
        former.setUserPassword(getEncodedPassword(former.getUserPassword()));
        former.setUserName(former.getUserName());
        former.setUserGender(former.getUserGender());
        former.setUserEmail(former.getUserEmail());
        former.setUserCity(former.getUserCity());
        former.setUserAddress(former.getUserAddress());
        former.setUserTelephoneNumber(former.getUserTelephoneNumber());
        former.setUserCountry(former.getUserCountry());
        former.setUserPostalCode(former.getUserPostalCode());
        former.setUserFullName(former.getUserFullName());
        former.setUserBirthday(former.getUserBirthday());
        former.setUserLevel(former.getUserLevel());
        former.setUserDomain(former.getUserDomain());
        former.setUserDescription(former.getUserDescription());
        former.setUserExperience(former.getUserExperience());


        return userRepository.save(former);
    }
    public User registerNewCenter(User center){
        Role role = roleRepository.findById("Center").get();
        Set<Role> centerRoles = new HashSet<>();
        centerRoles.add(role);
        center.setRole(centerRoles);
        center.setUserPassword(getEncodedPassword(center.getUserPassword()));
        center.setUserName(center.getUserName());
        center.setCenterDirectorName(center.getCenterDirectorName());
        center.setUserEmail(center.getUserEmail());
        center.setUserCity(center.getUserCity());
        center.setUserAddress(center.getUserAddress());
        center.setUserTelephoneNumber(center.getUserTelephoneNumber());
        center.setUserCountry(center.getUserCountry());
        center.setUserPostalCode(center.getUserPostalCode());
        center.setCenterName(center.getCenterName());
        center.setUserDescription(center.getUserDescription());


        return userRepository.save(center);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public void delete(String id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

}
