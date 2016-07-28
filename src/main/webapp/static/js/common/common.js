$(document).ready(function() {
	// change lang link state
	if ($('#hidLocale').val() === "en") {
		$('#en').css('color', 'gray');
		$('#en').removeAttr('href');
		$('#fr').css('color', 'blue');
		$('#fr').attr('href', '?locale=fr');
	} else if ($('#hidLocale').val() === "fr") {
		$('#fr').css('color', 'gray');
		$('#fr').removeAttr('href');
		$('#en').css('color', 'blue');
		$('#en').attr('href', '?locale=en');
	} else {
		$('#en').css('color', 'gray');
		$('#en').removeAttr('href');
		$('#fr').css('color', 'blue');
		$('#fr').attr('href', '?locale=fr');
	}
});