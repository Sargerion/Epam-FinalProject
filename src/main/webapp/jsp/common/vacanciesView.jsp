<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>

<c:set var="current_page" value="/jsp/common/vacanciesView.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<head>
    <title><fmt:message key="vacancy_list"/></title>
    <link href="${pageContext.request.contextPath}/css/tables.css" rel="stylesheet">
</head>
<body>
<header class="page-header">
    <nav>
        <div class="logo"><fmt:message key="logo"/></div>
        <ctag:show_avatar/>
        <h2 class="logo" style="font-size: 19px; "><fmt:message key="login_placeholder"/>:${sessionScope.user.getLogin()}</h2>
        <h2 class="logo" style="font-size: 19px;"><fmt:message key="user_role"/>:${sessionScope.user.getType()}</h2>
        <ul>
            <li><a href=<c:choose>
                   <c:when test="${sessionScope.user.getType() eq 'ADMIN'}">
                           "${pageContext.request.contextPath}/jsp/admin/welcomeAdmin.jsp"
                </c:when>
                <c:when test="${sessionScope.user.getType() eq 'COMPANY_HR'}">
                    "${pageContext.request.contextPath}/jsp/hr/welcomeHR.jsp"
                </c:when>
                <c:otherwise>
                    "${pageContext.request.contextPath}/jsp/finder/welcomeFinder.jsp"
                </c:otherwise>
                </c:choose>>
                <fmt:message key="home"/>
                </a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a>
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li><c:import url="/jsp/modules/logoutForm.jsp"/></li>
        </ul>
    </nav>
</header>
<h2 style="text-align: center; font-weight: bold"><fmt:message key="vacancy_list"/></h2>
<ctag:all_vacancies_list/>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/error/error_parts/error_part.jsp"/>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>