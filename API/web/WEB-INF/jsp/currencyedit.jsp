<%@include file="header.jsp" %>
<!--<form action="agentdetails" method="post" enctype="multipart/form-data">-->
<div class="container container-topMargin">
    <div class="Registration-tab" style="margin-left:19px" >
        <div class="wrap tabbable tabs-left">


            <div class="tab-content">
                <div class="tab-pane active" id="a">
                    <h3 class="tab-heading">Currency Details</h3>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price">
                                <label class="label">Currency<sup>*</sup></label>
                                <input type="text" class="input-text" id="currency" name="currency" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price">
                                <label class="label">Multiplication Factor<sup>*</sup></label>
                                <input type="text" class="input-text" id="mul_fact" name="mul_fact" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                   
                      <div class="row">
                        <div class="col-sm-3 top-buffer3">
                            <input type="submit" class="signupbutton" id="mortgagesettings" value="Update">
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


    errorid = "#currency,#mul_fact";
    $(errorid).keyup(function () {
        $(errorid).removeClass("input-error");
    });
    $(errorid).click(function () {
        $(errorid).removeClass("input-error");
    });
    $("#mortgagesettings").click(function () {
        var loginjson = {};

        mul_fact = $("#mul_fact").val();
        currency = $("#currency").val();
     
        iserror = false;
        if (currency == '') {
            $("#currency").addClass("input-error");
            iserror = true;
        } else {
            $("#currency").removeClass("input-error");
        }



        if (mul_fact == '') {
            $("#mul_fact").addClass("input-error");
            iserror = true;
        } else {
            $("#mul_fact").removeClass("input-error");
        }

        
        if (iserror) {
            return;
        }
        loginjson['mul_fact'] = mul_fact;
        loginjson['currency'] = currency;
       url = "updatecurrencyinfo";

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
                    swal({title: "Success", text: "Currency Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'currenysettingdet';
                        }
                    });
                } else {
                    sweetAlert('Oops...', 'Currency update Failed!', 'error');
                }
            }
        });
    });

    currencydetpage('${currencydet}');
    function currencydetpage(currencydet) {

        if (currencydet != "") {

            obj = $.parseJSON(currencydet);

            var length = Object.keys(obj).length;
            if (length > 0) {

                $("#currency").val(obj.currency);
                $("#mul_fact").val(obj.multiplication_factor);
            }
        }
    }
</script>
</body>

</html>
