package com.heroku.java.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heroku.java.model.Booking;
import com.heroku.java.repository.BookingRepository;

@Service
public class BookingManagementService {

  @Autowired
  private BookingRepository bookingRepository;

  public void addBooking(Booking booking) {
    bookingRepository.save(booking);
  }

  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  public void updateBooking(Booking booking) {
    bookingRepository.save(booking);
  }

  public void deleteBooking(Integer bookingID) {
    bookingRepository.deleteById(bookingID);
  }
}
