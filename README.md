# silver-bullet

# 目录结构

本系统工程采用maven进行代码管理
>silver-bullet
>>amain　　　　　　　　　//存放WEB部分内容<br> 
>>cms-core　　　　　　　//通用的文章管理core，包括对文件的管理、文章类别管理，包括评论、回复<br> 
>>code-generator　　　　//基于mybatic的代码自动生成<br> 
>>doc　　　　　　　　　　//文档<br> 
>>sys-core　　　　　　　//存放配置、通用工具等<br> 
>>sys-auth　　　　　　　//存放用户、机构、菜单管理等<br> 
>>sys-log　　　　　　　//日志管理<br> 
>>sys-message　　　　　//消息模块，集成activemq<br> 
>>sys-params　　　　　//数据字典模块<br> 
>>sys-tag　　　　　　　//标签模块<br> 
>>ztest　　　　　　　　//测试demo模块<br> 


# 操作手册
## sys-core
>SpringUtil类，用于动态获取spring bean的类
```java
SpringUtil.getBean(String name)
```
>TreeNode类，用于生成树状结构的通用类
>>例子
```java
List<Map<String, String>> listMenus = iSysAuthActionTreeService.getActionsByUserId(sysAuthUser.getId());
List<TreeNode> nodes = TreeNode.formatNodes2TreeNode(listMenus,"name","parent_id", "id", "url","permission","params", "icon");
```

## cms-core
>添加文件
```java
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
```

>添加文章
```java
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
```
