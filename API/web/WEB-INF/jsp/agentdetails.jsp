<%@include file="header.jsp" %>
<!--<form action="agentdetails" method="post" enctype="multipart/form-data">-->
<div class="container container-topMargin">
    <div class="Registration-tab1" style="margin-left:3.5%;">
        <div class="wrap tabbable tabs-left">
            <!--                        <ul class="nav nav-tabs nav-Bottom-broder">
                                        <li class="active"><a href="#a" data-toggle="tab">Profile</a></li>
                                        <li><a href="#b" data-toggle="tab">Setting</a></li>
                                    </ul>-->

            <div class="tab-content">
                <div class="tab-pane active" id="a">
                    <div class="row"><!--Extra row for responsive starts -->				
                    <div class="col-sm-6">
					<h3 class="tab-heading">Agent Details</h3>
               </div>
			   </div> 
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price">
                                <label class="label">Name<sup>*</sup></label>
                                <input type="text" class="input-text" id="name" name="name" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price">
                                <label class="label">Company Name<sup>*</sup></label>
                                <input type="text" class="input-text" id="companyname" name="companyname" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    Email Address<sup>*</sup>
                                </label>
                                <input type="text" class="input-text1" id="email" name="email" value="">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Phone<sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly"  maxlength="13" id="phone" name="phone" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">State</label>
                                <div class="Property-select">
                                    <select id="state" name="state">
                                        <option value="">-- Select One --</option>
<!--                                        <option value="Alabama">Alabama</option>
                                        <option value="Alaska">Alaska</option>
                                        <option value="Arizona">Arizona</option>
                                        <option value="Arkansas">Arkansas</option>-->
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">City*</label>
                                <div class="Property-select">
                                    <select id="city" name="city">
                                        <option value="">-- Select One --</option>
<!--                                        <option value="Alabama">Alabama</option>
                                        <option value="Alaska">Alaska</option>
                                        <option value="Arizona">Arizona</option>
                                        <option value="Arkansas">Arkansas</option>-->
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Password<sup>*</sup></label>
                                <input type="text" class="input-text1" id="password" name="password" value="">
                            </div><!--group price-->
                        </div><!--col-sm-6--> <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Agent Type<sup>*</sup></label>
                                <div class="Property-select">
                                    <select id="agenttype" name="agenttype" >
                                        <option value="">I am a...</option>
                                        <option value="1">Agent</option>
                                        <option value="2">Broker</option>
                                        <option value="3">Managing Broker</option>
                                        <option value="4">Mortgage Broker Or Lender</option>
                                        <option value="5">Property managers</option>
                                        <option value="6">Contractors</option>
                                        <option value="7">Home Inspectors</option>
                                        <option value="8">Appraisers</option>
                                        <option value="9">Builders / Developers</option>
                                        <option value="10">Stagers</option>
                                        <option value="11">Landlords</option>
                                        <option value="12">Other Pros</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Agent Specialty<sup>*</sup></label>
                                <div class="Property-select">
                                    <select id="agentspecialty" name="agentspecialty" >
                                        <option value="">-- Select Specialty --</option>
                                        <option value="2">REO / Bank owned</option>
                                        <option value="3">Short sales</option>
                                        <option value="4">Residential sales</option>
                                        <option value="5">Luxury homes</option>
                                        <option value="6">First time home buyers</option>
                                        <option value="7">Distressed properties</option>
                                        <option value="8">House properties</option>
                                        <option value="9">Probate</option>
                                        <option value="10">Staging</option>
                                        <option value="11">Relocation</option>
                                        <option value="12">Property management</option>
                                        <option value="13">TIC</option>
                                        <option value="14">Co-op</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6--> 
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Languages<sup>*</sup></label>
                                <div class="Property-select">
                                    <select id="language" name="language">
                                        <option value="">-- Select Language --</option>
                                        <option value="2">English</option>
                                        <option value="3">Arabic</option>
                                        <option value="4">Spanish</option>
                                        <option value="5">French</option>
                                        <option value="6">Mandarin</option>
                                        <option value="7">Russian</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Property Expertise<sup>*</sup></label>
                                <div class="Property-select">
                                    <select id="propertyExpertise" name="propertyExpertise">
                                        <option value="">-- Select Property Expertise --</option>
                                        <option value="2">Single-family home</option>
                                        <option value="3">Condo / townhouse</option>
                                        <option value="4">Multi-family home</option>
                                        <option value="5">Residential rental</option>
                                        <option value="6">TIC</option>
                                        <option value="7">Co-op</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Certifications</label>
                                <div class="Property-select">
                                    <select id="certification" name="certification">
                                        <option value="">-- Select Certification --</option>
                                        <option value="1">SRES</option>
                                        <option value="2">GRI</option>
                                        <option value="3">ABR</option>
                                        <option value="4">SFR</option>
                                        <option value="5">GREEN</option>
                                        <option value="6">CRB</option>
                                        <option value="7">CPM</option>
                                        <option value="8">CRS</option>
                                        <option value="9">ALC</option>
                                        <option value="10">CCO</option>
                                        <option value="11">CIPS</option>
                                        <option value="12">e-PRO</option>
                                        <option value="13">BPOR</option>
                                        <option value="14">PMN</option>
                                        <option value="15">CCIM</option>
                                        <option value="16">ABRM</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Keywords</label>
                                <div class="Property-select">
                                    <input type="text" class="input-text" id="keywords" name="keywords" value="" autocomplete="">
                                </div><!--Property-select-->
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">About</label>
                                <div >
                                    <textarea  class="input-text1" id="about" name="about" value="" autocomplete=""></textarea>
                                </div><!--Property-select-->
                            </div><!--group price-->

                        </div><!--col-sm-6-->


                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">Profile Photo</label>
                                <!--<label for="custom-file-upload" class="filupp filupp-ProfilePic">-->
                                <!--<span class="filupp-file-name js-value">Choose File</span><span class="glyphicon folderIcon pull-right glyphicon-folder-open"></span>-->
                                <!--<input type="file" name="file"  id="custom-file-upload">-->
                                <input type="file" style=" border: 2px solid #d2d2d2; width:100%; padding:1em;" name="file"  id="custom-file-upload">
                                </br>
                                <div class="gallery-img2">
                                    <img src="resources/images/user-img.jpg">
                                </div>
                                <!--</label>-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                </div><!--row-->


                <div class="row">
                    <div class="col-md-3 col-sm-6 top-buffer3">
                        <input type="submit" class="signupbutton" id="agentdetails" value="Save">
                    </div><!--col-sm-6-->
                    <div class="col-sm-6">

                    </div><!--col-sm-6-->
                </div><!--row-->

            </div><!--tab-pane id="a"-->
        </div><!--tab-content-->    
    </div><!--wrap tabbable tabs-left-->
