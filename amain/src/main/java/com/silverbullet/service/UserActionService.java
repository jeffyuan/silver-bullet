package com.silverbullet.service;

import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthActionTreeService;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.utils.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户菜单权限的service
 * Created by jeffyuan on 2018/3/9.
 */
@Service
public class UserActionService {

    @Autowired
    private ISysAuthActionTreeService iSysAuthActionTreeService;
    @Autowired
    private ISysAuthUserService iSysAuthUserService;

    /**
     * 获取用户左侧菜单
     * @param userName 用户账号
     * @return
     */
    public List<TreeNode> getUserLeftMenu(String userName) {

        SysAuthUser sysAuthUser = iSysAuthUserService.getOneByUserName(userName);
        if (sysAuthUser == null) {
            return new ArrayList<TreeNode>();
        }

        List<Map<String, String>> listMenus = iSysAuthActionTreeService.getActionsByUserId(sysAuthUser.getId());
        if (listMenus == null) {
            return new ArrayList<TreeNode>();
        }

        List<TreeNode> nodes = TreeNode.formatNodes2TreeNode(listMenus,"name","parent_id", "id", "url",
                "permission","params", "icon");
        if (nodes != null) {
            return nodes;
        }

        return new ArrayList<TreeNode>();
    }
}
