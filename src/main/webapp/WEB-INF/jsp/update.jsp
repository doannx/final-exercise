<%@ include file="/WEB-INF/jsp/common/commonHeader.jsp"%>
<script type='text/javascript' src='/static/js/update.js'></script>

<!-- <link rel="stylesheet" href="/static/css/style.css"> -->
<link rel="stylesheet" href="/static/css/chosen.css">
<script src="/static/js/chosen.jquery.js" type="text/javascript"></script>

<!-- CONTENT -->
<div class="content">
  <div class="header">
    <c:if test="${empty project.id }">
      <spring:message code="screen.new" />
    </c:if>
    <c:if test="${not empty project.id}">
      <spring:message code="screen.edit" />
    </c:if>
  </div>
  <br class="clear" />
  <c:if test="${sessionScope.ERROR_STATUS }">
    <div class="header alert alert-danger" style="font-size: 14pt; padding-left: 15px; margin-top: 5px; height: 50px; padding-top: 10px; padding-bottom: 10px;">
      <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
      <strong>${sessionScope.ERROR_CONTENT }</strong>
    </div>
  </c:if>
  <br class="clear" />
  <form:form method="post" modelAttribute="project" action="/update">
  	<input type="hidden" id="mode" name="mode" value="${UPDATE_MODE}"/>
  	<input type="hidden" id="hidUpdatePrjBtn" name="hidUpdatePrjBtn" value="<spring:message code="btn.updateprj" />"/>
  	<form:hidden path="id"/>
  	<form:hidden path="version"/>
    <div class="form">
      <div style="float: left; width: 5%;">&nbsp;</div>
      <div style="float: left; width: 95%; border: 0px #E5E4E2 solid; display: table-cell;">
          <table class="tbl normalText">
            <tr>
              <td><spring:message code="form.prjnumber" />&nbsp;<span class="mandatory">*</span></td>
              <td><form:input maxlength="4" id="prjnumber" path="number" class="form-control height30px" cssErrorClass="form-control height30px error" autofocus="true"/></td>
              <td colspan="2" style="padding-left: 10px;"><form:errors path="number" cssClass="error-text" /></td>
            </tr>
            <tr>
              <td><spring:message code="form.prjname" />&nbsp;<span class="mandatory">*</span></td>
              <td colspan="3"><form:input path="name" class="form-control height30px" cssErrorClass="form-control height30px error" style="width: 100%"/></td>
            </tr>
            <tr>
              <td><spring:message code="form.customer" />&nbsp;<span class="mandatory">*</span></td>
              <td colspan="3"><form:input path="customer" class="form-control height30px" cssErrorClass="form-control height30px error" style="width: 100%"/></td>
            </tr>
            <tr>
              <td><spring:message code="form.group" />&nbsp;<span class="mandatory">*</span></td>
              <td>
                <form:select path="group" class="form-control" cssErrorClass="form-control height30px error"  >
                  <form:option value="-1" disabled="true" selected="true" hidden="true"><spring:message code="ddl.selectgroup" /></form:option>
                  <form:options items="${allGroups}" itemValue="id" itemLabel="name"></form:options>
                </form:select>
              </td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><spring:message code="form.members" /></td>
              <td colspan="3">
              	<form:select path="members" data-placeholder=" " class="form-control height30px chosen-select" multiple="true" items="${allMember}" itemValue="id" itemLabel="displayName" />
              </td>
            </tr>
            <tr>
              <td><spring:message code="form.status" />&nbsp;<span class="mandatory">*</span></td>
              <td><form:select path="status" class="form-control" cssErrorClass="form-control height30px error" items="${allStatus}" itemValue="id" itemLabel="name" /></td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><spring:message code="form.startdate" />&nbsp;<span class="mandatory">*</span></td>
              <td>
                <div class="input-group">
                  <form:input path="finishingDate" type="text" id="datepicker1" class="form-control height30px" cssErrorClass="form-control height30px error" style="border-right: none;"/>
                  <span class="input-group-addon" id="btn1" style="cursor:pointer; background-color: white; border: 1px solid #ccc; border-left: none;">
                              <span class="glyphicon glyphicon-calendar"></span>
                  </span>
                </div>
              </td>
              <td style="text-align: center;"><spring:message code="form.enddate" /></td>
              <td>
                <div class="input-group">
                  <form:input path="endDate" type="text" id="datepicker2" class="form-control height30px" cssErrorClass="form-control height30px error" style="border-right: none;"/>
                  <span class="input-group-addon" id="btn2" style="cursor:pointer; background-color: white; border: 1px solid #ccc; border-left: none;">
                              <span class="glyphicon glyphicon-calendar"></span>
                  </span>
                </div>
                <form:errors path="endDate" cssClass="error-text"></form:errors>
			       </td>
            </tr>
          </table>
      </div>
    </div>
    <br class="clear" />
    <div class="form-button" style="right: 0%; text-align:right; padding-bottom: 20px;">
      <input type="button" class="btn btn-primary" style="width: 200px;" 
          value="<spring:message code='btn.cancel' />" onclick="javascript:location.href='/'" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="submit" id="btnCreatePrj" class="btn btn-primary" style="width: 200px;" value="<spring:message code='btn.createprj' />" />
    </div>
  </form:form>
</div>
<!-- END OF CONTENT -->
<script type="text/javascript">
    var config = {
      '.chosen-select'           : {},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
  </script>
<%@ include file="/WEB-INF/jsp/common/commonFooter.jsp"%>