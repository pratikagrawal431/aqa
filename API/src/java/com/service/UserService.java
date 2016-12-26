/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.beans.LoginRequestBean;
import com.beans.MortgageSettings;
import com.beans.PropertyBean;
import com.beans.UserPasswordBean;
import com.common.ResponseCodes;
import static com.common.ResponseCodes.ServiceErrorCodes.GENERIC_ERROR;
import static com.common.ResponseCodes.ServiceErrorCodes.SUCCESS;
import com.common.Utilities;
import com.controller.User;
import com.dao.UserDAO;
import com.google.gson.internal.bind.TypeAdapters;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author chhavikumar.b
 */
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private static UserDAO objUserDAO = null;

    public UserService() {
        try {
            if (objUserDAO == null) {
                objUserDAO = new UserDAO();
            }

        } catch (Exception e) {
            logger.error("Exception in UserService(),ex:" + e.getMessage(), e);
        }
    }

    public String addUserDetails(User user, String strTid) {
        int isUpdated = 0;
        int nUserID = -1;
        ResponseCodes.ServiceErrorCodes errorCode = null;
        try {

            isUpdated = objUserDAO.addUserDetails(user);
            if (isUpdated > 0) {
                return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTid);
            } else {
                return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
            }

        } catch (SQLException sqle) {
            if (sqle.getMessage().contains("idx_userid_uq")) {
                errorCode = ResponseCodes.ServiceErrorCodes.USERID_ALREADY_EXISTS;
            } else if (sqle.getMessage().contains("idx_loginid_uq")) {
                errorCode = ResponseCodes.ServiceErrorCodes.USERID_ALREADY_EXISTS;
            }
            try {
                nUserID = getUserId(user.getUser_id(), strTid);
            } catch (Exception e) {
            }
            logger.error(" Got SQLException " + Utilities.getStackTrace(sqle));
            return Utilities.prepareReponsetoken(errorCode.getCode() + "", errorCode.DESC(), strTid, nUserID);

        } catch (Exception e) {
            logger.error("Exception in addUserDetails(),ex:" + e.getMessage(), e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    public String signIn(LoginRequestBean reqBean, String strTid) {
        int isUpdated = 0;
        try {
            return objUserDAO.loginDetails(reqBean, strTid);
        } catch (SQLException sqle) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        } catch (Exception e) {
            logger.error("Exception in addUserDetails(),ex:" + e.getMessage(), e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    public int agentUpdate(User user, String strTid) {
        try {
            return objUserDAO.updateAgent(user);
        } catch (Exception e) {
            logger.error("Exception in addUserDetails(),ex:" + e.getMessage(), e);
            return -1;
        }
    }
    
    public int mortgageSettings(MortgageSettings mortgageSettigs,String id) {
        try {
            return objUserDAO.mortgageSettings(mortgageSettigs,id);
        } catch (Exception e) {
            logger.error("Exception in addUserDetails(),ex:" + e.getMessage(), e);
            return -1;
        }
    }

    public User adminLoginDetails(LoginRequestBean reqBean, String strTid) {
        int isUpdated = 0;
        User user = new User();
        try {
            user = objUserDAO.adminLoginDetails(reqBean, strTid);
        } catch (SQLException sqle) {
            user.setErrorMessage(Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid));
        } catch (Exception e) {
            logger.error("Exception in addUserDetails(),ex:" + e.getMessage(), e);
            user.setErrorMessage(Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid));
        }
        return user;
    }

    public User adminLoginDetailsBYAgentUI(String nID, String strTid) {
        int isUpdated = 0;
        User user = new User();
        try {
            user = objUserDAO.adminLoginDetailsBYAgentUI(nID, strTid);
        } catch (SQLException sqle) {
            user.setErrorMessage(Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid));
        } catch (Exception e) {
            logger.error("Exception in adminLoginDetailsUI(),ex:" + e.getMessage(), e);
            user.setErrorMessage(Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid));
        }
        return user;
    }

    public User adminLoginDetailsUI(LoginRequestBean reqBean, String strTid) {
        int isUpdated = 0;
        User user = new User();
        try {
            user = objUserDAO.adminLoginDetailsUI(reqBean, strTid);
        } catch (SQLException sqle) {
            user.setErrorMessage(Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid));
        } catch (Exception e) {
            logger.error("Exception in adminLoginDetailsUI(),ex:" + e.getMessage(), e);
            user.setErrorMessage(Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid));
        }
        return user;
    }

    public String passwordService(UserPasswordBean userPasswordBean, String transId) throws SQLException, Exception {
        return objUserDAO.passwordService(userPasswordBean, transId);
    }

    public String saveProperty(int nUserId, int nPropertyId, String transId, int nStatus) throws SQLException, Exception {
        return objUserDAO.saveProperty(nUserId, nPropertyId, transId, nStatus);
    }

    public int saveMortgage(String user_id, String phone, String type, String mortagage) {
        return objUserDAO.saveMortgage(user_id, phone, type, mortagage);
    }

    public String getSavedPropertys(int nUserId, String transId, String currency) throws SQLException, Exception {
        return objUserDAO.getSavedPropertys(nUserId, transId, currency);
    }

    public String getSavedSearches(int nUserId, String transId) throws SQLException, Exception {
        return objUserDAO.getSavedSearches(nUserId, transId);
    }

    public String savePropertySearch(int nUserId, String name, String searchQuery, String transId) throws SQLException, Exception {
        return objUserDAO.savePropertySearch(nUserId, name, searchQuery, transId);
    }

    public int getUserId(String userId, String transId) throws SQLException, Exception {
        return objUserDAO.getUserID(userId, transId);
    }

    public String updateUser(User user, String transId) throws SQLException, Exception {
        return objUserDAO.updateUserDetails(user, transId);
    }

    public String addRecommondation(int nAgentId, String transId) throws SQLException, Exception {
        return objUserDAO.addRecommondation(nAgentId, transId);
    }

    public String addReviewOnAgent(int nAgentId, String comment, int nRating, String transId, String userId) throws SQLException, Exception {
        return objUserDAO.addReviewOnAgent(nAgentId, comment, nRating, transId, userId);
    }

    public String agentReviews(int nAgentId, int nLimit, String transId) throws SQLException, Exception {
        return objUserDAO.agentTotalReviews(nAgentId, nLimit, transId);
    }

    public String getUserDetails(String userId, String strTid) throws SQLException, Exception {
        return objUserDAO.getUserDetails(userId, strTid);
    }

    public int addORUpdateAgentDetails(String profile_picture, String name, String email, String phone, String city, String state, String password, String id) throws Exception {
        return objUserDAO.addORUpdateAgentDetails(profile_picture, name, email, phone, city, state, password, id);
    }

    public String getAgentDetails(String userId, String strTid) throws SQLException, Exception {
        return objUserDAO.getAgentDetails(userId, strTid);
    }

    public String getProperties(String transId, int category, int nPropertyId, int nAgentId, String currency, int nUserId, int nIsSquareMeter, int min, int max, String latitude, String longitude, String strRadius) throws SQLException, Exception {
        if (category > 0 || nPropertyId > 0) {
            return objUserDAO.getProperties(transId, category, nPropertyId, currency, nUserId, nIsSquareMeter, min, max, latitude, longitude, strRadius);
        } else if (nAgentId > 0) {
            return objUserDAO.getPropertiesByAgentId(nAgentId, transId, currency, nUserId, nIsSquareMeter, min, max, latitude, longitude, strRadius);
        } else {
            return objUserDAO.getProperties(transId, currency, nUserId, nIsSquareMeter, latitude, longitude, strRadius);
        }
    }

    public String getPropertyCategories(String transId) throws SQLException, Exception {
        return objUserDAO.getpropertyCategory(transId);
    }

    public JSONArray getPropertiesList(String transId, int fromIndex, int endIndex, String agent_id) throws SQLException, Exception {
        return objUserDAO.getPropertiesList(transId, fromIndex, endIndex, agent_id);
    }

    public int getPropertiesListCount(String transId, String agent_id) throws SQLException, Exception {
        return objUserDAO.getPropertiesListCount(transId, agent_id);
    }

    public JSONArray getHomeWorthlistList(String transId, int fromIndex, int endIndex) throws SQLException, Exception {
        return objUserDAO.getHomeWorthlistList(transId, fromIndex, endIndex);
    }
    public int mortagesettinglistCount(String transId) throws SQLException, Exception {
        return objUserDAO.mortagesettinglistCount(transId);
    }

    public JSONArray mortagesettinglist(String transId, int fromIndex, int endIndex) throws SQLException, Exception {
        return objUserDAO.mortagesettinglist(transId, fromIndex, endIndex);
    }
    
    public int getHomeWorthlistListCount(String transId) throws SQLException, Exception {
        return objUserDAO.getHomeWorthlistListCount(transId);
    }
    public JSONArray getMortgageList(String transId, int fromIndex, int endIndex) throws SQLException, Exception {
        return objUserDAO.getMortgageList(transId, fromIndex, endIndex);
    }

    public int getMortgageListCount(String transId) throws SQLException, Exception {
        return objUserDAO.getMortgagelistCount(transId);
    }

    public JSONArray adminAgentList(String transId, int fromIndex, int endIndex) throws SQLException, Exception {
        return objUserDAO.adminAgentList(transId, fromIndex, endIndex);
    }

    public int adminAgentListCountString(String transId) throws SQLException, Exception {
        return objUserDAO.adminAgentListCountString(transId);
    }

    public JSONObject getPropertie(String strTid, String id) throws SQLException, Exception {
        return objUserDAO.getPropertie(strTid, id);
    }

    public String getAgentDetailsById(int id, String strTid) throws SQLException, Exception {
        return objUserDAO.getAgentDetailsById(id, strTid);
    }
    
     public String getMortgageDetails(String strTid,String id) throws SQLException, Exception {
        return objUserDAO.getMortgageDetails( strTid,id);
    }

    public String getAgentProfileDetailsByAgentId(int id, String strTid) throws SQLException, Exception {
        return objUserDAO.getAgentProfileDetailsByAgentId(id, strTid);
    }

    public String getAgentProfileDetailsById(int id, String strTid) throws SQLException, Exception {
        return objUserDAO.getAgentProfileDetailsById(id, strTid);
    }

   public String activateProperty(String strTid, int nPropertyId,User user) throws SQLException, Exception {
        return objUserDAO.activateProperty(strTid, nPropertyId,user);
    }

    public String deActivateProperty(int nPropertyId, int nStatus) throws SQLException, Exception {
        return objUserDAO.deActivateProperty(nPropertyId, nStatus);
    }

    public String addORUpdatePropertyDetails(PropertyBean property, String strTid, String agent_id) {
        int isUpdated = 0;
        ResponseCodes.ServiceErrorCodes errorCode = null;
        try {

            isUpdated = objUserDAO.addORUpdatePropertyDetails(property, agent_id);
            if (isUpdated > 0) {
                return Utilities.prepareIDReponse(SUCCESS.getCode(), isUpdated, strTid);
            } else {
                return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
            }

        } catch (Exception e) {
            logger.error("Exception in addUserDetails(),ex:" + e.getMessage(), e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }

    }

    public String propertySearch(int minPrice, int maxPrice, String propertyType, int propertyCategory, int beds, int baths, int minsFeetRange, int maxsFeetRange, int petsAllowed, String keywords, int lotSize, String fromYear, String toYear, int daysInAqarabia, String listingType,
            String showOnly, String mls, int soldInMonths, String sortBy, String orderBy, int nPropertyCategory, String transId, String currency, int nUserId, int nIsSquareMeter, int min, int max, String latitude, String longitude, String strRadius, String xParams, String eminitieKeywords) throws SQLException, Exception {
        return objUserDAO.propertySearch(minPrice, maxPrice, propertyType, propertyCategory, beds, baths, minsFeetRange, maxsFeetRange, petsAllowed, keywords, lotSize, fromYear, toYear, daysInAqarabia, listingType, showOnly, mls, soldInMonths, sortBy, orderBy, nPropertyCategory, transId, currency, nUserId, nIsSquareMeter, min, max, latitude, longitude, strRadius, xParams, eminitieKeywords);
    }

    public String getPropertyTypes(int nCategory, String transId) throws SQLException, Exception {
        return objUserDAO.getPropertyTypes(nCategory, transId);
    }
    public String getListingTypes(int nCategory, String transId) throws SQLException, Exception {
        return objUserDAO.getListingTypes(nCategory, transId);
    }

    public String getPropertyTypesAdmin(String transId) throws SQLException, Exception {
        return objUserDAO.getPropertyTypesAdmin(transId);
    }

    public String getpropertyCategory(String transId) throws SQLException, Exception {
        return objUserDAO.getpropertyCategory(transId);
    }

    public String getPropertyExpertise(String transId) throws SQLException, Exception {
        return objUserDAO.getPropertyExpertise(transId);
    }

    public String getAgentCertifications(String transId) throws SQLException, Exception {
        return objUserDAO.getAgentCertifications(transId);
    }

    public String getAgentTypes(String transId) throws SQLException, Exception {
        return objUserDAO.getAgentTypes(transId);
    }

    public String getAgentSpeciality(String transId) throws SQLException, Exception {
        return objUserDAO.getAgentSpeciality(transId);
    }

    public String getAgentLanguages(String transId) throws SQLException, Exception {
        return objUserDAO.getAgentLanguages(transId);
    }

    public String agentSearch(int minPrice, int maxPrice, String location, int agentType, String keywords, String propertyExpertise, String agentSpecialty, String languages, String transId) throws SQLException, Exception {
        return objUserDAO.agentSearch(minPrice, maxPrice, location, agentType, keywords, propertyExpertise, agentSpecialty, languages, transId);
    }
    public int propertyRequestInfo(String userId,String emailId,String mobile,String agentIds, String transId) throws SQLException, Exception {
        return objUserDAO.propertyRequestInfo(userId, emailId, mobile, agentIds, transId);
    }

    public String agentList(String transId, int nAgentId, int nPropertyId) throws SQLException, Exception {
        return objUserDAO.agentList(transId, nAgentId, nPropertyId);
    }

    public JSONArray getAgentlist(String strTid) throws SQLException, Exception {
        return objUserDAO.getAgentlist(strTid);
    }

    public String getNeighborhoodInfo(String latitude, String longtitude, String nRadius, String keywords, String strTid) throws Exception {
        return objUserDAO.getNeighborhoodInfo(latitude, longtitude, nRadius, keywords, strTid);
    }

    public int addHomeWorth(String address, String longitude, String latitude, int nBeds, int nBaths, int nPropertyType, int nSellInMonths, String mobile, String email, String name, String transId) throws SQLException, Exception {
        return objUserDAO.addHomeWorth(address, longitude, latitude, nBeds, nBaths, nPropertyType, nSellInMonths, mobile, email, name, transId);
    }

    public String deleteImage(String imageID) {
        int isUpdated = 0;
        int nUserID = -1;
        ResponseCodes.ServiceErrorCodes errorCode = null;
        String strTid = UUID.randomUUID().toString();
        try {

            isUpdated = objUserDAO.deleteImage(imageID);
            if (isUpdated > 0) {
                return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTid);
            } else {
                return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
            }

        } catch (Exception e) {
            logger.error("Exception in addPropertyToAgent(),ex:" + e.getMessage(), e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    public String deassignProperty(int agentId, int propertyId) {
        int isUpdated = 0;
        int nUserID = -1;
        ResponseCodes.ServiceErrorCodes errorCode = null;
        String strTid = UUID.randomUUID().toString();
        try {

            isUpdated = objUserDAO.deassignProperty(agentId, propertyId);
            if (isUpdated > 0) {
                return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTid);
            } else {
                return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
            }

        } catch (Exception e) {
            logger.error("Exception in deassignProperty(),ex:" + e.getMessage(), e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    public String addPropertyToAgent(String property_id, String agent_id, String strTid) {
        int isUpdated = 0;
        int nUserID = -1;
        ResponseCodes.ServiceErrorCodes errorCode = null;
        try {

            isUpdated = objUserDAO.addPropertyToAgent(property_id, agent_id);
            if (isUpdated > 0) {
                return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTid);
            } else {
                return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
            }

        } catch (SQLException sqle) {
            if (sqle.getMessage().contains("Duplicate entry")) {
                errorCode = ResponseCodes.ServiceErrorCodes.AGENT_PROPERTY_ALREADY_EXISTS;
            } else {
                errorCode = ResponseCodes.ServiceErrorCodes.GENERIC_ERROR;
            }
            logger.error(" Got SQLException " + Utilities.getStackTrace(sqle));
            return Utilities.prepareReponsetoken(errorCode.getCode() + "", errorCode.DESC(), strTid, nUserID);

        } catch (Exception e) {
            logger.error("Exception in addPropertyToAgent(),ex:" + e.getMessage(), e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

}
