package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.PostPictureRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPictureRepository extends JpaRepository<PostPictureRegistry, Long> {
}
