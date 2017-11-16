package servlet;

import server.Guitar;
import server.GuitarController;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;

public class MainServlet extends HttpServlet {

    private GuitarController guitarController = new GuitarController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        guitarController.AddGuitar(request.getParameter("name"),  request.getParameter("price")
                , request.getParameter("board_stuff"), request.getParameter("manufactureDate"));

        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            GuitarController.log.log(Level.SEVERE, "Error during request on POST method", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Guitar> guitars = guitarController.getGuitars();
        request.setAttribute("guitarList", guitars);

        try {
            request.getRequestDispatcher("guitarList.jsp").forward(request, response);
        } catch (Exception e) {
            GuitarController.log.log(Level.SEVERE, "Error during request on GET method", e);
        }
    }
}
