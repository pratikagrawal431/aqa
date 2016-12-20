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
                <p class="heading">What are you trying to do?</p>
                <button type="button" value="I want to buy a home" class="click-button getrates">I want to buy a home</button>
                <button type="button" value="I want to refinance my home" class="click-button getrates">I want to refinance my home</button>
            </div>    
        </div> 
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>  
        <script>
            $(".getrates").click(function () {


                var input1 = $("<input>").attr("type", "hidden").attr("name", "key").val('What are you trying to do?');
                var input2 = $("<input>").attr("type", "hidden").attr("name", "value").val($(this).val());
                var input3 = $("<input>").attr("type", "hidden").attr("name", "approvalseq").val("pre-approval-2");
                $('<form/>').append(input1).append(input2).append(input3).attr('method', 'POST').attr('action', 'approval').submit();
            });
        </script>
    </body>
</html>

