<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty successMsg}">
    <div class="alert-box alert-success">
        <c:out value="${successMsg}" />
    </div>
</c:if>

<c:if test="${not empty errorMsg}">
    <div class="alert-box alert-error">
        <c:out value="${errorMsg}" />
    </div>
</c:if>