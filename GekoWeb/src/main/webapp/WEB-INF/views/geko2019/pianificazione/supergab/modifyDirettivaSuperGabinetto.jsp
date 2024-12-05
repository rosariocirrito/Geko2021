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
<div class="jumbotron">          
	<h1>Modifica Direttiva annuale per l'anno <em>${anno} </em></h1>   
	<hr />      
	<c:if test="${empty listAreeStrategiche}">
	    <p>Aree Strategiche / PrioritaPolitiche non trovate !</p>
	</c:if>

	<!-- per ogni area strategica -->
	<c:forEach items="${listAreeStrategiche}" var="area">    
		<!-- riga area -->     
		<div class="row">
			<div class="col-12 col" style="background: lightgreen;">
				<h3>
				<a href="<spring:url value="/superGabAreaStrat/${area.id}/edit" htmlEscape="true" />" class="btn btn-primary" >Modifica</a>
					${area.codice} - ${area.descrizione} (da: ${area.annoInizio} a: ${area.annoFine})</h3> 
			</div>	
		</div>
			<!-- per ogni obiettivo strategico -->
	   		<c:forEach items="${area.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
		        <div class="row">     
					<div class="col-1 col" style="background: lightblue;">
						<p>	${objStrat.codice}</p>
					</div>	
					<div class="col-11 col" style="background: lightblue;">
						<p>	<a href="<spring:url value="/superGabObiettivoStrategico/${objStrat.id}/edit" htmlEscape="true" />" class="btn btn-info">Modifica</a>
							${objStrat.descrizione}</p>
					</div>	
		        </div>
			</c:forEach> <!-- objStrat -->
		    
	    	<div class="row">
	        	<div class="col-10 offset-md-2">
		    	<a href="<spring:url value="/superGabObiettivoStrategico/newObiettivoStrategico/${area.id}" htmlEscape="true" />" class="btn btn-info">Crea Obiettivo Strategico</a>
	        	</div>
	       	</div>
	<hr />  
	</c:forEach> <!-- area --> 
	

	<div class="row">
        <div class="col-5 offset-2">
        <a href="<spring:url value="/superGabAreaStrat/newAreaStrat" htmlEscape="true" />" class="btn btn-primary" >Crea Nuova Area Strategica</a>
        </div>
     </div> 
	<hr />  
	
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>
</div>
</div> 
</body>
</html>
