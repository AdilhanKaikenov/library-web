package com.epam.adk.web.library.tag;

import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Role;

import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * HasRoleTag class created on 24.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class HasRoleTag extends ConditionalTagSupport {

    private User user;
    private Role role;
    private boolean result;

    public HasRoleTag() {
        release();
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
        } else {
            this.user = new User();
            this.user.setRole(Role.ANONYMOUS);
        }
    }

    public void setRole(String role) {
        this.role = Role.from(role);
    }

    @Override
    protected boolean condition() {
        result = user.getRole().equals(role);
        return result;
    }

    @Override
    public void release() {
        super.release();
        result = false;
    }


}
