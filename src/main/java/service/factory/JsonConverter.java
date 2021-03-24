package service.factory;

import java.io.File;
import java.util.Map;

public interface JsonConverter {
    void writeJson(File path, Map<Object, String> queryMap);
}
