package com.madhatters.wazan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Dashboard implements Serializable {
    private double usedRam;
    @SerializedName("usedDisk")
    private double usedDisk;

    @SerializedName("ramStatus")
    private String ramStatus;
    @SerializedName("diskStatus")
    private String diskStatus;

    @SerializedName("notifications")
    private List<Notification> notification;

    public Dashboard(double usedRam,
                     double usedDisk, String ramStatus, String diskStatus) {
        this.usedRam = usedRam;
        this.usedDisk = usedDisk;
        this.ramStatus = ramStatus;
        this.diskStatus = diskStatus;
    }


    public String getDiskStatus() {
        return diskStatus;
    }

    public void setDiskStatus(String diskStatus) {
        this.diskStatus = diskStatus;
    }

    public double getUsedRam() {
        return usedRam;
    }

    public void setUsedRam(double usedRam) {
        this.usedRam = usedRam;
    }

    public double getUsedDisk() {
        return usedDisk;
    }

    public void setUsedDisk(double usedDisk) {
        this.usedDisk = usedDisk;
    }

    public Dashboard() {
    }

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public void setRamStatus(String ramStatus) {
        this.ramStatus = ramStatus;
    }

    public String getRamStatus() {
        return ramStatus;
    }
}
