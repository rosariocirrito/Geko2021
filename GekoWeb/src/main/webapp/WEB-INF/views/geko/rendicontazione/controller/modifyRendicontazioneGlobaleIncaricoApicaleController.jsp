<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Modifica Rendicontazione Obiettivi Apicali </title>
    <link href="<spring:url value="/resources/bootstrap336/css/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet"/>
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
<div class="col-lg-12 col"> 

<article>
<section id="obj-sezione"> 
    
<h1 id="titolo-lista">Modifica Rendicontazione Obiettivi Apicali per <em>${struttura} </em>- anno ${anno}</h1> 
<hr />  
<h4>Responsabile: ${responsabile}</h4>  

<c:if test="${empty listObiettiviDept}">
    <p>Nessun obiettivo apicale trovato.</p>
</c:if>

	<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >priorità</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead> 

<c:forEach items="${listObiettiviDept}" var="obj" varStatus ="status">
 
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
	<td><c:if test= "${obj.pesoApicale >0}"> ${obj.pesoApicale} </c:if></td> 
	</tr>
	<tr>
    <td colspan="5">

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
	        <th scope= "col" >indicazioni</th>
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
        
        <td>${act.denominazione} - ${act.descrizione}</td>
        
        <td><c:if test="${act.note>''}">${act.note}</c:if></td>
            
        <td>${act.indicatore}</td>
        <td>${act.prodotti}</td>
        <td><fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/></td>
        <td><c:if test="${act.pesoApicale >0 }" >${act.pesoApicale}</td></c:if> 
        <td>
        <c:if test="${obj.rendicontabile}" > 
	        <a href="<spring:url value="/controllerAct/${act.idAzione}/editRisultatoApicale" htmlEscape="true" />">Modifica</a>
	    </c:if>	
        ${act.risultato}
        </td>
            <td> 
            <c:if test="${obj.rendicontabile}" > 
				<a href="<spring:url value="/controllerDocumenti/new/${act.idAzione}" htmlEscape="true" />">Add</a>
	        </c:if>
                <c:forEach items="${act.documenti}" var="docu">
                	<c:if test="${docu.editable}" > 
	                	<a href="<spring:url value="/controllerDocumenti/${docu.id}/edit" htmlEscape="true" />">Edit</a>
	                </c:if>	
                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>
					</br>
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
 
</c:forEach>


</section>
</article>      

                
<!-- Footer -------------------------->
<div id="footer">      
<table class="footer" >
    <tr>
	    <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
	    Ge.Ko. by Regione Siciliana - Segreteria Generale - ing.R. Cirrito  </td>
    </tr>
    <tr>
	  	<td>[view:modifyRendicontazioneIncaricoApicaleController.jsp 2017/07/31] </td>
	</tr>
</table>
</div>

</div>
</div> 
</div>
</body>
  
</html>
