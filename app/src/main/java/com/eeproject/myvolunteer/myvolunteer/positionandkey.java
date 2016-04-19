package com.eeproject.myvolunteer.myvolunteer;

/**
 * Created by Altman Wong on 2016/4/19.
 */
public class positionandkey {
    public static String key;
    public static int position;

    public positionandkey(String key, int position){
        this.key = key;
        this.position = position;
    }

    public static String getKey(){
        return key;
    }

    public static int getPosition(){
        return position;
    }
}
