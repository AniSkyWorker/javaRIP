package server;

import database.GuitarDatabase;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuitarController {
    private GuitarDatabase database;
    public static Logger log = Logger.getLogger(GuitarController.class.getName());

    public GuitarController() {
        try {
            Class.forName("com.nuodb.jdbc.Driver");
            this.database = new GuitarDatabase();
        } catch (Exception e) {
            log.log(Level.SEVERE,"Can`t connect to database", e);
        }
    }

    public List<Guitar> getGuitars() {
        try {
            return database.getGuitars();
        } catch (SQLException e) {
            log.log(Level.WARNING,"Error while gettin guitar list from database", e);
            return null;
        }
    }

    public void AddGuitar(String name, String priceStr, String soundingBoardStuff, String dateStr) {
        try {
            int price = priceStr.isEmpty() ? 0 : Integer.parseInt(priceStr);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            
            database.insertGuitar(new Guitar(name, price, soundingBoardStuff, format.parse(dateStr)));
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Error while insert guitar into database", e);
        } catch (ParseException e) {
            log.log(Level.WARNING,"Incorrect data str", e);
        }
    }
}
