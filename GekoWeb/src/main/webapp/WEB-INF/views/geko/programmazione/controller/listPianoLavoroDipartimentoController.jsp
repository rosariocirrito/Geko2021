<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Piano di Lavoro del Dipartimento</title>
    <link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
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
<div class="container" >
<div class="row">
<div class="col-sm-12 col"> 

<article>
    
<h2>Piano di Lavoro per <em>${dipartimento} </em>- anno ${anno}</h2> 
<hr />  

<!--  c:if test="${empty listIncarichiDept}" -->
<c:if test="${empty mapDipartimentoAssegnazioni}">
     <p>Nessun incarico trovato.</p>
</c:if>


<!-- 1. mappa Map<Incarico,Map<OpPersonaFisica,List<AzioneAssegnazione>>> -->
<c:forEach items="${mapDipartimentoAssegnazioni}" var="mapItem" varStatus ="status">

	<!-- mapItem.key è l'incarico -->
	<hgroup >            
	<h3 >Struttura  ${mapItem.key.denominazioneStruttura} </h3>
	<h4 > Responsabile  ${mapItem.key.responsabile}  
	con incarico dal <fmt:formatDate value="${mapItem.key.dataInizio}" pattern="dd/MM/yyyy"/>
	al <fmt:formatDate value="${mapItem.key.dataFine}" pattern="dd/MM/yyyy"/>
	<c:if test="${mapItem.key.interim}">
	     AD INTERIM
	</c:if>
	</h4>
 
	<h4>Competenze</h4>  
	<h5>${mapItem.key.competenzeStruttura}</h5>  
	</hgroup>
	
	<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >priorità</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             
	
	<!-- 2. mapItem.key è l'incarico -->
	<c:forEach items="${mapItem.key.obiettivi}" var="obj" varStatus ="status">
		<c:choose>
			<c:when test="${obj.tipo == 'OBIETTIVO'}"><tr class="success"></c:when>
			<c:when test="${obj.tipo == 'ISTITUZ_MAGG_RILEV'}"><tr class="info"></c:when>
			<c:when test="${obj.tipo == 'AMMIN_MANUALE_SUPP' }"><tr class="warning"></c:when>
		</c:choose>
	
			<!-- colonna descrizione obiettivo -->
		    <td>     
		    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
			    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
		    </c:if>
		    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
		    ${obj.codice} -  ${obj.descrizione} 
		    </td>
		    
		    <!-- colonna note -->
    		<td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    		
    		<!-- colonna priorità -->
		    <td>   
		    <c:if test="${obj.tipo == 'OBIETTIVO'}">  
			    <c:if test="${obj.priorita == 'ALTA'}"> ALTA</c:if>
			    <c:if test="${obj.priorita == 'BASSA'}"> BASSA</c:if>
			</c:if>    
		    </td>
		    
		    <!-- colonna stato -->
		    <td>${obj.statoApprov} </td>
		    
		    <!-- colonna peso -->
			<td><c:if test= "${obj.peso >0}"> ${obj.peso} </c:if></td> 
		</tr>
		
		<!-- tabella azioni -->
		<tr>
	    	<td colspan="5">
	    		<%@ include file="/WEB-INF/views/common/tableActionsCompatta.jsp" %>
	    	</td>
	    </tr>
    
	</c:forEach><!-- fine 2. loop sugli obiettivi dell'incarico -->
    
    </table>
    
    
    <!-- 3. itero Map<OpPersonaFisica,List<AzioneAssegnazione> -->
	<c:forEach items="${mapItem.value}" var="mapItem2" varStatus ="status2">
	<hr />  
	<h4> CARICHI DI LAVORO per ${mapItem2.key.stringa} </h4>
	    
    	 <!-- carico lavoro dipendente -->
	    <table class="table table-bordered table-striped">
	    <colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
		</colgroup> 
	      
	    <tr>
	        <th scope= "col">Obiettivo</th>
	        <th scope= "col">Azione</th>
	        <th scope= "col">Assegnazione</th>
	        <th scope= "col">Peso</th>
	    </tr>
	    <tbody>
	     
	    
	    <!-- 4. mapItem2.value List<AzioneAssegnazione> -->
	    <c:forEach items="${mapItem2.value}" var="assegnazione" varStatus ="status">
	         <tr>
	         	<td > 
	                 ${assegnazione.azione.obiettivo.descrizione}  </td>
	            <td > 
	                 ${assegnazione.azione.descrizione} 
	                 </br>
	                 (scadenza azione: <fmt:formatDate value="${assegnazione.azione.scadenza}" pattern="dd/MM/yyyy"/>)
	                 </td>
	            <td > da: <fmt:formatDate value="${assegnazione.dataInizio}" pattern="dd/MM/yyyy"/>
	                 </br>
	                  a: <fmt:formatDate value="${assegnazione.dataFine}" pattern="dd/MM/yyyy"/>
	             </td>
	             <td> 
	             <c:if test="${assegnazione.azione.obiettivo.tipo == 'OBIETTIVO' && assegnazione.azione.obiettivo.anno>2015}">
	                 ${assegnazione.peso}
	             </c:if>    
	             <c:if test="${assegnazione.azione.obiettivo.anno<2016}">
	                 ${assegnazione.peso}
	             </c:if>
	             </td>
	         </tr>
			
			</c:forEach> <!-- 4. fine itero sulle assegnazioni del dipendente -->
	    	<!-- totale dei risultati -->
	 		<tr>
	 			<td colspan="3">totale pesi</td>
	         	<td>${mapItem2.key.pesoAssegnazioni}
	         	
	         	 <c:if test="${mapItem2.key.pesoAssegnazioni != 70}">
	                 <br /> <p style="color:red;">attenzione il totale pesi è diverso da 70 !</p>
	             </c:if>
	         	</td>
	         	
	         </tr>
	    </tbody>
	            
	  
   		
   	</table>
   	
   	</c:forEach>	<!-- 3. fine iterazioni sui dipendenti dell'incarico -->
    
    <hr />
    


    
    <hr />                       
</c:forEach> <!-- 1. fine loop incarichi -->
</article>      
                
<!-- Footer -------------------------->
<div id="footer">      
<table class="footer" >
    <tr>
	    <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
	    Ge.Ko. by Regione Siciliana - Segreteria Generale - 
	    ing. r. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listPianoLavoroDipartimentoController.jsp 14/01/2016] </td>
	</tr>
</table>
</div>
</div>
</div>    
</div>
</body>
  
</html>
