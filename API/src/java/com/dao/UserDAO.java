/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.beans.AgentInfo;
import com.beans.CurrencyBean;
import com.beans.LoginRequestBean;
import com.beans.MortgageSettings;
import com.beans.PropertyBean;
import com.beans.PropertyContactDetails;
import com.beans.UserPasswordBean;
import com.common.AESAlgo;
import com.common.ConfigUtil;
import com.common.Constants;
import com.common.DBConnection;
import com.common.HttpDispatchClient;
import com.common.Mailer;
import static com.common.ResponseCodes.ServiceErrorCodes.ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY;
import static com.common.ResponseCodes.ServiceErrorCodes.CONTACT_DEATILS_MANDATORY_TO_ACTIVATE_PROPERTY;
import static com.common.ResponseCodes.ServiceErrorCodes.EMAIL_IS_MANDATORY_TO_ACTIVATE_PROPERTY;
import static com.common.ResponseCodes.ServiceErrorCodes.FAILED_TO_SAVE_SEARCH;
import static com.common.ResponseCodes.ServiceErrorCodes.GENERIC_ERROR;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_AGENT_TYPE;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_FORGOT_PASSWORD_TOKEN;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_PROPERTY_CATEGORY;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_USER_DETAILS;
import static com.common.ResponseCodes.ServiceErrorCodes.MOBILE_IS_MANDATORY_TO_ACTIVATE_PROPERTY;
import static com.common.ResponseCodes.ServiceErrorCodes.OLDPASSWORD_IS_WRONG;
import static com.common.ResponseCodes.ServiceErrorCodes.PASSWORD_UPDATE_FAILED;
import static com.common.ResponseCodes.ServiceErrorCodes.PRICE_IS_MANDATORY_TO_ACTIVATE_PROPERTY;
import static com.common.ResponseCodes.ServiceErrorCodes.PROPERTY_HOLDER_NAME_IS_MANDATORY_TO_ACTIVATE_PROPERTY;
import static com.common.ResponseCodes.ServiceErrorCodes.SUCCESS;
import static com.common.ResponseCodes.ServiceErrorCodes.USERID_NOT_EXISTS;
import static com.common.ResponseCodes.ServiceErrorCodes.USER_INACTIVE;
import static com.common.ResponseCodes.ServiceErrorCodes.USER_PROPERTY_SAVE_FAILED;
import static com.common.ResponseCodes.ServiceErrorCodes.WRONG_PASSWORD;
import com.common.Utilities;
import com.controller.User;
import com.ning.http.client.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author chhavikumar.b
 */
public class UserDAO {

    DBConnection dbconnection = null;
    Mailer objMail = null;
    String mailFrom = "";
    String mailCC = "";
    String mailBCC = "";
    String mailSubjectForForgotPwd = "";
    String mailSubjectRequestInfo = "";
    String signupMailSubject = "";
    String forgotPWDMailContent = "";
    String requestInfoMailContent = "";
    String url = "";
    public static final double oneSquareMeter = Double.valueOf(ConfigUtil.getProperty("one.square.meter", "0.092903"));
    private final Lock propertylock = new ReentrantLock();
    private final Lock propertylock1 = new ReentrantLock();
    private final Lock propertylock2 = new ReentrantLock();
    private final Lock propertylock3 = new ReentrantLock();
    private final Lock propertylock4 = new ReentrantLock();

    public UserDAO() {
        dbconnection = DBConnection.getInstance();
        objMail = new Mailer(ConfigUtil.getProperties());
        mailFrom = ConfigUtil.getProperty("smtp.mail.from", "info@aqarabia.net");
        mailCC = ConfigUtil.getProperty("smtp.mail.cc", "chhavibahekar@gmail.com");
        mailBCC = ConfigUtil.getProperty("smtp.mail.bcc", "gvmpavankumar14@gmail.com");
        mailSubjectForForgotPwd = ConfigUtil.getProperty("smtp.mail.subject", "Your new Aqarabia password");
        mailSubjectRequestInfo = ConfigUtil.getProperty("smtp.mail.subject.requestinfo", "Request Info For a Property");
        signupMailSubject = ConfigUtil.getProperty("signup.mail.subject", "Welcome to Aquarbia");
        forgotPWDMailContent = ConfigUtil.getProperty("mail.forgotpwd.content", "forgot password content");
        requestInfoMailContent = ConfigUtil.getProperty("mail.requestinfo.content", "forgot password content");
        url = ConfigUtil.getProperty("url", "http://54.187.18.127:8080/API");
        HttpDispatchClient.getInstance();
    }
    static Log logger = LogFactory.getLog(UserDAO.class);

