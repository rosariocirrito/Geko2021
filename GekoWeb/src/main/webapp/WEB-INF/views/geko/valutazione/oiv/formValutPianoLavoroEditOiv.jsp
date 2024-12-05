<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Edit Pesi Comportamenti Organizzativi</title>
        
        <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">



<h3>
	Modifica Valutazione Attuazione Piano di Lavoro per l'incarico di </br> 
	${valutazione.incarico.responsabile} /
	${valutazione.incarico.denominazioneStruttura}/
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="PUT">
    
<fieldset>
<h2 class="info"> Valutazioni </h2>
<table width="50%">
  	
<tr>
	<td><form:label for="pdlVal" path="pdlVal">Piano di Lavoro Valutazione</form:label></td>      
    <td><form:select path="pdlVal" class="large" >
	        	<form:option value="ALTO"/>
            	<form:option value="MEDIO"/>
            	<form:option value="BASSO"/>
            	<form:option value="INSUFFICIENTE"/>
            	<form:option value="DA_VALUTARE"/>
           	</form:select>	
	        <form:errors path="pdlVal" cssClass="error"/>
	</td>
</tr>

    
</table>
</fieldset> 
    
    
<fieldset>
<table  width="80%">
<tr>  
	<td><p class="button"><input type="submit" name="update" value="Modifica Valutazione"/></p></td>
    <td><p class="button"><input type="submit" name="cancel" value="Esci"/></p></td>
</tr>
</table>
</fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />">Menu Oiv</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formValutPianoLavoroEditOiv.jsp 2017/10/12]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>