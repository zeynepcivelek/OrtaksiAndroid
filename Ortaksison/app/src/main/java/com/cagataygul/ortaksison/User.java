package com.cagataygul.ortaksison;

public class User {
     String id;
     String email;
     String password;
     String name;
     String surname;
     String phonenumber;
     String gender;
     String username;
     Double latitude;
     Double longitude;
     String status;
     String destination;
     String time;
     public User(){

     }

    public User(String id ,String email, String password, String name, String surname, String phonenumber, String gender, String username
            ,Double latitude, Double longitude,String status,String destination,String time) {
         this.id=id;
         this.status=status;
         this.latitude=latitude;
         this.longitude=longitude;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phonenumber = phonenumber;
        this.gender = gender;
        this.username = username;
        this.destination=destination;
        this.time=time;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLatitude(double latitude) {
       this.latitude = latitude;
   }

  public void setLongitude(double longitude) {
       this.longitude = longitude;
   }

    public double getLatitude(){return  latitude;}

    public double getLongitude(){return  longitude;}
    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }
}
