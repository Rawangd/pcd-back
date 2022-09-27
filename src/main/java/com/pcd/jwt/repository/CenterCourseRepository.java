package com.pcd.jwt.repository;

import com.pcd.jwt.entity.CenterCourses;

import com.pcd.jwt.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterCourseRepository  extends JpaRepository<CenterCourses,Long> {
    @Query("SELECT c from CenterCourses c  WHERE c.category= ?1 ")
    List<CenterCourses> findByCenterCategory(String category);

    @Query("SELECT c FROM CenterCourses c JOIN c.usersC u WHERE u.userName LIKE %?1%")
    List<CenterCourses> findCenterCoursesByUser(String userName);
    @Query("SELECT ci from CenterCourses ci  WHERE ci.city= ?1 ")
    List<CenterCourses> findByCity(String city);
    @Query("SELECT e FROM CenterCourses e WHERE e.formerEmail=?1")
    List<CenterCourses> findCenterCoursesByEmail(String formerEmail);
}
