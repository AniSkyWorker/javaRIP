package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

/**
 * Html template provider.
 */
public final class GuitarTemplatesView {

  private GuitarTemplatesView(){
  }

  /**
   * Return text file representing add guitar html-page.
   * @param context Current context of servlet
   * @return String representing add guitar html-page
   * @throws IOException throws when have issues with html-template file
   */
  public static String getAddGuitarPage(final ServletContext context) throws IOException {
    BufferedReader buffer = null;
    String resultPage = "";
    try {
      buffer = new BufferedReader(new InputStreamReader(new FileInputStream(context.getRealPath("addGuitar")), "UTF-8"));
      resultPage = buffer.lines().collect(Collectors.joining("\n"));
    } finally {
      if (buffer != null) {
        buffer.close();
      }
    }
    return resultPage;
  }

  /**
   * Return text file representing guitar list html-page.
   * @param context Current context of servlet
   * @param guitarList list of guitar model objects
   * @return String representing guitar list html-page
   * @throws IOException throws when have issues with html-template file
   */
  public static String getGuitarListPage(final ServletContext context,
                                         final List<Guitar> guitarList) throws IOException {
    BufferedReader buffer = null;
    final StringBuffer contentBuffer = new StringBuffer(100);
    String page = "";

    try {
      buffer = new BufferedReader(new InputStreamReader(
          new FileInputStream(context.getRealPath("guitarList")), "UTF-8"));
      page = buffer.lines().collect(Collectors.joining("\n"));

      for (final Guitar guitar : guitarList) {
        final String date = guitar.getManufactureDate().toString();
        contentBuffer.append("    <tr>\n"
            + "    <td>" + guitar.getName() + "</td>\n"
            + "    <td>" + guitar.getSoundBoardStuff() + "</td>\n"
            + "    <td>" + guitar.getPrice() + "</td>\n"
            + "    <td>" + date + "</td>\n"
            + "    </tr>\n");
      }
    } finally {
      if (buffer != null) {
        buffer.close();
      }
    }
    return page.replace("${guitarList}", contentBuffer.toString());
  }
}
