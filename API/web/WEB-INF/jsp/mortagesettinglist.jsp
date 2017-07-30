<%@include file="header.jsp" %>

<div class="container container-topMargin">
    <h3 class="Draft-heading"> <spring:message code="label.mortgage.settings" /> </h1>
        <hr>
        <div id="ctl00_cph_divWorkAreaContent" style="overflow: hidden;" ></div>
        <div id="tt" style="width:auto;height:0!important"
             title="DataGrid - CardView" 
             showFooter="true" pagination="true" data-options="singleSelect:true,page:'',url:'mortagesettinglist',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">

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

            if (rowData.rateOfIntrest != undefined) {
                if (rowIndex == 0) {
                    $("#ctl00_cph_divWorkAreaContent").html('<div class="table-responsive" style="padding:30px 20px 20px 20px;"><table class="table table-bordered table-hover" style="text-align:center;">    <thead>      <tr>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.ptax" /></th>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.years" /></th>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.intrest" /></th>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.insurence.tax" /></th>        <th style="text-align:center;"><spring:message code="label.update" /></th>        </tr>    </thead>    <tbody>');
                }

                var anchorArray = "";

//                anchorArray = "<div class='row'>";
//                anchorArray = anchorArray + "<div class='col-sm-9'>";
//                anchorArray = anchorArray + "<div class='Draft-name' style='padding-left: 61px;'>" + rowData.propertyTax + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rowData.no_of_years + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rowData.rateOfIntrest + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rowData.insurenceTax + "</div>";
////            anchorArray = anchorArray + "<div class='Draft-name' style='padding-left: 61px;'><p>"+rowData.propertyTax+"</p>"+rowData.no_of_years+rowData.rateOfIntrest+rowData.insurenceTax+ "</div>";    
//                anchorArray = //anchorArray +'<div class="Draft-name" style="padding-left: 61px;" title="'+rowData.no_of_years+'">'+rowData.no_of_years+'</div>';
//
//                        anchorArray = anchorArray + "</div>";

                //  anchorArray = anchorArray + "<div class='col-sm-3 text-center'><a  href='mortgagesettings?id=" + rowData.id + "'><input type='button'  class='btn-edit' value='Update Details' /></a></div>";
                //  anchorArray = anchorArray + "<hr><hr>";
                anchorArray = anchorArray + "<tr>";
                anchorArray = anchorArray + "<td>" + rowData.propertyTax + "</td>";
                anchorArray = anchorArray + "<td>" + rowData.no_of_years + "</td>";
                anchorArray = anchorArray + "<td>" + rowData.rateOfIntrest + "</td>";
                anchorArray = anchorArray + "<td>" + rowData.insurenceTax + "</td>";
                anchorArray = anchorArray + '<td><input class="Update-btn" onclick="callmortgagesettings(' + rowData.id + ')" type="button" value="<spring:message code="label.mortgage.settings.update.details" />"></td>';
                anchorArray = anchorArray + "</tr>";
//                 alert(rowIndex);
                if (rowIndex == $('#tt').datagrid('getRows').length - 1) {
                    // alert($("#ctl00_cph_divWorkAreaContent").html());
                    anchorArray = anchorArray + '</tbody>  </table>        </div>';

                }
                $("#ctl00_cph_divWorkAreaContent").append(anchorArray);
            }
            return "";

        }
    });
    function callmortgagesettings(id) {
        window.location = "mortgagesettings?id=" + id;
    }
    //callgrid();
    $(document).ready(function () {

        // var gridhtml = '<div class="table-responsive" style="padding:30px 20px 20px 20px;"><table class="table table-bordered table-hover" style="text-align:center;"><thead>      <tr>        <th style="text-align:center;">PropertyTax</th>        <th style="text-align:center;">Years</th>        <th style="text-align:center;">Interest</th>        <th style="text-align:center;">InsurenceTax</th>        <th style="text-align:center;">Update</th>        </tr>    </thead>    <tbody>      <tr>        <td>4</td>        <td>20</td>        <td>22</td>        <td>45</td>        <td><input class="Update-btn" type="button" value="Update details"></td>        </tr>      <tr>        <td>2</td>        <td>10</td>        <td>15</td>        <td>20</td>        <td><input class="Update-btn" type="button" value="Update details"></td>        </tr>      <tr>        <td>15</td>        <td>14</td>        <td>12</td>        <td>8</td>        <td><input class="Update-btn" type="button" value="Update details"></td>        </tr>    </tbody>  </table>        </div><!--table-responsive-->                ';
        // $("#ctl00_cph_divWorkAreaContent").html(gridhtml);
        childserviceload();
    });

    function childserviceload() {

        $('#tt').datagrid({
            url: 'mortagesettinglist',
            onLoadSuccess: checkchildserviceload
        });
    }
    function checkchildserviceload() {
        length = $('#tt').datagrid('getRows').length;
        htmocontent = '<div class="table-responsive" style="padding:30px 20px 20px 20px;">        <table class="table table-bordered table-hover" style="text-align:center;">    <thead>      <tr>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.ptax" /></th>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.years" /></th>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.intrest" /></th>        <th style="text-align:center;"><spring:message code="label.mortgage.settings.insurence.tax" /></th>        <th style="text-align:center;"><spring:message code="label.action" /></th>        </tr>    </thead>    <tbody>';
        for (i = 0; i < length; i++) {
            var selectedRow = $('#tt').datagrid('getRows')[i];
            //  alert(selectedRow.propertyTax);

            htmocontent = htmocontent + '<tr>        <td>'+selectedRow.propertyTax+'</td>        \n\
<td>'+selectedRow.no_of_years+'</td>        <td>'+selectedRow.rateOfIntrest+'</td>        <td>'+selectedRow.insurenceTax+'</td>        <td><input class="Update-btn" onclick="callmortgagesettings(' + selectedRow.id + ')" type="button" value="<spring:message code="label.mortgage.settings.update.details" />"></td>        </tr>';
        }
        htmocontent = htmocontent + '    </tbody>  </table>        </div><!--table-responsive-->';
$("#ctl00_cph_divWorkAreaContent").html(htmocontent);
    }
    /* $('#tt').datagrid({
     view: cardview, onLoadSuccess: function (data) {
     if ($('#tt').datagrid('getRows').length == "0") {
     $("#ctl00_cph_divWorkAreaContent").append("No Records Found");
     }
     
     }});*/
</script>

<!--row-->
</html>
