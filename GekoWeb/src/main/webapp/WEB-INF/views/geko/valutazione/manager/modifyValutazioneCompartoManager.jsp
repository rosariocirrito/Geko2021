<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <%@ include file= "/WEB-INF/views/common/bootstrap4_css.jsp" %>

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
	                 <a class="btn btn-info" href="<spring:url value="/dirigenteAssegn/${assegnazione.id}/edit" htmlEscape="true" />" role="button">MODIFICA ASSEGNAZIONE </a>
			</div>
			<div class="col-1 colobj"> 
				<p>${assegnazione.peso}</p>
			</div>
			<div class="col-2 colobj">
				<p>
				<a class="btn btn-info" href="<spring:url value="/dirigenteValutazioneAzioneComparto/${assegnazione.id}/editValutazione" htmlEscape="true" />" role="button">MODIFICA</a>
	            ${assegnazione.valutazione}</p>
			</div>
			<div class="col-2 colobj">
				<p>${assegnazione.punteggio}</p>
			</div>
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
    	
    	
	   
	        
	
	    
	    <!-- itero sulla lista Valutazioni anche se dovrebbe contenerne solo 1  -->
	    <c:forEach items="${map2Item.value}" var="valutazioneComparto" varStatus ="status">
	    
	    	<c:set var= "esisteGia" value="true" />
	    	<!-- comportamenti organizzativi -->
		<div class="row">
			<div class="col-7 col "><h4>Comportamenti Organizzativi</h4></div>
		    <div class="col-1 col ">Peso</div>
			<div class="col-2 col ">
				<a class="btn btn-info" href="<spring:url value="/dirigenteValutazioneComparto/${valutazioneComparto.id}/edit" htmlEscape="true" />"role="button">Edit Valutazione </a>
			</div>
			<div class="col-2 col ">Punteggio</div>
		</div>
	    	
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
	    <p><a href="<spring:url value="/dirigenteCompOrg/new/${mapItem.key.idPersona}/${anno}/${idIncarico}" htmlEscape="true" />">Aggiungi Comportamenti Organizzativi per ${mapItem.key.stringa} </a></p> 
	   	</c:if>
    </c:if>
    	
	
	</c:forEach>
	<br />
	<hr />          
	</c:forEach>
	
   

<div class="row">
<div class="col-12 col ">
<hr/>
Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito [modifyValutazioneCompartoManager.jsp] [ultima modifica: 2019/12/09]
<hr/>
</div>
</div>

 <%@ include file="/WEB-INF/views/common/footer.jsp" %>  
</div>
</div> <!-- container -->   
 <%@ include file= "/WEB-INF/views/common/bootstrap4_js.jsp" %>

