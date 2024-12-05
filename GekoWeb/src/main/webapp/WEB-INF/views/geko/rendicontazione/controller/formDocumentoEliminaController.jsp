<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Documenti di una Azione</title>
    <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>

<div id="wrapper">
    <h3>Documento da Eliminare per ${documento.azione.descrizione}</h3>

	<form:form modelAttribute="documento" method="post" enctype="multipart/form-data">
    
    <fieldset>
    <h2 class="info"> Dati descrittivi </h2>
    
    <table width="80%">
    <tr>
        <td><form:label for="nomefile" path="nomefile">nome</form:label></td>
        <td><form:input path="nomefile" size="50" maxlength="50"/> </td>
        <td><form:errors path="nomefile" cssClass="error"/></td>
    </tr>
    
    
    </table>
    
    </fieldset> 
    
    
    <fieldset>
    
        <table  width="80%">
    	<tr>  
        
        <td>
           <p class="button"><input type="submit" name="elimina" value="Elimina Documento"/></p>
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
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R.Cirrito 18/01/2016</td>
    </tr>
  </table>
</div>
      
</body>

</html>