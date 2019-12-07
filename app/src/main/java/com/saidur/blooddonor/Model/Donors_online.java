package com.saidur.blooddonor.Model;

public class Donors_online {private String id;
    private String name;
    private String age;
    private String image;
    private String bloodgroup;
    private String countractnumber;
    private String lastdonationdate;
    private String address;

    public Donors_online() {
    }


    public Donors_online(String id, String name, String age, String image, String bloodgroup, String countractnumber, String lastdonationdate, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.bloodgroup = bloodgroup;
        this.countractnumber = countractnumber;
        this.lastdonationdate = lastdonationdate;
        this.address = address;
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

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getCountractnumber() {
        return countractnumber;
    }

    public void setCountractnumber(String countractnumber) {
        this.countractnumber = countractnumber;
    }

    public String getLastdonationdate() {
        return lastdonationdate;
    }

    public void setLastdonationdate(String lastdonationdate) {
        this.lastdonationdate = lastdonationdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
