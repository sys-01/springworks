<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	<style type="text/css">
		div {
			margin: 5px;
		}
		
		div.row {
			margin: 0 auto;
		}
		
		div#p_sub_list {
			height: 900px;
			overflow: auto;
		}
		
		#p_image {
			position: relative;
		}
		
		#p_image:hover {
			background-color: rgba(0, 0, 0, 0.3);
		}
	</style>
	<script>
		$(function() {
			$("#p_image").click(function() {
				if(confirm("이미지를 삭제할까요?")) {
					let p_code = $("#p_code").val()
					document.location.href = "${rootPath}/imgDelete?p_code=" + p_code
					
				}
			})
		})
	</script>
</head>
<body>
	<header class="container-fluid jumbotron text-center bg-success">
		<h3>상품정보 관리</h3>
		<p>대한쇼핑몰 상품정보 관리자 화면</p>
	</header>
	<nav class="container-fluid navbar navbar-expand-sm bg-dark navbar-dark">
		<ul class="nav navbar-nav">
		  <li class="nav-item"><a class="nav-link" href="#">Home</a></li>
		  <li class="nav-item"><a class="nav-link" href="#">상품등록</a></li>
		  <li class="nav-item"><a class="nav-link" href="#">재고관리</a></li>
		  <li class="nav-item"><a class="nav-link disabled" href="#">관리자</a></li>
		</ul>
	</nav>
	<section class="container-fluid">
		<article class="row d-flex justify-content-center">
			<section id="p_list" class="col-lg-4 col-md-5 col-sm-11">
				<%@ include file="/WEB-INF/views/p-list.jsp" %>
			</section>
			
			<section class="col-lg-4 col-md-5 col-sm-11">
				<form class="was-validated" action="${rootPath}/input" method="POST" enctype="multipart/form-data">
				
				  <div class="form-group">
				    <label for="p_code">상품코드</label>
				    <input name="p_code" id="p_code" type="text" class="form-control" placeholder="상품코드를 입력하세요">
				  </div>
				  
				  <div class="form-group">
				    <label for="p_name">상품이름</label>
				    <input name="p_name" id="p_name" type="text" class="form-control"placeholder="상품명을 입력하세요" required>
				    <div class="valid-feedback">Valid.</div>
				    <div class="invalid-feedback">상품명은 반드시 입력하세요</div>
				  </div>
				  
				  <div class="form-group">
				    <label for="p_iprice">매입가격</label>
				    <input name="p_iprice" id="p_iprice" type="text" class="form-control"placeholder="매입가격을 입력하세요" required>
				    <div class="valid-feedback">Valid.</div>
				    <div class="invalid-feedback">매입가격을 입력하세요</div>
				  </div>
				  
				  <div class="form-group form-check">
				    <label class="form-check-label">
				      <input class="form-check-input" type="checkbox" name="p_vat" value="1" >과세
				    </label>
				  </div>
				  
				  <div class="form-group">
				    <label for="p_oprice">매출가격</label>
				    <input name="p_oprice" id="p_oprice" type="text" class="form-control" placeholder="매출가격을 입력하세요" required>
				    <div class="valid-feedback">Valid.</div>
				    <div class="invalid-feedback">매출가격을 입력하세요</div>
				  </div>
				  
				  <div class="form-group">
				    <input type="hidden" name="p_file" id="p_file">
				    
				    <%/*
				    input type="file" : 파일을 업로드하기 위한 컨트롤 요소
				    multiple="multiple" : 여러개의 파일을 동시에 업로드 가능
				    
				    accept="" : 특정한 종류의 파일만 올리고 싶을때
				    accept="image/*" : 이미지 관련 파일들
				    accept="text/plain" : text 파일만(*.txt)
				    accept="text/html" : text 파일중 *.html만
				    accept="video/*" : 동영상 관련 파일
				    accept="application/vnd.ms-excel" : excel 파일만
				    accept=".xls|.xlsx" : excel 파일
				    */%>
				    <input type="file" name="u_files" id="u_files" class="form-controller" multiple="multiple" accept="image/*|.pdf|.jpg">
				  </div>
				  
				  <div class="form-group">
				  	<img src="${rootPath}/files/noimage.png" width="200px" height="200px" id="p_image">
				  </div>
				  
				  <button type="reset" class="btn btn-warning">리셋</button>
				  <button type="submit" class="btn btn-primary">저장</button>
				</form>
			</section>
			
			<section id="p_sub_list" class="col-lg-4 col-md-5 col-sm-11">보조정보</section>
			
		</article>
	</section>
	<footer class="">
		<address>CopyRight &copy; sianblone@gmail.com</address>
	</footer>
</body>
</html>