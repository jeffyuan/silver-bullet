package com.silverbullet.auth.sysconfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * Created by jeffyuan on 2018/3/8.
 */
public class SysDictionary {

    // 系统角色的初始化表
    // 1:超级用户 2:系统用户 3:其他 4: 系统管理员 5：安全管理员 6：审计员
    private static Map<String, String> mapUserRole;

    static {
        mapUserRole = new HashMap<String, String>();
        mapUserRole.put("1", "SUPERMAN");
        mapUserRole.put("2", "SYSUSER");
        mapUserRole.put("3", "OTHER");
        mapUserRole.put("4", "SYSADMIN");
        mapUserRole.put("5", "SYSSSO");
        mapUserRole.put("6", "SYSADUIT");
    }

    public SysDictionary() {

    }

    /**
     * 根据roleId查找角色代号
     * @param roleId
     * @return
     */
    public static String getRoleNo(String roleId) {
        if (mapUserRole.containsKey(roleId)) {
            return mapUserRole.get(roleId);
        }

        return "";
    }
}
