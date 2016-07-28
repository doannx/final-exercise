<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">

<head>

<meta charset="utf-8">

<script type='text/javascript' src='https://code.jquery.com/jquery-2.1.4.js'></script>

<script type='text/javascript' src='http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.1.0.js'></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

<script type='text/javascript' src='/static/js/ProjectViewModel.js'></script>
<script type='text/javascript' src='/static/js/moment.js'></script>

<title><spring:message code="application.title" /></title>

</head>

<body>

  <div class="container">
    <h2>VALUE_FROM_SESSION_SCOPED_BEAN: ${VALUE_FROM_SESSION_SCOPED_BEAN }</h2>
    <button onclick="javascript:location.href='/';" class="btn btn-default">Go Back</button>

  </div>

</body>

</html>