<%--
    Document   : index
    Created on : 2011-12-28, 20:31:47
    Author     : zgww
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="jquery/jquery-1.6.2.js"></script>
		<title>${i18n.error500_title}</title>
		<style>
			html, body {
				padding : 0px;
				margin  : 0px;
				text-align: center;
			}
			input {
				width:180px;
				height:24px;
				line-height: 24px;
				margin: 3px;
			}
			.login {
				background:url(img/login/login.png) no-repeat 0px 0px;
				color:#e2ffde;
				font-size: 24px;
				margin-top:10px;
				width:99px;
				height: 44px;
				display: inline-block;
				text-align: center;
				line-height: 40px;
				vertical-align: middle;
				cursor: pointer;
			}
			.login:hover {
				background-image: url(img/login/loginHover.png);
			}
			.toast {
				border-radius : 5px;
				border : 3px solid rgba(190, 111, 119, 0.8);
				background-color:rgba(144, 63, 69, 0.8);
				color: #f0d3d8;
				padding:10px;
				position:fixed;
				left: 40%;
				top:300px;
				display: none;
			}
		</style>
		<script>
			window.onload = function () {
				var login = document.querySelector(".login");

				login.onmousedown = function (e) {
					e = e || event;
					this.style.backgroundImage = "url(img/login/loginDown.png);";
				}
				login.onmouseout  = restoreBgImg;
				login.onmouseup   = restoreBgImg;
				login.onselectstart = function () {return false;}
				login.onclick = function () {
					document.querySelector("form").submit();
				}
			${empty msg} || toast('${msg}');
					}
					function restoreBgImg() {
						this.style.backgroundImage = "";
					}
					function toast(msg) {
						$(".toast").html(msg);
						$(".toast").css({opacity : 0, display : "block"}).animate({opacity : 1}, 100).delay(1000)
						.animate({opacity : 0}, {duration : 400, complete : function () {$(".toast").css("display", "block");}});
					}
		</script>
	</head>
	<body style="padding:100px;">
		<div style="width:900px;margin: 0px auto;">
			<div style="padding:10px;padding-right:50px;padding-top:50px;margin-right:0px;float:left; height: 100px;background: url(img/login/loginLine.png) no-repeat 250px 0px;">

				<div style="text-align:right;height:70px">

					<img src="jsp/login/img/logo.png" style="float:right;"/>
				</div>

			</div>

			<div style="text-align: left;padding-top:50px;">
				<div>
					${i18n.error500_server_error}
					<br />
					${i18n.error500_error_msg}：${empty errorMsg ? i18n.error500_error_em_suffix_page  : errorMsg}

                    <br/>
                    <a href="javascript:history.back();">${i18n.error500_back}</a>
                    <br/>
                    ${i18n.error500_or_you_can}<a href="jsp/permission/logout.em">${i18n.error500_logout}</a>
				</div>
			</div>

		</div>
		<div class=toast>
		</div>
	</body>
</html>
