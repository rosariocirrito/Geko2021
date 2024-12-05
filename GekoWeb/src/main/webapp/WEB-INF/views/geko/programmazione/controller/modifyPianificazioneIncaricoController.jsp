<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Modifica Obiettivi della Struttura del Dirigente</title>
    <link href="<spring:url value="/resources/bootstrap/css/bootstrap.css" htmlEscape="true" />" rel="stylesheet"/>
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
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			
			<th scope= "col" >comandi</th>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >priorità</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	        <th scope= "col" >peso apicale</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">

	<c:choose>
	<c:when test="${obj.tipo == 'OBIETTIVO'}">
		<tr class="success">
		<td>
		
		<c:choose>
    	<c:when test="${obj.statoApprov == 'INTERLOCUTORIO' }">
    	<span class="label label-error">
			<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/edit" htmlEscape="true" />">MODIFICA</a>
			</span>
			
			</br>
			<span class="label label-warning">
			<a href="<spring:url value="/controllerAct/new/${obj.idObiettivo}" htmlEscape="true" />">NUOVA AZIONE</a>
			</span>
			</br>	
		<span class="label label-error">		
	    	<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/richiede/${obj.incarico.idIncarico}" htmlEscape="true" />">RICHIEDI</a>
	    </span>
	    </c:when>
    	<c:when test="${obj.statoApprov == 'PROPOSTO' }">
    	<span class="label label-error">
	    	<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/valida" htmlEscape="true" />">VALIDA</a>
	    </span>
	    	</br>
	    	<span class="label label-warning">
			<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/rivede" htmlEscape="true" />">RIVEDI</a>
			</span>
			</br>
			<span class="label label-error">
			<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/respinge" htmlEscape="true" />">RESPINGI</a>
			
		</span>
		</c:when>
    	<c:when test="${obj.statoApprov == 'RICHIESTO' }">
    	<span class="label label-error">
    		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/edit" htmlEscape="true" />">MODIFICA</a>
    		</span>
    		</br>
    		<span class="label label-warning">
			<a href="<spring:url value="/controllerAct/new/${obj.idObiettivo}" htmlEscape="true" />">NUOVA AZIONE</a>
			</span>	
    	</c:when>
    	<c:when test="${obj.statoApprov == 'VALIDATO' }">
    	</c:when>
    	<c:when test="${obj.statoApprov == 'RESPINTO' }">
    	</c:when>
    	<c:when test="${obj.statoApprov == 'ANNULLATO' }">
    	</c:when>
	    <c:when test="${obj.statoApprov == 'DEFINITIVO' }">
	    <span class="label label-error">
			<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/apicale" htmlEscape="true" />">MODIFICA APICALE </a>&nbsp; 
			</span>
			</br>
			<span class="label label-warning">
			<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/annulla" htmlEscape="true" />">ANNULLA </a>
			</span>
		</c:when>
    	<c:when test="${obj.statoApprov == 'RIVISTO' }">
    	</c:when>
    	</c:choose>
	</c:when>
	</c:choose>
	
	<c:choose>
	<c:when test="${obj.tipo == 'ISTITUZ_MAGG_RILEV'}">
	
		<tr class="info">
		<td>
		<c:choose>
		<c:when test="${obj.statoApprov == 'INTERLOCUTORIO' }">
		
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/edit" htmlEscape="true" />">MODIFICA</a>
		</c:when>
		
		<c:when test="${obj.statoApprov == 'PROPOSTO' }">
    	<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/valida" htmlEscape="true" />">VALIDA</a>
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/rivede" htmlEscape="true" />">RIVEDI</a>
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/respinge" htmlEscape="true" />">RESPINGI</a>
	
    	</c:when>
    	<c:when test="${obj.statoApprov == 'VALIDATO' }">
    	</c:when>
    	<c:when test="${obj.statoApprov == 'RESPINTO' }">
    	</c:when>
    	<c:when test="${obj.statoApprov == 'ANNULLATO' }">
    	</c:when>
    	</c:choose>
	</c:when>
</c:choose>
</td>

    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
    </c:if>
    
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    <td>   
    <c:if test="${obj.tipo == 'OBIETTIVO'}">  
	    <c:if test="${obj.priorita == 'ALTA'}"> ALTA</c:if>
	    <c:if test="${obj.priorita == 'BASSA'}"> BASSA</c:if>
	</c:if>    
    </td>
    <td>${obj.statoApprov} </td>
	<td><c:if test= "${obj.peso >0}"> ${obj.peso} </c:if></td> 
	<td><c:if test= "${obj.apicale}"> ${obj.pesoApicale} </c:if></td>
	</tr>
	<tr>
    <td colspan="6">
    
    <%@ include file="/WEB-INF/views/geko/controller/tableActionsCtrlMod.jsp" %>
    </td>
     
    </tr>
</c:forEach>
</table>   
<hr />                       

</section>

</article>  
    
     <!-- Footer -------------------------->
  <div id="footer">
      
<table class="footer" >
    <tr>
      <td>
      <span class="label label-warning">
      <a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      </span>
      Ge.Ko. by Regione Siciliana - Segreteria Generale -  ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: modifyPianificazioneIncaricoController.jsp 12/01/2016]</p></td>
    </tr>
  </table>
</div>
</div>
</div>  
</div>  
</body>
</html>
