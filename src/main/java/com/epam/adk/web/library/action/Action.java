package com.epam.adk.web.library.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Interface Action created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public interface Action {

    String execute(HttpServletRequest request, HttpServletResponse response);

}
