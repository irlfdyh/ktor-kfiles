package com.plygrnd.ktor.kfiles.poi

import com.plygrnd.ktor.kfiles.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

suspend fun readExcelFile(file: File): List<User> {
    val users = mutableListOf<User>()
    withContext(Dispatchers.IO) {
        val inputStream = FileInputStream(file)
        val wb = XSSFWorkbook(inputStream)
        wb.getSheetAt(0).let { sheet ->
            sheet.rowIterator().forEach { row ->
                val user = User().also {
                    it.no = row.getCell(0).numericCellValue.toInt()
                    it.name = row.getCell(1).stringCellValue
                    it.address = row.getCell(2).stringCellValue
                }
                users.add(user)
            }
        }
    }
    return users
}

suspend fun createExcelFile(filename: String, users: List<User>): File {
    val userFile = File("downloads/$filename.xlsx")
    withContext(Dispatchers.IO) {
        XSSFWorkbook().use { workbook ->
            workbook.createSheet().let { sheet ->
                sheet.createSheetHeader()
                users.forEachIndexed { index, user ->
                    sheet.createRow(index + 1).apply {
                        createCell(0).setCellValue(user.no.toString())
                        createCell(1).setCellValue(user.name)
                        createCell(2).setCellValue(user.address)
                    }
                }
            }
            FileOutputStream(userFile).use { workbook.write(it) }
        }
    }
    return userFile
}

fun XSSFSheet.createSheetHeader(): XSSFRow {
    return this.createRow(0).apply {
        this.createCell(0).setCellValue("Nomor")
        this.createCell(1).setCellValue("Nama")
        this.createCell(2).setCellValue("Alamat")
    }
}