</div><!--Registration-tab-->


</div><!--container-->
<!--</form>-->
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
    loadstates();
    $( "#state" ).change(function() {
  //alert($("#state").val());
  loadcity($("#state").val());
});
     function loadcity(state) {
         $("#city option").remove();
        $.ajax({
            url: "getcities?state="+state,
            type: "GET",
            dataType: "json",
            async: false,
            contentType: "application/json",
            success: function (response)
            {
               
                response = response.response.cities;

                $.each(response, function (idx, rec) {
                    $('<option/>', {
                        'value': rec.id,
                        'text': rec.name
                    }).appendTo('#city');
                })

            }
        });
    }

 
    
    function loadstates() {
        $.ajax({
            url: "getstates",
            type: "GET",
            dataType: "json",
            async: false,
            contentType: "application/json",
            success: function (response)
            {
                
                response = response.response.states;

                $.each(response, function (idx, rec) {
                    $('<option/>', {
                        'value': rec.id,
                        'text': rec.name
                    }).appendTo('#state');
                })

            }
        });
    }

    id = getParameterByName("id");
    if (id != "") {
        $("#email").attr("disabled", "true");
    }
    errorid = "#name,#companyname,#email,#phone,#city,#state,#password,#agenttype,#agentspecialty,#language,#certification,#propertyExpertise";
    $(errorid).keyup(function () {
        $(errorid).removeClass("input-error");

        //$("#formatted_address").removeClass("input-error");


    });
    $(errorid).click(function () {
        $(errorid).removeClass("input-error");

        //$("#formatted_address").removeClass("input-error");


    });
    $("#agentdetails").click(function () {
        var loginjson = {};
        id = getParameterByName("id");
        name = $("#name").val();
        companyname = $("#companyname").val();
        email = $("#email").val();
        phone = $("#phone").val();
       // city = $("#city").val();
        city = $('#city option:selected').text();
        state = $('#state option:selected').text()
        
       
        password = $("#password").val();
        agenttype = $("#agenttype").val();
        

        agentspecialty = $("#agentspecialty").val();
        language = $("#language").val();
        propertyExpertise = $("#propertyExpertise").val();
        certification = $("#certification").val();
        keywords = $("#keywords").val();
        about = $("#about").val();
        iserror = false;
        if (password == '') {
            $("#password").addClass("input-error");
            iserror = true;
        } else {
            $("#password").removeClass("input-error");
        }



        if (city == '') {
            $("#city").addClass("input-error");
            iserror = true;
        } else {
            $("#city").removeClass("input-error");
        }

        if (phone == '') {
            $("#phone").addClass("input-error");
            iserror = true;
        } else {
            $("#phone").removeClass("input-error");
        }

        if (email == '') {
            $("#email").addClass("input-error");
            iserror = true;
        } else {
            $("#email").removeClass("input-error");
        }

        if (name == '') {
            $("#name").addClass("input-error");
            iserror = true;
        } else {
            $("#name").removeClass("input-error");
        }
        if (companyname == '') {
            $("#companyname").addClass("input-error");
            iserror = true;
        } else {
            $("#companyname").removeClass("input-error");
        }

        if (agentspecialty == '') {
            $("#agentspecialty").addClass("input-error");
            iserror = true;
        } else {
            $("#agentspecialty").removeClass("input-error");
        }
        if (language == '') {
            $("#language").addClass("input-error");
            iserror = true;
        } else {
            $("#language").removeClass("input-error");
        }
        if (propertyExpertise == '') {
            $("#propertyExpertise").addClass("input-error");
            iserror = true;
        } else {
            $("#propertyExpertise").removeClass("input-error");
        }
        if (agenttype == '') {
            $("#agenttype").addClass("input-error");
            iserror = true;
        } else {
            $("#agenttype").removeClass("input-error");
        }
        if (iserror) {
            return;
        }
        loginjson['firstname'] = name;
        loginjson['companyname'] = companyname;
        loginjson['user_id'] = email;
        loginjson['email'] = email;
        loginjson['phone'] = phone;
        loginjson['city'] = city;
        loginjson['state'] = state;
        loginjson['password'] = password;
        loginjson['agentType'] = agenttype;
        loginjson['agentSpecialty'] = agentspecialty;
        loginjson['propertyExpertise'] = propertyExpertise;
        loginjson['certification'] = certification;
        loginjson['keywords'] = keywords;
        loginjson['about'] = about;
        loginjson['language'] = language;
        loginjson['logintype'] = "2";
        loginjson['status'] = "1";
        loginjson['userType'] = "2";

        url = "agentCreate"
        if (id != '') {
            loginjson['id'] = id;
            url = "agent/agentupdate";
        }
        var login = JSON.stringify(loginjson);
        var file_data = $("#custom-file-upload").prop("files")[0];
        var form_data = new FormData();
//        alert(file_data);

        form_data.append("file[]", file_data)
        form_data.append("login", login);
        $.ajax({
            url: url,
            cache: false,
            enctype: "multipart/form-data",
            contentType: false,
            processData: false,
            data: form_data,
            type: 'post',
            success: function (data)
            {
                //   alert(data);
                code = data.response.code;
                //alert(code);
                if (code == 0) {
//                    swal({title: "Success", text: "Agent Created Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
                    if (id != '') {
                        swal({title: "Success", text: "Agent Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                if ('${useradmin.userType}' == 3)
                                    window.location = 'agentlist';
                                else
                                    window.location = 'userprofile?id=' + id;
                            }
                        });
                    } else {
                        swal({title: "Success", text: "Agent Created Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                window.location = 'agentlist';
                            }
                        });
                    }
                } else if (code == 108) {
                    sweetAlert('Oops...', 'Invalid UserId!', 'error');
                } else if (code == 109) {
                    sweetAlert('Oops...', 'Invalid Password!', 'error');

                } else if (code == 100) {
                    sweetAlert('Oops...', 'Agent Already Exists!', 'error');

                } else {
                    sweetAlert('Oops...', 'Something went wrong!', 'error');
                }
            }
        });
    });

    loadagentjson('${agentdetails}');
    function loadagentjson(agentdetails) {
        if (agentdetails != "") {

            obj = $.parseJSON(agentdetails);

            var length = Object.keys(obj).length;
            if (length > 0) {
               
                $("#name").val(obj.firstname);
                $("#companyname").val(obj.company);
                $("#email").val(obj.email);
              // alert(obj.city);
               
                $("#state").val(obj.state);
                loadcity(obj.state);
                 $("#city").val(obj.city);
                //alert(obj.city);
                $("#phone").val(obj.phone);
                $("#password").val(obj.password);
                $("#agenttype").val(obj.agent_type);
                if (obj.agent_specialty != 0)
                    $("#agentspecialty").val(obj.agent_specialty);
                if (obj.property_expertise != 0)
                    $("#propertyExpertise").val(obj.property_expertise);
                if (obj.certifications != 0)
                    $("#certification").val(obj.certifications);
                $("#keywords").val(obj.keywords);
                $("#about").val(obj.about);
                if (obj.languages != 0)
                    $("#language").val(obj.languages);
             
                if(obj.profilePicture!=''&&obj.profilePicture!= undefined){
                 $(".gallery-img2").html("<img src=\""+obj.profilePicture+"\">");
             }
            }
        }
    }
</script>
</body>

</html>
