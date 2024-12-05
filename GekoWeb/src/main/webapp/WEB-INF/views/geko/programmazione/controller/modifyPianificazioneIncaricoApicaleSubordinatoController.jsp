<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Programmazione Obiettivi Apicali subordinati della Struttura del Dirigente</title>
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
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
<h1 id="titolo-lista">Programmazione Obiettivi Apicali subordinati per dipartimento: <em>${dipartimento} </em>- anno ${anno}</h1>
<hr />  

<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>

<div id="incarico">
<c:forEach items="${listIncarichiDept}" var="inc" varStatus ="status">

<hgroup >            
<h4 >Struttura  ${inc.denominazioneStruttura} </h4>
<h4 class="responsabile"> Responsabile  ${inc.responsabile}  </h4>
<h4>con incarico dal <fmt:formatDate value="${inc.dataInizio}" pattern="dd/MM/yyyy"/>
al <fmt:formatDate value="${inc.dataFine}" pattern="dd/MM/yyyy"/>
<c:if test="${inc.interim}">
     AD INTERIM
</c:if>
</h4>
            
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
			
			<th>
			
			comandi</th>
			<th scope= "col" >strategico</th>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >priorità</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${inc.obiettivi}" var="obj" varStatus ="status">
	
	<tr class="success">
	
	<td>
		<c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
			<span class="label label-success">
			    <a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
		    </span>
	    	  </br>
	    </c:forEach>
	</td>
	
	<td>
	<c:choose>
    <c:when test="${obj.tipo =='OBIETTIVO'}">
    <span class="label label-info">
		<a href="<spring:url value="/controllerAssocia/new/${obj.idObiettivo}/${idIncaricoApicale}" htmlEscape="true" />">ASSOCIA STRATEGICO </a>
	</span>
	</c:when>
	
    </c:choose>
    </br>
		<c:forEach items="${obj.associazObiettivi}" var = "associaz">
		<span class="label label-success">
		<a href="<spring:url value="/controllerAssocia/${associaz.id}/edit/${idIncaricoApicale}" htmlEscape="true" />">MODIFICA STRATEGICO </a>
		</span> 
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
	    	
	        <th scope= "col" >scadenza dirigente</th>
	        
	        <th scope= "col" >scadenza apicale</th>
	        <th scope= "col" >peso apicale</th>
	        
	    </tr>  
	</thead> 
    <c:forEach items="${obj.azioni}" var="act">
        <tr>
        <td>
        
		
		<span class="label label-info">
		<a href="<spring:url value="/controllerAct/${act.idAzione}/editApicaleSubordinata/${idIncaricoApicale}" htmlEscape="true" />">MODIFICA APICALE</a>
		</span>
		
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
      
        
        <td>
        	<fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/>
        </td>
        <td>${act.pesoApicale}</td>
        
        </tr>
    </c:forEach>
    </tbody>
</table>   
    
    </td>
    </tr>
</c:forEach>
</table>
<hr />                       
</c:forEach>

</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view: modifyPianificazioneIncaricoApicaleSubordinatoController.jsp 2016/04/27] </td>
    </tr>
</table>
</div>
  
 </div>
 </div>
    
    
</div>     
</body>
</html>
