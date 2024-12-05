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
    <title>Menu SuperController</title>
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

<security:authorize access="hasRole('ROLE_SUPERCONTROLLER')">  
    

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
<legend>Parametri della Sessione SUPERCONTROLLER </legend>

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

<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoDeptScelta" path="idIncaricoDeptScelta">Incarico Dirigenziale</form:label>
	<form:select path="idIncaricoDeptScelta" class="form-control">
		<form:options items="${menu.incarichiDept}" itemValue="idIncarico" itemLabel="stringa"/>    
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
         <a class="dropdown-toggle" data-toggle="dropdown" href ="#" >Programmazione<b class="caret"></b></a>
		<ul class="dropdown-menu">
 
	    <li> <a href="controller/sintesiPianificazioneDipartimentoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	    Sintetizza obiettivi dirigenziali incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li> 
    
    	<li><a href="controller/listCompattaProgrammazioneDipartimentoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	    Visualizza Progr. compatta incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li>
	    
	     <li><a href="controller/listPianificazioneCompletaIncaricoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	    Visualizza la Programmazione completa incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li> 
	    
	    
		
	    <li class="divider"></li>  
	    
	    <li><a href="controllerPdf/pdfPianificazioneSemplificataIncaricoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
		Pdf Programmazione senza risorse incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li>
		
		<li><a href="controllerPdf/pdfPianificazioneCompletaIncaricoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
		Pdf Programmazione completa incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li>
		    
	    <li class="divider"></li>
	    
	    <li><a href="controller/listPianificazioneApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	    Visualizza la Programmazione apicale incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li> 
	    
	  	<li class="divider"></li>
     	
	     
	    <li><a href="controllerPdf/pdfPianificazioneApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
	    Pdf Programmazione apicale incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a></li> 
	    
		<li class="divider"></li> 
		<li><a href="qryProgrXls/xlsProgrammazioneApicaleIncarico/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
		Genera Excel programmazione apicale: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
		</li> 
		
	    
	    <li class="divider"></li>
	    
	 	    
		<li><a href="controller/listPianificazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
		    Visualizza la Programmazione incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>   
		   
	    
	    
		    
	</ul>
</li>



<li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Rendicontazione<b class="caret"></b></a>
		<ul class= "dropdown-menu">	
		
			<li> <a href="controllerRend/listRendicontazioneDipartimentaleController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
			    Visualizza la Rendicontazione Dipartimentale: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
			</li> 
			
			 
			
			<li> <a href="controllerRend/listRendicontazioneDipartimentalePrioritariaController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
			    Visualizza la Rendicontazione Prioritaria dip.: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
			</li> 
			
			<li class="divider"></li>  
			
			
			
			<li> 
         	<a href="controllerRend/listRendicontazioneIncaricoApicaleController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
		     Visualizza la Rendicontazione Apicale Incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
	     	</li>   
	     	<li> 
         	<a href="controllerRend/listRendicontazioneDirettaIncaricoApicaleController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
		     Visualizza la Rendicontazione Apicale Diretta Incarico: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
	     	</li> 
			
	    	
			<li class="divider"></li>    
			
		    
		    <li><a href="controllerRendPdf/pdfRendicontazioneCompletaIncaricoController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
			    Pdf Rendicontazione Dipartimentale: ${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
			</li>
			
			<li><a href="controllerRendPdf/pdfRendicontazioneDipartimentalePrioritariaController/${menu.anno}/${menu.incaricoApicaleAmm_ne.idIncarico}">
			    Pdf Rendicontazione Prioritaria dip.:${menu.incaricoApicaleAmm_ne.stringa} / ${menu.anno}</a>
			</li>    
			    
			<li class="divider"></li>  
	 
			 <li><a href="controllerRend/listRendicontazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
			    Visualizza la Rendicontazione incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
			    
			 
			
			 
			
		</ul>
</li>
</ul>  	
</li>
</ul>
</div>
</div>
	
</security:authorize> 

</body>
</html>
