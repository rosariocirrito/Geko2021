<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Rendicontazione Obiettivi della Struttura del Dirigente</title>
<link href="../../../resources/css/lista.css" rel="stylesheet"/>

</head>

<body>
<div id="wrapper">
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Rendicontazione Obiettivi con criticità per <em>${nomeStruttura} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${userName}</h4>   
        
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
                
<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
    <div id="${obj.tipo}">
    <hgroup >            
    <h3 >
        ${obj.codice} - <em>&nbsp; ${obj.descrizione} </em>
    </h3>
        
    <c:if test="${obj.note}"><h4>${obj.note}</h4></c:if> 
    <c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>
    <h4>
        stati: ${obj.statoApprov} / ${obj.statoRealiz}
        <c:if test= "${obj.peso >0}"> peso: ${obj.peso} </c:if> 
    </h4> 
    </hgroup>
            
    <table >   
    <tbody>
    <c:forEach items="${obj.azioniCritiche}" var="act">
    	<tr><th colspan="9"><h4> ${act.descrizione}</h4></th></tr> 
        
        <c:if test="${act.note}">
        <tr>
            <td>Note</td>      
            <td colspan="8"> ${act.note}</td>
        </tr>
		</c:if>
        <tr>
            <th class="prod" width="5%">Indicatore</th>
            <th class="prod" width="10%">Valore Obiettivo</th>
            <th class="prod" width="5%">Scadenza</th>
            <c:if test="${act.peso >0 }" > 
            	<th class="prod" width="5%">Peso</th>
            </c:if>
            <th class="prod" width="10%">Risultato</th>
            <th class="prod" width="10%">Documenti</th>
            <th class="prod" width="20%">Criticità</th>
            <th class="prod" width="20%">Proposte</th>
            <th class="prod" width="20%">Indicazioni</th>
        </tr>

        <tr>    
            <td>${act.indicatore}</td>
            <td>${act.prodotti}</td>
            <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td>
            <c:if test="${act.peso >0 }" > 
            	<td>${act.peso}</td>
            </c:if>
            <td>${act.risultato}</td>
            <td> 
                <c:forEach items="${act.documenti}" var="docu">
                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>

                 </c:forEach>
             </td>
            <td> 
                <c:forEach items="${act.criticita}" var="critic">

                            ${critic.descrizione} </br>

                </c:forEach>
            </td>
			<td> 
                <c:forEach items="${act.criticita}" var="critic">

                            ${critic.proposte} </br>

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
            
</div>
</c:forEach>
</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[listIndicazioniAltraStrutturaController.jsp] </td>
    </tr>
  </table>
</div>
  
    
</div>    
</body>
</html>
