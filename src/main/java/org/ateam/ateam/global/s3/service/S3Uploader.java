package org.ateam.ateam.global.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.ateam.ateam.global.s3.S3Config;
import org.ateam.ateam.global.s3.exception.InvalidS3UrlException;
import org.ateam.ateam.global.s3.exception.S3DeleteFailedException;
import org.ateam.ateam.global.s3.exception.S3UploadFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Uploader {

  private final AmazonS3 amazonS3;
  private final S3Config s3Config;

  public String upload(MultipartFile file, String dirName) {
    String fileName = buildFileName(dirName, file.getOriginalFilename());
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    metadata.setContentType(file.getContentType());

    try (InputStream inputStream = file.getInputStream()) {
      PutObjectRequest request =
          new PutObjectRequest(s3Config.getBucket(), fileName, inputStream, metadata);

      amazonS3.putObject(request);
    } catch (IOException e) {
      throw new S3UploadFailedException();
    }

    return amazonS3.getUrl(s3Config.getBucket(), fileName).toString();
  }

  public void delete(String fileUrlOrKey) {
    String key = extractKey(fileUrlOrKey);
    try {
      amazonS3.deleteObject(s3Config.getBucket(), key);
    } catch (Exception e) {
      throw new S3DeleteFailedException();
    }
  }

  private String buildFileName(String dirName, String originalFilename) {
    String ext = null;
    if (originalFilename != null && originalFilename.contains(".")) {
      ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }

    String uuid = UUID.randomUUID().toString();
    if (ext == null) {
      return dirName + "/" + uuid;
    }
    return dirName + "/" + uuid + "." + ext;
  }

  private String extractKey(String fileUrlOrKey) {
    if (fileUrlOrKey.startsWith("http")) {
      try {
        URL url = new URL(fileUrlOrKey);
        String path = url.getPath();
        if (path.startsWith("/")) {
          path = path.substring(1);
        }
        return java.net.URLDecoder.decode(path, java.nio.charset.StandardCharsets.UTF_8);
      } catch (MalformedURLException e) {
        throw new InvalidS3UrlException();
      }
    }
    throw new InvalidS3UrlException();
  }
}
