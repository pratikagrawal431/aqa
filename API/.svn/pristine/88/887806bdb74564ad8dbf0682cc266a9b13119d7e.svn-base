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
                <p class="heading"><strong>Well done.</strong> Now, for the last step. Please fill in these details and you'll complete the process.</p>
                <hr>
                <div class="left-inner-addon">
                    <input type="text" id="user_id" placeholder="Enter your Email Id*" />
                </div>
                <div class="left-inner-addon">
                    <input type="text" id="mobile" maxlength="13" class="numbersOnly" placeholder="Enter your Phone no*" />
                </div>

                <div class="row"> 

                    <div class="col-sm-3"><button type="button" id="mortgagesave" class="submit">Submit</button></div>
                    <div class="col-sm-6"></div>
                    <div class="col-sm-3"></div>

                </div><!--row-->
            </div>    
        </div> 
        <!-- jQuery library -->

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>    
        <script>
            $(document).ready(function () {
                //called when key is pressed in textbox
                $(".numbersOnly").keypress(function (e) {
                    //if the letter is not digit then display error and don't type anything
                    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                        //display error message
                        $("#errmsg").html("Digits Only").show().fadeOut("slow");
                        return false;
                    }
                });
            });
            $("#user_id,#mobile").keyup(function () {
                $("#user_id").removeClass("input-error");
                $("#mobile").removeClass("input-error");

            });
            function validateEmail(sEmail) {
                var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
                if (filter.test(sEmail)) {
                    return true;
                } else {
                    return false;
                }
            }
            $("#mortgagesave").click(function () {
                user_id = $("#user_id").val();
                mobile = $("#mobile").val();
                iserror = false;
                if (user_id == ''||!validateEmail(user_id)) {
                    $("#user_id").addClass("input-error");
                    iserror = true;
                } else {
                    $("#user_id").removeClass("input-error");
                }
                if (mobile == '') {
                    $("#mobile").addClass("input-error");
                    iserror = true;
                } else {
                    $("#mobile").removeClass("input-error");
                }
                if (iserror) {
                    return;
                }
                var input = $("<input>").attr("type", "hidden").attr("name", "user_id").val(user_id);
                var input1 = $("<input>").attr("type", "hidden").attr("name", "mobile").val(mobile);
                $('<form/>').append(input).append(input1).attr('method', 'POST').attr('action', 'mortgagesave').submit();
            });
        </script>
    </body>
</html>

