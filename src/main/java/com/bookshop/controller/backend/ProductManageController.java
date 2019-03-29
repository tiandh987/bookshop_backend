package com.bookshop.controller.backend;

import com.bookshop.common.ServerResponse;
import com.bookshop.pojo.ProductWithBLOBs;
import com.bookshop.service.IFileService;
import com.bookshop.service.IProductService;
import com.bookshop.service.IUserService;
import com.bookshop.util.PropertiesUtil;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    //新增或更新产品
    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest httpServletRequest, ProductWithBLOBs product){
        //全部通过拦截器验证，已登录管理员账号
        return iProductService.saveOrUpdateProduct(product);
    }

    //更改产品状态
    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest httpServletRequest, Integer productId, Integer status){
        //全部通过拦截器验证，已登录管理员账号
        return iProductService.setSaleStatus(productId,status);
    }

    //获取商品详情
    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest httpServletRequest, Integer productId){
        //全部通过拦截器验证，已登录管理员账号
        return iProductService.manageProductDetail(productId);
    }

    //获取商品列表
    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest,
                                  @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        //全部通过拦截器验证，已登录管理员账号
        return iProductService.getProductList(pageNum,pageSize);
    }

    //商品搜索功能
    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest httpServletRequest,Integer productId,String productName,
                                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        //全部通过拦截器验证，已登录管理员账号
        return iProductService.productSearch(productId,productName,pageNum,pageSize);
    }

    //文件上传
    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }

    //富文本上传
    @RequestMapping(value = "richtext_img_upload.do",method = RequestMethod.POST)
    @ResponseBody
    public Map richtextImgUpload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response){

        Map resultMap = Maps.newHashMap();

        String path = request.getSession().getServletContext().getRealPath("upload");

        String targetFileName = iFileService.upload(file,path);
        if (StringUtils.isBlank(targetFileName)){
            resultMap.put("success",false);
            resultMap.put("msg","上传失败");
            return resultMap;
        }

        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);
        //*************修改response的header*************
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }
}
