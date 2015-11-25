package com.eeproject.myvolunteer.myvolunteer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Altman on 2015/11/13.
 */
public class parameter {
    public static String[] catagory = {"All", "Donation", "Medical", "Linguistic", "Environment", "Production", "Animal Protections",
                            "Culture and Arts", "Tutorial", "Administration", "Oversea services", "Center Visit"};
    public static String[] language = {"All", "Cantonese", "English", "Mandarin", "Japanese", "Korean", "Hakka", "Shanghainese",
                            "Min", "French", "German", "Tagalog", "Indonesian", "Thai", "Vietnamese"};
    public static String[] defaulticonpath = {"nomore369", "nomorehehe", "nomoreangry", "nomorebam", "nomorebounce",
                                    "nomoreflowerface", "nomorefrown", "nomoregood", "nomorequestion", "nomorewtf", "nomoreyup"};
    public static AtomicBoolean login = new AtomicBoolean(false);
    public static AtomicBoolean remeberme = new AtomicBoolean(false);
    public static user logineduser = new user("Guest", null, 0, "nomorequestion", "Guest", null, null, null, null);
    public static int userID;

    public static void setUserID(String user){
        for(int i = 0; i < database_loadDatabase.usernamelist.size(); i++) {
            if (database_loadDatabase.usernamelist.get(i).equals(user)) {
                userID = i;
            }
        }
    }
}
