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
              
<h3>Documento da Modificare per ${documento.azione.descrizione}</h3>

<form:form modelAttribute="documento" method="PUT" enctype="multipart/form-data">
    
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
    
    </table>
    </fieldset> 
    
    
       
    
    <fieldset>
        <table  width="80%">
    <tr>  
        <td>
           <p class="button"><input type="submit" name="update" value="Modifica Descrizione Documento"/></p>
        </td>
        <td>
           <p class="button"><input type="submit" name="delete" value="Cancella Documento"/></p>
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

      <p><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a></p>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing R. Cirrito </td>
      <p>[view: formDocumentoEditDescrizioneController.jsp 2019/06/18]</p>  
    
</div>
      
</body>

</html>