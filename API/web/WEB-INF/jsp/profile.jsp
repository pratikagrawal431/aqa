<%@include file="header.jsp" %>
<form action="updateprofile" method="post" enctype="multipart/form-data">
    <div class="container container-topMargin">
        <div class="Registration-tab">
            <div class="wrap tabbable tabs-left">
                <ul class="nav nav-tabs nav-Bottom-broder">
                    <li class="active"><a href="#a" data-toggle="tab"><spring:message code="label.profile" /></a></li>
                    <!--<li><a href="#b" data-toggle="tab">Setting</a></li>-->
                </ul>

                <div class="tab-content">
                    <div class="tab-pane active" id="a">
                        <h3 class="tab-heading"><spring:message code="label.profile" /></h3>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="group price">
                                    <label class="label"><spring:message code="label.profile.name" /></label>
                                    <input type="text" class="input-text" id="name" name="name" value="${useradmin.firstname}" autocomplete="off">
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                            <div class="col-sm-6">
                                <div class="group price">
                                    <label class="label">
                                        <spring:message code="label.profile.email" /><sup>*</sup>
                                    </label>
                                    <input type="text" class="input-text1" id="email" name="email" value="${useradmin.email}" placeholder="<spring:message code="label.profile.email.address" />">
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                        </div><!--row-->
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="group price top-buffer2">
                                    <label class="label"><spring:message code="label.profile.phone" /></label>
                                    <input type="text" class="input-text" id="phone" name="phone" value="${useradmin.phone}" autocomplete="off">
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                            <div class="col-sm-6">
                                <div class="group price top-buffer2">
                                    <label class="label">
                                        <spring:message code="label.profile.city" />
                                    </label>
                                    <input type="text" class="input-text" id="city" name="city" value="${useradmin.city}" placeholder="">
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                        </div><!--row-->
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="group price top-buffer2">
                                    <label class="label"><spring:message code="label.profile.state" /></label>
                                    <div class="Property-select">
                                        <select id="state" name="state">
                                            <option value="">-- Select One --</option>
                                            <option value="Alabama" ${useradmin.state == 'Alabama' ? 'selected' : ''}>Alabama</option>
                                            <option value="Alaska" ${useradmin.state == 'Alaska' ? 'selected' : ''}>Alaska</option>
                                            <option value="Arizona" ${useradmin.state == 'Arizona' ? 'selected' : ''}>Arizona</option>
                                            <option value="Arkansas" ${useradmin.state == 'Arkansas' ? 'selected' : ''}>Arkansas</option>
                                        </select>
                                    </div><!--Property-select-->                       </div><!--group price-->
                            </div><!--col-sm-6-->
                            <div class="col-sm-6">
                                <div class="group price top-buffer2">
                                    <label class="label"><spring:message code="label.profile.pphoto" /></label>
                                    <label for="custom-file-upload" class="filupp filupp-ProfilePic">
                                        <span class="filupp-file-name js-value"><spring:message code="label.profile.choose.file" /></span><span class="glyphicon folderIcon pull-right glyphicon-folder-open"></span>
                                        <input type="file" name="profilephoto" value="1" id="custom-file-upload">
                                    </label>
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                        </div><!--row-->
                        <!--row-->
                        <!--                            <div class="row">
                                                        <div class="col-sm-12">
                                                            <div class="group price top-buffer3">
                                                                <label class="label">Postlet Contact Information</label>
                                                                <h6>If you like, you can use a different email address & phone number as the contact information on your Postlets.</h6>
                                                            </div>group price 
                        
                                                        </div>col-sm-12
                                                    </div>row
                                                    <div class="row">
                                                        <div class="col-sm-6">
                                                            <div class="group price top-buffer2">
                                                                <label class="label">Email</label>
                                                                <input type="text" class="input-text" id="" name="" value="" placeholder="bahekar.deepak@gmail.com">
                                                            </div>group price
                                                        </div>col-sm-6
                                                        <div class="col-sm-6">
                                                            <div class="group price top-buffer2">
                                                                <label class="label">Phone</label>
                                                                <input type="text" class="input-text" id="" name="" value="" placeholder="bahekar.deepak@gmail.com">
                                                            </div>group price   
                                                        </div>col-sm-6
                                                    </div>row-->

                        <div class="row">
                            <div class="col-sm-12">
                                <div class="group price top-buffer3">
                                    <label class="label"><spring:message code="label.profile.change.pwd" /></label>
                                    <h6><spring:message code="label.profile.change.pwd.text" /></h6>
                                </div><!--group price--> 

                            </div><!--col-sm-12-->
                        </div><!--row-->
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="group price top-buffer2">
                                    <label class="label"><spring:message code="label.profile.old.pwd" /></label>
                                    <input type="password" class="input-text1" id="oldpassword" name="oldpassword" value="" placeholder="">
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                            <div class="col-sm-6">
                                <div class="group price top-buffer2">
                                    <label class="label"><spring:message code="label.profile.new.pwd" /></label>
                                    <input type="password" class="input-text1" id="password" name="password" value="">
                                </div><!--group price-->
                            </div><!--col-sm-6-->
                        </div><!--row-->

                        <div class="row">
                            <div class="col-sm-3 top-buffer3">
                                <input type="submit" class="signupbutton" id="profileupdate" value="<spring:message code="label.update" />">
                            </div><!--col-sm-6-->
                            <div class="col-sm-6">

                            </div><!--col-sm-6-->
                        </div><!--row-->

                    </div><!--tab-pane id="a"-->
                </div><!--tab-content-->    
            </div><!--wrap tabbable tabs-left-->
        </div><!--Registration-tab-->


    </div><!--container-->
