<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="context" value="${pageContext.request.contextPath }" />
        
<%--
  /**
  * @Class Name : login.jsp
  * @Description : login 화면
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2019.09.26            최초 생성
  *
  * author SIST 개발팀
  * since 2019.09.26
  *
  * Copyright (C) 2009 by KandJang  All right reserved.
  */
--%>

<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${context}/resources/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${context}/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${context}/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${context}/resources/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="${context}/resources/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${context}/resources/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${context}/resources/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="${context}/resources/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="${context}/resources/css/util.css">
	<link rel="stylesheet" type="text/css" href="${context}/resources/css/main.css">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>부트스트랩 HR_LIST 템플릿</title>

<!-- 부트스트랩 -->
<link href="${context}/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
<!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>
<body>
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-50">
				<form class="login100-form validate-form" name="loginForm" id="loginForm" 
				     action="${context}/login/do_login.do" method="post">

					<span class="login100-form-title p-b-33">
						Account Login
					</span>
				    <div class="form-group">
					    <select id="lang" name="lang" class="form-control input-sm">
					    	<option value="ko">한글</option>
					    	<option value="en">영어</option>
					    </select> 
				    </div>
					<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
						<input class="input100" type="text" name="u_id" id="u_id" placeholder="아이디">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>

					<div class="wrap-input100 rs1 validate-input" data-validate="Password is required">
						<input class="input100" type="password" name="passwd" id="passwd"   placeholder="비번">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>
                 </form>
					<div class="container-login100-form-btn m-t-20">
						<button class="login100-form-btn" id="signIn">
							Sign in
						</button>
					</div>

					<div class="text-center p-t-45 p-b-4">
						<span class="txt1">
							Forgot
						</span>

						<a href="#" class="txt2 hov1">
							Username / Password?
						</a>
					</div>

					<div class="text-center">
						<span class="txt1">
							Create an account?
						</span>

						<a href="#" class="txt2 hov1">
							Sign up
						</a>
					</div>
				
			</div>
		</div>
	</div>
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="${context}/resources/js/jquery-1.12.4.js"></script>
	<!-- jQuery validate -->
	<script src="${context}/resources/js/jquery.validate.js"></script>	
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="${context}/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function do_login(){
			var frm = document.loginForm;
			//validation
			frm.submit();
		}
	
	
		$("#signIn").on("click",function(){
			alert("signIn");
			if($("#loginForm").valid()==false)return;
			
			$.ajax({
				type : "POST",
				url : "${context}/login/do_login.do",
				dataType : "html",
				data : {
					"lang" : $("#lang").val(),
					"u_id" : $("#u_id").val(),
					"passwd" : $("#passwd").val()
				},
				success : function(data) {
					var jData = JSON.parse(data);
					if(null != jData){
						if (jData.msgId == "30") {
							location.href = "${context}/main/main.do";
	
						}else if (jData.msgId == "10") {
							$("#u_id").focus();
							alert(jData.msgId + "|" + jData.msgMsg);
	
						}else if ( jData.msgId == "20") {
							$("#passwd").focus();
							alert(jData.msgId + "|" + jData.msgMsg);
						}
					}
				},
				complete : function(data) {

				},
				error : function(xhr, status, error) {
					alert("error:" + error);
				}
			});
			//--ajax  			
			
			
		});
	
		//form validate
		$("#loginForm").validate({
			rules: {					
				u_id: {
					required: true,
					minlength: 2,
					maxlength: 20
				},
				passwd: {
					required: true,
					minlength: 2,
					maxlength: 100
				}
			},
			messages: {
				u_id: {
					required: "ID를 입력 하세요.",
					minlength: $.validator.format("{0}자 이상 입력 하세요."),
					maxlength: $.validator.format("{0}자 내로 입력 하세요.")
				},
				passwd: {
					required: "비번을 입력 하세요.",
					minlength: $.validator.format("{0}자 이상 입력 하세요."),
					maxlength: $.validator.format("{0}자 내로 입력 하세요.")
				}
			},
			errorPlacement : function(error, element) {
			     //do nothing
			    },
			    invalidHandler : function(form, validator) {
			     var errors = validator.numberOfInvalids();
			     if (errors) {
			      alert(validator.errorList[0].message);
			      validator.errorList[0].element.focus();
			     }
			}

		});			
	
	
		$(document).ready(function() {
			//alert("ready");
		});
	</script>
</body>
</html>