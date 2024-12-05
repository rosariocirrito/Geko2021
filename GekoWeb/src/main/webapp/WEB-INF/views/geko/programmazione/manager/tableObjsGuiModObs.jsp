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
	    	<a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a><br />
	    </c:forEach>
	    </td>


    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> <br />    
    </c:if>
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>  
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    <td>${obj.statoApprov} </td>
	<td><c:if test= "${obj.peso >0}"> ${obj.peso} </c:if> <c:if test="${obj.pesoAzioniNOK}"><span class="warning"> attnz. peso Azioni diverso da 100!</span></c:if></td> 
	</tr>
	<tr>
	    <td colspan="5">    
	    	<%@ include file="/WEB-INF/views/geko/programmazione/manager/tableActionsMngrMod.jsp" %>
	    </td>
    </tr>
</c:forEach>
</table>