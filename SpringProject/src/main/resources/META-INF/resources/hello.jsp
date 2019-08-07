<html>
<head><style type="text/css">
    .center {
        padding: 70px 0;
        text-align: center;
    }
</style></head>
<body style="margin:auto">
<center><h1>Log Creator</h1></center>
<div class="center" >
    <div class="left" style="display: inline-block; text-align: left;">
        <form  action = "http://localhost:8080/writeToFile" method = "POST" accept-charset="ISO-8859-1">
        City: <input type = "text" name = "city">
        <br /><br>
        Detail: <input  type = "text" name = "detail" >
        <br><br>
        Type: <select name="type">
        <option value="INFO">INFO</option>
        <option value="WARN">WARN</option>
        <option value="FATAL">FATAL</option>
        <option value="DEBUG">DEBUG</option>
        <option value="ERROR">ERROR</option>
    </select>
        <br><br>
            <center><input type = "submit" value = "Submit" /></center>

    </form>
    </div>


</div>
</body>
</html>

