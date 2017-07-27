<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

    <head>

<meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><spring:message code="label.title" /></title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <!-- Custom CSS -->
        <link href="resources/css/style.css" rel="stylesheet">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script src="https://limonte.github.io/sweetalert2/dist/sweetalert2.min.js"></script> 
        <link rel="stylesheet" type="text/css" href="https://limonte.github.io/sweetalert2/dist/sweetalert2.css">
    </head>

    <body>
        <div class="signup-bg">
            <header class="header">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-sm-4">
                            <img src="resources/images/logo.png"/>  
                        </div><!--col-sm-4-->
                    </div>
                </div><!--container-fluid-->
            </header>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-4"></div><!--col-sm-4-->
                    <div class="col-sm-8">
                        <div class="container-fluid form-bg">
                             
                                <div class="wrap">
                                    <div class="col-sm-6 wrap" style="padding:0px;"> 
                                        <div class="signup-get-bg">
                                            <h1 class="Get">Get your free Agent Profile</h1>
                                            <div class="get-icon-1"> Help clients find you in our Find an Agent directory </div>
                                            <div class="get-icon-2"> Showcase your experience with client reviews </div>
                                            <div class="get-icon-3"> Showcase your experience with client reviews </div>
                                        </div><!--signup-form-bg-->
                                    </div><!--col-sm-6-->
                                    <div class="col-sm-6 wrap" style="padding:0px;">
                                        <div class="signup-form-bg">
                                            <h1 class="signup-heading">Create your FREE account<br><span class="signup-heading2">(All fields are required)</span></h1>
                                            <form>

                                                <input type="Email" id="username" name="username" class="easyui-textbox"  data-options="required:true" placeholder="Email"/>
                                                <input type="Password" id="password" name="password" placeholder="Password"/>

                                                <input  type="button" class="signupbutton" name="sigin" value="Login"  id="sigin"/>
                                                <h1 class="AlreadysignIn">Don't have an account yet? <a href="signup" class="AlreadysignInLink">Sign Up</a></h1>
                                            </form>
                                        </div><!--signup-form-bg-->
                                    </div><!--col-sm-6-->
                                </div><!--</div>-->    
                    
                        </div>
                    </div><!--col-sm-8-->
                </div>
            </div><!--container-fluid-->
        </div><!--signup-bg-->
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script>
            $.fn.onEnterKey =
                    function (closure) {
                        $(this).keypress(
                                function (event) {
                                    var code = event.keyCode ? event.keyCode : event.which;

                                    if (code == 13) {
                                        closure();
                                        return false;
                                    }
                                });
                    }
            $(document).ready(function () {
                $('#password').on('keypress', function (event) {
                    if (event.which == 13) {
                        login();
                    }

                });
                $("#sigin").click(function () {
                    login();
                });
                
                 $("#username,#password").keyup(function () {
                    $("#username").removeClass("input-error");
                  
                    $("#password").removeClass("input-error");
                   
                    
                });
                function login() {
                    var loginjson = {};
                    username = $("#username").val();
                    password = $("#password").val();
                    iserror=false;
                    if (username == '') {
                        $("#username").addClass("input-error");
                        iserror=true;
                    } else {
                        $("#username").removeClass("input-error");
                    }
                    if (password == '') {
                        $("#password").addClass("input-error");
                       iserror=true;
                    } else {
                        $("#password").removeClass("input-error");
                    }
                    
                    if(iserror){
                        return;
                    }

                    loginjson['userId'] = username;
                    loginjson['password'] = password;
                    var login = JSON.stringify(loginjson);
                    $.ajax({
                        url: "user/adminsignin",
                        type: "POST",
                        data: login,
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data)
                        {

                            code = data.response.code;

                            if (code == 0) {
                                window.location = "home";
                            } else if (code == 108) {
                                sweetAlert('Oops...', 'Invalid UserId!', 'error');

                            } else if (code == 109) {
                                sweetAlert('Oops...', 'Invalid Password!', 'error');

                            } else {
                                sweetAlert('Oops...', 'Something went wrong!', 'error');
                            }
                        }
                    });
                }
            });
        </script>
    </body>

</html>
