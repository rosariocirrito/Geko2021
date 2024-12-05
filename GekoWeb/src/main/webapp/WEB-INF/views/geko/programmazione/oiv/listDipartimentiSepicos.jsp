<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Lista Dipartimenti </title>
    <link href="../resources/css/lista.css" rel="stylesheet"/>
</head>
    
<body>
    <security:authentication property="name" var="nomeUtente"/>
    <security:authentication property="authorities" var="ruoli" />
    
    
<article>
    <div id="wrapper">
    <h1 id="titolo-lista">Lista Dipartimenti attivati</h1>   
	<c:forEach items="${listDipartimenti}" var="Item">   
    
	      
		    <h4 id="titolo-lista"> <em>${Item.denominazione} </em></h4>   
		
		    <hr />      
		
		
	</c:forEach>
</div>
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
	  	<td>[view:listDipartimentiSepicos.jsp] </td>
	</tr>
  </table>
</div>
    
</body>
</html>
