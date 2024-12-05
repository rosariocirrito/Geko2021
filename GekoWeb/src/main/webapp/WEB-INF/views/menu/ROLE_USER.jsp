<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="ISO-8859-1" />
        <title>Menu USER</title>
        <link href="resources/css/menu.css" rel="stylesheet"/>
        <link href="resources/css/mask.css" rel="stylesheet"/>
    </head>
    
<body>
    <security:authentication property="name" var="nomeUtente"/>
        
    <header class="testata">
        <p class="user">Utente: ${nomeUtente} / 
            <c:forEach items="${ruoliUtente}" var="ruolo">
                 <a href="${ruolo.authority}">${ruolo.authority}</a> &nbsp
                
            </c:forEach>
        </p>
    </header>
    
<form:form modelAttribute="menu" method="post">
<fieldset>
<h2 class="info">Parametri della Sessione</h2>
<table>
	<tr>
		<td width="20%"><form:label for="anno" path="anno">Anno</form:label></td>
	    <td><form:input path="anno"/></td>
	    <td><form:errors path="anno" cssClass="error"/></td> 
	</tr>  
	<tr>
		<td width="20%"><form:label for="anno" path="anno">Altra Struttura</form:label></td>
		<td>
		<form:select path="idStrutturaScelta" >
			<form:options items="${menu.strutture}" itemValue="idpersona" itemLabel="denominazione"/>    
		</form:select>
		</td>
	</tr>
	<tr>
		<td>
		</td>
		<td >
			<p class="button"><input type="submit" name="update" value="Aggiorna"/></p>
			<p class="button"><input type="submit" name="logout" value="Logout"/></p>
		</td> 
	</tr>
</table>  
</fieldset>
</form:form>              
    <article>
        <section id="sezione">   
            <p id="titolo-lista">MENU USER</p>
            <hr />
        
        <table >
            <tbody>
                <security:authorize ifAnyGranted="ROLE_USER">
                    <tr>
                        <td>Lista Le tue azioni</td>
                    </tr>
                </security:authorize>
                
                
            </table>      
            <p><c:url value="/j_spring_security_logout" var="logoutUrl" /><a href="${logoutUrl}">Log out</a></p>
</section>
    </article>
            
    </body>
</html>
