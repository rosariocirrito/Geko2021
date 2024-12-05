<table class="table table-bordered table-striped">
		<colgroup>
			<col class="name">
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
			<th scope= "col" >comandi</th>
	    	<th scope= "col" >azioni</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >indicatore previsto</th>
	    	<th scope= "col" >valore obiettivo</th>
	         <th scope= "col" >
	        	<c:if test="${!obj.apicale}">scadenza </c:if>
				<c:if test="${obj.apicale}" >scadenza apicale </c:if>
				
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
		
		<c:forEach items="${act.guiCommands}" var="actCmd" varStatus ="status">
			<span class="label label-info">
		    <a href="<spring:url value="  ${actCmd.uri}  " htmlEscape="true" />">${actCmd.label}</a>
		    </span>
	    	  </br>
	    </c:forEach>
		<span class="label label-info">
	            <a href="<spring:url value="/controllerAssegn/new/${act.idAzione}" htmlEscape="true" />">AGGIUNGI DIP.</a>
	        </span>
		</td>
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
	        <fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/><c:if test="${act.tassativa}">- TASSATIVA</c:if>
        </td>
        <td>
	        <c:if test="${act.peso >0 }" >${act.peso}</c:if>
	        <c:if test="${act.obiettivo.apicale}" >${act.pesoApicale}</c:if>
        </td>
         <!-- Dipendenti -->
	        
	        <c:forEach items="${act.assegnazioni}" var="assegn">
	        	<tr>
		        <td> 
		        
		        </td>
		        <td colspan="6">
		        
	           	<span class="label label-error">
	            	<a href="<spring:url value="/controllerAssegn/${assegn.id}/edit" htmlEscape="true" />">MODIFICA/CANCELLA ASSEGNAZIONE</a>
	           	</span>
	                ${assegn.opPersonaFisica.stringa} - ${assegn.peso}%	                	           
	         </td>
        	</tr>
        	</c:forEach>
        
       
        </tr>
    </c:forEach>
    </tbody>
</table>   
    