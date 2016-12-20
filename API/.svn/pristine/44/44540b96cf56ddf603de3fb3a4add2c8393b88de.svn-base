<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Basic LinkButton - jQuery EasyUI Demo</title>
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo.css">
        <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.min.js"></script>
        <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?sensor=false&amp;libraries=places"></script>
 <script src="resources/js/jquery.geocomplete.js"></script>
     <link rel="stylesheet" type="text/css" href="resources/styles.css" />
    <style type="text/css" media="screen">
      .map_canvas { float: left; }
      form { width: 300px; float: left; }
      fieldset { width: 320px; margin-top: 20px}
      fieldset label { display: block; margin: 0.5em 0 0em; }
      fieldset input { width: 95%; }
    </style>
        <script>
            $(document).ready(function () {
             
                $("#location").click(function () {
                    var location = {};
                    location['street'] = $("#street").val();
                    location['propertyUnit'] = $("#propertyUnit").val();
                    location['state'] = $("#state").val();
                    location['city'] = $("#city").val();
                    location['longitude'] = $("#longitude").val();
                    location['latitude'] = $("#latitude").val();
                    location['zipcode'] = $("#zipcode").val();
                    location['forRent'] = $('input[name=forRent]:checked').val();
                    location['forSale'] = $('input[name=forSale]:checked').val();

                    var locationjson = JSON.stringify(location);
                    alert(locationjson);
                    $.ajax({
                        url: "createproperty",
                        type: "POST",
                        data: locationjson,
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data)
                        {

                            code = data.response.code;
                            if (code == 0) {
                                 id = data.response.id;
                                 $("#id").val(id);
                    
                            }
                        }
                    });
                });
                
                     $("#propertycontact").click(function () {
                    var propertycontact = {};
                    propertycontact['name'] = $("#name").val();
                    propertycontact['company'] = $("#company").val();
                    propertycontact['email'] = $("#email").val();
                    propertycontact['id'] = $("#id").val();
                    propertycontact['phone'] = $("#phone").val();


                    var propertyjson = JSON.stringify(propertycontact);
                    alert(propertyjson);
                    $.ajax({
                        url: "createproperty",
                        type: "POST",
                        data: propertyjson,
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data)
                        {

                            code = data.response.code;
                            if (code == 0) {
                                 id = data.response.id;
                                 $("#id").val(id);
                    
                            }
                        }
                    });
                });
                
                
            });
            function uploadJqueryForm(){
var input = $("<input>")
               .attr("type", "hidden")
               .attr("name", "id").val($("#id").val());
$('#imageupload').append($(input));

   $("#imageupload").ajaxForm({
	success:function(data) { 
	      //$('#result').text(data+" uploaded by Jquery Form plugin!");
	    //  $('#result').html(data);

	 },
	 dataType:"text"
   }).submit();
}

      $(function(){
        $("#geocomplete").geocomplete({
          map: ".map_canvas",
          details: "form",
          types: ["geocode", "establishment"],
        });

        $("#find").click(function(){
          $("#geocomplete").trigger("geocode");
        });
      });
        </script>

    </head>
    <body>
        <h2>Basic Tabs</h2>
        <p>Click tab strip to swap tab panel content.</p>
        <div style="margin:20px 0 10px 0;"></div>
        <div id ="mytabs" class="easyui-tabs" style="width:1200px;height:1250px">
            <div title="Location" style="padding:10px">

                <div class="map_canvas"></div>

    <form>   
            <div class="easyui-panel" title="New Topic" style="width:800px;height:800px">
                 <input id="geocomplete" type="text" placeholder="Type in an address" value="Empire State Bldg" />
                    <div style="padding:10px 60px 20px 60px">
                        <table cellpadding="5">
                            <tr>
                                <td>Street:</td>
                                <td><input type="text" id="street"  name="street_number" ></input></td>
                            </tr>
                            <tr>
                                <td>Unit/Floor/Building:</td>
                                <td><input  type="text" id="propertyUnit" name="formatted_address" ></input></td>
                            </tr>
                            <tr>
                                <td>City:</td>
                                <td><input type="text" id="city" name="locality" ></input></td>
                            </tr>
                            <tr>
                                <td>State:</td>
                                <td>
                                   <input name="administrative_area_level_1" type="text" value="">
                                </td>
                                <td>Zip Code:</td>
                                <td><input name="postal_code" type="text" value=""></td>



                            </tr>
                            <tr>
                                Postlet Category
                                <td>
                                    <input type="radio" id="forRent" name="forRent"    /> For Rent
                                </td>
                                <td>
                                    <input type="radio" id="forSale" name="forSale"  /> For Sale
                                </td>
                            </tr>
                            <tr>

                            </tr>
                        </table>
                        <input name="lat" id="latitude" type="hidden" value="">
                         <input name="lng" id="longitude" type="hidden" value="">
                        <div style="text-align:center;padding:5px">
                            <a class="easyui-linkbutton" id="location"  >Submit</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
                        </div>
                    </div>
                </div>

    
    </form>
            </div>
            <div title="ContactInfo" style="padding:10px">

                <div class="easyui-panel" title="New Topic" style="width:800px;height:800px">
                    <div style="padding:10px 60px 20px 60px">
                        <form id="ff" method="post">
                            <table cellpadding="5">

                                <tr>
                                    Contact Information <br>
                                For Rent By *
                                <td>
                                    <input type="radio" name="for_rent" value="rent" class="easyui-validatebox"  /> Management Company / Broker
                                    <br>
                                    <input type="radio" name="for_sale" value="sale" class="easyui-validatebox"  /> Owner
                                </td>

                                </tr>


                                <tr>
                                    <td>Name:</td>
                                    <td><input class="easyui-textbox" type="text"  id="name"  name="name" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>Company:</td>
                                    <td><input class="easyui-textbox" type="text" id="company" name="company" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>Email Address*</td>
                                    <td><input class="easyui-textbox" type="text" id="email" name="email" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>Phone</td>
                                    <td><input class="easyui-textbox" type="text" id="phone" name="phone" data-options="required:true"></input></td>



                                </tr>

                                <tr>

                                </tr>
                            </table>
                        </form>
                        <div style="text-align:center;padding:5px">
                            <a href="javascript:void(0)" class="easyui-linkbutton"  id="propertycontact">Submit</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
                        </div>
                    </div>
                </div>


            </div>
            <div title="Basic Details" data-options="closable:true" style="padding:10px">
                Rental Details

                <div class="easyui-panel" title="New Topic" style="width:800px;height:800px">
                    <div style="padding:10px 60px 20px 60px">
                        <form id="ff" method="post">
                            <table cellpadding="5">
                                <tr>
                                    <td>Rent*</td>
                                    <td><input class="easyui-textbox" type="text" name="rent" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>Date Available</td>
                                    <td><input class="easyui-datebox"></input></td>
                                </tr>
                                <tr>
                                    <td>Property Type*</td>
                                    <td>
                                        <select class="easyui-combobox" name="property_type"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
                                        <input type="radio" name="for_rent" value="rent" class="easyui-validatebox"  /> room-for-rent
                                    </td>
                                </tr>
                                <tr>
                                    <td>Bedrooms*</td> 
                                    <td>

                                        <select class="easyui-combobox" name="property_type"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
                                    </td>
                                    <td>Bathrooms*</td> 
                                    <td>

                                        <select class="easyui-combobox" name="property_type"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Square Feet</td> 
                                    <td><input class="easyui-textbox" type="text" name="rent" data-options="required:true"></input></td>
                                    <td>Unit Floor</td> 
                                    <td><input class="easyui-textbox" type="text" name="rent" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>Year Built</td> 
                                    <td><input class="easyui-textbox" type="text" name="rent" data-options="required:true"></input></td>
                                </tr>

                                <tr>
                                    <td>  <input type="radio" name="furnished" value="rent" class="easyui-validatebox"  /> Furnished</td> 
                                </tr>
                                <tr>
                                    <td>Parking Spaces</td> 
                                    <td>  <select class="easyui-combobox" name="property_type"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select></td>
                                    <td>Parking Type</td> 
                                    <td>  <select class="easyui-combobox" name="property_type"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select></td>
                                </tr>

                            </table>
                        </form>
                        <div style="text-align:center;padding:5px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
                        </div>
                    </div>
                </div>


            </div>

            <div title="Photos" data-options="closable:true" style="padding:10px">
                Photos

                <div class="easyui-panel" title="New Topic" style="width:800px;height:800px">
                    <div style="padding:10px 60px 20px 60px">
                <form id="imageupload" method="post" action="save" enctype="multipart/form-data">
  
  <!-- File input -->     
  <input name="file" id="file" type="file" /><br/>
  <input type="hidden" name="propertyid" id="id"/> 
  <input type="submit" onclick="uploadJqueryForm()" value="Upload" />
</form>
                        
                        <div style="text-align:center;padding:5px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
                        </div>
                    </div>
                </div>


            </div>

            <div title="Description" data-options="closable:true" style="padding:10px">
                Property Description

                <div class="easyui-panel" title="New Topic" style="width:800px;height:800px">
                    <div style="padding:10px 60px 20px 60px">
                        <form id="ff" method="post">
                            <table cellpadding="5">
                                <tr>
                                    <td>Property Title</td>
                                    <td><input class="easyui-textbox" type="text" name="rent" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>Property Description</td>
                                    <td><input class="easyui-textbox" name="message" data-options="multiline:true" style="height:60px"></input></td>
                                </tr>
                            </table>
                        </form>
                        <div style="text-align:center;padding:5px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
                        </div>
                    </div>
                </div>


            </div>
        </div>




    </body>
</html>