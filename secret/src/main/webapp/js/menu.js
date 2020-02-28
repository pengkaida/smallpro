$(document).ready(
		function() {
			$.extend({
				"setTrolley" : function(food) {
					var fid = "#" + food.idname;
					var fooddiv = "<div class=\"food\" id=\"" + food.idname
							+ "\"><div class=\"foodName\">" + food.foodName
							+ "</div><div class=\"foodSum\">*<lable>"
							+ food.sum
							+ "</lable></div><div class=\"foodprice\">" + food.price 
							+ "</div>";
					$("#trolleylistmsg").hide();
					$("#trolleylist").append(fooddiv);
					$(fid).hide();
					$(fid).slideDown("slow");
				}
			});

			var juge = true;
			var idnamelist = [];
			var list = [];
			var totallpay = 0;
			
			$(document).on('click', '.buttonaction', function(){
				
				var food = {};
				for (var i = 0; i < list.length; i++) {
					if (list[i].foodName == this.name) {
						list[i].sum = list[i].sum + 1;
						juge = false;
						var upsum = "#" + list[i].idname + " lable";
						$(upsum).html(list[i].sum);
						var addpay = list[i].sum * list[i].price;
						var addprice = "#" + list[i].idname + " .foodprice";
						$(addprice).text(addpay);
						totallpay= totallpay+list[i].price;
						$("#pay").text(totallpay);
					}
				}

				if (juge) {
					var foodprice = "label[name='"+this.name+"']";
					food.foodName = this.name;
					food.sum = 1;
					food.price = Number($(foodprice).html());
					if (idnamelist.length == 0) {
						food.idname = "F" + list.length;
					} else {
						food.idname = idnamelist.shift();
					}
					list.push(food);
					$.setTrolley(food);
					totallpay = Number($("#pay").text())+food.price;
					$("#pay").text(totallpay);
					$("#settlemsg").text("前往结算>>>");
					$("#settlemsg").css("color","#FFFFFF");
					$("#settle").css("background-color","#51D862");
				}
				juge = true;
			});

			$(document).on('click', '.buttoncut', function(){
				for (var i = 0; i < list.length; i++) {
					if (list[i].foodName == this.name) {
						list[i].sum = list[i].sum - 1;
						var iszero = list[i].sum;
						if (iszero == 0) {
							totallpay= totallpay-list[i].price;
							$("#pay").text(totallpay);
							idnamelist.push(list[i].idname);
							$("#" + list[i].idname).remove();
							list.splice(i, 1);
							var hidediv = ".ac[name='" + this.name + "']";
							$(hidediv).hide();
							var showdiv = ".buttons[name='"+this.name+"']";    //ac仅仅区别其他元素
							$(showdiv).show();
						} else {
							totallpay= totallpay-list[i].price;
							$("#pay").text(totallpay);
							var cutprice = "#" + list[i].idname + " .foodprice";
							$(cutprice).text((list[i].sum * list[i].price));
							var cutsum = "#" + list[i].idname + " lable";
							$(cutsum).html(list[i].sum);
						}
					}
				}
				if (list.length == 0) {
					$("#trolleylistmsg").show();
					$("#settlemsg").text("购物车是空的");
					$("#settlemsg").css("color","#2C2C2C");
					$("#settle").css("background-color","#E4E4E4");
				}
			});
			
			$(document).on('click', '.buttonaction', function(){
				$(this).hide();
				var showdiv = ".ac[name='"+this.name+"']";    //ac仅仅区别其他元素
				$(showdiv).show();
			});
			var aid;
			$(document).on('click', '.navison a', function(){
				if(aid!="#"+this.name){
					aid = "#"+this.name;
					$('body,html').animate({scrollTop: ($(aid).offset().top-50)},1000);
				}
			});
			$(document).on('click', '.navison', function(){
				if(aid!="#"+this.name){
					aid = "#"+this.name;
					$('body,html').animate({scrollTop: ($(aid).offset().top-50)},1000);
				}
			});
			
			$("#youqu").click(function(){
			    $(".jumbotron").slideUp(3000);
			    $("#top").slideDown();
			  }) ;
			  
			$("#settle").click(function(){
				if($("#settlemsg").text()=="前往结算>>>"){
					$(".modal-body").empty();
					$(".modal-footer").empty();
					$.ajax({
						async : true,
						cache : false,
						data : JSON.stringify(list),
						contentType : "application/json;charset=UTF-8",
						dataType : "json",
						type : 'post',
						url : 'calculate',
						error : function() {
							alert('服务器连接失败，请稍后重试 ');
						},
						success : function(vm) {
							if(vm.id!=-1){
								$("#demo5").tmpl(vm).appendTo('.modal-body');
								$("#demo6").tmpl(vm).appendTo('.modal-footer');
								$("#myModal1").modal("show");
							}else{
								alert('请补全收件人信息');			
							}
						}
					});
				}
			  });  
			  
			$.fn.serializeObject = function() {
				var o = {};
				var a = this.serializeArray();
				$.each(a, function() {
								if (o[this.name]) {
									if (!o[this.name].push) {
										o[this.name] = [ o[this.name] ];
									}
									o[this.name].push(this.value || '');
								} else {
									o[this.name] = this.value || '';
								}
							});			
				return o;									
			};
			
			$("#alteraddr").click(function(){
				var para = $('#alteraddrform').serializeObject();
				para = JSON.stringify(para);
			  	$.ajax({
						async : true,
						cache : false,
						data : para,
						contentType : "application/json;charset=UTF-8",
						dataType : "json",
						type : 'post',
						url : 'addaddress',
						success : function(vm) {
							alert('修改成功 ');
						},
						error : function() {
							alert('修改失败 ，服务器连接失败，请稍后重试');
						}
					});
			  });
			$(document).on('click', '#paybtn', function() {
				var pata = $("#payform").serializeObject();
				$.ajax({
							async : true,
							cache : false,
							data : pata,
							contentType : "application/x-www-form-urlencoded;charset=UTF-8",
							dataType : "json",
							type : 'post',
							url : 'judgeprice',
							success : function(vm) {
								 if(vm.code==100){
								 	window.open("http://47.113.124.24/secret/success.html",'_blank');
								 }else{
								 	window.open("http://47.113.124.24/secret/error.html",'_blank');
								 }
							},
							error : function() {
								alert('服务器出错，请稍后再点餐');
							}
						});
			})
			
			$("#youqu").click(function(){
				var para = $('#addrform').serializeObject();
				para = JSON.stringify(para);
			  	$.ajax({
						async : true,
						cache : false,
						data : para,
						contentType : "application/json;charset=UTF-8",
						dataType : "json",
						type : 'post',
						url : 'addaddress',
						success : function(vm) {
							$("#addr1").val(vm.name);
							$("#addr2").val(vm.phone);
							$("#addr3").val(vm.address);
						},
						error : function() {
							alert('服务器连接失败，请稍后重试 ');
						}
					});
			  }); 
		});