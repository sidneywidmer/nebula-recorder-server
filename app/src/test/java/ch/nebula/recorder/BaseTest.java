package ch.nebula.recorder;

import io.ebean.DB;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base test class for all tests.
 */
public class BaseTest {

    /**
     * To guarantee that all tables are empty before a test is running we truncate them before each test.
     */
    @BeforeEach
    public void truncateTables() {
        var database = DB.getDefault();
        database.script().run("/db-truncate-all.sql");
    }
}
