package com.plygrnd.ktor.kfiles.route

import com.plygrnd.ktor.kfiles.poi.readExcelFile
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.poi.hssf.usermodel.HSSFSheet
import java.io.File

fun Application.poiFileRoute() {
    routing {
        var fileDescription = ""
        var filename = ""
        route("/poi") {
            post("/read") {
                val multipartData = call.receiveMultipart()

                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            fileDescription = part.value
                        }
                        is PartData.FileItem -> {
                            filename = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
                            File("uploads/$filename").writeBytes(fileBytes).let {
                                val uploadedFile = File("uploads/$filename")
                                readExcelFile(uploadedFile)
                            }

                        }
                        else -> Unit
                    }
                }
                call.respond("$fileDescription is uploaded to 'uploads/$filename'")
            }
        }
    }
}