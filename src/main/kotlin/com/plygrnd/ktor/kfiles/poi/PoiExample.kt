package com.plygrnd.ktor.kfiles.poi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

suspend fun readExcelFile(file: File) {
    withContext(Dispatchers.IO) {
        val inputStream = FileInputStream(file)
        val wb = XSSFWorkbook(inputStream)
    }
}