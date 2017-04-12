package me.dalianmao.util.web.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractView;

/**
 * pdf显示，支持类路径和文件系统路径，不使用iText和lowagie，直接把文件写到输出流中<br/>
 *
 * 使用方式：
 * <li>在controller中：</li>
 *
 * <pre>
 * ModelAndView modelAndView = new ModelAndView("pdfView"); // 指定使用的view
 * modelAndView.addObject(PdfView.PATH_KEY, "classpath:pdf_file_path.pdf");
 * </pre>
 *
 * <li>在配置中：</li>
 *
 * <pre>
 * &#64;Bean(name = "pdfView") // 创建pdfView
 * public PdfView getContractView() {
 *     return new PdfView();
 * }
 * </pre>
 *
 * @author xiezhenzong
 *
 * @see org.springframework.web.servlet.view.document.AbstractPdfView
 * @see org.springframework.web.servlet.view.document.AbstractPdfStamperView
 * @see ApplicationContext#getResource(String)
 */
public class PdfView extends AbstractView {

    public static final String PATH_KEY = "pdf_path";

    public PdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        InputStream inputStream = getPdfFileInputStream(model);
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        try {
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        } finally {
            inputStream.close();
        }

        // Flush to HTTP response.
        writeToResponse(response, baos);
    }

    private InputStream getPdfFileInputStream(Map<String, Object> model) throws IOException {
        String pdfPath = (String) model.get(PATH_KEY);
        Resource resource = getApplicationContext().getResource(pdfPath);
        InputStream inputStream = resource.getInputStream();
        return inputStream;
    }
}
