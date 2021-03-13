<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="property.pagecontent"/>

<html>
<body>
<form style="position:relative;" name="forward_avatar_form" method="post" action="<c:url value="/controller"/>">
    <input type="hidden" name="command" value="forward_to_change_avatar">
    <input type="submit" style="margin-left: 50%;transform:translate(-50%);width: 120px;height: 76px;border: none;outline: none;background: royalblue;
    cursor: pointer; color: snow;border-radius: 4px;font-size:16px;transition: .3s;white-space: normal;" value="<fmt:message key="upload_title"/>">
</form>
</body>
</html>