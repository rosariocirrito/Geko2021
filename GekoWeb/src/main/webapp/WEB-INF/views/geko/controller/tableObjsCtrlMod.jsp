<table class="table table-bordered table-striped">
		<colgroup>
			<col class="name">
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
	    	<th scope= "col" >priorità</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
	<tr class="success">

    <td>
    <c:choose>
	<c:when test="${obj.statoApprov == 'INTERLOCUTORIO' || obj.statoApprov == 'RICHIESTO'}">
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/edit}" htmlEscape="true" />">MODIFICA</a>
		<a href="<spring:url value="/controllerAct/new/${obj.idObiettivo}" htmlEscape="true" />">NUOVA AZIONE</a>	
		<c:if test="${obj.statoApprov != 'RICHIESTO'}">
			<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/richiede}" htmlEscape="true" />">RICHIEDI</a>
		</c:if>
	</c:when>
	
	<c:when test="${obj.statoApprov == 'PROPOSTO' }">
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/valida" htmlEscape="true" />">VALIDA</a>
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/rivede" htmlEscape="true" />">RIVEDI</a>
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/respinge" htmlEscape="true" />">RESPINGI</a>
	</c:when>
	<c:when test="${obj.statoApprov == 'DEFINITIVO' && obj.tipo =='OBIETTIVO'}">
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/apicale" htmlEscape="true" />">RENDI APICALE </a>
		&nbsp; <a href="<spring:url value="/controllerObj/${obj.idObiettivo}/annulla" htmlEscape="true" />">ANNULLA </a>
	</c:when>
	<c:when test="${obj.statoApprov == 'DEFINITIVO' }">
		&nbsp; <a href="<spring:url value="/controllerObj/${obj.idObiettivo}/annulla" htmlEscape="true" />">ANNULLA </a>
	</c:when>
	
	</c:choose>
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
	</tr>
	<tr>
    <td colspan="6">
    
    <%@ include file="/WEB-INF/views/geko/controller/tableActionsCtrlMod.jsp" %>
    </td>
    </tr>
</c:forEach>
</table>