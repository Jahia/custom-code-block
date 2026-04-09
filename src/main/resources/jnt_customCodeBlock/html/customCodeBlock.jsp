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
${currentNode.properties.html.string}
<c:if test="${! empty js}">
<template:addResources targetTag="body">
<script>
${js}
</script>
</template:addResources>
</c:if>
<c:if test="${renderContext.editMode}">
    <c:if test="${empty functions:removeHtmlTags(html)}">
        ${currentNode.name}
    </c:if>
</c:if>
