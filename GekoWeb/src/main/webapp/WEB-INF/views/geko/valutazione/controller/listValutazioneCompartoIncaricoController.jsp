<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Lista Valutazione Dipendenti della Struttura/SubStrutture</title>
    <link href="../../../resources/css/lista.css" rel="stylesheet" />
</head>
    
<body>
    
    
<article>
    <div id="wrapper">
    <section id="obj-sezione">  
    <div class="breakafter"> 
        <h1 id="titolo-lista">Valutazione Dipendenti per <em>${struttura} anno: ${anno}</em></h1>   

        <hr />      
        <c:if test="${empty mapDipendentiAssegnazioni}">
            <p>Nessuna assegnazione trovata !</p>
        </c:if>
    </div>
    <!-- itero sulle assegnazioni -->
    <c:forEach items="${mapDipendentiAssegnazioni}" var="mapItem">
    <div class="breakafter">
    	<!-- nome dipendente -->
   		<h3>${mapItem.key.stringa}</h3> 
   		
   		<!-- valutazione dei risultati -->
	    <table>
	    
	    <tr>
	        <th colspan="6"> <h3>VALUTAZIONE DEI RISULTATI &nbsp; anno: ${anno}</h3> </th>
	    </tr>   
	    <tr>
	        <th colspan="2">Obiettivo / Azione</th>
	        <th>Peso</th>
	        <th>Valutazione</th>
	        <th>Punteggio</th>
	        <th>Annotazioni</th>
	    </tr>
	
	    <tbody>
	    <c:forEach items="${mapItem.value}" var="assegnazione" varStatus ="status">
	         <tr>
	         	<td colspan="2"> 
	                 ${assegnazione.azione.obiettivo.descrizione} /
	                 ${assegnazione.azione.descrizione} 
	                 </br>
	                 (scadenza azione: <fmt:formatDate value="${assegnazione.azione.scadenza}" pattern="dd/MM/yyyy"/>)
	                 </br>
	                 [assegnazione da: <fmt:formatDate value="${assegnazione.dataInizio}" pattern="dd/MM/yyyy"/>
	                 &nbsp; a: <fmt:formatDate value="${assegnazione.dataFine}" pattern="dd/MM/yyyy"/>]
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
	             <td> 
	                 ${assegnazione.annotazioni}
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
	            <td> 
	                 <c:if test="${mapItem.key.punteggioAssegnazioni ge 49}">
	                 	punteggio risultati nella fascia 49 - 70 (->100%)
	                 </c:if>
	                 <c:if test="${(mapItem.key.punteggioAssegnazioni ge 35) && (mapItem.key.punteggioAssegnazioni lt 49)}">
	                 	punteggio risultati nella fascia 35 - 48 (->85%)
	                 </c:if>
	                 <c:if test="${mapItem.key.punteggioAssegnazioni lt 35}">
	                 	punteggio risultati inferiore a 35 (->70%)
	                 </c:if>
	            </td>
	         </tr>
	    </tbody>
	    </table>           
	    
	<!-- valutazione delle prestazioni -->
	<c:forEach items="${mapDipendentiValutazioneComparto}" var="map2Item">
    	<c:if test="${map2Item.key == mapItem.key}">
	    <table>
	    
	    <tr>
	        <th colspan="6"> <h3>Valutazione delle prestazioni &nbsp; anno: ${anno}</h3> </th>
	    </tr>   
	    
	    <tr>
	        <th colspan="2">Indicatore</th>
	        <th>Peso</th>
	        <th>Valutazione</th>
	        <th>Punteggio</th>
	        <th>Annotazioni</th>
	    </tr>
	
	    <tbody>
	    <c:forEach items="${map2Item.value}" var="valutazioneComparto" varStatus ="status">
	    	
	         <tr>
	         	<td colspan="2"> 
	                <p> Capacità di gestione della complessità e delle difficoltà</p>
	            </td>
	         	<td> 
	                 ${valutazioneComparto.complessDifficoltaAss}
	             </td>
	             <td> 
	                 ${valutazioneComparto.complessDifficoltaVal}
	             </td>
	             <td> 
	                 ${valutazioneComparto.complessDifficPunteggio}
	             </td>
	             <td> 
	                 ${valutazioneComparto.annot1}
	             </td>
	         </tr>
	         
	         <tr>
	         	<td colspan="2"> 
	                <p> Competenze tecnico-professionali</p>
	            </td>
	         	<td> 
	                 ${valutazioneComparto.competProfAss}
	             </td>
	             <td> 
	                 ${valutazioneComparto.competProfVal}
	             </td>
	             <td> 
	                 ${valutazioneComparto.competProfPunteggio}
	             </td>
	             <td> 
	                 ${valutazioneComparto.annot2}
	             </td>
	         </tr>
	         
	         <tr>
	         	<td colspan="2"> 
	                <p> Capacità di promuovere e gestire l'innovazione</p>
	            </td>
	         	<td> 
	                 ${valutazioneComparto.innovazAss}
	             </td>
	             <td> 
	                 ${valutazioneComparto.innovazVal}
	             </td>
	             <td> 
	                 ${valutazioneComparto.innovazPunteggio}
	             </td>
	             <td> 
	                 ${valutazioneComparto.annot3}
	             </td>
	         </tr>
	         
	    
	 		<tr>
		 		<td colspan="2"> 
		             totale prestazioni  
	            </td>
	 			<td>
	         		${valutazioneComparto.totPeso}
	         	</td>
	         	<td>
	         		
	         	</td>
	         	<td> 
	                 ${valutazioneComparto.totPunteggio}
	            </td>
		        <td> 
		        	 <c:if test="${valutazioneComparto.totPunteggio ge 21}">
	                 	punteggio prestazioni nella fascia 21 - 30 (->100%)
	                 </c:if>
	                 <c:if test="${(valutazioneComparto.totPunteggio ge 15) && (valutazioneComparto.totPunteggio lt 20)}">
	                 	punteggio prestazioni nella fascia 15 - 20 (->85%)
	                 </c:if>
	                 <c:if test="${valutazioneComparto.totPunteggio lt 15}">
	                 	punteggio prestazioni inferiore a 15 (->70%)
	                 </c:if>
	            </td>
	        </tr>
	        
	        <tr>
		        <th colspan="6"> <h3>Valutazione finale &nbsp; anno: ${anno}</h3> </th>
		    </tr> 
	        <tr>
		 		<td colspan="2"> 
		             totale generale  
	            </td>
	 			<td>
	         		 ${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso}
	         	</td>
	         	<td>
	         		
	         	</td>
	         	<td> 
	                 ${mapItem.key.punteggioAssegnazioni + valutazioneComparto.totPunteggio}
	            </td>
		        <td> 
	                 
	            </td>
	        </tr>
	        <tr>
		 		<td colspan="4"> 
		             <h5>Percentuale indennità spettante ==></h5> 
	            </td>
	 			
	         	<td> 
	         	
	         	<c:choose>
				    <c:when test="${(mapItem.key.punteggioAssegnazioni ge 49) && (valutazioneComparto.totPunteggio ge 21)}">
				       100%
				    </c:when>
				    <c:when test="${(mapItem.key.punteggioAssegnazioni ge 35) && (valutazioneComparto.totPunteggio ge 15)}">
				        85%
				    </c:when>
				    <c:otherwise>
				        70%
				    </c:otherwise>
				</c:choose>

	            </td>
		        <td> 
	                 
	            </td>
	        </tr>
	        <tr>
		 		<td colspan="3"> 
		             <p>&nbsp; &nbsp; &nbsp; &nbsp; Data &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;Firma Dirigente Responsabile 
		             ${mapItem2.key.opPersonaGiuridica.responsabile.nome} &nbsp; 
		             ${mapItem2.key.opPersonaGiuridica.responsabile.cognome}</p> 
		             <p>&nbsp;</p>
		             <p>___/___/___ &nbsp; &nbsp; ________________________________________________ </p>
	            </td>
	            <td colspan="3"> 
		             <p>Firma Dipendente &nbsp;
		             ${mapItem2.key.nome} &nbsp; ${mapItem2.key.cognome}</p> 
		             <p>&nbsp;</p>
		             <p> ________________________________________________ </p>
	            </td>
	        </tr>    
	        </c:forEach>
	    </tbody>
	    </table>       
    	</c:if>
    	
	
	</c:forEach>
	<br />
	<hr />    
	</div>
</c:forEach>
</section>
</div>
</article>       
           <!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
<tr>
	<td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a> [view:listValutazioneCompartoIncaricoController.jsp 20/06/2017] </td>
</tr>
  </table>
</div>
    
</body>
</html>
