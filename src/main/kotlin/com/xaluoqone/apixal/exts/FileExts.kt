package com.xaluoqone.apixal.exts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.source
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

suspend fun MultipartFile.save(
    saveName: String = originalFilename!!,
    savePath: String = "files/",
) = withContext(Dispatchers.IO) {
    val filePath = "$savePath$saveName"
    val file = "/var/www/com.xaluoqone.apixal/$filePath".file()
    Files.copy(inputStream, Path.of(file.path), StandardCopyOption.REPLACE_EXISTING)
    "https://api.xaluoqone.com/$filePath"
}

suspend fun InputStream.save(
    name: String,
    savePath: String = "files/",
) = withContext(Dispatchers.IO) {
    val filePath = "$savePath$name"
    val path = "/var/www/com.xaluoqone.apixal/$filePath".path()
    FileSystem.SYSTEM.write(path) { writeAll(source()) }
    "https://api.xaluoqone.com/$filePath"
}

fun String.path(): okio.Path {
    val path = toPath()
    path.toFile().init()
    return path
}

fun String.file(): File {
    val file = toPath().toFile()
    file.init()
    return file
}

fun File.init() {
    if (parentFile?.exists() != true) {
        parentFile.mkdirs()
    }
    if (!exists()) {
        createNewFile()
    }
}