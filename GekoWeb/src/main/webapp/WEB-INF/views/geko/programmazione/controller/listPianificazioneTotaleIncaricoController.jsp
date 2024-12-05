<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Lista Programmazione Obiettivi del Dipartimento</title>
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
<div class="col-lg-12 col"> 

<article>
<section > 
    
<h2>Programmazione Obiettivi per <em>${dipartimento} </em>- anno ${anno}</h2> 
<hr />  


<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>


<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">

<hgroup >            
<h4 >Struttura  ${incarico.denominazioneStruttura} </h4>
<h4 class="responsabile"> Responsabile  ${incarico.responsabile}  </h4>
<h4>con incarico dal <fmt:formatDate value="${incarico.dataInizio}" pattern="dd/MM/yyyy"/>
al <fmt:formatDate value="${incarico.dataFine}" pattern="dd/MM/yyyy"/>
<c:if test="${incarico.interim}">
     AD INTERIM
</c:if>
</h4>
</hgroup>
<c:forEach items="${incarico.obiettivi}" var="obj" varStatus ="status">

<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			<col class="name">
		</colgroup>   
    
    <thead>
		<tr>
			
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >
				<c:if test= "${!obj.apicale}">peso </c:if>
				<c:if test= "${obj.apicale}"> peso apicale </c:if>	
	        </th>
	        
	    </tr>  
	</thead>           
	  
 	<tbody>
	<tr class="success">			
	    <td>     
	    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
		    Descr_Proposta - <em> ${obj.descrizioneProp} </em> 
		    <br />    
	    </c:if>
	    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
	    ${obj.codice} -  ${obj.descrizione} 
	    </td>
	    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
	    
	    <td>${obj.statoApprov} </td>
		<td>
			<c:if test= "${!obj.apicale}"> ${obj.peso} </c:if>
			<c:if test= "${obj.apicale}"> ${obj.pesoApicale} </c:if>
		</td>
	</tr>
	
	<tr>
	    <td colspan="5">
	    <table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead style="background:lightgray;"> 
		<tr>
	    	<th scope= "col" >azioni</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >indicatore previsto</th>
	    	<th scope= "col" >valore obiettivo</th>
	        <th scope= "col" >
	        	<c:if test="${!obj.apicale}">scadenza </c:if>
				<c:if test="${obj.apicale}" > scadenza apicale </c:if>
			</th>
	        <th scope= "col" >
	        	<c:if test= "${!obj.apicale}">peso </c:if>
				<c:if test= "${obj.apicale}"> peso apicale </c:if>	
	        
	        </th>
	        
	        
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
        <td>
        	<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
			        ${act.descrizioneProp}
		    </c:if>
        	${act.denominazione} - ${act.descrizione} <c:if test="${act.tassativa}">- AZIONE E SCADENZA TASSATIVE</c:if>
        </td>
        
        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
            
        <td>${act.indicatore}</td>
        <td>${act.prodotti}</td>
        <td>
	        <c:if test="${!act.obiettivo.apicale}"><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/><c:if test="${act.tassativa}">- TASSATIVA</c:if></c:if>
	        <c:if test="${act.obiettivo.apicale}"><fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/><c:if test="${act.tassativa}">- TASSATIVA</c:if></c:if>        
        </td>
        <td>
	        <c:if test="${!act.obiettivo.apicale}">${act.peso}</c:if>
	        <c:if test="${act.obiettivo.apicale}">${act.pesoApicale}</c:if>
        </td>
        
        </tr>
        
    </c:forEach>
    </tbody>
</table>   
	    </td>
    </tr>

</table>
</c:forEach>
<hr />                       
<hr /> 

		<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
			<!-- Tabella dei comportamenti organizzativi -->
			<table>   
			<tbody>
			
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
				<td>${incarico.totPesoObiettivi}</td>
			</tr>		
			<tr>
				<td>Totale programmato Comportamento Organizzativo (= 30)</td>
				<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}</td>
			</tr>
			
			</tbody>
			</table>
		
	
	</c:forEach> <!-- inc.valutazioni -->
</c:forEach>
</section>
</article>      
                
<!-- Footer -------------------------->
<span class="label label-warning"><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a></span>
  [view:listPianificazioneTotaleIncaricoController.jsp 2019/04/26] 
	
    
</div>
</div>
</div>

</body>
  
</html>