</form>
<footer>
    <div class="container">
        <div class="row">
            <div class="Draft-GrayBox"></div>
        </div>
    </div><!--container-->
</footer>
<!-- jQuery -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>

<script>
    $("#profileupdate").click(function () {
        email = $('#email').val();
        if (email != '') {
            if (!validateEmail(email)) {
                sweetAlert("<spring:message code="label.oops" />", "<spring:message code="label.profile.invalid.email" />", "error");
                return false;
            }
        }

        mobile = $('#mobile').val();

//        if(mobile!=''){
//            
//            if(!validatePhone(mobile)){
//                sweetAlert("Oops...", "Invalid mobile number", "error");
//                return false;
//            }
//            
//        }

        password = '${useradmin.password}';
        oldPassword = $('#oldpassword').val();
        newPassword = $('#password').val();
        //  alert('Pwd => ' + password);
        //   alert('oldPwd => ' + oldPassword);updateprofile

        if (oldPassword != '' || newPassword != '') {
//alert(password);
//alert(oldPassword);
            if (!(password == oldPassword)) {
                sweetAlert("<spring:message code="label.oops" />", "<spring:message code="label.profile.old.pwd.iswrong" />", "error");
                return false;
            }
            if ((newPassword == '')) {
                sweetAlert("<spring:message code="label.oops" />", "<spring:message code="label.profile.new.pwd.notempty" />", "error");
                return false;
            }
            if ((newPassword == oldPassword)) {
                sweetAlert("<spring:message code="label.oops" />", "<spring:message code="label.profile.old.new.pwd.shouldsame" />", "error");
                return false;
            }
        }


        return true;
    });

    // loadagentjson('${agentdetails}');

    function loadagentjson(agentdetails) {

        if (agentdetails != "") {

            obj = $.parseJSON(agentdetails);

            var length = Object.keys(obj).length;
            if (length > 0) {
                $("#name").val(obj.firstname);
                $("#companyname").val(obj.company);
                $("#email").val(obj.email);
                $("#city").val(obj.city);
                $("#state").val(obj.state);
                $("#phone").val(obj.phone);
            }


        }
    }
</script>
</body>

</html>
