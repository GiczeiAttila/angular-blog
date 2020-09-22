package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.PostPictureRegistry;
import com.progmasters.reactblog.domain.dto.PostPictureResource;
import com.progmasters.reactblog.service.cloudinary.CloudinaryFileUploader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private CloudinaryFileUploader fileUploader;

    @Autowired
    public FileUploadController(CloudinaryFileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    @PostMapping
    public ResponseEntity<Long> uploadFile(@RequestParam("picture") @NotNull @NotBlank CommonsMultipartFile file) throws IOException {
        return new ResponseEntity<>(fileUploader.processFile(file, null), HttpStatus.OK);
    }

//    @PostMapping(path = "/withFormData")
//    public ResponseEntity<Long> uploadFileWithFormData(@ModelAttribute FormDataWithFile data) throws IOException {
//       System.out.println(data.toString());
//
//        return new ResponseEntity<>(fileUploader.processFile(data.getFile(), data.getTitle(), data.getCategory()),
//               HttpStatus.OK);
//    }

    @GetMapping
    public List<PostPictureRegistry> downloadFile() {
        List<PostPictureRegistry> fileRegistryList = fileUploader.getFileRegistryList();
        return fileRegistryList;
    }


    @GetMapping("/{resourceId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long resourceId) {
        PostPictureResource fileResource = fileUploader.getFile(resourceId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(fileResource.getMediaType()))
                .body(fileResource.getData());
    }

}
