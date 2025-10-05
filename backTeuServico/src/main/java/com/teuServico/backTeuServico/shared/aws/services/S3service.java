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

/**
 * Serviço responsável por realizar operações com o Amazon S3,
 */
@Service
public class S3service {
    private final S3Client s3Client;
    private final String endpoint = System.getenv("AWS_ENDPOINT");
    private final String bucketName = System.getenv("AWS_BUCKET_NAME");

    /**
     * Construtor da classe S3service.
     *
     * @param s3Client cliente do Amazon S3 utilizado para interagir com o serviço.
     */
    @Autowired
    public S3service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Realiza o upload de uma imagem para o bucket S3.
     *
     * @param file arquivo de imagem a ser enviado.
     * @param fileName nome com o qual o arquivo será armazenado no bucket.
     * @return URL pública do arquivo armazenado.
     * @throws IOException se ocorrer erro ao ler os bytes do arquivo.
     * @throws BusinessException se o tipo de arquivo não for suportado.
     */
    public String uploadImagem(MultipartFile file, String fileName){
        String tipoArquivo = file.getContentType();
        List<String> tiposPermitidos = List.of("image/jpeg", "image/png", "image/gif");
        if (!tiposPermitidos.contains(tipoArquivo)) {
            throw new BusinessException("Tipo de imagem não suportado.");
        }
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        }catch (IOException e){
            throw new BusinessException("Erro ao processar a imagem ", e);
        }
        return getFileUrl(fileName);
    }

    /**
     * Gera a URL pública do arquivo armazenado no bucket S3.
     * @param fileName nome do arquivo armazenado.
     * @return URL pública do arquivo.
     */
    private String getFileUrl(String fileName) {
        return String.format("%s/%s/%s", endpoint, bucketName, fileName);
    }
}