import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ParseCriterias {


    public Criterias parse(File path){

        Gson gson = new Gson();
        Criterias criterias = null;
        try (
            FileReader reader = new FileReader(path);) {
            criterias = gson.fromJson(reader, Criterias.class);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return criterias;
    }
}
