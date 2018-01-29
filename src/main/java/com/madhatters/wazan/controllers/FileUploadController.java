package com.madhatters.wazan.controllers;

import com.madhatters.wazan.storage.StorageFileNotFoundException;
import com.madhatters.wazan.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final StorageService storageService;

    private static final String BASE_IMAGE_URL = "http://nationalforgas.com/upload-dir/img/";

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{pathCode}")
    public ResponseEntity<List<String>> listUploadedFiles(@PathVariable String pathCode) throws IOException {
            ArrayList<String> urls = new ArrayList<>();
            String basePath = "upload-dir/img/" + pathCode;
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(basePath));
            for (Path path : directoryStream) {
                urls.add(basePath + path.getFileName().toString());
            }
            return ResponseEntity.ok(urls);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>>
    handleFileUpload(@RequestParam("file") MultipartFile[] file) {

            storageService.init();
            long pathCode = new Random().nextLong();
            String dir = "/img/" + pathCode + "/";
            ArrayList<String> urls = new ArrayList<>(file.length);
            for (MultipartFile f : file) {
                storageService.store(dir, f);
                urls.add(BASE_IMAGE_URL + pathCode + "/" + f.getOriginalFilename());
            }
            return ResponseEntity.ok(urls);

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}