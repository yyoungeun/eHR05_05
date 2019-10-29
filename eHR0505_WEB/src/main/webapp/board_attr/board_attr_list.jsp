<%@page import="kr.co.ehr.user.service.Search"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.ehr.cmn.StringUtil"%>
<%@page import="kr.co.ehr.code.service.Code"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="context" value="${pageContext.request.contextPath }" />
<%

	//검색조건
	String searchDiv = "";
	String searchWord = "";
	String pageNum = "1";
	String pageSize = "10";
	
	//엑셀다운 확장자
	String ext		 ="xlxs";
	
	//검색 조건 분기
	Search search = (request.getAttribute("vo")==null)?null:(Search)request.getAttribute("vo");
	if(null != search){
		searchDiv  =  StringUtil.nvl(search.getSearchDiv(),"");
		searchWord =  StringUtil.nvl(search.getSearchWord(),"");
		pageNum    =  StringUtil.nvl(search.getPageNum()+"","1");
		pageSize   =  StringUtil.nvl(search.getPageSize()+"","10");
	}else{
		searchDiv  = ""; 
		searchWord = ""; 
		pageNum   = "1"; 
		pageSize = "10"; 		                
	}
	//페이지 사이즈
	List<Code> listPageSize =(request.getAttribute("listPageSize")==null)? (List<Code>)new ArrayList<Code>():(List<Code>)request.getAttribute("listPageSize");

	//검색 조건
	List<Code> listBoardSearch =(request.getAttribute("listBoardSearch")==null)? (List<Code>)new ArrayList<Code>():(List<Code>)request.getAttribute("listBoardSearch");

	//엑셀타입
	List<Code> listExcelType =(request.getAttribute("listExcelType")==null)? (List<Code>)new ArrayList<Code>():(List<Code>)request.getAttribute("listExcelType");
	
	//paging, url, scriptName
	/*
	 * @param url:호출url
	 * @param scriptName:호출    javascript
	*/
	int  maxNum = 0;//총 글수
	int  currPageNo = 1;// 현재페이지
	int rowPerPage = 10; //한 페이지에 보여질 글 수
	int bottomCount = 10; //바닥에 보여질 페이지 수
	
	//호출url
	String url = request.getContextPath() + "/board_attr/get_retrieve.do";
	String scriptName = "search_page";
	
	String tmpMaxNum = (request.getAttribute("totalCnt") == null) ? "0" : request.getAttribute("totalCnt").toString();
	
	maxNum = Integer.valueOf(tmpMaxNum);
	currPageNo = Integer.valueOf(pageNum);
	rowPerPage = Integer.valueOf(pageSize);
	
