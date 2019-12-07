package com.saidur.blooddonor.Model;

public class Donors {
    private String id;
    private String name;
    private String age;
    private String image;
    private String bloodgroup;
    private String countractnumber;
    private String lastdonationdate;
    private String address;

    // private String firebaseID;
/*
public Donor(Donor donor,String firebaseID)
{
    this.firebaseID = firebaseID;
 Donor(donor.getName(), donor.getAge(), donor.getLastDonationDate(), donor.getBloodGroup(),donor.get contractNumber, lastDonationDate, address);

}*/

    public Donors()
    {

    }

    public Donors(String id, String name, String age, String image, String bloodGroup, String countractNumber, String lastDonationDate, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.bloodgroup = bloodGroup;
        this.countractnumber = countractNumber;
        this.lastdonationdate = lastDonationDate;
        this.address = address;
    }

    public Donors(String name, String age, String image, String bloodGroup, String countractNumber, String lastDonationDate, String address) {
        this.name = name;
        this.age = age;
        this.image = image;
        this.bloodgroup = bloodGroup;
        this.countractnumber = countractNumber;
        this.lastdonationdate = lastDonationDate;
        this.address = address;
    }


    public Donors(String id, String bloodGroup) {
        this.id = id;
        this.bloodgroup = bloodGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBloodGroup() {
        return bloodgroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodgroup = bloodGroup;
    }

    public String getCountractNumber() {
        return countractnumber;
    }

    public void setCountractNumber(String countractNumber) {
        this.countractnumber = countractNumber;
    }

    public String getLastDonationDate() {
        return lastdonationdate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastdonationdate = lastDonationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
