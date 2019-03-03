package com.silverbullet.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.silverbullet.activiti.service.ActModelService;
import com.silverbullet.utils.BaseDataResult;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.Model;
import org.activiti.rest.editor.model.ModelSaveRestResource;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by jeffyuan on 2018/5/2.
 */
@Controller
@RequestMapping(value = "/act/model")
public class ActModuleController implements ModelDataJsonConstants {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ActModuleController.class);

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ActModelService actModelService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * ����ģ��
     */
    @RequestMapping(value = "/create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            RepositoryService repositoryService = processEngine.getRepositoryService();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, "hello1111");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = "hello1111";
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName("hello1111");
            modelData.setKey("12313123");

            //����ģ��
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/static/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            System.out.println("����ģ��ʧ�ܣ�");
        }
    }

    @RequestMapping(value = "/modeler")
    public String createModel(String modelId,org.springframework.ui.Model model) {

        model.addAttribute("modelId", modelId);

        return "/modeler";
    }

    @RequestMapping(value = "test")
    @ResponseBody
    public List<Group> test(String type) {

        identityService.checkPassword("11","11");

        return identityService.createGroupQuery().list();
    }

    ///**
    // * ����ģ���б�
    // */
    //@ResponseBody
    //@RequiresPermissions("act:model:list")
    //@RequestMapping(value = "data")
    //public Map<String, Object> data(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
    //	Page<org.activiti.engine.repository.Model> page = actModelService.modelList(
    //			new Page<org.activiti.engine.repository.Model>(request, response), category);
    //	return getBootstrapData(page);
    //}


    /**
     * ����ģ���б�
     */
    //@RequiresPermissions("act:model:list")
    @RequestMapping(value = { "list/pub.html", "" })
    public String modelList(String category, HttpServletRequest request,
                            HttpServletResponse response, org.springframework.ui.Model model) {

        int nCurPage = 1;

        BaseDataResult<Model> models = actModelService.modelList(nCurPage, 20, category);
        model.addAttribute("results", models);
        model.addAttribute("curPage", nCurPage);
        model.addAttribute("category", category);
        model.addAttribute("pageSize", "20");

        return "/modules/activiti/list";
    }

    /**
     * ��ҳ��̬��ȡģ������
     * @param curpage  ��ǰҳ��
     * @param category ģ�ͷ���
     * @return org.springframework.web.servlet.ModelAndView
     * @author jeffyuan
     * @createDate 2019/2/22 14:02
     * @updateUser jeffyuan
     * @updateDate 2019/2/22 14:02
     * @updateRemark
     */
    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage, String category) {
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/modules/activiti/list");

        BaseDataResult<Model> models = actModelService.modelList(nCurPage, 20, category);
        modelAndView.addObject("results", models);
        modelAndView.addObject("curPage", nCurPage);
        modelAndView.addObject("category", category);
        modelAndView.addObject("pageSize", "20");

        return modelAndView;
    }

    /**
     * ����ģ��
     */
    //@RequiresPermissions("act:model:create")
//	@RequestMapping(value = "create", method = RequestMethod.GET)
//	public String create(Model model) {
//		return "modules/act/actModelCreate";
//	}

    /**
     * ����ģ��
     */
    @ResponseBody
    //@RequiresPermissions("act:model:create")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(String name, String key, String description, String category) {
        try {
            actModelService.create(name, key, description, category);
            return "���ģ�ͳɹ�";

        } catch (Exception e) {
            return "���ģ��ʧ��";
            //logger.error("����ģ��ʧ�ܣ�", e);
        }
    }

    /**
     * ����Model��������
     */
    //@ResponseBody
    //@RequiresPermissions("act:model:deploy")
    //@RequestMapping(value = "deploy")
    //public AjaxJson deploy(String id, RedirectAttributes redirectAttributes) {
    //	AjaxJson j = new AjaxJson();
    //	String message = actModelService.deploy(id);
    //	j.setMsg(message);
    //	return j;
    //}

    /**
     * ����model��xml�ļ�
     */
    //@RequiresPermissions("act:model:export")
    @RequestMapping(value = "export")
    public void export(String id, HttpServletResponse response) {
        actModelService.export(id, response);
    }

    /**
     * ����Model����
     */
    //@ResponseBody
    //@RequiresPermissions("act:model:edit")
    //@RequestMapping(value = "updateCategory")
    //public AjaxJson updateCategory(String id, String category, RedirectAttributes redirectAttributes) {
    //	AjaxJson j = new AjaxJson();
    //	actModelService.updateCategory(id, category);
    //	j.setMsg("���óɹ���ģ��ID=" + id);
    //	return j;
    //}

    /**
     * ɾ��Model
     * @param id
     * @param redirectAttributes
     * @return
     */
    //@ResponseBody
    //@RequiresPermissions("act:model:del")
    //@RequestMapping(value = "delete")
    //public AjaxJson delete(String id, RedirectAttributes redirectAttributes) {
    //	AjaxJson j = new AjaxJson();
    //	actModelService.delete(id);
    //	j.setMsg("ɾ���ɹ���ģ��ID=" + id);
    //	return j;
    //}
    //
    /**
     * ɾ��Model
     * @param ids
     * @param redirectAttributes
     * @return
     */
    //@ResponseBody
    //@RequiresPermissions("act:model:del")
    //@RequestMapping(value = "deleteAll")
    //public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
    //	String idArray[] =ids.split(",");
    //	for(String id : idArray){
    //		actModelService.delete(id);
    //	}
    //	AjaxJson j = new AjaxJson();
    //	j.setMsg("ɾ���ɹ�" );
    //	return j;
    //}

    /**
     *  �˷������ڸ���Activiti model ����ķ�����
     *  ��Ϊ�Ǹ�������ȡǰ̨���͹����Ĳ���ʱ�ᱨ��
     *  ���ݸ��ٽ����Ϊ��Ϣ�������޷���������������֪����ô�޸ģ�
     * @see ModelSaveRestResource#saveModel(java.lang.String, org.springframework.util.MultiValueMap)
     **/
    @RequestMapping(value="/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestParam("name") String name,
                          @RequestParam("json_xml") String json_xml, @RequestParam("svg_xml") String svg_xml,
                          @RequestParam("description") String description) {
        try {

            org.activiti.engine.repository.Model model = repositoryService.getModel(modelId);

            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            modelJson.put(MODEL_NAME, name);
            modelJson.put(MODEL_DESCRIPTION, description);
            model.setMetaInfo(modelJson.toString());
            model.setName(name);

            repositoryService.saveModel(model);

            repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();

        } catch (Exception e) {
            LOGGER.error("Error saving model", e);
            throw new ActivitiException("Error saving model", e);
        }
    }
}