    public int addUserDetails(User user) throws SQLException, Exception {
        String insertQuery = ConfigUtil.getProperty("store.user.data.query", "INSERT INTO `aqarabia`.`users`(`user_id`,`salutation`,`loginid`,`password`,`logintype`,`firstname`,`middlename`,`lastname`,`address1`,`address2`,`city`,`state`,`country`,`zipcode`,`phone`,`email`,`status`,`createdby`,`updated_on`,`user_type`,`profile_picture`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        String insertagent_detailsQuery = ConfigUtil.getProperty("store.agent_details.data.query", "INSERT INTO `aqarabia`.`agent_details`(`agent_type`,`company`,`user_id`,`agent_specialty`,`languages`,`property_expertise`,`certifications`,`keywords`,`description`) VALUES (?,?,?,?,?,?,?,?,?)");
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, user.getUser_id());
                pstmt.setString(2, user.getSalutation());
                pstmt.setString(3, user.getLoginid());
                if (StringUtils.isNotBlank(user.getPassword())) {
                    pstmt.setString(4, AESAlgo.encrypt(user.getPassword()));
                } else {
                    pstmt.setString(4, user.getPassword());
                }
                pstmt.setString(5, user.getLogintype());
                pstmt.setString(6, user.getFirstname());
                pstmt.setString(7, user.getMiddlename());
                pstmt.setString(8, user.getLastname());
                pstmt.setString(9, user.getAddress1());
                pstmt.setString(10, user.getAddress2());
                pstmt.setString(11, user.getCity());
                pstmt.setString(12, user.getState());
                pstmt.setString(13, user.getCountry());
                pstmt.setString(14, user.getZipcode());
                pstmt.setString(15, user.getPhone());
                pstmt.setString(16, user.getEmail());
                pstmt.setInt(17, user.getStatus());
                pstmt.setString(18, user.getCreatedby());
                pstmt.setString(19, user.getUpdated_on());
                if (StringUtils.isNotBlank(user.getUserType())) {
                    pstmt.setString(20, user.getUserType());
                } else {
                    pstmt.setString(20, "1");
                }
                pstmt.setString(21, user.getProfilePicture());
                status = pstmt.executeUpdate();
                try {
                    String singupContent = ConfigUtil.getProperty("signup.mail.content", "Dear User, \n\n Thanks for register with Aquarbia....\n\n\n Thanks & Regards,\nAquarbia Support ");
                    singupContent = singupContent.replace("hostURL", url);
                    int nRes = objMail.sendHtmlMail(mailFrom, user.getUser_id(), mailCC, mailBCC, signupMailSubject, singupContent, new ArrayList());

                } catch (Exception e) {
                    logger.error("Sending mail failed" + Utilities.getStackTrace(e));
                }
                if (StringUtils.isNotBlank(user.getAgentType()) && status > 0) {
                    rs = pstmt.getGeneratedKeys();
                    int agentId = 0;
                    if (rs.next()) {
                        agentId = rs.getInt(1);
                    }
                    pstmt = objConn.prepareStatement(insertagent_detailsQuery);
                    pstmt.setString(1, user.getAgentType());
                    pstmt.setString(2, user.getCompanyname());
                    pstmt.setInt(3, agentId);
                    if (user.getAgentSpecialty() != 0) {
                        pstmt.setInt(4, user.getAgentSpecialty());
                    } else {
                        pstmt.setInt(4, 1);
                    }
                    pstmt.setInt(5, user.getLanguage());
                    if (user.getPropertyExpertise() != 0) {
                        pstmt.setInt(6, user.getPropertyExpertise());
                    } else {
                        pstmt.setInt(6, 1);
                    }
                    pstmt.setString(7, user.getCertification());
                    pstmt.setString(8, user.getKeywords());
                    pstmt.setString(9, user.getAbout());
                    status = pstmt.executeUpdate();
                }
            } else {
                logger.error("addUserDetails(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public int updateAgent(User user) throws SQLException, Exception {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {
                String updateAgentDetailsQuery = "update `aqarabia`.agent_details set updated_on=now() ";
                if (StringUtils.isNotBlank(user.getAgentType())) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,agent_type=" + user.getAgentType() + " ";
                }
                if (user.getAgentSpecialty() > 0) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,agent_specialty=" + user.getAgentSpecialty();
                }
                if (user.getLanguage() > 0) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,languages=" + user.getLanguage();
                }
                if (user.getPropertyExpertise() > 0) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,property_expertise=" + user.getPropertyExpertise();
                }
                if (StringUtils.isNotBlank(user.getCertification())) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,certifications=" + user.getCertification();
                }
                if (StringUtils.isNotBlank(user.getKeywords())) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,keywords='" + user.getKeywords() + "' ";
                }
                if (StringUtils.isNotBlank(user.getAbout())) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,description='" + user.getAbout() + "' ";
                }
                if (StringUtils.isNotBlank(user.getCompanyname())) {
                    updateAgentDetailsQuery = updateAgentDetailsQuery + " ,company='" + user.getCompanyname() + "' ";
                }

                updateAgentDetailsQuery = updateAgentDetailsQuery + " where id=" + user.getId();
                pstmt = objConn.prepareStatement(updateAgentDetailsQuery);
                status = pstmt.executeUpdate();

                // String updating User table
                if (status == 1) {
                    pstmt = objConn.prepareStatement("select user_id from agent_details where id=" + user.getId());
                    rs = pstmt.executeQuery();
                    int nUserId = 0;
                    if (rs.next()) {
                        nUserId = rs.getInt("user_id");
                    }

                    if (nUserId > 0) {
                        String updateUsersDetailsQuery = "update `aqarabia`.users set updated_on=now() ";

                        if (StringUtils.isNotBlank(user.getFirstname())) {
                            updateUsersDetailsQuery = updateUsersDetailsQuery + " ,firstname='" + user.getFirstname() + "' ";
                        }
                        if (StringUtils.isNotBlank(user.getPhone())) {
                            updateUsersDetailsQuery = updateUsersDetailsQuery + " ,phone='" + user.getPhone() + "' ";
                        }
                        if (StringUtils.isNotBlank(user.getCity())) {
                            updateUsersDetailsQuery = updateUsersDetailsQuery + " ,city='" + user.getCity() + "' ";
                        }
                        if (StringUtils.isNotBlank(user.getState())) {
                            updateUsersDetailsQuery = updateUsersDetailsQuery + " ,state='" + user.getState() + "' ";
                        }
                        if (StringUtils.isNotBlank(user.getProfilePicture())) {
                            updateUsersDetailsQuery = updateUsersDetailsQuery + " ,profile_picture='" + user.getProfilePicture() + "' ";
                        }
                        if (StringUtils.isNotBlank(user.getPassword())) {
                            updateUsersDetailsQuery = updateUsersDetailsQuery + " ,password='" + AESAlgo.encrypt(user.getPassword()) + "' ";
                        }

                        updateUsersDetailsQuery = updateUsersDetailsQuery + " where id=" + nUserId;
                        pstmt = objConn.prepareStatement(updateUsersDetailsQuery);
                        status = pstmt.executeUpdate();
                    }
                }

            } else {
                logger.error("updateAgentDetails(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("updateAgentDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("updateAgentDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public int mortgageSettings(MortgageSettings mortgageSettings, String id) throws SQLException, Exception {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = -1;
        String updateMortgageSettings = "update mortgage_master_info set updated_on=now() ";
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {

                if (mortgageSettings.getPropertyTax() > 0) {
                    updateMortgageSettings = updateMortgageSettings + ",property_tax=" + mortgageSettings.getPropertyTax();
                }
                if (mortgageSettings.getInsurenceTax() > 0) {
                    updateMortgageSettings = updateMortgageSettings + ",insurence_tax=" + mortgageSettings.getInsurenceTax();
                }
                if (mortgageSettings.getRateOfInterest() > 0) {
                    updateMortgageSettings = updateMortgageSettings + ",rate_of_interest=" + mortgageSettings.getRateOfInterest();
                }
                if (mortgageSettings.getNoOfYears() > 0) {
                    updateMortgageSettings = updateMortgageSettings + ",no_of_years=" + mortgageSettings.getNoOfYears();
                }
                updateMortgageSettings = updateMortgageSettings + " where id=" + id;
                pstmt = objConn.prepareStatement(updateMortgageSettings);
                status = pstmt.executeUpdate();

            }

        } catch (SQLException sqle) {
            logger.error("updateAgentDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("updateAgentDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public int currencySettings(CurrencyBean mortgageSettings, String id) throws SQLException, Exception {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = -1;
        String updateMortgageSettings = "update currency_converter";
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {

                if (StringUtils.isNotBlank(mortgageSettings.getCurrency())) {
                    updateMortgageSettings = updateMortgageSettings + " SET currency='" + mortgageSettings.getCurrency() + "'";
                }
                if (StringUtils.isNotBlank(mortgageSettings.getMul_fact())) {
                    updateMortgageSettings = updateMortgageSettings + " ,multiplication_factor='" + mortgageSettings.getMul_fact() + "'";
                }

                updateMortgageSettings = updateMortgageSettings + " where id=" + id;
                pstmt = objConn.prepareStatement(updateMortgageSettings);
                status = pstmt.executeUpdate();

            }

        } catch (SQLException sqle) {
            logger.error("updateAgentDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("updateAgentDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public String loginDetails(LoginRequestBean loginRequestBean, String strTransId) throws SQLException, Exception {
        String selectQuery = ConfigUtil.getProperty("user.login.query", "select * from users where user_id=? OR loginid=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(selectQuery);
                String password = AESAlgo.encrypt(loginRequestBean.getPassword());
                pstmt.setString(1, loginRequestBean.getUserId());
                pstmt.setString(2, loginRequestBean.getUserId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String pwd = rs.getString("password");
                    if (!pwd.equals(password)) {
                        return Utilities.prepareReponse(WRONG_PASSWORD.getCode(), WRONG_PASSWORD.DESC(), strTransId);
                    }
                    return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId, rs.getInt("id"));
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTransId);
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), "");
    }

    public User adminLoginDetails(LoginRequestBean loginRequestBean, String strTransId) throws SQLException, Exception {
        String selectQuery = ConfigUtil.getProperty("user.login.query", "select * from users where user_id=? OR loginid=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        User user = new User();
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(selectQuery);
                String password = AESAlgo.encrypt(loginRequestBean.getPassword());
                pstmt.setString(1, loginRequestBean.getUserId());
                pstmt.setString(2, loginRequestBean.getUserId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String pwd = rs.getString("password");
                    if (!pwd.equals(password)) {
                        user.setErrorMessage(Utilities.prepareReponse(WRONG_PASSWORD.getCode(), WRONG_PASSWORD.DESC(), strTransId));
                        return user;
                    }
                    user.setErrorMessage(Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId));
                    user.setId(rs.getString("id"));
                    user.setUser_id(rs.getString("user_id"));
                    user.setUserType(rs.getString("user_type"));
                    user.setPassword(loginRequestBean.getPassword());
                    user.setFirstname(rs.getString("firstname"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setCity(rs.getString("city"));
                    user.setState(rs.getString("state"));
                    String profile = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profile)) {
                        user.setProfilePicture(url + rs.getString("profile_picture"));
                    }
                    int user_type = rs.getInt("user_type");
                    if (user_type == 2) {
                        pstmt = objConn.prepareStatement("SELECT * FROM agent_details WHERE user_id=?");

                        pstmt.setString(1, user.getId());
                        rs = pstmt.executeQuery();
                        if (rs.next()) {

                            user.setAgentId(rs.getString("id"));
                        }

                    }
                    return user;
                } else {
                    user.setErrorMessage(Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTransId));
                    return user;
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        user.setErrorMessage(Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), ""));
        return user;
    }

    public User adminLoginDetailsUI(LoginRequestBean loginRequestBean, String strTransId) throws SQLException, Exception {
        String selectQuery = ConfigUtil.getProperty("user.login.query", "select * from users where user_id=? OR loginid=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        User user = new User();
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(selectQuery);
                // String password = AESAlgo.encrypt(loginRequestBean.getPassword());
                pstmt.setString(1, loginRequestBean.getUserId());
                pstmt.setString(2, loginRequestBean.getUserId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
//                    String pwd = rs.getString("password");
//                    if (!pwd.equals(password)) {
//                        user.setErrorMessage(Utilities.prepareReponse(WRONG_PASSWORD.getCode(), WRONG_PASSWORD.DESC(), strTransId));
//                        return user;
//                    }
                    user.setErrorMessage(Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId));
                    user.setId(rs.getString("id"));
                    user.setUser_id(rs.getString("user_id"));
                    user.setUserType(rs.getString("user_type"));
                    user.setPassword((rs.getString("password")));
                    user.setFirstname(rs.getString("firstname"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setCity(rs.getString("city"));
                    user.setState(rs.getString("state"));
                    String profile = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profile)) {
                        user.setProfilePicture(url + rs.getString("profile_picture"));
                    }

                    return user;
                } else {
                    user.setErrorMessage(Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTransId));
                    return user;
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        user.setErrorMessage(Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), ""));
        return user;
    }

    public User adminLoginDetailsBYAgentUI(String nID, String strTransId) throws SQLException, Exception {
        String selectQuery = ConfigUtil.getProperty("user.agent login.query", "select * from users u,agent_details ag where u.id=ag.user_id and ag.id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        User user = new User();
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(selectQuery);
                // String password = AESAlgo.encrypt(loginRequestBean.getPassword());
                pstmt.setString(1, nID);
                //   pstmt.setString(2, loginRequestBean.getUserId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
//                    String pwd = rs.getString("password");
//                    if (!pwd.equals(password)) {
//                        user.setErrorMessage(Utilities.prepareReponse(WRONG_PASSWORD.getCode(), WRONG_PASSWORD.DESC(), strTransId));
//                        return user;
//                    }
                    user.setAgentId(nID);
                    user.setErrorMessage(Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId));
                    user.setId(rs.getString("id"));
                    user.setUser_id(rs.getString("user_id"));
                    user.setUserType(rs.getString("user_type"));
                    user.setPassword((rs.getString("password")));
                    user.setFirstname(rs.getString("firstname"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setCity(rs.getString("city"));
                    user.setState(rs.getString("state"));
                    String profile = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profile)) {
                        user.setProfilePicture(url + rs.getString("profile_picture"));
                    }

                    return user;
                } else {
                    user.setErrorMessage(Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTransId));
                    return user;
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        user.setErrorMessage(Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), ""));
        return user;
    }

    public String passwordService(UserPasswordBean userPasswordBean, String transId) throws SQLException, Exception {
        String tokenQuery = ConfigUtil.getProperty("user.password.token", "select * from users where user_id=?");
        String forgotQuery = ConfigUtil.getProperty("user.password.forgot.query", "select * from users where forgot_password_token=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;

        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                if (userPasswordBean.getType().equalsIgnoreCase("forgot")) {
                    pstmt = objConn.prepareStatement(forgotQuery);
                    pstmt.setString(1, userPasswordBean.getToken().trim());
                } else {
                    pstmt = objConn.prepareStatement(tokenQuery);
                    pstmt.setString(1, userPasswordBean.getUserId().trim());

                }
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int status = rs.getInt("status");
                    String email = rs.getString("email");
                    String forgotPasswordToken = rs.getString("forgot_password_token");
                    if (status == Constants.USER_INACTIVE) {
                        return Utilities.prepareReponse(USER_INACTIVE.getCode(), USER_INACTIVE.DESC(), transId);
                    }
                    if (userPasswordBean.getType().equalsIgnoreCase("token")) {
                        String forgotToken = UUID.randomUUID().toString();
                        String tempPwd = Utilities.generateRandomString();
                        int nRes = updateToken(tempPwd, userPasswordBean.getUserId());
                        String pwdMailContent = forgotPWDMailContent;
                        if (nRes == Constants.INSERT_RECORD_SUCCESSFULLY) {
                            pwdMailContent = pwdMailContent.replaceAll("\\$\\(newPassword\\)", tempPwd);
                            pwdMailContent = pwdMailContent.replaceAll("\\$\\(userId\\)", email);
                            pwdMailContent = pwdMailContent.replaceAll("hostURL", url);
                            if (logger.isDebugEnabled()) {
                                logger.debug("Mail sending to user => " + email);
                            }
                            nRes = objMail.sendHtmlMail(mailFrom, email, mailCC, mailBCC, mailSubjectForForgotPwd, pwdMailContent, new ArrayList());
                        }
                    } else if (userPasswordBean.getType().equalsIgnoreCase("forgot")) {
                        String token = userPasswordBean.getToken();
                        if (StringUtils.isBlank(token) || !token.trim().equalsIgnoreCase(forgotPasswordToken)) {
                            return Utilities.prepareReponse(INVALID_FORGOT_PASSWORD_TOKEN.getCode(), INVALID_FORGOT_PASSWORD_TOKEN.DESC(), transId);
                        }
                        String password = AESAlgo.encrypt(userPasswordBean.getNewpwd());
                        int nRes = updatePassword(password, rs.getString("user_id"));
                        if (nRes == Constants.UPDATED_RECORD_SUCCESSFULLY) {
                            return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
                        } else {
                            return Utilities.prepareReponse(PASSWORD_UPDATE_FAILED.getCode(), PASSWORD_UPDATE_FAILED.DESC(), transId);
                        }
                    } else if (userPasswordBean.getType().equalsIgnoreCase("change")) {
                        String oldPassword = AESAlgo.encrypt(userPasswordBean.getOldpwd().trim());

                        if (!oldPassword.equalsIgnoreCase(rs.getString("password"))) {
                            return Utilities.prepareReponse(OLDPASSWORD_IS_WRONG.getCode(), OLDPASSWORD_IS_WRONG.DESC(), transId);
                        }

                        String newPassword = AESAlgo.encrypt(userPasswordBean.getNewpwd());
                        int nRes = updatePassword(newPassword, userPasswordBean.getUserId());
                        if (nRes == Constants.UPDATED_RECORD_SUCCESSFULLY) {
                            return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
                        } else {
                            return Utilities.prepareReponse(PASSWORD_UPDATE_FAILED.getCode(), PASSWORD_UPDATE_FAILED.DESC(), transId);
                        }
                    }
                    return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
                } else {
                    if (userPasswordBean.getType().equalsIgnoreCase("forgot")) {
                        return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), transId);
                    } else {
                        return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), transId);
                    }
                }
            }

        } catch (SQLException sqle) {
            logger.error("passwordService() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("passwordService() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), "");
    }

    public int updateToken(String pwd, String userId) {
        String tokenQuery = ConfigUtil.getProperty("user.password.update.token.query", "update users set password=? where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(tokenQuery);
                pwd = AESAlgo.encrypt(pwd);
                pstmt.setString(1, pwd);
                pstmt.setString(2, userId);
                return pstmt.executeUpdate();
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while updating the forgot password token" + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error(" Got Exception while updating the forgot password token" + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return -1;
    }

    public String addRecommondation(int nAgentId, String transId) {
        String tokenQuery = ConfigUtil.getProperty("add.agent.recommondation.query", "update agent_details set recommandations=recommandations+1 where id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(tokenQuery);
                pstmt.setInt(1, nAgentId);
                int nRes = pstmt.executeUpdate();
                if (nRes >= 1) {
                    return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
                }
            }
        } catch (SQLException sqle) {
            logger.error(" addRecommondation : Got SQLException while updating the recommondation" + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error("addRecommondation :  Got Exception while updating the recommondation" + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), "");
    }

    public String addReviewOnAgent(int nAgentId, String comment, int rating, String transId, String userId) {
        String tokenQuery = ConfigUtil.getProperty("add.agent.review.query", "INSERT INTO aqarabia.agent_reviews (agent_id, COMMENT, rating,user_id) VALUES (?,?,?,?);");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(tokenQuery);
                pstmt.setInt(1, nAgentId);
                pstmt.setString(2, comment);
                pstmt.setInt(3, rating);
                pstmt.setString(4, userId);
                int nRes = pstmt.executeUpdate();
                if (nRes >= 1) {
                    return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
                }
            }
        } catch (SQLException sqle) {
            logger.error(" addReviewOnAgent : Got SQLException while inserting review of agent " + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error("addReviewOnAgent :  Got Exception while inserting review of agent " + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), "");
    }

    public int getUserID(String userId, String transId) {
        String userQuery = ConfigUtil.getProperty("user.query", "select * from users where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userQuery);
                pstmt.setString(1, userId);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while updating the get userId" + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error(" Got Exception while updating the get userId" + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return -1;
    }

    public int updatePassword(String password, String userId) {
        String tokenQuery = ConfigUtil.getProperty("user.password.update.query", "update users set password=?,forgot_password_token=null where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(tokenQuery);
                pstmt.setString(1, password);
                pstmt.setString(2, userId);
                return pstmt.executeUpdate();
            }
        } catch (SQLException sqle) {
            logger.error("[updatePassword] Got SQLException while updating the forgot password token" + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error("[updatePassword] Got Exception while updating the forgot password token" + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }

        return -1;
    }

    public String updateUserDetails(User user, String strTransId) throws SQLException, Exception {
        String updateQuery = ConfigUtil.getProperty("user.update.query", "update users set $(updateString) where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        String updateString = " updated_on=now()";
        try {

            if (StringUtils.isNotBlank(user.getEmail())) {
                updateString = updateString + ",email='" + user.getEmail() + "'";
            }

            if (StringUtils.isNotBlank(user.getFirstname())) {
                updateString = updateString + ",firstname='" + user.getFirstname() + "'";
            }

            if (StringUtils.isNotBlank(user.getMiddlename())) {
                updateString = updateString + ",middlename='" + user.getMiddlename() + "'";
            }
            if (StringUtils.isNotBlank(user.getLastname())) {
                updateString = updateString + ",lastname='" + user.getLastname() + "'";
            }

            if (StringUtils.isNotBlank(user.getPhone())) {
                updateString = updateString + ",phone='" + user.getPhone() + "'";
            }

            if (StringUtils.isNotBlank(user.getSalutation())) {
                updateString = updateString + ",salutation='" + user.getSalutation() + "'";
            }

            if (StringUtils.isNotBlank(user.getAddress1())) {
                updateString = updateString + ",address1='" + user.getAddress1() + "'";
            }

            if (StringUtils.isNotBlank(user.getAddress2())) {
                updateString = updateString + ",address2='" + user.getAddress2() + "'";
            }
            if (StringUtils.isNotBlank(user.getCity())) {
                updateString = updateString + ",city='" + user.getCity() + "'";
            }
            if (StringUtils.isNotBlank(user.getCountry())) {
                updateString = updateString + ",country='" + user.getCountry() + "'";
            }
            if (StringUtils.isNotBlank(user.getState())) {
                updateString = updateString + ",state='" + user.getState() + "'";
            }

            if (user.getStatus() != null && (user.getStatus() == 1 || user.getStatus() == 2)) {
                updateString = updateString + ",status=" + user.getStatus();
            }

            updateQuery = updateQuery.replaceAll("\\$\\(updateString\\)", updateString);

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setString(1, user.getUser_id());
                int nRes = pstmt.executeUpdate();
                if (nRes == 1) {
                    return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId);
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTransId);
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), "");
    }

    public String saveProperty(int nUserId, int nPropertyId, String strTransId, int nStatus) throws SQLException, Exception {
        String updateQuery = ConfigUtil.getProperty("save.property.to.user", "INSERT INTO aqarabia.user_saved_propertys (user_id,property_id)	VALUES (?,?);");
        String unSavePropertyQuery = ConfigUtil.getProperty("unsave.property.to.user", "DELETE FROM aqarabia.user_saved_propertys WHERE user_id=? and property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                if (nStatus == 2) {
                    pstmt = objConn.prepareStatement(unSavePropertyQuery);
                } else {
                    pstmt = objConn.prepareStatement(updateQuery);
                }
                pstmt.setInt(1, nUserId);
                pstmt.setInt(2, nPropertyId);
                int nRes = pstmt.executeUpdate();
                if (nRes == 1) {
                    return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId);
                } else {
                    return Utilities.prepareReponse(USER_PROPERTY_SAVE_FAILED.getCode(), USER_PROPERTY_SAVE_FAILED.DESC(), strTransId);
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }

        return Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), "");
    }

    public String getSavedPropertys(int nUserId, String strTransId, String currency) throws SQLException, Exception {
        String updateQuery = ConfigUtil.getProperty("saved.propertys.query", "select group_concat(property_id) as propertyIds from user_saved_propertys where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setInt(1, nUserId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String propertyIds = rs.getString("propertyIds");
                    if (StringUtils.isNotBlank(propertyIds)) {
                        return getPropertiesByIds(strTransId, 0, propertyIds, currency);
                    }
                    return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId);
                } else {
                    return Utilities.prepareReponse(USER_PROPERTY_SAVE_FAILED.getCode(), USER_PROPERTY_SAVE_FAILED.DESC(), strTransId);
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), "");
    }

    public String getSavedSearches(int nUserId, String strTransId) throws SQLException, Exception {
        String updateQuery = ConfigUtil.getProperty("get.saved.user.searches.query", "select * from user_searches where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {
            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTransId);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setInt(1, nUserId);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.name, rs.getString("name"));
                    property.put(Constants.searchQuery, rs.getString("search"));
                    propertyArray.put(property);
                }
                objRequest.put("searches", propertyArray);
                objFinalResponse.put("response", objRequest);
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String savePropertySearch(int nUserId, String name, String searchQuery, String strTransId) throws SQLException, Exception {
        String updateQuery = ConfigUtil.getProperty("save.property.search", "INSERT INTO aqarabia.user_searches (user_id,name,search)	VALUES (?,?,?);");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setInt(1, nUserId);
                pstmt.setString(2, name);
                pstmt.setString(3, searchQuery);
                int nRes = pstmt.executeUpdate();
                if (nRes == 1) {
                    return Utilities.prepareReponsetoken(SUCCESS.getCode(), SUCCESS.DESC(), strTransId);
                } else {
                    return Utilities.prepareReponse(FAILED_TO_SAVE_SEARCH.getCode(), FAILED_TO_SAVE_SEARCH.DESC(), strTransId);
                }
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(INVALID_USER_DETAILS.getCode(), INVALID_USER_DETAILS.DESC(), "");
    }

    public String getUserDetails(String userId, String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("user.details.query", "select * from users where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setString(1, userId);
                rs = pstmt.executeQuery();

                JSONArray strResponse = Utilities.convertResultSetIntoJSON(rs);

                if (strResponse != null && strResponse.length() > 0) {
                    objRequest.put("code", "1000");
                    objRequest.put("transid", strTid);
                    objRequest.put("userdetails", strResponse);
                    objFinalResponse.put("response", objRequest);
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTid);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getProperties(String strTid, String currency, int nUserId, int nIsSquareMeter, String latitude, String longitude, String strRadius) throws SQLException, Exception {
        String propertydetailsquery = ConfigUtil.getProperty("property.details.query", "SELECT ( 3959 * ACOS( COS( RADIANS($(lat)) ) * COS( RADIANS( latitude ) ) * COS( RADIANS( longitude ) - RADIANS($(long)) ) + SIN( RADIANS($(lat)) ) * SIN( RADIANS( latitude ) ) ) ) AS distance,p.* FROM property p ,property_features_mapping pfm WHERE p.id=pfm.property_id AND pfm.category=? ");
        String propertyCatQuery = ConfigUtil.getProperty("property.category.details.query", "SELECT * FROM property_features_mapping group by category");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        ResultSet rs1 = null;
        PreparedStatement pstmt1 = null;
        // Connection objConn1 = null;
        JSONObject objFinalResponse = new JSONObject();
        propertylock.lock();
        try {
            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt1 = objConn.prepareStatement(propertyCatQuery);
                rs1 = pstmt1.executeQuery();
                // objConn = DBConnection.getInstance().getConnection();
                //latitude ,longitude and radius values are updating to query
                try {
                    propertydetailsquery = propertydetailsquery.replaceAll("\\$\\(lat\\)", latitude);
                    propertydetailsquery = propertydetailsquery.replaceAll("\\$\\(long\\)", longitude);
                    if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                        propertydetailsquery = propertydetailsquery + " HAVING distance < " + strRadius + " limit 5";
                    } else {
                        propertydetailsquery = propertydetailsquery + " limit 5";
                    }
                } catch (Exception e) {
                    propertydetailsquery = propertydetailsquery + " limit 5";
                    e.printStackTrace();
                    logger.error("Got Exception while replacing latitude and longitude " + Utilities.getStackTrace(e));
                }
                while (rs1.next()) {
                    if (objConn != null) {

                        pstmt = objConn.prepareStatement(propertydetailsquery);
                        pstmt.setInt(1, rs1.getInt("category"));
                        rs = pstmt.executeQuery();
                        JSONArray propertyArray = new JSONArray();
                        while (rs.next()) {
                            JSONObject property = new JSONObject();
                            property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                            property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));
                            String price = rs.getString("price");
                            int nPrice = 0;
                            if ((StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(price)) && !currency.equalsIgnoreCase("USD")) {
                                Double dPrice = Double.parseDouble(price);
                                if (dPrice > 0) {
                                    Double factor = getCurrencyValue(currency, objConn);
                                    Double finalPrice = 0d;
                                    if (factor != null) {
                                        finalPrice = dPrice * factor;
                                    }
                                    nPrice = finalPrice.intValue();
                                }
                            } else {
                                nPrice = Integer.parseInt(price);
                            }
                            property.put(Constants.price, nPrice + "");
                            property.put(Constants.rateOfIntrest, nPrice / 20 / 12);
                            property.put(Constants.propertyTax, 0);
                            property.put(Constants.insurenceTax, (int) ((nPrice * 3) / 100));
                            property.put(Constants.propertyType, rs.getInt("property_type"));
                            property.put(Constants.propertyId, rs.getInt("id"));
                            property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                            property.put(Constants.beds, rs.getInt("beds"));
                            property.put(Constants.bath, rs.getInt("bath"));
                            property.put(Constants.category, rs.getInt("category"));
                            property.put(Constants.propertyCategory, propertyIdByFeauters(rs.getInt("id"), objConn));
                            if (nIsSquareMeter == 1) {
                                property.put(Constants.footRange, (int) (rs.getInt("foot_range") * oneSquareMeter));
                            } else {
                                property.put(Constants.footRange, rs.getInt("foot_range"));
                            }
                            //Natural disasters
                            JSONObject naturalDisasters = new JSONObject();
                            naturalDisasters.put(Constants.flood, rs.getInt("flood"));
                            naturalDisasters.put(Constants.seismic, rs.getInt("seismic"));
                            property.put(Constants.crimeInfo, rs.getInt("crime_info"));
                            property.put(Constants.naturalDisasters, naturalDisasters);
                            property.put(Constants.yearBuilt, rs.getInt("year_built"));
                            property.put(Constants.furnished, rs.getInt("furnished"));
                            property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                            property.put(Constants.parkingType, rs.getString("parking_type"));
                            property.put(Constants.lotSize, rs.getInt("lot_size"));
                            property.put(Constants.listingType, rs.getInt("listing_type"));
                            property.put(Constants.houseType, rs.getInt("house_type"));
                            property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                            property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                            property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                            property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                            property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                            property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                            property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                            property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));
                            property.put(Constants.dateAvailable, rs.getTimestamp("date_available") + "");
                            property.put(Constants.createdOn, rs.getTimestamp("created_on") + "");
                            property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                            property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                            property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                            // need to get property is saved or not
                            if (nUserId > 0) {
                                property.put(Constants.propertySaved, isPropertySaved(nUserId, rs.getInt("id"), objConn));
                            } else {
                                property.put(Constants.propertySaved, 0);
                            }
                            int nDays = Utilities.toInt(ConfigUtil.getProperty("property.new.in.days", "15"), 15);
                            int days = (int) Utilities.getDifferenceDays(rs.getDate("created_on"), new Date());

                            if (nDays >= days) {
                                property.put(Constants.isPropertyNew, 1);
                            } else {
                                property.put(Constants.isPropertyNew, 0);
                            }
                            property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                            JSONArray images = propertyImages(rs.getInt("id"), objConn);
                            if (images != null && images.length() > 0) {
                                property.put(Constants.images, images);
                            }
                            propertyArray.put(property);
                        }
                        objRequest.put(propertyCategoryDesc(rs1.getInt("category")), propertyArray);
                    }

                }
                objFinalResponse.put("response", objRequest);
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getpropertylist" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getpropertylist" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }

            propertylock.unlock();
            try {

                if (rs1 != null) {
                    rs1.close();
                    rs1 = null;
                }
                if (pstmt1 != null) {
                    pstmt1.close();
                    pstmt1 = null;
                }
//                if (objConn1 != null) {
//                    objConn1.close();
//                    objConn1 = null;
//                }
            } catch (Exception e) {
                logger.error("objConn1=>" + e);
            }
        }
        return objFinalResponse.toString();
    }

    public String getProperties(String strTid, int categoryId, int nPropertyId, String currency, int nUserId, int nIsSquareMeter, int min, int max, String latitude, String longitude, String strRadius) throws SQLException, Exception {
        String propertydetailsquery = ConfigUtil.getProperty("property.details.query", "SELECT ( 3959 * ACOS( COS( RADIANS($(lat)) ) * COS( RADIANS( latitude ) ) * COS( RADIANS( longitude ) - RADIANS($(long)) ) + SIN( RADIANS($(lat)) ) * SIN( RADIANS( latitude ) ) ) ) AS distance,p.* FROM property p ,property_features_mapping pfm WHERE p.id=pfm.property_id ");
        String propertyDetailsById = ConfigUtil.getProperty("property.details.query", "SELECT * FROM property p where id=? ");
//        String radiusQuery = ConfigUtil.getProperty("radius.query", "( 3959 * ACOS( COS( RADIANS(25) ) * COS( RADIANS( latitude ) ) * COS( RADIANS( longitude ) - RADIANS(55) ) + SIN( RADIANS(25) ) * SIN( RADIANS( latitude ) ) ) ) AS distance FROM property HAVING distance < 100");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        propertylock1.lock();
        JSONObject objFinalResponse = new JSONObject();
        try {
            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                if (categoryId > 0) {
                    propertydetailsquery = propertydetailsquery + " AND pfm.category=?";
                } else if (nPropertyId > 0) {
                    propertydetailsquery = propertyDetailsById;
                }

                //latitude ,longitude and radius values are updating to query
                try {

                    propertydetailsquery = propertydetailsquery.replaceAll("\\$\\(lat\\)", latitude);
                    propertydetailsquery = propertydetailsquery.replaceAll("\\$\\(long\\)", longitude);
                    if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                        propertydetailsquery = propertydetailsquery + " HAVING distance < " + strRadius;
                    }
                } catch (Exception e) {
                }

                pstmt = objConn.prepareStatement(propertydetailsquery);
                if (categoryId > 0) {
                    pstmt.setInt(1, categoryId);
                } else if (nPropertyId > 0) {
                    pstmt.setInt(1, nPropertyId);

                }
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));

                    String price = rs.getString("price");
                    int nPrice = 0;
                    if ((StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(price)) && !currency.equalsIgnoreCase("USD")) {
                        Double dPrice = Double.parseDouble(price);
                        if (dPrice > 0) {
                            Double factor = getCurrencyValue(currency, objConn);
                            Double finalPrice = 0d;
                            if (factor != null) {
                                finalPrice = dPrice * factor;
                            }
                            nPrice = finalPrice.intValue();
                        }
                    } else {
                        nPrice = Integer.parseInt(price);
                    }
                    property.put(Constants.price, nPrice + "");
                    property.put(Constants.rateOfIntrest, nPrice / 20 / 12);
                    property.put(Constants.propertyTax, 0);
                    property.put(Constants.insurenceTax, (int) ((nPrice * 3) / 100));

                    try {
                        if (nPropertyId > 0) {
                            property.put("propertyMortgageDetails", mortgageDetails(nPrice));
                        }
                    } catch (Exception e) {
                        logger.error("Got Exception while calculating mortgage details " + Utilities.getStackTrace(e));
                    }

                    property.put(Constants.propertyType, rs.getInt("property_type"));
                    property.put(Constants.propertyId, rs.getInt("id"));
                    property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                    property.put(Constants.beds, rs.getInt("beds"));
                    property.put(Constants.bath, rs.getInt("bath"));
                    property.put(Constants.category, rs.getInt("category"));
                    property.put(Constants.developer, rs.getString("developer"));
                    property.put(Constants.propertyCategory, propertyIdByFeauters(rs.getInt("id"), objConn));
                    if (nIsSquareMeter == 1) {
                        property.put(Constants.footRange, (int) (rs.getInt("foot_range") * oneSquareMeter));
                    } else {
                        property.put(Constants.footRange, rs.getInt("foot_range"));
                    }
                    //Natural disasters
                    JSONObject naturalDisasters = new JSONObject();
                    naturalDisasters.put(Constants.flood, rs.getInt("flood"));
                    naturalDisasters.put(Constants.seismic, rs.getInt("seismic"));
                    property.put(Constants.naturalDisasters, naturalDisasters);
                    property.put(Constants.crimeInfo, rs.getInt("crime_info"));
                    property.put(Constants.yearBuilt, rs.getInt("year_built"));
                    property.put(Constants.furnished, rs.getInt("furnished"));
                    property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                    property.put(Constants.parkingType, rs.getString("parking_type"));
                    property.put(Constants.lotSize, rs.getInt("lot_size"));
                    property.put(Constants.listingType, rs.getInt("listing_type"));
                    property.put(Constants.houseType, rs.getInt("house_type"));
                    property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                    property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                    property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                    property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                    property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                    property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));
                    property.put(Constants.dateAvailable, rs.getTimestamp("date_available") + "");
                    property.put(Constants.createdOn, Utilities.getDate(rs.getDate("created_on")));
                    property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                    property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                    property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                    // need to get property is saved or not
                    if (nUserId > 0) {
                        property.put(Constants.propertySaved, isPropertySaved(nUserId, rs.getInt("id"), objConn));
                    }
                    int nDays = Utilities.toInt(ConfigUtil.getProperty("property.new.in.days", "15"), 15);
                    int days = (int) Utilities.getDifferenceDays(rs.getDate("created_on"), new Date());

                    if (nDays >= days) {
                        property.put(Constants.isPropertyNew, 1);
                    } else {
                        property.put(Constants.isPropertyNew, 0);
                    }
                    property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                    JSONArray images = propertyImages(rs.getInt("id"), objConn);
                    if (images != null && images.length() > 0) {
                        property.put(Constants.images, images);
                    }
                    propertyArray.put(property);
                }
                objRequest.put("properties", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
            propertylock1.unlock();
        }
        return objFinalResponse.toString();
    }

    public String getPropertiesByAgentId(int nAgentId, String strTid, String currency, int nUserId, int nIsSquareMeter, int min, int max, String latitude, String longitude, String strRadius) throws SQLException, Exception {
        String propertydetailsquery = ConfigUtil.getProperty("property.details.by.agentid.query", "SELECT ( 3959 * ACOS( COS( RADIANS($(lat)) ) * COS( RADIANS( latitude ) ) * COS( RADIANS( longitude ) - RADIANS($(long)) ) + SIN( RADIANS($(lat)) ) * SIN( RADIANS( latitude ) ) ) ) AS distance,p.* FROM property p, agent_property_mapping apm WHERE apm.agent_id=? AND p.status=1 AND p.category NOT IN (3) AND p.share_to_agents=1 group by p.id ");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        propertylock2.lock();
        JSONObject objFinalResponse = new JSONObject();
        try {
            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                //latitude ,longitude and radius values are updating to query
                try {

                    propertydetailsquery = propertydetailsquery.replaceAll("\\$\\(lat\\)", latitude + "");
                    propertydetailsquery = propertydetailsquery.replaceAll("\\$\\(long\\)", longitude + "");
                    if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                        propertydetailsquery = propertydetailsquery + " HAVING distance < " + strRadius;
                    }
                } catch (Exception e) {
                }
                pstmt = objConn.prepareStatement(propertydetailsquery);
                pstmt.setInt(1, nAgentId);

                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));
                    String price = rs.getString("price");
                    int nPrice = 0;
                    if ((StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(price)) && !currency.equalsIgnoreCase("USD")) {
                        Double dPrice = Double.parseDouble(price);
                        if (dPrice > 0) {
                            Double factor = getCurrencyValue(currency, objConn);
                            Double finalPrice = 0d;
                            if (factor != null) {
                                finalPrice = dPrice * factor;
                            }
                            nPrice = finalPrice.intValue();
                        }
                    } else {
                        nPrice = Integer.parseInt(price);
                    }
                    property.put(Constants.price, nPrice + "");
                    property.put(Constants.rateOfIntrest, nPrice / 20 / 12);
                    property.put(Constants.propertyTax, 0);
                    property.put(Constants.insurenceTax, (int) ((nPrice * 3) / 100));
                    property.put(Constants.propertyType, rs.getInt("property_type"));
                    property.put(Constants.propertyId, rs.getInt("id"));
                    property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                    property.put(Constants.beds, rs.getInt("beds"));
                    property.put(Constants.bath, rs.getInt("bath"));
                    property.put(Constants.category, rs.getInt("category"));
                    property.put(Constants.developer, rs.getString("developer"));
                    property.put(Constants.propertyCategory, propertyIdByFeauters(rs.getInt("id"), objConn));
                    if (nIsSquareMeter == 1) {
                        property.put(Constants.footRange, (int) (rs.getInt("foot_range") * oneSquareMeter));
                    } else {
                        property.put(Constants.footRange, rs.getInt("foot_range"));
                    }

                    //Natural disasters
                    JSONObject naturalDisasters = new JSONObject();
                    naturalDisasters.put(Constants.flood, rs.getInt("flood"));
                    naturalDisasters.put(Constants.seismic, rs.getInt("seismic"));
                    property.put(Constants.crimeInfo, rs.getInt("crime_info"));
                    property.put(Constants.naturalDisasters, naturalDisasters);
                    property.put(Constants.yearBuilt, rs.getInt("year_built"));
                    property.put(Constants.furnished, rs.getInt("furnished"));
                    property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                    property.put(Constants.parkingType, rs.getString("parking_type"));
                    property.put(Constants.lotSize, rs.getInt("lot_size"));
                    property.put(Constants.listingType, rs.getInt("listing_type"));
                    property.put(Constants.houseType, rs.getInt("house_type"));
                    property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                    property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                    property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                    property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                    property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                    property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));
                    property.put(Constants.dateAvailable, rs.getTimestamp("date_available") + "");
                    property.put(Constants.createdOn, rs.getTimestamp("created_on") + "");
                    property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                    property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                    property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                    // need to get property is saved or not
                    if (nUserId > 0) {
                        property.put(Constants.propertySaved, isPropertySaved(nUserId, rs.getInt("id"), objConn));
                    }
                    int nDays = Utilities.toInt(ConfigUtil.getProperty("property.new.in.days", "15"), 15);
                    int days = (int) Utilities.getDifferenceDays(rs.getDate("created_on"), new Date());

                    if (nDays >= days) {
                        property.put(Constants.isPropertyNew, 1);
                    } else {
                        property.put(Constants.isPropertyNew, 0);
                    }
                    property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                    JSONArray images = propertyImages(rs.getInt("id"), objConn);
                    if (images != null && images.length() > 0) {
                        property.put(Constants.images, images);
                    }
                    propertyArray.put(property);
                }
                objRequest.put("properties", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
            propertylock2.unlock();
        }
        return objFinalResponse.toString();
    }

    public String getPropertiesByIds(String strTid, int categoryId, String propertyIds, String currency) throws SQLException, Exception {
        String propertydetailsquery = ConfigUtil.getProperty("property.details.query", "SELECT * FROM property p where id in (?) ");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        propertylock3.lock();
        JSONObject objFinalResponse = new JSONObject();
        try {
            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                propertydetailsquery = propertydetailsquery.replaceAll("\\?", propertyIds);
                pstmt = objConn.prepareStatement(propertydetailsquery);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));
                    String price = rs.getString("price");
                    int nPrice = 0;
                    if ((StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(price)) && !currency.equalsIgnoreCase("USD")) {
                        Double dPrice = Double.parseDouble(price);
                        if (dPrice > 0) {
                            Double factor = getCurrencyValue(currency, objConn);
                            Double finalPrice = 0d;
                            if (factor != null) {
                                finalPrice = dPrice * factor;
                            }
                            nPrice = finalPrice.intValue();
                        }
                    } else {
                        nPrice = Integer.parseInt(price);
                    }
                    property.put(Constants.price, nPrice + "");
                    property.put(Constants.propertyType, rs.getInt("property_type"));
                    property.put(Constants.propertyId, rs.getInt("id"));
                    property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                    property.put(Constants.beds, rs.getInt("beds"));
                    property.put(Constants.bath, rs.getInt("bath"));
                    property.put(Constants.category, rs.getInt("category"));
                    property.put(Constants.developer, rs.getString("developer"));
                    property.put(Constants.propertyCategory, propertyIdByFeauters(rs.getInt("id"), objConn));
                    property.put(Constants.footRange, rs.getInt("foot_range"));
                    property.put(Constants.yearBuilt, rs.getInt("year_built"));
                    property.put(Constants.furnished, rs.getInt("furnished"));
                    property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                    property.put(Constants.parkingType, rs.getString("parking_type"));
                    property.put(Constants.lotSize, rs.getInt("lot_size"));
                    property.put(Constants.listingType, rs.getInt("listing_type"));
                    property.put(Constants.houseType, rs.getInt("house_type"));
                    property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                    property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                    property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                    property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                    property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                    property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));
                    property.put(Constants.dateAvailable, rs.getTimestamp("date_available") + "");
                    property.put(Constants.createdOn, rs.getTimestamp("created_on") + "");
                    property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                    property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                    property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                    property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                    JSONArray images = propertyImages(rs.getInt("id"), objConn);
                    if (images != null && images.length() > 0) {
                        property.put(Constants.images, images);
                    }
                    propertyArray.put(property);
                }
                objRequest.put("properties", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
            propertylock3.unlock();
        }
        return objFinalResponse.toString();
    }

    public JSONArray getPropertiesList(String strTid, int fromIndex, int endIndex, String agent_id) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("property.details.query", "SELECT * FROM property p ");
        // ",agent_property_mapping apm WHERE p.share_to_agents=1 AND p.id=apm.property_id");
        if (StringUtils.isNotBlank(agent_id)) {

            userdetailsquery = userdetailsquery + " ,agent_property_mapping apm WHERE  p.id=apm.property_id and apm.agent_id=" + agent_id + " and p.status in (1,2,3)";
        } else {

            userdetailsquery = userdetailsquery + "  where p.status in (1,2,3) ";
        }
