package org.QAHexagon.testng.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import java.nio.charset.StandardCharsets;

public class csvHelper {
    public static Iterator<Object[]> readCSV(String path) {
        List<Object[]> data = new ArrayList<>();
        CSVFormat format = CSVFormat.EXCEL.builder()
                .setHeader()
                .setTrim(false)
                .setIgnoreSurroundingSpaces(true)
                .setIgnoreHeaderCase(true)
                .setSkipHeaderRecord(true).build();

        try (
                InputStream is = csvHelper.class
                        .getClassLoader()
                        .getResourceAsStream(path);
                BOMInputStream bomIn = BOMInputStream.builder().setInputStream(is).get();
                Reader reader = new InputStreamReader(bomIn, StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = format.parse(reader);

            for (CSVRecord record : records) {
                Map<String, String> row = record.toMap();
                data.add(new Object[] { row });
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV", e);
        }

        return data.iterator();
    }
}
