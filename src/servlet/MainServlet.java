package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.GuitarController;
import server.GuitarTemplatesView;

/**
 * Main Servlet object initiate add and show guitars routing.
 */
public class MainServlet extends HttpServlet {

  /**
   * Controller of guitar model objects.
   */
  private final transient GuitarController guitarController = new GuitarController();

  /**
   * Route add guitar page.
   * @param request Client request
   * @param response Method response
   */
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
    if (request.getAttribute("showAddGuitarPage") == null) {
      guitarController.addGuitar(request.getParameter("name"), request.getParameter("price"),
          request.getParameter("board_stuff"), request.getParameter("manufactureDate"));
    }
    try {
      printHtmlPage(response, GuitarTemplatesView.getAddGuitarPage(getServletContext()));
    } catch (IOException e) {
      guitarController.log(Level.SEVERE, "Error during request on POST method", e);
    }
  }

  /**
   * Route show guitar list page.
   * @param request Client request
   * @param response Method response
   */
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
    try {

      if (request.getParameter("show_guitars") == null) {
        request.setAttribute("showAddGuitarPage", true);
        doPost(request, response);
      } else {
        printHtmlPage(response, GuitarTemplatesView.getGuitarListPage(getServletContext(),
            guitarController.getGuitars()));
      }
    } catch (IOException e) {
      guitarController.log(Level.SEVERE, "Error during request on GET method", e);
    }
  }

  private void printHtmlPage(final HttpServletResponse response,
                             final String page) throws IOException {
    response.setContentType("text/html");
    final PrintWriter out = response.getWriter();
    out.print(page);
    out.flush();
  }
}
