package com.madhatters.wazan.controllers;


import com.madhatters.wazan.model.Dashboard;
import com.madhatters.wazan.repositories.NotificationRepository;
import com.madhatters.wazan.utils.Utils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dashboard")
@Api(value = "Dashboard Controller", description = "Dashboard operations")
public class DashboardController {
    private String ramStatus;
    private String diskStatus;
    private NotificationRepository repository;

    @Autowired
    public DashboardController(NotificationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/get", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        try {
            Dashboard dashboard = new Dashboard();
            dashboard.setUsedDisk(getUserDisk());
            dashboard.setUsedRam(readRam());
            dashboard.setRamStatus(ramStatus);
            dashboard.setDiskStatus(diskStatus);
            Pageable topTen = new PageRequest(0, 10, Sort.Direction.DESC, "time");
            dashboard.setNotification(repository.findAll(topTen).getContent());
            return ResponseEntity.ok(dashboard);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private double readRam() throws Exception {
        Process process = Runtime.getRuntime().exec("free");
        String result = Utils.convertStreamToString(process.getInputStream());
        String secondLine = result.split("\n")[1];
        String after = secondLine.trim().replaceAll(" +", " ");
        String[] splits = after.split(" ");
        double totalRam = Double.parseDouble(splits[1]);
        double usedRam = Double.parseDouble(splits[2]);
        ramStatus = humanReadableByteCount(usedRam * 1024, false) + " / " +
                humanReadableByteCount(totalRam * 1024, false) + " Used";
        Double resut = ((usedRam / totalRam) * 360.0);  //to draw over pie char
        return resut.intValue();
    }

    public static String humanReadableByteCount(double bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }


    private int getUserDisk() {
        File file = new File("/");
        double usedSpace = file.getTotalSpace() - file.getFreeSpace();
        Double result = (usedSpace / file.getTotalSpace()) * 360.0;
        diskStatus = humanReadableByteCount(usedSpace, false) + " / "
                + humanReadableByteCount(file.getTotalSpace(), false) + " Used.";
        return result.intValue();
    }
}
