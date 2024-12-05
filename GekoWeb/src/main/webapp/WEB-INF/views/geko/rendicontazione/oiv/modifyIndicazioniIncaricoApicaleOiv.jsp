<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Modifica Obiettivi della Struttura del Dirigente</title>
    <link href="<spring:url value="/resources/bootstrap336/css/bootstrap.css" htmlEscape="true" />" rel="stylesheet"/>
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
<section>

<h3>Modifica Indicazioni incarico per: ${struttura} - anno ${anno}</h3>
<hr />    
<h4>Responsabile: ${responsabile}</h4> 

<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>


                
<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
    <hr />
    <hgroup >            
    <h3 >
        ${obj.codice} - <em>&nbsp; ${obj.descrizione} </em>
    </h3>
        
    <c:if test="${obj.note}"><h4>${obj.note}</h4></c:if> 
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>
    <h4>
        stato: ${obj.statoApprov} 
        <c:if test= "${obj.peso >0}"> peso: ${obj.peso} </c:if> 
    </h4> 
    </hgroup>
            
    <table class="table table-bordered table-striped">
    <colgroup>
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="name">
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
			<col class="desrc">
		</colgroup>   
    <tbody>
    <c:forEach items="${obj.azioni}" var="act">
    	<tr class="success"><th colspan="6"><h4> ${act.denominazione} - ${act.descrizione} <c:if test="${act.tassativa}">- AZIONE E SCADENZA TASSATIVE</c:if></h4></th></tr> 
        
        <c:if test="${act.note}">
        <tr>
            <td>Note</td>      
            <td colspan="6"> ${act.note}</td>
        </tr>
		</c:if>
        <tr>
            <th >Indicatore</th>
            <th >Valore Obiettivo</th>
            <th >Scadenza</th>
            <th >Peso</th>
            <th >Risultato</th>
            <th >Documenti</th>
        </tr>

        <tr>    
            <td>${act.indicatore}</td>
            <td>${act.prodotti}</td>
            <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td><c:if test="${act.tassativa}">- TASSATIVA</c:if>
            <td>${act.pesoApicale}</td>
            <td>${act.risultato}</td>
            <td> 
                <c:forEach items="${act.documenti}" var="docu">
                <p>
                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>
				</p>
                 </c:forEach>
             </td>
        </tr>
        
        <tr>
        	<th colspan="2">
            	<a href="<spring:url value="/oivOivAzione/new/${act.idAzione}" htmlEscape="true" />"> Nuovo </a> Memo
            </th>
            <th colspan="2">Proposte</th>
            <th colspan="2">Valutazioni</th>
        
        </tr>
        <tr>
	        <td colspan="2"> 
	             <c:forEach items="${act.oivAzione}" var="oivAzione">
					<a href="<spring:url value="/oivOivAzione/${oivAzione.id}/edit" htmlEscape="true" />"> Edit </a>
					<span style="color: #ffffff; background-color: #ff0000;">
	                    ${oivAzione.memo}<br />
	                </span>
	             </c:forEach>
	        </td>
			<td colspan="2"> 
	        	<c:forEach items="${act.oivAzione}" var="oivAzione2">
	        		<span style="color: #ffffff; background-color: #ff0000;">
	               		${oivAzione2.proposte} <br />
	               	</span>
	            </c:forEach>
	        </td>
	        <td colspan="2"> 
	            <c:forEach items="${act.oivAzione}" var="oivAzione3">
	            	<span style="color: #ffffff; background-color: #ff0000;">
	                 	${oivAzione3.valutazioni} <br />
	                </span>
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
      <td><a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />">Menu Oiv</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[modifyIndicazioniIncaricoApicale.jsp 09/06/2017] </td>
    </tr>
  </table>
</div>
</div>
</div>  
</body>
</html>
  
    
