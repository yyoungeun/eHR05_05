<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>gangster eleven</title>


 <script language='javascript'>
 function change(num)
 {
 var x  = document.cartFrm;
 var y = Number(x.cartCnt.value) + num;
 if(y < 1) y = 1;
 x.cartCnt.value = y;
 }
 </script>


</head>
<body>
 <form name='cartFrm'>
  <table>
   <tr>
    <td>갯수</td>
    <td>
     <table>
      <tr>
       <td><input type='text' name="cartCnt" value='1' size='3' readonly><td>
       <td>
        <a href='#' onclick='javascript_:change(1);'>▲</a><br>
        <a href='#' onclick='javascript_:change(-1);'>▼</a>
        <!--버튼을 이미지로 바꾸세요 <img src='이미지경로' onclick='javascript_:change(1);'>-->
       </td>
      </tr>
     </table>

    </td>
   </tr>
  </table>
 </form>
</body>
</html>