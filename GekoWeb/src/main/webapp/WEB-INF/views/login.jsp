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
	<div class="col-sm-12"  id="wrapCol" >
		<div class="panel panel-success" >
			<div class="panel-heading">
				<h3 class="panel-title">MENU LOGIN</h3>
			</div>
			<div class="panel-body">
				<div class="row "> <!-- row1  -->
					<div class="col-lg-12" >
						<span class="label label-success">GEKO 2021 rel. 1.1.11 del 09/07/2021 by R. Cirrito</span></br>
						<span class="label label-info"><a href="http://172.28.109.94:8080/login">GE.KO. 2018</a></span></br>
						<span class="label label-warning"><a href="http://172.28.109.98">Portale Segreteria Generale</a></span></br>
						<span class="label label-info"><a href="http://172.28.109.93:9010/login">ORGANIKO (per ADMINISTRATOR)</a></span></br>
						
						<span class="label label-success"><a href="http://pti.regione.sicilia.it/portal/page/portal/PIR_PORTALE/PIR_IlPresidente/Direttive_Presidenziali">
						Direttive Presidenziali</a></span>
					</div>
				</div> <!-- row1  -->
				
				<!-- Logo -->
				<div class="row">
					<div class="col-lg-12" >
					<img  src="<spring:url value="/resources/images/geco2021.jpg" htmlEscape="true"/>" class="img-polaroid">
					</div>					
				</div>
		
				<!-- Segnalazioni -->
				<div class="row">
					<div class="col-lg-12" >
					<c:if test="${param.error != null}"> 
						<p>Credenziali di autenticazione errate. Riprovare!</p>
					</c:if>
					<c:if test="${param.logout != null}">
						<p>Sessione scaduta. Rieffettuare l'autenticazione!</p>
					</c:if>
					<p></p>
					</div>
				</div>
				
				<fieldset>
					<legend>Credenziali Autenticazione</legend>
					<!-- username -->
					<div class="row">
					<div class="form-group">
						<label for="username"class="col-lg-2 controllabel">UserName</label>
						<div class="col-lg-4">
						<input type="input" id="username" name="username"/> 
						</div>
					</div>
					</div>
					
					<div class="row">
					<div class="form-group">
					<label for="password" class="col-lg-2 controllabel">Password</label>
					<div class="col-lg-4">
					<input type="password" id="password" name="password"/> 
					</div>
					</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}"/>
					
					<!--  <button type="submit" class="btn">Log in</button>-->
					<div class="row">
					<div class="col-lg-6">
					<hr/>
					</div>
					</div>
					<div class="row">
						<div class="col-sm-offset-2 col-lg-4">
						<button class="btn btn-primary btn-lg" type="submit">Autentica</button>
						</div> <!--  -->
					</div> <!-- row  -->
				</fieldset>
			</div> <!-- panel body -->
	</div> <!-- panel success -->
	</div>
	</div>
	</form>
	
	</div> <!-- container -->
	<script src="<spring:url value="/resources/bootstrap336/js/bootstrap.js" htmlEscape="true" />"></script>
</body>

</html>





