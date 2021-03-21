package service.converter;

import java.io.File;
import java.util.Map;

public interface JsonConverter {
    void writeJson(File path, Map<Object, String> queryMap);
}
