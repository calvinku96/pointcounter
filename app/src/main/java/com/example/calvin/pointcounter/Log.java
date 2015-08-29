package com.example.calvin.pointcounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;

/**
 * Created by calvin on 8/29/15.
 */
public class Log {
    //String path = getFilesDir().getAbsolutePath() + "/(point)(time)(chess).log";
    //FileOutputStream stream = new FileOutputStream(file, file.exists());
    String tempLogString = "";
    File file;
    FileOutputStream stream;
    public static final int CONST_LINE_BREAK = 0;
    public static final int CONST_TWENTY_EQUALS = 1;

    public Log(String path) throws FileNotFoundException {
        //Init Log
        file = new File(path);
        //Some more init log
    }

    public void addCode(int code) {
        String codestring;
        switch (code) {
            case CONST_LINE_BREAK :
                codestring = "\n";
                break;
            case CONST_TWENTY_EQUALS :
                codestring = "====================";
                break;
            default :
                codestring = "";
                break;
        }
        tempLogString += codestring;
    }

    public void addCodeAndSave(int code) throws IOException {
        addCode(code);
        saveLog();
    }

    public void addText(String logstring) {
        //add log string
        tempLogString += logstring;
    }

    public void addTextAndSave(String logstring) throws IOException {
        //add log string and save
        addText(logstring);
        saveLog();
    }

    public void saveLog() throws IOException {
        //save log text
        stream = new FileOutputStream(file, file.exists());
        stream.write(tempLogString.getBytes());
        stream.close();
        tempLogString = "";
    }
}
