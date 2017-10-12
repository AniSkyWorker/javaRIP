package servlet;

import server.Guitar;
import server.GuitarController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainServlet extends HttpServlet {

    private GuitarController guitarController;
    private final String addGuitarPage =  "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<body>\n" +
            "<form action = \"MainServlet\" method = \"POST\">\n" +
            "    Guitar Name: <input type = \"text\" name = \"name\">\n" +
            "    <br />\n" +
            "    Sounding board stuff: <input type = \"text\" name = \"board_stuff\" />\n" +
            "    <br />\n" +
            "    Price: <input type = \"number\" name = \"price\" />\n" +
            "    <br />\n" +
            "    Manufacture Date: <input type=\"date\" name = \"manufactureDate\" />" +
            "    <br />\n" +
            "    <input type = \"submit\" value = \"Add guitar\" />\n" +
            "</form>\n" +
            "<form action = \"MainServlet\" method=\"GET\">\n" +
            "    <input type = \"submit\" value = \"Show my guitars\" />\n" +
            "</form>\n" +
            "</body>\n" +
            "</html>";

    public MainServlet() {
        this.guitarController = new GuitarController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getAttribute("showAddGuitarPage") == null)
        {
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
        }

        printHtmlPage(response, addGuitarPage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Guitar> guitars = guitarController.getGuitars();

        if(guitars.isEmpty()) {
            request.setAttribute("showAddGuitarPage", true);
            doPost(request, response);
            return;
        }

        printHtmlPage(response, getGuitarsListPage(guitars));
    }

    private void printHtmlPage(HttpServletResponse response, String page) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(page);
        out.flush();
    }

    private String getGuitarsListPage(List<Guitar> guitars) {
        String guitarListHtmlPage = "<!DOCTYPE html>\n" +
                " <html lang=\"en\">\n" +
                "<head><title> Guitar`s list </title></head>\n" +
                "<body>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th>Name</th>\n" +
                "        <th>Sounding Board Stuff</th>\n" +
                "        <th>Price</th>\n" +
                "        <th>Manufacture date</th>\n" +
                "    </tr>\n";

        for(Guitar guitar : guitars)
        {
            guitarListHtmlPage += "    <tr>\n" +
                            "    <td>" + guitar.getName() + "</td>\n" +
                            "    <td>" + guitar.getSoundingBoardStuff() + "</td>\n" +
                            "    <td>" + guitar.getPrice() + "</td>\n" +
                            "    <td>" + guitar.getManufatureDate().toString() + "</td>\n" +
                            "    </tr>\n";
        }

        guitarListHtmlPage += "</table>\n" +
                "</body>\n" +
                "</html>";

        return guitarListHtmlPage;
    }
}
