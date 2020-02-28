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

	$(document).ready(function() {
		$("#button").click(function() {
			var para = $('#loginform').serializeObject();
			para = JSON.stringify(para);
			$.ajax({
				async : true,
				cache : false,
				data : para,
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				type : 'post',
				url : 'judge',
				error : function() {
					alert('smx失败 ');
				},
				success : function(data) {
					if (data.code == 100) {
						window.location.href = "correct";
					} else {
						alert(data.msg); 
					}
				}
			});
		});
		
		$("#registerdiv a").click(function() {
			if($('#acc').val()==null || $('#acc').val()=='' || $('#accpassw').val()==null ||$('#accpassw').val()=='')
			{
				alert("注册账号或密码不能为空");
			}else{
				var isture = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,12}$/;
				if(!isture.test($('#acc').val())){
					alert("注册账号只能是字母和数字组成，且长度为3-12位");
				}else{
				var info = {}; 
				info.userName = $('#acc').val();
				info.password = $('#accpassw').val();		
				$.ajax({
					async : true,
					cache : false,
					data : JSON.stringify(info),
					contentType : "application/json;charset=UTF-8",
					dataType : "json",
					type : 'post',
					url : 'accregister',
					error : function() {
						alert('smx失败 ');
					},
					success : function(data) {
						alert(data.msg);
					}
				});
				}
			}
		});
	})