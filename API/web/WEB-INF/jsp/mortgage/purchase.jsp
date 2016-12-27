<!DOCTYPE html>
<html>
    <head lang="en">
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <meta name="viewport" content="width=device-width, initial-scale=1">    
        <meta charset="utf-8">    
        <title>Aqarabia</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/style_mor.css">   
        <link href='https://fonts.googleapis.com/css?family=Lato:400,300,700' rel='stylesheet' type='text/css'>
        <style>
            body { font-family: 'Lato', sans-serif; }
        </style>    
    </head>

    <body>
        <div class="container">
            <div class="col-sm-12" style="padding-left:0px; padding-right:0px;">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#home">Purchase</a></li>
                    <li><a href="#menu1">Refinance</a></li>
                    <li><a href="#menu2">Pre-approval</a></li>
                </ul>
                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        <div class="input-title">Pin code</div>
                        <div class="purchase-addon">
                            <input type="text" id="pincode" class="json numbersOnly" name="Pin code" />
                            <span class="glyphicon glyphicon-map-marker"></span>
                        </div>
                        <div class="input-title">Purchase price</div>
                        <div class="left-inner-addon">
                            <span>$</span>
                            <input type="text" id="purchaseprice" class="json numbersOnly" name="Purchase price" />
                        </div>
                        <div class="input-title">Down payment</div>
                        <div class="left-inner-addon">
                            <span>$</span>
                            <input type="text"  id="Down_payment1" class="input-down-payment numbersOnly" />
                        </div>
                        <div class="left-inner-addon">
                            <input class="input-down-payment2 json numbersOnly" name="Down payment" id="Down_payment2"  type="text" maxlength="2" value="20" /><span class="input-persent">%</span>
                        </div>

                        <div class="input-title2">Credit score <button class="btn btn-danger credit-score btn-md">?</button></div>
                        <div class="left-inner-addon">
                            <div class="select-style">
                                <select name="Credit score" id="creditscore" >
                                    <option>720-739</option>
                                    <option>720-639</option>
                                </select>
                            </div><!--select-style-->
                        </div>

                        <div class="div-checkbok">
                            <label><input type="checkbox" name="GCC national" id="ggcnan" /> GCC national</label><br />
                        </div>

                        <div class="accordion-group">
                            <div id="collapseTwo" class="accordion-body collapse">
                                <div class="accordion-inner">
                                    <div class="input-title">Annual Income</div>
                                    <div class="left-inner-addon">
                                        <span>$</span>
                                        <input type="text" class="json numbersOnly" name="Annual Income" />
                                    </div>
                                    <div class="input-title">Monthly debts <button class="btn btn-danger monthly-debts btn-md">?</button></div>
                                    <div class="left-inner-addon">
                                        <span>$</span>
                                        <input type="text" name="Monthly debts" class="json numbersOnly" />
                                    </div>
                                    <div class="input-title2">Property type</div>
                                    <div class="left-inner-addon">
                                        <div class="select-style">
                                            <select name="services" id="propertytype">
                                                <option>Single family home</option>
                                                <option>Single family home</option>
                                            </select>
                                        </div><!--select-style-->
                                    </div>
                                    <div class="input-title2">How is home used?</div>
                                    <div class="left-inner-addon">
                                        <div class="select-style">
                                            <select name="services" id="howhome" >
                                                <option>Primary residence</option>
                                                <option>Primary residence</option>
                                            </select>
                                        </div><!--select-style-->
                                    </div>
                                    <div class="input-title2">First-time buyer?</div>
                                    <div class="left-inner-addon">
                                        <div class="select-style">
                                            <select name="services"id="firsttime" >
                                                <option>No</option>
                                                <option>Yes</option>
                                            </select>
                                        </div><!--select-style-->
                                    </div>
                                    <div class="input-title2">New Construction?</div>
                                    <div class="left-inner-addon">
                                        <div class="select-style">
                                            <select name="services"id="newcon" >
                                                <option>No</option>
                                                <option>Yes</option>
                                            </select>
                                        </div><!--select-style-->
                                    </div>
                                    <div class="div-checkbok">
                                        <label><input type="checkbox" name="Filed bankrupty?" class="bank" /> Filed bankrupty?</label><br />
                                        <label><input type="checkbox" name="Had a foreclosure?" class="fore"/> Had a foreclosure?</label><br />
                                        <label><input type="checkbox" name="Self-employed?" class="self" /> Self-employed?</label><br />
                                        <div class="input-title2">Desired loan programs?</div>
                                        <label><input type="checkbox" name="desired" value="30 year fixed" /> 30 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="15 year fixed" /> 15 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="5/1 ARM" /> 5/1 ARM</label><br />
                                        <label><input type="checkbox" name="desired" value="20 year fixed" /> 20 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="10 year fixed"/> 10 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="7/1 ARM"/> 7/1 ARM</label><br />

                                    </div>    	
                                </div><!--accordion-inner-->
                            </div>
                            <button class="SeeMore2" data-toggle="collapse" href="#collapseTwo">Advanced &nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-chevron-down"></span></button>
                            <div class="row" align="center"> 

                                <div class="col-sm-12" align="center"><button type="button" id="getrates" class="get-rates">Get Rates</button></div>

                            </div><!--row-->

                        </div>

                    </div><!--home-->
                    <div id="menu1" class="tab-pane fade">
                        <div class="input-title">Pin code</div>
                        <div class="purchase-addon">
                            <input type="text" id="pincode1" class="json1 numbersOnly" name="Pin code" />
                            <span class="glyphicon glyphicon-map-marker"></span>
                        </div>
                        <div class="input-title">Property value</div>
                        <div class="left-inner-addon">
                            <span>$</span>
                            <input type="text" id="purchaseprice1" class="json1 numbersOnly" name="Property value" />
                        </div>
                        <div class="input-title">Current balance</div>
                        <div class="left-inner-addon">
                            <span>$</span>
                            <input type="text"  id="currentbalance1" class="input-down-payment numbersOnly" />
                        </div>
                        <div class="left-inner-addon">
                            <input class="input-down-payment2 json numbersOnly" name="Current balance" id="currentbalance2"  type="text" maxlength="2" value="20" /><span class="input-persent">%</span>
                        </div>

                        <div class="input-title2">Credit score <button class="btn btn-danger credit-score btn-md">?</button></div>
                        <div class="left-inner-addon">
                            <div class="select-style">
                                <select name="Credit score" id="creditscore1" >
                                    <option>720-739</option>
                                    <option>720-639</option>
                                </select>
                            </div><!--select-style-->
                        </div>

                        <div class="div-checkbok">
                            <label><input type="checkbox" name="GCC national" id="ggcnan1" /> GCC national</label><br />
                        </div>

                        <div class="accordion-group">
                            <div id="collapseTwo2" class="accordion-body collapse">
                                <div class="accordion-inner">
                                    <div class="input-title">Annual Income</div>
                                    <div class="left-inner-addon">
                                        <span>$</span>
                                        <input type="text" class="json1 numbersOnly" name="Annual Income" />
                                    </div>
                                    <div class="input-title">Monthly debts <button class="btn btn-danger monthly-debts btn-md">?</button></div>
                                    <div class="left-inner-addon">
                                        <span>$</span>
                                        <input type="text" name="Monthly debts" class="json1 numbersOnly" />
                                    </div>
                                    <div class="input-title2">Property type</div>
                                    <div class="left-inner-addon">
                                        <div class="select-style">
                                            <select name="services" id="propertytype1">
                                                <option>Single family home</option>
                                                <option>Single family home</option>
                                            </select>
                                        </div><!--select-style-->
                                    </div>
                                    <div class="input-title2">How is home used?</div>
                                    <div class="left-inner-addon">
                                        <div class="select-style">
                                            <select name="services" id="howhome1" >
                                                <option>Primary residence</option>
                                                <option>Primary residence</option>
                                            </select>
                                        </div><!--select-style-->
                                    </div>
                                    <div class="input-title2">Cash out</div>
                                    <div class="left-inner-addon">
                                        <span>$</span>
                                        <input type="text" name="Cash out" class="json1 numbersOnly" />
                                    </div>

                                    <div class="div-checkbok">
                                        <label><input type="checkbox" name="Filed bankrupty?" class="bank" /> Filed bankrupty?</label><br />
                                        <label><input type="checkbox" name="Had a foreclosure?" class="fore"/> Had a foreclosure?</label><br />
                                        <label><input type="checkbox" name="Self-employed?" class="self" /> Self-employed?</label><br />
                                        <div class="input-title2">Desired loan programs?</div>
                                        <label><input type="checkbox" name="desired" value="30 year fixed" /> 30 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="15 year fixed" /> 15 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="5/1 ARM" /> 5/1 ARM</label><br />
                                        <label><input type="checkbox" name="desired" value="20 year fixed" /> 20 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="10 year fixed"/> 10 year fixed</label><br />
                                        <label><input type="checkbox" name="desired" value="7/1 ARM"/> 7/1 ARM</label><br />

                                    </div>    	
                                </div><!--accordion-inner-->
                            </div>
                            <button class="SeeMore2" data-toggle="collapse" href="#collapseTwo2">Advanced &nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-chevron-down"></span></button>
                            <div class="row" align="center"> 

                                <div class="col-sm-12" align="center"><button type="button" id="getrates1" class="get-rates">Get Rates</button></div>

                            </div><!--row-->

                        </div>
                    </div>
                    <div id="menu2" class="tab-pane fade">
                        <div class="row" align="center"> 

                            <div class="col-sm-12" align="center"><button type="button" id="getrates2" class="get-rates">Get Rates</button></div>

                        </div><!--row-->

                    </div>
                </div>


            </div>
        </div> 
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> 

        <script>
            $(document).ready(function () {
                $(".nav-tabs a").click(function () {
                    $(this).tab('show');
                });
                $('.nav-tabs a').on('shown.bs.tab', function (event) {
                    var x = $(event.target).text();         // active tab
                    var y = $(event.relatedTarget).text();  // previous tab
                    $(".act span").text(x);
                    $(".prev span").text(y);
                });

                $(".numbersOnly").keypress(function (e) {
                    //if the letter is not digit then display error and don't type anything
                    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                        //display error message
                        $("#errmsg").html("Digits Only").show().fadeOut("slow");
                        return false;
                    }
                });
            });
            $(document).ready(function () {

                $("#pincode,#purchaseprice,#Down_payment1,#Down_payment2,#pincode1,#purchaseprice1,#currentbalance1,#currentbalance2").keyup(function () {
                    $("#pincode").removeClass("input-error");
                    $("#purchaseprice").removeClass("input-error");
                    $("#Down_payment1").removeClass("input-error");
                    $("#Down_payment2").removeClass("input-error");

                    $("#pincode1").removeClass("input-error");
                    $("#purchaseprice1").removeClass("input-error");
                    $("#currentbalance1").removeClass("input-error");
                    $("#currentbalance2").removeClass("input-error");

                });
                $("#getrates").click(function () {

                    pincode = $("#pincode").val();
                    purchaseprice = $("#purchaseprice").val();
                    Down_payment1 = $("#Down_payment1").val();
                    Down_payment2 = $("#Down_payment2").val();
                    iserror = false;
                    if (Down_payment1 == '') {
                        $("#Down_payment1").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#Down_payment1").removeClass("input-error");
                    }
                    if (Down_payment2 == '') {
                        $("#Down_payment2").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#Down_payment2").removeClass("input-error");
                    }
                    if (pincode == '') {
                        $("#pincode").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#pincode").removeClass("input-error");
                    }
                    if (purchaseprice == '') {
                        $("#purchaseprice").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#purchaseprice").removeClass("input-error");
                    }
                    if (iserror) {
                        return;
                    }


                    var jsonArr = {};

                    $('input[class^="json"]').each(function () {
                        // alert($(this).attr("name"));
                        //  alert($(this).val());
                        jsonArr[$(this).attr("name")] = $(this).val();
                    });
                    down = $("#Down_payment1").val() + " SAR " + $("#Down_payment2").val() + " %"
                    creditscore = $("#creditscore").val();
                    ggcnan = "No";
                    if ($('#ggcnan').is(':checked')) {
                        ggcnan = "Yes";
                    }

                    if ($('.bank').is(':checked')) {
                        jsonArr['Filed bankrupty?'] = "Yes";
                    }

                    if ($('.fore').is(':checked')) {
                        jsonArr['Had a foreclosure?'] = "Yes";
                    }

                    if ($('.self').is(':checked')) {
                        jsonArr['Self-employed?'] = "Yes";
                    }
                    $('input[name^="desired"]').each(function () {
                        // alert($(this).attr("name"));
                        if ($(this).is(':checked')) {
                            jsonArr['Desired loan programs?'] = $(this).val();
                        }
                        //  jsonArr[$(this).attr("name")] = $(this).val();
                    });

                    jsonArr['Credit score'] = creditscore;
                    jsonArr['GCC national'] = ggcnan;
                    jsonArr['Down payment'] = down;
                    // alert(howhome);
                    //return false;
                    jsonArr['Property type'] = $("#propertytype").val();
                    jsonArr['How is home used?'] = $("#howhome").val();
                    jsonArr['First-time buyer?'] = $("#firsttime").val();
                    jsonArr['New Construction?'] = $("#newcon").val();
                    /// jsonArr['Property type'] = propertytype;

                    // jsonArr['Down payment'] = down;
                    // return false;
                    // console.log(jsonArr.toString());
                    var mortgage = JSON.stringify(jsonArr);

                    var input = $("<input>").attr("type", "hidden").attr("name", "mortgage").val(mortgage);
                    var input1 = $("<input>").attr("type", "hidden").attr("name", "mortgagetype").val("Purchase");
                    $('<form/>').append(input1).append(input).attr('method', 'POST').attr('action', 'mortgage').submit();
                });

                $("#getrates1").click(function () {

                    pincode = $("#pincode1").val();
                    purchaseprice = $("#purchaseprice1").val();
                    currentbalance1 = $("#currentbalance1").val();
                    currentbalance2 = $("#currentbalance2").val();
                    iserror = false;
                    if (currentbalance1 == '') {
                        $("#currentbalance1").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#currentbalance1").removeClass("input-error");
                    }
                    if (currentbalance2 == '') {
                        $("#currentbalance2").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#currentbalance2").removeClass("input-error");
                    }
                    if (pincode1 == '') {
                        $("#pincode1").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#pincode1").removeClass("input-error");
                    }
                    if (purchaseprice1 == '') {
                        $("#purchaseprice1").addClass("input-error");
                        iserror = true;
                    } else {
                        $("#purchaseprice1").removeClass("input-error");
                    }
                    if (iserror) {
                        return;
                    }


                    var jsonArr = {};

                    $('input[class^="json1"]').each(function () {
                        // alert($(this).attr("name"));
                        //  alert($(this).val());
                        jsonArr[$(this).attr("name")] = $(this).val();
                    });
                    down = $("#currentbalance1").val() + " " + $("#currentbalance2").val() + " %"
                    creditscore = $("#creditscore1").val();
                    ggcnan = "No";
                    if ($('#ggcnan1').is(':checked')) {
                        ggcnan = "Yes";
                    }

                    if ($('.bank').is(':checked')) {
                        jsonArr['Filed bankrupty?'] = "Yes";
                    }

                    if ($('.fore').is(':checked')) {
                        jsonArr['Had a foreclosure?'] = "Yes";
                    }

                    if ($('.self').is(':checked')) {
                        jsonArr['Self-employed?'] = "Yes";
                    }
                    $('input[name^="desired"]').each(function () {
                        // alert($(this).attr("name"));
                        if ($(this).is(':checked')) {
                            jsonArr['Desired loan programs?'] = $(this).val();
                        }
                        //  jsonArr[$(this).attr("name")] = $(this).val();
                    });

                    jsonArr['Credit score'] = creditscore;
                    jsonArr['GCC national'] = ggcnan;
                    jsonArr['Down payment'] = down;
                    // alert(howhome);
                    //return false;
                    jsonArr['Property type'] = $("#propertytype").val();
                    jsonArr['How is home used?'] = $("#howhome").val();
                    jsonArr['First-time buyer?'] = $("#firsttime").val();
                    jsonArr['New Construction?'] = $("#newcon").val();
                    /// jsonArr['Property type'] = propertytype;

                    // jsonArr['Down payment'] = down;
                    // return false;
                    // console.log(jsonArr.toString());
                    var mortgage = JSON.stringify(jsonArr);

                    var input = $("<input>").attr("type", "hidden").attr("name", "mortgage").val(mortgage);
                    var input1 = $("<input>").attr("type", "hidden").attr("name", "mortgagetype").val("Refinance");
                    $('<form/>').append(input1).append(input).attr('method', 'POST').attr('action', 'mortgage').submit();
                });

                $("#getrates2").click(function () {
                    var input = $("<input>").attr("type", "hidden").attr("name", "approvalseq").val("pre-approval-1");
                    $('<form/>').append(input).attr('method', 'POST').attr('action', 'approval').submit();
                });

            });
        </script>
        <script>
            function getParameterByName(name) {
                name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                        results = regex.exec(location.search);
                return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
            }
            function pagetab(tid) {
                //$('.tab-pane').hide();
                //$('#'+tid).show();
                $('.tab-pane').removeClass('in active');
                $('#' + tid).addClass('in active');

                $('.nav li').removeClass('in active');
                $("a[href='#" + tid + "']").parent('li').addClass('in active');
                //alert(nav);
            }
            $(document).ready(function () {
                tab = getParameterByName('tab');
                if (tab != '') {
                    pagetab(tab);
                }

                $('.credit-score').tooltip({title: "Hooray dfasdfas asdf asdf asdf asdf asdfasdf asdf sadf asdf sadfsdf!", trigger: "focus"});
                $('.monthly-debts').tooltip({title: "Hooray Hooray", trigger: "focus"});
            });</script>

        <script>
            $('.SeeMore2').click(function () {
                var $this = $(this);
                $this.toggleClass('SeeMore2');
                if ($this.hasClass('SeeMore2')) {
                    $this.html('Advanced &nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-chevron-down"></span>');
                } else {
                    $this.html('Simple &nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-chevron-up"></span>').addClass('simple');
                }
            });
        </script>
    </body>
</html>

