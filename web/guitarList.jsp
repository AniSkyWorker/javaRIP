<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Here`s your guitars</title>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>Sounding Board Stuff</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${guitarList}" var="guitar">
        <tr>
            <td>${guitar.name}</td>
            <td>${guitar.soundingBoardStuff}</td>
            <td>${guitar.price}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
