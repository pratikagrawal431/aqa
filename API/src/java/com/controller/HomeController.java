package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
@Controller
public class HomeController {

//    @Override
//    protected ModelAndView handleRequestInternal(HttpServletRequest request,
//            HttpServletResponse response) throws Exception {
//
//        ModelAndView model = new ModelAndView("WelcomePage");
//
//        return model;
//    }

    @RequestMapping(value = "/signuptest", method = RequestMethod.GET)
    public Object index1(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
       // HttpSession session = request.getSession();
      //  session.invalidate();
        model.setViewName("index");
        return model;

    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public Object index(HttpServletRequest request) {
        System.out.println("came to index");
        ModelAndView model = new ModelAndView();
      //  HttpSession session = request.getSession();
       // session.invalidate();
        model.setViewName("index");
       // return new RedirectView("", true);
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
}
