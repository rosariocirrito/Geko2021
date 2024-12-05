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
	<title>Geko - Menu Responsabile POP</title>	
	    
	<link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
	<style>
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
	</style>
</head>
    
    
<body>

<script src="resources/js/jquery-1.8.2.js"></script>
<script src="resources/bootstrap/js/bootstrap.js"></script>
<security:authentication property="name" var="nomeUtente"/>
<security:authentication property="authorities" var="ruoli" />

<security:authorize access="hasRole('RESP_POP')">

<div class="container" >
<div class="row">
<div class="col-lg-12 col">

<p>Utente: <span class="label label-success">${nomeUtente} </span> </p>
<p>Persona: <span class="label label-success">${persona.stringa}</span> </p>
<p>Struttura: <span class="label label-warning">${struttura.denominazione} </span></p> 
<p>Dipartimento: <span class="label label-warning">${dipartimento.denominazione} </span></p> 

<ul>
<c:forEach items="${ruoli}" var="ruolo">
     <li><a href="${ruolo}">${ruolo}</a> </li>
</c:forEach>
</ul>

<hr />
<form:form role="form" modelAttribute="menu" method="post">

<fieldset>
<legend>Parametri della Sessione RESPONSABILE POP</legend> 
	
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="anno" path="anno">Anno</form:label>
    <form:input class="form-control" type="text" path="anno"/>
    <form:errors path="anno" cssClass="error"/>  
    </div>
	
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoScelta" path="idIncaricoScelta">Incarico POP</form:label>
	<form:select path="idIncaricoScelta" class="form-control">
		<form:options items="${menu.incarichi}" itemValue="idIncarico" itemLabel="stringa"/>    
	</form:select>
	</div>
	
		
	
</fieldset>

<div class="control-group col-sm-2">
	<div class="controls">
		<button type="submit" class="btn-large  btn-primary" name="update">Aggiorna</button>
		<button type="submit" class="btn-large btn-warning" name="logout">Logout</button>
	</div>
</div>
</form:form>

</div>



<div class="navbar navbar-default" >
	<div class="container-fluid" >
	
		<ul class= "nav nav-tabs">	
		
		<li class= "dropdown">
         <a class="dropdown-toggle" data-toggle="dropdown" href ="#" >Programmazione<b class="caret"></b></a>
		<ul class="dropdown-menu">
			
			<li> <a href="respPop/modifyProgrammazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
			Gestisci progr. pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
			         
            <li><a href="respPop/listProgrammazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
        	Visualizza progr. pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
                        
		 </ul>
		</li>
		<li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Rendicontazione<b class="caret"></b></a>
		<ul class= "dropdown-menu">	
			<li> <a href="respPop/modifyRendicontazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
			Aggiorna rendicontazione pop: ${menu.incarico.stringa} / ${menu.anno}</a> </li>
			
			<li> <a href="respPop/listRendicontazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
			Visualizza rendicontazione pop: ${menu.incarico.stringa} / ${menu.anno}</a></li>
		 </ul>
	</li>

</ul>
</div>
</div>
</div>
</div>

	
</security:authorize> 


</body>
    
</html>
