<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi
			</th>
			<th>
			
			struttura / responsabile</th>
			<th scope= "col" >strategico</th>
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
	<c:when test="${obj.apicaleDiretto}">
		apicale diretto
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/apicale/${obj.incarico.idIncarico}" htmlEscape="true" />">TOGLI APICALE </a>
		&nbsp; <a href="<spring:url value="/controllerObj/${obj.idObiettivo}/annulla/${obj.incarico.idIncarico}" htmlEscape="true" />">ANNULLA </a>
	</c:when>
	<c:when test="${!obj.apicaleDiretto}">
		apicale ereditato
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/apicale/${obj.incarico.idIncarico}" htmlEscape="true" />">TOGLI APICALE </a>
		&nbsp; <a href="<spring:url value="/controllerObj/${obj.idObiettivo}/annulla/${obj.incarico.idIncarico}" htmlEscape="true" />">ANNULLA </a>
	</c:when>
	
    <c:when test="${obj.statoApprov == 'DEFINITIVO' && obj.tipo =='OBIETTIVO'}">
		<a href="<spring:url value="/controllerObj/${obj.idObiettivo}/apicale/${obj.incarico.idIncarico}" htmlEscape="true" />">TOGLI APICALE </a>
		&nbsp; <a href="<spring:url value="/controllerObj/${obj.idObiettivo}/annulla/${obj.incarico.idIncarico}" htmlEscape="true" />">ANNULLA </a>
	</c:when>
    </c:choose> 
	modifica
			proponi
			nuova azione
	</td>
	<td>
		${obj.incarico.opPersonaGiuridica.denominazione} / ${obj.incarico.opPersonaFisica.stringa}
	</td>
	
	<td>
		<c:forEach items="${obj.associazObiettivi}" var = "associaz">
		 	${associaz.strategico.descrizione}
		</c:forEach>
	</td>
    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">    
    interlocut   
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
    </c:if>
    
     
    
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
	<td><c:if test= "${obj.pesoApicale >0}"> ${obj.pesoApicale} </c:if></td> 
	</tr>
	<tr>
    <td colspan="5">
    
    <%@ include file="/WEB-INF/views/controller/tableActionsApicaliMod.jsp" %>
    </td>
    </tr>
</c:forEach>
</table>