<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
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
			
	    	<th scope= "col" >azioni</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >indicatore previsto</th>
	    	<th scope= "col" >valore obiettivo</th>
	        <th scope= "col" >scadenza</th>
	        <th scope= "col" >peso</th>
	        
	        <th scope= "col" >risultato</th>
	        <th scope= "col" >documenti</th>
	        <th scope= "col" >criticità</th>
	        <th scope= "col" >indicazioni</th>
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
        
        <td>
        	<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
			        ${act.descrizioneProp}
		    </c:if>
        	${act.denominazione} - ${act.descrizione}
        </td>
        
        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
            
        <td>${act.indicatore}</td>
        <td>${act.prodotti}</td>
        <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td>
        <td><c:if test="${act.peso >0 }" >${act.peso}</td></c:if> 
        <td>${act.risultato}</td>
            <td> 
                <c:forEach items="${act.documenti}" var="docu">
                <a href="<spring:url value="/controllerDocumenti/${docu.id}/elimina" htmlEscape="true" />">Elimina</a>
                
                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>

                 </c:forEach>
             </td>
            <td> 
                <c:forEach items="${act.criticita}" var="critic">

                            ${critic.descrizione} </br>

                </c:forEach>
            </td>

            <td> 
                <c:forEach items="${act.criticita}" var="critic3">

                            ${critic3.indicazioni} </br>

                </c:forEach>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>   
    