%>
<%--
  /**
  * @Class Name : board_attr_list.jsp
  * @Description : board_attr list
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2019.10.08            최초 생성
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
    <title>첨부게시판</title>

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
       <h1>첨부게시판</h1>
      </div>
      <!--// div title -->
       
      <!-- 검색영역 -->
      <div class="row">
       <div class="col-md-12 text-right">
       <form class="form-inline" method="get" name="boardFrm" id="boardFrm">
       <input type="hidden"  name="pageNum"  id="pageNum" value="${vo.pageNum}" >
		<input type="hidden"  name="boardId"  id="boardId" />
        <div class="form-group">
        
        <!-- PageSize -->
        <%=StringUtil.makeSelectBox(listPageSize, "pageSize", pageSize, false) %>
        <!-- 검색 조건 -->
        <%=StringUtil.makeSelectBox(listBoardSearch, "searchDiv", searchDiv, true) %>
     <input type="text"  class="form-control input-sm" id="searchWord" name="searchWord" placeholder="검색어" value="${vo.searchWord}" />
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <button type="button" class="btn btn-default btn-sm" id="doRetrieve">조회</button>
     <!-- 엑셀구분 -->
        <%=StringUtil.makeSelectBox(listExcelType, "ext", ext, false) %>
     <input type="button" class="btn btn-default btn-sm" id="doExcel" value="엑셀다운" />
        </div>
      </form>
     </div> 
    </div>
      <!--// 검색영역 -->  
      <%-- ${list } --%>
      <!-- Grid영역 -->
      <div class="table-responsive">
       <table class="table  table-striped table-bordered table-hover" id="listTable">
        <thead class="bg-primary">
         <th class="text-center col-md-1 col-xs-1" style="display:none">BOARD_ATTR_ID</th>
         <th class="text-center col-md-1 col-xs-1">번호</th>
         <th class="text-center col-md-7 col-xs-7 ">제목</th>
         <th class="text-center col-md-1 col-xs-1 hidden-xs">조회수</th>
         <th class="text-center col-md-2 col-xs-2 hidden-xs">글쓴이</th>
         <th class="text-center col-md-1 col-xs-1">등록일</th>
        </thead>
        
        <tbody>
        	<c:choose>
        		<c:when test="${list.size()>0}">
        			<c:forEach var="vo" items="${list}">
				         <tr>
				          <td class="text-center" style="display:none"><c:out value="${vo.boardId}"/></td>
				          <td class="text-center"><c:out value="${vo.num}"/></td>
						  <td class="text-left">
							<c:out value="${vo.title}"/>
							&nbsp;<c:if test="${not empty vo.fileId and '0'!=vo.fileId}">
									<img alt="img" src="<c:url  value='/resources/image/file/file.gif'/>">
								  </c:if> 
						  </td>
				          <td class="text-right  hidden-xs"><c:out value="${vo.readCnt}"/></td>
				          <td class="text-center hidden-xs"><c:out value="${vo.regId}"/></td>
				           <td class="text-center"><c:out value="${vo.regDt}"/></td>
				         </tr>
			        </c:forEach> 
		         </c:when>
		         <c:otherwise>
		         	<tr>
		         		<td class="text-center" colspan="99"><spring:message code="message.msg.no_data"/></td>
		         	</tr>
		         </c:otherwise>
		    </c:choose>
        </tbody>
       </table>
      </div>
      <!--// Grid영역 -->
      
      <!-- pagenation -->
      <div class="text-center">
      	<%=StringUtil.renderPaing(maxNum, currPageNo, rowPerPage, bottomCount, url, scriptName) %>
      <!--// pagenation -->
   </div>
    <!--// div container -->
        <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
     <script src="${context}/resources/js/jquery-1.12.4.js"></script>
    <!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
      <script src="${context}/resources/js/bootstrap.min.js"></script>
          <script type="text/javascript">
          
          $("#listTable>tbody").on("click","tr",function(){
        	 //alert("listTable>tbody");
        	 var trs = $(this);
        	 var tds = trs.children();
        	 if(null == tds || tds.length ==1)return;
        	 //console.log("tds.length:" + tds.length);
        	 
        	 var boardId = tds.eq(0).text();
        	 //console.log("boardId: " + boardId);
        	 
 	      	 var frm = document.boardFrm;
	  		 frm.boardId.value = boardId;
	  		 frm.action = "${context}/board_attr/do_selectOne.do";
	  		 frm.submit();
        	 
          });
          
          function search_page(url,pageNum){
        	  alert("search_page: "+ url + "||" + pageNum);
	      	  var frm = document.boardFrm;
	  		  frm.pageNum.value = pageNum;
	  		  frm.action = url;
	  		  frm.submit();
          }
          
  		//ExcelDown
  		$("#doExcel").on("click",function(){
  			//alert("doExcel");
  			//board_attr/do_exceldown.do
  			if(false == confirm('<spring:message code="message.msg.excel.down" />'))return;
  			doExcelDown();
  		});
  	
  		function doExcelDown(){
  			var frm = document.boardFrm;
  			frm.action = "${context}/board_attr/do_exceldown.do";
  			frm.submit();
  		}
  		
  		//검색어 Enter
  	    $("#searchWord").keydown(function(key){
  	    	//alert(key.keyCode);
  	    	if(key.keyCode == 13){
  	    		doRetrieve()
  	    	}
  	    });
  	
  	
  		function doRetrieve(){
  			var frm = document.boardFrm;
  			frm.pageNum.value = 1;
  			frm.action = "${context}/board_attr/get_retrieve.do";
  			frm.submit();
  		}
  	
  		$("#doRetrieve").on("click",function(){
  			doRetrieve();
  		});
  	
  		$(document).ready(function() {
  			//alert("ready");
  		});
    </script>
  </body>
</html>