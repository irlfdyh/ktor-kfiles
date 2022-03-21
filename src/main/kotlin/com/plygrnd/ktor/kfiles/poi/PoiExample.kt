package com.plygrnd.ktor.kfiles.poi

import com.plygrnd.ktor.kfiles.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

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