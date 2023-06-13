package com.example.fleetfare.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.example.fleetfare.Models.Record;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    private static final String TAG = "ExcelExporter";

    public static void exportToExcel(Context context, List<Record> records, String date) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Record-"+date);

        // Add header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Vehicle Number");
//        headerRow.createCell(2).setCellValue("Comments");
        headerRow.createCell(2).setCellValue("Weight(Kg)");
        headerRow.createCell(3).setCellValue("Deduction Percent");
        headerRow.createCell(4).setCellValue("Weight After Deduction");
        headerRow.createCell(5).setCellValue("Weight(Mann)");
        headerRow.createCell(6).setCellValue("Price Per Mann");
        headerRow.createCell(7).setCellValue("Total Payment");

        // Add data rows
        int rownum = 1;
        for (Record record : records) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(record.getDate());
            row.createCell(1).setCellValue(record.getVehicleNO());
//            row.createCell(2).setCellValue(record.comments);
            row.createCell(2).setCellValue(record.getWeight());
            row.createCell(3).setCellValue(record.getWeightDeductionPercent());
            row.createCell(4).setCellValue(record.getWeightAfterDeduction());
            row.createCell(5).setCellValue(record.getWeightInMann());
            row.createCell(6).setCellValue(record.getPricePerMann());
            row.createCell(7).setCellValue(record.getTotalPayement());
        }

        // Save the workbook to a file
        String fileName = date+"-record.xls";
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "FleetFare");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            Log.d(TAG, "File saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Share the file using an intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, "Share Excel file"));
    }

}
