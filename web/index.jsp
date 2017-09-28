<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Guitar Addition</title>
  </head>
  <body>
  <form action = "MainServlet" method = "POST">
    Guitar Name: <input type = "text" name = "name">
    <br />
    Sounding board stuff: <input type = "text" name = "board_stuff" />
    <br />
    Price: <input type = "number" name = "price" />
    <input type = "submit" value = "Add guitar" />
  </form>
  <form action = "MainServlet" method="GET">
    <input type = "submit" value = "Show my guitars" />
  </form>
  </body>
</html>
