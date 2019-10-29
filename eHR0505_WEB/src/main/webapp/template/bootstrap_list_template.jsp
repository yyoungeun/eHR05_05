<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath }" />
<%--
  /**
  * @Class Name : list_template.jsp
  * @Description : bootstrap list
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
    <!-- div container -->
   <div class="container">
        <!-- div title --> 
     <div class="page-header">
       <h1>부트스트랩 HR_LIST 템플릿</h1>
      </div>
      <!--// div title -->
       
      <!-- 검색영역 -->
      <div class="row">
       <div class="col-md-12 text-right">
       <form class="form-inline" >
        <div class="form-group">
     <select class="form-control input-sm">
       <option>1</option>
       <option>2</option>
       <option>3</option>
       <option>4</option>
       <option>5</option>
     </select>
     <select class="form-control input-sm">
       <option>ID</option>
       <option>이름</option>
     </select>
     <input type="text"  class="form-control input-sm" id="searchWord" name="searchWord" 
       placeholder="검색어" />
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <button type="button" class="btn btn-default btn-sm" id="doRetrieve">조회</button>     
     <input type="button" class="btn btn-default btn-sm" id="doExcel" value="엑셀다운" />
        </div>
       </form>
        </div> 
      </div>
      <!--// 검색영역 -->  
      
      <!-- Grid영역 -->
      <div class="table-responsive">
       <table class="table  table-striped table-bordered table-hover">
        <thead class="bg-primary">
         <th class="text-center col-md-1 col-xs-1">번호</th>
         <th class="text-center col-md-9 col-xs-9 ">제목</th>
         <th class="text-center col-md-1 col-xs-1">글쓴이</th>
         <th class="text-center col-md-1 col-xs-1">조회수</th>
        </thead>
        
        <tbody>
         <tr>
          <td class="text-center">1</td>
          <td class="text-left">제목: 5월 휴가</td>
          <td class="text-center">이상무</td>
          <td class="text-right">1</td>
         </tr>
         <tr>
          <td class="text-center">2</td>
          <td class="text-left">제목: 5월 휴가</td>
          <td class="text-center">이상무</td>
          <td class="text-right">1</td>
         </tr>
         <tr>
          <td class="text-center">3</td>
          <td class="text-left">제목: 5월 휴가</td>
          <td class="text-center">이상무</td>
          <td class="text-right">1</td>
         </tr>
         <tr>
          <td class="text-center">4</td>
          <td class="text-left">제목: 5월 휴가</td>
          <td class="text-center">이상무</td>
          <td class="text-right">1</td>
         </tr>
         <tr>
          <td class="text-center">5</td>
          <td class="text-left">제목: 5월 휴가</td>
          <td class="text-center">이상무</td>
          <td class="text-right">1</td>
         </tr>                           
        </tbody>
       </table>
      </div>
      <!--// Grid영역 -->
      
      <!-- pagenation -->
      <div class="text-center">
       <ul class="pagination">
        <li><a href="#">&lt;</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">&gt;</a></li>
       </ul>
      </div>
      <!--// pagenation -->
      
   </div>
    <!--// div container -->
        <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
     <script src="${context}/resources/js/jquery-1.12.4.js"></script>
    <!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
      <script src="${context}/resources/js/bootstrap.min.js"></script>
          <script type="text/javascript">
    	$(document).ready(function(){
    		//alert("ready");
    		
    	})
    </script>
  </body>
</html>