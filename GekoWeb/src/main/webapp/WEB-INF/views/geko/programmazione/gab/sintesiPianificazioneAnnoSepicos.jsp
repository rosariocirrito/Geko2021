<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Sintesi Programmazione Obiettivi Regionali</title>
    <link href="../../resources/css/lista.css" rel="stylesheet"/>
</head>
    
<body>
<div id="wrapper">
<article>
<section id="obj-sezione"> 
    
<h1 id="titolo-lista">Programmazione Obiettivi Regione Siciliana per anno ${anno}</h1> 
<hr />  

<c:if test="${empty mapObiettiviReg}">
    <p>Nessun obiettivo trovato per l'anno in questione.</p>
</c:if>

<!--  mapObiettiviReg<Struttura,List<Obiettivi>> -->
<c:forEach items="${mapObiettiviReg}" var="mapItem">

<div id="wrapper2">

<h3>${mapItem.key.denominazione}</h3>
<h4>${mapItem.key.responsabile}</h4>


    <c:forEach items="${mapItem.value}" var="obj" varStatus ="status">
        <div id="${obj.tipo}">
        <hgroup > 
        	<h3>${obj.struttura.denominazione} /  ${obj.codice} - <em>${obj.descrizione} </em> </h3>  
	    
	
			<c:if test="${obj.apicale}"><h4>APICALE</h4></c:if>
			 
			<h4> 
			<c:if test="${obj.tipo == 'OBIETTIVO'}">
	    Priorità      
	    <c:if test="${obj.priorita == 'ALTA'}"> ALTA</c:if>
	    <c:if test="${obj.priorita == 'BASSA'}"> BASSA</c:if>
	    </c:if>
			stato: ${obj.statoApprov} &nbsp;&nbsp;
			    <c:if test= "${obj.pesoApicale >0}"> peso apicale: ${obj.pesoApicale}  </c:if>
			</h4> 
		</hgroup>
            
            
        </div>
                  
    </c:forEach>
    
    </div>  
    <hr />                              
</c:forEach>
</section>
</article>      
                
<!-- Footer -------------------------->
<div id="footer">      
<table class="footer" >
    <tr>
	    <td><a href="<spring:url value="/ROLE_SEPICOS" htmlEscape="true" />">Menu Sepicos</a>
	    Ge.Ko. by Regione Siciliana - Segreteria Generale - 
	    ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:sintesiPianificazioneAnnoSepicos.jsp] </td>
	</tr>
</table>
</div>
    
</div>
</body>
  
</html>
