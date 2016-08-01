function ProjectViewModel(value) {
	var self = this;
	self.prjName = ko.observable(value);
	self.projects = ko.observableArray([]);
	// load list at pageLoad
	$.ajax({
		method : "POST",
		url : "/query",
		data : {
			name : self.prjName
		}
	}).done(function(data) {
		renderPaging($('#hidTotalPage').val(), 1, 1);
		hideNoResult();
		self.projects(data);
		if (data.length == 0) {
			displayNoResult();
		}
	});
	// query
	self.query = function() {
		hideNoResult();
		$.ajax({
			method : "POST",
			url : "/query",
			data : {
				name : self.prjName,
				status : $('#status').val()
			}
		}).done(function(data) {
			$.ajax({
				method : "POST",
				url : "/count",
			}).done(function(data) {
				$('#hidTotalPage').val(data);
				renderPaging(data, 1, 1);
			}).fail(function(data) {
			});
			
			hideNoResult();
			self.projects(data);
			if (data.length == 0) {
				displayNoResult();
			}
		});
	}
}

$(document).ready(function() {
	// set default button
	$("#prjName").bind("keyup", function(event) {
	  // track enter key
	  var keycode = (event.keyCode ? event.keyCode : (event.which ? event.which : event.charCode));
	  if (keycode == 13) {
	     $('#btnSearch').click();
	     return false;
	  } else  {
	     return true;
	}});
	// process footer
	$('#tbl-footer').css('visibility', 'hidden');
	var prevPage = "1";
	var textCriteria = $('#textCriteria').val();
	var myViewModel = new ProjectViewModel(textCriteria);
	ko.applyBindings(myViewModel);
	//
	$('#btnReset').click(function(){
		hideNoResult();
		$('#prjName').val('');
		$('#status').val('-1');
		$('#filterNumber').val('');
		$('#filterName').val('');
		$('#filterStatus').val('');
		$('#filterCustomer').val('');
		$('#filterStartDate').val('');
		$.ajax({
			method : "POST",
			url : "/query",
			data : {
				name : $('#prjName').val()
			}
		}).done(function(data) {
			$.ajax({
				method : "POST",
				url : "/count",
			}).done(function(data) {
				$('#hidTotalPage').val(data);
				renderPaging(data, 1, 1);
			}).fail(function(data) {
			});
			
			hideNoResult();
			myViewModel.projects(data);
			if (data.length == 0) {
				displayNoResult();
			}
		});
	});
	$('select').change(function() {
		if ($(this).children('option:first-child').is(':selected')) {
			$(this).addClass('placeholder');
		} else {
			$(this).removeClass('placeholder');
		}
	});
	$('#deleteSelectedItems').click(function(){
		$('.tbl-list tr').each(function (i, row) {
	        var $row = $(row);
	        if($row.find('input:checked').val()==="on"){
	        	window.location = "/delete/" + $row.find('input:checked').attr("id");
	        }
		});
	});
	window.next = function() {
		// hide the footer
		$('#tbl-footer').css('visibility', 'hidden');
		nextPage = +prevPage + 1;
		if (nextPage != prevPage) {
			$.ajax({
				method : "POST",
				url : "/paging/" + nextPage,
			}).done(function(data) {
				myViewModel.projects(data);
				renderPaging($('#hidTotalPage').val(), 1, nextPage);
				$('#paging' + nextPage).addClass('active');

				$('#paging' + prevPage).removeClass('active');
				$('#paging' + prevPage).attr('href', '#');

				prevPage = nextPage;
			}).fail(function(status, error) {
			    alert(status);
			});
		}
	};
	window.prev = function() {
		// hide the footer
		$('#tbl-footer').css('visibility', 'hidden');
		var nextPage = prevPage - 1;
		if (nextPage != prevPage) {
			$.ajax({
				method : "POST",
				url : "/paging/" + nextPage,
			}).done(function(data) {
				myViewModel.projects(data);
				renderPaging($('#hidTotalPage').val(), 1, nextPage);
				$('#paging' + nextPage).addClass('active');

				$('#paging' + prevPage).removeClass('active');
				$('#paging' + prevPage).attr('href', '#');

				prevPage = nextPage;
			});
		}
	};
	window.paging = function(nextPage) {
		// hide the footer
		$('#tbl-footer').css('visibility', 'hidden');
		if (nextPage != prevPage) {
			$.ajax({
				method : "POST",
				url : "/paging/" + nextPage,
			}).done(function(data) {
				myViewModel.projects(data);
				renderPaging($('#hidTotalPage').val(), 1, nextPage);
				$('#paging' + nextPage).addClass('active');

				$('#paging' + prevPage).removeClass('active');
				$('#paging' + prevPage).attr('href', '#');

				prevPage = nextPage;
			});
		}
	};
	window.sort = function(colName) {
		// hide the footer
		hideNoResult();
		$.ajax({
			method : "POST",
			url : "/sort/" + colName,
		}).done(function(data) {
			renderPaging($('#hidTotalPage').val(), 1, 1);
			hideNoResult();
			myViewModel.projects(data);
			if (data.length == 0) {
				displayNoResult();
			}
		});
	};
	window.clickCheckbox = function() {
		var selected = 0;
		$('.tbl-list tr').each(function (i, row) {
	        var $row = $(row);
	        if($row.find('input:checked').val()==="on"){
	        	selected++;
	        }
		});
		if (selected === 0) {
			$('#selectedItem').html('');
			$('#tbl-footer').css('visibility', 'hidden');
		} else {
			$('#selectedItem').html(selected + $('#hidSelectedItemText').val());
			$('#deleteSelectedItems').css('visibility', 'visible');
			$('#tbl-footer').css('visibility', 'visible');
		}
	};
	window.displayNoResult = function() {
		$('#selectedItem').html($('#hidNoPrjFoundText').val());
		$('#tbl-footer').css('visibility', 'visible');
		$('#deleteSelectedItems').css('visibility', 'hidden');
		$('#filterNumber').attr('disabled', 'disabled');
		$('#filterName').attr('disabled', 'disabled');
		$('#filterStatus').attr('disabled', 'disabled');
		$('#filterCustomer').attr('disabled', 'disabled');
		$('#filterStartDate').attr('disabled', 'disabled');
	};
	window.hideNoResult = function() {
		$('#tbl-footer').css('visibility', 'hidden');
		$('#deleteSelectedItems').css('visibility', 'hidden');
		
		$('#filterNumber').removeAttr('disabled');
		$('#filterName').removeAttr('disabled');
		$('#filterStatus').removeAttr('disabled');
		$('#filterCustomer').removeAttr('disabled');
		$('#filterStartDate').removeAttr('disabled');
	};
	window.renderPaging = function(totalPage, beginIndex, currentPage) {
		var html = "";
		if (totalPage > 1) {
			html = '<ul class="pagination" style="height: 32px;">';
			if (currentPage == 1) {
				html += '<li class="disabled"><a href="#"><img src="/static/img/previous_page.png"/></a></li>';
			} else {
				html += '<li><a onclick="javascript:prev()"><img src="/static/img/previous_page.png"/></a></li>';
			}
			for (i = beginIndex; i <= totalPage; i++) {
				if (i === currentPage) {
					html += '<li class="active" id="paging'
							+ i
							+ '"><a onclick="javascript:paging(' + i  + ');">' + i + '</a></li>';
				} else {
					html += '<li id="paging'
							+ i
							+ '"><a href="#" onclick="javascript:paging(' + i + ');">' + i + '</a></li>';
				}
			}
			if (currentPage == totalPage) {
				html += '<li class="disabled"><a href="#"><img src="/static/img/nextpage_icon.png" /></a></li>';
			} else {
				html += '<li><a onclick="javascript:next()"><img src="/static/img/nextpage_icon.png" /></a></li>';
			}
			html += '</ul>';
		}
		$('#paging').empty();
		$('#paging').html(html);
	};
	window.filter = function() {
		$.ajax({
			method : "POST",
			url : "/filter",
			data : {
				id : $('#filterNumber').val(),
				name : $('#filterName').val(),
				status : $('#filterStatus').val(),
				customer : $('#filterCustomer').val(),
				startdate : $('#filterStartDate').val()
			}
		}).done(function(data) {
			hideNoResult();
			myViewModel.projects(data);
			if (data.length == 0) {
				displayNoResult();
				$('#filterNumber').removeAttr('disabled');
				$('#filterName').removeAttr('disabled');
				$('#filterStatus').removeAttr('disabled');
				$('#filterCustomer').removeAttr('disabled');
				$('#filterStartDate').removeAttr('disabled');
			}
		});
	}
});