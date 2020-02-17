package com.cpi.rnd.cpi_rnd_swipe_app;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String TAG = "Utils";

    public static List<ItemPairProfile> loadItemPairs(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Log.d("rohan_Util_size","util file caller reached here");
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "itemPair.json"));
            List<ItemPairProfile> profileList = new ArrayList<>();
            Log.d("rohan_Util_size",String.valueOf(array.length()));
            for(int i=0;i<array.length();i++){
                ItemPairProfile itemPairProfilePair = gson.fromJson(array.getString(i), ItemPairProfile.class);
                profileList.add(itemPairProfilePair);
            }
            return profileList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<ItemPairProfile> loadItemPairsById(Context context, int row_id){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Log.d("rohan_Util_size","util file caller reached here");
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "itemPair.json"));
            List<ItemPairProfile> profileList = new ArrayList<>();
            Log.d("rohan_Util_size",String.valueOf(array.length()));
            for(int i=0;i<array.length();i++){
                ItemPairProfile itemPairProfilePair = gson.fromJson(array.getString(i), ItemPairProfile.class);
                if (row_id == itemPairProfilePair.getRow_id()){
                    profileList.add(itemPairProfilePair);
                }

            }
            return profileList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}