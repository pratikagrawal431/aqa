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
                <p class="heading">What do you plan to spend on your new home?</p>
                <div class="left-inner-addon"> 
                    <span>$</span>
                    <input type="text" class="numbersOnly" id="spent" />
                </div>
                <button type="button" class="click-button getrates">Next</button>
            </div>    
        </div> 
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>     
        <script>
            $("#spent").keyup(function () {
                    $("#spent").removeClass("input-error");
                   
                });
            $(".numbersOnly").keypress(function (e) {
                    //if the letter is not digit then display error and don't type anything
                    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                        //display error message
                        $("#errmsg").html("Digits Only").show().fadeOut("slow");
                        return false;
                    }
                });
            $(".getrates").click(function () {
                iserror = false;
                spent=$("#spent").val();
 if (spent == '') {
                        $("#spent").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#spent").removeClass("input-error");
                    }
                if(iserror){
                    return ;
                }
                var input1 = $("<input>").attr("type", "hidden").attr("name", "key").val('What do you plan to spend on your new home?');
                var input2 = $("<input>").attr("type", "hidden").attr("name", "value").val($("#spent").val()+" $");
                var input3 = $("<input>").attr("type", "hidden").attr("name", "approvalseq").val("pre-approval-9");
                $('<form/>').append(input1).append(input2).append(input3).attr('method', 'POST').attr('action', 'approval').submit();
            });
        </script>
    </body>
</html>

