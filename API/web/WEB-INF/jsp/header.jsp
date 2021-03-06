<!DOCTYPE html>
<html lang="en">
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ page  isELIgnored="false" %> 
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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

        <link href="resources/css/style.css" rel="stylesheet">
        <link href="resources/css/calendar.css" rel="stylesheet">
        <script src="http://t4t5.github.io/sweetalert/dist/sweetalert-dev.js"></script>
<link rel="stylesheet" type="text/css" href="http://t4t5.github.io/sweetalert/dist/sweetalert.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <!--        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">-->
        <!--        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">-->
        <!--        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo.css">-->
        <!--        <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.min.js"></script>-->
        <script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
        <script src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;libraries=places"></script>
        <script src="resources/js/jquery.geocomplete.js"></script>
        <!--        <script src="resources/js/ajaxfileupload.js"></script>-->

        <link rel="stylesheet" type="text/css" href="resources/styles.css" />
        <link rel="stylesheet" type="text/css" href="resources/css/easyui.css">
        <script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
        <link href="resources/css/jquery.filer.css" type="text/css" rel="stylesheet" />
        <link href="resources/css/jquery.filer-dragdropbox-theme.css" type="text/css" rel="stylesheet" />

        <!--jQuery-->



    <body style="background:#f0f0f0;">
        <nav class="navbar navbar-default navbar-fixed-top">
            <!--<div class="container">-->
            <div class="fluid-container" style="padding-left:20px; padding-right:20px;">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand brand-logo" href="http://devserver2.com/aqarabia/" target="_blank"><img src="resources/images/logo.png"/></a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">

                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><div class="userimg-div">

                                    <c:if test="${useradmin.profilePicture !=null }">
                                        <img src="${useradmin.profilePicture}" width="30" height="30"/>
                                    </c:if>
                                    <c:if test="${useradmin.profilePicture ==null }">
                                        <img src="resources/images/user-img.jpg" width="30" height="30"/> 
                                    </c:if>
                                    <span class="caret"></span></div></a>

                            <ul class="dropdown-menu">
                                <c:if test="${useradmin.userType eq 2}">
                                    <li><a href="userprofile?id=${useradmin.agentId}">Profile</a></li>
                                    </c:if>
                                    <c:if test="${useradmin.userType eq 3}">
                                    <li><a href="profile">Profile</a></li>
                                    </c:if>                                
                                <li><a href="logout">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="home">List Properties &nbsp;|</a></li>
                        <li><a href="showproperty">Add Property &nbsp;|</a></li>
                        <c:if test="${useradmin.userType eq 3}">
                            <li><a href="agentlist">List Agents &nbsp;|</a></li>
                            <li><a href="agentdetails">Add Agent &nbsp;|</a></li>
                            <li><a href="homewortlist">Home Worth &nbsp;|</a></li>
                            <li><a href="mortgagelist">Mortgage List &nbsp;|</a></li>
                            <li><a href="mortgagedetails">Mortgage Settings &nbsp;|</a></li>
                            <li><a href="currenysettingdet">Currency Settings</a></li></c:if>
                         <li><a href="requestproperty">&nbsp;|&nbsp; Requested Property Info</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>

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

            function activateProperty(id) {

                $.ajax({
                    url: "activateproperty?id=" + id,
                    type: "GET",
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data)
                    {

                        code = data.response.code;
                        if (code == 0) {
//                       swal("Success", "Property Acticated Successfully", "success")
                            swal({title: "Success", text: "Property Activated Successfully", imageUrl: "resources/images/thumbs-up.jpg"}, function (isConfirm) {
                                if (isConfirm) {
                                    window.location = 'home';
                                }
                            });
                        } else if (code == 121) {
                            sweetAlert("Activation Failed...", "Price is mandatory to activate property", "error");
                        } else if (code == 122) {
                            sweetAlert("Activation Failed...", "Address is mandatory to activate property", "error");
                        } else if (code == 123) {
                            sweetAlert("Activation Failed...", "Name is mandatory to activate property", "error");
                        } else if (code == 124) {
                            sweetAlert("Activation Failed...", "Email is mandatory to activate property", "error");
                        } else if (code == 125) {
                            sweetAlert("Activation Failed...", "Mobile is mandatory to activate property", "error");
                        } else if (code == 126) {
                            sweetAlert("Activation Failed...", "Contact details is mandatory to activate property", "error");
                        }

                    }
                });
            }
            function deactivateProperty(id) {


                swal({title: "Are you sure?", text: "You want to Deactivated Property!", type: "warning", showCancelButton: true, confirmButtonColor: "#DD6B55", confirmButtonText: "Yes!", closeOnConfirm: false}, function () {
                    $.ajax({
                        url: "deactivateproperty?id=" + id,
                        type: "GET",
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data)
                        {
                            code = data.response.code;
                            if (code == 0) {
                                swal({title: "Success", text: "Property Deactivated Successfully", imageUrl: "resources/images/thumbs-up.jpg"}, function (isConfirm) {
                                    if (isConfirm) {
                                        window.location = 'home';
                                    }
                                });
                            } else {
                                sweetAlert("Deactivation Failed...", "", "error");
                            }
                        }
                    });

                });
            }

   function deleteProperty(id) {


                swal({title: "Are you sure?", text: "You want to Delete Property!", type: "warning", showCancelButton: true, confirmButtonColor: "#DD6B55", confirmButtonText: "Yes!", closeOnConfirm: false}, function () {
                    $.ajax({
                        url: "deleteProperty?id=" + id,
                        type: "GET",
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data)
                        {
                            code = data.response.code;
                            if (code == 0) {
                                swal({title: "Success", text: "Property Deleted Successfully", imageUrl: "resources/images/thumbs-up.jpg"}, function (isConfirm) {
                                    if (isConfirm) {
                                        window.location = 'home';
                                    }
                                });
                            } else {
                                sweetAlert("Deactivation Failed...", "", "error");
                            }
                        }
                    });

                });
            }

            function getParameterByName(name) {
                name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                        results = regex.exec(location.search);
                return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
            }

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

            function pagetab(tid) {
                //$('.tab-pane').hide();
                //$('#'+tid).show();
                $('.tab-pane').removeClass('active');
                $('#' + tid).addClass('active');

                $('.nav li').removeClass('active');
                $("a[href='#" + tid + "']").parent('li').addClass('active');
                //alert(nav);
            }

        </script>

