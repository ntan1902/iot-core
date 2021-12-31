package com.iot.server.common.entity;

public class EntityConstants {

    public static final String ID_PROPERTY = "id";
    public static final String CREATED_AT_PROPERTY = "created_at";
    public static final String UPDATED_AT_PROPERTY = "updated_at";
    public static final String CREATE_UID_PROPERTY = "create_uid";
    public static final String UPDATE_UID_PROPERTY = "update_uid";
    public static final String DELETED_PROPERTY = "deleted";

    public static final String USER_TABLE_NAME = "tb_user";
    public static final String USER_EMAIL_PROPERTY = "email";
    public static final String USER_FIRST_NAME_PROPERTY = "first_name";
    public static final String USER_LAST_NAME_PROPERTY = "last_name";
    public static final String USER_TENANT_ID_PROPERTY = "tenant_id";
    public static final String USER_CUSTOMER_ID_PROPERTY = "customer_id";

    public static final String USER_CREDENTIALS_TABLE_NAME = "user_credentials";
    public static final String USER_CREDENTIALS_USER_ID_PROPERTY = "user_id";
    public static final String USER_CREDENTIALS_PASSWORD_PROPERTY = "password";
    public static final String USER_CREDENTIALS_ENABLED_PROPERTY = "enabled";
    public static final String USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY = "activate_token";
    public static final String USER_CREDENTIALS_RESET_TOKEN_PROPERTY = "reset_token";

    public static final String ROLE_TABLE_NAME = "role";
    public static final String ROLE_NAME_PROPERTY = "name";

    public static final String USER_ROLE_TABLE_NAME = "user_role";
    public static final String USER_ROLE_USER_ID_PROPERTY = "user_id";
    public static final String USER_ROLE_ROLE_ID_PROPERTY = "role_id";

    public static final String TS_KV_TABLE_NAME = "ts_kv";
    public static final String TS_KV_ENTITY_ID_PROPERTY = "entity_id";
    public static final String TS_KV_TS_PROPERTY = "ts";
    public static final String TS_KV_KEY_PROPERTY = "key";
    public static final String TS_KV_TYPE_PROPERTY = "type";
    public static final String TS_KV_BOOL_V_PROPERTY = "bool_v";
    public static final String TS_KV_DOUBLE_V_PROPERTY = "double_v";
    public static final String TS_KV_LONG_V_PROPERTY = "long_v";
    public static final String TS_KV_STRING_V_PROPERTY = "string_v";
    public static final String TS_KV_JSON_V_PROPERTY = "json_v";

    private EntityConstants() {
    }

}
