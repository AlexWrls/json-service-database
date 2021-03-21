package model.stat;

import com.google.gson.Gson;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ParseCriteriaStat {


    public CriteriaStat parse(File path) {

        Gson gson = new Gson();
        CriteriaStat criteriaStat = null;
        try (
                FileReader reader = new FileReader(path);) {
            criteriaStat = gson.fromJson(reader, CriteriaStat.class);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return criteriaStat;
    }

}
