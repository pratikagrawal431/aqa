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
                                            <h1 class="Get"><spring:message code="label.h1" /></h1>
                                            <div class="get-icon-1"> <spring:message code="label.h1.div1" /> </div>
                                            <div class="get-icon-2"> <spring:message code="label.h1.div2" /> </div>
                                            <div class="get-icon-3"> <spring:message code="label.h1.div3" /> </div>
                                        </div><!--signup-form-bg-->
                                    </div><!--col-sm-6-->
                                    <div class="col-sm-6 wrap" style="padding:0px;">
                                        <div class="signup-form-bg">
                                            <h1 class="signup-heading"><spring:message code="label.create.account" /><br><span class="signup-heading2"><spring:message code="label.create.account.mandatory" /></span></h1>
                                            <form>

                                                <input type="Email" id="username" name="username" class="easyui-textbox"  data-options="required:true" placeholder="<spring:message code="label.email" />"/>
                                                <input type="Password" id="password" name="password" placeholder="<spring:message code="label.password" />"/>

                                                <input  type="button" class="signupbutton" name="sigin" value=<spring:message code="label.login.button" />  id="sigin"/>
                                                <h1 class="AlreadysignIn"><spring:message code="label.issignup.text" /> <a href="signup" class="AlreadysignInLink"><spring:message code="label.signup" /></a></h1>
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
