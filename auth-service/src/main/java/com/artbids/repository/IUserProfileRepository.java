package com.artbids.repository;

import com.artbids.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {


}
