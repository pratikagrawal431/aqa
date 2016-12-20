<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>ResetPassword</title>
    </head>
    <body>

        <!-- Form Name -->
    <legend>Password Assistance</legend>

    <!-- Text input-->
    <div class="control-group">
        <label class="control-label" for="password">New password:</label>
        <div class="controls">
            <input id="username" name="password" type="password" placeholder="" class="input-xlarge">

        </div>
    </div>

    <!-- Password input-->
    <div class="control-group">
        <label class="control-label" for="password1">Reenter new password:</label>
        <div class="controls">
            <input id="password" name="password1" type="password" placeholder="" class="input-xlarge">

        </div>
    </div>
 <div class="controls">
     
        </div>
    <!-- Button -->
    <div class="errormessage" style="display: none;color: #ff0000">
            
        </div>
    <div class="control-group">
        <!--<label class="control-label" for="Save changes">Save changes</label>-->
        <div class="controls">
            <input type="button" name="Save changes" value="Save changes"  id="forgotpwd"/>

        </div>
    </div>



    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        $(document).ready(function () {

            $("#forgotpwd").click(function () {
                var loginjson = {};
                //loginjson['userId'] = $("#username").val();
                loginjson['newpwd'] = $("#password").val();
                loginjson['type'] = "forgot";
                loginjson['token'] = "<%= request.getParameter("token") %>";
                var login = JSON.stringify(loginjson);
                $.ajax({
                    url: "user/password",
                    type: "POST",
                    data: login,
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data)
                    {
                        code = data.response.code;
                        
                        if (code == 0) {
                            window.location = "user/forgotpassword"
                        } else if(code == 108){
                            $(".errormessage").show();
                             $(".errormessage").html("User is Inactive");
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>