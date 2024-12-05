<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Modifica Obiettivi della Struttura del Dirigente</title>
    <link href="<spring:url value="/resources/bootstrap336/css/bootstrap.css" htmlEscape="true" />" rel="stylesheet"/>
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
<section>
  
<h3>Modifica Programmazione Obiettivi per incarico <em>${incarico.stringa} </em>- anno ${anno}</h3>    

<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>

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
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">


		<tr class="success">
		<td>
		<c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
			<span class="label label-success">
			    <a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
		    </span>
	    	  </br>
	    </c:forEach>
	
	
	</td>

    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
    </c:if>
    
     
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    <td>${obj.statoApprov} </td>
	<td><c:if test= "${obj.peso >0}"> ${obj.peso} </c:if></td> 
	<td><c:if test= "${obj.apicale}"> ${obj.pesoApicale} </c:if></td>
	</tr>
	<tr>
    <td colspan="5">
    
    <%@ include file="/WEB-INF/views/geko/controller/tableActionsCtrlMod.jsp" %>
    </td>
    </tr>
</c:forEach>
</table>   
<hr />  

		<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
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
				<td>${incarico.totPesoObiettivi}</td>
			</tr>		
			<tr>
				<td>Totale programmato Comportamento Organizzativo (= 30)</td>
				<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}</td>
			</tr>
			
			</tbody>
			</table>
		
	
	</c:forEach> <!-- inc.valutazioni -->
	<c:if test="${empty incarico.valutazioni}">
	<p><a href=\"../../../controllerCompOrg/new/${incarico.idIncarico}/${anno}>Aggiungi sezione Comportamenti Organizzativi per ${incarico.responsabile} </a></p>
	
	</c:if>
</section>

</article>  
    
     <!-- Footer -------------------------->
  <%@ include file="/WEB-INF/views/common/footer.jsp" %>
  <div class="row">
		<div class="col-lg-12 col"> 
			view: [${pageName}]
		</div>
	</div>
</div>
</div>  
</div>  
</body>
</html>
