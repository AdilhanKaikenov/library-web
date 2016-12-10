package com.epam.adk.web.library.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * ImageServlet class created on 01.12.2016
 *
 * @author Kaikenov Adilhan
 */
public final class ImageServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ImageServlet.class);
    private static final String IMAGE_PARAMETER = "image";
    private static final String IMAGE_PATH = "D:\\images";

    private static Map<String, String> contentTypes = new HashMap<>();

    static {
        contentTypes.put("jpg", "image/jpeg");
        contentTypes.put("png", "image/png");
    }

    @Override
    public void init() throws ServletException {
        log.debug("Start ImageServlet initialization servlet.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String image = request.getParameter(IMAGE_PARAMETER);

        if (image == null) {
            log.error("image = NULL");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String format = image.substring(image.indexOf(".") + 1);
        String contentType = contentTypes.get(format);

        log.trace("ImageServlet class, doGet() method: format = {}, contentType = {}", format, contentType);

        if (contentType != null) {
            response.setContentType(contentType);
        }

        ServletOutputStream out = response.getOutputStream();

        File file = new File(IMAGE_PATH, image);

        response.setHeader("Content-Type", getServletContext().getMimeType(image));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        Path filePath = file.toPath();

        Files.copy(filePath, out);
    }
}
