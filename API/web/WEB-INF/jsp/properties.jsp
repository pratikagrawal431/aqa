<%@include file="header.jsp" %>
<style>
    /****** Rating Starts *****/
    @import url(http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css);

    fieldset, label { margin: 0; padding: 0; }
    body{ margin: 20px; }
    h1 { font-size: 1.5em; margin: 10px; }

    .rating { 
        border: none;
        float: left;
    }

    .rating > input { display: none; } 
    .rating > label:before { 
        margin: 5px;
        font-size: 1.25em;
        font-family: FontAwesome;
        display: inline-block;
        content: "\f005";
    }

    .rating > .half:before { 
        content: "\f089";
        position: absolute;
    }

    .rating > label { 
        color: #ddd; 
        float: right; 
    }

    .rating > input:checked ~ label, 
    .rating:not(:checked) > label:hover,  
    .rating:not(:checked) > label:hover ~ label { color: #FFD700;  }

    .rating > input:checked + label:hover, 
    .rating > input:checked ~ label:hover,
    .rating > label:hover ~ input:checked ~ label, 
    .rating > input:checked ~ label:hover ~ label { color: #FFED85;  }     
    #dvLoading

{

   background:#000 url(images/loader.gif) no-repeat center center;

   height: 100px;

   width: 100px;

   position: fixed;

   z-index: 1000;

   left: 50%;

   top: 50%;

   margin: -25px 0 0 -25px;
 
   display: none;
}

    /* Downloaded from http://devzone.co.in/ */
</style>
<div id="dvLoading"></div>
<div class="container container-topMargin">
    <div class="topDraft-GrayBox">
			<div class="row">
				<div class="col-xs-12">
					<div ></div>
				</div><!--col-sm-1-->
			</div><!--row-->
		</div><!--Draft-GrayBox-->


    <div class="Registration-tab">
        <div class="wrap tabbable tabs-left my-nav">
            <ul class="nav nav-tabs nav-Bottom-broder">
                <li class="active" id="1"><a href="#a" class="1" data-toggle="tab"><spring:message code="label.property.location" /></a></li>
                <li id="2"><a  href="#b" data-toggle="tab"><spring:message code="label.property.contact.info" /> </a></li>
                <li id="3"><a class="3" href="#c" data-toggle="tab"><spring:message code="label.property.basic.details" /></a></li>
                <li id="4"><a href="#d" data-toggle="tab"><spring:message code="label.property.description" /></a></li>
                <li id="5"><a href="#e" data-toggle="tab"><spring:message code="label.property.photos.media" /></a></li>                            
                <li id="6"><a href="#f" data-toggle="tab"><spring:message code="label.property.review" /></a></li>
            </ul>
			
		    <div class="tab-content">
                <div id="a" class="tab-pane fade in active contactinfo0">
				<div class="tab-name1"><h2><spring:message code="label.property.location" /></h2></div>
                    <h3 class="tab-heading "><spring:message code="label.property.plocation" /></h3>

                    <div class="row">
                        <div class="col-xm-12 col-md-6 locationform">
                            <form>
                                <div class="group price">
                                    <input id="geocomplete" type="text" placeholder="<spring:message code="label.property.type.address" />" value="" />
                                    <!--<input id="find" type="button" value="find" />-->
                                </div><!--group price-->
                                <h3 class="Postlet-heading"><spring:message code="label.property.postlet.category" /></h3>
                                <div class="group price">
                                    <label class="label"><spring:message code="label.property.address" /><sup>*</sup></label>
									<input type="text" class="input-text" id="formatted_address" name="formatted_address" value="" autocomplete="off">
                                </div><!--group price-->
                                <div class="group price">
                                    <label class="label top-buffer"><spring:message code="label.property.unit.floor" /></label>
                                    <input type="text" class="input-text" id="propertyUnit" name="" value="" autocomplete="off">
                                </div><!--group price-->
                                <div class="group price">
                                    <label class="label top-buffer"><spring:message code="label.property.city" /><sup>*</sup></label>
                                    <input type="text" class="input-text" id="city" name="locality" value="" autocomplete="off">
                                </div><!--group price-->
                                <div class="group price">
                                    <label class="label top-buffer"><spring:message code="label.property.state" /><sup>*</sup></label>
                                    <input type="text" class="input-text" id="administrative_area_level_1" name="administrative_area_level_1" value="" autocomplete="off">
                                </div><!--group price-->
                                <div class="group price">
                                    <label class="label top-buffer"><spring:message code="label.property.country" /><sup>*</sup></label>
                                    <input type="text" class="input-text" id="country" name="country" value="" autocomplete="off">
                                </div><!--group price-->
                                <div class="group price">
                                    <label class="label top-buffer"><spring:message code="label.property.zipcode" /><sup>*</sup></label>
                                    <input type="text" class="input-text" maxlength="12" id="postal_code" name="postal_code" value="" autocomplete="off">
                                </div><!--group price-->
								
                                <div class="row">
                                    <div class="col-sm-5 radiorent" style="margin-left:20px;box-sizing:border-box;">
									<div style="float:left; margin-top:18px;">
									
                                        <input type="radio" id="forSale" checked="true" name="category"  value="1"/><label for="checkbox2"><span><span></span></span><spring:message code="label.property.forsale" /></label>
                                    </div>
									</div><!--col-sm-6-->
									
                                    <div class="col-sm-5 radiorent">
									<div style="float:left; margin-top:18px;">
                                        <input type="radio" id="forRent" name="category" value="2"/> <label for="checkbox1"><span><span></span></span><spring:message code="label.property.forrent" /></label>
									</div>
                                    </div><!--col-sm-6-->
								</div><!--row-->
								
								
                                <input name="lat" id="latitude" type="hidden" value="">
                                <input name="lng" id="longitude" type="hidden" value="">
                                <div class="row but">
                                    <div class="col-xs-12 col-sm-5 top-buffer">
                                        <input type="button" class="signupbutton" id="location" value="<spring:message code="label.property.submit" />"/>
                                    </div><!--col-xm-12 col-sm-6-->
                                    <div class="col-xs-12 col-sm-5 top-buffer">
                                        <input type="button" class="signupbutton" id="Clear" value="<spring:message code="label.clear" />"/>
                                    </div><!--col-xm-12 col-sm-6-->
                                </div><!--row-->
                            </form>
                        </div><!--col-sm-6-->
						
                        <div class="col-xs-12  col-md-6 map">
                            <div style="width: 100%; height: 400px;" class="map_canvas"></div>
                        </div><!--col-sm-6-->
                    </div><!--row-->
                </div><!--tab-pane id="a"-->
	<!--Location tab ends --> 
	
                <div class="tab-pane contactinfo1" id="b">
				<div class="tab-name2"><h2><spring:message code="label.property.contact.info" /></h2></div>
                    <h3 class="tab-heading"><spring:message code="label.property.contact.information" /></h3>
				<div class="row ">
                 <div class="col-xm-12  col-sm-12  col-md-12 contactform">	
                    <div class="row">
                        <div class="col-xs-12 col-sm-12 col-md-12">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <p id="rentbyid"> For Sale<sup>*</sup></p>
                                </label><br><br>
                                <input id="radio1" type="radio" class="contacttype" name="contacttype" value="1"><label for="radio1"><span><span></span></span>Management Company / Broker</label> <br>
                                <input id="radio2" type="radio" class="contacttype" name="contacttype" value="2"><label for="radio2"><span><span></span></span>Owner</label><br>
                                <input id="radio3" type="radio" class="contacttype" name="contacttype" value="3"><label for="radio3"><span><span></span></span>Tenant</label>
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.name" /><sup>*</sup></label>
                                <input type="text" class="input-text" id="name" name="name" value="">
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.company" /><sup>*</sup></label>
                                <input type="text" class="input-text" id="company" name="company" value="">
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.email" /><sup>*</sup></label>
                                <input type="text" class="input-text1"   id="email" name="email" value="">
                                <div style="float:left; margin-top:18px;">
                                    <!--<input id="showEmail" type="checkbox" name="checkbox"><label for="checkboxEmail"><span><span></span></span> Hide email address on Aqarabia </label>-->
                                </div><!--inline style-->
                            </div><!--group price-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-md-2">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.country.code" /><sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly"  maxlength="3" id="countrycode" name="countrycode" value="">
                            </div><!--group price-->
                        </div>
						<div class="col-md-2">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.area.code" /><sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly"  maxlength="3" id="areacode" name="areacode" value="">
                            </div><!--group price-->
                        </div>
						<div class="row" style="margin-left:0;margin-right:0px;">
							<div class="col-md-3">
								<div class="group price top-buffer2">
								<label class="label"><spring:message code="label.property.phone.number" /><sup>*</sup></label>
								   <input type="text" class="input-text numbersOnly"  maxlength="10" id="number" name="number" value="">
								</div>
							</div>
						</div>
						
						 <!--<div class="col-md-2">
                            <div class="group price top-buffer2">
                                <label class="label">Phone Number<sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly"  maxlength="10" id="countrycode" name="countrycode" value="">
                            </div><!--group price-->
                       </div>
                    <div class="row">
                        <div class="col-sm-12">

                            <div style="float:left; margin-top:18px;">
                                <!--<input id="showMobile" type="checkbox" name="checkbox"><label for="checkboxphone"><span><span></span></span>Hide  contact info on Aqarabia </label>-->
                            </div><!--inline style-->

                        </div><!--col-sm-6--><div class="col-sm-12">


                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-12">

                             <div class="withother" style="float:left; margin-top:18px;">
                                <input id="share_to_agents" type="checkbox" name="checkbox"><label for="checkboxphone"><span><span></span></span> <spring:message code="label.property.intrested.broker" /> </label>
                            </div><!--inline style-->

                        </div><!--col-sm-6--><div class="col-sm-12">

                            <div  class="withother" id="commissiondiv" style="float:left; margin-top:18px;display:none">
                                <input id="commission" type="checkbox" name="checkbox"><label for="checkboxphone"><span><span></span></span> <spring:message code="label.property.intrested.commission" /> </label>
                            </div><!--inline style-->

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-xs-12 col-sm-6">
                            <input type="button" class="signupbutton top-buffer3" id="contactinfo" value="<spring:message code="label.property.save.continue" />">     
                        </div><!--col-sm-6-->
                    </div><!--row-->
					</div><!--row-->
					</div><!--row-->
					
                </div><!--tab-pane id="b"-->
				
	<!--Contact info tab ends -->
				
<div class="tab-pane contactinfo2" id="c">
<div class="tab-name2"><h2><spring:message code="label.property.basic.details" /></h2></div>
<h3 class="tab-heading" ><p id="rentdet"><spring:message code="label.property.rental.details" /></p></h3>
                    
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.price.in.sar" /><sup>*</sup>
                                </label>
                                <!--<span class="note">per month</span>-->
                                <input type="text" class="input-text" id="price" name="price" value="" autocomplete="off">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label" for="price">
                                    <spring:message code="label.property.dateavailable" /><sup>*</sup>
                                </label>
                                <input id="datepicker" name="dateAvailable" readonly="true" class="input-text input-calendar">
                            </div> <!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.type" /><sup>*</sup>
                                </label>
                                <div class="Property-select">
                                    <select id="propertyType" name="propertyType">
                                        <option value=""><spring:message code="label.property.select.type" /></option>
                                        <!--<option value="1">Apartment</option>-->
                                        <!--<option value="2">Condo</option>-->
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->

                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.listing.type" /><sup>*</sup>
                                </label>
                                <div class="Property-select">
                                    <select id="listingType" name="listingType">
                                        <option value=""><spring:message code="label.property.select.type" /></option>
<!--                                        <option value="1">Resale</option>
                                        <option value="2">New Construction</option>
                                        <option value="3">Foreclosure</option>-->
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.beds" />
                                </label>
                                <div class="Property-select">
                                    <select id="beds" name="beds">
                                        <option value="0"><spring:message code="label.property.select.beds" /></option>
                                        <option value="1">1 Bed</option>
                                        <option value="2">2 Beds</option>
                                        <option value="3">3 Beds</option>
                                        <option value="4">4 Beds</option>
                                        <option value="5">5 Beds</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2" style="float:left; margin-top:20px;">
                                <label class="label">
                                    <spring:message code="label.property.baths" />
                                </label>
                                <div class="Property-select">
                                    <select id="baths" name="baths">
                                        <option value="0"><spring:message code="label.property.select.baths" /></option>
                                        <option value="1">1 Bath</option>	
                                        <option value="2">2 Bath</option>
                                        <option value="3">3 Bath</option>
                                        <option value="4">4 Bath</option>
                                        <option value="5">5 Bath</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.square.feet" /><sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly" id="squareFeet" name="squareFeet" value="">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.unit.floor" /></label>
                                <!--<input type="text" class="input-text numbersOnly" id="unitFloor" name="unitFloor" value="">-->

                                <input type="text" class="input-text numbersOnly" maxlength="3" style="width:80%;" id="unitFloor" name="unitFloor">
                                <span style="margin-left:0px;"><spring:message code="label.property.floor" /></span>
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.year.built" /><sup>*</sup></label>
                                <input type="text" class="input-text numbersOnly" id="yearBuilt" name="yearBuilt" value="">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.developer" /></label>
                                <input type="text" class="input-text" id="developer" name="developer" value="">
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.parking" />
                                </label>
                                <div class="Property-select">
                                    <select id="parkingSpaces" name="parkingSpaces">
                                        <option value="0"><spring:message code="label.select" /></option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">
                            <div class="group price top-buffer2" style="float:left; margin-top:20px;">
                                <label class="label">
                                    <spring:message code="label.property.parking.type" />
                                </label>
                                <div class="Property-select">
                                    <select id="parkingType" name="parkingType">
                                        <option value="0"  selected=""><spring:message code="label.select" /></option>
                                        <option value="garage">Garage</option>
                                        <option value="covered">Carport</option>
                                        <option value="uncovered">Off street</option>
                                        <option value="other">Other</option>
                                    </select>
                                </div><!--Property-select-->
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                    </div><!--row-->

                    <div class="row">
                        <div class="col-xs-12 col-sm-3">
                            <div class="bdetails" style="float:left; margin-top:18px;">
                                <input id="furnished" type="checkbox" name="furnished" value=""><label for="checkbox3"><span><span></span></span><spring:message code="label.property.furnished" /></label>
                            </div><!--inline-Style-->  
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-xs-12 col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.pet.policy" /><sup>*</sup>
                                </label>
                            </div><!--group price-->
                        </div><!--col-sm-6-->
                        <div class="col-sm-6">

                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row bdetails">
                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox5" type="checkbox" name="petsAllowed"  value="0"><label for="checkbox5"><span><span></span></span><spring:message code="label.property.pets.notallowed" /></label>
                            </div><!--inline-Style-->  
                        </div><!--col-sm-6-->
                         <div class="col-xs-6 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox6" type="checkbox" name="petsAllowed" value="1"><label for="checkbox6"><span><span></span></span><spring:message code="label.property.pets.dogsok" /></label>
                            </div><!--inline-Style--> 
                        </div><!--col-sm-6-->
                        <div class="col-xs-6 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox7" type="checkbox" name="petsAllowed"  value="2"><label for="checkbox7"><span><span></span></span><spring:message code="label.property.pets.catsok" /></label>
                            </div><!--inline-Style--> 
                        </div><!--col-sm-6-->
                    </div><!--row-->
					
					
                    <div class="row">
                        <div class="col-xs-12 col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.laundry" />
                                </label>
                            </div><!--group price-->
                        </div><!--col-sm-6-->

                    </div><!--row-->
                    <div class="row bdetails1" >
                        <div class="col-xs-12 col-sm-3  ">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox8" type="radio" name="laundry" checked="true" value="0"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.laundry.none" /></label>
                            </div><!--inline-Style-->  
                        </div><!--col-sm-6-->
                        <div class="col-xs-6 col-sm-3  ">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox9" type="radio" name="laundry" value="1"><label for="checkbox9"><span><span></span></span><spring:message code="label.property.laundry.inunit" /></label>
                            </div><!--inline-Style--> 
                        </div><!--col-sm-6-->
                        <div class="col-xs-6 col-sm-3 ">
                            <div style="float:left; margin-top:18px;" style="margin-left:-2em;">
                                <input id="checkbox10" type="radio" name="laundry" value="2"><label for="checkbox10"><span><span></span></span><spring:message code="label.property.laundry.shared" /></label>
                            </div><!--inline-Style--> 
                        </div><!--col-sm-6-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">

                                    <spring:message code="label.property.category" />
                                </label>
                            </div><!--group price-->
                        </div><!--col-sm-6-->
					</div>
					
                    <div id="propertyCategory" class="row bdetails1">
                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox8" type="checkbox" name="propertycat" value="1"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.newlistings" /></label>
                            </div><!--inline-Style-->  
                        </div>
                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox8" type="checkbox" name="propertycat" value="2"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.upgradedhomes" /></label>
                            </div><!--inline-Style-->  
                        </div>
                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;width:260px">
                                <input id="checkbox8" type="checkbox" name="propertycat" value="3"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.discover" /></label>
                            </div><!--inline-Style-->  
                        </div>

                        <!--                        <div class="col-sm-3">
                                                    <div style="float:left; margin-top:18px;">
                                                        <input id="checkbox8" type="checkbox" name="propertycat" value="4"><label for="checkbox8"><span><span></span></span>New Listings</label>
                                                    </div>inline-Style  
                                                </div>-->



                    </div><!--row-->

                    <div class="row bdetails1">
                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox8" type="checkbox" name="propertycat" value="4"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.safeareas" /></label>
                            </div><!--inline-Style-->  
                        </div> 
                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;">
                                <input id="checkbox8" type="checkbox" name="propertycat" value="5"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.branded" /></label>
                            </div><!--inline-Style-->  
                        </div>

                        <div class="col-xs-12 col-sm-3">
                            <div style="float:left; margin-top:18px;width:260px ">
                                <input id="checkbox8" type="checkbox" name="propertycat" value="6"><label for="checkbox8"><span><span></span></span><spring:message code="label.property.signature" /></label>
                            </div><!--inline-Style-->  
                        </div>
                        <!--                        <div class="col-sm-3">
                                                    <div style="float:left; margin-top:18px;">
                                                        <input id="checkbox8" type="checkbox" name="propertycat" value="8"><label for="checkbox8"><span><span></span></span>Luxury Homes</label>
                                                    </div>inline-Style  
                                                </div>-->
                    </div>

                    <div class="row ">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                   <spring:message code="label.property.crimeinfo" />
                                </label>
                            </div><!--group price-->
                        </div><!--col-sm-6-->

                    </div>
                    <div class="row bdetails-star">                       
                        <fieldset id='demo1' class="rating" style="margin-left: 0px;" >
                            <input class="stars" type="radio" id="star5" name="rating" value="5" />
                            <label class = "full" for="star5" title="Awesome - 5 stars"></label>
                            <input class="stars" type="radio" id="star4" name="rating" value="4" />
                            <label class = "full" for="star4" title="Pretty good - 4 stars"></label>
                            <input class="stars" type="radio" id="star3" name="rating" value="3" />
                            <label class = "full" for="star3" title="Meh - 3 stars"></label>
                            <input class="stars" type="radio" id="star2" name="rating" value="2" />
                            <label class = "full" for="star2" title="Kinda bad - 2 stars"></label>
                            <input class="stars" type="radio" id="star1" name="rating" value="1" />
                            <label class = "full" for="star1" title="Sucks big time - 1 star"></label>
                        </fieldset>                       
                    </div><!--row-->

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">
                                    <spring:message code="label.property.flood" />
                                </label>
                            </div><!--group price-->
                        </div><!--col-sm-6-->

                    </div>
                    <div class="row bdetails-star">                       
                        <fieldset id='demo2' class="rating" style="margin-left: 0px;" >
                            <input class="stars" type="radio" id="star10" name="flood" value="5" />
                            <label class = "full" for="star10" title="Awesome - 5 stars"></label>
                            <input class="stars" type="radio" id="star9" name="flood" value="4" />
                            <label class = "full" for="star9" title="Pretty good - 4 stars"></label>
                            <input class="stars" type="radio" id="star8" name="flood" value="3" />
                            <label class = "full" for="star8" title="Meh - 3 stars"></label>
                            <input class="stars" type="radio" id="star7" name="flood" value="2" />
                            <label class = "full" for="star7" title="Kinda bad - 2 stars"></label>
                            <input class="stars" type="radio" id="star6" name="flood" value="1" />
                            <label class = "full" for="star6" title="Sucks big time - 1 star"></label>
                        </fieldset>                       
                    </div><!--row-->

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="group price top-buffer2">
                                <label class="label">

                                    <spring:message code="label.property.seismic" />
                                </label>
                            </div><!--group price-->
                        </div><!--col-sm-6-->

                    </div>
                    <div class="row bdetails-star">                       
                        <fieldset id='demo3' class="rating" style="margin-left: 0px;" >
                            <input class="stars" type="radio" id="star15" name="seismic" value="5" />
                            <label class = "full" for="star15" title="Awesome - 5 stars"></label>
                            <input class="stars" type="radio" id="star14" name="seismic" value="4" />
                            <label class = "full" for="star14" title="Pretty good - 4 stars"></label>
                            <input class="stars" type="radio" id="star13" name="seismic" value="3" />
                            <label class = "full" for="star13" title="Meh - 3 stars"></label>
                            <input class="stars" type="radio" id="star12" name="seismic" value="2" />
                            <label class = "full" for="star12" title="Kinda bad - 2 stars"></label>
                            <input class="stars" type="radio" id="star11" name="seismic" value="1" />
                            <label class = "full" for="star11" title="Sucks big time - 1 star"></label>
                        </fieldset>                       
                    </div><!--row-->

                    <div class="row">
                        <div class=" col-xs-12 col-sm-3 top-buffer3">
                            <input type="button" class="signupbutton" id="basicdetails" value="<spring:message code="label.property.save.continue" />">     
                        </div><!--col-sm-3-->
                    </div><!--row-->
					
                </div><!--tab-pane id="c"-->
				
	<!--Basic details tab ends -->
				
                <div class="tab-pane contactinfo3" id="d">
				<div  class="tab-name1"><h2><spring:message code="label.property.description" /></h2></div>
                    <h3 class="tab-heading"><spring:message code="label.property.pdescription" /></h3>
                     <div class="row bdetails2">
                        <div class="col-xs-12 col-sm-12 " style="padding-left:0px;">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.details" /><sup>*</sup></label><span class="note"><spring:message code="label.property.limit" /></span>
                                <input type="text" class="input-text" placeholder="" maxlength="120"  id="property_name" name="property_name" value="">
                            </div><!--group price-->
                        </div><!--col-sm-12-->
                    </div><!--row-->
                    <div class="row bdetails2">
                        <div class="col-xs-12 col-sm-12" style="padding-left:0px;">
                            <div class="group price top-buffer2">
                                <label class="label"><spring:message code="label.property.pdescription" /></label>
                                <textarea class="input-text1"id="property_desc" name="property_desc" rows="3"></textarea>
                            </div><!--group price-->
                        </div><!--col-sm-12-->
                    </div><!--row-->
                    <!--                            <div class="row">
                                                    <div class="col-sm-12" style="padding-left:0px;">
                                                        <div class="group price top-buffer2">
                                                            <label class="label">Property Website URL</label>
                                                            <input type="text" class="input-text" placeholder=""  id="" name="" value="">
                                                        </div>group price
                                                    </div>col-sm-12
                                                </div>row-->
                    <div class="row bdetails2">
                        <div class="col-xs-12 col-sm-4 top-buffer2" style="padding-left:0px;">
                            <input type="button" class="signupbutton top-buffer3" id="propertydetails" value="<spring:message code="label.property.save.continue" />">

                        </div><!--col-sm-6-->
                    </div><!--row-->
                </div><!--tab-pane id="d"-->
				
				
	<!--Desc tab ends -->			
				
				
    <div class="tab-pane contactinfo3" id="e">
				<div class="tab-name3"><h2><spring:message code="label.property.photos.media" /></h2></div>
                    <h3 class="tab-heading"><spring:message code="label.property.photos" /></h3>
					<div class="col-xs-12 col-sm-12" style="padding-left:0px;">
					<div class="group price-text top-buffer2">
                    <h6><spring:message code="label.property.photos.description" /></h6>
                    <form id="imageupload" method="post" action="save" enctype="multipart/form-data" style="width:100%;">
					
                        <div class="row bdetails03">
                            <div class="col-sm-6 top-buffer3">
                                <!--<label for="custom-file-upload" class="filupp">-->
                                <!--<span class="filupp-file-name js-value">Choose File</span><span class="glyphicon folderIcon pull-right glyphicon-folder-open"></span>-->
                                <!--<input type="file" name="attachment-file"  id="custom-file-upload">-->
                                <input type="file" multiple="multiple" name="files[]" id="input2">
                                <!--</label>-->
                            </div><!--col-sm-6-->
                        </div><!--row-->
                        <div class="row" style="width:100%;">
                            <div>
                                <input type="hidden" name="propertyid" id="id"/>
                                <!--<input type="button" class="signupbutton top-buffer3" onclick="uploadJqueryForm()" value="Save & Continue">-->
                                <div class="image_class"></div>
                            </div><!--col-sm-6-->
                        </div><!--row-->
                    </form>
					</div><!--group price-->
					
					<div class="row Reviewlink">
                        <div class="col-xs-12 col-sm-4 top-buffer2" style="padding-left:0px;">
                            <a href="#f"  data-toggle="tab" class="signupbutton signupbutton1 top-buffer3">Review</a>
                        </div><!--col-sm-6-->
                    </div><!--row-->
					
                        </div><!--col-sm-12-->
                    
    </div><!--tab-pane id="e"-->		
				
	<!--Photo tab ends -->			
				
                <div class="tab-pane contactinfo3" id="f">
<div class="tab-name3"><h2><spring:message code="label.property.review" /></h2></div>
                    <div class="row">
                        <div class="col-sm-6 top-buffer3">
                            <div id="carousel" class="carousel slide" data-ride="carousel">
                                <div class="carousel-inner" id="imagediv">

                                </div>
                            </div> 
                        </div><!--col-sm-7-->
                        <div class="col-sm-4 top-buffer3">


                        </div><!--col-sm-7-->
                    </div><!--row-->

                    <div class="row">
                        <div class="col-sm-10 top-buffer3">
                            <h3 class="tab-heading"><spring:message code="label.property.details" /></h3>
                        </div><!--col-sm-10-->
                        <div class="col-sm-2 top-buffer3">
                            <!--<span class="glyphicon glyphicon-edit editProperty "></span>-->
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading" id="rentbyid1"><spring:message code="label.property.rent" /></p> 
                            <p id="price1">--</p>    
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.dateavailable" /></p> 
                            <p id="datepicker1">--</p>
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.type" /></p> 
                            <p id="propertyType1">--</p>
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.beds" /></p> 
                            <p id="beds1">--</p>    
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.baths" /></p> 
                            <p id="baths1">--</p>
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.square.feet" /></p> 
                            <p id="squareFeet1">--</p>
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.unit.floor" /></p> 
                            <p id="unitFloor1">--</p>    
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.year.built" /></p> 
                            <p id="yearBuilt1">--</p>
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.furnished" /></p> 
                            <p id="Furnished1">--</p>
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.pet.policy" /></p> 
                            <p id="petPolicy1">--</p>    
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.parking" /></p> 
                            <p id="parkingSpaces1">--</p>
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.parking.type" /></p> 
                            <p id="parkingType1">--</p>
                        </div><!--col-sm-10-->
                    </div><!--row--> 
					<div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.laundry" /></p> 
                            <p id="Laundry1">--</p>    
                        </div><!--col-sm-10--><div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.listing.type" /></p> 
                            <p id="listingType1">--</p>    
                        </div><!--col-sm-10--><div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.developer" /></p> 
                            <p id="Developer1">--</p>    
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row">
                        <div class="col-sm-10 top-buffer3">
                            <h3 class="tab-heading"><spring:message code="label.property.pdescription" /></h3>
                        </div><!--col-sm-10-->
                        <div class="col-sm-2 top-buffer3">
                            <!--<span class="glyphicon glyphicon-edit editProperty "></span>-->
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.title" /></p> 
                            <p id="property_name1">--</p>    
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.pdescription" /></p> 
                            <p id="property_desc1">--</p>    
                        </div><!--col-sm-10-->
                    </div><!--row-->

                    <div class="row">
                        <div class="col-sm-10 top-buffer3">
                            <h3 class="tab-heading"><spring:message code="label.property.contact.information" /></h3>
                        </div><!--col-sm-10-->
                        <div class="col-sm-2 top-buffer3">
                            <!--<span class="glyphicon glyphicon-edit editProperty "></span>-->
                        </div><!--col-sm-10-->
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.name" /></p> 
                            <p id="name1">--</p>    
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.company" /></p> 
                            <p id="company1">--</p>
                        </div>
                    </div><!--row-->
                    <div class="row review1">
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.phone" /></p> 
                            <p id="phone1">--</p>    
                        </div><!--col-sm-10-->
                        <div class="col-sm-4 top-buffer2">
                            <p class="Details-heading"><spring:message code="label.property.email" /></p> 
                            <p id="email1">--</p>
                        </div>
                    </div><!--row-->
                    <div class="row">
                        <div class=" col-xm-12 col-sm-3 top-buffer3 reviewbut">
                            <input type="button" class="signupbutton" id="activateproperty" value="<spring:message code="label.property.activate.property" />">
                        </div><!--col-sm-6-->
                        <!--                                <div class="col-sm-4 top-buffer3">
                                                            <input type="button" class="clear-button" value="Customize Property">
                                                        </div>col-sm-6-->
                    </div><!--row-->
                </div><!--tab-pane id="f"--> 
	<!-- Review tab ends here -->

				
            </div><!--tab-content-->    
        </div><!--wrap tabbable tabs-left-->
    </div><!--Registration-tab-->


</div><!--container-->
<footer>
    <div class="container">
        <div class="row">
            <div class="Draft-GrayBox"></div>
        </div>
    </div><!--container-->
</footer>
<!-- jQuery -->


<script type="text/javascript" src="resources/js/jquery.filer.min.js"></script>

<style type="text/css" media="screen">
    .map_canvas { float: left; }
   
    fieldset label { display: block; margin: 0.5em 0 0em; }
    fieldset input { width: 95%; }
</style>

</head>
<script>
    function loadPrpertyType(nCat) {
        $.ajax({
            url: "getPropertyTypesAdmin?nCat=" + nCat,
            type: "GET",
            dataType: "json",
            async: false,
            contentType: "application/json",
            success: function (response)
            {
                response = response.response.propertyTypes;

                $.each(response, function (idx, rec) {
//                        alert(rec.propertyTypes.toString());
                    $('<option/>', {
                        'value': rec.id,
                        'text': rec.name
                    }).appendTo('#propertyType');
                    //  $("#content").append('<h1>'+rec.Id+'</h1>'+'<p>'rec.Title+'</p>'+'<span>'+rec.url+'</span>').slideDown('slow');
                })

            }
        });
    }
    
    function loadListingType(nCat) {
        $.ajax({
            url: "getListingTypes?nCat=" + nCat,
            type: "GET",
            dataType: "json",
            async: false,
            contentType: "application/json",
            success: function (response)
            {
                console.log(response.response.listingTypes);
                response = response.response.listingTypes;
                $.each(response, function (idx, rec) {
//                        alert(rec.propertyTypes.toString());
                    $('<option/>', {
                        'value': rec.id,
                        'text': rec.name
                    }).appendTo('#listingType');
                    //  $("#content").append('<h1>'+rec.Id+'</h1>'+'<p>'rec.Title+'</p>'+'<span>'+rec.url+'</span>').slideDown('slow');
                })

            }
        });
    }
       tab = getParameterByName("tab");
        propertyIdMap = getParameterByName("id");
//
        if (tab != '') {
            pagetab(tab);
        }
    $(document).ready(function () {
        $("#demo1 .stars").click(function () {

            $.post('rating.php', {rate: $(this).val()}, function (d) {
                if (d > 0)
                {
                    alert('You already rated');
                } else {
                    alert('Thanks For Rating');
                }
            });
            $(this).attr("checked");
        });
     



//          
//         //   $(".Registration-tab li").removeClass("active");
//           // $(".1").attr("aria-expanded","false");
//          //  $("#" + tab).addClass("active");
//          //  $("#3").attr("aria-expanded","true");
//        }

        $("#datepicker").datepicker({dateFormat: 'DD,dd MM yy'});
        $("#activateproperty").click(function () {
            // alert($("#id").val());
            activateProperty($("#id").val());
        });


        errorid = "#property_name,#geocomplete,#formatted_address,#city,#administrative_area_level_1,#country,#postal_code,#name,#company,#email,#countrycode,#areacode,#number,#price,#datepicker,#propertyType,#squareFeet,#yearBuilt";
        $(errorid).keyup(function () {
            $(errorid).removeClass("input-error");

            //$("#formatted_address").removeClass("input-error");


        });
        $("#datepicker,#propertyType").click(function () {
            // alert($("#id").val());
            $(errorid).removeClass("input-error");
        });
        $("#share_to_agents").click(function () {
            // alert($("#id").val());

            $("#commissiondiv").toggle();
        });
        $("#checkbox5").click(function () {
            // alert($("#id").val());
            if ($("#checkbox5").is(":checked")) {

                // $("#checkbox6").toggle();
                $("#checkbox6").prop('checked', false);
                $("#checkbox7").prop('checked', false);
            }

        });
        $("#checkbox7").click(function () {
            // alert($("#id").val());
            if ($("#checkbox7").is(":checked")) {

                // $("#checkbox6").toggle();
                $("#checkbox5").prop('checked', false);
                //$("#checkbox7").prop('checked', false);
            }

        });
        $("#checkbox6").click(function () {
            // alert($("#id").val());
            if ($("#checkbox6").is(":checked")) {

                // $("#checkbox6").toggle();
                $("#checkbox5").prop('checked', false);

            }

        });
        $("#location").click(function () {

            var postal_code = $('#postal_code').val();
            var zipRegex = /^\d{6}$/;
            var location = {};
            country = $("#country").val();
            city = $("#city").val();
            address = $("#formatted_address").val();
            state = $("#administrative_area_level_1").val();
            iserror = false;
            if (country == '') {
                $("#country").addClass("input-error");
                iserror = true;
            } else {
                $("#country").removeClass("input-error");
            }
            if (city == '') {
                $("#city").addClass("input-error");
                iserror = true;
            } else {
                $("#city").removeClass("input-error");
            }

            if (address == '') {
                $("#formatted_address").addClass("input-error");
                iserror = true;
            } else {
                $("#formatted_address").removeClass("input-error");
            }

            if (state == '') {
                $("#administrative_area_level_1").addClass("input-error");
                iserror = true;
            } else {
                $("#administrative_area_level_1").removeClass("input-error");
            }

            if (postal_code == '') {
                $("#postal_code").addClass("input-error");
                iserror = true;
            } else {
                $("#postal_code").removeClass("input-error");
            }
            if (iserror) {
                return;
            }

//            if (!zipRegex.test(postal_code))
//            {
//                sweetAlert("Oops...", "Your Zip Code must be  numbers!", "error");
//                return;
//            }
            location['country'] = $("#country").val();
            location['address'] = $("#formatted_address").val();
            location['propertyUnit'] = $("#propertyUnit").val();
            location['state'] = $("#administrative_area_level_1").val();
            location['city'] = $("#city").val();
            location['longitude'] = $("#longitude").val();
            location['latitude'] = $("#latitude").val();
            location['pincode'] = postal_code;
            location['category'] = $('input[name=category]:checked').val();
            id = getParameterByName("id");
            if (id == '') {
                id = 0;
            } else {

            }
            location['id'] = id;
            var locationjson = JSON.stringify(location);
            // alert(locationjson);
            $.ajax({
                url: "createproperty",
                type: "POST",
                data: locationjson,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data)
                {

                    code = data.id
                    if (code > 0) {
                        id = data.id;
                        $("#id").val(id);
                        swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                window.location = 'showproperty?tab=b&id=' + id;
                            }
                        });
                    }
                }
            });
        });
        $("#propertycontact").click(function () {
            var propertycontact = {};
            name = $("#name").val();
            company = $("#company").val();
            email = $("#email").val();
            phone = $("#phone").val();
            if (name == '') {
                $("#name").addClass("input-error");
                return;
            }
            if (company == '') {
                $("#company").addClass("input-error");
                return;
            }
            if (email == '') {
                $("#email").addClass("input-error");
                return;
            }
            if (phone == '') {
                $("#phone").addClass("input-error");
                return;
            }


            propertycontact['name'] = name;
            propertycontact['company'] = company;
            propertycontact['email'] = email;
            propertycontact['id'] = $("#id").val();
            propertycontact['phone'] = phone;
            var propertyjson = JSON.stringify(propertycontact);
            //    alert(propertyjson);
            $.ajax({
                url: "createproperty",
                type: "POST",
                data: propertyjson,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data)
                {

                    code = data.id;
                    if (code > 0) {
                        id = data.id;
                        $("#id").val(id);
                        swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
                        loadjson(data);
                    }
                }
            });
        });
        $("#propertydetails").click(function () {
            var propertydetails = {};
            property_name = $("#property_name").val();
            if (property_name == '') {
                $("#property_name").addClass("input-error");
                return;
            } else {
                $("#property_name").removeClass("input-error");
            }

            propertydetails['propertyName'] = property_name;
            propertydetails['description'] = $("#property_desc").val();
            propertydetails['id'] = $("#id").val();
            var propertydet = JSON.stringify(propertydetails);
            $.ajax({
                url: "createproperty",
                type: "POST",
                data: propertydet,
                dataType: "json",
               contentType: "application/json; charset=utf-8",
                success: function (data)
                {


                    code = data.id;
                    if (code > 0) {
                        id = data.id;
                        //   swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});

                        //       loadjson(data);
                        swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                window.location = 'showproperty?tab=e&id=' + id;
                            }
                        });
                    }
                }
            });
        });
        $("#basicdetails").click(function () {
  iserror = false;
            var basicdetails = {};
            propertycat = "";
            $('input[name="propertycat"]:checked').each(function () {
                propertycat = propertycat + this.value + ",";
            });
            petsAllowed = 4;
            i = 0;
            $('input[name="petsAllowed"]:checked').each(function () {
                if (i == 0) {
                    petsAllowed = this.value;
                } else {
                    petsAllowed = petsAllowed + "," + this.value;
                }

            });
            crime_info = $('input[name="rating"]:checked').val();
            flood = $('input[name="flood"]:checked').val();
            seismic = $('input[name="seismic"]:checked').val();


            if (petsAllowed != "" && petsAllowed == "1,2") {
                petsAllowed = 3;
            }
            if (petsAllowed != "") {
                basicdetails['petsAllowed'] = petsAllowed;
            }

            laundry = 0;
            $('input[name="laundry"]:checked').each(function () {
                laundry = this.value;
                basicdetails['laundry'] = laundry;
            });
            price = $("#price").val()
            price=price.replace(",", "");
        
           
            if (price == '') {
                $("#price").addClass("input-error");
                iserror = true;
            } else {
                $("#price").removeClass("input-error");
            }
            
                if (!$.isNumeric(price)) {
                
     $("#price").addClass("input-error");
                iserror = true;
              
}else {
                $("#price").removeClass("input-error");
            }

            datepicker = $("#datepicker").val();
            if (datepicker == '') {
                $("#datepicker").addClass("input-error");
                iserror = true;
            } else {
                $("#datepicker").removeClass("input-error");
            }
            yearBuilt = $("#yearBuilt").val();
            if (yearBuilt == '') {
             
                $("#yearBuilt").addClass("input-error");
                iserror = true;
            } else {
                $("#yearBuilt").removeClass("input-error");
            }
          
           
           if (yearBuilt.length != 4||!$.isNumeric(yearBuilt)) {
                $("#yearBuilt").addClass("input-error");
                  iserror = true;
            } else {
                $("#yearBuilt").removeClass("input-error");
              
            }

            squareFeet = $("#squareFeet").val()

            if (squareFeet == '') {
                $("#squareFeet").addClass("input-error");
                iserror = true;
            } else {
                $("#squareFeet").removeClass("input-error");
            }


            unitFloor = $("#unitFloor").val();
            if (unitFloor == '') {
                $("#unitFloor").addClass("input-error");
                iserror = true;
            } else {
                $("#unitFloor").removeClass("input-error");
            }

            propertyType = $("#propertyType").val();
            if (propertyType == '') {
                $("#propertyType").addClass("input-error");
                iserror = true;
            } else {
                $("#propertyType").removeClass("input-error");
            }
            listingType = $("#listingType").val();
            developer = $("#developer").val();
            if (listingType == '') {
                $("#listingType").addClass("input-error");
                iserror = true;
            } else {
                $("#listingType").removeClass("input-error");
            }
            if (iserror) {
                return;
            }
            basicdetails['listingType'] = listingType;
            basicdetails['developer'] = developer;
            basicdetails['crime_info'] = crime_info;
            basicdetails['flood'] = flood;
            basicdetails['seismic'] = seismic;

            basicdetails['price'] = price;
            basicdetails['dateAvailable'] = datepicker;
            basicdetails['propertyType'] = $("#propertyType").val();
            basicdetails['beds'] = $("#beds").val();
            basicdetails['baths'] = $("#baths").val();
            basicdetails['squareFeet'] = squareFeet;
            basicdetails['unitFloor'] = unitFloor;
            basicdetails['yearBuilt'] = yearBuilt;
            basicdetails['propertycat'] = propertycat;
            if ($("#furnished").is(":checked")) {
                basicdetails['furnished'] = 1;
            } else {
                basicdetails['furnished'] = 0;
            }

            basicdetails['parkingSpaces'] = $("#parkingSpaces").val();
            basicdetails['parkingType'] = $("#parkingType").val();
            basicdetails['id'] = $("#id").val();
            var propertybasicdetails = JSON.stringify(basicdetails);
            $.ajax({
                url: "createproperty",
                type: "POST",
                data: propertybasicdetails,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data)
                {

                    code = data.id;
                    if (code > 0) {
                        id = data.id;
                        // swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
                        //loadjson(data);
                        swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                window.location = 'showproperty?tab=d&id=' + id;
                            }
                        });
                    }
                }
            });
        });
        $("#contactinfo").click(function () {
            var contactinfodetails = {};
            contacttype = $(".contacttype").val();
            name = $("#name").val();
            company = $("#company").val();
            email = $("#email").val();
            phone = $("#countrycode").val() + "-" + $("#areacode").val() + "-" + $("#number").val();
            isrror = false;
            countrycode = $("#countrycode").val();
            if (countrycode == '') {
                $("#countrycode").addClass("input-error");
                isrror = true;
            } else {
                $("#countrycode").removeClass("input-error");
            }

            areacode = $("#areacode").val();
            if (areacode == '') {
                $("#areacode").addClass("input-error");
                isrror = true;
            } else {
                $("#areacode").removeClass("input-error");
            }

            number = $("#number").val();
            if (number == '') {
                $("#number").addClass("input-error");
                isrror = true;
            } else {
                $("#number").removeClass("input-error");
            }



            if (name == '') {
                $("#name").addClass("input-error");
                isrror = true;
            } else {
                $("#name").removeClass("input-error");
            }
            if (company == '') {
                $("#company").addClass("input-error");
                isrror = true;
            } else {
                $("#company").removeClass("input-error");
            }
            if (email == '') {
                $("#email").addClass("input-error");
                isrror = true;
            } else {
                $("#email").removeClass("input-error");
            }

            if (phone == '') {
                $("#phone").addClass("input-error");
                isrror = true;
            } else {
                $("#phone").removeClass("input-error");
            }
            if (isrror) {
                return;
            }
            if (!validateEmail(email)) {
                sweetAlert("Oops...", "Invalid Email Id", "error");
                return;
            }
            if (!validatePhone(phone)) {
                sweetAlert("Oops...", "Invalid Phone Number", "error");
                return;
            }
            contactinfodetails['contacttype'] = contacttype;
            contactinfodetails['name'] = name;
            contactinfodetails['company'] = company;
            contactinfodetails['email'] = email;
            if ($('#showEmail').is(":checked")) {
                contactinfodetails['showEmail'] = 1;
            } else {
                contactinfodetails['showEmail'] = 2;
            }
            if ($('#showMobile').is(":checked")) {
                contactinfodetails['showMobile'] = 1;
            } else {
                contactinfodetails['showMobile'] = 2;
            }

            if ($('#share_to_agents').is(":checked")) {
                contactinfodetails['share_to_agents'] = 1;
                if ($('#commission').is(":checked")) {
                    contactinfodetails['commission'] = 1;
                } else {
                    contactinfodetails['commission'] = 0;
                }
            } else {
                contactinfodetails['share_to_agents'] = 0;
                contactinfodetails['commission'] = 0;
            }

            contactinfodetails['isPropertyContactDeatils'] = true;
            contactinfodetails['phone'] = phone;
            contactinfodetails['id'] = $("#id").val();
            var contactinfo = JSON.stringify(contactinfodetails);
            //alert(contactinfo);
            $.ajax({
                url: "createproperty",
                type: "POST",
                data: contactinfo,
                dataType: "json",
               contentType: "application/json; charset=utf-8",
                success: function (data)
                {

                    code = data.id;
                    if (code > 0) {
                        id = data.id;
                        swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                            if (isConfirm) {
                                window.location = 'showproperty?tab=c&id=' + id;
                            }
                        });
                        //  swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
                        // loadjson(data);
                        // pagetab("c");
                    }
                }
            });
        });
        $("#geocomplete").geocomplete({
            map: ".map_canvas",
            details: "form",
            types: ["geocode", "establishment"],
        });

        if (tab == '' && propertyIdMap == '') {

            var map = $("#geocomplete").geocomplete("map")
            var center = new google.maps.LatLng(25.204849, 55.270783);
            map.setCenter(center);
        }
        $("#geocomplete").blur(function () {
            $("#country").removeClass("input-error");
            $("#city").removeClass("input-error");
            $("#formatted_address").removeClass("input-error");
            $("#administrative_area_level_1").removeClass("input-error");
            $("#postal_code").removeClass("input-error");
        });
        $("#Clear").click(function () {

            $("#country").val("");
            $("#city").val("");
            $("#formatted_address").val("");
            $("#administrative_area_level_1").val("");
            $("#postal_code").val("");
        });
        $("#find").click(function () {

            $("#geocomplete").trigger("geocode");
        });
