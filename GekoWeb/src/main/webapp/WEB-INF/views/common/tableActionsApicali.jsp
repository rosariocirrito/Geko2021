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
        	<fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/> <c:if test="${act.tassativa}">- TASSATIVA</c:if>
        </td>
        <td><c:if test="${act.pesoApicale >0 }" >${act.pesoApicale}</td></c:if>
        
        </tr>
    </c:forEach>
    </tbody>
</table>   
    