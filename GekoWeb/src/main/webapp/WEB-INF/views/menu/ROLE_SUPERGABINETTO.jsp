<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="ISO-8859-1" />
    <title>Menu SuperGabinetto</title>
    <link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet"/>
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

<security:authorize access="hasRole('ROLE_SUPERGABINETTO')">

<div class="container" >
<div class="row">
<div class="col-lg-8 col">

<p>Utente: <span class="label label-success">${nomeUtente} </span> 
&nbsp Persona: <span class="label label-success">${persona.stringa}</span> </p>
<p>Struttura: <span class="label label-warning">${struttura.denominazione} </span></p> 


<ul>
<c:forEach items="${ruoli}" var="ruolo">
     <li><a href="${ruolo}">${ruolo}</a> </li>
</c:forEach>
</ul>
<hr />

<form:form modelAttribute="menu" method="post">

<fieldset>
<legend>Parametri della Sessione SUPER_GABINETTO </legend>

<form:label class="control-label" for="anno" path="anno">Anno</form:label>
    <form:input class="input-medium" type="text" path="anno"/>
    <form:errors path="anno" cssClass="error"/>  
    
    
<form:label class="control-label" for="idAssessoratoScelta" path="idAssessoratoScelta">Assessorato</form:label>
<form:select path="idAssessoratoScelta" >
	<form:options items="${menu.assessorati}" itemValue="idPersona" itemLabel="denominazione"/>    
</form:select> 



<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoGekoApicaleAmm_neScelta" path="idIncaricoGekoApicaleAmm_neScelta">Incarico Apicale (premere Aggiorna se vuoto)</form:label>
	<form:select path="idIncaricoGekoApicaleAmm_neScelta" class="form-control">
		<form:options items="${menu.incarichiApicaliAmm_ne}" itemValue="idIncarico" itemLabel="stringa"/>    
	</form:select>
</div>
	
<div class="control-group col-sm-2">
	<div class="controls">
		<button type="submit" class="btn-large  btn-primary" name="update">Aggiorna</button>
		<button type="submit" class="btn-large btn-warning" name="logout">Logout</button>
	</div>
</div>
</fieldset>	
</form:form>
	
   
<div class="navbar navbar-default" >
	<div class="container-fluid" >
	<ul class= "nav nav-tabs">	
		
	<li class= "dropdown">
	<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Pianificazione<b class="caret"></b></a>
	<ul class= "dropdown-menu">	
		<li> 
         <a href="pianifHtml/listDirettivaIndirizzo/${menu.anno}">
         Visualizza la Direttiva di Indirizzo - ${menu.anno}</a>
     	</li>
     	<li> 
        <a href="superGabPianificazione/modifyDirettivaSuperGab/${menu.anno}">
         Modifica la Direttiva di Indirizzo - ${menu.anno}</a>
     	</li>
     	<li class="divider"></li>
     	<li> 
        <a href="superGabPianificazione/clonaDirettivaIndirizzoSuperGab/${menu.anno}">
         Clona la Direttiva di Indirizzo sul ${menu.anno +1}</a>
     	</li>
     	<li class="divider"></li>
     	<li> 
         <a href="pianifHtml/listDirettivaAzAmmin/${menu.anno}">
         Visualizza la Direttiva Azione ammi.va - ${menu.anno}</a>
     	</li>
     	<!-- 
		<li> 
         <a href="superGabPianificazione/listPianificazioneTriennaleSuperGab/${menu.anno}">
         Lista Pianificazione Triennale da: ${menu.anno}</a>
     	</li>
     	<li> 
         <a href="superGabPianificazione/modifyPianificazioneTriennaleSuperGab/${menu.anno}">
         Modifica Pianificazione Triennale da: ${menu.anno}</a>
     	</li>
		 -->
	</ul>
	</li>
	
		
	<li class= "dropdown">
	<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Programmazione<b class="caret"></b></a>
	<ul class= "dropdown-menu">	
		<li> 
         <a href="superGabProg/listPianificazioneApicaleIncaricoSuperGab/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	     Lista Pianificazione Apicale Incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
     	</li>
     	
     	<li class="divider"></li> 
		<li><a href="qryProgrXls/xlsProgrammazioneApicaleIncarico/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
		Genera Excel programmazione apicale: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
		</li> 
	</ul>
	</li>
	
	<li class= "dropdown">
	<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Rendicontazione<b class="caret"></b></a>
	<ul class= "dropdown-menu">	
		
         <li> 
         <a href="superGabRend/listRendicontazioneIncaricoApicaleSuperGab/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	     Lista Rendicontazione Apicale Incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
     	</li>
     	
     	<li class="divider"></li>	     
			    
			  <li><a href="superGabRend/scadenze/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
			    Visualizza scadenzario Incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
	</ul>
	</li>
	
	
	
	
</ul>  
</div>
</div>
	
</security:authorize> 

</body>
</html>