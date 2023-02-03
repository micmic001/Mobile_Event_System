package com.micmicdev.mobileeventsystem.STR;

import com.google.gson.annotations.SerializedName;

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
}
