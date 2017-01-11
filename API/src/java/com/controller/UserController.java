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
import com.beans.CurrencyBean;
import com.beans.LoginRequestBean;
import com.beans.MortgageSettings;
import com.beans.UserPasswordBean;
import com.common.ConfigUtil;
import static com.common.ResponseCodes.ServiceErrorCodes.FAILED_TO_SAVE_SEARCH;
import static com.common.ResponseCodes.ServiceErrorCodes.GENERIC_ERROR;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_AGENT_ID;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_COMMENTS;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_DATA;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_JSON;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_LOGIN_TYPEID;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_PASSWORD;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_PASSWORD_TYPE;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_PROPERTY_ID;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_TOKEN;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_USERID;
import static com.common.ResponseCodes.ServiceErrorCodes.MORTGAGE_INFO_UPDATE_FAILED;
import static com.common.ResponseCodes.ServiceErrorCodes.NAME_IS_REQUIRED;
import static com.common.ResponseCodes.ServiceErrorCodes.SEARCH_QUERY_IS_REQUIRED;
import static com.common.ResponseCodes.ServiceErrorCodes.SUCCESS;
import static com.common.ResponseCodes.ServiceErrorCodes.UMP_INVALID_STATE;
import static com.common.ResponseCodes.ServiceErrorCodes.USER_PROPERTY_SAVE_FAILED;
import com.common.Utilities;
import com.dao.FileDAO;
import com.google.gson.JsonSyntaxException;
import com.service.UserService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {

    private static final String template = "Welcome, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = Logger.getLogger(UserService.class);
    private static UserService objUserService = null;
    @Autowired
    private FileDAO documentDao;
    String FILES_DIR = ConfigUtil.getProperty("FILES_DIR", "E:\\omm_api\\branches\\API\\images\\");
    String VIR_DIR = ConfigUtil.getProperty("VIR_DIR", "E:\\omm_api\\branches\\API\\images\\");

    @Autowired
    private ServletContext servletContext;

    UserController() {
        try {
            if (objUserService == null) {
                objUserService = new UserService();
            }

        } catch (Exception e) {
            logger.error("Exception in UserService(),ex:" + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/json"})
    public String signup(@RequestBody String strJSON, HttpSession httpSession) {
        User user = null;
        String strResponse = null;
        String transId = UUID.randomUUID().toString();
        try {
            user = Utilities.fromJson(strJSON, User.class);
            if (StringUtils.isBlank(user.getUser_id())) {
                return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), transId);
            }
            String loginType = user.getLogintype();

            if (StringUtils.isBlank(loginType)) {
                return Utilities.prepareReponse(INVALID_LOGIN_TYPEID.getCode(), INVALID_LOGIN_TYPEID.DESC(), transId);
            }
            int nLoginTypeId = 1;
            try {
                nLoginTypeId = Utilities.toInt(loginType, 1);
            } catch (Exception e) {
                logger.error("[signup] Invalid LogintypeId , Ex => " + Utilities.getStackTrace(e));
                return Utilities.prepareReponse(INVALID_LOGIN_TYPEID.getCode(), INVALID_LOGIN_TYPEID.DESC(), transId);
            }
            // LoginType=1 is direct signup, so password is mandatory
            if (nLoginTypeId == 1 && StringUtils.isBlank(user.getPassword())) {
                return Utilities.prepareReponse(INVALID_PASSWORD.getCode(), INVALID_PASSWORD.DESC(), transId);
            }
            if (user.getStatus() == null) {
                return Utilities.prepareReponse(UMP_INVALID_STATE.getCode(), UMP_INVALID_STATE.DESC(), transId);
            }
            strResponse = objUserService.addUserDetails(user, transId);
            if (strResponse.contains("SUCCESS") || strResponse.contains("UserId Already Exists")) {
                httpSession.setAttribute("token", transId);
                LoginRequestBean requestBean = new LoginRequestBean();
                requestBean.setUserId(user.getUser_id());
                requestBean.setPassword(user.getPassword());
                httpSession.setAttribute("user", requestBean);
            }
        } catch (JsonSyntaxException je) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }

        return strResponse;
    }

    @RequestMapping(value = "/agentCreate", method = RequestMethod.POST, produces = {"application/json"})
    public String agentCreate(@RequestParam(value = "file[]", required = false) MultipartFile multipartfile, @RequestParam("login") String strJSON, HttpSession httpSession) {
        User user = null;
        String strResponse = null;
        String transId = UUID.randomUUID().toString();
        try {
            user = Utilities.fromJson(strJSON, User.class);
            if (StringUtils.isBlank(user.getUser_id())) {
                return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), transId);
            }
            String loginType = user.getLogintype();

            if (StringUtils.isBlank(loginType)) {
                return Utilities.prepareReponse(INVALID_LOGIN_TYPEID.getCode(), INVALID_LOGIN_TYPEID.DESC(), transId);
            }
            int nLoginTypeId = 1;
            try {
                nLoginTypeId = Utilities.toInt(loginType, 1);
            } catch (Exception e) {
                logger.error("[signup] Invalid LogintypeId , Ex => " + Utilities.getStackTrace(e));
                return Utilities.prepareReponse(INVALID_LOGIN_TYPEID.getCode(), INVALID_LOGIN_TYPEID.DESC(), transId);
            }
            // LoginType=1 is direct signup, so password is mandatory
            if (nLoginTypeId == 1 && StringUtils.isBlank(user.getPassword())) {
                return Utilities.prepareReponse(INVALID_PASSWORD.getCode(), INVALID_PASSWORD.DESC(), transId);
            }
            if (user.getStatus() == null) {
                return Utilities.prepareReponse(UMP_INVALID_STATE.getCode(), UMP_INVALID_STATE.DESC(), transId);
            }
            if (multipartfile != null) {
                String contextPath = servletContext.getContextPath();
                //System.out.println("contextPath:" + contextPath);
                byte[] bytes = multipartfile.getBytes();
                File dir = new File(FILES_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //  String hostname=InetAddress.getLocalHost().getHostName();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + multipartfile.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location=" + serverFile.getAbsolutePath());
                String newFilenmae = UUID.randomUUID() + ".jpg";
                serverFile.renameTo(new File(dir.getAbsolutePath() + File.separator + newFilenmae));

                // documentDao.save(multipartfile, "/images/" + multipartfile.getOriginalFilename(), property_id);
                user.setProfilePicture("/images/" + newFilenmae);
            }

            strResponse = objUserService.addUserDetails(user, transId);
            if (strResponse.contains("SUCCESS") || strResponse.contains("UserId Already Exists")) {
                httpSession.setAttribute("token", transId);
                LoginRequestBean requestBean = new LoginRequestBean();
                requestBean.setUserId(user.getUser_id());
                requestBean.setPassword(user.getPassword());
                httpSession.setAttribute("user", requestBean);
            }
        } catch (JsonSyntaxException je) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }

        return strResponse;
    }

    @RequestMapping(value = "/agent/agentupdate", method = RequestMethod.POST, produces = {"application/json"})
    public String agentUpdate(@RequestParam(value = "file[]", required = false) MultipartFile multipartfile, @RequestParam("login") String strJSON, HttpSession httpSession) {
        User user = null;
        String strResponse = null;
        String transId = UUID.randomUUID().toString();
        try {
            user = Utilities.fromJson(strJSON, User.class);

            if (multipartfile != null) {
                String contextPath = servletContext.getContextPath();
                //System.out.println("contextPath:" + contextPath);
                byte[] bytes = multipartfile.getBytes();
                File dir = new File(FILES_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //  String hostname=InetAddress.getLocalHost().getHostName();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + multipartfile.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                String newFilenmae = UUID.randomUUID() + ".jpg";
                serverFile.renameTo(new File(dir.getAbsolutePath() + File.separator + newFilenmae));

                logger.info("Server File Location=" + serverFile.getAbsolutePath());

                // documentDao.save(multipartfile, "/images/" + multipartfile.getOriginalFilename(), property_id);
                user.setProfilePicture("/images/" + newFilenmae);
            }
            int nUpdated = objUserService.agentUpdate(user, transId);
            if (nUpdated >= 1) {
                return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
            }
        } catch (JsonSyntaxException je) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }

        return strResponse;
    }
    @RequestMapping(value = "/updatemortgageinfo", method = RequestMethod.POST, produces = {"application/json"})
    public String agentUpdate(@RequestBody String strJSON, HttpSession httpSession, @RequestParam(value = "id") String id) {
        MortgageSettings mortgageSettings = null;
        String strResponse = null;
        String transId = UUID.randomUUID().toString();
        try {
            mortgageSettings = Utilities.fromJson(strJSON, MortgageSettings.class);
           
            int nUpdated = objUserService.mortgageSettings(mortgageSettings,id);
            if (nUpdated >= 1) {
                return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
            }else{
                return Utilities.prepareReponse(MORTGAGE_INFO_UPDATE_FAILED.getCode(), MORTGAGE_INFO_UPDATE_FAILED.DESC(), transId);
            }
        } catch (JsonSyntaxException je) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/updatecurrencyinfo", method = RequestMethod.POST, produces = {"application/json"})
    public String updatecurrencyinfo(@RequestBody String strJSON, HttpSession httpSession, @RequestParam(value = "id") String id) {
        CurrencyBean mortgageSettings = null;
        String strResponse = null;
        String transId = UUID.randomUUID().toString();
        try {
            mortgageSettings = Utilities.fromJson(strJSON, CurrencyBean.class);
           
            int nUpdated = objUserService.currencySettings(mortgageSettings,id);
            if (nUpdated >= 1) {
                return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), transId);
            }else{
                return Utilities.prepareReponse(MORTGAGE_INFO_UPDATE_FAILED.getCode(), MORTGAGE_INFO_UPDATE_FAILED.DESC(), transId);
            }
        } catch (JsonSyntaxException je) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), transId);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), transId);
        }
    }

    @RequestMapping(value = "/user/signin", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/json"})
    public String signin(@RequestBody LoginRequestBean requestBean, HttpSession httpSession) {
        String transId = UUID.randomUUID().toString();
        String strResponse = objUserService.signIn(requestBean, transId);
        if (strResponse.contains("SUCCESS")) {
            httpSession.setAttribute("token", transId);
            httpSession.setAttribute("user", requestBean);
        }
        return strResponse;
    }

    @RequestMapping(value = "/user/adminsignin", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/json"})
    public String adminSignin(@RequestBody LoginRequestBean requestBean, HttpSession httpSession) {
        String transId = UUID.randomUUID().toString();
        User strResponse = objUserService.adminLoginDetails(requestBean, transId);
        if (strResponse != null && strResponse.getErrorMessage().contains("SUCCESS")) {
            httpSession.setAttribute("token", transId);
            httpSession.setAttribute("useradmin", strResponse);
        }
        return strResponse.getErrorMessage();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public Object home(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        if (session.getAttribute("useradmin") != null) {
            User bean = (User) session.getAttribute("useradmin");
            if (bean.getUser_id() != null) {
                model.setViewName("home");
            } else {
                return new RedirectView("", true);
            }
        } else {
            return new RedirectView("", true);
        }

        return model;

    }

    @RequestMapping(value = "/homeredirect", method = RequestMethod.GET)
    public Object homeredirect(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        model.setViewName("home");

        return model;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        session.invalidate();
        model.setViewName("index");
        return new RedirectView("", true);

    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Object index(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        session.invalidate();
        model.setViewName("index");
        return new RedirectView("", true);

    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {

        ModelAndView model = new ModelAndView();
//		model.addObject("title", "Spring Security Custom Login Form");
//		model.addObject("message", "This is protected page!");
        model.setViewName("signin");
        return model;

    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signuppage() {

        ModelAndView model = new ModelAndView();
//		model.addObject("title", "Spring Security Custom Login Form");
//		model.addObject("message", "This is protected page!");
        model.setViewName("signup");
        return model;

    }

    @RequestMapping(value = "/user/validate", method = RequestMethod.GET, produces = {"application/json"})
    public String validate(@RequestParam(value = "token") String token, HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();
        if (StringUtils.isBlank(token)) {
            return Utilities.prepareReponse(INVALID_DATA.getCode(), INVALID_DATA.DESC(), strTid);
        }
        if (httpSession.getAttribute("token") != null && ((String) httpSession.getAttribute("token")).equalsIgnoreCase(token)) {
            return Utilities.prepareReponse(SUCCESS.getCode(), SUCCESS.DESC(), strTid);
        } else {
            return Utilities.prepareReponse(INVALID_TOKEN.getCode(), INVALID_TOKEN.DESC(), strTid);
        }

    }

    @RequestMapping(value = "/user/saveproperty", method = RequestMethod.POST, produces = {"application/json"})
    public String saveProperty(@RequestParam(value = "userId", defaultValue = "0") int nUserId, 
            @RequestParam(value = "propertyId", defaultValue = "0") int nPropertyId,
            @RequestParam(value = "status", defaultValue = "1") int nStatus,
            HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();

        if (nUserId <= 0) {
            return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), strTid);
        }
        if (nPropertyId <= 0) {
            return Utilities.prepareReponse(INVALID_PROPERTY_ID.getCode(), INVALID_PROPERTY_ID.DESC(), strTid);
        }
        String strRes = "";
        try {
            strRes = objUserService.saveProperty(nUserId, nPropertyId, strTid,nStatus);
        } catch (Exception e) {
            logger.error(" [saveProperty] " + Utilities.getStackTrace(e));
            return Utilities.prepareReponse(USER_PROPERTY_SAVE_FAILED.getCode(), USER_PROPERTY_SAVE_FAILED.DESC(), strTid);
        }

        return strRes;
    }

    @RequestMapping(value = "/user/getsavedpropertys", method = RequestMethod.GET, produces = {"application/json"})
    public String getSavedPropertys(@RequestParam(value = "userId", defaultValue = "0") int nUserId,
            @RequestParam(value = "currency", defaultValue = "SAR") String currency,
            HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();

        if (nUserId <= 0) {
            return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), strTid);
        }

        String strRes = "";
        try {
            strRes = objUserService.getSavedPropertys(nUserId, strTid, currency);
        } catch (Exception e) {
            logger.error(" [saveProperty] " + Utilities.getStackTrace(e));
            return Utilities.prepareReponse(USER_PROPERTY_SAVE_FAILED.getCode(), USER_PROPERTY_SAVE_FAILED.DESC(), strTid);
        }

        return strRes;
    }

    @RequestMapping(value = "/user/savesearch", method = RequestMethod.POST, produces = {"application/json"})
    public String saveSearch(@RequestParam(value = "userId", defaultValue = "0") int nUserId, @RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery, HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();

        if (nUserId <= 0) {
            return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), strTid);
        }
        if (StringUtils.isBlank(name)) {
            return Utilities.prepareReponse(NAME_IS_REQUIRED.getCode(), NAME_IS_REQUIRED.DESC(), strTid);
        }
        if (StringUtils.isBlank(searchQuery)) {
            return Utilities.prepareReponse(SEARCH_QUERY_IS_REQUIRED.getCode(), SEARCH_QUERY_IS_REQUIRED.DESC(), strTid);
        }
        String strRes = "";
        try {
            strRes = objUserService.savePropertySearch(nUserId, name, searchQuery, strTid);
        } catch (Exception e) {
            logger.error(" [saveProperty] " + Utilities.getStackTrace(e));
            return Utilities.prepareReponse(FAILED_TO_SAVE_SEARCH.getCode(), FAILED_TO_SAVE_SEARCH.DESC(), strTid);
        }
        return strRes;
    }

    @RequestMapping(value = "/user/getsavedsearchs", method = RequestMethod.GET, produces = {"application/json"})
    public String saveProperty(@RequestParam(value = "userId", defaultValue = "0") int nUserId, @RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "searchQuery", defaultValue = "") String searchQuery, HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();

        if (nUserId <= 0) {
            return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), strTid);
        }
        String strRes = "";
        try {
            strRes = objUserService.getSavedSearches(nUserId, strTid);
        } catch (Exception e) {
            logger.error(" [saveProperty] " + Utilities.getStackTrace(e));
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }

        return strRes;
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.POST, produces = {"application/json"})
    public String passwordService(@RequestBody String strJSON, HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();
        UserPasswordBean userPasswordBean = null;
        try {
            userPasswordBean = Utilities.fromJson(strJSON, UserPasswordBean.class);

            String passwordType = userPasswordBean.getType();

            if (StringUtils.isBlank(passwordType)) {
                return Utilities.prepareReponse(INVALID_PASSWORD_TYPE.getCode(), INVALID_PASSWORD_TYPE.DESC(), strTid);
            }
            String response = objUserService.passwordService(userPasswordBean, strTid);
            return response;
        } catch (JsonSyntaxException e) {
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/user/forgotpassword", method = RequestMethod.GET, produces = {"application/json"})
    public ModelAndView forgotpassword() {

        ModelAndView model = new ModelAndView();
//		model.addObject("title", "Spring Security Custom Login Form");
//		model.addObject("message", "This is protected page!");
        model.setViewName("forgotpassword");
        return model;

    }

    @RequestMapping(value = "/user/update", method = RequestMethod.PUT, produces = {"application/json"})
    public String updateUser(@RequestBody String strJSON) {
        String strTid = UUID.randomUUID().toString();
        User user = null;
        try {
            user = Utilities.fromJson(strJSON, User.class);

            if (StringUtils.isBlank(user.getUser_id())) {
                return Utilities.prepareReponse(INVALID_USERID.getCode(), INVALID_USERID.DESC(), strTid);
            }
            String response = objUserService.updateUser(user, strTid);
            return response;
        } catch (JsonSyntaxException e) {
            logger.error(e);
            return Utilities.prepareReponse(INVALID_JSON.getCode(), INVALID_JSON.DESC(), strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/user/getuserdetails", method = RequestMethod.GET, produces = {"application/json"})
    public String getuserdetails(@RequestParam(value = "userId") String userId, HttpSession httpSession) {
        String strTid = UUID.randomUUID().toString();
        if (StringUtils.isBlank(userId)) {
            return Utilities.prepareReponse(INVALID_DATA.getCode(), INVALID_DATA.DESC(), strTid);
        }
        try {

            return objUserService.getUserDetails(userId, strTid);
        } catch (Exception e) {
            logger.error(Utilities.getStackTrace(e));
        }
        return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
    }

    @RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
    public RedirectView updateagent(@RequestParam("profilephoto") MultipartFile multipartfile, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("city") String city, @RequestParam("state") String state, @RequestParam("oldpassword") String oldpassword, @RequestParam("password") String password, HttpSession httpSession) {
        //ModelAndView model = new ModelAndView();
        try {
            String strTid = UUID.randomUUID().toString();
            User user = (User) httpSession.getAttribute("useradmin");
            String contextPath = servletContext.getContextPath();
            System.out.println("contextPath:" + contextPath);
            byte[] bytes = multipartfile.getBytes();
            String filePath = "";
            if (bytes.length > 0) {
                File dir = new File(FILES_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + multipartfile.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                logger.info("Server File Location=" + serverFile.getAbsolutePath());
//                filePath =  + multipartfile.getOriginalFilename();
                filePath = UUID.randomUUID() + ".jpg";
                serverFile.renameTo(new File(dir.getAbsolutePath() + File.separator + filePath));
            }

            //  documentDao.save(multipartfile, contextPath + File.separator + multipartfile.getOriginalFilename(), property_id);
            int nRes = objUserService.addORUpdateAgentDetails("/images/" + filePath, name, email, phone, city, state, password, user.getUser_id());
            //   String objResponse = objUserService.getAgentDetails(user.getUser_id(), strTid);
            if (nRes == 1) {
                LoginRequestBean bean = new LoginRequestBean();
                bean.setUserId(user.getUser_id());
                user = objUserService.adminLoginDetailsUI(bean, strTid);
                httpSession.setAttribute("useradmin", user);
            }
            // model.addObject("agentdetails", objResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //   model.setViewName("profile");
        return new RedirectView("profile", true);
    }

    @RequestMapping(value = "/agent/recommendation", method = RequestMethod.GET, produces = {"application/json"})
    public String addRecommendationToAgent(@RequestParam(value = "agentId") int nAgentId) {
        String strTid = UUID.randomUUID().toString();
        try {
            if (nAgentId <= 0) {
                return Utilities.prepareReponse(INVALID_AGENT_ID.getCode(), INVALID_AGENT_ID.DESC(), strTid);
            }
            return objUserService.addRecommondation(nAgentId, strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/agent/addreviewtoagent", method = RequestMethod.POST, produces = {"application/json"})
    public String addReviewOnAgent(@RequestParam(value = "agentId") int nAgentId,
            @RequestParam(value = "comment") String strComment,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "rating", defaultValue = "0") int rating) {
        String strTid = UUID.randomUUID().toString();
        try {
            if (nAgentId <= 0) {
                return Utilities.prepareReponse(INVALID_AGENT_ID.getCode(), INVALID_AGENT_ID.DESC(), strTid);
            }
            if (StringUtils.isBlank(strComment)) {
                return Utilities.prepareReponse(INVALID_COMMENTS.getCode(), INVALID_COMMENTS.DESC(), strTid);
            }
            return objUserService.addReviewOnAgent(nAgentId, strComment, rating, strTid, userId);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/agent/reviews", method = RequestMethod.GET, produces = {"application/json"})
    public String addReviewOnAgent(@RequestParam(value = "agentId") int nAgentId,
            @RequestParam(value = "limit", defaultValue = "0") int nLimit) {
        String strTid = UUID.randomUUID().toString();

        try {
            if (nAgentId <= 0) {
                return Utilities.prepareReponse(INVALID_AGENT_ID.getCode(), INVALID_AGENT_ID.DESC(), strTid);
            }
            return objUserService.agentReviews(nAgentId, nLimit, strTid);
        } catch (Exception e) {
            logger.error(e);
            return Utilities.prepareReponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.DESC(), strTid);
        }
    }

    @RequestMapping(value = "/testjsp", method = RequestMethod.GET, produces = {"application/json"})
    public ModelAndView testjsp() {

        ModelAndView model = new ModelAndView();
//		model.addObject("title", "Spring Security Custom Login Form");
//		model.addObject("message", "This is protected page!");
        model.setViewName("newjsp");
        return model;

    }

    @RequestMapping(value = "/agent/agentupdatetest", method = RequestMethod.POST)
    public String agentupdatetest(@RequestParam(value = "file[]", required = false) MultipartFile multipartfile, @RequestParam("jsonreq") String jsonreq, HttpSession httpSession) {

        System.out.println("multipartfile:" + multipartfile);
        System.out.println("jsonreq:" + jsonreq);
        return "";
    }

}
