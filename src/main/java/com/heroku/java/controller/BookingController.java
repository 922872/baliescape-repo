package com.heroku.java.controller;

import com.heroku.java.model.BookingRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingRequestController {

    private final DataSource dataSource;

    @Autowired
    public BookingRequestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/submitBookingRequest")
    public String submitBookingRequest(@ModelAttribute("bookingRequest") BookingRequestModel bookingRequest) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO booking_requests (customer_name, email, travel_date, package_type, no_of_pax, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, bookingRequest.getCustomerName());
            statement.setString(2, bookingRequest.getEmail());
            statement.setDate(3, bookingRequest.getTravelDate());
            statement.setString(4, bookingRequest.getPackageType());
            statement.setInt(5, bookingRequest.getNoOfPax());
            statement.setString(6, "Pending"); // Initial status

            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/submitBookingRequest";
    }

    @GetMapping("/bookingRequests")
    public String getAllBookingRequests(Model model) {
        List<BookingRequestModel> bookingRequests = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM booking_requests ORDER BY request_id";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BookingRequestModel bookingRequest = new BookingRequestModel();
                bookingRequest.setRequestId(resultSet.getInt("request_id"));
                bookingRequest.setCustomerName(resultSet.getString("customer_name"));
                bookingRequest.setEmail(resultSet.getString("email"));
                bookingRequest.setTravelDate(resultSet.getDate("travel_date"));
                bookingRequest.setPackageType(resultSet.getString("package_type"));
                bookingRequest.setNoOfPax(resultSet.getInt("no_of_pax"));
                bookingRequest.setStatus(resultSet.getString("status"));

                bookingRequests.add(bookingRequest);
            }

            model.addAttribute("bookingRequests", bookingRequests);

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/submitBookingRequest";
    }

    @PostMapping("/updateBookingRequest")
    public String updateBookingRequest(@ModelAttribute("bookingRequest") BookingRequestModel bookingRequest) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "UPDATE booking_requests SET customer_name = ?, email = ?, travel_date = ?, package_type = ?, " +
                         "no_of_pax = ?, status = ? WHERE request_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, bookingRequest.getCustomerName());
            statement.setString(2, bookingRequest.getEmail());
            statement.setDate(3, bookingRequest.getTravelDate());
            statement.setString(4, bookingRequest.getPackageType());
            statement.setInt(5, bookingRequest.getNoOfPax());
            statement.setString(6, bookingRequest.getStatus());
            statement.setInt(7, bookingRequest.getRequestId());

            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/submitBookingRequest";
    }

    @PostMapping("/deleteBookingRequest")
    public String deleteBookingRequest(@RequestParam("requestId") Integer requestId) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "DELETE FROM booking_requests WHERE request_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, requestId);

            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/submitBookingRequest";
    }
}
