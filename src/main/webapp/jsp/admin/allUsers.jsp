<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="/jsp/error/500.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctag" uri="custom_tag" %>
<c:set var="current_page" value="/jsp/admin/allUsers.jsp" scope="request"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>
<html>
<head>
    <title><fmt:message key="all_users"/></title>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/tables.css" rel="stylesheet">
</head>
<body>
<header class="page-header">
    <nav>
        <div class="logo"><fmt:message key="logo"/></div>
        <h2 class="logo" style="font-size: 19px; "><fmt:message key="login_placeholder"/>:${sessionScope.user.getLogin()}</h2>
        <h2 class="logo" style="font-size: 19px;"><fmt:message key="user_role"/>:${sessionScope.user.getType()}</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/jsp/admin/welcomeAdmin.jsp"><fmt:message key="home"/></a></li>
            <li>
                <a href="#"><fmt:message key="languages_list_name"/></a href="#">
                <ul>
                    <li><c:import url="/jsp/modules/locale.jsp"/></li>
                </ul>
            </li>
            <li>
                <a href="#" class="delim"><fmt:message key="service"/></a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/jsp/admin/hrRequestsView.jsp"><fmt:message key="see_hrs"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/admin/allUsers.jsp"><fmt:message key="all_users"/></a></li>
                    <li>
                        <a href="#">More<span class="picture"></span></a>
                        <ul>
                            <li><a href="#">Sub1</a></li>
                            <li><a href="#">Sub2</a></li>
                            <li><a href="#">Sub3</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li>
                <form method="get" action="<c:url value="/controller"/>">
                    <input type="hidden" name="command" value="log_out">
                    <input type="submit" value="<fmt:message key="logout"/>" class="delim" style="margin-left: 50%;transform:  translate(-50%);width: 120px;height: 34px;border: none;outline: none;background: aliceblue;cursor: pointer;font-size: 16px;color: royalblue;border-radius: 4px;transition: .3s; margin-top: 18px">
                </form>
            </li>
        </ul>
    </nav>
</header>
<h2 style="text-align: center; font-weight: bold"><fmt:message key="all_users"/></h2>
<ctag:all_users_list/>
<c:import url="/jsp/modules/part/message_part.jsp"/>
<c:import url="/jsp/modules/footer.jsp"/>
</body>
</html>