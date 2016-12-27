/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import static com.common.ResponseCodes.ServiceErrorCodes.GENERIC_ERROR;
import static com.common.ResponseCodes.ServiceErrorCodes.INVALID_JSON;
import com.common.Utilities;
import com.google.gson.JsonSyntaxException;
import com.service.UserService;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author chhavikumar.b
 */
@RestController
public class MortgageController {

    private static UserService objUserService = null;
    private static final Logger logger = Logger.getLogger(MortgageController.class);

    MortgageController() {
        try {
            if (objUserService == null) {
                objUserService = new UserService();
            }

        } catch (Exception e) {
            logger.error("Exception in PropertyController(),ex:" + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/real-estate", method = RequestMethod.GET)
    public ModelAndView realestate(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        //  session.invalidate();
        model.setViewName("mortgage/real-estate");
        return model;

    }

    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public ModelAndView policy(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        //  session.invalidate();
        model.setViewName("privacy_policy");
        return model;

    }

    @RequestMapping(value = "/terms_of_use", method = RequestMethod.GET)
    public ModelAndView terms_of_use(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        //  session.invalidate();
        model.setViewName("terms_of_use");
        return model;

    }

    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public ModelAndView purchase(HttpServletRequest request, @RequestParam(value = "tab", required = false) String tab) {

        ModelAndView model = new ModelAndView();
//        HttpSession session = request.getSession();
        if (tab != null) {
            request.setAttribute("tab", tab);
        }
        model.setViewName("mortgage/purchase");
        return model;

    }

    @RequestMapping(value = "/mortgage", method = RequestMethod.POST)
    public ModelAndView mortgage(HttpServletRequest request, @RequestParam(value = "mortgage") String mortgage, @RequestParam(value = "mortgagetype") String type) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        // session.invalidate();
        session.setAttribute("mortgage", mortgage);
        session.setAttribute("mortgagetype", type);
        System.out.println("mortgage:" + mortgage);
        model.setViewName("mortgage/pre-approval-14");
        return model;

    }

    @RequestMapping(value = "/mortgagesave", method = RequestMethod.POST)
    public ModelAndView mortgagesave(HttpServletRequest request, @RequestParam(value = "user_id") String user_id, @RequestParam(value = "mobile") String phone) {

        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        String mortgage = "";

        if (session.getAttribute("mortgage") instanceof JSONObject) {
            JSONObject res = (JSONObject) session.getAttribute("mortgage");
            mortgage = res.toString();
        } else {
            mortgage = (String) session.getAttribute("mortgage");
        }
        String type = (String) session.getAttribute("mortgagetype");
        // session.invalidate();
        //session.setAttribute("mortgage", mortgage);
        // System.out.println("mortgage:"+mortgage);
        objUserService.saveMortgage(user_id, phone, type, mortgage);
        model.setViewName("mortgage/pre-approval-15");
        return model;

    }

    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public ModelAndView approval(HttpServletRequest request, @RequestParam(value = "approvalseq") String approvalseq, @RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value) {

        ModelAndView model = new ModelAndView();
        try {

            HttpSession session = request.getSession();
            JSONObject mortgage = null;
            session.setAttribute("mortgagetype", "Pre-approval");
            mortgage = (JSONObject) session.getAttribute("mortgage");
            if (mortgage == null) {
                mortgage = new JSONObject();
            }
            if (key != null && value != null) {
                mortgage.put(key, value);
                session.setAttribute("mortgage", mortgage);
            }
            //  String mortgage = (String) session.getAttribute("mortgage");
            // String type = (String) session.getAttribute("mortgagetype");
            // session.invalidate();

            // System.out.println("mortgage:"+mortgage);
            //    objUserService.saveMortgage(user_id, phone, type, mortgage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.setViewName("mortgage/" + approvalseq);
        return model;

    }

    @RequestMapping(value = "/mortagesettinglist", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String mortagesettinglist(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex) {
        String strTid = UUID.randomUUID().toString();
        String strResult = null;
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            JSONObject json = new JSONObject();
            JSONArray obj = objUserService.mortagesettinglist(strTid, fromIndex, endIndex);
            json.put("total", objUserService.mortagesettinglistCount(strTid));
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
 @RequestMapping(value = "/currencysettinglist", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String currencysettinglist(@RequestParam("page") int page,
            @RequestParam("rows") int endIndex) {
        String strTid = UUID.randomUUID().toString();
        String strResult = null;
        try {
            int fromIndex = 0;
            if (page > 0) {
                fromIndex = (page - 1) * endIndex;
            }
            JSONObject json = new JSONObject();
            JSONArray obj = objUserService.currencysettinglist(strTid, fromIndex, endIndex);
            json.put("total", objUserService.currencysettinglistCount(strTid));
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
}
