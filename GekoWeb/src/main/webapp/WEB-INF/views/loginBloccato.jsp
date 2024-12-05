<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Ge.ko. autenticazione</title>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0"/>
<!-- Bootstrap -->
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap.min.css"  htmlEscape="true" /> rel="stylesheet"/>
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap-theme.min.css"  htmlEscape="true" /> rel="stylesheet"/>
	
	
	<style>
		#wrapCont {
		padding-top: 25px;
		padding-bottom: 25px;
		padding-right: 50px;
		padding-left: 250px;
		
		}
		
		#wrapCol {
		padding-top: 25px;
		padding-bottom: 25px;
		padding-right: 50px;
		border-style: solid;
		border-color: lightgray;
		background: lightblue;
		}
		
		#wrapLogo {
		padding-top: 25px;
		padding-bottom: 25px;
		padding-right: 50px;
		border-style: solid;
		border-color: lightgray;
		background: lightblue;
		}
		
		
	</style>
</head>

<body >
	<div class="container" id="wrapCont">
	<c:url value="/login" var="loginUrl"/>
	<form action="${loginUrl}" method="post"> 
	<div class="row" >
	<div class="col-lg-8"  id="wrapCol" >
		<div class="panel panel-success" >
			<div class="panel-heading">
				<h3 class="panel-title">MENU LOGIN</h3>
			</div>
			<div class="panel-body">
				<div class="row "> <!-- row1  -->
					<div class="col-lg-6" >
						<span class="label label-success">GEKO release: 21.3.7 del 27/03/2018 by R. Cirrito</span>
						<span class="label label-warning"><a href="http://172.16.2.100">Portale Segreteria Generale</a></span>
						<span class="label label-info"><a href="http://172.28.109.93:9010/login">ORGANIKO (per ADMINISTRATOR)</a></span>
					</div>
				</div> <!-- row1  -->
				
				<!-- Logo -->
				<div class="row">
					<div class="col-lg-6" >
					<img  src="<spring:url value="/resources/images/gecoLeopardino.jpg" htmlEscape="true"/>" class="img-polaroid">
					</div>
				</div>
		
				<!-- Logo -->
				<div class="row">
					<div class="col-lg-6" >
					<h3>Sorpresa di Pasqua! Stiamo migrando verso i nuovi server di Sicilia @ Digitale</h3>
					<h3>Se tutto va bene il servizio sarà disponibile dopo Pasqua! Auguri a tutti voi!</h3>
					</div>
				</div>
				
				
			</div> <!-- panel body -->
	</div> <!-- panel success -->
	</div>
	</div>
	</form>
	
	</div> <!-- container -->
	<script src="<spring:url value="/resources/bootstrap336/js/bootstrap.js" htmlEscape="true" />"></script>
</body>

</html>





