booking com.heroku.java.controller;

import java.sql.Connection;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.java.model.BookingModel;
@Controller
@RequestMapping("/customer")
public class BookingController {
  
  @Autowired
  private BookingService bookingService;
  
  @PostMapping("/AddBooking")
  public String addBooking(@ModelAttribute Booking booking, Model model) {
    // Call the booking service to add the booking
    bookingService.addBooking(booking);
    
    // Set a success message
    model.addAttribute("message", "Your booking has been added successfully!");
    
    // Return the confirmation page
    return "confirmation";
  }
}
@Controller
public class BookingSummaryController {
  
  @GetMapping("/ViewBooking")
  public String bookingSummary(Model model) {
    // Retrieve the list of bookings from the database (you'll need to implement this logic)
    List<Booking> bookings = bookingService.getAllBookings();
    
    // Add the bookings to the model
    model.addAttribute("bookings", bookings);
    
    // Return the booking summary view
    return "booking-summary";
  }
}

    @PostMapping("/DeleteBooking")
    public String deleteBooking(@RequestParam("bookingID") Integer bookingID) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE FROM bookings WHERE bookingid = ?";
            final var statement = connection.prepareStatement(sql);
    
            statement.setInt(1, bookingID);
    
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    
        return "redirect:/AddBooking";
    }
}

@Controller
public class PackageController {
  private List<PackageForm> packages = new ArrayList<>();

  @GetMapping("UpdateBooking")
  public String showForm(Model model) {
    model.addAttribute("packageForm", new PackageForm());
    model.addAttribute("packages", packages);
    return "index";
  }

  @PostMapping("/")
  public String savePackage(PackageForm packageForm) {
    packages.add(packageForm);
    return "redirect:/";
  }
}
