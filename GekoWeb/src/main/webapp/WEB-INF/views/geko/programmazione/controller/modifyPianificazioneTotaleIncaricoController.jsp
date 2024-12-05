<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<!-- <meta charset="ISO-8859-1" />  -->
    <meta charset="UTF-8" />
    <title>Modifica Programmazione Obiettivi del Dipartimento</title>
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
<div class="container" >
<div class="row">
<div class="col-lg-12 col"> 

<article>
<section > 
    
<h2>Programmazione Obiettivi per <em>${dipartimento} </em>- anno ${anno}</h2> 
<hr />  


<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>


<c:forEach items="${listIncarichiDept}" var="inc" varStatus ="status">

<hgroup >            
<h4 >Struttura  ${inc.denominazioneStruttura} </h4>
<h4 class="responsabile"> Responsabile  ${inc.responsabile}  </h4>
<h4>con incarico dal <fmt:formatDate value="${inc.dataInizio}" pattern="dd/MM/yyyy"/>
al <fmt:formatDate value="${inc.dataFine}" pattern="dd/MM/yyyy"/>
<c:if test="${inc.interim}">
     AD INTERIM
</c:if>
</h4>
</hgroup>

	<c:forEach items="${inc.obiettivi}" var="obj" varStatus ="status">
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
				
				<th scope= "col" >comandi</th>
		    	<th scope= "col" >obiettivo</th>
		    	<th scope= "col" >note</th>
		    	<th scope= "col" >stato</th>
		        <th scope= "col" >
					<c:if test= "${obj.peso >0}">peso </c:if>
					<c:if test= "${obj.apicale}"> peso apicale </c:if>	
		        </th>
		    </tr>  
		</thead>      
		
		<tr class="success">
		<td>
		
		<c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
			<span class="label label-success">
			    <a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
		    </span>
	    	  <br />
	    </c:forEach> <!-- obj.guiCommands  -->
		
		</td>

	    <td>     
	    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
		    Descr_Proposta - <em> ${obj.descrizioneProp} </em> <br />    
	    </c:if>
	    
	    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
	    ${obj.codice} -  ${obj.descrizione} 
	    </td>
	    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
	    
	    <td>${obj.statoApprov} </td>
		<td>
			<c:if test= "${obj.peso >0}"> ${obj.peso} </c:if>
			<c:if test= "${obj.apicale}"> ${obj.pesoApicale} </c:if>
		</td>
		</tr>
		<tr>
	    <td colspan="6">
	    
	    <%@ include file="/WEB-INF/views/geko/controller/tableActionsCtrlMod.jsp" %>
	    </td>
	    </tr>

		</table> 
		
		</c:forEach> <!-- inc.obiettivi -->
		
		<hr /> 

		<c:forEach items="${inc.valutazioni}" var="valutazione" >	
			<!-- Tabella dei comportamenti organizzativi -->
			<table>   
			<tbody>
			<tr>
				<th>Comportamenti Organizzativi</th>
				<th><a href="../../../controllerCompOrg/${valutazione.id}/editPesiComportOrgan">Edit</a>Peso Assegnato (0 o intero>=5)</th>
				
			</tr>
			<tr>
		    	<td>Capacità di intercettare, gestire risorse e programmare</td>
				<td>${valutazione.gestRealAss}</td>
			</tr>
			
			<tr>
		    	<td>Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione</td>
				<td>${valutazione.analProgrAss}</td>
			</tr>
			<tr>
		    	<td>Capacita' di valorizzare competenze ed attitudini dei propri collaboratori</td>
				<td>${valutazione.relazCoordAss}</td>
			</tr>
			
			<tr>
		    	<td>Capacità di individuazione del livello di priorità degli interventi da realizzare</td>
				<td>${valutazione.pdlAss}</td>
			</tr>
			<tr>
				<th >Sintesi</th>
				<th>Peso Attribuito</th>
			</tr>
			<tr>
				<td>Totale programmato Performance operativa (= 70)</td>
				<td>${inc.totPesoObiettivi}</td>
			</tr>		
			<tr>
				<td>Totale programmato Comportamento Organizzativo (= 30)</td>
				<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}</td>
			</tr>
			
			</tbody>
			</table>
		
	
	</c:forEach> <!-- inc.valutazioni -->
	
	<c:if test="${empty inc.valutazioni}">
	<p><a href=\"../../../controllerCompOrg/new/${inc.idIncarico}/${anno}>Aggiungi sezione Comportamenti Organizzativi per ${inc.responsabile} </a></p>
	
	</c:if>
	<hr /> 

                      
</c:forEach><!-- listIncarichiDept -->
</section>
</article>      
                
<!-- Footer -------------------------->

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	
</div>
</div>
</div>

</body>
  
</html>





