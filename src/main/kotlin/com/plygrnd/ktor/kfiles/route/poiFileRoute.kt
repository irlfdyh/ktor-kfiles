package com.plygrnd.ktor.kfiles.route

import com.plygrnd.ktor.kfiles.entity.User
import com.plygrnd.ktor.kfiles.poi.createExcelFile
import com.plygrnd.ktor.kfiles.poi.readExcelFile
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.poiFileRoute() {
    routing {
        var fileDescription = ""
        var filename = ""
        route("/poi") {
            post("/read") {
                val multipartData = call.receiveMultipart()
                val users = mutableListOf<User>()

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
                                users.addAll(readExcelFile(uploadedFile))
                            }

                        }
                        else -> Unit
                    }
                }
                call.respond(users)
            }
            get("/{filename}/{filetype}") {
                val fileName = call.parameters["filename"] ?: "noname"
                val fileType = call.parameters["filetype"] ?: "xlsx"
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "$fileName.$fileType")
                        .toString()
                )
                call.respondFile(createExcelFile(fileName, dummies))
            }
        }
    }
}

val dummies = listOf(
    User(
        no = 1,
        name = "Ahmad",
        address = "Jl. Sudirman"
    ),
    User(
        no = 2,
        name = "Gaga",
        address = "Jl. Ahmad Yani No. 31"
    ),
    User(
        no = 3,
        name = "Sudirman",
        address = "Jl. Soklat No. 55"
    )
)