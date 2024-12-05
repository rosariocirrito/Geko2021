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
	<c:choose>
	<c:when test="${obj.tipo == 'OBIETTIVO'}">
		<tr class="success">	
	</c:when>
	
	<c:when test="${obj.tipo == 'ISTITUZ_MAGG_RILEV'}">
		<tr class="info">
	</c:when>
	
	<c:when test="${obj.tipo == 'AMMIN_MANUALE_SUPP' }">
		<tr class="warning">	
	</c:when>
	</c:choose>


    <td>
    <c:choose>
	<c:when test="${obj.statoApprov == 'INTERLOCUTORIO' }">
		<a href="<spring:url value="/dirigenteObj/${obj.idObiettivo}/edit/${idIncarico}" htmlEscape="true" />">MODIFICA</a>
		<a href="<spring:url value="/dirigenteObj/${obj.idObiettivo}/proponi/${idIncarico}" htmlEscape="true" />">PROPONI</a>
		<a href="<spring:url value="/dirigenteAct/new/${obj.idObiettivo}" htmlEscape="true" />">NUOVA AZIONE</a>	
	</c:when>
	
	<c:when test="${obj.statoApprov == 'RICHIESTO' || 
					obj.statoApprov == 'VALIDATO' ||
					obj.statoApprov == 'RIVISTO'
					}">
		<a href="<spring:url value="/dirigenteObj/${obj.idObiettivo}/accetta/${idIncarico}" htmlEscape="true" />">DEFINITIVO</a>
	</c:when>
	
	<c:when test="${obj.statoApprov == 'PROPOSTO' }">
		<a href="<spring:url value="/dirigenteObj/${obj.idObiettivo}/edit/${idIncarico}" htmlEscape="true" />">MODIFICA</a>
		<a href="<spring:url value="/dirigenteAct/new/${obj.idObiettivo}" htmlEscape="true" />">NUOVA AZIONE</a>	
	</c:when>
	
	<c:when test="${obj.statoApprov == 'DEFINITIVO' }">
		<a href="<spring:url value="/dirigenteObj/${obj.idObiettivo}/proponi/${idIncarico}" htmlEscape="true" />">RIPROPONI</a>
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
	</tr>
	<tr>
    <td colspan="5">
    
    <%@ include file="/WEB-INF/views/geko/programmazione/manager/tableActionsMngrMod.jsp" %>
    </td>
    </tr>
</c:forEach>
</table>