<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Lista Programmazione Dipendenti della Struttura/SubStrutture</title>
    <link href=<spring:url value="/resources/bootstrap336/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
    <style>
		.row{
			margin-top:16px;
			margin-left:16px;
			background-color:lightgray;
		}
		.col{
			background-color:lightblue;
			padding:8px;
			border:2px solid darkgray;
		}
	</style>
</head>
    
<body>
   
    
    
<article>
<div class="container" >
<div class="row">
<div class="col-lg-12 col"> 

 
<h1 id="titolo-lista">Programmazione Dipendenti per <em>${struttura.denominazione} anno: ${anno}</em></h1>   

<hr />      
<c:if test="${empty mapDipendentiAssegnazioni}">
    <p>Nessuna associazione dipendente->azione trovata !</p>
    <p>Seleziona gestisci programmazione e aggiungi i dipendenti alle singole azioni. Poi puoi tornare a questa opzione per modificare i pesi!</p>
</c:if>

    
<!-- itero sulla mappa assegnazioni <dipendente, List<Azioneassegnazione> -->
<c:forEach items="${mapDipendentiAssegnazioni}" var="mapItem">
 <c:set var= "esisteGia" value="false" />
   	<!-- nome dipendente -->
	<h3>${mapItem.key.stringa}</h3> 
	
  	<!-- tabella assegnazioni azioni -->
    <table class="table table-bordered table-striped">
	    <colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
		</colgroup> 
		
		<thead> 
	    <tr>
	        <th colspan="3">Performance operativa</th>
	    </tr>  
	    <tr>
	        <th>Obiettivo <span class="label label-info"><a href="<spring:url value="/dirigenteObj/newComparto/${anno}/${idIncarico}" htmlEscape="true" />">Crea obiettivo di gruppo o individuale</a></span>
	        </th>
	        <th>Azione</th>
	        <th>Peso </th>
	    </tr>
		</thead>
		
	    <tbody>
	    <!-- itero sulla lista assegnazioni  -->
	    <c:forEach items="${mapItem.value}" var="assegnazione" varStatus ="status">
	         <tr>
	         	<td >${assegnazione.azione.obiettivo.descrizione}  </td>
	            <td >${assegnazione.azione.descrizione} 	                 
	                 <br />
	                 (scadenza: <fmt:formatDate value="${assegnazione.azione.scadenza}" pattern="dd/MM/yyyy"/>)
                 </td>
	             <td>	             
	             	<a href="<spring:url value="/dirigenteAssegn/${assegnazione.id}/editComparto" htmlEscape="true" />">MODIFICA</a>
	                 ${assegnazione.peso}
	             </td>
	         </tr>	         
	    </c:forEach> <!-- fine iterazione sulla lista assegnazioni -->
	    
	    <!-- totale dei risultati accumulati sulla persona fisica-->
 		<tr>
 			<td colspan="2">totale pesi performance organizzativa</td>
         	<td>${mapItem.key.pesoAssegnazioni} / ${mapItem.key.pesoAzioniOK}
         		 <c:if test="${mapItem.key.pesoAssegnazioni != mapItem.key.pesoAzioniOK}">
         		 	<br /> <p style="color:red;">attenzione il totale pesi è diverso da quello max della categoria!</p>
         		 </c:if>
         	</td>
         </tr>
	     </tbody>
	    </table> <!-- tabella assegnazioni azioni -->   
	         
	         
    	<!-- itero sulla mappa valutazioni <dipendente, ListValutazioneComparto -->
		<c:forEach items="${mapDipendentiValutazioneComparto}" var="map2Item">
		<!-- se trovo il dipendente -->
    	<c:if test="${map2Item.key == mapItem.key}">
    	
    	<!-- comportamenti organizzativi -->
	    <table class="table table-bordered table-striped">
	    <colgroup>
			<col class="desrc">
			<col class="name">
		</colgroup> 
		
	    <tbody>
	    <!-- itero sulla lista Valutazioni anche se dovrebbe contenerne solo 1  -->
	    <c:forEach items="${map2Item.value}" var="valutazioneComparto" varStatus ="status">
		    <c:set var= "esisteGia" value="true" />
		    <thead>  
		    <tr>
		        <th colspan="2">Comportamenti organizzativi </th>
		    </tr> 
		    <tr>
		    	<th>Indicatore</th>
		        <th>Peso <a href="<spring:url value="/dirigenteCompOrg/${valutazioneComparto.id}/editPesiComportOrgan" htmlEscape="true" />">MODIFICA </a>
		           </th>
		    </tr>
			</thead>
	        <tr>
	            <td><p>Competenza nello svolgimento delle attività</p></td>
	         	<td>${valutazioneComparto.competSvolgAttivAss}</td>
			</tr>
	         
	        <tr>
	         	<td><p>Capacità di adattamento al contesto lavorativo</p></td>
	         	<td>${valutazioneComparto.adattContextLavAss}</td>
	        </tr>
	         
	        <tr>
	         	<td><p>Capacità di assolvere ai compiti assegnati</p></td>
	         	<td>${valutazioneComparto.capacAssolvCompitiAss}</td>
	        </tr>
	         
	        <c:if test="${!map2Item.key.catAB}">
		        <tr>
		         	<td><p> Capacità di promuovere e gestire l'innovazione</p></td>
		         	<td>${valutazioneComparto.innovazAss}</td>
		        </tr>
		        <tr>
		         	<td><p> Capacità di organizzazione del lavoro</p></td>
		         	<td>${valutazioneComparto.orgLavAss}</td>
		        </tr>
	    	</c:if>
	    	
	 		<tr>
		 		<td>totale comportamenti organizzativi</td>
	 			<td>${valutazioneComparto.totPeso}</td>	         		         	
	        </tr>
	        
	        <tr>
		 		<td>totale generale</td>
	 			<td>
	 				${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso}
	         		 <c:if test="${mapItem.key.pesoAssegnazioni + valutazioneComparto.totPeso ne 100}">
	                 	<font color="red"> rivedi i pesi !!!</font>
	                 </c:if>
	         	</td>	         	
	        </tr>
        </c:forEach> <!-- fine iterazione lista ValutazioniComparto -->
	        
	    <c:if test="${!esisteGia}">
	    <p><a href="<spring:url value="/dirigenteCompOrg/new/${mapItem.key.idPersona}/${anno}/${idIncarico}" htmlEscape="true" />">Aggiungi Comportamenti Organizzativi per ${mapItem.key.stringa} </a>
	   </p>           
	    
	   </c:if>
	   
			</tbody>
	    </table>    <!-- chiudi tabella comportam organ -->   
    	</c:if>
    	
	
	</c:forEach> <!-- mappa 2 dip, ListaValutaz -->
	       
	    
	
	<br />
	<hr />    

</c:forEach> <!-- per ogni dipendente -->

<!-- Footer -------------------------->
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></span>
    Ge.Ko. by ing. R. Cirrito [view:modifyProgrammazioneCompartoManager.jsp 2020/04/27] 

</div>
</div>    
</div>


</article>       
          
    
</body>
</html>

