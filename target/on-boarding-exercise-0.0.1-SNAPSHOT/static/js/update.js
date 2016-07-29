$(document).ready(function() {
	var config = {
		'.chosen-select-width' : {
			width : "75%"
		}
	}
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	$("#datepicker1").datepicker({
		dateFormat : "dd.mm.yy"
	});
	$('#btn1').click(function() {
		$("#datepicker1").focus();
	});
	$("#datepicker2").datepicker({
		dateFormat : "dd.mm.yy"
	});
	$('#btn2').click(function() {
		$("#datepicker2").focus();
	});
	
	// set default button
	$("#input").bind("keyup", function(event) {
	  // track enter key
	  var keycode = (event.keyCode ? event.keyCode : (event.which ? event.which : event.charCode));
	  if (keycode == 13) {
		 alert('enter');
	     $('#btnCreatePrj').click();
	     return false;
	  } else  {
	     return true;
	}});
});