//        userdetailsquery = userdetailsquery + " ";
        userdetailsquery = userdetailsquery + " order by p.created_on desc LIMIT " + fromIndex + "," + endIndex;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();

        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.propertyId, rs.getString(Constants.id));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));
                    property.put(Constants.price, Utilities.nullToEmpty(rs.getString("price")));
                    property.put(Constants.propertyType, rs.getInt("property_type"));
                    property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                    property.put(Constants.beds, rs.getInt("beds"));
                    property.put(Constants.bath, rs.getInt("bath"));
                    property.put(Constants.category, rs.getInt("category"));
                    property.put(Constants.developer, rs.getString("developer"));
                    property.put(Constants.footRange, rs.getInt("foot_range"));
                    property.put(Constants.yearBuilt, rs.getInt("year_built"));
                    property.put(Constants.furnished, rs.getInt("furnished"));
                    property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                    property.put(Constants.parkingType, rs.getString("parking_type"));
                    property.put(Constants.lotSize, rs.getInt("lot_size"));
                    property.put(Constants.listingType, rs.getInt("listing_type"));
                    property.put(Constants.houseType, rs.getInt("house_type"));
                    property.put(Constants.status, rs.getInt("status"));
                    property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                    property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                    property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                    property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                    property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                    property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));
                    property.put(Constants.dateAvailable, rs.getTimestamp("date_available") + "");
                    property.put(Constants.createdOn, rs.getTimestamp("created_on") + "");
                    property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                    property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                    property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));

                    property.put("share_to_agents", Utilities.nullToEmpty(rs.getString("share_to_agents")));
                    property.put(Constants.agents, getPropertyAgentDetails(rs.getInt("id")));

                    property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                    JSONArray images = propertyImages(rs.getInt("id"), objConn);
                    if (images != null && images.length() > 0) {
                        property.put(Constants.images, images);
                    }
                    propertyArray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return propertyArray;
    }

    public int getPropertiesListCount(String strTid, String agent_id) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("property.details.count.query", "SELECT count(*) as count FROM property p ");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();
        if (StringUtils.isNotBlank(agent_id)) {

            userdetailsquery = userdetailsquery + " ,agent_property_mapping apm WHERE  p.id=apm.property_id and apm.agent_id=" + agent_id;
            userdetailsquery = userdetailsquery + " and p.status in (1,2,3)";
        } else {
            userdetailsquery = userdetailsquery + " where p.status in (1,2,3)";
        }

