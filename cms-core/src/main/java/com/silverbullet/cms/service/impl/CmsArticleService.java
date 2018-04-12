package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.config.RepositoryProperties;
import com.silverbullet.cms.dao.CmsArticleFileHistoryMapper;
import com.silverbullet.cms.dao.CmsArticleFileMapper;
import com.silverbullet.cms.dao.CmsArticleMapper;
import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.cms.domain.CmsArticleFileHistory;
import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.cms.service.ICmsArticleService;
import com.silverbullet.cms.service.IFileService;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

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
    private CmsArticleFileMapper cmsArticleFileMapper;
    @Autowired
    private CmsArticleFileHistoryMapper cmsArticleFileHistoryMapper;

    @Autowired
    private RepositoryProperties repositoryProperties;

    @Override
    public int countNum() {
        return cmsArticleMapper.countNum();
    }

    @Override
    public BaseDataResult<CmsArticle> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsArticle> listResults = new BaseDataResult<CmsArticle>();
        listResults.setResultList(cmsArticleMapper.findList());
        listResults.setTotalNum(cmsArticleMapper.countNum());

        return listResults;
    }

    @Override
    public CmsArticle getOneById(String id) {
        return cmsArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(CmsArticle cmsArticle) {
        try {
            CmsArticle cmsArticleNew = getOneById(cmsArticle.getId());

            if (cmsArticleNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return cmsArticleMapper.updateByPrimaryKey(cmsArticle) == 1 ? true : false;
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
            bret = cmsArticleMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(CmsArticle cmsArticle) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            cmsArticle.setId(ToolUtil.getUUID());

            return cmsArticleMapper.insert(cmsArticle) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
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
    @Transactional
    @Override
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
                boolean bHistoryInsertSuccess = cmsArticleFileHistoryMapper.insert(cmsArticleFileHistory) == 1 ? true : false;
                if (!bHistoryInsertSuccess) {
                    return null;
                }

                // 更新最新结果
                boolean bUpdateSuccess = cmsArticleFileMapper.updateByPrimaryKeySelective(newFile) == 1 ? true : false;
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
                boolean bHistoryInsertSuccess = cmsArticleFileHistoryMapper.insert(cmsArticleFileHistory) == 1?true:false;
                if (!bHistoryInsertSuccess) {
                    try {
                        iFileService.deleteFile(path);
                    } catch (Exception ex) {

                    }
                }

                // 更新最新结果
                newFile.setFileLen(cmsFileInfo.getFileLen());
                newFile.setFileUrl(path);
                boolean bUpdateSuccess = cmsArticleFileMapper.updateByPrimaryKeySelective(newFile) == 1 ? true : false;
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
    @Transactional
    @Override
    public boolean deleteFile(CmsArticleFile file) {

        boolean bDelete = cmsArticleFileMapper.deleteByPrimaryKey(file.getId()) == 1 ? true : false;
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
}
