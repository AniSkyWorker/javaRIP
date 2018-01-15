package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import server.Guitar;

/**
 * Object of guitar database aggregator.
 */
@Component
public class GuitarDatabase {

  /**
   * Store database driver connection.
   */
  private final transient Connection dbConnection;
  private Logger logger;

  /**
   * Create object of guitar database aggregator.
   * @throws SQLException throws when can`t init connection to database
   */
  public GuitarDatabase(Logger loggerRef) throws SQLException {
    logger = loggerRef;
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
  @Async
  public void insertGuitar(final Guitar guitar) throws SQLException {
    final PreparedStatement stmt = dbConnection.prepareStatement(
        "insert into guitars (name, soundingBoard, price, manufactureDate) values (?, ?, ?, ?)");

    try {
      logger.log(Level.INFO, "Start database inserting");
      stmt.setString(1, guitar.getName());
      stmt.setString(2, guitar.getSoundBoardStuff());
      stmt.setInt(3, guitar.getPrice());
      final java.sql.Date sqlDate = new java.sql.Date(guitar.getManufactureDate().getTime());
      stmt.setDate(4, sqlDate);

      stmt.addBatch();
      stmt.executeBatch();
    } finally {
      stmt.close();
      dbConnection.commit();
    }
  }

  /**
   * Return list of guitars from database.
   * @return List of guitar model objects
   * @throws SQLException throws when have an error with SQL request
   */
  public List<Guitar> getGuitars() throws SQLException {
    final List<Guitar> guitars = new LinkedList<Guitar>();
    PreparedStatement pst = null;
    ResultSet resultGuitarSet = null;

    try {
      pst = dbConnection.prepareStatement("select * from guitars");
      resultGuitarSet = pst.executeQuery();
      while (resultGuitarSet.next()) {
        Date manufactureDate = resultGuitarSet.getDate(5);
        if (manufactureDate == null) {
          manufactureDate = new Date();
        }
        guitars.add(new Guitar(resultGuitarSet.getString(2),
            resultGuitarSet.getInt(4), resultGuitarSet.getString(3), manufactureDate));
      }
    } finally {
      if (pst != null) {
        pst.close();
      }
      if (resultGuitarSet != null) {
        resultGuitarSet.close();
      }
    }
    return guitars;
  }
}