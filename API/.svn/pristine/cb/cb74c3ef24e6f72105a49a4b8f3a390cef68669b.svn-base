<%@include file="header.jsp" %>

        <div class="container container-topMargin">
            <h3 class="Draft-heading"> Agent Listings </h1>
                <hr>
                <div id="ctl00_cph_divWorkAreaContent" style="overflow: hidden;" ></div>
                <div id="tt" style="width:auto;height:0!important"
                     title="DataGrid - CardView" 
                     showFooter="true" pagination="true" data-options="singleSelect:true,page:'',url:'agentlist',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">

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
             
                if(rowData.name!=undefined){
                if (rowIndex == 0) {
                    $("#ctl00_cph_divWorkAreaContent").html("");
                }
             
                var anchorArray = "";
                id = rowData.id;
                console.log(rowData);
                var pic = rowData.profilePicture;
                
                if(pic != ''&&pic!= undefined){
                    var img = pic.split('/');
                    var imgsrc = img[img.length - 1];
                }else{
                    var imgsrc = 'user-img.jpg';
                }
                anchorArray = "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-9'>";
                anchorArray = anchorArray + "<div style='float:left; width:30%; text-align:center;'><img src='images/"+ imgsrc +"' width='120' height='100' /></div>";
                anchorArray = anchorArray + "<div style='float:left; width:69%;'>";
                anchorArray = anchorArray + "<a class='Draft-name' title='" + rowData.name + "' href='home?agent_id=" + id + "'>" + rowData.name + "</a>";
                anchorArray = anchorArray + "<p class='Draft-for'> <b>Company:</b> " + rowData.company + "<br />";
                anchorArray = anchorArray + "<b>City:</b> " + rowData.city + "</p>";
                anchorArray = anchorArray + "</div></div>";
                anchorArray = anchorArray + "<div class='col-sm-3 text-center'><a href='agentdetails?id=" + id + "'><input type='button' class='btn-edit' value='Edit' /></a> </div>";
                anchorArray = anchorArray + "</div><hr>";
                $("#ctl00_cph_divWorkAreaContent").append(anchorArray);
            }
                return "";

            }
        });

        $('#tt').datagrid({
            view: cardview, onLoadSuccess: function (data) {
                if ($('#tt').datagrid('getRows').length == "0") {
                    $("#ctl00_cph_divWorkAreaContent").append("No Records Found");
                }

            }});
    </script>
</html>
