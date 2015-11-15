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
    public static String[] defaulticonpath = {"nomore369", "nomorehehe", "nomoreanry", "nomorebam", "nomorebounce",
                                    "nomoreflowerface", "nomorefrown", "nomoregood", "nomorequestion", "nomorewtf", "nomoreyup"};
    public static AtomicBoolean login = new AtomicBoolean(false);
    public static AtomicBoolean remeberme = new AtomicBoolean(false);
}
