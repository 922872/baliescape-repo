package com.heroku.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.heroku.java.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
