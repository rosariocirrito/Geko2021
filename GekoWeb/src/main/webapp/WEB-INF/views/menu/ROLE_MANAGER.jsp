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
	<title>Geko - Menu Manager</title>	
	    
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

<!--security:authorize ifAnyGranted="ROLE_MANAGER"--> 
<security:authorize access="hasRole('MANAGER')">

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
<legend>Parametri della Sessione MANAGER</legend> 
	
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="anno" path="anno">Anno</form:label>
    <form:input class="form-control" type="text" path="anno"/>
    <form:errors path="anno" cssClass="error"/>  
    </div>
	
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoScelta" path="idIncaricoScelta">Incarico Dirigenziale</form:label>
	<form:select path="idIncaricoScelta" class="form-control">
		<form:options items="${menu.incarichi}" itemValue="idIncarico" itemLabel="stringa"/>    
	</form:select>
	</div>
	
	 
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoApicaleDeptScelta" path="idIncaricoApicaleDeptScelta">Incarico Apicale</form:label>
	<form:select path="idIncaricoApicaleDeptScelta" class="form-control">
		<form:options items="${menu.incarichiApicaliDept}" itemValue="idIncarico" itemLabel="stringa"/>    
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
		<!-- 
		<li class= "dropdown">
	         <a class="dropdown-toggle" data-toggle="dropdown" href ="#" >Avvisi<b class="caret"></b></a>
			<ul class="dropdown-menu">

	        	<li><a href="dirigente/listAvvisiManager">
		      	Lista Avvisi</a></li>
		    
			</ul>  	
		</li>
		 -->
		 <li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Pianificazione<b class="caret"></b></a>
		<ul class= "dropdown-menu">	
		<li> 
         <a href="pianifHtml/listDirettivaIndirizzo/${menu.anno}">
         Visualizza la Direttiva di Indirizzo - ${menu.anno}</a>
     	</li>
		</ul>
</li>
		
		
		
		<li class= "dropdown">
         <a class="dropdown-toggle" data-toggle="dropdown" href ="#" >Programmazione<b class="caret"></b></a>
		<ul class="dropdown-menu">
			
			<li> <a href="dirigente/modifyProgrammazioneIncarico/${menu.anno}/${menu.incarico.idIncarico}">
			Gestisci progr. dirigenziale per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
			         
            <li><a href="dirigente/listCompattaProgrammazioneIncarico/${menu.anno}/${menu.incarico.idIncarico}">
        	Visualizza progr. dirigenziale per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
            
            <li><a href="dirigente/associaPianificazioneStrategicaIncaricoManager/${menu.anno}/${menu.incarico.idIncarico}">
	    	Associa Obiettivi Strat. a progr. dirigenziale per: ${menu.incarico.stringa} / ${menu.anno}</a></li> 
	    	
	    	<li> <a href="dirigente/associaCompartoProgrammazioneDirigenzialeIncarico/${menu.anno}/${menu.incarico.idIncarico}">
			Associa dipend. comparto a progr. dirigenziale per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
		 	  
        	<li><a href="dirigentePdf/pdfPianificazioneIncaricoManager/${menu.anno}/${menu.incarico.idIncarico}">
	    	Genera pdf progr. dirigenziale per: ${menu.incarico.stringa} / ${menu.anno}</a></li>
			
                <li class="divider"></li>
                <li> <a href="dirigente/listProgrammazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
                Visualizza progr. pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
                <li> <a href="dirigente/modifyProgrammazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
                Gestisci progr. pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
                <li><a href="dirigentePdf/pdfProgrammazionePopManager/${menu.anno}/${menu.incarico.idIncarico}">
	    	Genera pdf progr. pop per: ${menu.incarico.stringa} / ${menu.anno}</a></li>			
			
			
                <li class="divider"></li>    
            
	    	<li><a href="dirigente/modifyObjCompartoProgrammazione/${menu.anno}/${menu.incarico.idIncarico}">
        	Gestisci obiettivi struttura per incarico: ${menu.incarico.stringa} per ${menu.anno}</a></li>  
        	
        	<li class="divider"></li>   
        	    	
	    	<li><a href="dirigente/modifyCompartoProgrammazione/${menu.anno}/${menu.incarico.idIncarico}">
        	Gestisci assegnazioni comparto incarico: ${menu.incarico.stringa} per ${menu.anno}</a></li> 
        	
	    	<li><a href="dirigente/listCompartoProgrammazione/${menu.anno}/${menu.incarico.idIncarico}">
        	Visualizza assegnazioni comparto incarico: ${menu.incarico.stringa} per ${menu.anno}</a></li>        	
        	
        	<li><a href="dirigentePdf/pdfProgrammazioneCompartoManager/${menu.anno}/${menu.incarico.idIncarico}">        	
        	Pdf assegnazioni comparto incarico: ${menu.incarico.stringa} per ${menu.anno}</a></li>   
        	
	      	
	      <!--
	    	<li class="divider"></li>
	    	<li> <a href="dirigente/sintesiPianificazioneDipartimentoManager/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    	Visualizza obiettivi dipartimentali incarico: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
    
	    	
	    		<li class="divider"></li>
	    		<li><a href="dirigente/listPianificazioneAltroIncaricoManager/${menu.anno}/${menu.incaricoDept.idIncarico}">
			    Visualizza la Programmazione incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>  
		    -->
		 </ul>
