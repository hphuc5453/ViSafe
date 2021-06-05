package hphuc.project.visafe_version1.core.base.config;

public interface ConfigSaver {
    String CONFIG_PAGER = "config_pager";
    String CONFIG_SETTING_SAVED_IS_FIRST_LOGIN_APP = "config_setting_saved_is_first_login_app";
    String CONFIG_SETTING_PASSPORT = "config_setting_saved_passport";
    String CONFIG_SETTING_KEY_LOGIN = "config_setting_saved_key_login";
    String CONFIG_SETTING_PUSH_TOKEN= "config_setting_saved_push_token";
    String CONFIG_SETTING_FIRST_TIME_LOGIN= "config_setting_first_time_login";
    String CONFIG_SETTING_KEY_MAP_STYLE = "config_setting_saved_key_map_style";
    String CONFIG_SETTING_LIST_CONTACTS = "config_setting_saved_key_list_contacts";
    String CONFIG_SETTING_LIST_SUPPORT = "config_setting_saved_key_list_support";

    void save(String key, Object data);

    <T> T get(String key);

    void delete(String key);

    void deleteAll();
}
