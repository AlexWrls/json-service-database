package service.criteria.search;

import com.google.gson.Gson;
import service.exception.ExceptionJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseCriteriaSearch {

    public CriteriaSearch parse(File path){

        Gson gson = new Gson();
        CriteriaSearch criteriaSearch = null;
        try (
            FileReader reader = new FileReader(path);) {
            criteriaSearch = gson.fromJson(reader, CriteriaSearch.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionJson("error",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionJson("error",e.getMessage());
        }
        return criteriaSearch;
    }
}
