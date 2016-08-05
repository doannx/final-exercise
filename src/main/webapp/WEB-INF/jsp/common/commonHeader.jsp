<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html lang="en-US">
<head>
  <meta charset="utf-8">
  <script type='text/javascript' src='https://code.jquery.com/jquery-2.1.4.js'></script>
  <script type='text/javascript' src='http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.1.0.js'></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.css">
  <!-- Optional theme -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.js"></script>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
  <script src="/static/js/common/common.js"></script>
  <link rel="stylesheet" href="/static/css/pim.css">
  <title><spring:message code="application.title" /></title>
</head>

<body>
<!-- HEADER -->
<div class="header">
  <input type="hidden" id="hidLocale" value="${pageContext.response.locale}"/>
  <img src="/static/img/logo_elca.png" />
  <p><spring:message code="header.title" /></p>
  <div class="multilingual-link">
    <a href="?locale=en" id="en">EN</a>&nbsp;<b>|</b>&nbsp;<a href="?locale=fr" id="fr">FR</a>
  </div>
  <a href="#" id="help" style="color: #2F85FA;"><spring:message code="menu.help" /></a> <a href="#" id="logout" style="color: #9d9d9d;"><spring:message code="menu.logout" /></a>
</div>
<!-- LEFT MENU -->
<div style="width: 25%; position: relative;">
  <div class="menu width50">
    <ul>
      <li><a href="/" class="activateItem" id="linkPrjlist"><spring:message code="menu.projectlist" /></a></li>
      <li style="line-height: 20px;">&nbsp;</li>
      <li><a href="/add" class="activateItem" id="linkNew"><spring:message code="menu.new" /></a></li>
      <li class="potentialItem" id="linkPrj"><spring:message code="menu.project" /></li>
      <li class="regularItem"><spring:message code="menu.customer" /></li>
      <li class="regularItem"><spring:message code="menu.supplier" /></li>
    </ul>
  </div>
</div>
<!-- END OF LEFT MENU -->