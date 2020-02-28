$(document).ready(function() {
	$.ajax({
		async : true,
		cache : false,
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		type : 'post',
		url : 'loaduserpage',
		error : function() {
			alert('smx失败 ');
		},
		success : function(vm) {
			var doc = {};
			doc.win = vm;
			//$("#demo2").tmpl(doc).appendTo('#navi');
			$("#demo10").tmpl(doc).appendTo('#naviga');
			$("#demo").tmpl(doc).appendTo('#allgoodsframe');
		}
	});
	$.ajax({
		async : true,
		cache : false,
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		type : 'post',
		url : 'loaduseraddr',
		error : function() {
			alert('smx失败 ');
		},
		success : function(vm) {
			if(vm.id == -1){
				$(".jumbotron").show();
			}else{
				$("#top").show();
				$("#addr1").val(vm.name);
				$("#addr2").val(vm.phone);
				$("#addr3").val(vm.address);
			}
		}
	});
});