<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
  	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0"/>
	<title>Geko - Menu scelta ruolo</title>
	<!-- Bootstrap 
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap.min.css"  htmlEscape="true" /> rel="stylesheet"/>
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap-theme.min.css"  htmlEscape="true" /> rel="stylesheet"/>
	-->
	<link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
	
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
		
		.row{
			margin-top:16px;
			margin-left:16px;
			background-color:lightgray;
		}
		.col{
			background-color:lightblue;
			padding:8px;
			border:2px solid darkgray;
		}
		}
		
	</style>
</head>
    
<body>
<security:authentication property="name" var="nomeUtente"/>
<security:authentication property="authorities" var="ruoli" />

<div class="container" id="wrapCont">
<div class="row" >
<div class="col-lg-12"  id="wrapCol" >

<div class="panel panel-success">
<div class="panel-heading">
<h3 class="panel-title">MENU SCELTA RUOLO</h3>
</div>
<div class="panel-body">

<h4>Utente: ${nomeUtente} </h4> 
<p>Persona: <span class="label label-success">${persona.cognome} ${persona.nome}</span> </p>
<p>Struttura: <span class="label label-warning">${struttura} </span></p> 
<p>Dipartimento: <span class="label label-warning">${dipartimento} </span></p> 
<p>Assessorato: <span class="label label-warning">${assessorato} </span></p> 
<hr />


<h4>Selezionare un ruolo dal menu sottostante </h4> 
<ul>
<c:forEach items="${ruoli}" var="ruolo">
     <li><a href="${ruolo}">${ruolo}</a> </li>
</c:forEach>
</ul>          
<hr />
<form:form role="form" modelAttribute="menu" method="post">
<fieldset>
<legend>SCELTA ANNO</legend> 
	
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="anno" path="anno">Anno</form:label>
    <form:input class="form-control" type="text" path="anno"/>
    <form:errors path="anno" cssClass="error"/>  
    </div>
	
</fieldset>
<div class="control-group col-sm-2">
	<div class="controls">
		<button type="submit" class="btn-large  btn-primary" name="update">Aggiorna</button>
		<button type="submit" class="btn-large btn-warning" name="logout">Logout</button>
	</div>
</div>
</form:form>

<!-- cambio pwd -->

<span class="label label-warning"><c:url value="userPwd/changePwd" var="chgpwdUrl" /><a href="${chgpwdUrl}">Cambio Password</a></span> 



</div>            
</div>
</div>
</div>
</div>
</body>
</html>
