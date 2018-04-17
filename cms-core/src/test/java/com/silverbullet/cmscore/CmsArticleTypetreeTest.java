package com.silverbullet.cmscore;

import com.silverbullet.cms.CmsCoreApplication;
import com.silverbullet.cms.domain.CmsArticleTypetree;
import com.silverbullet.cms.service.ICmsArticleTypetreeService;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.utils.TreeNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by jeffyuan on 2018/4/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmsCoreApplication.class)
public class CmsArticleTypetreeTest {

    @Autowired
    private ICmsArticleTypetreeService iCmsArticleTypetreeService;

    @Test
    public void testInsertTypeTree() {
        CmsArticleTypetree cmsArticleTypetree = new CmsArticleTypetree();
        cmsArticleTypetree.setId(ToolUtil.getUUID());
        cmsArticleTypetree.setName("首页");
        cmsArticleTypetree.setCreateUser("40288b854a329988014a329a12f30002");
        cmsArticleTypetree.setModifyUser("40288b854a329988014a329a12f30002");
        cmsArticleTypetree.setModifyTime(Calendar.getInstance().getTime());
        cmsArticleTypetree.setCreateTime(Calendar.getInstance().getTime());
        cmsArticleTypetree.setDomain("back");
        cmsArticleTypetree.setModule("NEWS");
        cmsArticleTypetree.setParentId("NONE");
        cmsArticleTypetree.setPath(cmsArticleTypetree.getId());
        cmsArticleTypetree.setSort(1);
        cmsArticleTypetree.setState("1");
        cmsArticleTypetree.setType("1");

        assert iCmsArticleTypetreeService.Insert(cmsArticleTypetree) == true;

        String parentId = cmsArticleTypetree.getId();
        cmsArticleTypetree = new CmsArticleTypetree();
        cmsArticleTypetree.setId(ToolUtil.getUUID());
        cmsArticleTypetree.setName("图片导航");
        cmsArticleTypetree.setCreateUser("40288b854a329988014a329a12f30002");
        cmsArticleTypetree.setModifyUser("40288b854a329988014a329a12f30002");
        cmsArticleTypetree.setModifyTime(Calendar.getInstance().getTime());
        cmsArticleTypetree.setCreateTime(Calendar.getInstance().getTime());
        cmsArticleTypetree.setDomain("back");
        cmsArticleTypetree.setModule("NEWS");
        cmsArticleTypetree.setParentId(parentId);
        cmsArticleTypetree.setPath(parentId+cmsArticleTypetree.getId());
        cmsArticleTypetree.setSort(1);
        cmsArticleTypetree.setState("1");
        cmsArticleTypetree.setType("2");
        assert iCmsArticleTypetreeService.Insert(cmsArticleTypetree) == true;

        parentId = "NONE";
        cmsArticleTypetree = new CmsArticleTypetree();
        cmsArticleTypetree.setId(ToolUtil.getUUID());
        cmsArticleTypetree.setName("新闻动态");
        cmsArticleTypetree.setCreateUser("40288b854a329988014a329a12f30002");
        cmsArticleTypetree.setModifyUser("40288b854a329988014a329a12f30002");
        cmsArticleTypetree.setModifyTime(Calendar.getInstance().getTime());
        cmsArticleTypetree.setCreateTime(Calendar.getInstance().getTime());
        cmsArticleTypetree.setDomain("back");
        cmsArticleTypetree.setModule("NEWS");
        cmsArticleTypetree.setParentId(parentId);
        cmsArticleTypetree.setPath(cmsArticleTypetree.getId());
        cmsArticleTypetree.setSort(2);
        cmsArticleTypetree.setState("1");
        cmsArticleTypetree.setType("1");
        assert iCmsArticleTypetreeService.Insert(cmsArticleTypetree) == true;
    }

    @Test
    public void deleteArticleTypeTree() {
        assert iCmsArticleTypetreeService.delete("d4814a027f3e410ca85987bd9295ec11") == true;
    }

    @Test
    public void getArticleTypeTree() {

        List<Map<String, Object>> cmsArticleTypetreeList = iCmsArticleTypetreeService.findListByModule("NEWS", "back", "NONE");

        List<TreeNode> nodes = TreeNode.formatNodes2TreeNode(cmsArticleTypetreeList,
                "name","parent_id", "id");

        Assert.assertNotNull(nodes);
    }
}
