//package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;
//
//import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//public class ImageController {
//    private final ImageService imageService;
//
//    @Autowired
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//    }
//
//    @PostMapping("/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
//        Long imageId = imageService.uploadImage(file);
//        model.addAttribute("imageId", imageId);
//        return "upload-success"; // Return a success view
//    }
//}
