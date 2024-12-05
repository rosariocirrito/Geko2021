<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Documenti di una Azione</title>
    <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>

<div id="wrapper">
              
  

    <h3>Nuovo Documento per ${documento.azione.descrizione}</h3>



<form:form modelAttribute="documento" method="POST" enctype="multipart/form-data">
    
    <fieldset>
    <h2 class="info"> Dati descrittivi </h2>
    <table width="80%">
    <tr>
        <td><form:label for="nome" path="nome">nome</form:label></td>
        <td><form:input path="nome" size="30" maxlength="200"/> </td>
        <td><form:errors path="nome" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label for="descrizione" path="descrizione">descrizione</form:label></td>
        <td><form:textarea path="descrizione" size="30" maxlength="1000"/> </td>
        <td><form:errors path="descrizione" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label for="fileData" path="fileData">File</form:label></td>
        <td><form:input path="fileData" type="file"/></td>
    </tr>
    </table>
    </fieldset> 
    
	
   
    
    <fieldset>
        <table  width="80%">
    <tr>  
       <td>             
       <p class="button"><input type="submit" name="add" value="Aggiungi Documento"/></p>
       </td>
       
           
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</div>

<div id="footer">
 <p><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></p>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing R. Cirrito </td>
      <p>[view: formDocumentoCreateManager.jsp]</p>  
    
</div>
      
</body>

</html>