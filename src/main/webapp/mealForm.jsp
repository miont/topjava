<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Add meal</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
</head>
<body>
    <%--<script>--%>
        <%--$(function() {--%>
            <%--$('input[name=dateTime]').datetimepicker();--%>
        <%--});--%>
    <%--</script>--%>

    <form method="POST" action="meals" name="mealsForm">
        <table>
            <tr style="display: none">
                <td >ID</td>
                <td><input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>"/> </td>
            </tr>
            <tr>
                <td>Дата/время</td>
                <td><input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>" /></td>
                <%--<td><input type="text" name="dateTime" value="${f:formatLocalDateTime(meal.dateTime, formatter)}" /></td>--%>
            </tr>
            <tr>
                <td>Описание</td>
                <td><input type="text" name="description" value="<c:out value="${meal.description}"/>" /></td>
            </tr>
            <tr>
                <td>Калории</td>
                <td><input type="text" name="calories" value="<c:out value="${meal.calories}"/>" /></td>
            </tr>
        </table>
        <input type="submit" value="OK"/>
    </form>

</body>
</html>
