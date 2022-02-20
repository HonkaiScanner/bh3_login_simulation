package com.github.haocen2004.login_simulation.login;

import com.github.haocen2004.login_simulation.data.RoleData;

public interface LoginImpl {
    void login();

    void logout();

    RoleData getRole();

    boolean isLogin();

    String getUsername();

}
