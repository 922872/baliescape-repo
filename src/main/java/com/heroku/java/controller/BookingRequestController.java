package com.heroku.java.controller;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.heroku.java.model.Booking;
import com.heroku.java.model.PackageForm;
import com.heroku.java.service.BookingManagementService;

@Controller
@RequestMapping("/customer")
public class BookingController {
  
  @Autowired
  private BookingManagementService bookingManagementService;

  @Autowired
  private DataSource dataSource;

  private List<PackageForm> packages = new ArrayList<>();
  
  // Add a new booking
  @PostMapping("/AddBooking")
  public String addBooking(@ModelAttribute Booking booking, Model model) {
    bookingManagementService.addBooking(booking);
    model.addAttribute("message", "Your booking has been added successfully!");
    return "confirmation";
  }

  // View all bookings
  @GetMapping("/ViewBooking")
  public String bookingSummary(Model model) {
    List<Booking> bookings = bookingManagementService.getAllBookings();
    model.addAttribute("bookings", bookings);
    return "booking-summary";
  }

  // Update an existing booking
  @PostMapping("/UpdateBooking")
  public String updateBooking(@ModelAttribute Booking booking, Model model) {
    bookingManagementService.updateBooking(booking);
    model.addAttribute("message", "Your booking has been updated successfully!");
    return "confirmation";
  }

  // Delete a booking
  @PostMapping("/DeleteBooking")
  public String deleteBooking(@RequestParam("bookingID") Integer bookingID, Model model) {
    try {
      bookingManagementService.deleteBooking(bookingID);
      model.addAttribute("message", "Booking deleted successfully.");
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:/error";
    }
    return "redirect:/customer/ViewBooking";
  }

  // Show update booking form
  @GetMapping("/ShowUpdateBookingForm")
  public String showForm(Model model) {
    model.addAttribute("packageForm", new PackageForm());
    model.addAttribute("packages", packages);
    return "index";
  }

  // Save package form
  @PostMapping("/SavePackageForm")
  public String savePackage(@ModelAttribute PackageForm packageForm) {
    packages.add(packageForm);
    return "redirect:/customer/ShowUpdateBookingForm";
  }
}

  @PostMapping("/")
  public String savePackage(PackageForm packageForm) {
    packages.add(packageForm);
    return "redirect:/";
  }
}
