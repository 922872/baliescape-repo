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


}