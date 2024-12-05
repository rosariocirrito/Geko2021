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
    
    <title>Geko - Menu Controller </title>	
	    
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

<!--  security:authorize ifAnyGranted="ROLE_CONTROLLER"> -->
<security:authorize access="hasRole('CONTROLLER')">

<div class="container" >
<div class="row">
<div class="col-sm-8 col">



<p>Utente: <span class="label label-success">${nomeUtente} </span> 
&nbsp Persona: <span class="label label-success">${persona.stringa}</span> </p>
<p>Struttura: <span class="label label-warning">${struttura.denominazione} </span></p> 
<p>Dipartimento: <span class="label label-warning">${dipartimento.denominazione} </span></p> 

<ul>

<ul>
<c:forEach items="${ruoli}" var="ruolo">
     <li><a href="${ruolo}">${ruolo}</a> </li>
</c:forEach>
</ul>

<hr />
<form:form modelAttribute="menu" method="post">
<fieldset>
<legend>Parametri della Sessione CONTROLLER </legend>


<form:label class="control-label" for="anno" path="anno">Anno</form:label>
    <form:input class="input-medium" type="text" path="anno"/>
    <form:errors path="anno" cssClass="error"/>  

	<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoApicaleDeptScelta" path="idIncaricoApicaleDeptScelta">Incarico Apicale</form:label>
	<form:select path="idIncaricoApicaleDeptScelta" class="form-control">
		<form:options items="${menu.incarichiApicaliDept}" itemValue="idIncarico" itemLabel="stringa"/>    
	</form:select>
	</div>
	
	<div class="form-group col-sm-2">
	<form:label class="control-label" for="idIncaricoDeptScelta" path="idIncaricoDeptScelta">Incarico Dirigenziale</form:label>
	<form:select path="idIncaricoDeptScelta" class="form-control">
		<form:options items="${menu.incarichiDept}" itemValue="idIncarico" itemLabel="stringa"/>    
	</form:select>
	</div>
	
	<form:label class="control-label" for="giorniHistory" path="giorniHistory">Giorni storico</form:label>
	<form:input class="input-medium" type="text" path="giorniHistory"/>
    <form:errors path="giorniHistory" cssClass="error"/>
	
</fieldset>
 <div class="control-group col-sm-2">
	<div class="controls">
		<button type="submit" class="btn-large  btn-primary" name="update">Aggiorna</button>
		<button type="submit" class="btn-large btn-warning" name="logout">Logout</button>
	</div>
</div>
</form:form>

