import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class ParseCriterias {
    Arg arg = Arg.getArguments();

    public Criterias parse(){

        Gson gson = new Gson();
        Criterias criterias = null;
        try (
            FileReader reader = new FileReader(arg.getInputFile());) {
             criterias = gson.fromJson(reader,Criterias.class);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return criterias;
    }
}
