package recorder;

import io.ebean.annotation.Platform;
import io.ebean.dbmigration.DbMigration;

import java.io.IOException;

public class GenerateDbMigration {

    /**
     * Generate the next "DB schema DIFF" migration.
     */
    public static void main(String[] args) throws IOException {
        var dbMigration = DbMigration.create();
        dbMigration.setPlatform(Platform.POSTGRES);

        dbMigration.generateMigration();
    }
}