//file upload logic
        $('#input1').filer();
        $('#input2').filer({
            limit: null,
            maxSize: null,
            extensions: null,
            changeInput: '<div class="jFiler-input-dragDrop"><div class="jFiler-input-inner"><div class="jFiler-input-icon"><i class="icon-jfi-cloud-up-o"></i></div><div class="jFiler-input-text"><h3>Drag&Drop files here</h3> <span style="display:inline-block; margin: 15px 0">or</span></div><a class="jFiler-input-choose-btn blue">Browse Files</a></div></div>',
            showThumbs: false,
            appendTo: null,
            theme: "dragdropbox",
            templates: {
                box: '<ul class="jFiler-item-list"></ul>',
                item: '<li class="jFiler-item">\
                        <div class="jFiler-item-container">\
                            <div class="jFiler-item-inner">\
                                <div class="jFiler-item-thumb">\
                                    <div class="jFiler-item-status"></div>\
                                    <div class="jFiler-item-info">\
                                        <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
                                    </div>\
                                    {{fi-image}}\
                                </div>\
                                <div class="jFiler-item-assets jFiler-row">\
                                    <ul class="list-inline pull-left">\
                                        <li>{{fi-progressBar}}</li>\
                                    </ul>\
                                    <ul class="list-inline pull-right">\
                                        <li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
                                    </ul>\
                                </div>\
                            </div>\
                        </div>\
                    </li>',
                itemAppend: '<li class="jFiler-item">\
                        <div class="jFiler-item-container">\
                            <div class="jFiler-item-inner">\
                                <div class="jFiler-item-thumb">\
                                    <div class="jFiler-item-status"></div>\
                                    <div class="jFiler-item-info">\
                                        <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
                                    </div>\
                                    {{fi-image}}\
                                </div>\
                                <div class="jFiler-item-assets jFiler-row">\
                                    <ul class="list-inline pull-left">\
                                        <span class="jFiler-item-others">{{fi-icon}} {{fi-size2}}</span>\
                                    </ul>\
                                    <ul class="list-inline pull-right">\
                                        <li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
                                    </ul>\
                                </div>\
                            </div>\
                        </div>\
                    </li>',
                progressBar: '<div class="bar"></div>',
                itemAppendToEnd: false,
                removeConfirmation: false,
                _selectors: {
                    list: '.jFiler-item-list',
                    item: '.jFiler-item',
                    progressBar: '.bar',
                    remove: '.jFiler-item-trash-action',
                }
            },
            uploadFile: {
                url: "user/save?id=" + getParameterByName("id"),
                data: {},
                type: 'POST',
                enctype: 'multipart/form-data',
                beforeSend: function () {
                       $('#dvLoading').show();
                },
                success: function (data, el) {
                     $('#dvLoading').hide();
                    //  alert(data)
//                    var parent = el.find(".jFiler-jProgressBar").parent();
//                    el.find(".jFiler-jProgressBar").fadeOut("slow", function () {
//                        $("<div class=\"jFiler-item-others text-success\"><i class=\"icon-jfi-check-circle\"></i> Success</div>").hide().appendTo(parent).fadeIn("slow");
//                    });
                    window.location = 'showproperty?tab=e&id=' + $("#id").val();
//                    swal({title: "Success", text: "Property Updated Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
//                        if (isConfirm) {
//                            window.location = 'showproperty?tab=f&id=' + $("#id").val();
//                        }
//                    });
                },
                error: function (el) {
                     $('#dvLoading').hide();
                    var parent = el.find(".jFiler-jProgressBar").parent();
                    el.find(".jFiler-jProgressBar").fadeOut("slow", function () {
                        $("<div class=\"jFiler-item-others text-error\"><i class=\"icon-jfi-minus-circle\"></i> Error</div>").hide().appendTo(parent).fadeIn("slow");
                    });
                },
                statusCode: {},
                onProgress: function () {
                },
            },
            dragDrop: {
                dragEnter: function () {
                },
                dragLeave: function () {
                },
                drop: function () {
                },
            },
            addMore: true,
            clipBoardPaste: true,
            excludeName: null,
            beforeShow: function () {
                return true
            },
            onSelect: function () {
            },
            afterShow: function () {
            },
            onRemove: function () {
            },
            onEmpty: function () {
            },
            captions: {
                button: "Choose Files",
                feedback: "Choose files To Upload",
                feedback2: "files were chosen",
                drop: "Drop file here to Upload",
                removeConfirmation: "Are you sure you want to remove this file?",
                errors: {
                    filesLimit: "Only {{fi-limit}} files are allowed to be uploaded.",
                    filesType: "Only Images are allowed to be uploaded.",
                    filesSize: "{{fi-name}} is too large! Please upload file up to {{fi-maxSize}} MB.",
                    filesSizeAll: "Files you've choosed are too large! Please upload files up to {{fi-maxSize}} MB."
                }
            }
        });
    });
    loadpropertycat();
    function  loadpropertycat() {

        $.ajax({
            url: "property/getpropertyCategory",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data)
            {

// $.each(data.response.propertyCategory, function (index, value) {
//  $("#propertyCategory").append("<div class=\"col-sm-3\"><div style=\"float:left; margin-top:18px;\"><input id=\"checkbox8\" type=\"checkbox\" name=\"propertycat\" value=\""+value.id+"\"><label for=\"checkbox8\"><span><span></span></span>"+value.name+"</label></div></div>");
//    });

                // obj = $.parseJSON(data.);
                //alert(obj.propertyCategory);
            }
        });
    }


