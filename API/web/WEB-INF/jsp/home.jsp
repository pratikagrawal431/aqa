<%@include file="header.jsp" %>
<script>
    agent_id = getParameterByName("agent_id");
</script>
<div class="container container-topMargin">
    <h3 class="Draft-heading"> <spring:message code="label.home.property.listings" /> </h1>
        <hr>
        <div style="display:none;margin-left:400px" class="signup-heading" id="ctl00_cph_divMsg">

            &nbsp;
            <span id="ctl00_cph_lblMsg"><spring:message code="label.home.norecords.found" /></span></div>

        <div id="ctl00_cph_divWorkAreaContent"  style="overflow: hidden" ></div>
        <div id="tt" style="width:auto;height:0!important"
             title="DataGrid - CardView" 
             showFooter="true" pagination="true" data-options="singleSelect:true,page:'',url:'propertyslist?agent_id=<%= request.getParameter("agent_id")%>',method:'get',pageList: [8,16,24],pageSize: 8,layout:['list']">

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

            if (rowData.address != undefined) {
                if (rowIndex == 0) {
                    $("#ctl00_cph_divWorkAreaContent").html("");
                }
                var category = rowData.category;
                categorytext = "<spring:message code="label.property.forrent" />";

                if (category == 1) {
                    categorytext = "<spring:message code="label.property.forsale" />"
                } else if (category == 2) {
                    categorytext = "<spring:message code="label.property.forrent" />"
                } else {
                    categorytext = "<spring:message code="label.property.sold" />"
                }

                var anchorArray = "";
                id = rowData.propertyId;
                status = rowData.status;
                agents = rowData.agents;
                // images=rowData.images;

                imagelink = "";
                if (typeof rowData.images != 'undefined') {
                    $.each(rowData.images, function (index, value) {
                        if (index == 0) {
                            imagelink = value.imageLink;
                            //  $("#imagediv").append("<div class=\"item item-big active\"><img src=\"" + value.imageLink + "\"></div>");
                        }

                    });
                } else {
                    imagelink = "images/property-default-800x600.jpg"
                }
                propertystatus = "<spring:message code="label.property.draft" />";
                if (status == 2) {
                    propertystatus = "<spring:message code="label.property.active" />";
                }
                if (status == 3) {
                    propertystatus = "<spring:message code="label.property.deactivated" />";
                }
                //console.log(rowData);
                share_to_agents = rowData.share_to_agents;
                anchorArray = "<div class='row'>";
                anchorArray = anchorArray + "<div class='col-sm-9 img-content'>";
                anchorArray = anchorArray + "<div class='proimg'><img src='" + imagelink + "' width='120' height='100' /></div>";
                             anchorArray = anchorArray + "<div class='pdetails'>";
                anchorArray = anchorArray + "<a class='Draft-name' title='" + rowData.address + "' href='showproperty?id=" + id + "'><spring:message code="label.property.title" />:" + rowData.name + "</a>";
                anchorArray = anchorArray + "<p class='Draft-for'> " + rowData.address + " <br />";
                anchorArray = anchorArray + "$" + rowData.price + " " + categorytext + "</p>";
                if (agents != "") {
    <c:if test="${useradmin.userType eq 3}">
                    if (agent_id == '') {
                        anchorArray = anchorArray + "<p class='Draft-for'<b> <spring:message code="label.property.assaignto" />: </b>" + agents + "</p>";
                    }
    </c:if>
                }
                anchorArray = anchorArray + "<p class='Draft-for'<b> <spring:message code="label.property.status" />: </b>" + propertystatus + "</p>";

                anchorArray = anchorArray + "</div></div>";
                anchorArray = anchorArray + "<div class='col-sm-3 text-center probut'>";
                if (status == 1 || status == 3) {
                    anchorArray = anchorArray + " <a href='showproperty?id=" + id + "'><input type='button' class='btn-edit' value='<spring:message code="label.edit" />' /></a> ";
                }
                if (status == 1 || status == 3) {
                    anchorArray = anchorArray + " <a href='#' onclick='activateProperty(" + id + ")'><input type='button' class='btn-edit' value='<spring:message code="label.property.activate" />' /></a>";
                } else if (status == 2) {
                    anchorArray = anchorArray + " <a href='#' onclick='deactivateProperty(" + id + ")'><input type='button' class='btn-edit' value='<spring:message code="label.property.deactivate" />' /></a>";
//                    anchorArray = anchorArray + "/<a href='#' class='edit-teg'>Activated</a>";
                }

                if (status == 1) {
                    anchorArray = anchorArray + " <a href='#' onclick='deleteProperty(" + id + ")'><input type='button' class='btn-edit' value='<spring:message code="label.delete" />' /></a>";
//                    anchorArray = anchorArray + "/<a href='#' class='edit-teg'>Activated</a>";
                }
                if (agent_id != '') {
                    anchorArray = anchorArray + " <a href='#' onclick='deassignProperty(" + agent_id + "," + id + ")'><input type='button' class='btn-edit' value='<spring:message code="label.property.deassaign" />' /></a>";
                }
                //  alert(share_to_agents);
    <c:if test="${useradmin.userType eq 3}">
                if (share_to_agents == 0)
                    anchorArray = anchorArray + " <a href='#myModal' onclick='javascript:getPropertyId(" + id + ")' type='button' data-toggle='modal' data-target='#myModal'><input type='button' class='btn-edit' value='<spring:message code="label.property.assaign.to.agent" />' /></a>";
    </c:if>

                anchorArray = anchorArray + "</div></div><hr>";
                $("#ctl00_cph_divWorkAreaContent").append(anchorArray);
            }
            return "";

        }
    });

    function deassignProperty(agentId, propertyId) {
        $.ajax({
            url: "deassignProperty?agentId=" + agentId + "&propertyId=" + propertyId,
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data)
            {

                code = data.response.code;
                if (code == 0) {
//                       swal("Success", "Property Acticated Successfully", "success")
                    swal({title: "<spring:message code="label.success" />", text: "<spring:message code="label.home.deassaign.success" />", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "<spring:message code="label.ok" />", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'home?agent_id=' + agent_id;
                        }
                    });
                    //    swal({title: "Success", text: "Property Acticated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
//                    window.location = 'home';
                } else {
                    sweetAlert("<spring:message code="label.home.update.failed" />", "<spring:message code="label.home.deassaign.failed" />", "error");
                }

            }, error: function (request, status, error) {
                sweetAlert("<spring:message code="label.home.update.failed" />", "<spring:message code="label.home.deassaign.failed" />", "error");
            }
        });
    }
    function getPropertyId(id) {
        $("#assigntoproperty").val(id);
        $.ajax({
            url: "agent/agentlist",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (response)
            {

                $.each(response, function (idx, rec) {
                    $('<option/>', {
                        'value': rec.id,
                        'text': rec.name
                    }).appendTo('#agentId');
                    //  $("#content").append('<h1>'+rec.Id+'</h1>'+'<p>'rec.Title+'</p>'+'<span>'+rec.url+'</span>').slideDown('slow');
                })

            }
        });
    }
    $('#tt').datagrid({
        view: cardview, onLoadSuccess: function (data) {

            if ($('#tt').datagrid('getRows').length == "0") {
                // $("#ctl00_cph_divWorkAreaContent").append("No Records Found");
                $(".pagination").css("display", "none");
                $("#ctl00_cph_divMsg").css("display", "block");

            } else {
                if ($('#tt').datagrid('getData').total > $('#tt').datagrid('getRows').length) {
                    $(".pagination").css("display", "block");
                }

                $("#ctl00_cph_divMsg").css("display", "none");
            }

        }});


    $(document).ready(function () {
        $("#assigntoproperty").click(function () {

            assigntoproperty = $("#assigntoproperty").val();
            agentId = $("#agentId").val();
            
            $('#myModal').modal('hide');
            $.ajax({
                url: "assignproperty?id=" + assigntoproperty + "&agent_id=" + agentId,
                type: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (data)
                {

                    code = data.response.code;
                    if (code == 0) {
//                       swal("Success", "Property Acticated Successfully", "success")
                        swal({title: "<spring:message code="label.success" />", text: "<spring:message code="label.home.assaigned.to.agent.success" />", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "<spring:message code="label.ok" />", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                window.location = 'home';
                            }
                        });
                        //    swal({title: "Success", text: "Property Acticated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
//                    window.location = 'home';
                    } else if (code == 137) {
                        sweetAlert("<spring:message code="label.home.update.failed" />", "<spring:message code="label.home.property.already.assaign" />", "error");
                    } else {
                        sweetAlert("<spring:message code="label.home.update.failed" />", "<spring:message code="label.home.property.update.failed" />", "error");
                    }

                }, error: function (request, status, error) {
                    sweetAlert("<spring:message code="label.home.update.failed" />", "<spring:message code="label.home.property.update.failed" />", "error");
                }
            });
        });
    });

</script>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-sm">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><spring:message code="label.agent.list" /></h4>
            </div>
            <div class="modal-body">
                <select id="agentId" multiple="multiple" style="width:100%; padding:10px;">
                    <!--<option value="">-- Select Type --</option>-->
                </select>
            </div>
            <input type="hidden" id="property_id" value=""/>
            <div class="modal-footer">
                <button type="button" id="assigntoproperty" class="btn btn-default" value=""  data-dismiss="modal"><spring:message code="label.ok" /></button>
            </div>
        </div>

    </div>
</div>
</html>
