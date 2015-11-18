package com.eeproject.myvolunteer.myvolunteer;

/**
 * Created by Altman on 2015-11-14.
 */
public class user {
    private String username;
    private String iconpath;
    private String password;
    private int ranking_mark;

    public user(String username, String password, int ranking_mark, String iconpath){
        setUsername(username);
        setPassword(password);
        setIconpath(iconpath);
        setRanking_mark(ranking_mark);
    }

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

}
