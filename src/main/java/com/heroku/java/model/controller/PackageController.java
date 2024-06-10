package com.heroku.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heroku.java.model.Package;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PackageController {
    private final DataSource dataSource;

    @Autowired
    public PackageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/staffPackageList")
    public String staffPackageList(Model model, HttpSession session) {
        String staffICNumber = (String) session.getAttribute("staffICNumber");
        List<Package> packages = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT packageID, packageName, packageActivity, packageType, packagePrice FROM public.package ORDER BY packageID";
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String packageID = resultSet.getString("packageID");
                String packageName = resultSet.getString("packageName");
                String packageActivity = resultSet.getString("packageActivity");
                String packageType = resultSet.getString("packageType");
                double packagePrice = resultSet.getDouble("packagePrice");

                Package pack = new Package();
                pack.setPackageID(packageID);
                pack.setPackageName(packageName);
                pack.setPackageActivity(packageActivity);
                pack.setPackageType(packageType);
                pack.setPackagePrice(packagePrice);

                packages.add(pack);
            }
            model.addAttribute("packages", packages);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

        return "staff/staffPackageList";
    }

    @PostMapping("/staffAddPackage")
    public String staffAddPackage(@ModelAttribute("staffAddPackage") Package pack, HttpSession session) {
        String staffICNumber = (String) session.getAttribute("staffICNumber");

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO public.package(packageID, packageName, packageActivity, packageType, packagePrice) VALUES(?,?,?,?,?)";
            var statement = connection.prepareStatement(sql);

            statement.setString(1, pack.getPackageID());
            statement.setString(2, pack.getPackageName());
            statement.setString(3, pack.getPackageActivity());
            statement.setString(4, pack.getPackageType());
            statement.setDouble(5, pack.getPackagePrice());
            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("message", "Addition Failed");
            return "redirect:/staffAddPackage";
        }
        session.setAttribute("message", "Successfully Added");
        return "redirect:/staffPackageList";
    }

    @GetMapping("/staffViewPackage")
    public String staffViewPackage(@RequestParam("packageID") String packageID, Model model, HttpSession session) {
        String staffICNumber = (String) session.getAttribute("staffICNumber");

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT packageID, packageName, packageActivity, packageType, packagePrice FROM public.package WHERE packageID = ?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, packageID);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String packageName = resultSet.getString("packageName");
                String packageActivity = resultSet.getString("packageActivity");
                String packageType = resultSet.getString("packageType");
                double packagePrice = resultSet.getDouble("packagePrice");

                Package pack = new Package();
                pack.setPackageID(packageID);
                pack.setPackageName(packageName);
                pack.setPackageActivity(packageActivity);
                pack.setPackageType(packageType);
                pack.setPackagePrice(packagePrice);
                model.addAttribute("package", pack);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

        return "staff/staffViewPackage";
    }

    @GetMapping("/staffUpdatePackage")
    public String staffUpdatePackage(@RequestParam("packageID") String packageID, Model model, HttpSession session) {
        String staffICNumber = (String) session.getAttribute("staffICNumber");

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT packageID, packageName, packageActivity, packageType, packagePrice FROM public.package WHERE packageID = ?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, packageID);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String packageName = resultSet.getString("packageName");
                String packageActivity = resultSet.getString("packageActivity");
                String packageType = resultSet.getString("packageType");
                double packagePrice = resultSet.getDouble("packagePrice");

                Package pack = new Package();
                pack.setPackageID(packageID);
                pack.setPackageName(packageName);
                pack.setPackageActivity(packageActivity);
                pack.setPackageType(packageType);
                pack.setPackagePrice(packagePrice);
                model.addAttribute("package", pack);

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

        return "staff/staffUpdatePackage";
    }

    @PostMapping("/staffUpdatePackage")
    public String staffUpdatePackage(@ModelAttribute("staffUpdatePackage") Package pack, HttpSession session) {
        String staffICNumber = (String) session.getAttribute("staffICNumber");

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE public.package SET packageName=?, packageActivity=?, packageType=?, packagePrice=? WHERE packageID=?";
            var statement = connection.prepareStatement(sql);

            statement.setString(1, pack.getPackageName());
            statement.setString(2, pack.getPackageActivity());
            statement.setString(3, pack.getPackageType());
            statement.setDouble(4, pack.getPackagePrice());
            statement.setString(5, pack.getPackageID());
            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("message", "Update Failed");
            return "redirect:/staffUpdatePackage";
        }
        session.setAttribute("message", "Successfully Updated");
        return "redirect:/staffPackageList";
    }
}