</script>


<script>

    function deleteimage(id, pId) {


        $.ajax({
            url: "deleteimage?id=" + id,
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (data)
            {

                code = data.response.code;
                if (code == 0) {
//                       swal("Success", "Property Acticated Successfully", "success")
                    swal({title: "Success", text: "Image Deleted Successfully", imageUrl: "resources/images/thumbs-up.jpg", showCancelButton: false, confirmButtonText: "OK", closeOnConfirm: false, closeOnCancel: false}, function (isConfirm) {
                        if (isConfirm) {
                            window.location = 'showproperty?tab=e&id=' + pId;
                        }
                    });
                    //    swal({title: "Success", text: "Property Acticated Successfully", imageUrl: "resources/images/thumbs-up.jpg"});
//                    window.location = 'home';
                } else {
                    sweetAlert("Update Failed...", "Image Deletion Failed", "error");
                }

            }, error: function (request, status, error) {
                sweetAlert("Update Failed...", "Image Deletion Failed", "error");
            }
        });

    }
    // alert("${propertie}");
//    string foo = Encoding.ASCII.GetString('${propertie}');
//     alert(foo);
//    json=bin2string('${propertie}');
  
   
getPropertyId();
    function getPropertyId() {
      id=getParameterByName("id");
        $.ajax({
            url: "showpropertyResult?id="+id,
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (response)
            {
//alert(response.propertie);
loadjson(response.propertie,response.objPropertyType,response.objListingType);
            }
        });
    }
    function loadjson(propertie,objPropertyType,objListingType) {
        if (propertie != "") {

            try {
                obj = $.parseJSON(propertie);
            } catch (e) {
                obj = propertie;
            }
            var length = Object.keys(obj).length;
            if (length > 0) {
//                var options = {
//                    map: ".map_canvas",
//                    location: obj.address
//                };
//                $("#geocomplete").geocomplete(options);

                $("#geocomplete").geocomplete({
                    map: ".map_canvas",
                    details: "form",
                    types: ["geocode", "establishment"]
                });


                var map = $("#geocomplete").geocomplete("map")
                var center = new google.maps.LatLng(obj.latitude, obj.longitude);
                map.setCenter(center);
                      $("#id").val(obj.id);
                // alert(obj.address);
                $("#formatted_address").val(obj.address);
                $("#geocomplete").val(obj.address);
                $("#country").val(obj.country);
                $("#postal_code").val(obj.pincode);
                $("#administrative_area_level_1").val(obj.state);
                $("#city").val(obj.city);
                $("#propertyUnit").val(obj.propertyUnit);
                //start of basic details
                if (obj.price != 0) {
                    $("#price").val(obj.price);
                    $("#price1").text(obj.price);
                }

                if (obj.dateAvailable != "" && obj.dateAvailable != "null") {
                    date = obj.dateAvailable;
                    $("#datepicker").val(date);
                    $("#datepicker1").text(date);
                }

                if (obj.category == 2) {
                    
                    $("#rentbyid").html("For Rent <sup>*</sup>");
                     
                } if (obj.category == 1) {
                    
                    
                      $("#rentdet").html("Sale Details <sup></sup>");
                       $("#rentbyid1").html("Sale");
                      
                }
                $("input[name=category]").val([obj.category]);
                loadPrpertyType([obj.category]);
//               
                if (obj.propertyType != 0) {
                    ptype = obj.propertyType;

                    $("#propertyType").val(ptype);
                    objProperty = $.parseJSON(objPropertyType);

                   <!-- $("#propertyType1").text(objProperty.response.propertyTypes[ptype].name);-->
                     $.each(objProperty.response.propertyTypes, function (idx, rec) {
                         //alert(rec.id);
                                                if(ptype==rec.id){
                                                                               $("#propertyType1").text(rec.name);
                                                                                                        }
                                                                                                                        })
                }
                
                loadListingType([obj.category]);
                
                 if (obj.listingType != 0) {
                    ltype = obj.listingType;

                    $("#listingType").val(ltype);
                    
                    objProperty = $.parseJSON(objListingType);
                    
                     $.each(objProperty.response.listingTypes, function (idx, rec) {
                         //alert(rec.id);
                                                if(ltype==rec.id){
                                                                               $("#listingType1").text(rec.name);
                                                                                                        }
                                                                                                                        })
                }
                
                $("#beds").val(obj.beds);
                $("#beds1").text(obj.beds);
                $("#baths").val(obj.bath);
                $("#baths1").text(obj.bath);
                if (obj.footRange != 0) {
                    $("#squareFeet").val(obj.footRange);
                    $("#squareFeet1").text(obj.footRange);
                }

                $("#unitFloor").val(obj.lotSize);
                $("#unitFloor1").text(obj.lotSize);
                if (obj.yearBuilt != 0) {
                    $("#yearBuilt").val(obj.yearBuilt);
                    $("#yearBuilt1").text(obj.yearBuilt);
                }
                if (obj.parkingSpace != '')
                    $("#parkingSpaces").val(obj.parkingSpace);
                if (obj.parkingType != '' && obj.parkingType != undefined)
                    $("#parkingType").val(obj.parkingType);
                $("#parkingSpaces1").text(obj.parkingSpace);
                $("#parkingType1").text(obj.parkingType);
                furnished = obj.furnished;

                $("#developer").val(obj.developer);
                $("#Developer1").text(obj.developer);
//$("#crime_info").val(5);

                if (obj.crimeInfo != '' && obj.crimeInfo != undefined) {
                    crime_info = obj.crimeInfo;

                    for (i = 0; i <= crime_info; i++) {
                        // text += cars[i] + "<br>";

                        $("input[name=rating][value='" + i + "']").prop("checked", true);
                    }
                }

                if (obj.flood != '' && obj.flood != undefined) {
                    flood = obj.flood;

                    for (i = 0; i <= flood; i++) {
                        // text += cars[i] + "<br>";

                        $("input[name=flood][value='" + i + "']").prop("checked", true);
                    }
                }

                if (obj.seismic != '' && obj.seismic != undefined) {
                    seismic = obj.seismic;

                    for (i = 0; i <= seismic; i++) {
                        // text += cars[i] + "<br>";

                        $("input[name=seismic][value='" + i + "']").prop("checked", true);
                    }
                }
                
               

                if (furnished == 1) {
                    $('#furnished').prop('checked', true);
                    $("#Furnished1").text("Yes");
                }

                // laundry= obj.laundry;
                petsAllowed = obj.pets_allowed;
                if (petsAllowed == 0 || petsAllowed == 1 || petsAllowed == 2) {
                    $("input[name=petsAllowed][value='" + petsAllowed + "']").prop("checked", true);
                } 
//                else {
//                    $("input[name=petsAllowed][value='" + 1 + "']").prop("checked", true);
//                    $("input[name=petsAllowed][value='" + 2 + "']").prop("checked", true);
//                }
                if (petsAllowed == 0) {
                    $("#petPolicy1").text("No Pet Allowed");
                } else if (petsAllowed == 1) {
                    $("#petPolicy1").text("Dogs Ok");
                } else if (petsAllowed == 2) {
                    $("#petPolicy1").text("Cats Ok");
                }else {
                    $("#petPolicy1").text("None");
                }
                $("input[name=laundry][value='" + obj.laundry + "']").prop("checked", true);
                if (obj.laundry == 0) {
                    $("#Laundry1").text("None");
                } else if (obj.laundry == 1) {
                    $("#Laundry1").text("In Unit");
                } else if (obj.laundry == 2) {
                    $("#Laundry1").text("Shared");
                }
                // $("#laundry").val(obj.laundry);
//end of basic details

                property_features_mapping = obj.property_features_mapping;
                if (property_features_mapping != "" && property_features_mapping != undefined) {
                    var array = property_features_mapping.split(",");
                    $.each(array, function (i) {

                        $("input[name=propertycat][value='" + array[i] + "']").prop("checked", true);
                    });
                }

//start of property desc
                $("#property_name").val(obj.name);
                $("#property_desc").val(obj.description);
                $("#property_name1").text(obj.name);
                $("#property_desc1").text(obj.description);
////end of property desc
//start of contact info
                $("#name").val(obj.propertyContactDetails.name);
                $("#company").val(obj.propertyContactDetails.company);
                $("#email").val(obj.propertyContactDetails.email);
                phone = obj.propertyContactDetails.mobile;

                if (phone != "" && phone.split("-").length > 1) {
                    $("#countrycode").val(phone.split("-")[0]);
                    $("#areacode").val(phone.split("-")[1]);
                    $("#number").val(phone.split("-")[2]);
                }
                // $("#phone").val(obj.propertyContactDetails.mobile);

                $("#name1").text(obj.propertyContactDetails.name);
                $("#company1").text(obj.propertyContactDetails.company);
                $("#email1").text(obj.propertyContactDetails.email);
                $("#phone1").text(obj.propertyContactDetails.mobile);
                showEmail = obj.propertyContactDetails.showemail;
                if (showEmail == 1) {
                    $('#showEmail').prop('checked', true);
                }

                share_to_agents = obj.share_to_agents;
                if (share_to_agents == 1) {
                    $('#share_to_agents').prop('checked', true);
                    $('#commissiondiv').css("display", "block");
                }
                commission = obj.commission;
                if (commission == 1) {
                    $('#commission').prop('checked', true);
                }

//                showMobile = obj.propertyContactDetails.showmobile;
//                if (showMobile == 1) {
//                    $('#showMobile').prop('checked', true);
//                }

                $("input[name=contacttype]").val([obj.propertyContactDetails.type]);
//end of contact into
//start of image
                $("#imagediv").html("");
                $.each(obj.images, function (index, value) {
                    if (index == 0) {

                        $(".image_class").append("<div class=\"gallery-img2\"><a class=\"imgsmall-delete\" onclick=\"deleteimage('" + value.id + "','" + obj.id + "')\" href=\"#\">x</a><img src=\"" + value.imageLink + "\"></div>");
                        $("#imagediv").append("<div class=\"item item-big active\"><img src=\"" + value.imageLink + "\"></div>");
                    } else {
                        $(".image_class").append("<div class=\"gallery-img2\"><a class=\"imgsmall-delete\" onclick=\"deleteimage('" + value.id + "','" + obj.id + "')\" href=\"#\">x</a><img src=\"" + value.imageLink + "\"></div>");
                        $("#imagediv").append("<div class=\"item item-big\"><img src=\"" + value.imageLink + "\"></div>");
                    }

                });
//end of image
            }

        }
    }
</script>
<script src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>
<script src="resources/js/calendar.js"></script>

</body>

</html>
