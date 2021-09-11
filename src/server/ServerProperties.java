package server;

import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import database.DatabaseConnection;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Emilyx3
 */
public class ServerProperties {

    private static final Properties props = new Properties();

    private static final String[] toLoad = {
        "Settings.ini"
    };

    private ServerProperties() {
    }

    static {
        for (String s : toLoad) {
            InputStreamReader fr;
            try {
                fr = new InputStreamReader(new FileInputStream(s), "UTF-8");
                props.load(fr);
                fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getProperty(String s) {
        return props.getProperty(s);
    }

    public static void setProperty(String prop, String newInf) {
        props.setProperty(prop, newInf);
    }

    public static String getProperty(String s, String def) {
        return props.getProperty(s, def);
    }

    public static int getIntProperty(String s, String def) {
        return Integer.valueOf(props.getProperty(s, def));
    }
}
