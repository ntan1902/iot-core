package com.iot.server.dao.entity;

public class EntityConstants {

  private EntityConstants() {}

  public static final String ID_PROPERTY = "id";
  public static final String CREATED_AT_PROPERTY = "created_at";
  public static final String UPDATED_AT_PROPERTY = "updated_at";

  public static final String USER_TABLE_NAME = "tb_user";
  public static final String USER_EMAIL_PROPERTY = "email";
  public static final String USER_FIRST_NAME_PROPERTY = "first_name";
  public static final String USER_LAST_NAME_PROPERTY = "last_name";
  public static final String USER_AUTHORITY_PROPERTY = "authority";

  public static final String USER_CREDENTIALS_TABLE_NAME = "user_credentials";
  public static final String USER_CREDENTIALS_USER_ID_PROPERTY = "user_id";
  public static final String USER_CREDENTIALS_PASSWORD_PROPERTY = "password";
  public static final String USER_CREDENTIALS_ENABLED_PROPERTY = "enabled";
  public static final String USER_CREDENTIALS_ACTIVATE_TOKEN_PROPERTY = "activate_token";
  public static final String USER_CREDENTIALS_RESET_TOKEN_PROPERTY = "reset_token";

}
