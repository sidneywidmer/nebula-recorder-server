package recorder.config;


import com.google.inject.Provider;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

public class DbProvider implements Provider<Database> {

    @Override
    public Database get() {
        var dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("django");
        dataSourceConfig.setPassword("django");
        dataSourceConfig.setUrl("jdbc:postgresql://10.0.1.45:5432/sidney_dev");

        var config = new DatabaseConfig();
        config.setDataSourceConfig(dataSourceConfig);
        config.setRunMigration(true);

        var db = DatabaseFactory.create(config);

        return db;
    }
}
