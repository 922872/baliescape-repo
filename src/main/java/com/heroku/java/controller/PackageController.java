package com.heroku.java.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/CreatePack")
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
            statement.setDouble(4, packPrice);

    
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    
        return "redirect:/CreatePack";
    }
     
    @GetMapping("/packageList")
    public String packageList(Model model) {
        List<PackageModel> packages = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT packid, packname, packactivity, packprice FROM public.package ORDER BY packid";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer packID = resultSet.getInt("packid");
                String packName = resultSet.getString("packname");
                String packActivity = resultSet.getString("packactivity");
                double packPrice = resultSet.getDouble("packprice");

                PackageModel packageModel = new PackageModel();
                packageModel.setPackID(packID);
                packageModel.setPackName(packName);
                packageModel.setPackActivity(packActivity);
                packageModel.setPackPrice(packPrice);

                packages.add(packageModel);
            }

            model.addAttribute("packages", packages);

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/CreatePack"; 
    }
}


    
    @PostMapping("/UpdatePack")
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
            statement.setDouble(3, packPrice);
            statement.setInt(4, packID);
    
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    
        return "redirect:/CreatePack";
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

     return "redirect:/CreatePack";
  }
    
}