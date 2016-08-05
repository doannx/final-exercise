<%@ include file="/WEB-INF/jsp/common/commonHeader.jsp"%>
<script type='text/javascript' src='/static/js/moment.js'></script>
<script type='text/javascript' src='/static/js/ProjectViewModel.js'></script>
<!-- CONTENT -->
<input type="hidden" id="textCriteria"
	value="${sessionScope.TEXT_SEARCH_CRITERIA}" />
<input type="hidden" id="statusCriteria"
	value="${sessionScope.STATUS_SEARCH_CRITERIA}" />
<input type="hidden" id="hidSelectedItemText"	value="&nbsp;<spring:message code="grid.itemsslected" />" />
<input type="hidden" id="hidNoPrjFoundText" value="&nbsp;<spring:message code="gird.noprjfound" />" />
<input type="hidden" id="hidConfirmDelText" value="&nbsp;<spring:message code="confirm.delete" />" />
<input type="hidden" id="hidConfirmCloneText" value="&nbsp;<spring:message code="confirm.clone" />" />
<input type="hidden" id="hidSearchRes" value="0" />
<input type="hidden" id="hidTotalPage" value="${totalPage }" />
<input type="hidden" id="hidBeginIndex" value="${beginIndex }" />
<input type="hidden" id="hidCurrentPage" value="${currentPage }" />
<div class="content">
	<div class="header">
		<spring:message code="screen.list" />
	</div>
	<br class="clear" />
	<div class="header-list">
		<!-- input search criteria -->
		<div style="margin-left: 0%;">
			<table class="table normalText">
				<tr valign="middle">
					<td style="padding-left: 0px; border: 0px solid white; width: 25%;">
						<input type="text" class="form-control" id="prjName" autofocus
						data-bind="value: prjName"
						placeholder="<spring:message code="screen.textplaceholder" />" />
					</td>
					<td style="border: 0px solid white; width: 15%;"><select
						class="form-control placeholder" id="status">
							<option value="-1" disabled selected hidden=true><spring:message
									code="screen.statusplaceholder" /></option>
							<option value="FIN"><spring:message code="status.fin" /></option>
							<option value="INP"><spring:message code="status.inp" /></option>
							<option value="NEW"><spring:message code="status.new" /></option>
              <option value="MAI"><spring:message code="status.mai" /></option>
							<option value="PLA"><spring:message code="status.pla" /></option>
					</select></td>
					<td style="border: 0px solid white; width: 2%;">&nbsp;</td>
					<td style="border: 0px solid white; width: 12%;">
            <input type="button" class="btn btn-primary" style="width: 150px;" value="<spring:message code='btn.search' />" id="btnSearch"
						        data-bind="click: query" />
          </td>
					<td style="border: 0px solid white; vertical-align: middle;"><a
						href="#" id="btnReset"><spring:message code="btn.reset" /></a></td>
				</tr>
			</table>
		</div>

		<!-- display result list -->
		<table class="tbl-list" id="tbl-list-result">
			<thead class="">
				<tr>
					<td style="width: 4%;">&nbsp;</td>
					<td style="width: 10%;">
						<a onclick="javascript:sort('id');" style="cursor: pointer;color:#666666;text-decoration: none;"><spring:message code="grid.number" /></a>
						<br/>
						<input type="text" id="filterNumber" class="form-control width50px" onkeyup="javascript:filter();"/>
					</td>
					<td><a onclick="javascript:sort('name');"
						style="cursor: pointer;color:#666666;text-decoration: none;"><spring:message code="grid.name" /></a>
						<br/>
						<input type="text" id="filterName" class="form-control width50px" onkeyup="javascript:filter();"/>
					</td>
					<td style="width: 15%;"><spring:message code="grid.status" />
						<br/>
						<input type="text" id="filterStatus" class="form-control width50px" onkeyup="javascript:filter();"/>
					</td>
					<td><spring:message code="grid.customer" />
						<br/>
						<input type="text" id="filterCustomer" class="form-control width50px" onkeyup="javascript:filter();"/>
					</td>
					<td style="width: 10%;"><spring:message code="grid.startdate" />
						<br/>
						<input type="text" id="filterStartDate" class="form-control width50px" onkeyup="javascript:filter();"/>
					</td>
					<td><spring:message code="grid.clone" /></td>
          <td><spring:message code="grid.delete" /></td>
				</tr>
			</thead>
			<tbody data-bind="foreach: projects">
				<tr>
					<td><input type="checkbox"
						data-bind="attr: {id: id, disabled: status !== 'New' && status !== 'Nouveau'}"
						onclick="javascript:clickCheckbox();" /></td>
					<td style="text-align: right; padding-right: 10px;"><a style="color: #666666;"
						data-bind="attr: {href: '/detail/' + id}, text: id"></a></td>
					<td data-bind="text: name"></td>
					<td data-bind="text: status"></td>
					<td data-bind="text: customer"></td>
					<td data-bind="text: moment(finishingDate).format('DD.MM.YYYY')"></td>
					<td style="text-align: center;padding: 0px;"><a
            style="vertical-align: middle; padding-bottom: 2px; color: #FF7F50;cursor: pointer;"
            data-bind="visible: status != 'Maintenance' && status != 'Entretien', attr: {onclick: 'clone(' + id + ',\'' + name + '\')'}">
            <span class="glyphicon glyphicon-plus"></span></a></td>
          <td><a
						style="vertical-align: middle; padding-bottom: 2px; color: #FF7F50;cursor: pointer;"
						data-bind="visible: status == 'New' || status == 'Nouveau', attr: {onclick: 'del(' + id + ',\'' + name + '\')'}">
            <span class="glyphicon glyphicon-trash"></span></a></td>
				</tr>
			</tbody>
			<tfoot id="tbl-footer">
				<tr>
					<td colspan="3" align="left" style="padding-left: 10px;"><div
							style="color: #1E90FF;" id="selectedItem"></div></td>
					<td colspan="5" align="right" style="padding-right: 30px;"><a
						href="#" style="color: #FF7F50;" id="deleteSelectedItems"><spring:message
								code="grid.deleteselected" />&nbsp;<span
							class="glyphicon glyphicon-trash"></span></a></td>
			</tfoot>
		</table>

		<!-- pagination -->
		<div id="paging" align="right"
			style="right: 20%; position: relative; height: 32px;"></div>

		<!-- end of pagination -->
	</div>
</div>
<!-- END OF CONTENT -->
<%@ include file="/WEB-INF/jsp/common/commonFooter.jsp"%>