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
        <tr>
        
        
        <td scope= "col" >Dipendenti</td>
        <td colspan =7> 
            <c:forEach items="${act.assegnazioni}" var="assegn">
                ${assegn.opPersonaFisica.stringa} - ${assegn.peso}% - 
                da: <fmt:formatDate value="${assegn.dataInizio}" pattern="dd/MM/yyyy"/> 
                a: <fmt:formatDate value="${assegn.dataFine}" pattern="dd/MM/yyyy"/> 
                </br>
                    
         </c:forEach>
         </hr>
         </td>
        </tr>
    </c:forEach>
    </tbody>
</table>   
    