<div class="navbar navbar-default" >
    <div class="container-fluid" >
        <ul class= "nav nav-tabs">
            
        <!-- pianificazione -->
	<li class= "dropdown">
	<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Pianificazione<b class="caret"></b></a>
	<ul class= "dropdown-menu">		
            <li> 
             <a href="pianifHtml/listDirettivaAzAmmin/${menu.anno}">
             Visualizza la Direttiva Azione ammi.va - ${menu.anno}</a></li>     	
	</ul>
	</li>
	
        <!-- programmazione apicale -->
	<li class= "dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href ="#" >ProgrApicale<b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li><a href="controller/modifyProgrTriennaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    (non usare) Gestisci programmazione triennale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	  
	    <li><a href="controller/listProgrTriennaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    (non usare) Visualizza programmazione triennale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	    
	    <li class="divider"></li> 
            <li><a href="controller/modifyPianificazioneApicaleDirettaController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Gestisci programmazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	  
	    <li><a href="controller/listPianificazioneApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Visualizza programmazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	        
	    <li><a href="controller/associaPianificazioneStrategicaIncaricoApicaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Associa ad Obiettivi Strategici programmazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	    
	    <li><a href="controller/associaProgrammaIncaricoApicaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Associa a Missioni/Programmi a programmazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	    
	    <li><a href="controllerPdf/pdfPianificazioneApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Genera Pdf programmazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	    	    
	    <li class="divider"></li> 
            <li><a href="qryProgrXls/xlsProgrammazioneApicaleIncarico/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
            Genera Excel programmazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 

            <li class="divider"></li>	
	</ul>  	
	</li>
	
        <!-- programmazione dipartimentale -->
	<li class= "dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href ="#" >ProgrDipart<b class="caret"></b></a>
	<ul class="dropdown-menu">
            
	    <li> <a href="controller/modifyPianificazioneCompletaIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Gestisci programmazione dirigenziale dipartimentale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	    
	    <li><a href="controller/listPianificazioneCompletaIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Visualizza obiettivi dirigenziali con azioni: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	    
	    <li><a href="controller/listCompattaProgrammazioneDipartimentoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Visualizza obiettivi dirigenziali senza azioni: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
	    
	    <li><a href="controllerPdf/pdfProgrammazioneDirigenzialeController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
            Genera Pdf programmazione dirigenziale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
	    <li><a href="controllerPdf/pdfProgrammazionePopController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
            Genera Pdf programmazione Pop ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
	        	    	
            <li><a href="controller/modifyCompartoProgrammazione/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
            Gestisci assegnazioni comparto incarico: ${menu.incaricoApicaleDept.stringa} per ${menu.anno}</a></li> 
            <li><a href="controller/listCompartoProgrammazione/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
            Visualizza assegnazioni comparto incarico: ${menu.incaricoApicaleDept.stringa} per ${menu.anno}</a></li>        	

            <li><a href="controllerPdf/pdfProgrammazioneCompartoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">        	
            Pdf assegnazioni comparto incarico: ${menu.incaricoApicaleDept.stringa} per ${menu.anno}</a></li>   
	        
	    <li class="divider"></li> 			    
	    <li><a href="controller/richiediApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}/${menu.incaricoDept.idIncarico}">
		    Richiedi programmazione apicale a dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
	   	
	    <li><a href="controllerObj/new/${menu.anno}/${menu.incaricoDept.idIncarico}">
		    Richiedi programmazione a dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
	   	
            <li><a href="controller/modifyPianificazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
                Modifica programmazione del dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>

            <li><a href="controller/listPianificazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
                Visualizza programmazione del dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>   

            <li><a href="controller/navigaPianificazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
                Naviga programmazione dipartimentale: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li> 

            <li class="divider"></li>
            <li><a href="controllerPdf/pdfPianificazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
            Genera pdf programmazione per dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
		     
            <li><a href="controllerIncarico/clonaPianificazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
               Clona una programmazione esistente sull'incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>  
	</ul>
    </li>

    <!-- rendicontazione -->
    <li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Rendicontazione<b class="caret"></b></a>
            <ul class= "dropdown-menu">	
		
                <li><a href="controllerRend/modifyRendicontazioneIncaricoApicaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	        Aggiorna la Rendicontazione Apicale : ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>     
	    	</li>
	    	
	    	<li><a href="controllerRend/listRendicontazioneDirettaIncaricoApicaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
		     Visualizza la Rendicontazione Apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
	     	</li>
                
	     	<li><a href="controllerRendPdf/pdfRendicontazioneApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    	Genera Pdf Rendicontazione apicale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li> 
	     
		<li class="divider"></li>  
			
		<li><a href="controllerRend/verifyRendicontazioneDipartimentaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
                    Modifica la Rendicontazione Dipartimentale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>     
	    	</li>
	    	
                <li> <a href="controllerRend/listRendicontazioneDipartimentaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
                    Visualizza la Rendicontazione Dipartimentale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
                </li> 
			
                <li class="divider"></li>    
                
                <li><a href="controllerRend/listRendicontazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
                    Visualizza la Rendicontazione incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
			 
		<li><a href="controllerRend/navigaRendicontazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
	    	Naviga la Rendicontazione incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li> 
	 
		<li class="divider"></li>	     
		<li><a href="controllerRend/scadenze/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
			    Visualizza scadenzario incarico: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
			
		</ul>
</li>



