<table class="table table-bordered table-striped">
		<colgroup>
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
	        
	        <th scope= "col" >scadenza apicale</th>
	        <th scope= "col" >peso apicale</th>
	        
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
        <td>
        <c:choose>
		<c:when test="${obj.apicaleDiretto}">
		apicale diretto
		</c:when>
		<c:when test="${!obj.apicaleDiretto}">
		apicale ereditato
		
			<a href="<spring:url value="/controllerAct/${act.idAzione}/edit/${obj.incarico.idIncarico}" htmlEscape="true" />">MODIFICA</a>
		
		</c:when>
	
    
        
		</c:choose>
        </td>
        <td>
        	${act.denominazione} - ${act.descrizione}
        </td>
        
        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
            
        <td>${act.indicatore}</td>
        <td>${act.prodotti}</td>
        
        <td>
        	<fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
        </td>
        <td>${act.peso}</td>
        
        <td>
        	<fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/>
        </td>
        <td>${act.pesoApicale}</td>
        
        </tr>
    </c:forEach>
    </tbody>
</table>   
    