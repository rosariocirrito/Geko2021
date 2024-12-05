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
<h1 id="titolo-lista">Modifica Obiettivi Comparto per <em>${struttura} </em>- anno ${anno}</h1>
<hr />    
<h4>Responsabile: ${responsabile}</h4> 

<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>

<table class="table table-bordered table-striped">
		<colgroup>
		<col class="name">
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
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
	    	<span class="label label-info">
	    	<a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
	    	</span>
	    	<br />
	    	
	    </c:forEach>
	    <!--  
	    <span class="label label-warning">
            <a href="<spring:url value="/dirigenteObjAssegn/new/${obj.idObiettivo}/${idIncarico}" htmlEscape="true" />">AGGIUNGI DIP.</a>
        </span>
	    </td>
		-->

    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> <br />    
    </c:if>
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    <td>${obj.statoApprov} </td>
    <td>${obj.peso}
    <c:if test="${obj.pesoAzioni > 100}"><span class="warning"> attnz. peso Azioni= ${obj.pesoAzioni} maggiore di 100!</span></c:if>
    <c:if test="${obj.pesoAzioni < 100}"><span class="warning"> attnz. peso Azioni= ${obj.pesoAzioni} minore di 100!</span></c:if>
   
    </td> 
	
	</tr>
	
	
	
	
	<tr>
	    <td colspan="5">    
	    	<table class="table table-bordered table-striped">
		<colgroup>
			<col class="name">
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="desrc">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi</th>
	    	<th scope= "col" >azioni</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >indicatore previsto</th>
	    	<th scope= "col" >valore obiettivo</th>
	        <th scope= "col" >scadenza</th>
	        <th scope= "col" >peso</th>
	        
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
			<!-- Comandi -->
			<td>
			<c:forEach items="${act.guiCommands}" var="actCmd" varStatus ="status">
				<span class="label label-warning">
			    <a href="<spring:url value="  ${actCmd.uri}  " htmlEscape="true" />">${actCmd.label}</a>
			    </span>
			    <br />
		    </c:forEach>
		     
		    <span class="label label-info">
	            <a href="<spring:url value="/dirigenteAssegn/new/${act.idAzione}" htmlEscape="true" />">AGGIUNGI DIP.</a>
	        </span>
		    </td>
			
			<!-- Descrizione -->
	        <td>
	        	<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
				        ${act.descrizioneProp}
			    </c:if>
	        	${act.denominazione} - ${act.descrizione} <c:if test="${act.tassativa}">- AZIONE E SCADENZA TASSATIVE</c:if>
	        </td>
	        
	        <!-- Note -->
	        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
	        
	         <!-- Indicatore -->    
	        <td>${act.indicatore}</td>
	        
	         <!-- Valore obiettivo -->
	        <td>${act.prodotti}</td>
	        
	         <!-- Scadenza -->
	        <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
	        	<c:if test="${act.tassativa}"> TASSATIVA</c:if>
	        </td>
	         <!-- peso -->
	        <td>${act.peso}</td>
	        </tr>
	        
	         <!-- Dipendenti -->
	        
	        <c:forEach items="${act.assegnazioni}" var="assegn">
	        	<tr>
		        <td> 
		        
		        </td>
		        <td colspan="6">
		        
	           	<span class="label label-error">
	            	<a href="<spring:url value="/dirigenteAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA/CANCELLA ASSEGNAZIONE</a>
	           	</span>
	                ${assegn.opPersonaFisica.stringa} - ${assegn.peso}%	                	           
	         </td>
        	</tr>
        	</c:forEach>
    </c:forEach>
    </tbody>
</table>   
	    </td>
    </tr>
</c:forEach>
</table>
                

</section>
</article>  
    
<!-- Footer -------------------------->

<p><span class="label label-info">
<a href="<spring:url value="/dirigenteObj/newComparto/${anno}/${idIncarico}" htmlEscape="true" />">Crea obiettivo di gruppo o individuale</a>
</span>

<p><span class="label label-warning"><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></span>
    Ge.Ko. by ing. R. Cirrito [view: modifyObjCompartoManager.jsp 2019/09/16]
    
</div>
</div>  
</div>  
</body>
</html>
