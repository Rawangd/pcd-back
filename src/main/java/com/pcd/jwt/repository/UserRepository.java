package com.pcd.jwt.repository;

import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {


    @Query("SELECT e FROM User e WHERE e.userEmail=?1")
    List<User> findUserByEmail(String userEmail);
    @Query("SELECT e FROM User e WHERE e.userEmail=?1")
    Optional<User> findByEmail(String userEmail);
    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName LIKE %?1%")
    public List<User> findAll(String roleName);
    @Query("SELECT count(u) FROM User u JOIN u.role r WHERE r.roleName LIKE %?1%")
    public Long CountUser(String roleName);
    @Modifying
    @Query("update User u set u.userPostalCode=?1 where u.userName LIKE %?1%")
    int UpdateFullName(String userName);
    @Query("SELECT c FROM User c JOIN c.courses u WHERE u.id=?1 ")
    List<User> findUserByCourses(@Param(value="id") Long  id);
}
