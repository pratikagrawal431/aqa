<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>Aqarabia admin</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <!-- Custom CSS -->
        <link href="/API/resources/css/style.css" rel="stylesheet">

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
                            <a href="http://devserver2.com/aqarabia/" target="_blank"><img src="resources/images/logo.png"/></a>  
                        </div><!--col-sm-4-->
                    </div>
                </div><!--container-fluid-->
            </header>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-4"></div><!--col-sm-4-->
                    <div class="col-sm-8">
                        <div class="container-fluid form-bg">
                            <div class="row">
                                <div class="wrap">
                                    <div class="col-sm-6 col-sm-height wrap" style="padding:0px;"> 
                                        <div class="signup-get-bg">
                                            <h1 class="Get">Get your free Agent Profile</h1>
                                            <div class="get-icon-1"> Help clients find you in our Find an Agent directory </div>
                                            <div class="get-icon-2"> Showcase your experience with client reviews </div>
                                            <div class="get-icon-3"> Showcase your experience with client reviews </div>
                                        </div><!--signup-form-bg-->
                                    </div><!--col-sm-6-->
                                    <div class="col-sm-6 col-sm-height wrap" style="padding:0px;">
                                        <div class="signup-form-bg">
                                            <h1 class="signup-heading">Create your FREE account<br><span class="signup-heading2">(All fields are required)</span></h1>
                                            <form>
                                                <input type="text" placeholder="Name" name="firstname" id="firstname"/>
                                                <input type="Email" placeholder="Email" name="email" id="email"/>
                                                <input type="Password" placeholder="Password" name="password" id="password"/>
                                                <input type="Password" placeholder="Confirm Password" name="confirmpassword" id="confirmpassword"/>
                                                <input type="phone" placeholder="Phone" class="numbersOnly" name="phone" id="phone"/>
                                                <input type="text" placeholder="Location" name="city" id="city"/>
                                                <div class="select-style">
                                                    <select id="services" name="services" >
                                                        <option value="">I am a...</option>
                                                        <option value="1">Agent</option>
                                                        <option value="2">Broker</option>
                                                        <option value="3">Managing Broker</option>
                                                        <option value="4">Mortgage Broker Or Lender</option>
                                                    </select>
                                                </div><!--select-style-->

                                                <input type="button" class="signupbutton" name="SignUp" value="Sign up"  id="SignUp"/>
                                                <h1 class="sending">By sending, you agree to Aqarabia <a href="#" class="signup-termslink">Terms of Use</a> and <a href="#" class="signup-termslink">Privacy Policy</a>. </h1>
                                                <h1 class="AlreadysignIn">Already a member? <a href="index" class="AlreadysignInLink">Sign In</a></h1>
                                            </form>
                                        </div><!--signup-form-bg-->
                                    </div><!--col-sm-6-->
                                </div><!--</div>-->    
                            </div><!--row-->
                        </div>
                    </div><!--col-sm-8-->
                </div>
            </div><!--container-fluid-->
        </div><!--signup-bg-->
        <!-- jQuery -->
        <script src="js/bootstrap.js"></script>
        <script src="js/npm.js"></script>
        <script src="js/jquery.min.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <script>

            function validateEmail(sEmail) {
                var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
                if (filter.test(sEmail)) {
                    return true;
                } else {
                    return false;
                }
            }

            function validatePhone(txtPhone) {
//                var filter = /^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/;
                var filter = /[0-9 -()+]+$/;
                if (filter.test(txtPhone)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            $(".numbersOnly").keypress(function (e) {
                //if the letter is not digit then display error and don't type anything
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    //display error message
                    $("#errmsg").html("Digits Only").show().fadeOut("slow");
                    return false;
                }
            });
            $(document).ready(function () {
                $("#firstname,#email,#password,#confirmpassword,#phone,#city").keyup(function () {
                    $("#firstname").removeClass("input-error");
                    $("#email").removeClass("input-error");
                    $("#password").removeClass("input-error");
                    $("#confirmpassword").removeClass("input-error");
                    $("#phone").removeClass("input-error");
                    $("#city").removeClass("input-error");
                    $("#services").removeClass("input-error");
                    
                });
                $("#SignUp").click(function () {
                    var signupjson = {};
                    signupjson['firstname'] = $("#firstname").val();
                    signupjson['user_id'] = $("#email").val();
                    signupjson['email'] = $("#email").val();
                    signupjson['password'] = $("#password").val();
                    signupjson['phone'] = $("#phone").val();
                    signupjson['city'] = $("#city").val();
                    signupjson['address1'] = $("#city").val();
                    signupjson['address2'] = $("#city").val();
                    signupjson['agentType'] = $("#services").val();
                    signupjson['logintype'] = "1";
                    signupjson['status'] = "1";
                    signupjson['userType'] = "2";

                    firstname = $("#firstname").val();
                    email = $("#email").val();
                    password = $("#password").val();
                    confirmpassword = $("#confirmpassword").val();
                    phone = $("#phone").val();
                    city = $("#city").val();
                    services = $("#services").val();
                    iserror = false;
                    if (firstname == '') {
                        $("#firstname").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#firstname").removeClass("input-error");
                    }

                    if (email == '') {
                        $("#email").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#email").removeClass("input-error");
                    }
                    if (password == '') {
                        $("#password").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#password").removeClass("input-error");
                    }

                    if (confirmpassword == '') {
                        $("#confirmpassword").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#confirmpassword").removeClass("input-error");
                    }

                    if (phone == '') {
                        $("#phone").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#phone").removeClass("input-error");
                    }

                    if (city == '') {
                        $("#city").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#city").removeClass("input-error");
                    }
                    if (services == '') {
                        $("#services").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#services").removeClass("input-error");
                    }
                    if (iserror) {
                        return;
                    }
                    if (!validateEmail(email)) {
                        sweetAlert("Oops...", "Invalid Email Id", "error");
                        return;
                    }
                    if (!validatePhone(phone)) {
                        sweetAlert("Oops...", "Invalid Phone Number", "error");
                        return;
                    }
                    if (confirmpassword == '') {
                        $("#confirmpassword").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#confirmpassword").removeClass("input-error");
                    }

                    if (!(password === confirmpassword)) {
                        sweetAlert('Oops...', 'Password and Confirm Password shoud be same', 'error');
                    }
                    var login = JSON.stringify(signupjson);

                    $.ajax({
                        url: "user/signup",
                        type: "POST",
                        data: login,
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data)
                        {

                            code = data.response.code;

                            if (code == 0) {
                                var loginjson = {};
                                loginjson['userId'] = email;
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
                            } else if (code == 100) {
                                sweetAlert('Oops...', 'User Already Exists', 'error');
                            } else {
                                sweetAlert('Oops...', 'Something went wrong!', 'error');
                            }
                        }
                    });
                });
            });
        </script>
    </body>

</html>
