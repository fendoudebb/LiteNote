package z.note.lite.controller.api;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import z.note.lite.infra.IdWorker;
import z.note.lite.controller.Endpoint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RestController
public class UploadController {

    @Value("${preferences.admin.assets-path}")
    private String assetsPath;

    @Value("${preferences.admin.uploads-path}")
    private String uploadsPath;

    @PostConstruct
    private void init() {
        Path path = new File(assetsPath + uploadsPath).toPath();
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("create resource path error", e);
            }
        }
    }

    @PostMapping(value = Endpoint.Api.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();
        String filename = IdWorker.next() + name.substring(name.lastIndexOf("."));
        file.transferTo(new File(assetsPath + uploadsPath + filename));
        return uploadsPath + filename;
    }

}
