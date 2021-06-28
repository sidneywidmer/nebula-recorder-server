package ch.nebula.recorder;

import io.ebean.DB;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    public void truncateTables() {
        var database = DB.getDefault();
        database.script().run("/db-truncate-all.sql");
    }
}
