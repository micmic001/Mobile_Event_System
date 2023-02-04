package com.micmicdev.mobileeventsystem.STR;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class strEventDetails {

    @SerializedName("message")
    private String message;

    @SerializedName("seat_no")
    private String seat_no;

    @SerializedName("tix_no")
    private String tix_no;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("vaxCard")
    private String vaxCard;

    @SerializedName("id")
    private String id;

    @SerializedName("device")
    private String device;

    @SerializedName("addedBy")
    private String addedBy;

    @SerializedName("status")
    private String status;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("tid")
    private String tid;

    @SerializedName("attendanceStatus")
    private String attendanceStatus;

    @SerializedName("inStatServer")
    private String inStatServer;

    @SerializedName("outStatServer")
    private String outStatServer;

    @SerializedName("search")
    private String search ;

    @SerializedName("inTime")
    private String inTime ;

    @SerializedName("outTime")
    private String outTime ;

    private ArrayList attendanceList;

    public strEventDetails(String seat_no, String tix_no, String firstName, String lastName,
                           String vaxCard, String id, String device, String addedBy, String message){
        this.seat_no=seat_no;
        this.tix_no=tix_no;
        this.firstName=firstName;
        this.lastName=lastName;
        this.vaxCard=vaxCard;
        this.id=id;
        this.device=device;
        this.addedBy=addedBy;
        this.message=message;
    }

    public strEventDetails(String seat_no, String status){
        this.seat_no=seat_no;
        this.status=status;
    }

    public strEventDetails(String seat_no, String tix_no, String message){
        this.seat_no=seat_no;
        this.tix_no=tix_no;
    }

    public strEventDetails(String tid, String fullName, String attendanceStatus, String device, String addedBy){
        this.tid=tid;
        this.fullName=fullName;
        this.attendanceStatus=attendanceStatus;
        this.device=device;
        this.addedBy=addedBy;
    }

    public strEventDetails(String search){
        this.search=search;
    }

    public strEventDetails(String fullName, String seat_no, String inTime, String outTime, String inStatServer, String outStatServer){
        this.fullName=fullName;
        this.seat_no=seat_no;
        this.inTime=inTime;
        this.outTime=outTime;
        this.inStatServer=inStatServer;
        this.outStatServer=outStatServer;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public String getTix_no() {
        return tix_no;
    }

    public void setTix_no(String tix_no) {
        this.tix_no = tix_no;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVaxCard() {
        return vaxCard;
    }

    public void setVaxCard(String vaxCard) {
        this.vaxCard = vaxCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getInStatServer() {
        return inStatServer;
    }

    public void setInStatServer(String inStatServer) {
        this.inStatServer = inStatServer;
    }

    public String getOutStatServer() {
        return outStatServer;
    }

    public void setOutStatServer(String outStatServer) {
        this.outStatServer = outStatServer;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public ArrayList getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(ArrayList attendanceList) {
        this.attendanceList=attendanceList;
    }
}
