<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Lista Dipendenti della Struttura/SubStrutture</title>
    <link href="../resources/css/lista.css" rel="stylesheet"/>
</head>
    
<body>
    <security:authentication property="name" var="nomeUtente"/>
    <security:authentication property="authorities" var="ruoli" />
    
    
<article>
    <div id="wrapper">
    <section id="obj-sezione">   
        <h1 id="titolo-lista">Lista Utenti per <em>${nomeStruttura} </em></h1>   

        <hr />      
        <c:if test="${empty mapListUtenti}">
            <p>Nessun utente trovato !</p>
        </c:if>
    
    <c:forEach items="${mapListUtenti}" var="mapItem">
    
	    <table>
	    
	    <tr>
	        <th colspan="4"> <h3>${mapItem.key.idPersona} - ${mapItem.key.denominazione}</h3> </th>
	    </tr>   
	    
	    <tr>
	    	<th>Id</th>
	        <th>Username</th>
	        <th>Ruoli</th>
	        <th>Nome</th>
	    </tr>
	
	    
	
	    <tbody>
	    <c:forEach items="${mapItem.value}" var="user" varStatus ="status">
	         <tr>
	         <td> 
	         <a href="<spring:url value="/administratorUtenti/${user.idusers}/edit" htmlEscape="true" />">MODIFICA</a>
	         ${user.idusers}
             </td>
         	<td> 
         	<a href="<spring:url value="/administratorUtenti/${user.idusers}/resetPwd" htmlEscape="true" />">RESET-PWD</a>
                 ${user.username}
             </td>
         	<td> 
         	<c:forEach items="${user.authorities}" var="auth" varStatus ="status">
         	<c:if test="${auth.authorityType.application == 'geko'}">
                 ${auth.authorityType.authority} </br>
            </c:if>
            </c:forEach>
             </td>
             <td> 
                 ${user.persona.stringa}
             </td>
             
         </tr>
	    </c:forEach>
	 
	    </tbody>
	    </table>       
    
	<br/>      
	</c:forEach>
	
</section>
</div>
</article>       
           <!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_ADMINISTRATOR" htmlEscape="true" />">Menu Administrator</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view: security/administrator/modifyUtentiAdministrator.jsp 27/2/2017] </td>
	</tr>
  </table>
</div>
    
</body>
</html>
