<%@include file="header.jsp" %>
<!--<form action="agentdetails" method="post" enctype="multipart/form-data">-->
<div class="container container-topMargin">
    <div class="Registration-tab" style="margin-left:19px" >
        <div class="wrap tabbable tabs-left">


            <div class="tab-content">
                <div class="tab-pane active" id="a">
                    <h3 class="tab-heading"><spring:message code="label.mortgage.settings.details" /></h3>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price">
                                <label class="label"><spring:message code="label.mortgage.settings.property.tax" /><sup>*</sup></label>
                                <input type="text" class="input-text" id="property_tax" name="property_tax" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price">
                                <label class="label"><spring:message code="label.mortgage.settings.insurencetax" /><sup>*</sup></label>
                                <input type="text" class="input-text" id="insurence_tax" name="insurence_tax" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.mortgage.settings.noof.years" /><sup>*</sup>
                                </label>
                                <input type="text" class="input-text1" id="no_of_years" name="no_of_years" value="">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.mortgage.settings.rateof.intrest" /><sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly"  maxlength="13" id="rate_of_interest" name="rate_of_interest" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->

                    <div class="row">
                        <div class="col-sm-3 top-buffer3">
                            <input type="submit" class="signupbutton" id="mortgagesettings" value="<spring:message code="label.update" />">
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


    errorid = "#property_tax,#insurence_tax,#no_of_years,#rate_of_interest";
    $(errorid).keyup(function () {
        $(errorid).removeClass("input-error");
    });
    $(errorid).click(function () {
        $(errorid).removeClass("input-error");
    });
    $("#mortgagesettings").click(function () {
        var loginjson = {};

        propertyTax = $("#property_tax").val();
        insurenceTax = $("#insurence_tax").val();
        noOfYears = $("#no_of_years").val();
        rateOfInterest = $("#rate_of_interest").val();

        iserror = false;
        if (propertyTax == '') {
            $("#property_tax").addClass("input-error");
            iserror = true;
        } else {
            $("#property_tax").removeClass("input-error");
        }



        if (insurenceTax == '') {
            $("#insurence_tax").addClass("input-error");
            iserror = true;
        } else {
            $("#insurence_tax").removeClass("input-error");
        }

        if (noOfYears == '') {
            $("#no_of_years").addClass("input-error");
            iserror = true;
        } else {
            $("#no_of_years").removeClass("input-error");
        }

        if (rateOfInterest == '') {
            $("#rate_of_interest").addClass("input-error");
            iserror = true;
        } else {
            $("#rate_of_interest").removeClass("input-error");
        }


        if (iserror) {
            return;
        }
        loginjson['propertyTax'] = propertyTax;
        loginjson['insurenceTax'] = insurenceTax;
        loginjson['rateOfInterest'] = rateOfInterest;
        loginjson['noOfYears'] = noOfYears;

        url = "updatemortgageinfo";

        var mortgagedet = JSON.stringify(loginjson);
       id =getParameterByName('id');
      
        $.ajax({
            url: url+"?id="+id,
            cache: false,
            dataType: "json",
            contentType: "application/json",
            data: mortgagedet,
            type: 'post',
            success: function (data)
            {
                code = data.response.code;
                //alert(code);
                if (code == 0) {
                    swal({title: "Success", text: "Mortgageinfo Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'mortgagedetails';
                        }
                    });
                } else {
                    sweetAlert('Oops...', 'Mortgageinfo update Failed!', 'error');
                }
            }
        });
    });

    mortgagedetails('${mortgagedetails}');
    function mortgagedetails(mortgagedetails) {

        if (mortgagedetails != "") {

            obj = $.parseJSON(mortgagedetails);

            var length = Object.keys(obj).length;
            if (length > 0) {

                $("#property_tax").val(obj.propertyTax);
                $("#no_of_years").val(obj.no_of_years);
                $("#rate_of_interest").val(obj.rate_of_interest);
                $("#insurence_tax").val(obj.insurence_tax);
            }
        }
    }
</script>
</body>

</html>
