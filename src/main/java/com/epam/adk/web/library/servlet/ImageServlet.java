package com.epam.adk.web.library.servlet;

import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.exception.ServletConfigurationException;
import com.epam.adk.web.library.propmanager.PropertiesManager;
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

    private static final String DOT = ".";
    private static final String JPG = "jpg";
    private static final String PNG = "png";
    private static final String IMAGE_PARAMETER = "image";
    private static final String IMAGE_PNG_CONTENT_TYPE = "image/png";
    private static final String IMAGE_JPEG_CONTENT_TYPE = "image/jpeg";
    private static final String IMAGE_SERVLET_PROPERTIES_FILE_NAME = "image.servlet.properties";

    private static Map<String, String> contentTypes = new HashMap<>();
    private static PropertiesManager propertiesManager;

    private String imagesPath = propertiesManager.get("images.path");
    private String contentTypeHeaderName = propertiesManager.get("header.content.type");
    private String contentLengthHeaderName = propertiesManager.get("header.content.length");
    private String contentDispositionHeaderName = propertiesManager.get("header.content.disposition");

    static {
        contentTypes.put(JPG, IMAGE_JPEG_CONTENT_TYPE);
        contentTypes.put(PNG, IMAGE_PNG_CONTENT_TYPE);
    }

    @Override
    public void init() throws ServletException {
        log.debug("Start ImageServlet initialization servlet.");
    }

    public static void configure() throws ServletConfigurationException {
        try {
            propertiesManager = new PropertiesManager(IMAGE_SERVLET_PROPERTIES_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ServletConfigurationException("Error: FrontControllerServlet class, configure() method.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String image = request.getParameter(IMAGE_PARAMETER);

        if (image == null) {
            log.error("image = NULL");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String format = image.substring(image.indexOf(DOT) + 1); // obtaining picture format from the name
        String contentType = contentTypes.get(format);

        log.debug("ImageServlet class, doGet() method: format = {}, contentType = {}", format, contentType);

        if (contentType != null) {
            response.setContentType(contentType);
        }

        ServletOutputStream out = response.getOutputStream();

        File file = new File(imagesPath, image);

        response.setHeader(contentTypeHeaderName, getServletContext().getMimeType(image));
        response.setHeader(contentLengthHeaderName, String.valueOf(file.length()));
        response.setHeader(contentDispositionHeaderName, "inline; filename=\"" + file.getName() + "\"");

        Path filePath = file.toPath();

        Files.copy(filePath, out);
    }
}
