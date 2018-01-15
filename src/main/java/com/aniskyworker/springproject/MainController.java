package com.aniskyworker.springproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import server.GuitarController;

/**
 * Main controller of application.
 */
@Controller
public class MainController {

  /**
   * Controller of guitar model objects.
   */
  private final transient GuitarController guitarController = new GuitarController();

  /**
   * Route add guitar page.
   */
  @RequestMapping(value = "/MainServlet", method = RequestMethod.POST)
  public String addGuitar(final Locale locale,final Model model, @RequestParam(value = "name", required = false) final String name,
                          @RequestParam(value = "price", required = false) final String price,
                          @RequestParam(value = "board_stuff", required = false)final String boardStuff,
                          @RequestParam(value = "manufactureDate", required = false) final String manufactureDate) {
    if (name != null) {
      guitarController.addGuitar(name, price, boardStuff, manufactureDate);
    }
    return "addGuitar";
  }

  /**
   * Route show guitar list page.
   */
  @RequestMapping(value = "/MainServlet", method = RequestMethod.GET)
  public String showGuitarList(final Locale locale, final Model model,
                               @RequestParam(value = "show_guitars", required = false) final String showGuitars) {
    if (showGuitars == null) {
      return addGuitar(locale, model, null, null, null,null);
    } else {
      model.addAttribute("guitarList", guitarController.getGuitars());
      return "guitarList";
    }
  }

  /**
   * Route show guitar list page.
   */
  @RequestMapping(value = "/UrlText", method = RequestMethod.GET)
  public String openUrlContent(final Locale locale, final Model model) {
    CompletableFuture<String> contentResult = getTextFromUrl("http://www.example.com/");

    try {
      contentResult.join();
      model.addAttribute("exampleContent", contentResult.get());
    } catch (Exception e) {
      guitarController.log(Level.ALL, "Failed to get async content", e);
    }

    return "example";
  }

  /**
   * Get text from remote service.
   * @param urlStr service address url.
   * @return text from url.
   */
  @Async
  public CompletableFuture<String> getTextFromUrl(final String urlStr) {
    String content = "";
    try {

      URL url = new URL(urlStr);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

      String line = "";
      StringBuilder builder = new StringBuilder();

      while ((line = in.readLine()) != null) {
        builder.append(line);
      }
      in.close();
      content = builder.toString();
    } catch (Exception e) {
      guitarController.log(Level.ALL, "Error when open remote url", e);
    }

    return CompletableFuture.completedFuture(content);
  }
}
