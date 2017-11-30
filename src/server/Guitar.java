package server;

import java.util.Date;

/**
 * Guitar model object.
 */
public class Guitar {

  /**
   * Name.
   */
  private final String name;
  /**
   * Price.
   */
  private final int price;
  /**
   * Board stuff.
   */
  private final String soundBoardStuff;
  /**
   * Manufacture date.
   */
  private final Date manufactureDate;

  /**
   * Create instance of guitar model object.
   * @param name String representing name of guitar
   * @param price Price of guitar
   * @param soundBoardStuff Guitar board style type
   * @param manufactureDate Manufacture guitar date as Date type
   */
  public Guitar(final String name, final int price,
                final String soundBoardStuff, final Date manufactureDate) {
    this.name = name;
    this.price = price;
    this.soundBoardStuff = soundBoardStuff;
    this.manufactureDate = manufactureDate;
  }

  /**
   * Return name of guitar.
   * @return Guitar name
   */
  public String getName() {
    return name;
  }

  /**
   * Return guitar price.
   * @return Guitar price
   */
  public int getPrice() {
    return price;
  }

  /**
   * Return guitar board stuff.
   * @return Guitar board stuff
   */
  public String getSoundBoardStuff() {
    return soundBoardStuff;
  }

  /**
   * Return manufacture date.
   * @return Guitar manufacture date
   */
  public Date getManufactureDate() {
    return manufactureDate;
  }
}
