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
	<h1>Pianificazione triennale ${annoInizio} - ${annoFine}</h1>   
	     
	<c:if test="${empty listAreeStrategiche}">
	    <p>Aree Strategiche non trovate !</p>
	</c:if>
</div>



<!-- per ogni area strategica -->
<c:forEach items="${listAreeStrategiche}" var="area">    
<div class="jumbotron">    
	<!-- riga intestazione area strategica -->     
	<div class="row">
		
		<div class="col-12 col" style="background: lightgreen; ">
			<h2 class="text-center" >AREA STRATEGICA</h2> 
		</div>	
	</div>
	<!-- riga area -->     
	<div class="row">
		<div class="col-2 col" style="background: lightgreen;">
				<p>${area.codiceAss} da: ${area.annoInizio} a: ${area.annoFine}</p> 
		</div>
		<div class="col-10 col" style="background: lightgreen; ">
			<h3 class="text-center" >${area.codice} - ${area.descrizione}</h3> 
		</div>	
	</div>
	
	<!-- per ogni obiettivo specifico -->
   		<c:forEach items="${area.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
   		<hr />
   		<!-- riga intestazione obiettivo specifico -->     
		<div class="row">
			
			<div class="col-12 col" style="background: lightgreen; ">
				<h4 class="text-center" >OBIETTIVO SPECIFICO</h4> 
			</div>
		</div>
	        <div class="row">
		        
				<div class="col-12 col" style="background: lightgreen;">
					<p>
					${objStrat.descrizione}</p>
				</div>	
	        </div>
	        <div class="row">
		        <div class="col-2 col" style="background: white;">
					<p>Note<p> 
				</div>
					<div class="col-10 col" style="background: white;">
					<p>
					${objStrat.note}</p>
				</div>	
	        </div>
	        
	        <hr />
	        <!-- per ogni risultato atteso Map<String,List<Risultatteso>> risultatiAttesi--> 
	        
	        
	        
	    </c:forEach>    <!-- objStrat -->
	 
	 </div> 
</c:forEach> <!-- area --> 
  



          
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>

</div>

</body>
</html>
