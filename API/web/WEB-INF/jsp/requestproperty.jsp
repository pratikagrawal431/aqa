<%@include file="header.jsp" %>

        <div class="container container-topMargin">
            <h3 class="Draft-heading"><spring:message code="label.header.property.info" /> </h1>
                <hr>
                <div id="ctl00_cph_divWorkAreaContent" style="overflow: hidden;" ></div>
                <div id="tt" style="width:auto;height:0!important"
                     title="DataGrid - CardView" 
                     showFooter="true" pagination="true" data-options="singleSelect:true,page:'',url:'requestinfolist',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">

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
             
                if(rowData.propertyId!=undefined){
                if (rowIndex == 0) {
                    $("#ctl00_cph_divWorkAreaContent").html("");
                }
             
                var anchorArray = "";
               
                id = rowData.id;
                
                anchorArray = "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-9'>";
                anchorArray = anchorArray + "<div style='float:left; width:30%; text-align:center;'>"+id+"</div>";
                anchorArray = anchorArray + "<div style='float:left; width:69%;'>";
                anchorArray = anchorArray + "<a class='Draft-name' title='" + rowData.email + "' href='#'>" + rowData.email + "</a>";
                anchorArray = anchorArray + "<p class='Draft-for'> <b>userId:</b> " + rowData.userId + "<br />";
                anchorArray = anchorArray + "<b>Mobile:</b> " + rowData.mobile + "</p>";
                anchorArray = anchorArray + "<b>Date:</b> " + rowData.datetime + "</p>";
                anchorArray = anchorArray + "</div></div>";
                anchorArray = anchorArray + "<div class='col-sm-3 text-center'><a href='showproperty?id=" + rowData.propertyId + "'><input type='button' class='btn-edit' value='<spring:message code="label.view.details" />' /></a> </div>";
                anchorArray = anchorArray + "</div><hr>";
                $("#ctl00_cph_divWorkAreaContent").append(anchorArray);
            }
                return "";

            }
        });

        $('#tt').datagrid({
            view: cardview, onLoadSuccess: function (data) {
                if ($('#tt').datagrid('getRows').length == "0") {
                    $("#ctl00_cph_divWorkAreaContent").append("<spring:message code="label.norecords.found" />");
                }

            }});
    </script>
</html>
