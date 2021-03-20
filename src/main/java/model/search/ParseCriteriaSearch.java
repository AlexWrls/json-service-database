package model.search;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParseCriteriaSearch {


    public CriteriaSearch parse(File path){

        Gson gson = new Gson();
        CriteriaSearch criteriaSearch = null;
        try (
            FileReader reader = new FileReader(path);) {
            criteriaSearch = gson.fromJson(reader, CriteriaSearch.class);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return criteriaSearch;
    }
}
