package ch.nebula.recorder.providers;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.typesafe.config.Config;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

import java.util.Properties;

public class DbProvider implements Provider<Database> {
    private final Config config;

    @Inject
    public DbProvider(Config config) {
        this.config = config;
    }

    /**
     * Create a new Ebean instance with values from our app.config. We're setting runMigraiton to true so open
     * migrations are automatically triggered on every server start.
     */
    @Override
    public Database get() {
        var dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername(config.getString("database.username"));
        dataSourceConfig.setPassword(config.getString("database.password"));
        dataSourceConfig.setUrl(config.getString("database.url"));

        var config = new DatabaseConfig();
        var properties = new Properties();

        config.loadFromProperties(properties);
        config.setDataSourceConfig(dataSourceConfig);
        config.setRunMigration(true);

        return DatabaseFactory.create(config);
    }
}
