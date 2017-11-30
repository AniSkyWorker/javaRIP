package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import server.Guitar;

/**
 * Object of guitar database aggregator.
 */
public class GuitarDatabase {

  /**
   * Store database driver connection.
   */
  private final transient Connection dbConnection;

  /**
   * Create object of guitar database aggregator.
   * @throws SQLException throws when can`t init connection to database
   */
  public GuitarDatabase() throws SQLException {
    final Properties properties = new Properties();
    properties.put("user", "guitarmaster");
    properties.put("password", "12345");
    properties.put("schema", "GUITAR");

    dbConnection = DriverManager.getConnection(
        "jdbc:com.nuodb://localhost/guitar_base", properties);
  }

  /**
   * Insert info about guitar from guitar model object into dtabase.
   * @param guitar Guitar model object
   * @throws SQLException throws when have an error with SQL request
   */
  public void insertGuitar(final Guitar guitar) throws SQLException {
    final PreparedStatement stmt = dbConnection.prepareStatement(
        "insert into guitars (name, soundingBoard, price, manufactureDate) values (?, ?, ?, ?)");
    stmt.setString(1, guitar.getName());
    stmt.setString(2, guitar.getSoundBoardStuff());
    stmt.setInt(3, guitar.getPrice());
    final java.sql.Date sqlDate = new java.sql.Date(guitar.getManufactureDate().getTime());
    stmt.setDate(4, sqlDate);

    stmt.addBatch();
    stmt.executeBatch();
    stmt.closeOnCompletion();

    dbConnection.commit();
  }

  /**
   * Return list of guitars from database.
   * @return List of guitar model objects
   * @throws SQLException throws when have an error with SQL request
   */
  public List<Guitar> getGuitars() throws SQLException {
    final List<Guitar> guitars = new LinkedList<Guitar>();
    final PreparedStatement pst = dbConnection.prepareStatement("select * from guitars");
    final ResultSet resultGuitarSet = pst.executeQuery();
    while (resultGuitarSet.next()) {
      guitars.add(new Guitar(resultGuitarSet.getString(2),
          resultGuitarSet.getInt(4), resultGuitarSet.getString(3), resultGuitarSet.getDate(5)));
    }
    resultGuitarSet.close();
    return guitars;
  }
}