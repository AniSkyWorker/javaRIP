package server;

import database.GuitarDatabase;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class GuitarController {
    private GuitarDatabase database;

    public GuitarController() {
        try {
            Class.forName("com.nuodb.jdbc.Driver");
            this.database = new GuitarDatabase();
        } catch (Exception e) {
            System.err.println( "Can`t connect to database");
            e.printStackTrace();
        }
    }

    public List<Guitar> getGuitars() {
        try {
            return database.getGuitars();
        } catch (SQLException e) {
            System.err.println( "Error while gettin guitar list from database");
            e.printStackTrace();
            return null;
        }
    }

    public void AddGuitar(String name, int price, String soundingBoardStuff, Date manufactureDate) {
        try {
            database.insertGuitar(new Guitar(name, price, soundingBoardStuff, manufactureDate));
        } catch (SQLException e) {
            System.err.println( "Error while add guitar to database");
            e.printStackTrace();
        }
    }
}
