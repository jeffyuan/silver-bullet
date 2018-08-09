package com.silverbullet.cmscore;

import com.silverbullet.cms.CmsCoreApplication;
import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleContent;
import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.cms.pojo.CmsArticleEntity;
import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.cms.service.ICmsRepairFaultService;
import com.silverbullet.cms.service.impl.CmsArticleService;
import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jeffyuan on 2018/4/1.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmsCoreApplication.class)
public class CmsArticleServiceTest {

    @Autowired
    private CmsArticleService cmsArticleService;


    @Test
    public void testInsertFile() throws FileNotFoundException {

        CmsFileInfo cmsFileInfo = new CmsFileInfo();
        cmsFileInfo.setCreateUser("40288b854a329988014a329a12f30002");
        cmsFileInfo.setCreateUsername("管理员");
        cmsFileInfo.setFileExname("doc");
        cmsFileInfo.setFileType("1");
        cmsFileInfo.setFileName("addin.docx");

        InputStream inputStream = new FileInputStream("C:\\Users\\GESOFT\\Documents\\addin.docx");
        cmsFileInfo.setInputStream(inputStream);

        CmsArticleFile cmsArticleFile = cmsArticleService.insertFile(cmsFileInfo);
        Assert.notNull(cmsArticleFile);

        // 更新文件
        cmsArticleFile.setContVersion(2);
        cmsArticleFile.setContModifyReason("test");
        cmsArticleFile.setContText("test content");
        cmsArticleFile.setModifyTime(Calendar.getInstance().getTime());

        cmsArticleFile = cmsArticleService.updateFile(cmsArticleFile, null);
        Assert.notNull(cmsArticleFile);
    }

    @Test
    public void deleteFile() {

        assert cmsArticleService.deleteFile("58d5b9b489c349a1a5d26909c2b33383") == true;
    }

    @Test
    public void createArticle() throws FileNotFoundException {

        CmsArticleEntity cmsArticleEntity = new CmsArticleEntity();

        CmsArticle cmsArticle = new CmsArticle();
        cmsArticle.setArtType("1"); //文档类型
        cmsArticle.setTitle("测试文档html");
        cmsArticle.setAbs("文档摘要信息");
        cmsArticle.setAuthor("40288b854a329988014a329a12f30002");
        cmsArticle.setPublicTime(Calendar.getInstance().getTime());
        cmsArticle.setTagkey("标签1");
        cmsArticle.setSource("自己");
        cmsArticle.setTopLevel(1);
        cmsArticle.setArtState("1");
        cmsArticle.setCreateTime(Calendar.getInstance().getTime());
        cmsArticle.setModifyTime(Calendar.getInstance().getTime());
        cmsArticle.setCreateUser("40288b854a329988014a329a12f30002");
        cmsArticle.setCreateUsername("管理员");
        cmsArticle.setModifyUser("40288b854a329988014a329a12f30002");
        cmsArticle.setModifyUsername("管理员");
        cmsArticle.setWriteAuthority("0");
        cmsArticle.setReadAuthority("0");
        cmsArticle.setModule("article");
        cmsArticle.setModuleFilterKey("");
        cmsArticle.setLastUpdateTime(Calendar.getInstance().getTime());
        cmsArticle.setState("1");

        cmsArticleEntity.setCmsArticle(cmsArticle);


        CmsFileInfo artImage = new CmsFileInfo();
        artImage.setCreateUser("40288b854a329988014a329a12f30002");
        artImage.setCreateUsername("管理员");
        artImage.setFileExname("doc");
        artImage.setFileType("1");
        artImage.setFileName("微信图片_20180121175251.png");
        InputStream inputStream = new FileInputStream("C:\\Users\\GESOFT\\Documents\\微信图片_20180121175251.png");
        artImage.setInputStream(inputStream);

        cmsArticleEntity.setCmsArticleImage(artImage);

        CmsArticleContent cmsArticleContent = new CmsArticleContent();
        cmsArticleContent.setModifyTime(Calendar.getInstance().getTime());
        cmsArticleContent.setContHtml("<html><body>测试内容</body></html>");
        cmsArticleContent.setContModifyReason("");
        cmsArticleContent.setContText("测试内容");
        cmsArticleContent.setContState("1");
        cmsArticleContent.setContVersion(1);
        cmsArticleContent.setCreateTime(Calendar.getInstance().getTime());
        cmsArticleContent.setCreateUser("40288b854a329988014a329a12f30002");
        cmsArticleContent.setCreateUsername("管理员");
        cmsArticleContent.setModifyUser("40288b854a329988014a329a12f30002");
        cmsArticleContent.setModifyUsername("管理员");

        cmsArticleEntity.setCmsArticleContent(cmsArticleContent);


        List<CmsFileInfo> cmsFileInfoList = new ArrayList<CmsFileInfo>();

        CmsFileInfo file = new CmsFileInfo();
        file.setCreateUser("40288b854a329988014a329a12f30002");
        file.setCreateUsername("管理员");
        file.setFileExname("doc");
        file.setFileType("1");
        file.setFileName("addin.docx");

        inputStream = new FileInputStream("C:\\Users\\GESOFT\\Documents\\addin.docx");
        file.setInputStream(inputStream);
        cmsFileInfoList.add(file);


        cmsArticleEntity.setCmsArticleFileList(cmsFileInfoList);

        assert cmsArticleService.createArticle(cmsArticleEntity) == true;
    }

    @Test
    public void deleteArticle() {
        assert cmsArticleService.delete("0cda8674808f4e4d81b1d28b769f1ac0") == true;
    }
}
