<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Programmazione Obiettivi Apicali Diretti della Struttura del Dirigente</title>
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
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
    
<div class="container" >
<div class="row">
<div class="col-lg-12 col">
    
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Programmazione Obiettivi Apicali Diretti per dipartimento: <em>${dipartimento} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${responsabile}</h4>
    
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
            
<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi</th>
			<th scope= "col" >strategico</th>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
	
	<tr class="success">
	<td>
	<span class="label label-success">
	  <a href="<spring:url value="/controllerObj/newDip/${anno}/${idIncaricoApicale}" htmlEscape="true" />">CREA OBJ APICALE</a>
	</span> <br />
	<c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
			<span class="label label-success">
				<a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
		    </span>
	    	  <br />
	    </c:forEach>
	
	</td>
	
	
	<td>
	<c:if test="${empty obj.associazObiettivi}">
    <span class="label label-info">
		<a href="<spring:url value="/controllerAssocia/new/${obj.idObiettivo}/${idIncaricoApicale}" htmlEscape="true" />">ASSOCIA STRATEGICO </a>
	</span>
	</c:if>
		<c:forEach items="${obj.associazObiettivi}" var = "associaz">
		<span class="label label-success">
		<a href="<spring:url value="/controllerAssocia/${associaz.id}/edit/${idIncaricoApicale}" htmlEscape="true" />">MODIFICA STRATEGICO </a>
		</span> 
		${associaz.strategico.descrizione}
		</br> 	
		</c:forEach>
	</td>
    <td>     
    
    
     
    
    ${obj.codice} -  ${obj.descrizione} 
    
    
	</td>	 
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    <td>${obj.statoApprov} </td>
	<td>${obj.pesoApicale} </td> 
	</tr>
	<tr>
    <td colspan="5">
    
    <table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="desrc">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi</th>
	    	<th scope= "col" >azioni</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >indicatore previsto</th>
	    	<th scope= "col" >valore obiettivo</th>
	    	
	        
	        
	        <th scope= "col" >scadenza apicale</th>
	        <th scope= "col" >peso apicale</th>
	        
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
        <td>
        <c:forEach items="${act.guiCommands}" var="cmd" varStatus ="status">
	    	<span class="label label-warning">
	    	<a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
	    	</span>
	    	<br />
	    	
	    </c:forEach>
        <span class="label label-info">
	            <a href="<spring:url value="/controllerAssegn/new/${act.idAzione}" htmlEscape="true" />">AGGIUNGI DIP.</a>
	        </span>
        </td>
        <td>
        	${act.denominazione} - ${act.descrizione}
        </td>
        
        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
            
        <td>${act.indicatore}</td>
        <td>${act.prodotti}</td>
        
        
        <td>
        	<fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/>
        </td>
        <td>${act.pesoApicale}</td>
        
        </tr>
        <!-- Dipendenti -->
	        
	        <c:forEach items="${act.assegnazioni}" var="assegn">
	        	<tr>
		        <td> 
		        
		        </td>
		        <td colspan="6">
		        
	           	<span class="label label-error">
	            	<a href="<spring:url value="/controllerAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA/CANCELLA ASSEGNAZIONE</a>
	           	</span>
	                ${assegn.opPersonaFisica.stringa} - ${assegn.peso}%	                	           
	         </td>
        	</tr>
        	</c:forEach>
    </c:forEach>
    </tbody>
</table>   
    
    </td>
    </tr>
</c:forEach>
</table>

<hr /> 
<p><span class="label label-info"><a href="<spring:url value="/controllerObj/newDip/${anno}/${idIncarico}" htmlEscape="true" />">Crea ulteriore obiettivo apicale</a></span>
<hr /> 	

		<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
			<!-- Tabella dei comportamenti organizzativi -->
			<table>   
			<tbody>
			<tr>
				<th>Comportamenti Organizzativi</th>
				<th><a href="../../../controllerCompOrg/${valutazione.id}/editPesiComportOrgan">Edit</a>Peso Assegnato (0 o intero>=5)</th>
				
			</tr>
			<tr>
		    	<td>Capacità di intercettare, gestire risorse e programmare</td>
				<td>${valutazione.gestRealAss}</td>
			</tr>
			
			<tr>
		    	<td>Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione</td>
				<td>${valutazione.analProgrAss}</td>
			</tr>
			<tr>
		    	<td>Capacita' di valorizzare competenze ed attitudini dei propri collaboratori</td>
				<td>${valutazione.relazCoordAss}</td>
			</tr>
			
			<tr>
		    	<td>Capacità di individuazione del livello di priorità degli interventi da realizzare</td>
				<td>${valutazione.pdlAss}</td>
			</tr>
			<tr>
				<th >Sintesi</th>
				<th>Peso Attribuito</th>
			</tr>
			<tr>
				<td>Totale programmato Performance operativa (= 70)</td>
				<td>${incarico.totPesoObiettivi}</td>
			</tr>		
			<tr>
				<td>Totale programmato Comportamento Organizzativo (= 30)</td>
				<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}</td>
			</tr>
			
			</tbody>
			</table>
		
	
	</c:forEach> <!-- inc.valutazioni -->
	
	
	<c:if test="${empty incarico.valutazioni}">
	<p><a href=\"../../../controllerCompOrg/new/${incarico.idIncarico}/${anno}>Aggiungi sezione Comportamenti Organizzativi per ${incarico.responsabile} </a></p>
	
	</c:if>
	<hr /> 
	
</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view: modifyPianificazioneIncaricoApicaleDirettoController.jsp 2019/05/27] </td>
    </tr>
</table>
</div>
  
 </div>
 </div>
    
    
</div>     
</body>
</html>
