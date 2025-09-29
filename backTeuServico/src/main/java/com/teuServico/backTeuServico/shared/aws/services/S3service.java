package com.teuServico.backTeuServico.shared.aws.services;

import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
public class S3service {
    private final S3Client s3Client;
    private final String endpoint = System.getenv("AWS_ENDPOINT");
    private final String bucketName = System.getenv("AWS_BUCKET_NAME");

    @Autowired
    public S3service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadImagem(MultipartFile file, String fileName) throws IOException {
        String tipoArquivo = file.getContentType();
        List<String> tiposPermitidos = List.of("image/jpeg", "image/png", "image/gif");
        if (!tiposPermitidos.contains(tipoArquivo)) {
            throw new BusinessException("Tipo de imagem não suportado.");
        }
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return getFileUrl(fileName);
    }

    private String getFileUrl(String fileName) {
        return String.format("%s/%s/%s", endpoint, bucketName, fileName);
    }
}