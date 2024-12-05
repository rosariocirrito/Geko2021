<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Modifica Programmazione Obiettivi Pluriennali</title>
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
<h1 id="titolo-lista">Modifica Programmazione Obiettivi Pluriennali per dipartimento: <em>${dipartimento} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${responsabile}</h4>
    
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo pluriennale trovato.</p>
</c:if>
            
<table class="table table-bordered table-striped">
    <colgroup>
        <col class="desrc">
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
        <th scope= "col" >strategico</th>
        <th scope= "col" >obiettivo</th>
        <th scope= "col" >note</th>
        </tr>  
    </thead>             

    <c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
	<tr class="success">
	<td>
            <span class="label label-success">
              <a href="<spring:url value="/controllerObjPlur/newDip/${anno}/${idIncaricoApicale}" htmlEscape="true" />">CREA OBJ PLURIENNALE</a>
            </span> <br />
            <c:forEach items="${obj.guiCommands}" var="cmd" varStatus ="status">
                <span class="label label-success">
                <a href="<spring:url value="  ${cmd.uri}  " htmlEscape="true" />">${cmd.label}</a>
                </span>
                <br />
            </c:forEach>	
	</td>
	
	
	<td>
	<c:if test="${empty obj.associazTriennali}">
        <span class="label label-info">
		<a href="<spring:url value="/controllerAssociaPlur/new/${obj.idObiettivo}/${idIncaricoApicale}" htmlEscape="true" />">ASSOCIA STRATEGICO </a>
	</span>
	</c:if>
        <c:forEach items="${obj.associazTriennali}" var = "associaz">
        <span class="label label-success">
        <a href="<spring:url value="/controllerAssociaPlur/${associaz.id}/edit/${idIncaricoApicale}" htmlEscape="true" />">MODIFICA STRATEGICO </a>
        </span> 
        ${associaz.obiettivo.descrizione}
        </br> 	
        </c:forEach>
	</td>
    <td>     
    
    
     
    
    ${obj.codice} -  ${obj.descrizione} 
    
    
	</td>	 
    <td><c:if test="${obj.note> ''}">${obj.note}</c:if></td>
    
    
	
	</tr>
	<tr>
    <td colspan="5">
    


	
	<hr /> 
    </td>
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
      <td>[view: modifyProgrTriennaleController.jsp 2021/04/15] </td>
    </tr>
</table>
</div>
  
 </div>
 </div>
    
    
</div>     
</body>
</html>
