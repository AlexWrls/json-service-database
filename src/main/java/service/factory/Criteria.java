package service.factory;

import java.io.File;
import java.sql.SQLException;

public interface Criteria {
    Criteria parse(File path) throws SQLException;
}
