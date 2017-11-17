package server;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GuitarTemplatesView {

    public static String getAddGuitarPage(ServletContext context) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(context.getResourceAsStream("addGuitar")));
        return buffer.lines().collect(Collectors.joining("\n"));
    }

    public static String getGuitarListPage(ServletContext context, List<Guitar> guitarList) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(context.getResourceAsStream("guitarList")));
        String page = buffer.lines().collect(Collectors.joining("\n"));

        String rowContent = "";
        for (Guitar guitar : guitarList) {
            String date = (guitar.getManufactureDate() == null) ? "" : guitar.getManufactureDate().toString();
            rowContent += "    <tr>\n" +
                    "    <td>" + guitar.getName() + "</td>\n" +
                    "    <td>" + guitar.getSoundingBoardStuff() + "</td>\n" +
                    "    <td>" + guitar.getPrice() + "</td>\n" +
                    "    <td>" + date + "</td>\n" +
                    "    </tr>\n";
        }
        return page.replace("${guitarList}", rowContent);
    }
}
