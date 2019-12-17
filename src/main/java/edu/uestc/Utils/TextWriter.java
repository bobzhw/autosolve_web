package edu.uestc.Utils;

import java.io.FileWriter;
import java.io.Writer;


public class TextWriter {
    public TextWriter(String filepath,boolean isAppend)
    {
        try {
            writer = new FileWriter(filepath,isAppend);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void write(String content){
        try {
            writer.write(content);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeLines(String content){
        try {
            writer.write(content+"\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try{
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Writer writer;
}
