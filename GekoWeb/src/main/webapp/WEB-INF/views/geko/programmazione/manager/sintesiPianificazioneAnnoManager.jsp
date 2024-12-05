<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Sintesi Programmazione Obiettivi Regionali</title>
    <link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
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
<hgroup>  
<h2>Programmazione Obiettivi per <em>${dipartimento} </em>- anno ${anno}</h2> 
<h4> numero incarichi trovati : ${numero}</h4>
<hr />  
</hgroup>  

<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>


<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">

<hgroup >            
<h3 >Struttura  ${incarico.denominazioneStruttura} </h3>
<h4 class="responsabile"> Responsabile  ${incarico.responsabile}  </h4>
<h4>
con incarico dal <fmt:formatDate value="${incarico.dataInizio}" pattern="dd/MM/yyyy"/>
al <fmt:formatDate value="${incarico.dataFine}" pattern="dd/MM/yyyy"/>
<c:if test="${incarico.interim}">
     AD INTERIM
</c:if>
</h4>
 
</hgroup>
	
<table class="table table-bordered table-striped">
		<colgroup>
			<col class="desrc">
			<col class="desrc">
			<col class="name">
			<col class="name">
		</colgroup>   
    <tbody>
    <thead> 
		<tr>
	    	<th scope= "col" >obiettivo</th>
	    	<th scope= "col" >note</th>
	    	<th scope= "col" >stato</th>
	        <th scope= "col" >peso</th>
	    </tr>  
	</thead>             

<c:forEach items="${incarico.obiettivi}" var="obj" varStatus ="status">
	<tr class="success">
	

    <td>     
    <c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
	    Descr_Proposta - <em> ${obj.descrizioneProp} </em> </br>    
    </c:if>
     
    ${obj.codice} -  ${obj.descrizione} 
    </td>
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
   
    <td>${obj.statoApprov} </td>
	<td><c:if test= "${obj.peso >0}"> ${obj.peso} </c:if></td> 
	</tr>
	
</c:forEach>
</table>  
    
    <hr />                              
</c:forEach>
</section>
</article>      
</div>                
<!-- Footer -------------------------->
<div id="footer">      
<table class="footer" >
    <tr>
	    <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
	    Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:sintesiPianificazioneAnnoManager.jsp 2019/04/23] </td>
	</tr>
</table>
</div>
</div>
</div>
</body>
  
</html>
