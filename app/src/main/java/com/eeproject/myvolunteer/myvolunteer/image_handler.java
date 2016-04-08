package com.eeproject.myvolunteer.myvolunteer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Altman on 2016/4/8.
 */
public class image_handler {
    public static String encode(Bitmap bitmap){
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bitmap.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return imageFile;
    }

    public static Bitmap decode(String imgfile){
         InputStream stream = new ByteArrayInputStream(Base64.decode(imgfile.getBytes(), Base64.DEFAULT));
         return BitmapFactory.decodeStream(stream);
    }

}
