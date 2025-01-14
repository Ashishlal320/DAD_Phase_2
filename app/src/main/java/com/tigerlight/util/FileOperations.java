package com.tigerlight.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class FileOperations {
     public FileOperations() {
 
        }
 
     public Boolean write(String fname, String fcontent){
            try {
 
                String fpath = "/sdcard/"+fname+".txt";
 
                File file = new File(fpath);
 
                // If file does not exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
 
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(fcontent);
                bw.close();
 
                Log.d("Suceess","Sucess");
                return true;
 
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
 
     }
 
     public String read(String fname){
 
         BufferedReader br = null;
         String response = null;
 
            try {
 
                StringBuffer output = new StringBuffer();
                String fpath = "/sdcard/"+fname+".txt";
 
                br = new BufferedReader(new FileReader(fpath));
                String line = "";
                while ((line = br.readLine()) != null) {
                    output.append(line +"n");
                }
                response = output.toString();
 
            } catch (IOException e) {
                e.printStackTrace();
                return null;
 
            }
            return response;
 
     }
}