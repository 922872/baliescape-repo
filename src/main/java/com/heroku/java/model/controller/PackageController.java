package com.heroku.java.controller;

import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.heroku.java.DAO.PackageDAO;
import com.heroku.java.bean.Packages;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class packageController {
    private final DataSource dataSource;

    public packageController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/CreatePack")
    public String add(HttpSession session) {
        return "admin/CreatePack";
    }

    @PostMapping("/CreatePack")
    public String CreatePack(@ModelAttribute("package") Packages pack) {
        try {
            PackageDAO packageDAO = new PackageDAO(dataSource);

            MultipartFile packimage = pack.getPackimage();
            pack.setPackimagebyte(packimage.getBytes());
            packageDAO.CreatePack(pack);

            if (pack.getPackName().equalsIgnoreCase("honeymoon")) {
                return "redirect:/viewHoneymoon";
            } else if (pack.getPackName().equalsIgnoreCase("adventure")) {
                return "redirect:/viewAdventure";
            } else if (pack.getPackName().equalsIgnoreCase("group")) {
                return "redirect:/viewGroup";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/viewHoneymoon")
    public String viewHoneymoon(HttpSession session, Model model) {
        try {
            PackageDAO packageDAO = new PackageDAO(dataSource);
            ArrayList<Packages> packages = packageDAO.getHoneymoonPackages();
            model.addAttribute("packages", packages);
            return "admin/viewHoneymoon";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/viewAdventure")
    public String viewAdventure(HttpSession session, Model model) {
        try {
            PackageDAO packageDAO = new PackageDAO(dataSource);
            ArrayList<Packages> packages = packageDAO.getAdventurePackages();
            model.addAttribute("packages", packages);
            return "admin/viewAdventure";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/viewGroup")
    public String viewGroup(HttpSession session, Model model) {
        try {
            PackageDAO packageDAO = new PackageDAO(dataSource);
            ArrayList<Packages> packages = packageDAO.getGroupPackages();
            model.addAttribute("packages", packages);
            return "admin/viewGroup";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/UpdatePack")
    public String UpdatePack(@RequestParam("packID") int packID, Model model) {
        try {
            PackageDAO packageDAO = new PackageDAO(dataSource);
            Packages pack = packageDAO.getPackageById(packID);

            if (pack != null) {
                model.addAttribute("package", pack);
            }

            return "admin/UpdatePack";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping("/UpdatePack")
    public String UpdatePack(@ModelAttribute("package") Packages pack, @RequestParam("packimage") MultipartFile packimage) {
        PackageDAO packageDAO = new PackageDAO(dataSource);
        boolean success = packageDAO.UpdatePack(pack, packimage);
        if (success) {
            if (pack.getPackType().equalsIgnoreCase("honeymoon")) {
                return "redirect:/viewHoneymoon";
            } else if (pack.getPackType().equalsIgnoreCase("adventure")) {
                return "redirect:/viewAdventure";
            } else if (pack.getPackType().equalsIgnoreCase("group")) {
                return "redirect:/viewGroup";
            }
        } else {
            return "package-not-found";
        }
    }

    @GetMapping("/DeletePack")
    public String DeletePack(@RequestParam("packID") int packID, Model model) throws SQLException {
        PackageDAO packageDAO = new PackageDAO(dataSource);
        Packages pack = packageDAO.getPackageById(packID);
        String packType = pack.getPackType();
        boolean success = packageDAO.deletePackage(packID);

        if (pack != null) {
            packType = pack.getPackType();
            if ("honeymoon".equalsIgnoreCase(packType)) {
                return "redirect:/viewHoneymoon?success=" + String.valueOf(success);
            } else if ("adventure".equalsIgnoreCase(packType)) {
                return "redirect:/viewAdventure?success=" + String.valueOf(success);
            } else if ("group".equalsIgnoreCase(packType)) {
                return "redirect:/viewGroup?success=" + String.valueOf(success);
            }
        }
        return "redirect:/defaultPage?success=" + String.valueOf(success);
    }
}

