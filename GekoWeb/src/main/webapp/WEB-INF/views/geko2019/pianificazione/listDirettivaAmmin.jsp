<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="UTF-8" />
    <title>List Direttiva Presidenziale</title>
	<%@ include file="/WEB-INF/views/common/masteringBootstrapCSS.jsp" %>
</head>

<body>
<div class="container" > 
<div class="jumbotron">
          
	<h1>Direttiva annuale Azione Amministrativa per l'anno <em>${anno} </em></h1>   
	<hr />      
	<c:if test="${empty listAreeStrategiche}">
	    <p>Aree Strategiche non trovate !</p>
	</c:if>


<!-- per ogni area strategica -->
<c:forEach items="${listAreeStrategiche}" var="area">    
	<!-- riga area -->     
	<div class="row">
		<div class="col-12 col" style="background: lightgreen; ">
			<h3 class="text-center" >${area.codice} - ${area.descrizione} (da: ${area.annoInizio} a: ${area.annoFine})</h3> 
		</div>	
	</div>
		
	<!-- per ogni obiettivo strategico -->
	<c:forEach items="${area.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
	     <div class="row">
	        <div class="col-1 col" style="background: yellow;">
				<p>${objStrat.codice}</p>
			</div>
			<div class="col-11 col" style="background: lightblue;">
				<p>${objStrat.descrizione}</p>
			</div>	
	      </div>
		       
		<!-- obiettivi apicali -->
		<c:forEach items="${objStrat.associazObiettiviApicali}" var="associaz" varStatus ="statusAssociaz">
			<div class="row">
				<div class="col-5">
					<p>${associaz.apicale.responsabile}<p> 
				</div>
				<div class="col-7">
					<p>${associaz.apicale.codice} - ${associaz.apicale.descrizione}<p> 
				</div>
			</div>
		</c:forEach> <!-- obiettivi apicali -->
		    
	</c:forEach> <!-- objStrat -->
	<hr />  
</c:forEach> <!-- area --> 
<hr />       
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>
</div>
 </div> 
</body>
</html>
