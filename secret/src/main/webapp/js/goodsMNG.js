$(document).ready(function() {
	// 用于导航条
	$(".leftdiv").mouseenter(
			function() {
				$(this).css("background", "#363636");
				$(this).find(".leftcolor").css("background",
						"#FBDF47");
				$(this).find(".sp").css("color", "#E9DE51");
			}).mouseleave(
			function() {
				$(this).css("background", "#333333");
				$(this).find(".leftcolor").css("background",
						"#333333");
				$(this).find(".sp").css("color", "#FFFFFF");
			})
	$(document).on('click', '.addgood', function() {
		$("#myModal").modal("show");
	})
	$(document).on('click', '#MoneyMa', function() {
		alert("暂时未写 ...");
	})
	
	function ContentMethod(txt) {
		return "<img src=" + txt +" width=\"240px\" />";
	}

	function bindMethod(){
		$('.ppo').each(function() {
			var element = $(this);
			var id = element.attr('id');
			var txt = element.text();
			element.popover({
				placement:"right",
				trigger: "hover",
				html: true,
				container: "body",
				content: ContentMethod(txt),
	        })
		});
	}
	var updata_food={};
	var updata_findex;
	$(document).on('click', '.alertf', function() {
		var idname = this.name;
		updata_findex = Number(idname.substr(2));
		updata_food = now_foodlist[updata_findex];
		$("#updatafname").val(updata_food.foodName);
		$("#updatafprice").val(updata_food.foodPrice);
		$("#updatafsort").val(updata_food.foodSort.sortName);
		$("#updatafsort").attr("disabled","disabled");
		$("#updatafoodinfo").modal("show");
	})
	

	var now_page;// 当前页面
	var page_sum;// 页面总数
	var search_sort='';// 搜索的类别
	var now_foodlist = []; //加载的食物
	
	// 加载页面
	$.ajax({
		async : true,
		cache : false,
		contentType : "application/json;charset=UTF-8",
		dataType : "json",
		type : 'post',
		url : 'loadfoodlist',
		error : function() {
			alert('smx失败 ');
		},
		success : function(vm) {
			localStorage.setItem('nowpage', 0); //解决页面切换
			$("#initpage").tmpl(vm).appendTo('#rightspace');
			now_page = 1;
			now_foodlist=vm.foodlist;
			$("#demo3").tmpl(vm).appendTo('#tableplace');
			page_sum = Number(vm.page);
			var page = Number(vm.page) + 1;
			for (var i = 2; i < page; i++) {
				$("#afterpage").before("<li class=\"deletableli\"><a>"+ i+ "</a></li>");
			}
			bindMethod();
		}
	});
	// 分页
	function gootherpage(a) {
		$.ajax({
			async : true,
			cache : false,
			data : a,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			type : 'post',
			url : 'gopaging',
			error : function() {
				alert('smx失败 ');
			},
			success : function(vm) {
				$("#tableplace").empty();
				$("#demo3").tmpl(vm).appendTo('#tableplace');
				bindMethod();
			}
		});
	}
	// 搜索后的分页
	function goothersamepage(a) {
		$.ajax({
			async : true,
			cache : false,
			data : a,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			type : 'post',
			url : 'gosamepaging',
			error : function() {
				alert('smx失败 ');
			},
			success : function(vm) {
				$("#tableplace").empty();
				$("#demo3").tmpl(vm).appendTo('#tableplace');
				bindMethod();
			}
		});
	}
	var lpageNum = {};
	$(document).on('click', '.pagination a', function() {
		if (this.name == "afterpage") {
			lpageNum.spageNum = (parseInt(now_page) + 1);
			if (page_sum >= lpageNum.spageNum) {
				if (search_sort == null || search_sort == '') {
					now_page = lpageNum.spageNum;
					gootherpage(lpageNum);
				}else{
					now_page = lpageNum.spageNum;
					lpageNum.searchSort = search_sort;
					goothersamepage(lpageNum);
				}
			}
		} else if (this.name == 'beforepage') {
			lpageNum.spageNum = (parseInt(now_page) - 1);
			if (lpageNum.spageNum >= 1) {
				if (search_sort == null || search_sort == '') {
					now_page = lpageNum.spageNum;
					gootherpage(lpageNum);
				}else{
					now_page = lpageNum.spageNum;
					lpageNum.searchSort = search_sort;
					goothersamepage(lpageNum);
				}
			}
		} else if (parseInt(this.text) != now_page) {
			lpageNum.spageNum = this.text;
			now_page = parseInt(this.text);
			lpageNum.searchSort = search_sort;
			goothersamepage(lpageNum);
		}
	});
	// 搜索商品
	$(document).on('click','#searchfood',function() {
		var data = {};
		var foodSort = {};
		data = $('#searchfoodform').serializeObject();
		foodSort.sortName = data.sortName;
		data.foodSort = foodSort;
		$.ajax({
			async : true,
			cache : false,
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify(data),
			dataType : "json",
			type : 'post',
			url : 'searchfood',
			error : function() {
				alert('smx失败 ');
			},
			success : function(vm) {
				$("#tableplace").empty();
				$(".deletableli").remove();
				$("#demo3").tmpl(vm).appendTo('#tableplace');
				var page = Number(vm.page) + 1;
				page_sum = Number(vm.page);
				now_page = 1;
				search_sort = vm.foodlist[0].foodSort.sortName;
				for (var i = 2; i < page; i++) {
					$("#afterpage").before("<li class=\"deletableli\"><a>"+ i+ "</a></li>");
				}
				bindMethod();
			}
		});			
	});
	
	$(document).on('click', '#goodsMa', function() {
		if(localStorage.getItem('nowpage') == 1){
			$("#rightspace").empty();
				$.ajax({
					async : true,
					cache : false,
					contentType : "application/json;charset=UTF-8",
					dataType : "json",
					type : 'post',
					url : 'loadfoodlist',
					error : function() {
						alert('smx失败 ');
					},
					success : function(vm) {
						localStorage.setItem('nowpage', 0);
						$(".deletableli").remove();
						search_sort='';
						page_sum = Number(vm.page);
						now_page = 1;
						$("#initpage").tmpl(vm).appendTo('#rightspace');
						$("#demo3").tmpl(vm).appendTo('#tableplace');
						var page = Number(vm.page)+1;
						for(var i=2;i<page;i++){
							$("#afterpage").before("<li class=\"deletableli\"><a>"+ i+ "</a></li>");
						}
						bindMethod();
					}
			});
		}
	});
	
	// 删除食物
	$(document).on('click','#tableplace .deletef',function() {
		var pata = {};
		pata.vm = this.name;
		var deletediv = "#" + this.name;
		$.ajax({
			async : true,
			cache : false,
			data : pata,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			type : 'post',
			url : 'deletefood',
			error : function() {
				alert('smx失败 ');
			},
			success : function(vm) {
				$(deletediv).remove();
			}
		});
	});
	//用于更改商品
	$(document).on('click','#falert',function() {
		var fl = new FormData();
		var uploadfile = $("#updataffile").get(0).files[0];
		fl.append('uploadfile', uploadfile);
		fl.append('name', $("#updatafname").val());
		fl.append('price', $("#updatafprice").val());
		fl.append('id',updata_food.id);
		$("#updataffile").val("");
		$.ajax({
			url : 'updatafood',
			type : 'post',
			data : fl,
			async : false,
			contentType : false,
			processData : false,
			success : function(vm) {
				if(vm.id!=null){
					var updata_id = "#F"+vm.id;
					$(updata_id+" .td_fname").text(vm.foodName);
					$(updata_id+" .td_fprice").text(vm.foodPrice);
					now_foodlist[updata_findex].foodName=vm.foodName;
					now_foodlist[updata_findex].foodPrice=vm.foodPrice;
				}
				alert("修改成功");
			},
			error : function() {
				alert("上传失败");
			}
		});
	});	
	
	// 用于上传商品
	$(document).on('click','#fadd',function() {
		var fd = new FormData();
		var uploadfile = $("#ffile").get(0).files[0];
		fd.append('uploadfile', uploadfile);
		fd.append('name', $("#fname").val());
		fd.append('price', $("#fprice").val());
		fd.append('sort', $("#fsort").val())
		$.ajax({
			url : 'ajaxupload',
			type : 'post',
			data : fd,
			async : false,
			contentType : false,
			processData : false,
			success : function(vm) {
				if (vm.id == '-1') {
					alert("上传失败");
				} else {
					$(':input', '#addform').val('');
					$("#demo4").tmpl(vm).appendTo(
							'#tableplace .table');
				}
			},
			error : function() {
				alert("上传失败");
			}
		});
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
})