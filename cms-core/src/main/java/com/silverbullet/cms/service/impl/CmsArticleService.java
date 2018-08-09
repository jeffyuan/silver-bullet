package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.config.RepositoryProperties;
import com.silverbullet.cms.dao.*;
import com.silverbullet.cms.domain.*;
import com.silverbullet.cms.pojo.CmsArticleEntity;
import com.silverbullet.cms.pojo.CmsArticleEx;
import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.cms.service.ICmsArticleService;
import com.silverbullet.cms.service.IFileService;
import com.silverbullet.cms.util.EntryCreateFactory;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.HtmlUtils;
import com.silverbullet.utils.SpringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;


import java.awt.color.CMMException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章表 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class CmsArticleService implements ICmsArticleService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleService.class);

    @Autowired
    private CmsArticleMapper cmsArticleMapper;
    @Autowired
    private CmsArticleContentMapper cmsArticleContentMapper;

    @Autowired
    private CmsArticleRunBehaviorMapper cmsArticleRunBehaviorMapper;

    @Autowired
    private CmsArticleFileMapper cmsArticleFileMapper;
    @Autowired
    private CmsArticleFileHistoryMapper cmsArticleFileHistoryMapper;
    @Autowired
    private CmsRfArticleFileMapper cmsRfArticleFileMapper;

    @Autowired
    private RepositoryProperties repositoryProperties;

    @Override
    public int countNum() {
        return cmsArticleMapper.countNum();
    }

    @Override
    public BaseDataResult<CmsArticle> list(String module, String moduleFilterKey, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsArticle> listResults = new BaseDataResult<CmsArticle>();

        HashMap<String, String> mapParams = new HashMap<String, String>();
        if (module != null && module.length() > 0) {
            mapParams.put("module", module);
        }
        if (moduleFilterKey != null && moduleFilterKey.length() >0) {
            mapParams.put("moduleFilterKey", moduleFilterKey);
        }
        listResults.setResultList(cmsArticleMapper.findArticleByModule(mapParams));
        listResults.setTotalNum(cmsArticleMapper.countNumByModule(mapParams));

        return listResults;
    }

    @Override
    public CmsArticle getOneById(String id) {
        return cmsArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public CmsArticleEx getOneExById(String id) {
        CmsArticle cmsArticle = cmsArticleMapper.selectByPrimaryKey(id);
        if (cmsArticle == null) {
            return null;
        }

        CmsArticleEx cmsArticleEx = new CmsArticleEx();
        try {
            BeanUtils.copyProperties(cmsArticleEx, cmsArticle);

            if (cmsArticle.getArtType().equals("1")) {
                CmsArticleContent articleContent = cmsArticleContentMapper.findByArtId(id);
                if (articleContent != null) {
                    cmsArticleEx.setContHtml(articleContent.getContHtml());
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return cmsArticleEx;
    }

    /**
     *
     *
     * @param cmsArticle
     * @return
     */

    @Transactional
    @Override
    public boolean Update(CmsArticleEx cmsArticle) {
        try {
            CmsArticle cmsArticleNew = getOneById(cmsArticle.getId());
            if (cmsArticleNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            cmsArticleNew.setModifyTime(Calendar.getInstance().getTime());
            cmsArticleNew.setModifyUser(userInfo.getId());
            cmsArticleNew.setModifyUsername(userInfo.getUsername());
            cmsArticleNew.setTitle(cmsArticle.getTitle());
            cmsArticleNew.setAbs(cmsArticle.getAbs());
            cmsArticleNew.setSource(cmsArticle.getSource());
            cmsArticleNew.setTagkey(cmsArticle.getTagkey());
            cmsArticleNew.setTopLevel(cmsArticle.getTopLevel());
            cmsArticleNew.setArtState(cmsArticle.getArtState());
            cmsArticleNew.setReadAuthority(cmsArticle.getReadAuthority());
            cmsArticleNew.setWriteAuthority(cmsArticle.getWriteAuthority());

            // 修改当前内容为历史
            CmsArticleContent cmsArticleContent = cmsArticleContentMapper.findByArtId(cmsArticle.getId());
            if (cmsArticleContent == null) {
                return false;
            }

            // 去除html内容
            String contText = HtmlUtils.removeHtmlTag(cmsArticle.getContHtml());
            // 不相同情况需要更新内容
            if (!cmsArticleContent.getContText().equals(contText)) {
                CmsArticleContent cmsArticleContentNew = new CmsArticleContent();
                BeanUtils.copyProperties(cmsArticleContentNew, cmsArticleContent);

                cmsArticleContentNew.setId(ToolUtil.getUUID());
                cmsArticleContentNew.setContHtml(cmsArticle.getContHtml());
                cmsArticleContentNew.setContText(contText);
                cmsArticleContentNew.setContVersion(cmsArticleContentNew.getContVersion() + 1);
                cmsArticleContentNew.setModifyTime(Calendar.getInstance().getTime());
                cmsArticleContentNew.setModifyUser(userInfo.getId());
                cmsArticleContentNew.setModifyUsername(userInfo.getUsername());

                boolean bRet = cmsArticleContentMapper.insert(cmsArticleContentNew) == 1;
                if (!bRet) {
                    return false;
                }

                cmsArticleContent.setContState("2"); //历史信息
                bRet = cmsArticleContentMapper.updateByPrimaryKeySelective(cmsArticleContent) == 1;
                if (!bRet) {
                    return false;
                }
            }

            return cmsArticleMapper.updateByPrimaryKey(cmsArticleNew) == 1;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            CmsArticle cmsArticle = cmsArticleMapper.selectByPrimaryKey(id);
            if (cmsArticle == null) {
                continue;
            }

            List<CmsArticleFile> cmsArticleFileList = cmsArticleFileMapper.findListByArtId(id);

            // 删除附件引用
            bret = cmsRfArticleFileMapper.deleteByArtId(id) >= 0;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }

            // 删除附件
            if (cmsArticleFileList != null) {
                for (CmsArticleFile cmsArticleFile : cmsArticleFileList) {
                    bret = this.deleteFile(cmsArticleFile);
                    if (!bret) {
                        throw new RuntimeException("delete faild");
                    }
                }
            }

            // 删除内容
            bret = cmsArticleContentMapper.deleteByArtId(id) >= 0;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }

            // 删除运行状态
            bret = cmsArticleRunBehaviorMapper.deleteByPrimaryKey(cmsArticle.getRuninfoId()) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }

            // 删除文章
            bret = cmsArticleMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }

            // 删除图片
            if (cmsArticle.getArtImgId() != null && cmsArticle.getArtImgId().length() > 0) {
                bret = this.deleteFile(cmsArticle.getArtImgId());
                if (!bret) {
                    throw new RuntimeException("delete faild");
                }
            }
        }

        return bret;
    }

    /**
     * 添加文档信息
     * @param cmsArticleEntity
     * @return
     */
    @Override
    @Transactional
        public boolean createArticle(CmsArticleEntity cmsArticleEntity) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            // 存储文档信息
            CmsArticle cmsArticle = cmsArticleEntity.getCmsArticle();
            if (cmsArticle == null) {
                return false;
            }else{
                cmsArticle.setAuthor(userInfo.getUsername());
            }

            // 存储文档内容
            CmsArticleContent cmsArticleContent = cmsArticleEntity.getCmsArticleContent();
            if (cmsArticle.getArtType().equals("1") && (cmsArticleContent.getContText() == null
                    || cmsArticleContent.getContText().length()==0)) {
                // 添加web文档时，内容不能为空
                return false;
            }

            List<CmsFileInfo> listFiles = cmsArticleEntity.getCmsArticleFileList();
            if (cmsArticle.getArtType().equals("2") && (listFiles == null || listFiles.size() == 0)) {
                // 添加file 文档时，文件不能为空
                return false;
            }

            // 存储图片信息
            CmsArticleFile cmsArticleImage = null;
            if (cmsArticleEntity.getCmsArticleImage() != null) {
                cmsArticleImage = this.insertFile(cmsArticleEntity.getCmsArticleImage());
                if (cmsArticleImage == null) {
                    return false;
                }

                cmsArticle.setArtImgId(cmsArticleImage.getId());
            }

            // 保存文档内容
            if (cmsArticleContent != null) {
                cmsArticleContent.setArtId(cmsArticle.getId());
                boolean bSuccessCont = cmsArticleContentMapper.insert(cmsArticleContent) == 1;
                if (!bSuccessCont) {
                    throw new RuntimeException("创建文档失败。");
                }
            }

            CmsArticleRunBehavior cmsArticleRunBehavior = EntryCreateFactory.createCmsArticleRunBehavior();

            boolean bSuccess = cmsArticleRunBehaviorMapper.insert(cmsArticleRunBehavior) == 1;
            if (!bSuccess) {
                throw new RuntimeException("创建文档失败");
            }

            cmsArticle.setRuninfoId(cmsArticleRunBehavior.getId());
            bSuccess = cmsArticleMapper.insert(cmsArticle) == 1;
            if (!bSuccess) {
                throw new RuntimeException("创建文档失败");
            }

            // 存储文件信息
            if (listFiles != null) {
                for (CmsFileInfo cmsFileInfo : listFiles) {
                    CmsArticleFile cmsFile = this.insertFile(cmsFileInfo);
                    if (cmsFile == null) {
                        throw new RuntimeException("创建文档失败");
                    }

                    // 保存文档和文件的关系
                    CmsRfArticleFile cmsRfArticleFile = new CmsRfArticleFile();
                    cmsRfArticleFile.setId(ToolUtil.getUUID());
                    cmsRfArticleFile.setArtId(cmsArticle.getId());
                    cmsRfArticleFile.setFileId(cmsFile.getId());

                    boolean bSuccessRf = cmsRfArticleFileMapper.insert(cmsRfArticleFile) == 1;
                    if (!bSuccessRf) {
                        throw new RuntimeException("创建文档失败");
                    }
                }
            }

            return bSuccess;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            throw new RuntimeException("创建文档失败");
        }
    }

    /**
     * 插入文件
     *
     * @param cmsFileInfo
     * @return
     */

    @Override
    public CmsArticleFile insertFile(CmsFileInfo cmsFileInfo) {
        if (cmsFileInfo.getInputStream() == null) {
            return null;
        }

        IFileService iFileService = (IFileService) SpringUtil.getBean(repositoryProperties.getEngine());
        try {
            if (cmsFileInfo.getFileUrlShort() == null) {
                cmsFileInfo.setFileUrlShort(ToolUtil.getUUID());
            }

            String path = iFileService.saveFile(cmsFileInfo);
            if (path == null) {
                return null;
            }


            // 存储基本信息
            CmsArticleFile cmsArticleFile = new CmsArticleFile();
            cmsArticleFile.setId(ToolUtil.getUUID());
            cmsArticleFile.setFileUrlShort(cmsFileInfo.getFileUrlShort());
            cmsArticleFile.setFileUrl(path);
            cmsArticleFile.setPageNum(cmsFileInfo.getPageNum());
            cmsArticleFile.setFileType(cmsFileInfo.getFileType());
            cmsArticleFile.setContVersion(1);
            cmsArticleFile.setCreateUser(cmsFileInfo.getCreateUser());
            cmsArticleFile.setCreateUsername(cmsFileInfo.getCreateUsername());
            cmsArticleFile.setFileExname(cmsFileInfo.getFileExname());
            cmsArticleFile.setFileLen(cmsFileInfo.getFileLen());
            cmsArticleFile.setFileName(cmsFileInfo.getFileName());
            cmsArticleFile.setModifyUser(cmsFileInfo.getCreateUser());
            cmsArticleFile.setModifyUsername(cmsFileInfo.getCreateUsername());
            cmsArticleFile.setCreateTime(Calendar.getInstance().getTime());
            cmsArticleFile.setModifyTime(Calendar.getInstance().getTime());
            cmsArticleFile.setContText("");
            cmsArticleFile.setContModifyReason("");

            return cmsArticleFileMapper.insert(cmsArticleFile) == 1 ? cmsArticleFile : null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据cmsArticleFile 生成cmsArticleFileHistory对象
     * @param cmsArticleFile
     * @return
     */
    private CmsArticleFileHistory convertArticalFile2ArticalFileHistory(CmsArticleFile cmsArticleFile) {
        CmsArticleFileHistory cmsArticleFileHistory = new CmsArticleFileHistory();
        cmsArticleFileHistory.setId(ToolUtil.getUUID());
        cmsArticleFileHistory.setContText(cmsArticleFile.getContText());
        cmsArticleFileHistory.setContModifyReason(cmsArticleFile.getContModifyReason());
        cmsArticleFileHistory.setFileType(cmsArticleFile.getFileType());
        cmsArticleFileHistory.setFileExname(cmsArticleFile.getFileExname());
        cmsArticleFileHistory.setModifyUser(cmsArticleFile.getModifyUser());
        cmsArticleFileHistory.setModifyUsername(cmsArticleFile.getModifyUsername());
        cmsArticleFileHistory.setModifyTime(cmsArticleFile.getModifyTime());
        cmsArticleFileHistory.setContVersion(cmsArticleFile.getContVersion());
        cmsArticleFileHistory.setCreateTime(cmsArticleFile.getCreateTime());
        cmsArticleFileHistory.setCreateUser(cmsArticleFile.getCreateUser());
        cmsArticleFileHistory.setCreateUsername(cmsArticleFile.getCreateUsername());
        cmsArticleFileHistory.setFileId(cmsArticleFile.getId());
        cmsArticleFileHistory.setFileLen(cmsArticleFile.getFileLen());
        cmsArticleFileHistory.setFileName(cmsArticleFile.getFileName());
        cmsArticleFileHistory.setFileUrl(cmsArticleFile.getFileUrl());
        cmsArticleFileHistory.setFileUrlShort(cmsArticleFile.getFileUrlShort());
        cmsArticleFileHistory.setPageNum(cmsArticleFile.getPageNum());

        return cmsArticleFileHistory;
    }

    /**
     * 更新文件
     *
     * @param newFile    新文件信息
     * @param inputStream 新文件
     * @return
     */
    @Override
    @Transactional
    public CmsArticleFile updateFile(CmsArticleFile newFile, InputStream inputStream) {

        // 查找旧的文档信息
        CmsArticleFile oldFile = cmsArticleFileMapper.selectByPrimaryKey(newFile.getId());
        if (oldFile == null) {
            return null;
        }

        if (inputStream == null) {
            // 保存历史
            try {
                CmsArticleFileHistory cmsArticleFileHistory = convertArticalFile2ArticalFileHistory(oldFile);
                boolean bHistoryInsertSuccess = cmsArticleFileHistoryMapper.insert(cmsArticleFileHistory) == 1;
                if (!bHistoryInsertSuccess) {
                    return null;
                }

                // 更新最新结果
                boolean bUpdateSuccess = cmsArticleFileMapper.updateByPrimaryKeySelective(newFile) == 1;
                if (!bUpdateSuccess) {
                    throw new RuntimeException("更新文件失败");
                }
            } catch (Exception ex) {
                throw new RuntimeException("更新文件失败");
            }

            return newFile;
        } else {
            // 插入文件
            try {
                // 存储新文档
                IFileService iFileService = (IFileService) SpringUtil.getBean(repositoryProperties.getEngine());

                CmsFileInfo cmsFileInfo = new CmsFileInfo();
                cmsFileInfo.setFileUrlShort(ToolUtil.getUUID());
                cmsFileInfo.setInputStream(inputStream);

                String path = iFileService.saveFile(cmsFileInfo);
                if (path == null) {
                    throw new RuntimeException("更新失败，请查看存储是否正常。");
                }

                // 保存历史
                CmsArticleFileHistory cmsArticleFileHistory = convertArticalFile2ArticalFileHistory(oldFile);
                boolean bHistoryInsertSuccess = cmsArticleFileHistoryMapper.insert(cmsArticleFileHistory) == 1;
                if (!bHistoryInsertSuccess) {
                    try {
                        iFileService.deleteFile(path);
                    } catch (Exception ex) {

                    }
                }

                // 更新最新结果
                newFile.setFileLen(cmsFileInfo.getFileLen());
                newFile.setFileUrl(path);
                boolean bUpdateSuccess = cmsArticleFileMapper.updateByPrimaryKeySelective(newFile) == 1;
                if (!bUpdateSuccess) {
                    try {
                        iFileService.deleteFile(path);
                    } catch (Exception ex) {

                    }
                    throw new RuntimeException("更新文件失败");
                }

            } catch (Exception ex) {
                throw new RuntimeException("更新文件失败");
            }
        }

        return null;
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    @Override
    @Transactional
    public boolean deleteFile(CmsArticleFile file) {

        boolean bDelete = cmsArticleFileMapper.deleteByPrimaryKey(file.getId()) == 1;
        if (bDelete) {
            // 删除存储文件
            IFileService iFileService = (IFileService) SpringUtil.getBean(repositoryProperties.getEngine());
            try {
                iFileService.deleteFile(file.getFileUrl());
            } catch (Exception ex) {

            }

            // 删除历史文件信息
            List<CmsArticleFileHistory> listHistorys = cmsArticleFileHistoryMapper.findListByFileId(file.getId());
            for (CmsArticleFileHistory cms : listHistorys) {
                cmsArticleFileHistoryMapper.deleteByPrimaryKey(cms.getId());
                try {
                    iFileService.deleteFile(cms.getFileUrl());
                } catch (Exception ex) {

                }
            }

        }

        return bDelete;
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @Override
    public boolean deleteFile(String fileId) {
       if (fileId == null || fileId.length() == 0) {
            return false;
        }

        CmsArticleFile cmsArticleFile = cmsArticleFileMapper.selectByPrimaryKey(fileId);
        if (cmsArticleFile == null) {
            return true;
        }

        return deleteFile(cmsArticleFile);
    }

    /**
     * 根据文件id号获取文件
     *
     * @param fileId
     * @return
     */
    @Override
    public CmsArticleFile getOneFileById(String fileId) {
        if (fileId == null || fileId.length() == 0) {
            return null;
        }

        return cmsArticleFileMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public CmsFileInfo fileDispose(MultipartFile file, HttpServletRequest request) throws IOException {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        CmsFileInfo cmsFileInfo = new CmsFileInfo();
        cmsFileInfo.setFileName(file.getOriginalFilename());
        cmsFileInfo.setFileExname(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        cmsFileInfo.setCreateUser(userInfo.getName());
        cmsFileInfo.setCreateUsername(userInfo.getUsername());
        cmsFileInfo.setFileType("2");
        cmsFileInfo.setFileUrlShort(request.getPathInfo());
        cmsFileInfo.setFileLen((float)file.getSize());
        cmsFileInfo.setInputStream(file.getInputStream());
        return cmsFileInfo;
    }

    @Override
    public String ops(String con) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("fileDoc", "");
        map.put("webDoc", "(*文章图片不能大于1M)");
        map.put("site", "");

        return map.get(con);
    }
}
