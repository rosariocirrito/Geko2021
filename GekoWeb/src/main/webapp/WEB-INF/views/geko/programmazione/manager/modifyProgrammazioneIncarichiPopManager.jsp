<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<!-- <meta charset="ISO-8859-1" />  -->
    <meta charset="UTF-8" />
    <title>Modifica Programmazione Obiettivi Pop della Struttura</title>
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

    
<h3>Programmazione Obiettivi Pop per <em>${struttura} </em>- anno ${anno}</h3> 
<hr />  

<c:if test="${empty listIncarichiPop}"><p>Nessun incarico trovato.</p></c:if>

<c:forEach items="${listIncarichiPop}" var="inc" varStatus ="status">
	<hgroup >            
	<h4 >Struttura  ${inc.denominazioneStruttura} </h4>
	<h4 class="responsabile"> Responsabile  ${inc.responsabile}  </h4>
	
	</h4>
	</hgroup>
	<span class="label label-success">
		<a href="<spring:url value="/dirigenteObj/newIncPop/${anno}/${inc.idIncarico}/${incarico.idIncarico} " htmlEscape="true" />">Richiedi Nuovo Obiettivo POP</a>
	</span>
	
	<!-- 1. itero sugli obiettivi ------------------------------------------------------->
	<c:forEach items="${inc.obiettivi}" var="obj" varStatus ="status">
		<!-- 1.1 tabella obiettivi ------------------------------------------------------->
		<table class="table table-bordered table-striped">
			<colgroup>				
				<col class="desrc">
				<col class="desrc">
				<col class="name">
				<col class="name">
				<col class="name">
			</colgroup>   
		    <tbody>
		    <thead> 
			<tr>				
				<th scope= "col" >comandi</th>
		    	<th scope= "col" >obiettivo</th>
		    	<th scope= "col" >note</th>
		    	<th scope= "col" >stato</th>
		        <th scope= "col" >peso</th>
		    </tr>  
			</thead>		
			<tr class="success">
				<td>		
				<c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
					<span class="label label-success">
					    <a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
				    </span>
			    	  <br />
			    </c:forEach> <!-- obj.guiCommands  -->		
				</td>
			    <td>${obj.codice} -  ${obj.descrizione}</td>
			    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>	    
			    <td>${obj.statoApprov} </td>
				<td>${obj.peso}</td>
			</tr>
			<tr>
			    <td colspan="6">
			    <table class="table table-bordered table-striped">
				<colgroup>
					<col class="name">
					<col class="desrc">
					<col class="desrc">
					<col class="name">
					<col class="name">
					<col class="name">
					<col class="name">
				</colgroup>   
		    	<tbody>
		    <thead> 
				<tr>
					<th scope= "col" >comandi</th>
			    	<th scope= "col" >azioni</th>
			    	<th scope= "col" >note</th>
			    	<th scope= "col" >indicatore previsto</th>
			    	<th scope= "col" >valore obiettivo</th>
			         <th scope= "col" >scadenza</th>
			        <th scope= "col" >peso</th>
			    </tr>  
			</thead> 
			<!-- 1.1.1 iterazione su obiettivi per visualizzare azioni ------------------------------->
    		<c:forEach items="${obj.azioni}" var="act">
        		<tr>
					<td>		
					<c:forEach items="${act.guiCommands}" var="actCmd" varStatus ="status">
						<span class="label label-info">
		    			<a href="<spring:url value="  ${actCmd.uri}  " htmlEscape="true" />">${actCmd.label}</a>
		    			</span></br>
	    			</c:forEach>
					<span class="label label-info">
	            	  <a href="<spring:url value="/managerAssegn/new/${act.idAzione}" htmlEscape="true" />">AGGIUNGI DIP.</a>
	        		</span>
					</td>
			        <td>        	
			        	${act.denominazione} - ${act.descrizione} 
			        	<c:if test="${act.tassativa}">- AZIONE E SCADENZA TASSATIVE</c:if>
			        </td>        
        			<td><c:if test="${act.note>''}">${act.note}</c:if></td>
            		<td>${act.indicatore}</td>
        			<td>${act.prodotti}</td>        
			        <td>
				        <fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
				        <c:if test="${act.tassativa}">- TASSATIVA</c:if>
			        </td>
        			<td>${act.peso}</td>
         			<!-- 1.1.1.1 iterazione su azioni per visualizzare dipendenti ------------------------------->
			        <c:forEach items="${act.assegnazioni}" var="assegn">
			        	<tr>
				        <td></td><td colspan="6">
			           	<span class="label label-error">
			            	<a href="<spring:url value="/managerAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA/CANCELLA ASSEGNAZIONE</a>
			           	</span>
			                ${assegn.opPersonaFisica.stringa} - ${assegn.peso}%	                	           
			         	</td>
		        		</tr>
		        	</c:forEach> <!-- fine for dipendenti -->
        		</tr>
    		</c:forEach> <!-- fine for azioni -->
    		</tbody>
    		</table> <!-- fine tabella azioni -->
    	</tbody>
    	</table> <!-- fine tabella obiettivi -->
   	</c:forEach> <!-- inc.obiettivi --> 
		
	<hr /> 
	<c:forEach items="${inc.valutazioni}" var="valutazione" >	
		<!-- Tabella dei comportamenti organizzativi -->
		<table>   
		<tbody>
		<tr>
			<th>Comportamenti Organizzativi</th>
			<th><a href="<spring:url value="/managerCompOrgPop/${valutazione.id}/editPesiComportOrgan" htmlEscape="true" />">Edit</a>Peso Assegnato (0 o intero>=5)</th>
		 </tr>
		<tr>
	    	<td>Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione</td>
			<td>${valutazione.analProgrAss}</td>
		</tr>
		<tr>
	    	<td>Capacità di individuazione del livello di priorità degli interventi da realizzare</td>
			<td>${valutazione.pdlAss}</td>
		</tr>			
		<tr>
	    	<td>Capacità di organizzazione del lavoro</td>
			<td>${valutazione.relazCoordAss}</td>
		</tr>
		<tr>
			<th >Sintesi</th>
			<th>Peso Attribuito</th>
		</tr>
		<tr>
			<td>Totale programmato Performance operativa (= 70)</td>
			<td>${inc.totPesoObiettivi}</td>
		</tr>		
		<tr>
			<td>Totale programmato Comportamento Organizzativo (= 30)</td>
			<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}</td>
		</tr>		
		</tbody>
		</table>	
	</c:forEach> <!-- inc.valutazioni -->
	
	<c:if test="${empty inc.valutazioni}">
	<p><a href="<spring:url value="/managerCompOrgPop/new/${inc.idIncarico}/${anno}" htmlEscape="true" />">Aggiungi sezione Comportamenti Organizzativi per ${inc.responsabile} </a></p>
	</c:if>
	<hr /> 
                  
</c:forEach><!-- listIncarichiPop -->
    
                
<!-- Footer -------------------------->

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	
</div>
</div>
</div>

</body>
  
</html>





