package com.pcd.jwt.service;

import com.pcd.jwt.entity.Announcement;
import com.pcd.jwt.entity.CenterCourses;
import com.pcd.jwt.entity.Courses;
import com.pcd.jwt.repository.AnnoucementRepository;
import com.pcd.jwt.repository.CenterCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnnouncementService {
    private  final AnnoucementRepository annoucementRepository;
    @Autowired
    public AnnouncementService(AnnoucementRepository annoucementRepository) {
        this.annoucementRepository = annoucementRepository;
    }
    public Announcement create(Announcement announcement) {
        return annoucementRepository.save(announcement);
    }


    public List<Announcement> read() {
        return (List<Announcement>) annoucementRepository.findAll();
    }
    public void delete(Long id) {
        Optional<Announcement> course = annoucementRepository.findById(id);
        course.ifPresent(annoucementRepository::delete);
    }
}
