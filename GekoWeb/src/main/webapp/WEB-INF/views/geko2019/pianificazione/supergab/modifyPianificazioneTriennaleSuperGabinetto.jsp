<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="UTF-8" />
    <title>Modifica Direttiva Presidenziale</title>
	<%@ include file="/WEB-INF/views/common/masteringBootstrapCSS.jsp" %>
</head>

<body>
<div class="container" >           
	<h1>Modifica Pianificazione Triennale da anno <em>${anno} </em></h1>   
	<hr />      
	<c:if test="${empty listAreeStrategiche}">
	    <p>Aree Strategiche / PrioritaPolitiche non trovate !</p>
	</c:if>
</div>

<!-- per ogni area strategica -->
<c:forEach items="${listAreeStrategiche}" var="area">    
	<div class="container"> 
	<div class="jumbotron">
	
		<!-- riga area -->     
		<div class="row">
			<div class="col-2 col" style="background: yellow;">
					<p>${area.codiceAss}</p> 
				</div>
			<div class="col-10 col" style="background: lightgreen;">
			<h2>
			<a href="<spring:url value="/superGabAreaStrat/${area.id}/edit" htmlEscape="true" />" class="btn btn-primary" >Modifica</a>
				${area.codice} - ${area.descrizione}</h2> 
			</div>	
		</div>
		
		<!-- per ogni priorità politica -->
		<c:forEach items="${area.prioritaPolitiche}" var="prio" varStatus ="status">
			<!-- riga priorità politica -->     
			<div class="row">
				<div class="col-2 col" style="background: lightyellow;">
					<p>${prio.codiceAss}</p> 
				</div>
				<div class="col-10 col">
					<h4>
					<a href="<spring:url value="/superGabPrioritaPolitica/${prio.id}/edit" htmlEscape="true" />" class="btn btn-success" >Modifica</a>
					${prio.descrizione}</h4> 
				</div>	
			</div>
		
			<!-- per ogni obiettivo strategico -->
	   		<c:forEach items="${prio.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
		        <div class="row">
			        <div class="col-2 col" style="background: lightyellow;">
						<p>${objStrat.codiceAss}<p> 
					</div>
					<div class="col-5 col" style="background: lightblue;">
					<a href="<spring:url value="/superGabObiettivoStrategico/${objStrat.id}/edit" htmlEscape="true" />" class="btn btn-success" >Modifica</a>
					
						<p>
						${objStrat.descrizione}</p>
					</div>
					<div class="col-5 col" style="background: lightblue;">
						<p>
						${objStrat.note}</p>
					</div>	
		        </div>
		        <!-- per ogni risultato atteso Map<String,List<Risultatteso>> risultatiAttesi--> 
		        <c:forEach items="${objStrat.risultattesi}" var="mapItem">
			        <div class="row">
				        <div class="col-1 col" style="background: lightyellow;">
							<p>${mapItem.key}<p> 
							<p><a href="<spring:url value="/superGabRisultatteso/newRisultatteso/${objStrat.id}/${mapItem.key}" htmlEscape="true" />" class="btn btn-warning">Aggiungi Risultato Atteso</a>
	        				</p>
						</div>
						<div class="col-11 col" style="background: lightblue;">
						<div class="row">
						        <div class="col-2 col" style="background: lightyellow;">
									<p>anno</p>
								</div>
								<div class="col-1 col" style="background: lightyellow;">
									<p>indicatore</p>
								</div>
								<div class="col-5 col" style="background: lightyellow;">
									<p>target</p>
								</div>
								<div class="col-4 col" style="background: lightyellow;">
									<p>consuntivo</p>
								</div>
							</div>
						<!-- itero sulla lista con lo stesso codice -->
						<c:forEach items="${mapItem.value}" var="ra" varStatus ="status">
							<div class="row">
						        <div class="col-2 col" style="background: lightyellow;">
						        <p><a href="<spring:url value="/superGabRisultatteso/${ra.id}/edit" htmlEscape="true" />" class="btn btn-success" >Edita</a>
									${ra.anno}</p>
								</div>
								<div class="col-1 col" style="background: lightyellow;">
									<p>${ra.indicatore}</p>
								</div>
								<div class="col-5 col" style="background: lightyellow;">
									<p>${ra.target}</p>
								</div>
								<div class="col-4 col" style="background: lightyellow;">
									<p>${ra.consuntivo}</p>
								</div>
							</div>
							<div class="row">
							<div class="col-2 col" style="background: white;">
									<p>analisi</p>
								</div>
							<div class="col-10 col" style="background: white;">
									<p>${ra.analisi}</p>
								</div>
							</div>
						</c:forEach>
						</div>
					</div>
		        </c:forEach>
		        
		        <div class="row">
	        	<div class="col-10 offset-md-2">
		    	<a href="<spring:url value="/superGabRisultatteso/newRisultatteso/${objStrat.id}" htmlEscape="true" />" class="btn btn-warning">Crea Risultato Atteso</a>
	        	</div>
	        	</div>
		        
		        <div class="row">
			        <div class="col-2 col" style="background: lightyellow;">
						<p>Strutture<p> 
					</div>
					<div class="col-10 col" style="background: lightblue;">
						<p>
						${objStrat.struttureResp}</p>
					</div>
						
		        </div>
	    	</c:forEach> <!-- objStrat -->
	    	<div class="row">
        	<div class="col-10 offset-md-2">
	    	<a href="<spring:url value="/superGabObiettivoStrategico/newObiettivoStrategico/${prio.id}" htmlEscape="true" />" class="btn btn-info">Crea Obiettivo Strategico</a>
        	</div>
        	</div>
        	
        </c:forEach> <!-- prio -->  
        <div class="row">
        <div class="col-12">
        <a href="<spring:url value="/superGabPrioritaPolitica/newPrioritaPolitica/${area.id}" htmlEscape="true" />" class="btn btn-success" >Crea Nuova Priorità Politica</a>
        </div>
        </div>
        
        
</div>
</div>        
</c:forEach> <!-- area --> 

<div class="container"> 
<div class="jumbotron">
	<div class="row">
        <div class="col-5 offset-2">
        <a href="<spring:url value="/superGabAreaStrat/newAreaStrat" htmlEscape="true" />" class="btn btn-primary" >Crea Nuova Area Strategica</a>
        </div>
     </div> 
</div>
</div>
        

          
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>

</body>
</html>
