package com.heroku.java.controller;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.heroku.java.model.package;

@Controller
public class PackageController {
    private final DataSource dataSource;

    @Autowired
    public PackageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   

   @PostMapping("/CreatePack")
    public String createPack(@ModelAttribute("package")package package ){
       

        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO public.package(packID, packName, packActivity, packType, packPrice) VALUES(?,?,?,?,?)";
            final var statement = connection.prepareStatement(sql);

            Integer packID= package.getPackID();
            String packName= package.getPackName();
            String packActivity= package.getPackActivity();
            String packType= package.getPackType();
            double packagePrice = package.getPacktPrice();
            
            
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