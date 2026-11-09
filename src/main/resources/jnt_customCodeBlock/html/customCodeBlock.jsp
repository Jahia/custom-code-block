<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions" %>
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>

<c:set var="html" value="${currentNode.properties.html.string}"/>
<c:set var="css" value="${currentNode.properties.css.string}"/>
<c:set var="js" value="${currentNode.properties.js.string}"/>
<c:if test="${! empty css}">
<template:addResources>
<style>
${css}
</style>
</template:addResources>
</c:if>
${html}
<c:if test="${! empty js}">
<template:addResources targetTag="body">
<script>
${js}
</script>
</template:addResources>
</c:if>
<c:if test="${renderContext.editMode && (empty html || empty functions:removeHtmlTags(html))}">
<template:addResources>
<style>
.jnt-custom-code-block__empty-notice {
    border: 2px dashed #f90;
    padding: 1rem;
    color: #595959;
    font-style: italic;
    background: #fff8e1;
}
</style>
</template:addResources>
    <c:choose>
        <c:when test="${empty css && empty js}">
            <p class="jnt-custom-code-block__empty-notice">
                [Empty custom code block: <c:out value="${currentNode.name}"/>]
            </p>
        </c:when>
        <c:otherwise>
            <p class="jnt-custom-code-block__empty-notice">
                [Custom code block — CSS/JS only: <c:out value="${currentNode.name}"/>]
            </p>
        </c:otherwise>
    </c:choose>
</c:if>
