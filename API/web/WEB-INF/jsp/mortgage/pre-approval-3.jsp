<!DOCTYPE html>
<html>
<head lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="viewport" content="width=device-width, initial-scale=1">    
<meta charset="utf-8">    
<title>Aqarabia</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/style_mor.css">   
<link href='https://fonts.googleapis.com/css?family=Lato:400,300,700' rel='stylesheet' type='text/css'>
<style>
body { font-family: 'Lato', sans-serif; }
</style>    
</head>

<body>
    <div class="container">
        <div class="col-sm-12">
            <p class="heading">When will you purchase a home?</p>
            <button type="button" class="click-button getrates" value="0-3 months" >0-3 months</button>
            <button type="button" class="click-button getrates" value="3-6 months">3-6 months</button>
            <button type="button" class="click-button getrates" value="6-12 months">6-12 months</button>
            <button type="button" class="click-button getrates" value="12+ months">12+ months</button>
            <button type="button" class="click-button getrates"value="I'm not sure">I'm not sure</button>
        </div>    
    </div> 
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>   
<script>
            $(".getrates").click(function () {


                var input1 = $("<input>").attr("type", "hidden").attr("name", "key").val('When will you purchase a home?');
                var input2 = $("<input>").attr("type", "hidden").attr("name", "value").val($(this).val());
                var input3 = $("<input>").attr("type", "hidden").attr("name", "approvalseq").val("pre-approval-4");
                $('<form/>').append(input1).append(input2).append(input3).attr('method', 'POST').attr('action', 'approval').submit();
            });
        </script>
</body>
</html>

