package com.heroku.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.heroku.java.model.PackageModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PackageController {

    private final DataSource dataSource;

    @Autowired
    public PackageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/CreatePack")
    public String createPack(@ModelAttribute("package") PackageModel packages) {
        String sql = "INSERT INTO public.package(packid, packname, packactivity, packprice) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, packages.getPackID());
            statement.setString(2, packages.getPackName());
            statement.setString(3, packages.getPackActivity());
            statement.setDouble(4, packages.getPackPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/CreatePack"; // Redirect to avoid resubmission on refresh
    }

    @GetMapping("/packageList")
    public String packageList(Model model) {
        List<PackageModel> packages = new ArrayList<>();
        String sql = "SELECT packid, packname, packactivity, packprice FROM public.package ORDER BY packid";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                PackageModel packageModel = new PackageModel();
                packageModel.setPackID(resultSet.getInt("packid"));
                packageModel.setPackName(resultSet.getString("packname"));
                packageModel.setPackActivity(resultSet.getString("packactivity"));
                packageModel.setPackPrice(resultSet.getDouble("packprice"));
                packages.add(packageModel);
            }

            model.addAttribute("packages", packages);
        } catch (SQLException e) {
            e.printStackTrace();
            return "error"; // Handle error view
        }
        return "ViewPack"; // Render ViewPack.html template
    }

    @PostMapping("/UpdatePack")
    public String updatePack(@ModelAttribute("package") PackageModel packages) {
        String sql = "UPDATE public.package SET packname = ?, packactivity = ?, packprice = ? WHERE packid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, packages.getPackName());
            statement.setString(2, packages.getPackActivity());
            statement.setDouble(3, packages.getPackPrice());
            statement.setInt(4, packages.getPackID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/CreatePack"; // Redirect to avoid resubmission on refresh
    }

    @PostMapping("/DeletePack")
    public String deletePack(@RequestParam("packID") Integer packID) {
        String sql = "DELETE FROM public.package WHERE packid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, packID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/CreatePack"; // Redirect to avoid resubmission on refresh
    }
}
