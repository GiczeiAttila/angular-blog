package com.progmasters.reactblog.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.reactblog.domain.PostPictureRegistry;
import com.progmasters.reactblog.domain.dto.PostPictureResource;
import com.progmasters.reactblog.repository.PostPictureRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CloudinaryFileUploader {

    private final Cloudinary cloudinary;
    private final PostPictureRepository postPictureRepository;

    public CloudinaryFileUploader(PostPictureRepository postPictureRepository, Cloudinary cloudinary) {
        this.postPictureRepository = postPictureRepository;
        this.cloudinary = cloudinary;
    }

    public Long processFile(CommonsMultipartFile commonsMultipartFile, String category) throws IOException {
        PostPictureRegistry postPictureRegistry = storeFile(commonsMultipartFile, category);


        Long id = postPictureRepository.save(postPictureRegistry).getId();

        return id;
    }

    protected PostPictureRegistry storeFile(CommonsMultipartFile commonsMultipartFile, String category) {
        Map params = ObjectUtils.asMap(
                "folder", category,
                "access_mode", "authenticated",
//                "access_type", "token",
                "overwrite", false,
                "type", "authenticated",
                "resource_type", "auto",
                "use_filename", true);
        UploadResponse uploadResponse;
        File fileToUpload = new File(System.getProperty("java.io.tmpdir") + '/' + commonsMultipartFile.getOriginalFilename());
        try {
            commonsMultipartFile.transferTo(fileToUpload);
            uploadResponse = new ObjectMapper()
                    .convertValue(cloudinary.uploader().upload(fileToUpload, params), UploadResponse.class);
        } catch (IOException e) {
            throw new CloudinaryUploadException();
        }

        return new PostPictureRegistry(uploadResponse, commonsMultipartFile);
    }


    public PostPictureResource getFile(Long id) {
        PostPictureRegistry upload = findById(id);
        String url = upload.getFilePath();
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            byte[] bytes = IOUtils.toByteArray(in);
            return new PostPictureResource(bytes, upload.getMediaType(), upload.getOriginalFileName());
        } catch (IOException e) {
            throw new CloudinaryDownloadException();
        }
    }

    public PostPictureRegistry findById(Long id) {
        return postPictureRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Upload found with given Id: " + id));
    }

    public List<PostPictureRegistry> getFileRegistryList() {
        return postPictureRepository.findAll();
    }

}
