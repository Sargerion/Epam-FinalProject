<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty requestScope.errorMessage}">
    <p style="color: red; font-size: 15px; text-align: center;border: 1px solid red;padding: 15px;">${requestScope.errorMessage}</p>
</c:if>

<c:if test="${not empty requestScope.error_reg_fail}">
    <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${requestScope.error_reg_fail}</p>
</c:if>
<c:if test="${not empty requestScope.error_login}">
    <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${requestScope.error_login}</p>
</c:if>
<c:if test="${not empty requestScope.error_dif_passws}">
    <p style="color: red; font-size: 13px; text-align: center;border: 1px solid red;padding: 10px;">${requestScope.error_dif_passws}</p>
</c:if>

