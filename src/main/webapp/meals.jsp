<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="f" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .exceeded {color: red}
        .not-exceeded {color: green}
    </style>
</head>
<body>
<h3><a href="index.html">Стартовая страница</a></h3>
<p><a href="meals?action=add">Добавить</a></p>
<table border="1">
    <thead>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действие</th>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? 'exceeded' : 'not-exceeded'}">
            <td>${f:formatLocalDateTime(meal.dateTime, formatter)}</td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Редактировать</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