</li>
<li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Rendicontazione<b class="caret"></b></a>
            <ul class= "dropdown-menu">	
                <li> <a href="dirigente/modifyRendicontazioneIncaricoManager/${menu.anno}/${menu.incarico.idIncarico}">
                Aggiorna rendicontazione incarico: ${menu.incarico.stringa} / ${menu.anno}</a> </li>

                <li> <a href="dirigente/listRendicontazioneIncaricoManager/${menu.anno}/${menu.incarico.idIncarico}">
                Visualizza rendicontazione incarico: ${menu.incarico.stringa} / ${menu.anno}</a></li>

                <li class="divider"></li>
        	<li> <a href="dirigente/listRendicontazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
                Visualizza rendicontazione pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
                <li class="divider"></li>
        	<!--   
	        <li><a href="dirigentePdf/pdfRendicontazioneIncaricoManager/${menu.anno}/${menu.incarico.idIncarico}">
	    	Pdf rendicontazione incarico: ${menu.incarico.stringa} / ${menu.anno}</a></li> 
			
			<li class="divider"></li>
			--> 
			
                <li> <a href="dirigente/scadenze/${menu.anno}/${menu.incarico.idIncarico}">
                Visualizza scadenzario incarico: ${menu.incarico.stringa} / ${menu.anno}</a></li>

                <!--  
                <li class="divider"></li>

                <li> <a href="dirigente/listRendicontazioneDipartimentaleManager/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
                    Visualizza la Rendicontazione Dipartimentale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
                </li> 
                <li><a href="dirigente/listRendicontazioneAltroIncaricoManager/${menu.anno}/${menu.incaricoDept.idIncarico}">
                    Visualizza la Rendicontazione incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
                -->    

			 
        </ul>
</li>
<li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Valutazione<b class="caret"></b></a>
		<ul class= "dropdown-menu">	
		
		<li><a href="dirigenteVal/listDipendentiAssegnazioniValutazione/${menu.anno}/${menu.incarico.idIncarico}">
        Visualizza Valutazione Comparto incarico: ${menu.incarico.stringa} / ${menu.anno}</a></li>
        <li><a href="dirigenteVal/modifyDipendentiAssegnazioniValutazione/${menu.anno}/${menu.incarico.idIncarico}">
        Modifica Valutazione Comparto incarico: ${menu.incarico.stringa} / ${menu.anno}</a> </li>
		<li><a href="dirigenteValPdf/pdfValutazioneStrutturaManager/${menu.anno}/${menu.incarico.idIncarico}">
        Crea Pdf Valutazione Comparto incarico: ${menu.incarico.stringa} / ${menu.anno}</a></li>
    	
    	<li class="divider"></li>	
    	
    	<li> <a href="dirigenteVal/listValutazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
		Visualizza valutazione pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>   
		<li> <a href="dirigenteVal/modifyValutazionePopIncarico/${menu.anno}/${menu.incarico.idIncarico}">
		Modifica valutazione pop per: ${menu.incarico.stringa} per ${menu.anno}</a></li>
		<li><a href="dirigenteValPdf/pdfValutazionePopManager/${menu.anno}/${menu.incarico.idIncarico}">
    	Crea pdf valutazione pop per: ${menu.incarico.stringa} / ${menu.anno}</a></li>	
		</ul>
</li>	



<li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Liste Varie<b class="caret"></b></a>
		<ul class= "dropdown-menu">	
		
		<li><a href="dirigente/listDipendenti/${menu.anno}/${menu.incarico.idIncarico}">
       	Visualizza Dipendenti incarico: ${menu.incarico.stringa} / ${menu.anno}</a></li>
       	
	   
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
