package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

public class GuitarTemplatesView {

  /**
   * Return text file representing add guitar html-page.
   * @param context Current context of servlet
   * @return String representing add guitar html-page
   * @throws IOException throws when have issues with html-template file
   */
  public static String getAddGuitarPage(ServletContext context) throws IOException {
    BufferedReader buffer = new BufferedReader(new InputStreamReader(
        context.getResourceAsStream("addGuitar")));
    return buffer.lines().collect(Collectors.joining("\n"));
  }

  /**
   * Return text file representing guitar list html-page.
   * @param context Current context of servlet
   * @param guitarList list of guitar model objects
   * @return String representing guitar list html-page
   * @throws IOException throws when have issues with html-template file
   */
  public static String getGuitarListPage(ServletContext context,
                                         List<Guitar> guitarList) throws IOException {
    BufferedReader buffer = new BufferedReader(new InputStreamReader(
        context.getResourceAsStream("guitarList")));
    String page = buffer.lines().collect(Collectors.joining("\n"));

    String rowContent = "";
    for (Guitar guitar : guitarList) {
      String date = (guitar.getManufactureDate() == null)
          ? "" : guitar.getManufactureDate().toString();
      rowContent += "    <tr>\n"
          + "    <td>" + guitar.getName() + "</td>\n"
          + "    <td>" + guitar.getSoundingBoardStuff() + "</td>\n"
          + "    <td>" + guitar.getPrice() + "</td>\n"
          + "    <td>" + date + "</td>\n"
          + "    </tr>\n";
    }
    return page.replace("${guitarList}", rowContent);
  }
}
