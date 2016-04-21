package com.eeproject.myvolunteer.myvolunteer;

/**
 * Created by Altman on 2015-11-14.
 */
public class user {
    private String username;
    private String iconpath;
    private String password;
    private int ranking_mark;
    //------- add by Tat -------
    private String firstname;
    private String lastname;
    private String organization;
    private int quest_issued;
    private int quest_accepted;
    //--------------------------

    public user(String username, String password, int ranking_mark, String iconpath,
                String firstname, String lastname, String organization, int quest_issued, int quest_accepted){    //added by Tat
        setUsername(username);
        setPassword(password);
        setIconpath(iconpath);
        setRanking_mark(ranking_mark);
        //----------- add by Tat -----------
        setFirstname(firstname);
        setLastname(lastname);
        setOrganization(organization);
        setQuest_issued(quest_issued);
        setQuest_accepted(quest_accepted);
        //----------------------------------
    }

    // empty default constructor, necessary for Firebase to be able to deserialize users
    public user(){}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public void setRanking_mark(int ranking_mark) {
        this.ranking_mark = ranking_mark;
    }

    //----------------- added by Tat ------------------
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setOrganization(String organization){
        this.organization = organization;
    }

    public void setQuest_issued(int quest_issued) {
        this.quest_issued = quest_issued;
    }

    public void setQuest_accepted(int quest_accepted) {
        this.quest_accepted = quest_accepted;
    }

    //-------------------------------------------------

    public String getUsername(){
        return username;
    }

    public String getIconpath(){
        return iconpath;
    }

    public String getPassword(){
        return password;
    }

    public int getRanking_mark(){
        return ranking_mark;
    }

    //----------------- added by Tat ------------------
    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getOrganization(){
        return organization;
    }

    public int getQuest_issued(){
        return quest_issued;
    }

    public int getQuest_accepted(){
        return quest_accepted;
    }
    //-------------------------------------------------

}
