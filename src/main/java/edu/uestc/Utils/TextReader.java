package edu.uestc.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader {
    private BufferedReader reader;
    private FileReader r;

    public TextReader(String path){
        try{
            r = new FileReader(path);
            reader = new BufferedReader(r);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<String> ReadLines(){
        List<String> res = new ArrayList<String>();
        try{
            String line = "";
            while((line = reader.readLine()) != null){
                res.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }

    public void Close(){
        try{
            reader.close();
            r.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
