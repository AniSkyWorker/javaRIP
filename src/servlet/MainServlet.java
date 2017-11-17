package servlet;

import server.GuitarController;
import server.GuitarTemplatesView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

public class MainServlet extends HttpServlet {

    private GuitarController guitarController = new GuitarController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if(request.getAttribute("showAddGuitarPage") == null) {
            guitarController.AddGuitar(request.getParameter("name"), request.getParameter("price")
                    , request.getParameter("board_stuff"), request.getParameter("manufactureDate"));
        }
        try {
            printHtmlPage(response, GuitarTemplatesView.getAddGuitarPage(getServletContext()));
        } catch (Exception e) {
            GuitarController.log.log(Level.SEVERE, "Error during request on POST method", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            if(request.getParameter("show_guitars") != null) {
                printHtmlPage(response, GuitarTemplatesView.getGuitarListPage(getServletContext(), guitarController.getGuitars()));
            }
            else {
                request.setAttribute("showAddGuitarPage", true);
                doPost(request, response);
            }
        } catch (Exception e) {
            GuitarController.log.log(Level.SEVERE, "Error during request on GET method", e);
        }
    }

    private void printHtmlPage(HttpServletResponse response, String page) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(page);
        out.flush();
    }
}
