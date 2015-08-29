package com.example.calvin.pointcounter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by calvin on 8/29/15.
 */
public class Log {
    //String path = getFilesDir().getAbsolutePath() + "/(point)(time)(chess).log";
    public Log(String path) {
        //Init Log
        File file = new File(path);
        //put in try block
        FileOutputStream stream = new FileOutputStream(file, file.exists());
        //Some more init log
    }

    public void lineBreak(){
        //add line break
    }
    public void addText(String logstring){
        //add log string
    }
    public void saveLog(){
        //save log text
    }
}
