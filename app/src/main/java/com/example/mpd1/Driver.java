package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704

public class Driver {


     String driverId;
     String code;
     String url;
     String permanentNumber;
     String fName;
     String sName;
     String dob;
     String nationality;

    // Constructor, getters and setters
    public Driver()
    {

    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
    public String getDriverId() { return driverId; }

    public void setCode(String code) { this.code = code; }
    public  String getCode() { return  code; }

    public void setUrl(String url) { this.url = url; }
    public  String getUrl() { return  url; }
    public void setPermanentNumber(String permanentNumber) { this.permanentNumber = permanentNumber; }
    public  String getPermanentNumber() { return  permanentNumber; }

    public void setfName(String fName) { this.fName = fName; }
    public  String getfName() { return  fName; }

    public void setsName(String sName) { this.sName = sName; }
    public  String getsName() { return  sName; }

    public void setDob(String dob) { this.dob = dob; }
    public  String getDob() { return  dob; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public  String getNationality() { return  nationality; }





  /*  @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", permanentNumber=" + permanentNumber +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", dateOfBirth='" + dob + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }*/

}
