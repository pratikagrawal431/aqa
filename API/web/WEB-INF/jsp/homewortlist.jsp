<%@include file="header.jsp" %>

<div class="container container-topMargin">
    <h3 class="Draft-heading"> <spring:message code="label.homeworth" /> </h1>
    <div style="margin-left: 800px;margin-top: 20px;" class="row"><input style="padding: 5px;margin-left: -15px;" type="text" id="homeworth" placeholder="Search"/></div>
    <hr>
        
        <div id="ctl00_cph_divWorkAreaContent" style="overflow: hidden;" ></div>
        <div id="tt" style="width:auto;height:0!important"
             title="DataGrid - CardView" 
             showFooter="true" pagination="true" data-options="singleSelect:true,page:'',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">

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
    $("#homeworth").keypress(function(e) {
    if(e.which == 13) {
       homeworth=$("#homeworth").val();
    search(homeworth);
    }
});
   
    var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
        renderRow: function (target, fields, frozen, rowIndex, rowData) {

            if (rowData.name != undefined) {
                if (rowIndex == 0) {
                    $("#ctl00_cph_divWorkAreaContent").html("");
                }

                var anchorArray = "";
                id = "rowData.id";
                anchorArray = "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-9'>";
                //  anchorArray = anchorArray + "<p class='Draft-for'> $" + rowData.price + " " + categorytext + "</p>";
                rowint=rowIndex+1;
                anchorArray = anchorArray + "<a class='Draft-name' style='padding-left: 61px;' title='" + rowint + "' href='#'>" + rowint + "</a>";
                anchorArray = anchorArray + "<a class='Draft-name' style='padding-left: 300px;' title='" + rowData.name + "' href='#'>" + rowData.name + "</a>";
                
                anchorArray = anchorArray + "</div>";
                var text = "Not Contacted";
                if (rowData.status == 1) {
                    text = "Contacted";
                }

                anchorArray = anchorArray + "<div class='col-sm-3 text-center'><a  href='#'><input type='button' data-toggle='collapse' data-target='#demo"+rowIndex+"' class='btn-edit' value='<spring:message code="label.homeworth.checkdetails" />' /></a></div>";
                anchorArray = anchorArray + "</div><hr>";
                anchorArray = anchorArray + "<div class='row'>";
    anchorArray = anchorArray + "<div class='col-sm-12'>";
        anchorArray = anchorArray + "<div id='demo"+rowIndex+"' class='collapse' style='padding:0px 75px; background:#efefef; border:1px solid #dbdbdb;'>";
            anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'>Name:</span> <span id='name'>"+rowData.name+"</span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'>Email:</span> <span id='email'>"+rowData.email+"</span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
            anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'>Address:</span> <span id='address'>"+rowData.address+"</span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'>Beds:</span> <span id='beds'>"+rowData.beds+"</span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
            anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'>Bath:</span> <span id='bath'>"+rowData.bath+"</span>";
               anchorArray = anchorArray + " </div><!--col-sm-6-->";
               anchorArray = anchorArray + " <div class='col-sm-6' style='padding:5px 0px;'>";
                   anchorArray = anchorArray + " <span style='color:#4c4c4c;'>Mobile:</span> <span id='mobile'>"+rowData.mobile+"</span>";
                anchorArray = anchorArray + "</div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
                        anchorArray = anchorArray + "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-6' style='padding:5px 0px;'>";
                date1=rowData.createdOn;
                    anchorArray = anchorArray + "<span style='color:#4c4c4c;'>Request Date:</span> <span id='bath'>"+date1+"</span>";
               anchorArray = anchorArray + " </div><!--col-sm-6-->";
            anchorArray = anchorArray + "</div><!--row-->";
        anchorArray = anchorArray + "</div><!--demo-->";
    anchorArray = anchorArray + "</div>";
anchorArray = anchorArray + "</div>";
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
    search('');
function search(searchstr){
    $('#tt').datagrid({
        url:'homeworth?searchstr='+searchstr,
        view: cardview, onLoadSuccess: function (data) {
            if ($('#tt').datagrid('getRows').length == "0") {
                $("#ctl00_cph_divWorkAreaContent").append("No Records Found");
            }

        }});
    }
</script>

<!--row-->
</html>
