package lab.ds2022_assignment_1.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String API = "/api";
    public static final String ACCOUNTS_BASE_PATH = "/accounts";
    public static final String ACCOUNTS_PATH = API + ACCOUNTS_BASE_PATH;

    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_ID_PATH = ACCOUNTS_PATH + "/{" + ACCOUNT_ID + "}";
    public static final String DELETE_ACCOUNT_PATH = ACCOUNT_ID_PATH + "/delete";
}
