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

/**
 * Object that operate with guitar models.
 */
public class GuitarController {

  /**
   * Database aggregator.
   */
  private transient GuitarDatabase database;
  /**
   * Main logger.
   */
  private static final transient Logger LOGGER = Logger.getLogger(GuitarController.class.getName());

  /**
   * Create instance of guitar controller, that operate with guitar models.
   */
  public GuitarController() {
    try {
      Class.forName("com.nuodb.jdbc.Driver");
      this.database = new GuitarDatabase();
    } catch (SQLException | ClassNotFoundException exception) {
      LOGGER.log(Level.SEVERE, "Can`t connect to database", exception);
    }
  }

  /**
   * Getter for guitar list from database.
   * @return List of guitar model objects
   */
  public List<Guitar> getGuitars() {
    List<Guitar> guitarList = null;
    try {
      guitarList = database.getGuitars();
    } catch (SQLException e) {
      LOGGER.log(Level.WARNING, "Error while gettin guitar list from database", e);
    }
    return guitarList;
  }

  /**
   * Add guitar to database.
   * @param name Name o guitar
   * @param priceStr Guitar price
   * @param soundBoardStuff Guitar board stuff type
   * @param dateStr Manufacture date of guitar
   */
  public void addGuitar(final String name, final String priceStr,
                        final String soundBoardStuff, final String dateStr) {
    try {
      final int price = priceStr.isEmpty() ? 0 : Integer.parseInt(priceStr);
      final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

      database.insertGuitar(new Guitar(name, price, soundBoardStuff, format.parse(dateStr)));
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error while insert guitar into database", e);
    } catch (ParseException e) {
      LOGGER.log(Level.WARNING, "Incorrect data str", e);
    }
  }

  /**
   * Log the message and exception info.
   * @param logLevel Level of log
   * @param logMessage Log Message
   * @param exception Exception to log
   */
  public void log(final Level logLevel, final String logMessage, final Exception exception) {
    LOGGER.log(logLevel, logMessage, exception);
  }
}