<li class= "dropdown">
<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Valutazione<b class="caret"></b></a>
<ul class= "dropdown-menu">	
		
    <li>         
         <a href="controllerValut/listValutazioneDipartimentaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}"> 
         Visualizza la Valutazione Dipartimentale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
    </li>
     
    <li>
         <a href="controllerValut/modifyValutazioneDipartimentaleController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}"> 
         Modifica la Valutazione Dipartimentale: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
    </li>
		    
		     
    <li class="divider"></li>
     
    <li><a href="controllerValut/listValutazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
        Visualizza Valutazione Dirigente incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a>
    </li>
    <li><a href="controllerValut/navigaValutazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
	    Naviga Valutazione Dirigente incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li> 
    <li>    
        <a href="controllerValut/modifyValutazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
        Modifica Valutazione Dirigente incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a>
    </li>
    
    
     
    <li class="divider"></li>
    <li>
   		<a href="controllerValutPdf/pdfValutazioniDipartimentoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
   		Pdf Valutazione dipartimento:  ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
    </li>
     
     <li>
    	
	   <a href="controllerValutPdf/pdfSintesiValutazioniDipartimentoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	    Pdf SintesiValutazione dipartimento:  ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
     </li>
     
     <!-- 
     <li>
         <a href="controllerValutPdf/pdfValutazioneIncaricoController/${menu.anno}/${menu.incaricoDept.idIncarico}">
         Pdf Valutazione Dirigente incarico: ${menu.incaricoDept.stringa} / ${menu.anno}</a>
     </li>
      -->
      
     <li>
         <a href="controllerValutPdf/pdfValutazioneIncaricoControllerSenzaCalcolo/${menu.anno}/${menu.incaricoDept.idIncarico}">
         Pdf Valutazione Dirigente incarico (senza calcolo): ${menu.incaricoDept.stringa} / ${menu.anno}</a>
     </li>
     
     <li class="divider"></li>
     
     <li><a href="controllerValut/listDipendentiAssegnazioniValutazione/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
        Visualizza Valutazione Comparto diretto: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
        
    	<li><a href="controllerValut/modifyDipendentiAssegnazioniValutazione/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
        Modifica Valutazione Comparto diretto: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a> </li>
		
		<li><a href="controllerValutPdf/pdfValutazioneCompartoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
        Crea Pdf Valutazione Comparto diretto: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
    
    	<li class="divider"></li>
		<li><a href="controllerValutPdf/pdfValutazioneCompartoDipartimentoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
        Crea Pdf Valutazioni Comparto dai dirigenti: ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a></li>
        
        <li class="divider"></li>
          <li><a href="controllerValut/listValutazioneStrutturaManager/${menu.anno}/${menu.incaricoDept.idIncarico}">
        Visualizza Valutazione Comparto dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a> </li>
        
        <li><a href="controllerValut/modifyValutazioneStrutturaManager/${menu.anno}/${menu.incaricoDept.idIncarico}">
        Modifica Valutazione Comparto dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a> </li>
		
		<li><a href="controllerValutPdf/pdfValutazioneStrutturaManager/${menu.anno}/${menu.incaricoDept.idIncarico}">
        Crea Pdf Valutazione Comparto dirigente: ${menu.incaricoDept.stringa} / ${menu.anno}</a></li>
        
       
    </ul>
</li>	

<li class= "dropdown">
	<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Valutazione Apicale<b class="caret"></b></a>
	<ul class= "dropdown-menu">		
		<li><a href="controllerValutPdf/pdfValutazioneApicaleIncaricoController/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
		    Pdf Valutazione Dirigente apicale incarico: ${menu.incaricoApicaleDept.stringa}  / ${menu.anno}</a>
	    </li>
    </ul>
</li>



<li class= "dropdown">
	<a href ="#" class="dropdown-toggle" data-toggle="dropdown">Performance<b class="caret"></b></a>
	<ul class= "dropdown-menu">	
		<li>
	         <label class="extralarge"></label>
	         <a href="controllerPerformancePdf/valutazioniDipartimentali/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	         Pdf lista Valutazioni dipartimentali (pazienza!): ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
     	</li>
		<li>
	         <label class="extralarge"></label>
	         <a href="controllerPerformancePdf/form/${menu.anno}/${menu.incaricoApicaleDept.idIncarico}">
	         Pdf distribuzione Valutazioni per fascia: (pazienza!) ${menu.incaricoApicaleDept.stringa} / ${menu.anno}</a>
     	</li>
   </ul>
</li>	

<li class= "dropdown">
        <a href ="#" class="dropdown-toggle" data-toggle="dropdown">Storico e Liste Varie<b class="caret"></b></a>
		<ul class= "dropdown-menu">	
		<li>
		    <label class="extralarge"></label>
		    <a href="controller/listJournalAllController/${menu.giorniHistory}/${menu.incaricoApicaleDept.idIncarico}">
		    Visualizza la Storico del Dipartimento negli ultimi giorni: ${menu.giorniHistory} / ${menu.incaricoApicaleDept.stringa}</a>
	    </li>
		
	    <li>
		    <label class="extralarge"></label>
		    <a href="controller/listJournalUserController/${menu.giorniHistory}/${menu.incaricoDept.idIncarico}">
		    Visualizza la Storico del Manager negli ultimi giorni: ${menu.giorniHistory} / ${menu.incaricoDept.stringa}</a>
	    </li>
		<li>
         <label class="extralarge"></label>
         <a href="controller/listDipendenti">
         Visualizza Strutture / Dipendenti dipartimento: ${menu.dipartimento.codice}</a>
     </li>
	   </ul>
</li>	

</ul>
</div>
</div>
	
</security:authorize> 

</body>
</html>








