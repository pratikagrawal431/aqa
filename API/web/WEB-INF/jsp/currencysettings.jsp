<%@include file="header.jsp" %>

<div class="container container-topMargin">
    <h3 class="Draft-heading"> Currency Setting </h1>
        <hr>
        <div id="ctl00_cph_divWorkAreaContent" style="overflow: hidden;" ></div>
        <div id="tt" style="width:auto;height:0!important"
             title="DataGrid - CardView" 
             showFooter="true" pagination="true" data-options="singleSelect:true,page:'',url:'currencysettinglist',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">

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
       function callcurrencysettings(id) {
        window.location = "currenysettingedit?id=" + id;
    }
    //callgrid();
    $(document).ready(function () {

        // var gridhtml = '<div class="table-responsive" style="padding:30px 20px 20px 20px;"><table class="table table-bordered table-hover" style="text-align:center;"><thead>      <tr>        <th style="text-align:center;">PropertyTax</th>        <th style="text-align:center;">Years</th>        <th style="text-align:center;">Interest</th>        <th style="text-align:center;">InsurenceTax</th>        <th style="text-align:center;">Update</th>        </tr>    </thead>    <tbody>      <tr>        <td>4</td>        <td>20</td>        <td>22</td>        <td>45</td>        <td><input class="Update-btn" type="button" value="Update details"></td>        </tr>      <tr>        <td>2</td>        <td>10</td>        <td>15</td>        <td>20</td>        <td><input class="Update-btn" type="button" value="Update details"></td>        </tr>      <tr>        <td>15</td>        <td>14</td>        <td>12</td>        <td>8</td>        <td><input class="Update-btn" type="button" value="Update details"></td>        </tr>    </tbody>  </table>        </div><!--table-responsive-->                ';
        // $("#ctl00_cph_divWorkAreaContent").html(gridhtml);
        childserviceload();
    });

    function childserviceload() {

        $('#tt').datagrid({
            url: 'currencysettinglist',
            onLoadSuccess: checkchildserviceload
        });
    }
    function checkchildserviceload() {
        length = $('#tt').datagrid('getRows').length;
        htmocontent = '<div class="table-responsive" style="padding:30px 20px 20px 20px;">        <table class="table table-bordered table-hover" style="text-align:center;">    <thead>      <tr>        <th style="text-align:center;">Sr.</th>        <th style="text-align:center;">Currency</th>        <th style="text-align:center;">Multiplication Factor</th>      <th style="text-align:center;">Action</th>        </tr>    </thead>    <tbody>';
        for (i = 0; i < length; i++) {
            var selectedRow = $('#tt').datagrid('getRows')[i];
            //  alert(selectedRow.propertyTax);

            htmocontent = htmocontent + '<tr>        <td>'+selectedRow.id+'</td>        \n\
<td>'+selectedRow.currency+'</td>        <td>'+selectedRow.multiplication_factor+'</td><td><input class="Update-btn" onclick="callcurrencysettings(' + selectedRow.id + ')" type="button" value="Update details"></td>        </tr>';
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
