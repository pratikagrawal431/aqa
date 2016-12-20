<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login</title>
    </head>
    <body>

        <!-- Form Name -->
    <legend>login</legend>

    <!-- Text input-->
    <div class="control-group">
        <label class="control-label" for="username">Username</label>
        <div class="controls">
            <input id="username" name="username" type="text" placeholder="" class="input-xlarge">

        </div>
    </div>

    <!-- Password input-->
    <div class="control-group">
        <label class="control-label" for="password">Password</label>
        <div class="controls">
            <input id="password" name="password" type="password" placeholder="" class="input-xlarge">

        </div>
    </div>

    <!-- Button -->
    <div class="control-group">
        <label class="control-label" for="SignIn">Sign In</label>
        <div class="controls">
            <input type="button" name="SignIn" value="SignIn"  id="sigin"/>

        </div>
    </div>



    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        $(document).ready(function () {

            $("#sigin").click(function () {
                var loginjson = {};
                loginjson['userId'] = $("#username").val();
                loginjson['password'] = $("#password").val();
                var login = JSON.stringify(loginjson);
                $.ajax({
                    url: "user/signin",
                    type: "POST",
                    data: login,
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data)
                    {

                        code = data.response.code;
                        if (code == 0) {
                            alert("sd");
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>