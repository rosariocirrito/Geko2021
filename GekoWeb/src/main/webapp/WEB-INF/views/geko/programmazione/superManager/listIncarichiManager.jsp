<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Lista Incarichi dirigenziali</title>
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
<div class="col-sm-12 col">
    
<article>
<section >   

 
<h2 id="titolo-lista">Lista Incarichi Dirigenziali per <em>${manager.cognomeNome} </em> </h2>   
    

<c:if test="${empty listIncarichi}">
	<p>Nessun incarico trovato.</p>
</c:if>

<table class="table table-bordered table-striped">

<colgroup>
	<col class="desrc">
	<col class="name">
	<col class="name">
	<col class="name">
</colgroup>
<thead>   

<tr>
    <th class="prod" width="70%">Struttura</th>
    <th class="prod" width="10%">Data Inizio</th>
    <th class="prod" width="10%">Data Fine</th>
    <th class="prod" width="10%">Interim</th>
</tr>
</thead>
<tbody>
<c:forEach items="${listIncarichi}" var="incarico" varStatus ="status">
	<tr>    
	 	<td>${incarico.denominazioneStruttura}</td>
	    <td><fmt:formatDate value="${incarico.dataInizio}" pattern="dd/MM/yyyy"/></td>
	    <td><fmt:formatDate value="${incarico.dataFine}" pattern="dd/MM/yyyy"/></td>
		<td><c:if test="${incarico.interim}">X</c:if></td>
	</tr>
    
</c:forEach>
</tbody>
</table>   


</section>
</article>
    
<!-- Footer -------------------------->

      
<span class="label label-warning"><a href="<spring:url value="/ROLE_SUPERMANAGER" htmlEscape="true" />">Menu SuperManager</a></span>
      Ge.Ko. by ing. R. Cirrito [view:listIncarichiManager.jsp 2018/04/12] 
	
</div>
</div>
</div>
</body>
</html>