//        else {
//
//            userdetailsquery = userdetailsquery + "  WHERE p.share_to_agents=1";
//        }
        int count = 0;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    count = rs.getInt("count");
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public int getHomeWorthlistListCount(String strTid, String searchstr) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("homeworthlist.count.query", "SELECT count(*) as count FROM home_worth");

        if (StringUtils.isNotBlank(searchstr)) {
            userdetailsquery = userdetailsquery + " WHERE NAME LIKE \"%" + searchstr + "%\" OR address LIKE \"%" + searchstr + "%\" OR email LIKE \"%" + searchstr + "%\" OR mobile LIKE \"%" + searchstr + "%\" ";
        }
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int count = 0;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getHomeWorthlistListCount" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getHomeWorthlistListCount" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public JSONArray getHomeWorthlistList(String strTid, int fromIndex, int endIndex, String searchstr) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("homeworthlist.query", "SELECT * FROM home_worth");
        if (StringUtils.isNotBlank(searchstr)) {
            userdetailsquery = userdetailsquery + " WHERE NAME LIKE \"%" + searchstr + "%\" OR address LIKE \"%" + searchstr + "%\" OR email LIKE \"%" + searchstr + "%\" OR mobile LIKE \"%" + searchstr + "%\" ";
        }
        userdetailsquery = userdetailsquery + " ORDER BY created_on desc";
        userdetailsquery = userdetailsquery + " LIMIT " + fromIndex + "," + endIndex;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();

        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getString(Constants.id));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    property.put(Constants.email, Utilities.nullToEmpty(rs.getString("email")));
                    property.put(Constants.mobile, Utilities.nullToEmpty(rs.getString("mobile")));
                    property.put(Constants.propertyType, rs.getInt("property_type"));
                    property.put(Constants.beds, rs.getInt("beds"));
                    property.put(Constants.bath, rs.getInt("baths"));
                    property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                    property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                    property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                    property.put(Constants.status, rs.getInt("status"));
                    property.put("sell_in_months", rs.getInt("sell_in_months"));
                    property.put(Constants.createdOn, Utilities.getDateFromStringRiyadh(rs.getTimestamp("created_on") + ""));

                    property.put("comments", rs.getInt("comments"));

                    propertyArray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getHomeWorthlistList" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getHomeWorthlistList" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return propertyArray;
    }

    public int mortagesettinglistCount(String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("mortagesettinglist.count.query", "SELECT count(*) as count FROM mortgage_master_info");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int count = 0;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while mortagesettinglistCount" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while mortagesettinglistCount" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public JSONArray mortagesettinglist(String strTid, int fromIndex, int endIndex) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("mortagesettinglist.query", "SELECT * FROM mortgage_master_info ORDER BY id asc");
        userdetailsquery = userdetailsquery + " LIMIT " + fromIndex + "," + endIndex;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();

        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getString(Constants.id));
                    property.put(Constants.propertyTax, Utilities.nullToEmpty(rs.getString("property_tax")));
                    property.put("no_of_years", Utilities.nullToEmpty(rs.getString("no_of_years")));
                    property.put(Constants.rateOfIntrest, Utilities.nullToEmpty(rs.getString("rate_of_interest")));
                    property.put(Constants.insurenceTax, rs.getString("insurence_tax"));
                    property.put(Constants.createdOn, rs.getString("created_on"));
                    propertyArray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while mortagesettinglist" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while mortagesettinglist" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return propertyArray;
    }

    public JSONArray getMortgageList(String strTid, int fromIndex, int endIndex) throws SQLException, Exception {
        String mortgagedetailsquery = ConfigUtil.getProperty("mortagagelist.query", "SELECT * FROM mortgage_info ORDER BY created_on desc");
        mortgagedetailsquery = mortgagedetailsquery + " LIMIT " + fromIndex + "," + endIndex;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();

        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(mortgagedetailsquery);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getString(Constants.id));
                    property.put(Constants.userId, Utilities.nullToEmpty(rs.getString("user_id")));
                    property.put(Constants.mobile, Utilities.nullToEmpty(rs.getString("mobile")));
                    property.put(Constants.type, Utilities.nullToEmpty(rs.getString("type")));

                    String mortgageDetails = rs.getString("mortgage_details");
                    if (StringUtils.isNotBlank(mortgageDetails)) {
                        JSONObject object = new JSONObject(mortgageDetails);
//                        Iterator it = object.keys();
//                        while (it.hasNext()) {
//                            String key = (String) it.next();
//                            property.put(key, object.get(key));
//                        }
                        property.put("mortgage_details", object);
                    }

                    propertyArray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getHomeWorthlistList" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getHomeWorthlistList" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return propertyArray;
    }

    public int getMortgagelistCount(String strTid) throws SQLException, Exception {
        String mortgagedetailsquery = ConfigUtil.getProperty("mortagagelist.count.query", "SELECT count(*) as count FROM mortgage_info");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int count = 0;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(mortgagedetailsquery);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getMortgagelistCount" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getMortgagelistCount" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public JSONArray getRequestInfoList(String strTid, int fromIndex, int endIndex, String agentId) throws SQLException, Exception {
        String mortgagedetailsquery = ConfigUtil.getProperty("mortagagelist.query", "SELECT * FROM request_info");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();
        if (StringUtils.isNotBlank(agentId)) {
            mortgagedetailsquery = mortgagedetailsquery + " where agent_id=" + agentId;
        }
        mortgagedetailsquery = mortgagedetailsquery + " ORDER BY id desc";
        mortgagedetailsquery = mortgagedetailsquery + " LIMIT " + fromIndex + "," + endIndex;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(mortgagedetailsquery);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();

                    property.put(Constants.id, rs.getString(Constants.id));
                    property.put(Constants.userId, Utilities.nullToEmpty(rs.getString("user_id")));
                    property.put(Constants.mobile, Utilities.nullToEmpty(rs.getString("mobile")));
                    property.put(Constants.email, Utilities.nullToEmpty(rs.getString("email_id")));
                    property.put(Constants.propertyId, Utilities.nullToEmpty(rs.getString("property_id")));
                    property.put(Constants.datetime, Utilities.getDateFromStringRiyadh(Utilities.nullToEmpty(rs.getString("date"))));
                    propertyArray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getHomeWorthlistList" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getHomeWorthlistList" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return propertyArray;
    }

    public int getrequestinfolistCount(String strTid, String agentId) throws SQLException, Exception {
        String mortgagedetailsquery = ConfigUtil.getProperty("mortagagelist.count.query", "SELECT count(*) as count FROM request_info");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int count = 0;
        try {

            if (StringUtils.isNotBlank(agentId)) {
                mortgagedetailsquery = mortgagedetailsquery + " where agent_id=" + agentId;
            }
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(mortgagedetailsquery);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getMortgagelistCount" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getMortgagelistCount" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public JSONObject getPropertie(String strTid, String id) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("property.details.query", "SELECT * FROM property");
        String propertycat = ConfigUtil.getProperty("property.property_features_mapping.query", "SELECT GROUP_CONCAT(category) AS category FROM property_features_mapping WHERE property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject property = new JSONObject();

        try {
            userdetailsquery = userdetailsquery + " where id='" + id + "'";

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();

                if (rs.next()) {

                    property.put(Constants.id, rs.getString(Constants.id));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));
                    property.put(Constants.price, Utilities.nullToEmpty(rs.getString("price")));
                    property.put(Constants.propertyType, rs.getInt("property_type"));
                    property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                    property.put(Constants.beds, rs.getInt("beds"));
                    property.put(Constants.bath, rs.getInt("bath"));
                    property.put("share_to_agents", rs.getInt("share_to_agents"));
                    property.put("commission", rs.getInt("commission"));
                    property.put(Constants.category, rs.getInt("category"));
                    property.put(Constants.developer, rs.getString("developer"));
                    property.put(Constants.footRange, rs.getInt("foot_range"));
                    property.put(Constants.yearBuilt, rs.getInt("year_built"));
                    property.put(Constants.furnished, rs.getInt("furnished"));
                    property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                    property.put(Constants.parkingType, rs.getString("parking_type"));
                    property.put(Constants.lotSize, rs.getInt("lot_size"));
                    property.put(Constants.listingType, rs.getInt("listing_type"));
                    property.put(Constants.houseType, rs.getInt("house_type"));
                    property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                    property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                    property.put(Constants.laundry, rs.getInt("laundry"));
                    property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                    property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                    property.put("country", Utilities.nullToEmpty(rs.getString("country")));
                    property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                    property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));

                    property.put(Constants.dateAvailable, Utilities.getDateFromStringRiyadh(rs.getTimestamp("date_available") + ""));
                    property.put(Constants.createdOn, rs.getTimestamp("created_on") + "");
                    property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                    property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                    property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                    property.put(Constants.crimeInfo, Utilities.nullToEmpty(rs.getString("crime_info")));
                    property.put(Constants.flood, Utilities.nullToEmpty(rs.getString("flood")));
                    property.put(Constants.seismic, Utilities.nullToEmpty(rs.getString("seismic")));
                    property.put("developer", Utilities.nullToEmpty(rs.getString("developer")));
                    property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                    JSONArray images = propertyImages(rs.getInt("id"), objConn);
                    if (images != null && images.length() > 0) {
                        property.put(Constants.images, images);
                    }
                    pstmt = objConn.prepareStatement(propertycat);
                    pstmt.setString(1, id);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        String cat = rs.getString("category");
                        property.put("property_features_mapping", cat);
                    }

                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return property;
    }

    public String activateProperty(String strTid, int nPropertyId, User user) throws SQLException, Exception {
        String propertyDetailsQuery = ConfigUtil.getProperty("property.contact.details.query", "SELECT * FROM property WHERE id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        String propertyName = "";

        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(propertyDetailsQuery);
                pstmt.setInt(1, nPropertyId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    propertyName = rs.getString("name");
                    if (rs.getInt("price") <= 0) {
                        return Utilities.prepareReponse(PRICE_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), PRICE_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                    }
                    if (StringUtils.isBlank(rs.getString("longitude"))) {
                        return Utilities.prepareReponse(ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                    }
                    if (StringUtils.isBlank(rs.getString("latitude"))) {
                        return Utilities.prepareReponse(ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                    }
                    if (StringUtils.isBlank(rs.getString("address"))) {
                        return Utilities.prepareReponse(ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), ADDRESS_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                    }

                    PropertyContactDetails contactDetails = getPropertyContactDetails(nPropertyId);
                    if (contactDetails != null) {
                        if (StringUtils.isBlank(contactDetails.getName())) {
                            return Utilities.prepareReponse(PROPERTY_HOLDER_NAME_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), PROPERTY_HOLDER_NAME_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                        }
                        if (StringUtils.isBlank(contactDetails.getEmail())) {
                            return Utilities.prepareReponse(EMAIL_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), EMAIL_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                        }
                        if (StringUtils.isBlank(contactDetails.getMobile())) {
                            return Utilities.prepareReponse(MOBILE_IS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), MOBILE_IS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                        }
                    } else {
                        return Utilities.prepareReponse(CONTACT_DEATILS_MANDATORY_TO_ACTIVATE_PROPERTY.getCode(), CONTACT_DEATILS_MANDATORY_TO_ACTIVATE_PROPERTY.DESC(), strTid);
                    }

                    //update the property status to Live-2
                    int nRes = updatePropertyStatus(nPropertyId, 2);
                    if (nRes == 1) {
                        //sending mail after activate property
                        try {
                            String activateProperty = ConfigUtil.getProperty("mail.property.activate", "Dear User, \n\n Thanks for register with Aquarbia....\n\n\n Thanks & Regards,\nAquarbia Support ");
                            if (user != null && (StringUtils.isNotBlank(user.getEmail()) && StringUtils.isNotBlank(user.getFirstname()))) {
                                activateProperty = activateProperty.replace("$(name)", user.getFirstname());
                                activateProperty = activateProperty.replace("$(propertyname)", propertyName);
                                activateProperty = activateProperty.replace("hostURL", url);
                                int nRes1 = objMail.sendHtmlMail(mailFrom, user.getEmail(), mailCC, mailBCC, signupMailSubject, activateProperty, new ArrayList());
                                if (logger.isDebugEnabled()) {
                                    logger.debug(" Mail send status => " + nRes1);
                                }
                            }
                        } catch (Exception e) {
                            logger.error("Sending mail failed" + Utilities.getStackTrace(e));
                        }

                        return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), strTid);
                    }
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while activateProperty" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while activateProperty" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    public PropertyContactDetails getPropertyContactDetails(int nPropertyId) {
        PropertyContactDetails contactDetails = null;
        String propertyDetailsQuery = ConfigUtil.getProperty("contact.details.query", "SELECT * FROM property_contact_details WHERE property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(propertyDetailsQuery);
                pstmt.setInt(1, nPropertyId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    contactDetails = new PropertyContactDetails();
                    contactDetails.setName(rs.getString("name"));
                    contactDetails.setCompany(rs.getString("company"));
                    contactDetails.setType(rs.getInt("type"));
                    contactDetails.setEmail(rs.getString("email"));
                    contactDetails.setShowEmail(rs.getInt("show_email"));
                    contactDetails.setMobile(rs.getString("mobile"));
                    contactDetails.setShowMobile(rs.getInt("show_mobile"));
                    contactDetails.setPropertyId(rs.getInt("property_id"));
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while activateProperty" + Utilities.getStackTrace(sqle));
            return null;
        } catch (Exception e) {
            logger.error(" Got Exception while activateProperty" + Utilities.getStackTrace(e));
            return null;
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }

        return contactDetails;
    }

    public JSONArray propertyImages(int propertyId, Connection objConn) throws SQLException, Exception {
        JSONArray array = new JSONArray();
        String propertyImagesQuery = ConfigUtil.getProperty("property.images.details.query", "SELECT * FROM images where property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        try {

            //  objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(propertyImagesQuery);
                pstmt.setInt(1, propertyId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    JSONObject image = new JSONObject();
                    image.put(Constants.id, Utilities.nullToEmpty(rs.getString("id")));
                    image.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    image.put(Constants.contentType, Utilities.nullToEmpty(rs.getString("contentType")));
                    image.put(Constants.fileName, Utilities.nullToEmpty(rs.getString("filename")));
                    image.put(Constants.imageLink, Utilities.nullToEmpty(url + rs.getString("image_link")));
                    array.put(image);
                }
            }

        } finally {
            if (objConn != null) {
                //    dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return array;
    }

    public String propertyIdByFeauters(int propertyId, Connection objConn) throws SQLException, Exception {
        JSONArray array = new JSONArray();
        String propertyImagesQuery = ConfigUtil.getProperty("property.features.by.id", "SELECT group_concat(category) as property_features   FROM property_features_mapping where property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        try {

            //  objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(propertyImagesQuery);
                pstmt.setInt(1, propertyId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("property_features");
                }
            }

        } finally {
            if (objConn != null) {
                rs = null;
                pstmt = null;
                //   dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return "";
    }

    public JSONArray mortgageDetails(int principleAmount) throws SQLException, Exception {
        JSONArray array = new JSONArray();
        String propertyImagesQuery = ConfigUtil.getProperty("mortgage.master.details.query", "SELECT * FROM mortgage_master_info");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;

        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(propertyImagesQuery);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    JSONObject object = new JSONObject();
                    object.put(Constants.id, rs.getInt("id"));
                    Double rateOfInterest = rs.getDouble("rate_of_interest");
                    if (rateOfInterest > 0) {
                        object.put(Constants.monthlyMortgage, Utilities.calculateMonthlyPayment(principleAmount, rs.getInt("no_of_years"), rateOfInterest));
                    } else {
                        object.put(Constants.monthlyMortgage, principleAmount / rs.getInt("no_of_years") / 12);
                    }

                    Double propertyTax = rs.getDouble("property_tax");
                    if (rateOfInterest > 0) {
                        object.put(Constants.propertyTax, (int) ((principleAmount * propertyTax) / 100));
                    } else {
                        object.put(Constants.propertyTax, 0);
                    }

                    Double insurenceTax = rs.getDouble("insurence_tax");
                    if (rateOfInterest > 0) {
                        object.put(Constants.insurenceTax, (int) ((principleAmount * insurenceTax) / 100));
                    } else {
                        object.put(Constants.insurenceTax, 0);
                    }
                    array.put(object);
//                    property.put(Constants.rateOfIntrest, nPrice / 20 / 12);
//                    property.put(Constants.propertyTax, 0);
//                    property.put(Constants.insurenceTax, (int) ((nPrice * 3) / 100));
                }
            }
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return array;
    }

    public String propertyCategoryDesc(int categoryId) throws SQLException, Exception {
        JSONArray array = new JSONArray();
        String propertyImagesQuery = ConfigUtil.getProperty("property.category.desc", "SELECT * FROM property_category WHERE id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(propertyImagesQuery);
                pstmt.setInt(1, categoryId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("name");
                }
            }

        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return "";
    }

    public JSONObject propertyContactDetails(int propertyId, Connection objConn) throws SQLException, Exception {
        JSONObject contactDetails = new JSONObject();
        String query = ConfigUtil.getProperty("property.contact.details.query", "SELECT * FROM property_contact_details where property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        try {

            // objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, propertyId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    contactDetails = new JSONObject();
                    contactDetails.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    contactDetails.put(Constants.company, Utilities.nullToEmpty(rs.getString("company")));
                    contactDetails.put(Constants.email, Utilities.nullToEmpty(rs.getString("email")));
                    contactDetails.put(Constants.showemail, rs.getInt("show_email"));
                    contactDetails.put(Constants.mobile, Utilities.nullToEmpty(rs.getString("mobile")));
                    contactDetails.put(Constants.showmobile, rs.getInt("show_mobile"));
                    contactDetails.put(Constants.type, rs.getInt("type"));
                }
            }

        } finally {
            if (objConn != null) {
                //   dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return contactDetails;
    }

    public int isPropertySaved(int userId, int nPropertyId, Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("is.user.saved.property.query", "SELECT * FROM user_saved_propertys where user_id=? and property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        //  Connection objConn = null;
        try {

            //  objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, nPropertyId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return 1;
                }
            }

        } finally {
            if (objConn != null) {
                pstmt = null;
                rs = null;
                //  dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return 0;
    }

    public Double getCurrencyValue(String currency, Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("get.currency.value.query", "SELECT multiplication_factor FROM currency_converter WHERE currency=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        //Connection objConn = null;
        try {
            //objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setString(1, currency);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String factor = rs.getString("multiplication_factor");
                    if (StringUtils.isNotBlank(factor)) {
                        return Double.parseDouble(factor);
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            logger.error("[getCurrencyValue] Got Exception while getting value for the Currency = >" + currency + " , Ex => " + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                //   dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return null;
    }

    public int addORUpdatePropertyDetails(PropertyBean property, String agent_id) throws Exception {
        String insertQuery = ConfigUtil.getProperty("store.property.data.query", "INSERT INTO `aqarabia`.`property`(`address`,`property_unit`,`city`,`state`,`country`,`pincode`,`category`,`latitude`,`longitude`) VALUES ( ?,?,?,?,?,?,?,?,?)");
        String insertAgentQuery = ConfigUtil.getProperty("store.agent.property.data.query", "INSERT INTO `aqarabia`.`agent_property_mapping`(`property_id`,`agent_id`) VALUES ( ?,?)");
        String updateQuery = ConfigUtil.getProperty("property.update.query", "update property set $(updateString) where id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {
                if (property.getId() <= 0) {

                    pstmt = objConn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, property.getAddress());
                    pstmt.setString(2, property.getPropertyUnit());
                    pstmt.setString(3, property.getCity());

                    pstmt.setString(4, property.getState());

                    pstmt.setString(5, property.getCountry());
                    pstmt.setString(6, property.getPincode());

                    pstmt.setString(7, property.getCategory());

                    pstmt.setString(8, property.getLatitude());
                    pstmt.setString(9, property.getLongitude());
                    //pstmt.setString(9, agent_id);

                    status = pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        status = rs.getInt(1);
                        property.setId(status);
                        pstmt = objConn.prepareStatement(insertAgentQuery);
                        pstmt.setInt(1, status);
                        pstmt.setString(2, agent_id);
                        int agent_mapping = pstmt.executeUpdate();
                        logger.debug("agent_mapping:" + agent_mapping);
                    }
                } else {
                    if (property.isIsPropertyContactDeatils()) {
                        addORUpdatePropertyContactDetails(property, objConn);
                    }
                    String updateString = " updated_on=now()";

                    if (StringUtils.isNotBlank(property.getPropertyName())) {
                        updateString = updateString + ",name='" + property.getPropertyName() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getAddress())) {
                        updateString = updateString + ",address='" + property.getAddress() + "'";
                    }

                    if (StringUtils.isNotBlank(property.getPropertyUnit())) {
                        updateString = updateString + ",property_unit='" + property.getPropertyUnit() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getCity())) {
                        updateString = updateString + ",city='" + property.getCity() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getState())) {
                        updateString = updateString + ",state='" + property.getState() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getPincode())) {
                        updateString = updateString + ",pincode='" + property.getPincode() + "'";
                    }
//                    if (StringUtils.isNotBlank(property.getForRent()) && !property.getForRent().equals("0")) {
//                        updateString = updateString + ",category='" + property.getPincode() + "'";
//                        
//                    }
                    if (property.getCategory() != null) {
                        updateString = updateString + ",category='" + property.getCategory() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getDescription())) {
                        updateString = updateString + ",description='" + property.getDescription() + "'";
                    }

                    if (property.getPrice() > 0) {
                        updateString = updateString + ",price=" + property.getPrice();
                    }
                    if (property.getDateAvailable() != null) {

                        Timestamp ts = Timestamp.valueOf(Utilities.getDateFromString(property.getDateAvailable()) + " 00:00:00");
                        updateString = updateString + ",date_available='" + ts + "'";

                    }
                    if (property.getBeds() > 0) {
                        updateString = updateString + ",beds='" + property.getBeds() + "'";
                    }
                    if (property.getShare_to_agents() >= 0) {
                        updateString = updateString + ",share_to_agents='" + property.getShare_to_agents() + "'";
                    }
                    if (property.getCommission() >= 0) {
                        updateString = updateString + ",commission='" + property.getCommission() + "'";
                    }
                    if (property.getBaths() > 0) {
                        updateString = updateString + ",bath='" + property.getBaths() + "'";
                    }
                    if (property.getPropertyType() > 0) {
                        updateString = updateString + ",property_type=" + property.getPropertyType();
                    }
                    if (property.getSquareFeet() > 0) {
                        updateString = updateString + ",foot_range=" + property.getSquareFeet();
                    }
                    if (property.getUnitFloor() > 0) {
                        updateString = updateString + ",lot_size=" + property.getUnitFloor();
                    }
                    if (property.getFurnished() > 0) {
                        updateString = updateString + ",furnished='" + property.getFurnished() + "'";
                    }
                    if (property.getParkingSpaces() > 0) {
                        updateString = updateString + ",parking_space=" + property.getParkingSpaces();
                    }
                    if (property.getYearBuilt() > 0) {
                        updateString = updateString + ",year_built=" + property.getYearBuilt();
                    }
                    if (property.getPetsAllowed() >= 0) {
                        updateString = updateString + ",pets_allowed='" + property.getPetsAllowed() + "'";
                    }
                    if (property.getLaundry() >= 0) {
                        updateString = updateString + ",laundry='" + property.getLaundry() + "'";
                    }
                    if (property.getBeds() > 0) {
                        updateString = updateString + ",beds='" + property.getBeds() + "'";
                    }
                    if (property.getCrime_info() > 0) {
                        updateString = updateString + ",crime_info='" + property.getCrime_info() + "'";
                    }

                    if (property.getFlood() > 0) {
                        updateString = updateString + ",flood='" + property.getFlood() + "'";
                    }

                    if (property.getSeismic() > 0) {
                        updateString = updateString + ",seismic='" + property.getSeismic() + "'";
                    }
                    if (property.getListingType() > 0) {
                        updateString = updateString + ",listing_type='" + property.getListingType() + "'";
                    }

                    if (StringUtils.isNotBlank(property.getLatitude())) {
                        updateString = updateString + ",latitude='" + property.getLatitude() + "'";
                    }

                    if (StringUtils.isNotBlank(property.getLongitude())) {
                        updateString = updateString + ",longitude='" + property.getLongitude() + "'";
                    }

                    if (StringUtils.isNotBlank(property.getParkingType())) {
                        updateString = updateString + ",parking_type='" + property.getParkingType() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getDeveloper())) {
                        updateString = updateString + ",developer='" + property.getDeveloper() + "'";
                    }
                    updateQuery = updateQuery.replaceAll("\\$\\(updateString\\)", updateString);
                    System.out.println("updateQuery:" + updateQuery);
                    pstmt = objConn.prepareStatement(updateQuery);
                    pstmt.setInt(1, property.getId());
                    int nRes = pstmt.executeUpdate();
                    if (nRes == 1) {
                        if (StringUtils.isNotBlank(property.getPropertycat())) {
                            String[] catType = property.getPropertycat().split(",");
                            if (catType.length > 0) {
                                pstmt = objConn.prepareStatement("DELETE FROM `aqarabia`.`property_features_mapping` WHERE property_id=?");
                                pstmt.setInt(1, property.getId());
                                nRes = pstmt.executeUpdate();
                            }
                            for (String cat : catType) {
                                if (StringUtils.isNotBlank(cat)) {
                                    pstmt = objConn.prepareStatement("INSERT INTO `aqarabia`.`property_features_mapping`(`property_id`,`category`) VALUES (?,?)");
                                    pstmt.setInt(1, property.getId());
                                    pstmt.setString(2, cat);
                                    nRes = pstmt.executeUpdate();
                                }
                            }
                        }
                        return 1;
                    } else {
                        return -1;
                    }
                }
            } else {
                logger.error("addUserDetails(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public int addORUpdatePropertyContactDetails(PropertyBean property, Connection objConn) throws Exception {
        String insertQuery = ConfigUtil.getProperty("store.property.contact.details.query", "INSERT INTO `aqarabia`.`property_contact_details`(`name`,`company`,`email`,`mobile`,`property_id`,`show_email`,`show_mobile`) VALUES ( ?,?,?,?,?,?,?)");
        String updateQuery = ConfigUtil.getProperty("property.contact.details.update.query", "update `aqarabia`.`property_contact_details` set $(updateString) where property_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        int status = 0;
        try {
            // objConn = dbconnection.getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement("select * from `aqarabia`.`property_contact_details` where property_id=" + property.getId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String updateString = "";

                    if (StringUtils.isNotBlank(property.getName())) {
                        updateString = updateString + "name='" + property.getName() + "'";
                    }

                    if (StringUtils.isNotBlank(property.getCompany())) {
                        updateString = updateString + ",company='" + property.getCompany() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getEmail())) {
                        updateString = updateString + ",email='" + property.getEmail() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getPhone())) {
                        updateString = updateString + ",mobile='" + property.getPhone() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getShowEmail())) {
                        updateString = updateString + ",show_email='" + property.getShowEmail() + "'";
                    }
                    if (StringUtils.isNotBlank(property.getShowMobile())) {
                        updateString = updateString + ",show_mobile='" + property.getShowMobile() + "'";
                    }

                    updateQuery = updateQuery.replaceAll("\\$\\(updateString\\)", updateString);

                    pstmt = objConn.prepareStatement(updateQuery);
                    pstmt.setInt(1, property.getId());
                    int nRes = pstmt.executeUpdate();
                    if (nRes == 1) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {

                    pstmt = objConn.prepareStatement(insertQuery);
                    pstmt.setString(1, property.getName());
                    pstmt.setString(2, property.getCompany());
                    pstmt.setString(3, property.getEmail());
                    pstmt.setString(4, property.getPhone());
                    pstmt.setInt(5, property.getId());
                    pstmt.setString(6, property.getShowEmail());
                    pstmt.setString(7, property.getShowMobile());
                    status = pstmt.executeUpdate();

                }
            } else {
                logger.error("addORUpdatePropertyContactDetails(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("addORUpdatePropertyContactDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addORUpdatePropertyContactDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                //  dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public String propertySearch(int minPrice, int maxPrice, String propertyType, int nCategory, int beds, int baths, int minsFeetRange, int maxsFeetRange, int petsAllowed, String keywords, int lotSize, String fromYear, String toYear, int daysInAqarabia, String listingType,
            String showOnly, String mls, int soldInMonths, String sortBy, String orderBy, int nPropertyCategory, String transId, String currency, int nUserId, int nIsSquareMeter, int min, int max, String latitude, String longitude, String strRadius, String xParams, String eminitieKeywords) throws SQLException, Exception {

        String query = ConfigUtil.getProperty("property.search.query", "select ( 3959 * ACOS( COS( RADIANS($(lat)) ) * COS( RADIANS( latitude ) ) * COS( RADIANS( longitude ) - RADIANS($(long)) ) + SIN( RADIANS($(lat)) ) * SIN( RADIANS( latitude ) ) ) ) AS distance,p.* from property p ");
        String AND = " AND ";
        String OR = " OR ";
        Connection objConn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        JSONObject objFinalResponse = new JSONObject();
        try {
            propertylock4.lock();
            objConn = DBConnection.getInstance().getConnection();
            if (nPropertyCategory > 0) {
                query = query + " where listing_type=? ";
                if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                    query = query + " HAVING distance < " + strRadius;
                }
            } else if (nCategory == Constants.SALE) {
                nCategory = Constants.SALE;
                query = query + " where category=? ";

                if (minPrice > 0 && maxPrice > 0) {
                    if (StringUtils.isNotBlank(currency) && !currency.equalsIgnoreCase("USD")) {
                        Double factor = getCurrencyValue(currency, objConn);
                        Double mPrice = (minPrice / factor);
                        minPrice = mPrice.intValue();
                        Double xPrice = (maxPrice / factor);
                        maxPrice = xPrice.intValue();
                    }
                    query = query + AND + " (price>=" + minPrice + OR + "price<=" + maxPrice + ") ";
                }

                if (StringUtils.isNotBlank(propertyType)) {
                    query = query + AND + " property_type in (" + propertyType + ") ";
                }

                if (beds > 0) {
                    query = query + AND + " beds>=" + beds;
                }
                if (baths > 0) {
                    query = query + AND + " bath>=" + baths;
                }
                if (minsFeetRange > 0 && maxsFeetRange > 0) {
                    query = query + AND + " (foot_range>=" + minsFeetRange + OR + "foot_range<=" + maxsFeetRange + ") ";
                }
                if (lotSize > 0) {
                    query = query + AND + " lot_size>=" + lotSize;
                }

                if (StringUtils.isNotBlank(fromYear) && StringUtils.isNotBlank(toYear)) {
                    query = query + AND + " (year>=" + fromYear + OR + "year<=" + toYear + ") ";
                }
                if (StringUtils.isNotBlank(listingType)) {
                    query = query + AND + " listing_type in (" + listingType + ") ";

                }
                if (StringUtils.isNotBlank(showOnly)) {
                    query = query + AND + " house_type in (" + showOnly + ") ";

                }
                if (StringUtils.isNotBlank(keywords)) {
                    query = query + AND + " keywords like '%" + keywords + "%' ";
                }

                if (StringUtils.isNotBlank(xParams)) {
                    query = query + AND + " (city like '%" + xParams + "%' OR state like '%" + xParams + "%' OR address like '%" + xParams + "%' ) ";
                }

                if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                    query = query + " HAVING distance < " + strRadius;
                }
                if (StringUtils.isNotBlank(sortBy) && StringUtils.isNotBlank(orderBy)) {
                    query = query + " ORDER BY " + sortBy + " " + orderBy;
                }

            } else if (nCategory == Constants.RENT) {

                nCategory = Constants.RENT;
                query = query + " where category=? ";

                if (minPrice > 0 && maxPrice > 0) {
                    if (StringUtils.isNotBlank(currency) && !currency.equalsIgnoreCase("USD")) {
                        Double factor = getCurrencyValue(currency, objConn);
                        Double mPrice = (minPrice / factor);
                        minPrice = mPrice.intValue();
                        Double xPrice = (maxPrice / factor);
                        maxPrice = xPrice.intValue();
                    }
                    query = query + AND + " (price>=" + minPrice + OR + "price<=" + maxPrice + ") ";
                }

                if (StringUtils.isNotBlank(propertyType)) {
                    query = query + AND + " property_type in (" + propertyType + ") ";

                }

                if (beds > 0) {
                    query = query + AND + " beds>=" + beds;
                }
                if (baths > 0) {
                    query = query + AND + " bath>=" + baths;
                }
                if (minsFeetRange > 0 && maxsFeetRange > 0) {
                    query = query + AND + " (foot_range>=" + minsFeetRange + OR + "foot_range<=" + maxsFeetRange + ") ";
                }
                if (petsAllowed > 0) {
                    query = query + AND + " pets_allowed>=" + petsAllowed;
                }

                if (StringUtils.isNotBlank(keywords)) {
                    query = query + AND + " keywords like '%" + keywords + "%' ";
                }

                if (StringUtils.isNotBlank(xParams)) {
                    query = query + AND + " (city like '%" + xParams + "%' OR state like '%" + xParams + "%' OR address like '%" + xParams + "%' ) ";
                }
                if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                    query = query + " HAVING distance < " + strRadius;
                }

                if (StringUtils.isNotBlank(sortBy) && StringUtils.isNotBlank(orderBy)) {
                    query = query + " ORDER BY " + sortBy + " " + orderBy;
                }

            } else if (nCategory == Constants.SOLD) {
                nCategory = Constants.SOLD;
                query = query + " where category=? ";

                if (minPrice > 0 && maxPrice > 0) {
                    if (StringUtils.isNotBlank(currency) && !currency.equalsIgnoreCase("USD")) {
                        Double factor = getCurrencyValue(currency, objConn);
                        Double mPrice = (minPrice / factor);
                        minPrice = mPrice.intValue();
                        Double xPrice = (maxPrice / factor);
                        maxPrice = xPrice.intValue();
                    }
                    query = query + AND + " (price>=" + minPrice + OR + "price<=" + maxPrice + ") ";
                }

                if (StringUtils.isNotBlank(propertyType)) {
                    query = query + OR + " property_type in (" + propertyType + ") ";
                }

                if (beds > 0) {
                    query = query + AND + " beds>=" + beds;
                }
                if (baths > 0) {
                    query = query + AND + " bath>=" + baths;
                }
                if (minsFeetRange > 0 && maxsFeetRange > 0) {
                    query = query + AND + " (foot_range>=" + minsFeetRange + OR + "foot_range<=" + maxsFeetRange + ") ";
                }

                if (StringUtils.isNotBlank(fromYear) && StringUtils.isNotBlank(toYear)) {
                    query = query + AND + " (year>=" + fromYear + AND + "year<=" + toYear + ") ";
                }

                if (lotSize > 0) {
                    query = query + AND + " lot_size>=" + lotSize;
                }

                if (StringUtils.isNotBlank(keywords)) {
                    query = query + AND + " keywords like '%" + keywords + "%' ";
                }

                if (StringUtils.isNotBlank(xParams)) {
                    query = query + AND + " (city like '%" + xParams + "%' OR state like '%" + xParams + "%' OR address like '%" + xParams + "%' ) ";
                }
                if (StringUtils.isNotBlank(strRadius) && !"0".equalsIgnoreCase(strRadius)) {
                    query = query + " HAVING distance < " + strRadius;
                }

                if (StringUtils.isNotBlank(sortBy) && StringUtils.isNotBlank(orderBy)) {
                    query = query + " ORDER BY " + sortBy + " " + orderBy;
                }

            } else {
                logger.error("Invalid  category =>" + nCategory);
                return Utilities.prepareReponse(INVALID_PROPERTY_CATEGORY.getCode(), INVALID_PROPERTY_CATEGORY.DESC(), transId);
            }

            query = query + " LIMIT " + min + "," + max;

            try {

                JSONObject objRequest = new JSONObject();
                objRequest.put("code", "1000");
                objRequest.put("transid", transId);

                if (objConn != null) {

                    //latitude ,longitude and radius values are updating to query
                    int nCrimeRate = 0;
                    int nFloodRate = 0;
                    int nSeismicRate = 0;
                    try {

                        query = query.replaceAll("\\$\\(lat\\)", latitude);
                        query = query.replaceAll("\\$\\(long\\)", longitude);

                        String crimeQuery = query;
                        crimeQuery = crimeQuery.replaceAll("p.* from", "max(crime_info) as crime_info,max(flood) as flood,max(seismic) as seismic from ");
                        pstmt = objConn.prepareStatement(crimeQuery);
                        if (nPropertyCategory > 0) {
                            pstmt.setInt(1, nPropertyCategory);
                        } else {
                            pstmt.setInt(1, nCategory);
                        }
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            nCrimeRate = rs.getInt("crime_info");
                            nFloodRate = rs.getInt("flood");
                            nSeismicRate = rs.getInt("seismic");
                        }
                    } catch (Exception e) {
                    }
                    pstmt = objConn.prepareStatement(query);
                    if (nPropertyCategory > 0) {
                        pstmt.setInt(1, nPropertyCategory);
                    } else {
                        pstmt.setInt(1, nCategory);
                    }
                    rs = pstmt.executeQuery();
                    JSONArray propertyArray = new JSONArray();
                    while (rs.next()) {
                        JSONObject property = new JSONObject();
                        property.put(Constants.propertyId, rs.getString(Constants.id));
                        property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                        property.put(Constants.description, Utilities.nullToEmpty(rs.getString("description")));
                        String price = rs.getString("price");
                        int nPrice = 0;
                        if ((StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(price)) && !currency.equalsIgnoreCase("USD")) {
                            Double dPrice = Double.parseDouble(price);
                            if (dPrice > 0) {
                                Double factor = getCurrencyValue(currency, objConn);
                                Double finalPrice = 0d;
                                if (factor != null) {
                                    finalPrice = dPrice * factor;
                                }
                                nPrice = finalPrice.intValue();
                            }
                        } else {
                            nPrice = Integer.parseInt(price);
                        }
                        property.put(Constants.crimeRate, nCrimeRate + "");
                        property.put(Constants.floodRate, nFloodRate + "");
                        property.put(Constants.seismicRate, nSeismicRate + "");
                        property.put(Constants.rateOfIntrest, nPrice / 20 / 12);
                        property.put(Constants.propertyTax, 0);
                        property.put(Constants.insurenceTax, (int) ((nPrice * 3) / 100));
                        property.put(Constants.price, nPrice + "");
                        property.put(Constants.propertyType, rs.getInt("property_type"));
                        property.put(Constants.propertyUnit, Utilities.nullToEmpty(rs.getString("property_unit")));
                        property.put(Constants.beds, rs.getInt("beds"));
                        property.put(Constants.bath, rs.getInt("bath"));
                        property.put(Constants.category, rs.getInt("category"));
                        property.put(Constants.developer, rs.getString("developer"));
                        if (nIsSquareMeter == 1) {
                            property.put(Constants.footRange, (int) (rs.getInt("foot_range") * oneSquareMeter));
                        } else {
                            property.put(Constants.footRange, rs.getInt("foot_range"));
                        }

                        //Natural disasters
                        JSONObject naturalDisasters = new JSONObject();
                        naturalDisasters.put(Constants.flood, rs.getInt("flood"));
                        naturalDisasters.put(Constants.seismic, rs.getInt("seismic"));
                        property.put(Constants.crimeInfo, rs.getInt("crime_info"));
                        property.put(Constants.naturalDisasters, naturalDisasters);
                        property.put(Constants.yearBuilt, rs.getInt("year_built"));
                        property.put(Constants.furnished, rs.getInt("furnished"));
                        property.put(Constants.parkingSpace, Utilities.nullToEmpty(rs.getString("parking_space")));
                        property.put(Constants.parkingType, rs.getString("parking_type"));
                        property.put(Constants.lotSize, rs.getInt("lot_size"));
                        property.put(Constants.listingType, rs.getInt("listing_type"));
                        property.put(Constants.houseType, rs.getInt("house_type"));
                        property.put(Constants.keywords, Utilities.nullToEmpty(rs.getString("keywords")));
                        property.put(Constants.petsAllowed, rs.getInt("pets_allowed"));
                        property.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                        property.put(Constants.state, Utilities.nullToEmpty(rs.getString("state")));
                        property.put(Constants.address, Utilities.nullToEmpty(rs.getString("address")));
                        property.put(Constants.neighborhood, Utilities.nullToEmpty(rs.getString("neighborhood")));
                        property.put(Constants.pincode, Utilities.nullToEmpty(rs.getString("pincode")));
                        property.put(Constants.dateAvailable, rs.getTimestamp("date_available") + "");
                        property.put(Constants.createdOn, Utilities.getDateFromStringRiyadh(rs.getString("created_on")));
                        property.put(Constants.soldOn, rs.getTimestamp("updated_on") + "");
                        property.put(Constants.latitude, Utilities.nullToEmpty(rs.getString("latitude")));
                        property.put(Constants.longitude, Utilities.nullToEmpty(rs.getString("longitude")));
                        // need to get property is saved or not
                        if (nUserId > 0) {
                            property.put(Constants.propertySaved, isPropertySaved(nUserId, rs.getInt("id"), objConn));
                        } else {
                            property.put(Constants.propertySaved, 0);
                        }

                        int nDays = Utilities.toInt(ConfigUtil.getProperty("property.new.in.days", "15"), 15);
                        int days = (int) Utilities.getDifferenceDays(rs.getDate("created_on"), new Date());

                        if (nDays >= days) {
                            property.put(Constants.isPropertyNew, 1);
                        } else {
                            property.put(Constants.isPropertyNew, 0);
                        }
                        property.put("propertyContactDetails", propertyContactDetails(rs.getInt("id"), objConn));
                        JSONArray images = propertyImages(rs.getInt("id"), objConn);
                        if (images != null && images.length() > 0) {
                            property.put(Constants.images, images);
                        }
                        propertyArray.put(property);
                    }
                    objRequest.put("properties", propertyArray);
                    objFinalResponse.put("response", objRequest);
                }
            } catch (SQLException sqle) {
                logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
                throw new SQLException(sqle);
            } catch (Exception e) {
                logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
                throw new Exception(e);
            }
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
            propertylock4.unlock();
        }
        // NEIGHBOR HOOD INFO
        if (StringUtils.isNotBlank(eminitieKeywords)) {
            JSONObject neighborhoodInfo = neighborhoodInfo(latitude, longitude, strRadius, eminitieKeywords, sortBy);
            if (neighborhoodInfo != null) {
                objFinalResponse.put("neighbourhoodinfo", neighborhoodInfo);
            }
        }
        return objFinalResponse.toString();
    }

    public String getPropertyTypes(int nCategory, String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("propertytype.details.query", "SELECT * FROM property_type where category=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nCategory);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("propertyTypes", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getListingTypes(int nCategory, String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("listingtypes.details.query", "SELECT * FROM listing_type where category=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nCategory);
                rs = pstmt.executeQuery();
                JSONArray listingTypeArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    listingTypeArray.put(property);
                }
                objRequest.put("listingTypes", listingTypeArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getStates(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("get.states.query", "SELECT * FROM state");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray statesArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("state")));
                    statesArray.put(property);
                }
                objRequest.put("states", statesArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getCities(int nState, String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("get.states.query", "SELECT * FROM city where state_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nState);
                rs = pstmt.executeQuery();
                JSONArray statesArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("city")));
                    statesArray.put(property);
                }
                objRequest.put("cities", statesArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getPropertyTypesAdmin(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("propertytype.details.query.admin", "SELECT * FROM property_type");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("propertyTypes", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getPropertyExpertise(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("propertyexpertise.details.query", "SELECT * FROM property_expertise");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("propertyExpertise", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getAgentCertifications(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.certifications.details.query", "SELECT * FROM certifications");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("certifications", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getAgentTypes(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agenttypes.details.query", "SELECT * FROM agent_type");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("agentTypes", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getAgentSpeciality(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.speciality.details.query", "SELECT * FROM agent_specialty");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("certifications", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getAgentLanguages(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.languages.details.query", "SELECT * FROM agent_languages");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("language")));
                    propertyArray.put(property);
                }
                objRequest.put("languages", propertyArray);
                objFinalResponse.put("response", objRequest);
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String agentSearch(int minPrice, int maxPrice, String location, int agentType, String keywords, String propertyExpertise, String agentSpecialty, String languages, String transId) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.search.query", "SELECT * FROM agent_details ad ,users u WHERE ad.user_id=u.id ");
        String AND = " AND ";
        String OR = " OR ";
        int nCategory = 0;
        if (agentType > 0) {
            if (agentType > 0) {
                query = query + AND + " ad.agent_type =? ";
            }

            if (StringUtils.isNotBlank(location)) {
                query = query + AND + "( u.city like '%" + location + "%')";
            }

            if (minPrice > 0 && maxPrice > 0) {
                query = query + AND + " (ad.price>=" + minPrice + OR + "ad.price<=" + maxPrice + ") ";
            }

            if (StringUtils.isNotBlank(keywords)) {
                query = query + AND + "ad.keywords like '%" + keywords + "%' ";
            }

            if (StringUtils.isNotBlank(propertyExpertise)) {
                query = query + AND + " property_expertise in (" + propertyExpertise + ") ";
            }
            if (StringUtils.isNotBlank(agentSpecialty)) {
                query = query + AND + " agent_specialty in (" + agentSpecialty + ") ";
            }
            if (StringUtils.isNotBlank(languages)) {
                query = query + AND + " languages in (" + languages + ") ";
            }

        } else {
            logger.error("Invalid agent type =>" + agentType);
            return Utilities.prepareReponse(INVALID_AGENT_TYPE.getCode(), INVALID_AGENT_TYPE.DESC(), transId);
        }
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", transId);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, agentType);
                rs = pstmt.executeQuery();
                JSONArray agentArray = new JSONArray();
                while (rs.next()) {
                    JSONObject agent = new JSONObject();
                    agent.put(Constants.name, Utilities.nullToEmpty(rs.getString("firstname")));
                    agent.put(Constants.company, Utilities.nullToEmpty(rs.getString("company")));
                    agent.put(Constants.agentId, rs.getInt("ad.id"));
                    agent.put(Constants.about, Utilities.nullToEmpty(rs.getString("ad.description")));
                    agent.put(Constants.recommondation, rs.getInt("ad.recommandations"));
                    agent.put(Constants.totalreviews, agentReviews(rs.getInt("ad.id"), 1, transId, objConn));
                    agent.put(Constants.agentType, rs.getInt("agent_type"));
                    agent.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    String profilepic = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profilepic)) {
                        agent.put(Constants.profilePicture, url + profilepic);
                    }
                    // agent.put(Constants.profilePicture, Utilities.nullToEmpty(rs.getString("profile_picture")));
                    agent.put(Constants.soldHomes, getSoldHomesCountAgainistToAgent(rs.getInt("ad.id"), objConn));
                    agent.put(Constants.activeHomes, getActiveHomesCountAgainistToAgent(rs.getInt("ad.id"), objConn));
                    agent.put(Constants.certifications, rs.getInt("certifications"));
                    agent.put(Constants.prices, getAgentSoldPropertiesMinAvgPrices(rs.getInt("ad.id")));
                    agentArray.put(agent);
                }
                objRequest.put("agents", agentArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String agentList(String transId, int nAgentId, int nPropertyId) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.search.query", "SELECT * FROM agent_details ad ,users u WHERE ad.user_id=u.id ");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {
            if (nAgentId > 0) {
                query = query + " and ad.id=?";
            } else if (nPropertyId > 0) {
                query = ConfigUtil.getProperty("agent.details.by.propertyid.query", "SELECT * FROM agent_details ad ,users u WHERE ad.id IN(SELECT agent_id FROM agent_property_mapping WHERE property_id=?) AND ad.user_id=u.id");
            }
            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", transId);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                if (nAgentId > 0) {
                    pstmt.setInt(1, nAgentId);
                } else if (nPropertyId > 0) {
                    pstmt.setInt(1, nPropertyId);
                }
                rs = pstmt.executeQuery();
                JSONArray agentArray = new JSONArray();
                while (rs.next()) {
                    JSONObject agent = new JSONObject();
                    agent.put(Constants.name, Utilities.nullToEmpty(rs.getString("firstname")));
                    agent.put(Constants.company, Utilities.nullToEmpty(rs.getString("company")));
                    agent.put(Constants.agentType, rs.getInt("agent_type"));
                    agent.put(Constants.agentId, rs.getInt("ad.id"));
                    agent.put(Constants.about, Utilities.nullToEmpty(rs.getString("ad.description")));
                    agent.put(Constants.recommondation, rs.getInt("ad.recommandations"));
                    agent.put(Constants.totalreviews, agentReviews(rs.getInt("ad.id"), 1, transId, objConn));
                    agent.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    String profilepic = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profilepic)) {
                        agent.put(Constants.profilePicture, url + profilepic);
                    }
                    // agent.put(Constants.profilePicture, Utilities.nullToEmpty(rs.getString("profile_picture")));
                    agent.put(Constants.soldHomes, getSoldHomesCountAgainistToAgent(rs.getInt("ad.id"), objConn));
                    agent.put(Constants.phone, getAgentPhoneNumber(rs.getInt("ad.id"), objConn));
                    agent.put(Constants.agentRating, getAgentReview(rs.getInt("ad.id"), objConn));
                    agent.put(Constants.activeHomes, getActiveHomesCountAgainistToAgent(rs.getInt("ad.id"), objConn));
                    agent.put(Constants.certifications, rs.getInt("certifications"));
                    agent.put(Constants.prices, getAgentSoldPropertiesMinAvgPrices(rs.getInt("ad.id")));
                    agentArray.put(agent);
                }
                objRequest.put("agents", agentArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public int getActiveHomesCountAgainistToAgent(int nAgentId, Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.active.homes", "SELECT COUNT(*) AS active_homes FROM property p, agent_property_mapping apm WHERE apm.agent_id=? AND p.status=1 AND p.category NOT IN (3) AND p.share_to_agents=1");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        int nActiveHomes = 0;
        try {
            //  objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    nActiveHomes = rs.getInt("active_homes");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                //   dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return nActiveHomes;
    }

    public JSONObject getAgentSoldPropertiesMinAvgPrices(int nAgentId) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.sold.propertes.min.avg.max.prices.query", "SELECT MIN(price) minPrice,AVG(price) as avgPrice ,MAX(price) as maxPrice FROM property WHERE category=3 AND agent_id=?");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject object = new JSONObject();

        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    object.put("minPrice", rs.getInt("minPrice"));
                    object.put("avgPrice", rs.getInt("avgPrice"));
                    object.put("maxPrice", rs.getInt("maxPrice"));
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return object;
    }

    public int getSoldHomesCountAgainistToAgent(int nAgentId, Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.sold.homes.count.query", "select count(*) as sold_homes from property where agent_id=? and category=3");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        int nActiveHomes = 0;
        try {
            //objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    nActiveHomes = rs.getInt("sold_homes");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                //  dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return nActiveHomes;
    }

    public String getAgentPhoneNumber(int nAgentId, Connection objConn) {
        String query = ConfigUtil.getProperty("agent.phone.details.query", "SELECT u.phone FROM  users u,agent_details ad WHERE ad.user_id=u.id AND ad.id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        //Connection objConn = null;
        String phone = "";
        try {
            //  objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    phone = rs.getString("phone");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getAgentPhoneNumber" + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error(" Got Exception while getAgentPhoneNumber" + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                //     dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return phone;
    }

    public int getAgentReview(int nAgentId, Connection objConn) {
        String query = ConfigUtil.getProperty("agent review.query", " SELECT MAX(rating) as rating FROM agent_reviews WHERE agent_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        // Connection objConn = null;
        int nActiveHomes = 0;
        try {
            // objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    nActiveHomes = rs.getInt("rating");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error(" Got Exception while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                //   dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return nActiveHomes;
    }

    public String agentTotalReviews(int nAgentId, int nLimit, String transId) throws SQLException, Exception {

        JSONObject objRequest = new JSONObject();
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            objRequest.put("code", "1000");
            objRequest.put("transid", transId);
            objRequest.put("response", agentReviews(nAgentId, nLimit, transId, objConn));
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(null, null, objConn);
            }
        }
        return objRequest.toString();
    }

    public JSONObject agentReviews(int nAgentId, int nLimit, String transId, Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.reviews.count.query", "SELECT count(*) as reviews_count FROM agent_reviews where agent_id=? ");
        String query1 = ConfigUtil.getProperty("agent.reviews.query", "SELECT * FROM agent_reviews where agent_id=? order by created_on desc ");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        //Connection objConn = null;
        JSONObject objRequest = new JSONObject();
        try {
            // objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();

                int count = 0;
                if (rs.next()) {
                    count = rs.getInt("reviews_count");
                }
                objRequest.put("total_reviews_count", count);
                JSONArray agentArray = new JSONArray();
                if (nLimit > 0) {
                    query1 = query1 + " limit " + nLimit;
                }
                pstmt = objConn.prepareStatement(query1);
                pstmt.setInt(1, nAgentId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    JSONObject agent = new JSONObject();
                    agent.put(Constants.comment, Utilities.nullToEmpty(rs.getString("comment")));
                    agent.put(Constants.rating, rs.getInt("rating"));
                    agent.put(Constants.userName, rs.getInt("user_id"));
                    agent.put(Constants.datetime, rs.getInt("created_on"));
                    agentArray.put(agent);
                }
                objRequest.put("reviews", agentArray);
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                //  dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objRequest;
    }

    public int addHomeWorth(String address, String longitude, String latitude, int nBeds, int nBaths, int nPropertyType, int nSellInMonths, String mobile, String email, String name, String transId) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("property.add.homeworth.query", "INSERT INTO home_worth(NAME,email,mobile,address,property_type,beds,baths,latitude,longitude,sell_in_months) VALUES(?,?,?,?,?,?,?,?,?,?)");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);

                pstmt.setString(1, name.trim());
                pstmt.setString(2, email.trim());
                pstmt.setString(3, mobile.trim());
                pstmt.setString(4, address.trim());
                pstmt.setInt(5, nPropertyType);
                pstmt.setInt(6, nBeds);
                pstmt.setInt(7, nBaths);
                pstmt.setString(8, latitude.trim());
                pstmt.setString(9, longitude.trim());
                pstmt.setInt(10, nSellInMonths);

                int nRes = pstmt.executeUpdate();
                //Sending mail to user

//                if (nRes == Constants.INSERT_RECORD_SUCCESSFULLY) {
//                    String mailContent = ConfigUtil.getProperty("whatsmyhome.worth.mail.content", "Dear $(name), \n\n Thank you for contacting Aquarbia,Will contact you soon....\n\n\n Thanks & Regards,\nAquarbia Support ");
//                    String mailSubject = ConfigUtil.getProperty("whatsmyhome.worth.mail.subject", "WhatsMyHomeWorth");
//                    mailContent = mailContent.replaceAll("\\$\\(name\\)", name);
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("[WhatsMyhomeworth] Mail sending to user => " + email);
//                    }
//                    nRes = objMail.sendHtmlMail(mailFrom, email, mailCC, mailBCC, mailSubjectForForgotPwd, mailContent, new ArrayList());
//                }
                return nRes;
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while addHomeWorth" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while addHomeWorth" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return -1;
    }

    public JSONArray adminAgentList(String transId, int fromIndex, int endIndex) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.search.query", "SELECT * FROM agent_details ad ,users u WHERE ad.user_id=u.id ORDER BY ad.created_on DESC ");
        query = query + " LIMIT " + fromIndex + "," + endIndex;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray agentArray = new JSONArray();
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    JSONObject agent = new JSONObject();
                    agent.put(Constants.id, rs.getInt("id"));
                    agent.put(Constants.name, Utilities.nullToEmpty(rs.getString("firstname")));
                    agent.put(Constants.company, Utilities.nullToEmpty(rs.getString("company")));
                    agent.put(Constants.agentType, rs.getInt("agent_type"));
                    agent.put(Constants.city, Utilities.nullToEmpty(rs.getString("city")));
                    String profilepic = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profilepic)) {
                        agent.put(Constants.profilePicture, url + profilepic);
                    }
                    //  agent.put(Constants.profilePicture, Utilities.nullToEmpty(rs.getString("profile_picture")));
                    agent.put(Constants.soldHomes, rs.getInt("agent_type"));
                    agent.put(Constants.activeHomes, rs.getInt("agent_type"));
                    agent.put(Constants.certifications, rs.getInt("certifications"));
                    agentArray.put(agent);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return agentArray;
    }

    public int adminAgentListCountString(String transId) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agent.search.count.query", "SELECT count(*) as count FROM agent_details ad ,users u WHERE ad.user_id=u.id ");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray agentArray = new JSONArray();
        int count = 0;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("count");
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getUserDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getUserDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public int addORUpdateAgentDetails(String profile_picture, String name, String email, String phone, String city, String state, String password, String user_id) throws Exception {
        String updateAgentQuery = ConfigUtil.getProperty("user.update.data.query", "update users set $(updateString) where user_id=?");
        String updateQuery = ConfigUtil.getProperty("property.update.agent.details.query", "update agent_details set $(updateString) where user_id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {

                String updateString = " updated_on=now()";

                if (StringUtils.isNotBlank(profile_picture)) {
                    updateString = updateString + " ,profile_picture='" + profile_picture + "'";
                }
                if (StringUtils.isNotBlank(email)) {
                    updateString = updateString + ",email='" + email + "'";
                }

                if (StringUtils.isNotBlank(phone)) {
                    updateString = updateString + ",phone='" + phone + "'";
                }
                if (StringUtils.isNotBlank(name)) {
                    updateString = updateString + ",firstname='" + name + "'";
                }
                if (StringUtils.isNotBlank(city)) {
                    updateString = updateString + ",city='" + city + "'";
                }
                if (StringUtils.isNotBlank(state)) {
                    updateString = updateString + ",state='" + state + "'";
                }
                if (StringUtils.isNotBlank(password)) {
                    updateString = updateString + ",password='" + AESAlgo.encrypt(password) + "'";
                }

                updateAgentQuery = updateAgentQuery.replaceAll("\\$\\(updateString\\)", updateString);
                System.out.println("updateQuery:" + updateAgentQuery);
                pstmt = objConn.prepareStatement(updateAgentQuery);
                pstmt.setString(1, user_id);
                int nRes = pstmt.executeUpdate();
                if (nRes == 1) {
                    pstmt = objConn.prepareCall("select id from users where user_id=?");
                    pstmt.setString(1, user_id);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        return 1;
//                        String id = rs.getString("id");
//                        if (StringUtils.isNotBlank(companyname)) {
//                            updateString = " company='" + companyname + "'";
//                            updateQuery = updateQuery.replaceAll("\\$\\(updateString\\)", updateString);
//                            System.out.println("updateQuery:" + updateQuery);
//                            pstmt = objConn.prepareStatement(updateQuery);
//                            pstmt.setString(1, id);
//                            nRes = pstmt.executeUpdate();
//                            return 1;
//                        }
                    } else {
                        return -1;
                    }

                } else {
                    return -1;
                }

            } else {
                logger.error("addUserDetails(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public String getAgentDetails(String userId, String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("user.agent.details.query", "SELECT u.phone,u.firstname,u.password, ad.company,u.email,u.user_id,u.city,u.state,u.profile_picture \n"
                + "FROM users u\n"
                + ", agent_details ad\n"
                + "where u.id=ad.user_id AND u.user_id =?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setString(1, userId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    objFinalResponse.put("firstname", rs.getString("firstname"));
                    objFinalResponse.put("password", AESAlgo.decrypt(rs.getString("password")));
                    objFinalResponse.put("company", rs.getString("company"));
                    objFinalResponse.put("email", rs.getString("email"));
                    objFinalResponse.put("user_id", rs.getString("user_id"));
                    objFinalResponse.put("city", rs.getString("city"));
                    objFinalResponse.put("state", rs.getString("state"));
                    String profilepic = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profilepic)) {
                        objFinalResponse.put("profile_picture", url + profilepic);
                    }
                    //   objFinalResponse.put("profile_picture", url+rs.getString("profile_picture"));
                    objFinalResponse.put("phone", rs.getString("phone"));
                    return objFinalResponse.toString();
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTid);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getAgentDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getAgentDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getAgentDetailsById(int agentId, String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("agent.details.query", "SELECT u.firstname,ag.company,u.email,u.phone,u.city,u.state,u.password,u.profile_picture,ag.agent_type,ag.agent_specialty,ag.languages,ag.property_expertise,ag.certifications,ag.keywords,ag.description FROM agent_details ag,users u WHERE ag.user_id=u.id AND ag.id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setInt(1, agentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    objFinalResponse.put("firstname", rs.getString("firstname"));
                    objFinalResponse.put("company", rs.getString("company"));
                    objFinalResponse.put("email", rs.getString("email"));
                    objFinalResponse.put("phone", rs.getString("phone"));
                    objFinalResponse.put("city", getCityId(rs.getString("city"),objConn));
                    objFinalResponse.put("state", getStateId(rs.getString("state"),objConn));
                    objFinalResponse.put("password", AESAlgo.decrypt(rs.getString("password")));
                    objFinalResponse.put("agent_type", rs.getInt("agent_type"));
                    objFinalResponse.put("agent_specialty", rs.getInt("agent_specialty"));
                    objFinalResponse.put("languages", rs.getInt("languages"));
                    objFinalResponse.put("property_expertise", rs.getInt("property_expertise"));
                    objFinalResponse.put("certifications", rs.getInt("certifications"));
                    objFinalResponse.put("keywords", rs.getString("keywords"));
                    objFinalResponse.put("about", rs.getString("description"));
                    String profilepic = rs.getString("profile_picture");
                    if (StringUtils.isNotBlank(profilepic)) {
                        objFinalResponse.put(Constants.profilePicture, url + profilepic);
                    }
                    return objFinalResponse.toString();
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTid);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getAgentDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getAgentDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getMortgageDetails(String strTid, String id) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("mortgage.info.query", "SELECT * from mortgage_master_info where id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject response = new JSONObject();
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    response.put("propertyTax", rs.getFloat("property_tax") + "");
                    response.put("no_of_years", rs.getString("no_of_years"));
                    response.put("rate_of_interest", rs.getString("rate_of_interest"));
                    response.put("insurence_tax", rs.getString("insurence_tax"));
//                    response.put("city", rs.getString("city"));
                    return response.toString();
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getMortgageDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getMortgageDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return response.toString();
    }

    public String getCurrencyDetails(String strTid, String id) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("mortgage.info.query", "SELECT * from currency_converter where id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject response = new JSONObject();
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    response.put("currency", rs.getString("currency") + "");
                    response.put("multiplication_factor", rs.getString("multiplication_factor"));
//                    response.put("city", rs.getString("city"));
                    return response.toString();
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getMortgageDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getMortgageDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return response.toString();
    }

    public String getAgentProfileDetailsById(int agentId, String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("agent.details.query", "SELECT u.firstname,ag.company,u.email,u.phone,u.city,u.state,u.password,ag.agent_type,ag.agent_specialty,ag.languages,ag.property_expertise,ag.certifications,ag.keywords,ag.description FROM agent_details ag,users u WHERE ag.user_id=u.id AND u.id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setInt(1, agentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    objFinalResponse.put("firstname", rs.getString("firstname"));
                    objFinalResponse.put("company", rs.getString("company"));
                    objFinalResponse.put("email", rs.getString("email"));
                    objFinalResponse.put("phone", rs.getString("phone"));
                    objFinalResponse.put("city", rs.getString("city"));
                    objFinalResponse.put("state", rs.getString("state"));
                    objFinalResponse.put("password", AESAlgo.decrypt(rs.getString("password")));
                    objFinalResponse.put("agent_type", rs.getInt("agent_type"));
                    objFinalResponse.put("agent_specialty", rs.getInt("agent_specialty"));
                    objFinalResponse.put("languages", rs.getInt("languages"));
                    objFinalResponse.put("property_expertise", rs.getInt("property_expertise"));
                    objFinalResponse.put("certifications", rs.getInt("certifications"));
                    objFinalResponse.put("keywords", rs.getString("keywords"));
                    objFinalResponse.put("about", rs.getString("description"));
                    return objFinalResponse.toString();
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTid);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getAgentDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getAgentDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public String getAgentProfileDetailsByAgentId(int agentId, String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("agent.details.query", "SELECT u.firstname,ag.company,u.email,u.phone,u.city,u.state,u.password,ag.agent_type,ag.agent_specialty,ag.languages,ag.property_expertise,ag.certifications,ag.keywords,ag.description FROM agent_details ag,users u WHERE ag.user_id=u.id AND ag.id=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                pstmt.setInt(1, agentId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    objFinalResponse.put("firstname", rs.getString("firstname"));
                    objFinalResponse.put("company", rs.getString("company"));
                    objFinalResponse.put("email", rs.getString("email"));
                    objFinalResponse.put("phone", rs.getString("phone"));
                    objFinalResponse.put("city", getCityId(rs.getString("city"),objConn));
                    objFinalResponse.put("state", getStateId(rs.getString("state"),objConn));
                    objFinalResponse.put("password", AESAlgo.decrypt(rs.getString("password")));
                    objFinalResponse.put("agent_type", rs.getInt("agent_type"));
                    objFinalResponse.put("agent_specialty", rs.getInt("agent_specialty"));
                    objFinalResponse.put("languages", rs.getInt("languages"));
                    objFinalResponse.put("property_expertise", rs.getInt("property_expertise"));
                    objFinalResponse.put("certifications", rs.getInt("certifications"));
                    objFinalResponse.put("keywords", rs.getString("keywords"));
                    objFinalResponse.put("about", rs.getString("description"));
                    return objFinalResponse.toString();
                } else {
                    return Utilities.prepareReponse(USERID_NOT_EXISTS.getCode(), USERID_NOT_EXISTS.DESC(), strTid);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getAgentDetails" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getAgentDetails" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public int getStateId(String state,Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("get.stateid.query", "SELECT * FROM state where state=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {

            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setString(1, state);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getStateId" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getStateId" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
//            if (objConn != null) {
//                dbconnection.closeConnection(rs, pstmt, objConn);
//            }
        }
        return -1;
    }

    public int getCityId(String city , Connection objConn) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("get.cityid.query", "SELECT * FROM city where city=?");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {

            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                pstmt.setString(1, city);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getCityId" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getCityId" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
//            if (objConn != null) {
//                dbconnection.closeConnection(rs, pstmt, objConn);
//            }
        }
        return -1;
    }

    public String getpropertyCategory(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("propertycat.details.query", "SELECT * FROM property_category");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        try {

            JSONObject objRequest = new JSONObject();
            objRequest.put("code", "1000");
            objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();
                JSONArray propertyArray = new JSONArray();
                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, Utilities.nullToEmpty(rs.getString("name")));
                    propertyArray.put(property);
                }
                objRequest.put("propertyCategory", propertyArray);
                objFinalResponse.put("response", objRequest);

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getpropertyCategory" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getpropertyCategory" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return objFinalResponse.toString();
    }

    public int updatePropertyStatus(int nPropertyId, int nStatus) {
        String updateQuery = ConfigUtil.getProperty("property.status.update.query", "update property set updated_on=now(),status=? where id=?");
        PreparedStatement pstmt = null;
        Connection objConn = null;

        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setInt(1, nStatus);
                pstmt.setInt(2, nPropertyId);
                return pstmt.executeUpdate();
            }

        } catch (SQLException sqle) {
            logger.error("updatePropertyStatus() : Got SQLException " + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error("updatePropertyStatus() Got Exception : " + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(null, pstmt, objConn);
            }
        }
        return -1;
    }

    public String getPropertyAgentDetails(int nPropertyId) {
        String updateQuery = ConfigUtil.getProperty("property.agent.details.query", "select * from property where id=?");
        String query = ConfigUtil.getProperty("property.agent.details.query1", "SELECT GROUP_CONCAT(u.firstname) as agent_names FROM agent_property_mapping apm,agent_details ad,users u WHERE apm.agent_id=ad.id AND ad.user_id=u.id AND apm.property_id=?");
        PreparedStatement pstmt = null;
        Connection objConn = null;
        ResultSet rs = null;

        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setInt(1, nPropertyId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int nsharedtoagents = rs.getInt("share_to_agents");
                    if (nsharedtoagents != 1) {
                        pstmt = objConn.prepareStatement(query);
                        pstmt.setInt(1, nPropertyId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            String agent_names = rs.getString("agent_names");
                            if (StringUtils.isBlank(agent_names)) {
                                return "";
                            }
                            return agent_names;
                        }
                    } else {
                        return Constants.sharedToAgents;
                    }

                }
            }

        } catch (SQLException sqle) {
            logger.error("getPropertyAgentDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error("getPropertyAgentDetails() Got Exception : " + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return "";
    }

    public String deActivateProperty(int nPropertyId, int nStatus) {
        String updateQuery = ConfigUtil.getProperty("property.status.update.query", "update property set updated_on=now(),status=? where id=?");
        PreparedStatement pstmt = null;
        Connection objConn = null;

        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(updateQuery);
                pstmt.setInt(1, nStatus);
                pstmt.setInt(2, nPropertyId);
                int nRes = pstmt.executeUpdate();
                if (nRes == 1) {
                    return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), "");
                }
            }

        } catch (SQLException sqle) {
            logger.error("deactivateProperty() : Got SQLException " + Utilities.getStackTrace(sqle));
        } catch (Exception e) {
            logger.error("deactivateProperty() Got Exception : " + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(null, pstmt, objConn);
            }
        }
        return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), "");
    }

    public int addPropertyToAgent(String property_id, String agent_id) throws Exception {

        String insertAgentQuery = ConfigUtil.getProperty("store.agent.property.data.query", "INSERT INTO `aqarabia`.`agent_property_mapping`(`property_id`,`agent_id`) VALUES ( ?,?)");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {

                pstmt = objConn.prepareStatement(insertAgentQuery, Statement.RETURN_GENERATED_KEYS);
                for (String agentId : agent_id.split(",")) {
                    pstmt.setString(1, property_id);
                    pstmt.setString(2, agentId);

                    status = pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                }
                if (rs.next()) {
                    status = rs.getInt(1);

                }

            } else {
                logger.error("addUserDetails(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle.getMessage());
        } catch (Exception e) {
            logger.error("addUserDetails() Got Exception : " + Utilities.getStackTrace(e));
            throw new Exception(e.getMessage());
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public int deleteImage(String imageID) {

        String deleteImageQuery = ConfigUtil.getProperty("store.agent.property.data.query", "DELETE FROM `aqarabia`.`images`");
        deleteImageQuery = deleteImageQuery + "WHERE `id`='" + imageID + "';";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {

                pstmt = objConn.prepareStatement(deleteImageQuery);
                status = pstmt.executeUpdate();

            } else {
                logger.error("deleteImage(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("deleteImage() : Got SQLException " + Utilities.getStackTrace(sqle));

        } catch (Exception e) {
            logger.error("deleteImage() Got Exception : " + Utilities.getStackTrace(e));

        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public int deassignProperty(int agentId, int propertyId) {

        String deassignPropertyQuery = ConfigUtil.getProperty("deassign.property.data.query", "DELETE FROM `aqarabia`.`agent_property_mapping`");
        deassignPropertyQuery = deassignPropertyQuery + "WHERE `property_id`='" + propertyId + "' and `agent_id`=" + agentId + ";";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int status = 0;
        try {
            objConn = dbconnection.getConnection();
            if (objConn != null) {

                pstmt = objConn.prepareStatement(deassignPropertyQuery);
                status = pstmt.executeUpdate();

            } else {
                logger.error("deassignPropertyQuery(): connection object is null");
            }
        } catch (SQLException sqle) {
            logger.error("deassignPropertyQuery() : Got SQLException " + Utilities.getStackTrace(sqle));

        } catch (Exception e) {
            logger.error("deleteImage() Got Exception : " + Utilities.getStackTrace(e));

        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return status;
    }

    public JSONArray getAgentlist(String strTid) throws SQLException, Exception {
        String query = ConfigUtil.getProperty("agentlist.details.query", "SELECT ad.id,u.firstname FROM agent_details ad ,users u WHERE ad.user_id=u.id");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONObject objFinalResponse = new JSONObject();
        JSONArray agentarray = new JSONArray();
        try {

            //           objRequest.put("transid", strTid);
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(query);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getInt("id"));
                    property.put(Constants.name, rs.getString("firstname"));
                    agentarray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while getpropertyCategory" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while getpropertyCategory" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return agentarray;
    }
    //Rador search api
//    private static final String googleApiDetails = ConfigUtil.getProperty("google.maps.keyword.search.api", "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=$(lat),$(lang)&radius=$(radius)&keyword=$(keyword)&key=AIzaSyBtHZGB7BuAvnIAF_slqUwuMftXNZ5oObc");
    //Nearby search api
    private static final String googleApiDetails = ConfigUtil.getProperty("google.maps.keyword.search.api", "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$(lat),$(lang)&radius=$(radius)&keyword=$(keyword)&key=AIzaSyBtHZGB7BuAvnIAF_slqUwuMftXNZ5oObc");

    public String getNeighborhoodInfo(String latitude, String longitude, String nRadius, String keywords, String strTid) throws Exception {

        JSONObject objRequest = new JSONObject();
        objRequest.put("code", "1000");
        objRequest.put("transid", strTid);
        try {
            JSONObject neighborhoodInfo = null;
            neighborhoodInfo = neighborhoodInfo(latitude, longitude, nRadius, keywords, strTid);
            if (neighborhoodInfo != null) {
                objRequest.put("response", neighborhoodInfo);
            }
        } catch (Exception e) {
            logger.error(" Got Exception while getpropertyCategory" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {

        }
        return objRequest.toString();
    }

    public JSONObject neighborhoodInfo(String latitude, String longitude, String nRadius, String keywords, String strTid) throws Exception {
        JSONObject objKeywordsRequest = new JSONObject();
        try {
            String url = googleApiDetails;
            url = url.replaceAll("\\$\\(lat\\)", latitude);
            url = url.replaceAll("\\$\\(lang\\)", longitude);
            url = url.replaceAll("\\$\\(radius\\)", nRadius + "");
            Response response = null;
            String keywords1 = keywords;
            for (String keyword : keywords1.split("\\|")) {
                String keywordURL = url;
                keywordURL = keywordURL.replaceAll("\\$\\(keyword\\)", keyword);
                response = HttpDispatchClient.getInstance().Get(keywordURL, null);
                JSONObject object = new JSONObject(response.getResponseBody());
                objKeywordsRequest.put(Utilities.getKeywordName(keyword), object);
            }
        } catch (Exception e) {
            logger.error(" Got Exception while neighborhoodInfo" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {

        }

        return objKeywordsRequest;
    }

    public int saveMortgage(String user_id, String phone, String type, String mortagage) {
        String insertQuery = ConfigUtil.getProperty("save.mortagage.to.user", "INSERT INTO aqarabia.mortgage_info(user_id,mobile,type,mortgage_details)	VALUES (?,?,?,?);");

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        try {
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {

                pstmt = objConn.prepareStatement(insertQuery);

                pstmt.setString(1, user_id);
                pstmt.setString(2, phone);
                pstmt.setString(3, type);
                pstmt.setString(4, mortagage);
                int nRes = pstmt.executeUpdate();
                return nRes;
            }

        } catch (SQLException sqle) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(sqle));

        } catch (Exception e) {
            logger.error("addUserDetails() : Got SQLException " + Utilities.getStackTrace(e));

        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }

        return -1;
    }

    public int propertyRequestInfo(String userId, String emailId, String mobile, String agentIds, int nPropertyId, String transId) throws SQLException, Exception {
        int nRes = -1;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        String insertQuery = ConfigUtil.getProperty("insert.request.info.query", "insert into aqarabia.request_info(user_id,email_id,mobile,agent_id,property_id) values(?,?,?,?,?)");
        try {
            ArrayList<AgentInfo> agentList = (ArrayList) getAgentList(agentIds);
            objConn = DBConnection.getInstance().getConnection();
            for (AgentInfo agentList1 : agentList) {
                try {
                    if (objConn != null) {

                        pstmt = objConn.prepareStatement(insertQuery);

                        pstmt.setString(1, userId);
                        pstmt.setString(2, emailId);
                        pstmt.setString(3, mobile);
                        pstmt.setInt(4, agentList1.getAgentId());
                        pstmt.setInt(5, nPropertyId);
                        pstmt.executeUpdate();
                    }
                } catch (Exception e) {
                    logger.error("Got exception while adding request info into database , ex => " + Utilities.getStackTrace(e));
                } finally {
                    dbconnection.closeConnection(null, pstmt, null);
                }
                String requestInfoContent = requestInfoMailContent;
                requestInfoContent = requestInfoContent.replaceAll("\\$\\(mobile\\)", mobile);
                requestInfoContent = requestInfoContent.replaceAll("\\$\\(email\\)", emailId);
                requestInfoContent = requestInfoContent.replace("hostURL", url);
                if (logger.isDebugEnabled()) {
                    logger.debug("Mail sending to user => " + agentList1.getEmail());
                }
                nRes = objMail.sendHtmlMail(mailFrom, agentList1.getEmail(), mailCC, mailBCC, mailSubjectRequestInfo, requestInfoContent, new ArrayList());
            }

        } catch (Exception e) {
            logger.error(" Got Exception while getActiveHomesCountAgainistToAgent" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(null, pstmt, objConn);
            }
        }
        return nRes;
    }

    public List<AgentInfo> getAgentList(String agentIds) {
        String insertQuery = ConfigUtil.getProperty("get.agent.list.by.agentids", "SELECT ad.id,u.email,u.phone FROM aqarabia.agent_details ad,aqarabia.users u WHERE ad.user_id=u.id AND ad.id IN ");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        ArrayList<AgentInfo> agentList = new ArrayList<AgentInfo>();
        try {
            insertQuery = insertQuery + " (" + agentIds + ");";
            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(insertQuery);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    AgentInfo info = new AgentInfo();
                    info.setAgentId(rs.getInt("id"));
                    info.setEmail(rs.getString("email"));
                    info.setMobile(rs.getString("phone"));
                    agentList.add(info);
                }

            }
        } catch (SQLException sqle) {
            logger.error("getAgentList() : Got SQLException " + Utilities.getStackTrace(sqle));

        } catch (Exception e) {
            logger.error("getAgentList() : Got SQLException " + Utilities.getStackTrace(e));
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return agentList;
    }

    public int currencysettinglistCount(String strTid) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("mortagesettinglist.count.query", "SELECT count(*) as count FROM currency_converter");
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        int count = 0;
        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while mortagesettinglistCount" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while currency_converter" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return count;
    }

    public JSONArray currencysettinglist(String strTid, int fromIndex, int endIndex) throws SQLException, Exception {
        String userdetailsquery = ConfigUtil.getProperty("mortagesettinglist.query", "SELECT * FROM currency_converter ORDER BY id asc");
        userdetailsquery = userdetailsquery + " LIMIT " + fromIndex + "," + endIndex;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection objConn = null;
        JSONArray propertyArray = new JSONArray();

        try {

            objConn = DBConnection.getInstance().getConnection();
            if (objConn != null) {
                pstmt = objConn.prepareStatement(userdetailsquery);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    JSONObject property = new JSONObject();
                    property.put(Constants.id, rs.getString(Constants.id));
                    property.put("multiplication_factor", Utilities.nullToEmpty(rs.getString("multiplication_factor")));
                    property.put("currency", Utilities.nullToEmpty(rs.getString("currency")));
                    propertyArray.put(property);
                }

            }
        } catch (SQLException sqle) {
            logger.error(" Got SQLException while mortagesettinglist" + Utilities.getStackTrace(sqle));
            throw new SQLException(sqle);
        } catch (Exception e) {
            logger.error(" Got Exception while mortagesettinglist" + Utilities.getStackTrace(e));
            throw new Exception(e);
        } finally {
            if (objConn != null) {
                dbconnection.closeConnection(rs, pstmt, objConn);
            }
        }
        return propertyArray;
    }

}
