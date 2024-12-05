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

<form:form modelAttribute="documento" method="POST" enctype="multipart/form-data">
    
    
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
           <p class="button"><input type="submit" name="update" value="Modifica File Documento"/></p>
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
      <p>[view: formDocumentoEditFileController.jsp 2019/06/19]</p>  
    
</div>
      
</body>

</html>