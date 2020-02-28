$(document).ready(function() {
	
	var orderData = [];
	var id;// 保存被点看的DIV的class
	function getApi() {
		setTimeout(getApi, 10 * 1000);
		var pata = {};
		pata.id = orderData[orderData.length - 1].id;
		$.ajax({
			async : true,
			cache : false,
			data : pata,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			type : 'post',
			url : 'getRealTimeOrder',
			success : function(da) {
				for (var i = 0; i < da.length; i++) {
					orderData.push(da[i]);
				}
				var order = {};
				order.list = da;
				$("#demo7").tmpl(order).appendTo('#tableplace1');
			},
			error : function() {
				alert('smx失败 ');
			}
		});
	}

	$(document).on('click', '.orderdetiles', function() {
		var index = this.name;
		id = this.name;
		index = index.substr(1, index.length);
		var detile = orderData[Number(index)];
		$('.ordermsg').empty();
		$("#demo8").tmpl(detile).appendTo('.ordermsg');
		$("#orderdetile").modal("show");
	});
	
	$(document).on('click', '.print', function() {
		$("#orderdetile").modal("hide");
		var pata = {};
		pata.id = orderData[Number(id.substr(1, id.length))].id;
		$.ajax({
			async : true,
			cache : false,
			data : pata,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			type : 'post',
			url : 'finishOrder',
			success : function(vm) {
				$("." + id).remove();
			},
			error : function() {
				alert('抱歉现在系统繁忙.请稍后重试');
			}
		});
	});

	// --------------
	var indexlist = [];
	var ordernums = [];

	$(document).on('click', '.lookorder', function() {
		$('.moreorder').empty();
		$(":checkbox:checked").each(function() {
			var index = $(this).attr("id");
			indexlist.push(index);
			index = index.substr(1, index.length);
			var detile = orderData[Number(index)];
			ordernums.push(detile.orderNum);
			$("#demo9").tmpl(detile).appendTo('.moreorder');
		});
		$("#moredetile").modal("show");
	});
	$(".moreprint").click(function() {
		var model = {};
		model.ordernums = ordernums;
		$.ajax({
			async : true,
			cache : false,
			data : model,
			traditional : true,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			type : 'post',
			url : 'finishOrders',
			success : function(vm) {
				for (var i = 0; i < indexlist.length; i++) {
					$("." + indexlist[i]).remove();
				}
				$("#moredetile").modal("hide");
				indexlist = [];
			},
			error : function() {
				alert('抱歉现在系统繁忙.请稍后重试');
			}
		});
		ordernums = [];
	});
	$(document).on('click', '#orderMa', function() {
		if(localStorage.getItem('nowpage')==0){
			$("#rightspace").empty();
			orderData = [];
			$.ajax({
				async : true,
				cache : false,
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				type : 'post',
				url : 'getorderlist',
				success : function(vm) {
					localStorage.setItem('nowpage',1);
					$("#orderpage").tmpl().appendTo('#rightspace');
					orderData = vm;
					var order = {};
					order.list = vm;
					$("#demo7").tmpl(order).appendTo('#tableplace1');
					getApi();
				},
				error : function() {
					alert('smx失败 ');
				}
			});
		}
	});
	
});