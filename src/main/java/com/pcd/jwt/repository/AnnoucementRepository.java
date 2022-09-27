package com.pcd.jwt.repository;

import com.pcd.jwt.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnoucementRepository  extends JpaRepository<Announcement,Long> {
}
