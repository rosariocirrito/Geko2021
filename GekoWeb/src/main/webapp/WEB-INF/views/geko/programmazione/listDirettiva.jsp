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
	<h1>Direttiva annuale per l'anno <em>${anno} </em></h1>   
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
			<div class="col-10 col" style="background: lightgreen; ">
				<h3 class="text-center" >${area.codice} - ${area.descrizione}</h3> 
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
					<h5>${prio.descrizione}</h5> 
				</div>	
			</div>
		
			<!-- per ogni obiettivo strategico -->
	   		<c:forEach items="${prio.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
		        <div class="row">
			        <div class="col-2 col" style="background: lightyellow;">
						<p>${objStrat.codiceAss}<p> 
					</div>
					<div class="col-10 col" style="background: lightblue;">
						<p>${objStrat.descrizione}</p>
					</div>	
		        </div>
		       
		        <div class="row">
		       		<div class="col-5 offset-2">
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
		                <div class="col-5 offset-2">
							<p>${outcome.descrizione}<p> 
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
		    	
		    	<!-- obiettivi apicali -->
		    	<c:forEach items="${objStrat.associazObiettivi}" var="associaz" varStatus ="statusAssociaz">
			         <div class="row">
				         <div class="col-5">
								<p>${associaz.apicale.responsabile}<p> 
						</div>
				        <div class="col-7">
								<p>${associaz.apicale.codice} - ${associaz.apicale.descrizione}<p> 
						</div>
			        </div>
			        </c:forEach>
		    
		    
	    	</c:forEach> <!-- objStrat -->
        
        </c:forEach> <!-- prio -->   
        
        </div>      
        </div>
</c:forEach> <!-- area --> 
  

          
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>

</body>
</html>
