<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath }" />
<%--
  /**
  * @Class Name : board_attr_reg.jsp
  * @Description : Sample Register 화면
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2018.04.26            최초 생성
  *
  * author SIST 개발팀
  * since 2018.04.26
  *
  * Copyright (C) 2009 by KandJang  All right reserved.
  */
--%>

<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>첨부게시판 등록</title>

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
	<!-- div container -->
	<div class="container">
		<!-- div title -->
		<div class="page-header">
			<h1>첨부게시판 등록</h1>
		</div>
		<!--// div title -->

		<!-- div title -->
		<form class="form-horizontal">
		<input type="hidden"  name="fileId" id="fileId" >
			<!-- 제목 -->
			<div class="form-group">
				<label for="title" class="hidden-xs hidden-sm col-md-2 col-lg-2 control-label">제목</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<input type="text" class="form-control" id="title" name="title" placeholder="제목">
				</div>
			</div>
			<!-- 첨부 -->
			<div class="form-group">
				<label for="attrFile" class="hidden-xs hidden-sm col-md-2 col-lg-2 control-label">파일첨부</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<button id="attrFile" type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#layerpop">파일</button>
				</div>
			</div>
			<!-- 조회수 -->
			<div class="form-group">
				<label for="title" class="hidden-xs hidden-sm col-md-2 col-lg-2 control-label">조회수</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<input type="text" class="form-control" id="readCnt" name="readCnt" disabled="disabled" placeholder="조회수">
				</div>
			</div>
			<!-- 내용 -->
			<div class="form-group">
				<label for="contents" class="col-sm-2 control-label">내용</label>
				<div class="col-sm-8">
					<textarea class="form-control" id="contents" name="contents" rows="5" placeholder="내용"></textarea>
				</div>
			</div>
			
			<!-- 등록자 -->
			<div class="form-group">
				<label for="regId" class="hidden-xs hidden-sm col-md-2 col-lg-2 control-label">등록자</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<input type="text" class="form-control" id="regId" name="regId" disabled="disabled" placeholder="등록자">
				</div>
			</div>
				
			<!-- 등록일 -->
			<div class="form-group">
				<label for="regDt" class="hidden-xs hidden-sm col-md-2 col-lg-2 control-label">등록일</label>
				<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<input type="text" class="form-control" id="regDt" name="regDt" disabled="disabled" placeholder="등록일">
				</div>
			</div>		
			<!-- 첨부 그리드 -->	
					<div class="form-group">
				<label for="regDt" class="hidden-xs hidden-sm col-md-2 col-lg-2 control-label"></label>
				<div class="table-responsive col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<table class="table  table-striped table-bordered table-hover" id="listFileTable">
						<tbody></tbody>
					
					</table>
				</div>
				</div>
		</form>

		<!-- Modal -->
		<div class="modal fade" id="layerpop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">File Up</h4>
		      </div>
		      <div class="modal-body">
		        <form class="form-horizontal" action="${context}/file/do_save.do" name="saveFileForm" id="saveFileForm" method="post" enctype="multipart/form-data">
		        	<input type="hidden" name="work_dir" id="work_dir" value="com">
		         	<input type="hidden" name="attrFileId" id="attrFileId">
		          	<input type="hidden" name="orgFileNm" id="orgFileNm">
		           	<input type="hidden" name="saveFileNm" id="saveFileNm">
		        
		        <!--첨부파일 -->
		        <div class="custom-file">
		        	<input type="file" class="custom-file-input" id="file01" name="file01">
		        </div>
		        </form>
		      </div>
		      <div class="modal-footer">
		     	<button type="button" class="btn btn-default" data-dismiss="modal" id="doFileUpload">저장</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
		      </div>
		    </div>
		  </div>
		 </div>
		
		
		
	</div>
	<!--// div container -->
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script src="${context}/resources/js/jquery-1.12.4.js"></script>
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	<script src="${context}/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
		//파일목록 조회:
		function getFileList(fileId){
			
			//ajax
            $.ajax({
               type:"POST",
               url:"${context}/file/do_retrieve.do",
               dataType:"html",
               data:{
               "fileId":fileId
              }, 
            success: function(data){
            	alert(data);
              var jData = JSON.parse(data);
              
              if(null != jData){
            	 
            	  
            	   //기존: listFileTable 삭제.
            	  $("#listFileTable tbody tr").remove();
              
            		//전체 data를 동적으로 생성.
            	  $.each(jData, function(index,item){
            		  $("#listFileTable tbody:last").append("<tr>"+
            				  "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>"+<c:out value='item.num'/>+"</td>"+

            				  "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>"+<c:out value='item.num'/>+"</td>"+

            				  "<td class='text-left org-file-name'>"+<c:out value='item.orgFileNm'/>+"</td>"+

            				  "<td class='text-center hidden-xs hidden-sm hidden-md hidden-lg'>"+<c:out value='item.saveFileNm'/>+"</td>"+

            				  "<td class='text-right org-file-name'>"+<c:out value='item.fSize'/>+"&nbsp; byte</td>"+
            				  
            				  "<td class='text-right'><button type='button' class='btn btn-default btn-sm btn-danger'>X</button></td>"+
            				  "<tr>");
            		  
            	  });//$.each
            	  
              }else{
                alert(jData);
              }
            },
            complete:function(data){
             
            },
            error:function(xhr,status,error){
                alert("error:"+error);
            }
           }); 
           //--ajax  
		}
	
		$("#doFileUpload").on("click",function(e){
			//console.log("doFileUpload>>");
			if(confirm("등록 하시겠습니까? ")== false) return;
			e.preventDefault();
			
			doFileUpload();
			
		});
		
		function doFileUpload(){
			var form = $('form')[1];
			var formData = new FormData(form);
			
			
			//ajax
            $.ajax({
               type:"POST",
               url:"${context }/file/do_save.do",
               contentType:false,
               async:false,
               cache:false,
               processData:false,
               encType:"multipart/form-data",
               data:formData,
               
            success: function(data){
				  console.log("data.msgId:"+data.msgId)
				  console.log("data:"+data.msgMsg)
				  
				  $("#attrFileId").val(data.msgMsg);
				  $("#fileId").val(data.msgMsg);
				  
				  document.getElementById('file01').value="";
				  
	              //var jData = JSON.parse(data);
	              if(null != data && data.msgId=="1"){
	                //alert(data.msgMsg);
	                
	                //fileId file_mng조회
	               	getFileList( $("#fileId").val());
	              }else{
	                alert(data.msgId+"|"+data.msgMsg);
	              }
	            },
	            complete:function(data){
	             
	            },
	            error:function(xhr,status,error){
	                alert("error:"+error);
	            }
	           }); 
	           //--ajax  
	           
		}
	
		$(document).ready(function() {
			//alert("ready");
		});
	</script>
</body>
</html>