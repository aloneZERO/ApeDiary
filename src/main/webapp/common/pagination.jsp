<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  分页导航：首页和上一页 -->
<c:choose>
  <c:when test="${page.pageNum==1}">
    <li class='disabled'><a href='#'>首页</a></li>
  </c:when>
  <c:otherwise>
    <li><a href='${pageContext.request.contextPath}${page.url}?num=1'>首页</a></li>
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${page.pageNum==1}">
    <li class='disabled'><a href='#'>上一页</a></li>
  </c:when>
  <c:otherwise>
    <li><a href='${pageContext.request.contextPath}${page.url}?num=${page.pageNum-1}'>上一页</a></li>
  </c:otherwise>
</c:choose>

<!-- 分页导航：数字页码 -->
<c:forEach begin="${page.pageNum-2>0?page.pageNum-2:1}"
  end="${page.pageNum+2<page.totalPageNum?page.pageNum+2:page.totalPageNum}" var="index">
  <c:choose>
	  <c:when test="${index==page.pageNum}">
	    <li class='active'><a href='#'>${index}</a></li>
	  </c:when>
	  <c:otherwise>
	    <li><a href='${pageContext.request.contextPath}${page.url}?num=${index}'>${index}</a></li>
	  </c:otherwise>
  </c:choose>
</c:forEach>

<!-- 分页导航：下一页和尾页 -->
<c:choose>
  <c:when test="${page.totalPageNum==0 || page.pageNum==page.totalPageNum}">
    <li class='disabled'><a href='#'>下一页</a></li>
  </c:when>
  <c:otherwise>
    <li><a href='${pageContext.request.contextPath}${page.url}?num=${page.pageNum+1}'>下一页</a></li>
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${page.totalPageNum==0 || page.pageNum==page.totalPageNum}">
    <li class='disabled'><a href='#'>尾页</a></li>
  </c:when>
  <c:otherwise>
    <li><a href='${pageContext.request.contextPath}${page.url}?num=${page.totalPageNum}'>尾页</a></li>
  </c:otherwise>
</c:choose>
