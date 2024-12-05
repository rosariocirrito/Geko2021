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
		<div class="col-2 col" style="background: lightgreen;">
				<h2>FONTE</h2> 
		</div>
		<div class="col-10 col" style="background: lightgreen; ">
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
	
	
	
    <!-- per ogni priorità politica -->
	<c:forEach items="${area.prioritaPolitiche}" var="prio" varStatus ="status">
		<hr /> 
		<!-- riga intestazione priorità politica -->     
		<div class="row">
			<div class="col-2 col" style="background: yellow;">
				<h3>FONTE</h3> 
			</div>
			<div class="col-10 col" style="background: yellow; ">
				<h3 class="text-center" >PRIORITA' POLITICA</h3> 
			</div>	
		</div>
		<!-- riga priorità politica -->     
		<div class="row">
			<div class="col-2 col" style="background: yellow;">
				<p>${prio.codiceAss}</p> 
			</div>
			<div class="col-10 col" style="background: yellow;">
				<h5>${prio.descrizione}</h5> 
			</div>	
		</div>   
		
		
		
		<!-- per ogni obiettivo specifico -->
   		<c:forEach items="${prio.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
   		<hr />
   		<!-- riga intestazione obiettivo specifico -->     
		<div class="row">
			<div class="col-2 col" style="background: lightgreen;">
				<h4>FONTE</h4> 
			</div>
			<div class="col-10 col" style="background: lightgreen; ">
				<h4 class="text-center" >OBIETTIVO SPECIFICO</h4> 
			</div>
		</div>
	        <div class="row">
		        <div class="col-2 col" style="background: lightgreen;">
					<p>${objStrat.codiceAss}<p> 
				</div>
				<div class="col-10 col" style="background: lightgreen;">
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
	        <div class="row">
		        <div class="col-2 col" style="background: lightblue;">
					<p>Strutture<p> 
				</div>
				<div class="col-10 col" style="background: lightblue;">
					<p>
					${objStrat.struttureResp}</p>
				</div>
	        </div>
	        <hr />
	        <!-- per ogni risultato atteso Map<String,List<Risultatteso>> risultatiAttesi--> 
	        
	        <c:forEach items="${objStrat.risultattesi}" var="mapItem">
	        <hr />
		        <!-- riga intestazione risultato atteso -->     
				<div class="row">
					<div class="col-2 col" style="background: lightyellow;">
						<h4>CODICE</h4> 
					</div>
					<div class="col-10 col" style="background: lightyellow;">
						<h4>RISULTATI ATTESI</h4>
					</div>
				</div>
		        <div class="row">
			        <div class="col-1 col" style="background: lightyellow;">
						<p>${mapItem.key}<p> 
					</div>
					<div class="col-11 col" style="background: lightyellow;">
						<div class="row">
					        <div class="col-1 col" style="background: lightyellow;">
								<p>anno</p>
							</div>
							<div class="col-1 col" style="background: lightyellow;">
								<p>indicatore</p>
							</div>
							<div class="col-4 col" style="background: lightyellow;">
								<p>target</p>
							</div>
							<div class="col-2 col" style="background: lightyellow;">
								<p>consuntivo</p>
							</div>
							<div class="col-4 col" style="background: lightyellow;">
								<p>analisi</p>
							</div>
						</div>
					<!-- itero sulla lista con lo stesso codice -->
					<c:forEach items="${mapItem.value}" var="ra" varStatus ="status">
					<hr />
						<div class="row">
					        <div class="col-1 col" style="background: lightyellow;">
								<p>${ra.anno}</p>
							</div>
							<div class="col-1 col" style="background: lightyellow;">
								<p>${ra.indicatore}</p>
							</div>
							<div class="col-4 col" style="background: lightyellow;">
								<p>${ra.target}</p>
							</div>
							<div class="col-2 col" style="background: lightyellow;">
								<p>${ra.consuntivo}</p>
							</div>
							<div class="col-4 col" style="background: lightyellow;">
								<p>${ra.analisi}</p>
							</div>
						</div>
					</c:forEach>
					</div>
				</div>
	        </c:forEach>
	        
	    </c:forEach>    <!-- objStrat -->
	 </c:forEach> <!-- prio -->	
	 </div> 
</c:forEach> <!-- area --> 
  



          
<%@ include file="/WEB-INF/views/common/masteringBootstrapFooter.jsp" %>

<%@ include file="/WEB-INF/views/common/masteringBootstrapSRC.jsp" %>

</div>

</body>
</html>
