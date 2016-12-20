package com.controller;

import com.common.ConfigUtil;
import com.dao.FileDAO;
import com.service.UserService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {

    @Autowired
    private FileDAO documentDao;
    String FILES_DIR = ConfigUtil.getProperty("FILES_DIR", "E:\\omm_api\\branches\\API\\images\\");
    String VIR_DIR = ConfigUtil.getProperty("VIR_DIR", "E:\\omm_api\\branches\\API\\images\\");
    private static UserService objUserService = new UserService();

    private static final Logger logger = Logger.getLogger(FileController.class);
    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String save(@RequestParam("files[]") MultipartFile multipartfile, @RequestParam("id") String property_id) {

        try {
            ModelAndView model = new ModelAndView();
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
            String newFilenmae=UUID.randomUUID() + ".jpg";
            serverFile.renameTo(new File(dir.getAbsolutePath() + File.separator + newFilenmae));
            JSONObject objResponse = objUserService.getPropertie(property_id, property_id);
            model.addObject("propertie", objResponse.toString());
            logger.info("Server File Location=" + serverFile.getAbsolutePath());

            documentDao.save(multipartfile, "/images/" + newFilenmae, property_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/showproperty?tab=c&id=" + property_id;
    }

}
