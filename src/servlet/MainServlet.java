package servlet;

import server.Guitar;
import server.GuitarController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainServlet extends HttpServlet {

    GuitarController guitarController;

    public MainServlet() {
        this.guitarController = new GuitarController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String priceStr = request.getParameter("price");
        int price = priceStr.isEmpty() ? 0 : Integer.parseInt(priceStr);
        guitarController.AddGuitar(request.getParameter("name"), price, request.getParameter("board_stuff"));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Guitar> guitars = guitarController.getGuitars();
        request.setAttribute("guitarList", guitars);
        request.getRequestDispatcher("guitarList.jsp").forward(request, response);
    }
}
