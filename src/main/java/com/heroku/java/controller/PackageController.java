package com.heroku.java.controller;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.java.model.PackageModel;

@Controller
public class PackageController {
    private final DataSource dataSource;

    @Autowired
    public PackageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   

    @PostMapping("/UpdatePack")
    public String updatePack(@ModelAttribute("package") PackageModel packages) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "UPDATE public.package SET packname = ?, packactivity = ?, packtype = ?, packprice = ? WHERE packid = ?";
            final var statement = connection.prepareStatement(sql);
    
            Integer packID = packages.getPackID();
            String packName = packages.getPackName();
            String packActivity = packages.getPackActivity();
            String packType = packages.getPackType();
            double packPrice = packages.getPackPrice();
    
            statement.setString(1, packName);
            statement.setString(2, packActivity);
            statement.setString(3, packType);
            statement.setDouble(4, packPrice);
            statement.setInt(5, packID);
    
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    
        return "redirect:/ViewPack";
    }
     

    @PostMapping("/DeletePack")
    public String deletePack(@RequestParam("packID") Integer packID) {
       try {
        Connection connection = dataSource.getConnection();
        String sql = "DELETE FROM public.package WHERE packid = ?";
        final var statement = connection.prepareStatement(sql);

        statement.setInt(1, packID);

        statement.executeUpdate();
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/error";
      }

     return "redirect:/ViewPack";
  }
    
}