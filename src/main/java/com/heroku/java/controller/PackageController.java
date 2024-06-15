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

    @PostMapping("/staffPack")
    public String createPack(@ModelAttribute("package") PackageModel packages) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO public.package(packid, packname, packactivity, packprice) VALUES(?,?,?,?)";
            final var statement = connection.prepareStatement(sql);
    
            Integer packID = packages.getPackID();
            String packName = packages.getPackName();
            String packActivity = packages.getPackActivity();
            double packPrice = packages.getPackPrice();
    
            statement.setInt(1, packID);
            statement.setString(2, packName);
            statement.setString(3, packActivity);
            statement.setDouble(5, packPrice);

    
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    
        return "redirect:/staffPack";
    }
     

    @PostMapping("/staffUpdatePack")
    public String updatePack(@ModelAttribute("package") PackageModel packages) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "UPDATE public.package SET packname = ?, packactivity = ?, packprice = ? WHERE packid = ?";
            final var statement = connection.prepareStatement(sql);
    
            Integer packID = packages.getPackID();
            String packName = packages.getPackName();
            String packActivity = packages.getPackActivity();
            double packPrice = packages.getPackPrice();
    
            statement.setString(1, packName);
            statement.setString(2, packActivity);
            statement.setDouble(4, packPrice);
            statement.setInt(5, packID);
    
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    
        return "redirect:/staffPack";
    }
     

    @PostMapping("/staffDeletePack")
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

     return "redirect:/staffPack";
  }
    
}