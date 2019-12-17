package edu.uestc.Utils;

import java.util.ArrayList;
import java.util.List;

public class ColorEntity {
    private List<String> colors;
    private int size;
    private int index;
    public ColorEntity(){
        colors = new ArrayList<>();
        TextReader reader = new TextReader("C:\\Users\\10733\\IdeaProjects\\autosolve_web\\src\\main\\java\\edu\\uestc\\Utils\\color");
        for(String line : reader.ReadLines()){
            String[] gr = line.split(" ");
            colors.add(gr[0]);
        }
        size = colors.size();
        index=0;
    }

    public int getSize() {
        return size;
    }
    public String getColor(){
        return colors.get(index++);
    }

}
