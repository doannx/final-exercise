<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">

<head>

<meta charset="utf-8">

<script type='text/javascript' src='https://code.jquery.com/jquery-2.1.4.js'></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script type='text/javascript' src='http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.1.0.js'></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">

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
    <form class="form-horizontal" role="form" action="/update" method="post" id="frmUpdate">
      <input type="hidden" id="id" name="id" value="${project.id }" />
      <h2>Project Detail</h2>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <div class="col-sm-10">
            <table class="table table-striped form-group">
              <thead>
                <tr>
                  <th>Project Name</th>
                  <th>Finishing Date</th>
                </tr>
              </thead>
              <tr>
                <td><input type="text" id="name" name="name" value="${project.name}" /></td>
                <td><input type="text" id="finishingDate" name="finishingDate" 
                            value="<fmt:formatDate pattern="dd/MM/yyyy" value="${project.finishingDate}" />" 
                            data-toggle="tooltip" title="Input pattern: DD/MM/YYYY, i.e.: 31/12/2016" data-placement="right"/></td>
              </tr>
              <tr><td colspan="2"><label id="errName"></label></td></tr>
            </table>
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <input type="submit" value="Update" id="btnUpdate" class="btn btn-default"/>
          
        </div>
      </div>
    </form>
    <button onclick="javascript:location.href='/';" class="btn btn-default">Go Back</button>
    
  </div>

</body>

</html>