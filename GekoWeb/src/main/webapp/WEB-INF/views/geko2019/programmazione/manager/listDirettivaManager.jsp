<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Lista Direttiva per Manager</title>
    <link href="../../resources/css/lista.css" rel="stylesheet"/>
</head>

<body>
        <security:authentication property="name" var="nomeUtente"/>
        <security:authentication property="authorities" var="ruoli" />
        
<div id="wrapper">     
<article>
<section id="obj-sezione">   
<h2 id="titolo-lista">Lista Aree Strategiche / Priorità Politiche / Obiettivi Strategici per l'anno <em>${anno} </em></h2>   
<hr />      

<c:if test="${empty listAreeStrategiche}">
    <p>Aree Strategiche non trovate !</p>
</c:if>

<c:forEach items="${listAreeStrategiche}" var="area">
               
	<table>
	<tbody>   
	    <tr>
	        <th colspan="3"><h1>${area.codice} - ${area.descrizione}</h1> </th>
	    </tr>  
	     
	    
        <c:forEach items="${area.prioritaPolitiche}" var="prio" varStatus ="status">
	        <tr>
	            <th> 
	                <h4>${prio.descrizione}</h4>
	            </th>
	        </tr>
	        <c:forEach items="${prio.obiettiviStrategici}" var="objStrat" varStatus ="statusObj">
	        <tr>
	            <td> 
	                ${objStrat.descrizione}
	            </td>
	        </tr>
	    </c:forEach>
	    </c:forEach>
        
        
        </tbody>
       </table>       
                       
       <br/>                
</c:forEach>

</section>
</article>   
</div>    
           
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listDirettivaManager.jsp 2015/04/16] </td>
	</tr>
  </table>
</div>

 
</body>
</html>
