<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Guitar Addition</title>
  </head>
  <body>
  <form action = "MainServlet" method = "POST">
    Guitar Name: <input type = "text" name = "name" required />
    <br />
    Sounding board stuff: <input type = "text" name = "board_stuff" />
    <br />
    Price: <input type = "number" min=0 max=999999999 name = "price" required />
    <br />
    Manufacture Date: <input type= "date" name = "manufactureDate" required/>
    <br />
    <input type = "submit" id = "addButton" value = "Add guitar" />
  </form>
  <form action = "MainServlet" method="GET">
    <input type = "submit" value = "Show my guitars" />
  </form>
  </body>
</html>