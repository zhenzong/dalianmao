package me.dalianmao.util.file;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletResponse;

import me.dalianmao.util.Constants;
import me.dalianmao.util.collection.IterableUtil;

/**
 * csv文件工具
 *
 * @author xiezhenzong
 *
 */
public class CsvFile {

    public static final char CSV_COLUMN_SEPARATOR = Constants.COMMA;

    public static final String CSV_RN = Constants.WINDOW_RN;

    public static <T> void download(HttpServletResponse response, String fileName, List<String> header, List<T> data,
            Function<T, List<String>> converter) throws IOException {
        download(response, "UTF-8", fileName, header, data, converter);
    }

    public static <T> void download(HttpServletResponse response, String charset,String fileName, List<String> header, List<T> data,
            Function<T, List<String>> converter) throws IOException {
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(charset);
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, charset));
        try (OutputStream output = response.getOutputStream()) {
            write(output, charset, header, data, converter);
        }
    }

    public static <T> void write(OutputStream output, List<String> header, List<T> data, Function<T, List<String>> converter)
            throws IOException {
        write(output, "UTF-8", header, data, converter);
    }

    public static <T> void write(OutputStream output, String charset, List<String> header, List<T> data,
            Function<T, List<String>> converter) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(IterableUtil.join(header, CSV_COLUMN_SEPARATOR)).append(CSV_RN);
        for (T t : data) {
            if (t != null) {
                builder.append(IterableUtil.join(converter.apply(t), CSV_COLUMN_SEPARATOR)).append(CSV_RN);
            }
        }
        output.write(builder.toString().getBytes(charset));
        output.flush();
    }

}
