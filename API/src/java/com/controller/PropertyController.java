/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

/**
 *
 * @author chhavikumar.b
 */
import com.beans.PropertyBean;
import static com.common.ResponseCodes.ServiceErrorCodes.GENERIC_ERROR;
import static com.common.ResponseCodes.ServiceErrorCodes.INSERT_FAILED;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_ADDRESS;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_AGENT_ID;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_AGENT_TYPE;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_EMAILID;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_JSON;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_MOBILE_NUMBER;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_PROPERTY_CATEGORY;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_PROPERTY_ID;
import static com.common.ResponseCodes.ServiceErrorCodes.SUCCESS;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_RADIUS;
import static com.common.ResponseCodes.ServiceErrorCodes.LATITUDE_AND_LONGITUDE_IS_MANDATORY;
import com.common.Utilities;
import com.google.gson.JsonSyntaxException;
import com.service.UserService;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PropertyController {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private static UserService objUserService = null;

    PropertyController() {
        try {
            if (objUserService == null) {
                objUserService = new UserService();
            }

        } catch (Exception e) {
            logger.error("Exception in PropertyController(),ex:" + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/showproperty", method = RequestMethod.GET)
    public Object showproperty(@RequestParam(value = "id", required = false) String id, HttpServletRequest httpreq) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
//        try {
//            System.out.println("id*********" + id);
//            int category = 1;
//            if (StringUtils.isNotBlank(id)) {
//                JSONObject objResponse = objUserService.getPropertie(id, id);
//                System.out.println("res*****" + objResponse);
//                model.addObject("propertie", objResponse.toString().getBytes("UTF-8"));
//                category = (objResponse.getInt("category"));
//            }
//
//            String objPropertyType = objUserService.getPropertyTypes(category, transId);
//            String objListingType = objUserService.getListingTypes(category, transId);
//            model.addObject("objPropertyType", objPropertyType);
//            model.addObject("objListingType", objListingType);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        model.setViewName("properties");
        return model;

    }

    @RequestMapping(value = "/showpropertyResult", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public @ResponseBody
    byte[] showpropertyResult(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "language", defaultValue = "en", required = false) String language, HttpServletRequest httpreq) throws UnsupportedEncodingException {
        String transId = UUID.randomUUID().toString();
        JSONObject model = new JSONObject();
        try {
            System.out.println("id*********" + id);
            int category = 1;
            if (StringUtils.isNotBlank(id)) {
                JSONObject objResponse = objUserService.getPropertie(id, id);
                System.out.println("res*****" + objResponse);
                model.put("propertie", objResponse.toString());
                category = (objResponse.getInt("category"));
            }

            String objPropertyType = objUserService.getPropertyTypes(category, transId, language);
            String objListingType = objUserService.getListingTypes(category, transId, language);
            model.put("objPropertyType", objPropertyType);
            model.put("objListingType", objListingType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model.toString().getBytes("UTF-8");

    }

    @RequestMapping(value = "/getPropertyTypesAdmin", method = RequestMethod.GET)
    public String getPropertyTypesAdmin(HttpSession httpSession,HttpServletRequest httpreq, @RequestParam(value = "nCat", required = false) int nCat, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String transId = UUID.randomUUID().toString();
        String objPropertyType = null;
        try {
            if(null!=httpSession.getAttribute("language")){
                language=(String) httpSession.getAttribute("language");
            }
            objPropertyType = objUserService.getPropertyTypes(nCat, transId, language);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objPropertyType;

    }

    @RequestMapping(value = "/getListingTypes", method = RequestMethod.GET)
    public byte[] getListingTypes(HttpSession httpSession,HttpServletRequest httpreq, @RequestParam(value = "nCat", required = false) int nCat, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String transId = UUID.randomUUID().toString();
        String objListingType = null;
        try {
            if(null!=httpSession.getAttribute("language")){
                language=(String) httpSession.getAttribute("language");
            }
            objListingType = objUserService.getListingTypes(nCat, transId, language);
            return objListingType.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/getstates", method = RequestMethod.GET)
    public String getStates(HttpServletRequest httpreq, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String transId = UUID.randomUUID().toString();
        String objStates = null;
        try {
            objStates = objUserService.getStates(transId, language);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objStates;

    }

    @RequestMapping(value = "/getcities", method = RequestMethod.GET)
    public String getCities(HttpServletRequest httpreq, @RequestParam(value = "state", required = false) int nState, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String transId = UUID.randomUUID().toString();
        String objCities = null;
        try {
            objCities = objUserService.getCity(nState, transId, language);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objCities;
    }

    @RequestMapping(value = "/activateproperty", method = RequestMethod.GET)
    public String activateProperty(@RequestParam(value = "id", required = false) int nPropertyId, HttpServletRequest httpreq, HttpSession httpSession) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        User userResponse = null;
        try {
            System.out.println("nPropertyId*********" + nPropertyId);
            if (nPropertyId > 0) {

                if (httpSession.getAttribute("useradmin") != null) {
                    userResponse = (User) httpSession.getAttribute("useradmin");
                    if (!userResponse.getUserType().equalsIgnoreCase("1")) {
                        return objUserService.activateProperty(transId, nPropertyId, userResponse);
                    }
                }

                return objUserService.activateProperty(transId, nPropertyId, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @RequestMapping(value = "/deactivateproperty", method = RequestMethod.GET)
    public String deActivateProperty(@RequestParam(value = "id", required = false) int nPropertyId, HttpServletRequest httpreq) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        try {
            System.out.println("nPropertyId*********" + nPropertyId);
            if (nPropertyId > 0) {
                return objUserService.deActivateProperty(nPropertyId, 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/deleteProperty", method = RequestMethod.GET)
    public String deleteProperty(@RequestParam(value = "id", required = false) int nPropertyId, HttpServletRequest httpreq) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        try {
            System.out.println("nPropertyId*********" + nPropertyId);
            if (nPropertyId > 0) {
                return objUserService.deActivateProperty(nPropertyId, 4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @RequestMapping(value = "/deassignProperty", method = RequestMethod.GET)
    public String deassignProperty(@RequestParam(value = "agentId", required = false) int agentId, @RequestParam(value = "propertyId", required = false) int propertyId, HttpServletRequest httpreq) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        try {
            System.out.println("nPropertyId*********" + propertyId);
            if (propertyId > 0) {
                return objUserService.deassignProperty(agentId, propertyId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public Object adminprofile(HttpServletRequest httpreq, HttpSession httpSession) {

        ModelAndView model = new ModelAndView();
        try {
            String strTid = UUID.randomUUID().toString();
            User requestBean = (User) httpSession.getAttribute("useradmin");

            //  String objResponse = objUserService.getAgentDetails(requestBean.getUser_id(), strTid);
//            model.addObject("agentdetails", requestBean);
            model.setViewName("profile");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;

    }

    @RequestMapping(value = "/createproperty", method = RequestMethod.POST)
    public String createproperty(@RequestBody String strJSON, HttpSession httpSession) {
        PropertyBean objProperty = null;
        String strResponse = null;
        String transId = UUID.randomUUID().toString();
        try {
            String agent_id = null;
            if (httpSession.getAttribute("useradmin") != null) {
                User userResponse = (User) httpSession.getAttribute("useradmin");
                if (!userResponse.getUserType().equalsIgnoreCase("3")) {
                    agent_id = userResponse.getAgentId();
                }

            }

            objProperty = Utilities.fromJson(strJSON, PropertyBean.class);
            System.out.println("strJSON:" + strJSON);
            strResponse = objUserService.addORUpdatePropertyDetails(objProperty, transId, agent_id);
            // ModelAndView model = new ModelAndView();
            JSONObject objResponse = objUserService.getPropertie(transId, objProperty.getId() + "");
            strResponse = objResponse.toString();
            // httpSession.setAttribute("propertie", objResponse.toString());
            // model.addObject("propertie", objResponse.toString());

        } catch (JsonSyntaxException je) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
        return strResponse;
    }

    @RequestMapping(value = "/homeworthdet", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/json"})
    public String createHomeWorth(@RequestParam(value = "address") String address,
            @RequestParam(value = "longitude", defaultValue = "") String longitude,
            @RequestParam(value = "latitude", defaultValue = "") String latitude,
            @RequestParam(value = "beds", defaultValue = "0") int beds,
            @RequestParam(value = "baths", defaultValue = "0") int baths,
            @RequestParam(value = "propertyType", defaultValue = "0") int nPropertyType,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "mobile", defaultValue = "") String mobile,
            @RequestParam(value = "sellInMonths", defaultValue = "0") int nSellInMonths,
            HttpSession httpSession) {
        String transId = UUID.randomUUID().toString();
        try {
            if (StringUtils.isBlank(address)) {
                return Utilities.prepareReponse(INVALID_ADDRESS.getCode(), INVALID_ADDRESS.DESC(), transId);
            }
            if (StringUtils.isBlank(email)) {
                return Utilities.prepareReponse(INVALID_EMAILID.getCode(), INVALID_EMAILID.DESC(), transId);
            }
            if (StringUtils.isBlank(mobile)) {
                return Utilities.prepareReponse(INVALID_MOBILE_NUMBER.getCode(), INVALID_MOBILE_NUMBER.DESC(), transId);
            }
            int nRes = objUserService.addHomeWorth(address, longitude, latitude, beds, baths, nPropertyType, nSellInMonths, mobile, email, name, transId);
            if (nRes == 1) {
                return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
            } else {
                return Utilities.prepareReponse(INSERT_FAILED.getCode(), INSERT_FAILED.DESC(), transId);
            }
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/property/getpropertyslist", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public byte[] getProperties(@RequestParam(value = "category", defaultValue = "0") int category,
            @RequestParam(value = "propertyId", defaultValue = "0") int nPropertyId,
            @RequestParam(value = "agentId", defaultValue = "0") int nAgentId,
            @RequestParam(value = "userId", defaultValue = "0") int nUserId,
            @RequestParam(value = "isSquareMeter", defaultValue = "0") int nIsSquareMeter,
            @RequestParam(value = "min", defaultValue = "0") int min,
            @RequestParam(value = "max", defaultValue = "20") int max,
            @RequestParam(value = "latitude", defaultValue = "0") String latitude,
            @RequestParam(value = "longitude", defaultValue = "0") String longitude,
            @RequestParam(value = "radius", defaultValue = "0") String radius,
            @RequestParam(value = "sortBy", defaultValue = " created_on ") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = " desc ") String orderBy,
            @RequestParam(value = "currency", defaultValue = "SAR") String currency) {
        String strTid = UUID.randomUUID().toString();
        try {
            String response = objUserService.getProperties(strTid, category, nPropertyId, nAgentId, currency, nUserId, nIsSquareMeter, min, max, latitude, longitude, radius, sortBy, orderBy);
            return response.getBytes("UTF-8");
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponseByte(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/propertyslist", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public @ResponseBody
    byte[] propertyslist(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex, HttpSession httpSession, @RequestParam(value = "agent_id", required = false) String agent_id) throws UnsupportedEncodingException {
        String strTid = UUID.randomUUID().toString();
        String strResult = null;
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            JSONObject json = new JSONObject();
            //String agent_id = null;
            if (httpSession.getAttribute("useradmin") != null) {
                User strResponse = (User) httpSession.getAttribute("useradmin");
                if (!strResponse.getUserType().equalsIgnoreCase("3")) {
                    agent_id = strResponse.getAgentId();
                }

            }
            if (agent_id != null && agent_id.equalsIgnoreCase("null")) {
                agent_id = "";
            }
            JSONArray obj = objUserService.getPropertiesList(strTid, fromIndex, endIndex, agent_id);
            json.put("total", objUserService.getPropertiesListCount(strTid, agent_id));
            json.put("page", page);
            json.put("records", obj.length());
            json.put("rows", obj);
            return json.toString().getBytes("UTF-8");
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid).getBytes("UTF-8");
        }
    }

    /**
     *
     * @param minPrice
     * @param maxPrice
     * @param propertyType
     * @param category 1-SALE , 2-RENT, 3-SOLD
     * @param beds
     * @param minsFeetRange
     * @param maxsFeetRange
     * @param baths
     * @param petsAllowed
     * @param keywords
     * @param lotSize
     * @param fromYear
     * @param toYear
     * @param daysInAqarabia
     * @param listingType
     * @param showOnly
     * @param mls
     * @param soldInMonths
     * @param nPropertyCategory
     * @param sortBy
     * @param orderBy
     * @param currency
     * @param nUserId
     * @param isSquareMeter
     * @param min
     * @param max
     * @param latitude
     * @param longitude
     * @param xParams
     * @param strRadius
     * @param radius
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/property/search", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] propertySearch(@RequestParam(value = "minPrice", defaultValue = "0") int minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "0") int maxPrice,
            @RequestParam(value = "propertyType", defaultValue = "") String propertyType,
            @RequestParam(value = "category", defaultValue = "0") int category,
            @RequestParam(value = "beds", defaultValue = "0") int beds,
            @RequestParam(value = "baths", defaultValue = "0") int baths,
            @RequestParam(value = "minsFeetRange", defaultValue = "0") int minsFeetRange,
            @RequestParam(value = "maxsFeetRange", defaultValue = "0") int maxsFeetRange,
            @RequestParam(value = "petsAllowed", defaultValue = "0") int petsAllowed,
            @RequestParam(value = "keywords", defaultValue = "") String keywords,
            @RequestParam(value = "lotSize", defaultValue = "0") int lotSize,
            @RequestParam(value = "fromYear", defaultValue = "") String fromYear,
            @RequestParam(value = "toYear", defaultValue = "") String toYear,
            @RequestParam(value = "daysInAqarabia", defaultValue = "0") int daysInAqarabia,
            @RequestParam(value = "listingType", defaultValue = "") String listingType,
            @RequestParam(value = "showOnly", defaultValue = "") String showOnly,
            @RequestParam(value = "mls", defaultValue = "") String mls,
            @RequestParam(value = "soldInMonths", defaultValue = "0") int soldInMonths,
            @RequestParam(value = "propertyCategory", defaultValue = "0") int nPropertyCategory,
            @RequestParam(value = "sortBy", defaultValue = " created_on ") String sortBy,
            @RequestParam(value = "orderBy", defaultValue = " desc ") String orderBy,
            @RequestParam(value = "currency", defaultValue = "SAR") String currency,
            @RequestParam(value = "userId", defaultValue = "0") int nUserId,
            @RequestParam(value = "isSquareMeter", defaultValue = "0") int isSquareMeter,
            @RequestParam(value = "min", defaultValue = "0") int min,
            @RequestParam(value = "max", defaultValue = "100") int max,
            @RequestParam(value = "latitude", defaultValue = "0") String latitude,
            @RequestParam(value = "longitude", defaultValue = "0") String longitude,
            @RequestParam(value = "xParams", defaultValue = "") String xParams,
            @RequestParam(value = "radius", defaultValue = "0") String strRadius,
            @RequestParam(value = "eminitieKeywords", defaultValue = "") String eminitieKeywords,
            HttpSession httpSession) {
        String transId = UUID.randomUUID().toString();
        try {

            if (nPropertyCategory < 0 || (category <= 0 && category > 3)) {
                logger.error("Invalid property category =>" + category);
                return Utilities.prepareReponseByte(INVALID_PROPERTY_CATEGORY.getCode(), INVALID_PROPERTY_CATEGORY.DESC(), transId);
            }

            return objUserService.propertySearch(minPrice, maxPrice, propertyType, category, beds, baths, minsFeetRange, maxsFeetRange, petsAllowed, keywords, lotSize, fromYear, toYear, daysInAqarabia, listingType, showOnly, mls, soldInMonths, sortBy, orderBy, nPropertyCategory, transId, currency, nUserId, isSquareMeter, min, max, latitude, longitude, strRadius, xParams, eminitieKeywords).getBytes("UTF-8");

        } catch (SQLException sqle) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/property/getpropertytypes", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getPropertyTypes(@RequestParam(value = "category", defaultValue = "1") int nCategory, @RequestParam(value = "language", defaultValue = "en", required = false) String language, HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getPropertyTypes(nCategory, strTid, language).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/property/getpropertyCategory", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getpropertyCategory(HttpSession httpSession, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getpropertyCategory(strTid, language).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/property/getpropertyexpertises", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getPropertyExpertise(HttpSession httpSession, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getPropertyExpertise(strTid, language).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/agent/getcertifications", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getAgentCertifications(HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getAgentCertifications(strTid).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/agent/getagenttypes", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getAgetntTypes(HttpSession httpSession, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getAgentTypes(strTid, language).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/agent/getagentspeciality", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getAgentSpeciality(HttpSession httpSession, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getAgentSpeciality(strTid, language).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/agent/getlanguages", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] getAgentLanguages(HttpSession httpSession, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String strTid = UUID.randomUUID().toString();
        try {
            return objUserService.getAgentLanguages(strTid, language).getBytes("UTF-8");
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/agent/search", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] agentSearch(@RequestParam(value = "minPrice", defaultValue = "0") int minPrice,
            @RequestParam(value = "maxPrice", defaultValue = "0") int maxPrice,
            @RequestParam(value = "location", defaultValue = "") String location,
            @RequestParam(value = "agentType", defaultValue = "0") int agentType,
            @RequestParam(value = "keywords", defaultValue = "") String keywords,
            @RequestParam(value = "propertyExpertise", defaultValue = "") String propertyExpertize,
            @RequestParam(value = "agentSpecialty", defaultValue = "") String agentSpecialty,
            @RequestParam(value = "languages", defaultValue = "") String languages,
            HttpSession httpSession) {

        String transId = UUID.randomUUID().toString();
        try {
            if (agentType <= 0) {
                logger.error("Invalid agentType =>" + agentType);
                return Utilities.prepareReponseByte(INVALID_AGENT_TYPE.getCode(), INVALID_AGENT_TYPE.DESC(), transId);
            }

            return objUserService.agentSearch(minPrice, maxPrice, location, agentType, keywords, propertyExpertize, agentSpecialty, languages, transId).getBytes("UTF-8");

        } catch (SQLException sqle) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/agent/agentdetails", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] agentSearch(@RequestParam(value = "agentId", defaultValue = "0") int nAgentId,
            @RequestParam(value = "propertyId", defaultValue = "0") int nPropertyId) {

        String transId = UUID.randomUUID().toString();
        try {
            return objUserService.agentList(transId, nAgentId, nPropertyId).getBytes("UTF-8");
        } catch (SQLException sqle) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/agentlist", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String agentlist(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex) {
        String strTid = UUID.randomUUID().toString();
        String strResult = null;
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            JSONObject json = new JSONObject();
            JSONArray obj = objUserService.adminAgentList(strTid, fromIndex, endIndex);
            json.put("total", objUserService.adminAgentListCountString(strTid));
            json.put("page", page);
            json.put("records", obj.length());
            json.put("rows", obj);
            return json.toString();
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/homewortlist", method = RequestMethod.GET)
    public Object homewortlist(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("homewortlist");
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/mortgagelist", method = RequestMethod.GET)
    public Object mortgagelist(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("mortgagelist");
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/requestproperty", method = RequestMethod.GET)
    public Object requestproperty(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            model.setViewName("requestproperty");
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/requestinfolist", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String requestinfolist(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex, HttpServletRequest request) {
        String strTid = UUID.randomUUID().toString();
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            HttpSession session = request.getSession();
            User userResponse = (User) session.getAttribute("useradmin");
            JSONObject json = new JSONObject();
            JSONArray obj = objUserService.getRequestInfoList(strTid, fromIndex, endIndex, userResponse.getAgentId());
            json.put("total", objUserService.getrequestinfolistCount(strTid, userResponse.getAgentId()));
            json.put("page", page);
            json.put("records", obj.length());
            json.put("rows", obj);
            return json.toString();
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/agentlist", method = RequestMethod.GET)
    public Object agentlist(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("agentlist");
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/agentdetails", method = RequestMethod.GET)
    public Object agentdetails(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null || StringUtils.isNotBlank(id)) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("agentdetails");
                if (StringUtils.isNotBlank(id)) {
                    String response = objUserService.getAgentDetailsById(Integer.valueOf(id), strTid);
                    model.addObject("agentdetails", response);
                }

            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/mortgagedetails", method = RequestMethod.GET)
    public Object mortgagedetails(HttpServletRequest request) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("mortagesettinglist");
                // String response = objUserService.getMortgageDetails(strTid);
                // model.addObject("mortgagedetails", response);
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }
        return model;
    }

    @RequestMapping(value = "/currenysettingdet", method = RequestMethod.GET)
    public Object currenysettingdet(HttpServletRequest request) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("currencysettings");
                // String response = objUserService.getMortgageDetails(strTid);
                // model.addObject("mortgagedetails", response);
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }
        return model;
    }

    @RequestMapping(value = "/mortgagesettings", method = RequestMethod.GET)
    public Object mortgagesettings(HttpServletRequest request, @RequestParam(value = "id", required = false) String id) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("mortgagesettings");
                String response = objUserService.getMortgageDetails(strTid, id);
                model.addObject("mortgagedetails", response);
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }
        return model;
    }

    @RequestMapping(value = "/profiledetails", method = RequestMethod.GET)
    public Object profiledetails(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null || StringUtils.isNotBlank(id)) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("agentdetails");
                if (StringUtils.isNotBlank(id)) {
                    String response = objUserService.getAgentProfileDetailsById(Integer.valueOf(id), strTid);
                    model.addObject("agentdetails", response);
                }

            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/userprofile", method = RequestMethod.GET)
    public Object profiledetailsagent(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null || StringUtils.isNotBlank(id)) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("agentdetails");
                if (StringUtils.isNotBlank(id)) {
                    String response = objUserService.getAgentProfileDetailsByAgentId(Integer.valueOf(id), strTid);
                    model.addObject("agentdetails", response);
                    User user = objUserService.adminLoginDetailsBYAgentUI(id, strTid);
                    session.setAttribute("useradmin", user);
                }

            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/property/getpropertyscategories", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public byte[] getPropertyCategories(@RequestParam(value = "category", defaultValue = "0") int category, @RequestParam(value = "language", defaultValue = "en", required = false) String language) {
        String strTid = UUID.randomUUID().toString();
        try {
            String response = objUserService.getPropertyCategories(strTid, language);
            return response.getBytes("UTF-8");
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponseByte(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/homeworth", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String homeworthlist(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex,
            @RequestParam("searchstr") String searchstr) {
        String strTid = UUID.randomUUID().toString();
        String strResult = null;
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            JSONObject json = new JSONObject();
            JSONArray obj = objUserService.getHomeWorthlistList(strTid, fromIndex, endIndex, searchstr);
            json.put("total", objUserService.getHomeWorthlistListCount(strTid, searchstr));
            json.put("page", page);
            json.put("records", obj.length());
            json.put("rows", obj);
            return json.toString();
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/mortgages", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String mortgages(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex) {
        String strTid = UUID.randomUUID().toString();
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            JSONObject json = new JSONObject();
            JSONArray obj = objUserService.getMortgageList(strTid, fromIndex, endIndex);
            json.put("total", objUserService.getMortgageListCount(strTid));
            json.put("page", page);
            json.put("records", obj.length());
            json.put("rows", obj);
            return json.toString();
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/assignproperty", method = RequestMethod.GET)
    public String assignproperty(@RequestParam(value = "id", required = false) String nPropertyId, @RequestParam(value = "agent_id", required = false) String nAgentId, HttpServletRequest httpreq) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        try {
            System.out.println("nPropertyId*********" + nPropertyId);

            return objUserService.addPropertyToAgent(nPropertyId, nAgentId, transId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @RequestMapping(value = "/deleteimage", method = RequestMethod.GET)
    public String deleteimage(@RequestParam(value = "id", required = false) String nImageId, HttpServletRequest httpreq) {
        String transId = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        try {
            System.out.println("nImageId*********" + nImageId);

            return objUserService.deleteImage(nImageId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    @RequestMapping(value = "/agent/agentlist", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] agentlistAdmin() {

        String transId = UUID.randomUUID().toString();
        try {
            return objUserService.getAgentlist(transId).toString().getBytes("UTF-8");
        } catch (SQLException sqle) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/property/neighborhoodinfo", method = RequestMethod.GET, produces = {"application/json"})
    public byte[] propertyNeighborhoodInfo(
            @RequestParam(value = "latitude", defaultValue = "") String latitude,
            @RequestParam(value = "longitude", defaultValue = "") String longitude,
            @RequestParam(value = "keywords", defaultValue = "") String keywords,
            @RequestParam(value = "radius", defaultValue = "0") String nRadius) {
        String transId = UUID.randomUUID().toString();
        try {
            if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude)) {
                return Utilities.prepareReponseByte(LATITUDE_AND_LONGITUDE_IS_MANDATORY.getCode(), LATITUDE_AND_LONGITUDE_IS_MANDATORY.DESC(), transId);
            }
            if (StringUtils.isBlank(nRadius) || "0".equalsIgnoreCase(nRadius)) {
                return Utilities.prepareReponseByte(INVALID_RADIUS.getCode(), INVALID_RADIUS.DESC(), transId);
            }
            return objUserService.getNeighborhoodInfo(latitude, longitude, nRadius, keywords, transId).getBytes("UTF-8");
        } catch (Exception e) {
            return Utilities.prepareReponseByte(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/property/requestinfo", method = RequestMethod.GET, produces = {"application/json"})
    public String propertyRequestInfo(@RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "emailId", defaultValue = "") String emailId,
            @RequestParam(value = "mobile", defaultValue = "") String mobile,
            @RequestParam(value = "agentIds", defaultValue = "") String agentIds,
            @RequestParam(value = "propertyId", defaultValue = "0") int nPropertyId,
            HttpSession httpSession) {

        String transId = UUID.randomUUID().toString();
        try {
            if (StringUtils.isBlank(emailId)) {
                return Utilities.prepareReponse(INVALID_EMAILID.getCode(), INVALID_EMAILID.DESC(), transId);
            }
            if (StringUtils.isBlank(mobile)) {
                return Utilities.prepareReponse(INVALID_MOBILE_NUMBER.getCode(), INVALID_MOBILE_NUMBER.DESC(), transId);
            }
            if (StringUtils.isBlank(agentIds)) {
                return Utilities.prepareReponse(INVALID_AGENT_ID.getCode(), INVALID_AGENT_ID.DESC(), transId);
            }
            if (nPropertyId <= 0) {
                return Utilities.prepareReponse(INVALID_PROPERTY_ID.getCode(), INVALID_PROPERTY_ID.DESC(), transId);
            }
            int nRes = objUserService.propertyRequestInfo(userId, emailId, mobile, agentIds, nPropertyId, transId);
            if (nRes == 0) {
                return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
            }
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);

        } catch (SQLException sqle) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/currenysettingedit", method = RequestMethod.GET)
    public Object currenysetting(HttpServletRequest request, @RequestParam(value = "id", required = false) String id) throws Exception {
        String strTid = UUID.randomUUID().toString();
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("currencyedit");
                String response = objUserService.getCurrencyDetails(strTid, id);
                model.addObject("currencydet", response);
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }
        return model;
    }
}
