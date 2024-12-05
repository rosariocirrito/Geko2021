<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<style>
		.row{
			margin-top:1px;
			margin-left:16px;
			background-color:lightgray;
		}
		.rowobj{
			margin-top:1px;
			margin-left:16px;
			background-color:lightgreen;
		}
		.rowact{
			margin-top:4px;
			margin-left:16px;
			background-color:orange;
		}
		.col{
			background-color:lightblue;
			padding:10px;
			border:1px solid darkgray;
		}
		.colobj{
			background-color:lightgreen;
			padding:10px;
			border:1px solid darkgray;
		}
		.colact{
			background-color:orange;
			padding:10px;
			border:1px solid darkgray;
		}
		
	</style>

</head>
    
<body>
    
<div class="container-fluid" >
<div class="jumbotron" > 
  
 <div class="row">
	<div class="col-12 col">    	
	<h2>Valutazione Dipendenti per <em>${struttura} anno: ${anno}</em></h2>   
</div>
</div>	

    <hr />      
    <c:if test="${empty mapDipendentiAssegnazioni}">
        <p>Nessuna assegnazione dipendente trovata !</p>
    </c:if>
        
    <!-- itero sulle assegnazioni -->
    <c:forEach items="${mapDipendentiAssegnazioni}" var="mapItem">
    <c:set var= "esisteGia" value="false" />
    
    <!-- nome dipendente -->
   	<div class="row"><div class="col-12 col"><h3>${mapItem.key.stringa}</h3></div></div> 	
	
	<!-- sezione Performance Operativa -->
	<div class="row"><div class="col-12 colobj"><h4>Performance Operativa</h4></div></div>	
	
	<div class="row rowobj">
			<div class="col-7 colobj"><p>Descrizione Obiettivo Operativo / Azione</p></div>
			<div class="col-1 colobj"><p>Peso</p></div>
			<div class="col-2 colobj"><p>Valutazione</p></div>
			<div class="col-2 colobj"><p>Punteggio</p></div>
		</div>
		
	<!-- per ogni assegnazione valuto contributo ad azione -->
	<c:forEach items="${mapItem.value}" var="assegnazione" varStatus ="status">
		<div class="row rowobj">
			<div class="col-7 colobj"> 
				     ${assegnazione.azione.obiettivo.descrizione}
				     <br> 
				     <em>${assegnazione.azione.descrizione}</em>
	                 <br>
	                 (scadenza azione: <fmt:formatDate value="${assegnazione.azione.scadenza}" pattern="dd/MM/yyyy"/>)	                 
	        </div>
			<div class="col-1 colobj"> 
				<p>${assegnazione.peso}</p>
			</div>
			<div class="col-2 colobj"><p>${assegnazione.valutazione}</p></div>
			<div class="col-2 colobj"><p>${assegnazione.punteggio}</p></div>
		</div>	         
    </c:forEach>
	    
    <div class="row ">
		<div class="col-7 colobj"><p>totale risultati</p></div>
		<div class="col-1 colobj"><p>${mapItem.key.pesoAssegnazioni}</p></div>
		<div class="col-2 colobj"><p></p></div>
		<div class="col-2 colobj"><p>${mapItem.key.punteggioAssegnazioni}</p></div>
    </div>
	         	
    <!-- valutazione delle prestazioni -->
    
	<c:forEach items="${mapDipendentiValutazioneComparto}" var="map2Item">
    	<c:if test="${map2Item.key == mapItem.key}">
    	
    	<!-- comportamenti organizzativi -->
		<div class="row">
			<div class="col-7 col "><h4>Comportamenti Organizzativi</h4></div>
		    <div class="col-1 col ">Peso</div>
			<div class="col-2 col ">Valutazione</div>
			<div class="col-2 col ">Punteggio</div>
		</div>
	   
	        
	
	    
	    <!-- itero sulla lista Valutazioni anche se dovrebbe contenerne solo 1  -->
	    <c:forEach items="${map2Item.value}" var="valutazioneComparto" varStatus ="status">
	    
	    	<c:set var= "esisteGia" value="true" />
	    	
	    	<div class="row">
				<div class="col-7 "><p>Competenza nello svolgimento delle attività</p></div>
		        <div class="col-1 "><p>${valutazioneComparto.competSvolgAttivAss}</p></div>
		        <div class="col-2 "><p>${valutazioneComparto.competSvolgAttivVal}</p></div>
		        <div class="col-2 "><p>${valutazioneComparto.competSvolgAttivPunteggio}</p></div>	             
	        </div>
	        
	        <div class="row">
				<div class="col-7 "><p>Capacità di adattamento al contesto lavorativo</p></div>
		        <div class="col-1 "><p>${valutazioneComparto.adattContextLavAss}</p></div>
		        <div class="col-2 "><p>${valutazioneComparto.adattContextLavVal}</p></div>
		        <div class="col-2 "><p>${valutazioneComparto.adattContextLavPunteggio}</p></div>	             
	        </div>
	        
	        <div class="row">
				<div class="col-7 "><p>Capacità di assolvere ai compiti assegnati</p></div>
		        <div class="col-1 "><p>${valutazioneComparto.capacAssolvCompitiAss}</p></div>
		        <div class="col-2 "><p>${valutazioneComparto.capacAssolvCompitiVal}</p></div>
		        <div class="col-2 "><p>${valutazioneComparto.capacAssolvCompitiPunteggio}</p></div>	             
	        </div> 
	       
	         
	         <c:if test="${!map2Item.key.catAB}">
	         	<div class="row">
					<div class="col-7 "><p>Capacità di promuovere e gestire l'innovazione</p></div>
			        <div class="col-1 "><p>${valutazioneComparto.innovazAss}</p></div>
			        <div class="col-2 "><p>${valutazioneComparto.innovazVal}</p></div>
			        <div class="col-2 "><p>${valutazioneComparto.innovazPunteggio}</p></div>	             
		        </div> 
		        
		        <div class="row">
					<div class="col-7 "><p>Capacità di organizzazione del lavoro</p></div>
			        <div class="col-1 "><p>${valutazioneComparto.orgLavAss}</p></div>
			        <div class="col-2 "><p>${valutazioneComparto.orgLavVal}</p></div>
			        <div class="col-2 "><p>${valutazioneComparto.orgLavPunteggio}</p></div>	             
		        </div> 
		         
	    	</c:if>
	    	
	    	<div class="row ">
				<div class="col-7 col"><p>totale comportamenti organizzativi</p></div>
				<div class="col-1 col"><p>${valutazioneComparto.totPeso}</p></div>
				<div class="col-2 col"><p></p></div>
				<div class="col-2 col"><p>${valutazioneComparto.totPunteggio}</p></div>
		    </div>
	    	
	 		
	 		<!-- sintesi finale -->
		
		<div class="row">
			<div class="col-10 offset-1 colobj ">
				<h3> Sintesi valutazione anno: ${valutazioneComparto.anno} per ${mapItem.key.stringa}</h3>
			</div>
		</div>
		
		<div class="row">		
			<div class="col-6 offset-1 colobj ">Totale generale</div>	
			<div class="col-2 colobj ">${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso}
	         		 <c:if test="${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso ne 100}">
	                 	<font color="red"> rivedi i pesi !!!</font>
	                 </c:if></div>
			<div class="col-2 colobj ">${mapItem.key.punteggioAssegnazioni + valutazioneComparto.totPunteggio}</div>
		</div>
	 		
	    
	    </c:forEach>
	    
	    <c:if test="${!esisteGia}">	               
	    <p><a href="<spring:url value="/controllerCompOrgComparto/new/${mapItem.key.idPersona}/${anno}/${idIncarico}" htmlEscape="true" />">Aggiungi Comportamenti Organizzativi per ${mapItem.key.stringa} </a></p> 
	   	</c:if>
	   	
    </c:if>
    	
	
	</c:forEach>
	<br />
	<hr />          
	</c:forEach>
	
   

<div class="row">
<div class="col-12 col ">
<hr/>
Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito [listValutazioneCompartoController.jsp] [ultima modifica: 2020/04/14]
<hr/>
</div>
</div>

 <%@ include file="/WEB-INF/views/common/footer.jsp" %>  
</div>
</div> <!-- container -->   
 
<script src="<spring:url value="/resources/bootstrap4.0.0/js/bootstrap.js" htmlEscape="true" />"></script>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</html>
