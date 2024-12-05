<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Cambio Password</title>
        
        <style>
body {
	font-size: 100%;
	font-family: Arial, sans-serif;
}

#wrapper {
	width: 1050px;
}

h2 {
	background-color: #dedede;
	border-bottom: 1px solid #d4d4d4;
	border-top: 1px solid #d4d4d4;
	border-radius: 5px;
	box-shadow: 3px 3px 3px #ccc;
	color: #fff;
	font-size: 1.1em;
	margin: 12px;
	padding: 0.3em 1em;
	text-shadow: #9FBEB9 1px 1px 1px;
	text-transform: uppercase;
}

h2.account 				{ background-color: #0B5586; }
h2.address 				{ background-color: #4494C9; }
h2.public-profile 		{ background-color: #377D87; }		
h2.emails 				{ background-color: #717F88; }

legend {
	background-color: #dedede;
	border-top: 1px solid #d4d4d4;
	border-bottom: 1px solid #d4d4d4;
	-moz-box-shadow: 3px 3px 3px #ccc;
	-webkit-box-shadow: 3px 3px 3px #ccc;
	box-shadow: 3px 3px 3px #ccc;
	font-size: 1.1em;
	text-transform: uppercase;
	padding: 0.3em 1em;
}

fieldset {
	background-color: #f1f1f1;
	border: none;
	border-radius: 2px;
	margin-bottom: 12px;
	overflow: hidden;
	padding: 0 10px;
}

ul {
	background-color: #fff;
	border: 1px solid #eaeaea;
	list-style: none;
	margin: 12px;
	padding: 12px;
}

li {
	margin: 0.5em 0;
}

label {
	display: inline-block;
	padding: 3px 6px;
	text-align: right;
	width: 150px;
	vertical-align: top;
}

input, select, button {
	font: inherit;
}

.small {
	width: 75px;
}

.medium {
	width: 150px;
}

.large {
	width: 250px;
}

textarea {
	font: inherit;
	width: 500px;
}


.instructions {
	font-size: 75%;
	padding-left: 167px;
	font-style: italic;
}

.create_profile {
	background-color: #DA820A;
	border: none;
	border-radius: 4px;
	box-shadow: 2px 2px 2px #333;
	cursor: pointer;
	color: #fff;
	margin: 12px;
	padding: 8px;
	text-shadow: 1px 1px 0px #CCC;
}

.radios {
	background: none;
	display: inline;
	margin: 0;
	padding: 0;
}

.radios ul {
	border: none;
	display: inline-block;
	list-style: none;
	margin: 0;
	padding: 0;
}

.radios li {
	margin: 0;
	display: inline-block;
}

.radios label {
	margin-right: 25px;
	width: auto;
}

.radios input {
	margin-top: 3px;
}


.checkboxes label {
	text-align: left;
	width: 475px;
}

button * {
	vertical-align: middle;
}

h2.info 		{ background-color: #0B5586; }
h2.tipologia 		{ background-color: #4494C9; }
h2.stato		{ background-color: #377D87; }	

footer {
	background-color: #f1f1f1;
	border: none;
	border-radius: 2px;
	margin-bottom: 12px;
	overflow: hidden;
	padding: 0 10px;
}


</style>
</head>

    <body>
        <div id="wrapper">
            
        

<h3>
    Password da modificare 
</h3>


<form:form  class="form-horizontal" role ="form" modelAttribute="userGeko" method="PUT">
    
    <fieldset>
        <h2 class="info">Cambio Password</h2>
        <table width="80%">
            <tr>
                <td width="20%" align="right">Nuova Password</td>
                <td><input type="password" size="50" name='password' /></td>
            </tr>
            <tr>
                <td width="20%" align="right">Ripeti Password</td>
                <td><input type="password" size="50" name='password2' /></td>
            </tr>
            <tr>
                <td><input type="submit" style="margin: 0px 0 0 80px;" class="button" name="commit" value="Cambia"/></td>
                <td><input type="reset" style="margin: 0px 0 0 80px;" class="button" name="reset" value="Reset"/></td>
                        
            </tr> 
        </table>
    </fieldset>
        
</form:form>
</div>



<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/login.html?login_error=4"  />">Login</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. Rosario Cirrito </td>
    </tr>
</table>


</body>

</html>