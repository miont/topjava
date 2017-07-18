<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Add meal</title>
</head>
<body>
    <form method="POST" action="meals" name="mealsForm">
        <table>
            <tr style="display: none">
                <td >ID</td>
                <td><input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>"/> </td>
            </tr>
            <tr>
                <td>Дата/время</td>
                <td><input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>" /></td>
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
