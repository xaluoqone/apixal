package com.xaluoqone.apixal.exts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.source
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

suspend fun MultipartFile.save(
    saveName: String = originalFilename!!,
    savePath: String = "files/",
) = withContext(Dispatchers.IO) {
    val filePath = "$savePath$saveName"
    val localPath = Path.of("/var/www/com.xaluoqone.apixal/$filePath")
    Files.copy(inputStream, localPath, StandardCopyOption.REPLACE_EXISTING)
    "https://api.xaluoqone.com/$filePath"
}

suspend fun InputStream.save(
    name: String,
    savePath: String = "files/",
) = withContext(Dispatchers.IO) {
    val filePath = "$savePath$name"
    val path = "/var/www/com.xaluoqone.apixal/$filePath".toPath()
    val file = path.toFile()
    if (!file.parentFile.exists()) file.parentFile.mkdirs()
    if (!file.exists()) file.createNewFile()
    FileSystem.SYSTEM.write(path) { writeAll(source()) }
    "https://api.xaluoqone.com/$filePath"
}