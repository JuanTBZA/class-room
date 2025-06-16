package com.juantirado.virtual_classroom.controller.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.models.BlobProperties;
import com.juantirado.virtual_classroom.service.azure.AzureBlobService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final AzureBlobService azureBlobService;

    public FileUploadController(AzureBlobService azureBlobService) {
        this.azureBlobService = azureBlobService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = azureBlobService.upload(file);
            return ResponseEntity.ok("Archivo subido: " + url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/view/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        try {
            byte[] content = azureBlobService.download(filename);

            String contentType = getMimeType(filename);

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(content);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getMimeType(String filename) {
        if (filename.endsWith(".png")) return "image/png";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".gif")) return "image/gif";
        if (filename.endsWith(".pdf")) return "application/pdf";
        return "application/octet-stream";
    }

}