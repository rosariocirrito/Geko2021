<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Obiettivi Apicali dell'incarico apicale</title>
	<link href="<spring:url value="/resources/css/lista.css" htmlEscape="true" />" rel="stylesheet"/>
</head>
    
<body>
    
<div id="wrapper">
    
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Scadenziario azioni per <em>${responsabile} - ${struttura} </em>- anno ${anno}</h1>
<hr />  

    
<c:if test="${empty listAzioni}">
    <p>Nessuna azione trovata.</p>
</c:if>
            


    
<table >   
<tbody>
<c:forEach items="${listAzioni}" var="act" varStatus ="status">
    
    <tr><th colspan="9">
    <h3> 
    	Responsabile: ${act.dirigente.stringa} 
    </h3> 
    <h4>
	    Obiettivo: ${act.obiettivo.descrizione} </br>
	    Azione: ${act.descrizione}
    </h4>
    </th></tr> 
    
    <c:if test="${act.note > ''}">
    <tr>
        <td>Note</td>      
        <td colspan="8"> ${act.note}</td>
    </tr>
    </c:if>

    <tr>
       
        <th class="prod" width="5%">Scadenza</th>
        
         <th class="prod" width="10%">Indicatore</th>
         <th class="prod" width="10%">Valore Obiettivo</th>
         <th class="prod" width="30%">Risultato</th>
         <th class="prod" width="45%">Documenti</th>
         
     	
    </tr>

    <tr>    
    	<td><fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/></td>
        <td>${act.indicatore}</td>
        <td>${act.prodotti}</td>
        <td>${act.risultato}</td>
        <td> 
               <c:forEach items="${act.documenti}" var="docu">
                   <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>

                </c:forEach>
        </td>
        
        
    </tr>
    
</c:forEach>
</tbody>
</table>   
    

</section>
</article>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />">Menu Gabinetto</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. Rosario Cirrito</td>
    </tr>
    <tr>
      <td>[listScadenzeAzioniGabinetto.jsp] 2019/07/23</td>
    </tr>
</table>
</div>
  
    
    
</div>     
</body>
</html>
