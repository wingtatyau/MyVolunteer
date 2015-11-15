package com.eeproject.myvolunteer.myvolunteer;

/**
 * Created by Altman on 2015-11-14.
 */
public class quest {
    private String title, info, expirydate, location, catagory, user, requiredLanguage, requiredTime;
    int currentparti, partinumber;

    public quest(String title, String info, String expirydate, String location, String catagory, String requiredlanguage, String user,String requiredtime,  int currentparti, int partinumber){
        setTitle(title);
        setInfo(info);
        setExpirydate(expirydate);
        setLocation(location);
        setCatagory(catagory);
        setRequiredLanguage(requiredlanguage);
        setUser(user);
        setRequiredTime(requiredtime);
        setCurrentparti(currentparti);
        setPartinumber(partinumber);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public void setRequiredLanguage(String requiredLanguage) {
        this.requiredLanguage = requiredLanguage;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    public void setCurrentparti(int currentparti) {
        this.currentparti = currentparti;
    }

    public void setPartinumber(int partinumber) {
        this.partinumber = partinumber;
    }

    public String getTitle(){
        return title;
    }

    public String getInfo(){
        return info;
    }

    public String getExpirydate(){
        return expirydate;
    }

    public String getLocation(){
        return location;
    }

    public String getCatagory(){
        return catagory;
    }

    public String getUser(){
        return  user;
    }

    public String getRequiredLanguage(){
        return requiredLanguage;
    }

    public String getRequiredTime(){
        return requiredTime;
    }

    public int getCurrentparti(){
        return currentparti;
    }

    public int getPartinumber(){
        return partinumber;
    }
}
