package com.plygrnd.ktor.kfiles.route

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.basicFileRoute() {
    routing {
        var fileDescription = ""
        var fileName = ""
        route("/basic") {
            post("/upload") {
                val multipartData = call.receiveMultipart()

                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            fileDescription = part.value
                        }
                        is PartData.FileItem -> {
                            fileName = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
                            File("uploads/$fileName").writeBytes(fileBytes)
                        }
                        else -> Unit
                    }
                }
                call.respond("$fileDescription is uploaded to 'uploads/$fileName'")
            }
            get("/upload/{filename}") {
                val filename = call.parameters["filename"] ?: "unknown"
                try {
                    val file = File("uploads/$filename")
                    call.respondFile(file)
                } catch (e: Exception) {
                    call.respond(e.message.toString())
                }
            }
            delete("/upload/{filename}") {
                val filename = call.parameters["filename"] ?: "unknown"
                try {
                    val file = File("uploads/$filename")
                    file.delete().let { succeed ->
                        if (succeed) {
                            call.respond("file removed")
                        } else {
                            call.respond("failed when remove file")
                        }
                    }
                } catch (e: Exception) {
                    call.respond(e.message.toString())
                }
            }
        }
    }
}