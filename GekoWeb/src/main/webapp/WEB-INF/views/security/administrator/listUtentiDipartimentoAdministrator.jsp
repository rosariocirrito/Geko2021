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
        <c:if test="${empty listUtenti}">
            <p>Nessun utente trovato !</p>
        </c:if>
    

    
<table>
	
	  
	<tr>
		<th>Id</th>
	    <th>Username</th>
	    <th>Ruoli</th>
	    <th>Nome</th>
	</tr>
	
	
	<tbody>
	<c:forEach items="${listUtenti}" var="user" varStatus ="status">
	         <tr>
	         <td> 
	                 ${user.idusers}
	             </td>
	         	<td> 
	                 ${user.username}
	             </td>
	         	<td> 
	         	<c:forEach items="${user.authorities}" var="auth" varStatus ="status">
	                 ${auth.authorityType.authority} </br>
	            </c:forEach>
	             </td>
	             <td> 
	                 ${user.persona.stringa}
	             </td>
	             
	         </tr>
	    </c:forEach>
	 
	    </tbody>
	    </table>       
    
	
</section>
</div>
</article>       
           <!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_ADMINISTRATOR" htmlEscape="true" />">Menu Administrator</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:security/listUtentiDipartimentoAdministrator.jsp 2017/02/27] </td>
	</tr>
  </table>
</div>
    
</body>
</html>
