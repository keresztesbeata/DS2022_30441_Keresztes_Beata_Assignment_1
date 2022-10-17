package lab.ds2022_assignment_1.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";
    public static final String LOGOUT_PATH = "/logout";
    public static final String CURRENTLY_LOGGED_IN_USER_PATH = "/current_user";
    public static final String API = "/api";
    public static final String ACCOUNTS_BASE_PATH = "/accounts";
    public static final String ACCOUNTS_PATH = API + ACCOUNTS_BASE_PATH;
    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_USERNAME = "username";
    public static final String ACCOUNT_ID_PATH = ACCOUNTS_PATH + "/{" + ACCOUNT_ID + "}";
    public static final String ACCOUNT_USERNAME_PATH = ACCOUNTS_PATH + "/username" + "/{" + ACCOUNT_USERNAME + "}";
    public static final String DEVICES_BASE_PATH = "/devices";
    public static final String DEVICES_PATH = API + DEVICES_BASE_PATH;
    public static final String DEVICE_ID = "id";
    public static final String DEVICE_ID_PATH = DEVICES_PATH + "/{" + DEVICE_ID + "}";
    public static final String LINK_DEVICE_PATH = API + "/link_device";
    public static final String CLIENT = "/client";
    public static final String CLIENT_ACCOUNT_PATH = CLIENT + "/account";
    public static final String CLIENT_DEVICES_PATH = CLIENT + "/devices";
    public static final String CLIENT_DEVICE_ID_PATH = CLIENT_DEVICES_PATH + "/{" + DEVICE_ID + "}";
    public static final String ENERGY_CONSUMPTION_PATH = CLIENT + "/energy_consumption";
    public static final String DEVICE_ENERGY_CONSUMPTION_PATH = ENERGY_CONSUMPTION_PATH + "/{" + DEVICE_ID + "}";
}
