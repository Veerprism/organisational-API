import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

import java.net.URI;
import java.net.URISyntaxException;

public class DB {
    private static URI dbUri;
    public static Sql2o sql2o;
    public static Logger logger = LoggerFactory.getLogger(DB.class);
    static {

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://rzxyycrxguyjow:4532f6e7d77714d198645f21833bca0aed91afeb610505dacef4c793a2a04959@ec2-54-235-92-43.compute-1.amazonaws.com:5432/dem50d28o3djq7");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            }
            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? App.user : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? App.password : dbUri.getUserInfo().split(":")[1];
            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        } catch (URISyntaxException e ) {
            logger.error("Unable to connect to database.");
        }
    }

}
