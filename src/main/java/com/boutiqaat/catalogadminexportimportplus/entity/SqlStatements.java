package com.boutiqaat.catalogadminexportimportplus.entity;

public class SqlStatements {

    public final static String UPDATE_USER_CONFIGURATION_SQL = "UPDATE user_configuration " +
            "SET is_default = 0 " +
            "WHERE user_id = :userId AND page_id = :pageId ";

    public final static String MARK_USER_CONFIGURATION_AS_DEFAULT_SQL = "UPDATE user_configuration " +
            "SET is_default = 1 " +
            "WHERE id = :userConfigId ";
}
