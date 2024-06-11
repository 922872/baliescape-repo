package com.heroku.java.controller;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.heroku.java.model.PackageModel;

@Controller
public class PackageController {
    private final DataSource dataSource;

    @Autowired
    public PackageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   

   @PostMapping("/CreatePack")
    public String createPack(@ModelAttribute("package")PackageModel packages ){
       

        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO public.package(packid, packname, packactivity, packtype, packprice) VALUES(?,?,?,?,?)";
            final var statement = connection.prepareStatement(sql);

            Integer packID= packages.getPackID();
            String packName= packages.getPackName();
            String packActivity= packages.getPackActivity();
            String packType= packages.getPackType();
            double packPrice = packages.getPackPrice();
            
            
            statement.setInt(1, packID);
            statement.setString(2, packName);
            statement.setString(3, packActivity);
            statement.setString(4, packType);
            statement.setDouble(5, packPrice);
           
            statement.executeUpdate();
            
            connection.close();
                
                } catch (Exception e) {
                    e.printStackTrace();
            
                  
                    return "redirect:/error";
                }
       
                 
            return "redirect:/index";
         }


         @PostMapping("/UpdatePack")
    public String updatePack(@ModelAttribute("package")PackageModel packages ){
       

        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO public.package(packid, packname, packactivity, packtype, packprice) VALUES(?,?,?,?,?)";
            final var statement = connection.prepareStatement(sql);

            Integer packID= packages.getPackID();
            String packName= packages.getPackName();
            String packActivity= packages.getPackActivity();
            String packType= packages.getPackType();
            double packPrice = packages.getPackPrice();
            
            
            statement.setInt(1, packID);
            statement.setString(2, packName);
            statement.setString(3, packActivity);
            statement.setString(4, packType);
            statement.setDouble(5, packPrice);
           
            statement.executeUpdate();
            
            connection.close();
                
                } catch (Exception e) {
                    e.printStackTrace();
            
                  
                    return "redirect:/error";
                }
       
                 
            return "redirect:/index";
         }


         @GetMapping("/DeletePack")
         public String deletePack(HttpSession session, Model model, @RequestParam("serviceID") int serviceID){
             String packID = (String) session.getAttribute("packID");
             int reservationID = (int) session.getAttribute("reservationID");
             int durationOfStay = (int) session.getAttribute("durationOfStay");
             Date dateStart = (Date) session.getAttribute("dateStart");
             Date dateEnd = (Date) session.getAttribute("dateEnd");
             double totalPayment = (double) session.getAttribute("totalPayment");
             int totalRoom = (int) session.getAttribute("totalRoom");
     
             //for debugging purposes only
             System.out.println("guestICNumber: " + guestICNumber);
             System.out.println("reservationID: " + reservationID);
             System.out.println("durationOfStay: " + durationOfStay);
             System.out.println("dateStart: " + dateStart);
             System.out.println("dateEnd: " + dateEnd);
             System.out.println("totalPayment: " + totalPayment);
             System.out.println("total room: " + totalRoom);
             System.out.println("service id: " + serviceID);
     
             try (Connection connection = dataSource.getConnection()){
                 //delete servvice from the guest reservation list
                 final var deleteServiceStatement = connection.prepareStatement("DELETE from reservationservice where reservationid = ? AND serviceid = ?");
                 deleteServiceStatement.setInt(1, reservationID);
                 deleteServiceStatement.setInt(2, serviceID);
                 deleteServiceStatement.executeUpdate();
                 connection.close();
                 System.out.println("succeed to delete service from the guest service list");
             }
             catch (Exception e){
                 System.out.println("failed to delete service from the guest service list");
                 e.printStackTrace();
             }
             return "redirect:/guestMakeRoomService";
         }

}