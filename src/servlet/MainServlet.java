package servlet;

import server.Guitar;
import server.GuitarController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainServlet extends HttpServlet {

    private GuitarController guitarController = new GuitarController();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String priceStr = request.getParameter("price");
        int price = priceStr.isEmpty() ? 0 : Integer.parseInt(priceStr);
        String dateStr = request.getParameter("manufactureDate");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            guitarController.AddGuitar(request.getParameter("name"), price
                    , request.getParameter("board_stuff"), format.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Guitar> guitars = guitarController.getGuitars();
        request.setAttribute("guitarList", guitars);
        request.getRequestDispatcher("guitarList.jsp").forward(request, response);
    }
}
