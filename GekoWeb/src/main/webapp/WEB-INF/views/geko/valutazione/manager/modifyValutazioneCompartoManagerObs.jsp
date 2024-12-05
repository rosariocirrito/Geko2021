<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Modifica Valutazione Dipendenti della Struttura/SubStrutture</title>
    <link href="../../../resources/css/lista.css" rel="stylesheet"/>
</head>
    
<body>
    
    
<article>
    <div id="wrapper">
    <section id="obj-sezione">   
    <h1 id="titolo-lista">Valutazione Dipendenti per <em>${struttura} anno: ${anno}</em></h1>   

    <hr />      
    <c:if test="${empty mapDipendentiAssegnazioni}">
        <p>Nessun dipendente trovato !</p>
    </c:if>
        
    <!-- itero sulle assegnazioni -->
    <c:forEach items="${mapDipendentiAssegnazioni}" var="mapItem">
    <c:set var= "esisteGia" value="false" />
    
    	<!-- nome dipendente -->
    	<h3>${mapItem.key.stringa}</h3> 
    	
    	<!-- valutazione dei risultati -->
	    <table>
	    <tr>
	        <th colspan="5"> <h3>VALUTAZIONE DEI RISULTATI</h3> </th>
	    </tr>   
	    
	    <tr>
	        <th colspan="2">Obiettivo / Azione</th>
	        <th>Peso</th>
	        <th>Valutazione</th>
	        <th>Punteggio</th>
	        <!--  
	        	<th>Annotazioni</th>
	        -->
	    </tr>
	
	    <tbody>
	    <c:forEach items="${mapItem.value}" var="assegnazione" varStatus ="status">
	         <tr>
	         	<td colspan="2"> 
	         	<a href="<spring:url value="/dirigenteValutazioneAzioneComparto/${assegnazione.id}/editValutazione" htmlEscape="true" />">MODIFICA VALUTAZIONE</a>
	                 ${assegnazione.azione.obiettivo.descrizione} / ${assegnazione.azione.descrizione}
	                 </br>
	                 (scadenza azione: <fmt:formatDate value="${assegnazione.azione.scadenza}" pattern="dd/MM/yyyy"/>)
	                 </br>
	                 <a href="<spring:url value="/dirigenteAssegn/${assegnazione.id}/edit" htmlEscape="true" />">MODIFICA ASSEGNAZIONE </a>
	                 
	             </td>
	             <td> 
	                 ${assegnazione.peso}
	             </td>
	             <td> 
	                 ${assegnazione.valutazione}
	             </td>
	             <td> 
	                 ${assegnazione.punteggio}
	             </td>
	             
	         </tr>
	         
	    </c:forEach>
	    
	    <!-- totale dei risultati -->
	 		<tr>
	 			<td colspan="2">
	         		totale risultati
	         	</td>
	         	<td>
	         		${mapItem.key.pesoAssegnazioni}
	         	</td>
	         	<td> 
	                 
	            </td>
	            <td> 
	                ${mapItem.key.punteggioAssegnazioni} 
	            </td>
	            
	         </tr>
	    </tbody>
	    </table>       
    <!-- valutazione delle prestazioni -->
    
	<c:forEach items="${mapDipendentiValutazioneComparto}" var="map2Item">
    	<c:if test="${map2Item.key == mapItem.key}">
    	
    	<!-- comportamenti organizzativi -->
	    <table>
	    
	    <tr>
	        <th colspan="5"> <h3>Valutazione dei comportamenti organizzativi</h3> </th>
	    </tr>   
	    
	    <tr>
	        <th colspan="2">Indicatore</th>
	        <th>Peso</th>
	        <th>Valutazione</th>
	        <th>Punteggio</th>
	        
	    </tr>
	
	    <tbody>
	    <!-- itero sulla lista Valutazioni anche se dovrebbe contenerne solo 1  -->
	    <c:forEach items="${map2Item.value}" var="valutazioneComparto" varStatus ="status">
	    
	    	<c:set var= "esisteGia" value="true" />
	    	
	        <tr>
	         	<td colspan="2">  
	         		<a href="<spring:url value="/dirigenteValutazioneComparto/${valutazioneComparto.id}/edit" htmlEscape="true" />">MODIFICA </a>   
	            	<p>Competenza nello svolgimento delle attività</p>
	            </td>
	         	<td>${valutazioneComparto.competSvolgAttivAss}</td>
	            <td>${valutazioneComparto.competSvolgAttivVal}</td>
	            <td>${valutazioneComparto.competSvolgAttivPunteggio}</td>	             
	        </tr>
	         
	        <tr>
	         	<td colspan="2"><p>Capacità di adattamento al contesto lavorativo</p></td>
	         	<td>${valutazioneComparto.adattContextLavAss}</td>
	             <td>${valutazioneComparto.adattContextLavVal}</td>
	             <td>${valutazioneComparto.adattContextLavPunteggio}</td>	             
	         </tr>
	         
	         <tr>
	         	<td colspan="2"><p>Capacità di assolvere ai compiti assegnati</p></td>
	         	<td>${valutazioneComparto.capacAssolvCompitiAss}</td>
	            <td>${valutazioneComparto.capacAssolvCompitiVal}</td>
	            <td>${valutazioneComparto.capacAssolvCompitiPunteggio}</td>
	         </tr>
	         
	         <c:if test="${!map2Item.key.catAB}">
		         <tr>
		         	<td colspan="2"><p> Capacità di promuovere e gestire l'innovazione</p></td>
		         	<td>${valutazioneComparto.innovazAss}</td>
		            <td>${valutazioneComparto.innovazVal}</td>
		            <td>${valutazioneComparto.innovazPunteggio}</td>
		         </tr>
		         <tr>
		         	<td colspan="2"><p>Capacità di organizzazione del lavoro</p></td>
		         	<td>${valutazioneComparto.orgLavAss}</td>
		            <td>${valutazioneComparto.orgLavVal}</td>
		            <td>${valutazioneComparto.orgLavPunteggio}</td>		             
		         </tr>
	    	</c:if>
	    	
	 		<tr>
		 		<td colspan="2">totale comportamenti organizzativi</td>
	 			<td>${valutazioneComparto.totPeso}</td>
	         	<td></td>
	         	<td>${valutazioneComparto.totPunteggio}</td>
	        </tr>
	        
	        <tr>
		        <th colspan="5"> <h3>Valutazione finale &nbsp; &nbsp; anno: ${valutazioneComparto.anno}</h3> </th>
		    </tr> 
	        <tr>
		 		<td colspan="2">totale generale</td>
	 			<td>
	         		 ${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso}
	         		 <c:if test="${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso ne 100}">
	                 	<font color="red"> rivedi i pesi !!!</font>
	                 </c:if>
	         	</td>
	         	<td></td>
	         	<td>${mapItem.key.punteggioAssegnazioni + valutazioneComparto.totPunteggio}</td>
		       
	        </tr>
	        
	    </c:forEach>
	    
	    <c:if test="${!esisteGia}">
	    <p><a href="<spring:url value="/dirigenteValutazioneComparto/new/${mapItem.key.idPersona}/${anno}/${idIncarico}" htmlEscape="true" />">Aggiungi Comportamenti Organizzativi per ${mapItem.key.stringa} </a>
	    </p>           
	    
	   </c:if>
			</tbody>
	    </table>       
    	</c:if>
    	
	
	</c:forEach>
	<br />
	<hr />          
	</c:forEach>
	
</section>
</div>
</article>       
           <!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:modifyValutazioneCompartoManager.jsp 2019/10/17] </td>
	</tr>
  </table>
</div>
    
</body>
</html>
