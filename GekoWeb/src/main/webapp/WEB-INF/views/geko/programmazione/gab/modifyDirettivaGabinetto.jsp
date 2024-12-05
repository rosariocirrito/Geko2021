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
	<h1>Modifica Direttiva annuale per l'anno <em>${anno} </em></h1>   
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
			
			<a href="<spring:url value="/gabinettoAreaStrat/${area.id}/edit" htmlEscape="true" />" class="btn btn-primary" >Modifica</a>
			
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
					<a href="<spring:url value="/gabinettoPrioritaPolitica/${prio.id}/edit" htmlEscape="true" />" class="btn btn-success" >Modifica</a>
					${prio.descrizione}</h4> 
				</div>	
			</div>
		
			<!-- per ogni obiettivo strategico -->
	   		<c:forEach items="${prio.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
		        <div class="row">
			        <div class="col-2 col" style="background: lightyellow;">
						<p>${objStrat.codiceAss}<p> 
					</div>
					<div class="col-10 col" style="background: lightblue;">
						<p>
						<a href="<spring:url value="/gabinettoObj/${objStrat.id}/edit" htmlEscape="true" />" class="btn btn-info">Modifica</a>
						${objStrat.descrizione}</p>
					</div>	
		        </div>
		       
		        <div class="row">
		        	<div class="col-2">
					</div>
		       		<div class="col-5 col-sm-5 ">
						<p>descrizione outcome<p> 
					</div>
					<div class="col-1 col">
						<p>valore iniziale<p> 
					</div>
					<div class="col-1 col">
						<p>valore finale<p> 
					</div>
					<div class="col-3 col">
						<p>fonte di riferimento<p> 
					</div>
				</div>	
					
				<!-- per ogni outcome -->	
				<c:forEach items="${objStrat.outcomes}" var="outcome" varStatus ="statusObj">
	                <div class="row">
		                <div class="col-5 col-offset-2">
							<p>
							<a href="<spring:url value="/gabinettoOutcome/${outcome.id}/edit" htmlEscape="true" />">Modifica</a>
							${outcome.descrizione}<p> 
						</div>
						<div class="col-1 col">
							<p>${outcome.outinz}<p> 
						</div>
						<div class="col-1 col">
							<p>${outcome.outfin}<p> 
						</div>
						<div class="col-3 col">
							<p>${outcome.fonte}<p> 
						</div>
	                </div>
		    	</c:forEach> <!-- outcome -->
		    
	    	</c:forEach> <!-- objStrat -->
	    	<div class="row">
        	<div class="col-10 offset-md-2">
	    	<a href="<spring:url value="/gabinettoObj/newObiettivoStrategico/${prio.id}" htmlEscape="true" />" class="btn btn-info">Crea Obiettivo Strategico</a>
        	</div>
        	</div>
        	
        </c:forEach> <!-- prio -->  
        <div class="row">
        <div class="col-12">
        <a href="<spring:url value="/gabinettoPrioritaPolitica/newPrioritaPolitica/${area.id}" htmlEscape="true" />" class="btn btn-success" >Crea Nuova Priorità Politica</a>
        </div>
        </div>
        
        
</div>
</div>        
</c:forEach> <!-- area --> 

<div class="container"> 
<div class="jumbotron">
	<div class="row">
        <div class="col-5 offset-2">
        <a href="<spring:url value="/gabinettoAreaStrat/newAreaStrat" htmlEscape="true" />" class="btn btn-primary" >Crea Nuova Area Strategica</a>
        </div>
     </div> 
</div>
</div>
        

          
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>

</body>
</html>
