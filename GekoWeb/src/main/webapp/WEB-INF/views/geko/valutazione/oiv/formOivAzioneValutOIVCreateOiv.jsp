<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Valutazione OIV</title>
        
        <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">
        
            
         

<h3>
    
    Nuova OivAzione per ${oivAzione.azione.descrizione}
    
</h3>



<form:form modelAttribute="oivAzione" method="post">
    
    <fieldset>
        <h2 class="info"> Dati descrittivi </h2>
  <table width="80%">
    
    <tr>
        <td><form:label for="valutazioni" path="valutazioni">valutazione OIV</form:label></td>
        <td><form:textarea path="valutazioni" size="200" maxlength="1000"/> </td>
        <td><form:errors path="valutazioni" cssClass="errors"/></td>
    </tr>
    </table>
    </fieldset> 
    
    
    <fieldset>
    <h2 class="comandi"> Comandi </h2>
        <table  width="80%">
    <tr>  
       <td>             
            <p class="button"><input type="submit" name="add" value="Aggiungi"/></p>
       </td>
          
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />">Menu Oiv</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing R. Cirrito </br>
      [formOivAzioneValutOIVCreateOiv.jsp] 03/11/2017 </td>
    </tr>
  </table>

  </div>
</body>

</html>