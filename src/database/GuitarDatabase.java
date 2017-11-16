package database;
import server.Guitar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class GuitarDatabase {
    private final Connection dbConnection;
    private Properties properties;

    public GuitarDatabase()
            throws Exception
    {
        Properties properties = new Properties();
        properties.put("user", "guitarmaster");
        properties.put("password", "12345");
        properties.put("schema", "GUITAR");
        dbConnection = DriverManager.getConnection("jdbc:com.nuodb://localhost/guitar_base", properties);
    }

    public void insertGuitar(Guitar guitar) throws SQLException {
        try (PreparedStatement stmt = dbConnection.
                prepareStatement("insert into guitars (name, soundingBoard, price, manufactureDate) values (?, ?, ?, ?)")) {
            stmt.setString(1, guitar.getName());
            stmt.setString(2, guitar.getSoundingBoardStuff());
            stmt.setInt(3, guitar.getPrice());
            java.sql.Date sqlDate = new java.sql.Date(guitar.getManufatureDate().getTime());
            stmt.setDate(4,sqlDate);
            stmt.addBatch();
            stmt.executeBatch();
            dbConnection.commit();
        } catch(Exception exception) {
            System.out.println("Skipping insert..." + exception.getMessage());
        }
    }

    public List<Guitar> getGuitars() throws SQLException {
        List<Guitar> guitars = new LinkedList<Guitar>();
        try (PreparedStatement pst = dbConnection.
                prepareStatement("select * from guitars")) {
            try (ResultSet rs = pst.executeQuery()){
                while (rs.next()) {
                    guitars.add(new Guitar(rs.getString(2), rs.getInt(4),  rs.getString(3),  rs.getDate(5)));
                }
                return guitars;
            }
        }
    }
}