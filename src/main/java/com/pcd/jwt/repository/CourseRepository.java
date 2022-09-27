package com.pcd.jwt.repository;

import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses,Long> {
  /*  @Query("SELECT e FROM Courses e WHERE e.formerEmail=?1")
    List<Courses> findCoursesByEmail(String formerEmail);

    @Query("SELECT c from Courses c  WHERE c.category= ?1 ")
    List<Courses> findByCategory(String category);
    @Query("SELECT c from Courses c  WHERE c.category LIKE %:title%" )
    List<Courses> findAllCategory(String category);

*/
  List<Courses> findFirst5ByIsFavorite(boolean isFavorite);
  @Query("SELECT fc from Courses fc  WHERE fc.CourseName= ?1 and fc.formerEmail=?2")
  List<Courses> findByCourseAndFormer(String CourseName,String formerEmail);


  @Query("SELECT c from Courses c  WHERE c.category= ?1 ")
  List<Courses> findByCategory(String category);
  @Query("SELECT ci from Courses ci  WHERE ci.city= ?1 ")
  List<Courses> findByCity(String city);
  @Query("SELECT e FROM Courses e WHERE e.formerEmail=?1")
  List<Courses> findCoursesByEmail(String formerEmail);
  @Query("SELECT DISTINCT ce.formerEmail, ce.picture , ce.formerName FROM Courses ce")
  List findDistinctCoursesByEmail();
  @Query("SELECT DISTINCT  p.picture  FROM Courses p where p.formerEmail=?1 ")
  List findPictureByEmail(String formerEmail);
  @Query("SELECT f from Courses f where f.isFavorite=?1")
  List<Courses> findByFavorite(Boolean isFavorites);
  @Query("Select f.isFavorite,f.user from Courses f where f.isFavorite=:isFavorite and f.user=:user and f.id=:id ")
  void post(@Param(value = "id") long id, @Param(value = "user") String user,@Param(value = "isFavorite") boolean isFavorite);
  @Transactional
  @Modifying
  @Query("update Courses cf set cf.isFavorite =true ,cf.user=:user where cf.id = :id")
  void setIsFavoriteById(@Param(value = "id") long id, @Param(value = "user") String user);
  @Query("SELECT fc from Courses fc where fc.isFavorite=true ")
  List<Courses> findAllFavorite();


  @Query("SELECT c FROM Courses c JOIN c.users")
  List<Courses> findCoursesByUser(String userName);
 @Query("  UPDATE Courses c1 SET c1.isPresent = true WHERE c1.id in ( SELECT c2.id FROM Courses c2 JOIN c2.users u1 WHERE u1.userName LIKE %?1%  )")
  void setIsConfirmed(@Param(value="userName") String  userName);

/*
  @Modifying
  @Query(value = "insert into Courses c (c.isFavorite,c.user) VALUES (:isFavorite,:user) where c.id=?1", nativeQuery = true )
  @Transactional
  void insert (@Param("isFavorite") boolean isFavorite,@Param("user") String user,  @Param("id") Long id);*/
}

