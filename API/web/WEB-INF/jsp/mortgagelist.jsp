<%@include file="header.jsp" %>

<div class="container container-topMargin">
    <h3 class="Draft-heading"> <spring:message code="label.mortgageinfo" /> </h1>
        <hr>
        <div id="ctl00_cph_divWorkAreaContent" style="overflow: hidden;" ></div>
        <div id="tt" style="width:auto;height:0!important"
             title="DataGrid - CardView" 
             showFooter="true" pagination="true" data-options="singleSelect:true,page:'',url:'mortgages',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">
        </div> 


</div><!--container-->
<footer>
    <div class="container">
        <div class="row">
            <!--fgs sdfg s-->
        </div>
    </div><!--container-->
</footer>
<!-- jQuery -->
</body>
<script>
    var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
        renderRow: function (target, fields, frozen, rowIndex, rowData) {

            if (rowData.userId != undefined) {
                if (rowIndex == 0) {
                    $("#ctl00_cph_divWorkAreaContent").html("");
                }

                var anchorArray = "";
                id = "rowData.id";
                anchorArray = "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-9'>";
                 rowint=rowIndex+1;
                anchorArray = anchorArray + "<a class='Draft-name' style='padding-left: 61px;' title='" + rowint + "' href='#'>" + rowint + "</a>";
              
                anchorArray = anchorArray + "<a class='Draft-name' style='padding-left: 200px;' title='" + rowData.userId + "' href='#'>" + rowData.userId + "</a>";
                //  anchorArray = anchorArray + "<p class='Draft-for'> $" + rowData.price + " " + categorytext + "</p>";
                anchorArray = anchorArray + "</div>";
               

                anchorArray = anchorArray + "<div class='col-sm-3 text-center'><a  href='#'><input type='button' data-toggle='collapse' data-target='#demo"+rowIndex+"' class='btn-edit' value='<spring:message code="label.mortgageinfo.checkdetails" />' /></a></div>";
                anchorArray = anchorArray + "</div><hr>";
                
                 try {
                obj = $.parseJSON(rowData.mortgage_details);
            } catch (e) {
                obj = rowData.mortgage_details;
            }
             i=0;
                anchorArray = anchorArray + "<div class='row'>";
    anchorArray = anchorArray + "<div class='col-sm-12'>";
        anchorArray = anchorArray + "<div id='demo"+rowIndex+"' class='collapse' style='padding:0px 75px; background:#efefef; border:1px solid #dbdbdb;'>";
        anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'><b><spring:message code="label.mortgageinfo.mobile" /></b></span> ";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span id='email'><b>"+rowData.mobile+"</b></span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
        anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'><b><spring:message code="label.mortgageinfo.type" /></b></span> ";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span id='email'><b>"+rowData.type+"</b></span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
            
            $.each(obj, function (index, value) {
                 i++;

            anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'><b>"+index+"</b></span> ";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span id='email'><b>"+value+"</b></span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
            
                });
            anchorArray = anchorArray + "</div><!--demo-->";
        anchorArray = anchorArray + "</div>";
    anchorArray = anchorArray + "</div><br>";
            

                $("#ctl00_cph_divWorkAreaContent").append(anchorArray);
            }
            return "";

        }
    });

    function homeworth(rowIndex) {
//alert(rowData.name);
  var selectedRow = $('#tt').datagrid('getRows')[rowIndex];
  //alert(selectedRow.name);
  $("#name").html(selectedRow.name);
  $("#email").html(selectedRow.email);
  $("#beds").html(selectedRow.beds);
  $("#bath").html(selectedRow.bath);
  $("#mobile").html(selectedRow.mobile);
  $("#address").html(selectedRow.address);
  // $("#row"+rowIndex).html($("#details").html());
    }

    $('#tt').datagrid({
        view: cardview, onLoadSuccess: function (data) {
            if ($('#tt').datagrid('getRows').length == "0") {
                $("#ctl00_cph_divWorkAreaContent").append("<spring:message code="label.norecords.found" />");
            }

        }});
</script>

<!--row-->
</html>
