package com.example.diviction.infra.gcp

import com.example.diviction.infra.gcp.GCP_URLs.USER_PROFILE_URL
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class GcpStorageService(
        private val storage: Storage,
        @Value("\${spring.cloud.gcp.storage.bucket}")
        private val bucketName: String
) {
    fun uploadFileToGCS(imageFile: MultipartFile): String {
        val randUUID = UUID.randomUUID().toString()
        val fileEx = getFileEx(imageFile.originalFilename!!)
        val blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, "user-profile/$randUUID")
                        .setContentDisposition("/user-profile")
                        .setContentType("image/$fileEx")
                        .build(),
                imageFile.inputStream
        )
        return "$USER_PROFILE_URL$randUUID"
    }

    fun getFileEx(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1).lowercase()
    }

    fun deleteFileToGCS(
        url: String
    ) {
        val fileName = getFileNameFromUrl(url)
        //TODO: 나중에 적절한 예외를 던지는것으로 변경
        val blob = storage.get(bucketName, "user-profile/${fileName}") ?: throw RuntimeException("해당 파일은 존재하지 않습니다.")

        val precondition: Storage.BlobSourceOption = Storage.BlobSourceOption.generationMatch(blob.generation)

        storage.delete(bucketName, "user-profile/${fileName}", precondition)
    }

    fun getFileNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }
}