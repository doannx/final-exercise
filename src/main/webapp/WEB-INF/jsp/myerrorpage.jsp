<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en-US">
<head>
<meta charset="utf-8">
<script type='text/javascript' src='https://code.jquery.com/jquery-2.1.4.js'></script>
<script type='text/javascript' src='http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.1.0.js'></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="/static/css/pim.css">
<title><spring:message code="application.title" /></title>
</head>

<body>
  <!-- HEADER -->
  <div class="header">
    <img src="/static/img/logo_elca.png" />
    <p class="v-align-center">
      <spring:message code="header.title" />
    </p>
    <div class="multilingual-link"></div>
    <a href="#" id="help"><spring:message code="menu.help" /></a> <a href="#" id="logout"><spring:message
        code="menu.logout" /></a>
  </div>
  <!-- LEFT MENU -->
  <div>
    <table style="width: 50%; margin: 50px auto;">
      <tr>
        <td align="right" style="width: 20%; padding-right: 10px;"><span style="color: #FF7F50; font-size: 150pt;" class="glyphicon glyphicon-remove-sign"></span></td>
        <td align="left" style="width: 50%; padding-left: 10px;" class="regularItem">
            <spring:message code="error.title" />&nbsp;${errorCode}<br/>
            <spring:message code="error.contact" /><br/>
            <br/>
            <spring:message code="error.or" />&nbsp;<a href="/"><spring:message code="error.back" /></a>
        </td>
      </tr>
    </table>
  </div>
  <!-- END OF LEFT MENU -->
</body>
</html>