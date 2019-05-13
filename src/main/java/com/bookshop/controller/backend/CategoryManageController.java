package com.bookshop.controller.backend;

import com.bookshop.common.ServerResponse;
import com.bookshop.service.ICategoryService;
import com.bookshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    //添加品类
    @RequestMapping(value = "add_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId",defaultValue = "0")Integer parentId){

        //全部通过拦截器验证，已登录管理员账号
        return iCategoryService.addCategroy(categoryName,parentId);
    }

    //更新品类名
    @RequestMapping(value = "set_category_name.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest,Integer categoryId,String categoryName){

        //全部通过拦截器验证，已登录管理员账号
        return iCategoryService.updateCategoryName(categoryId,categoryName);
}

    //查询子节点的平级节点
    @RequestMapping(value = "get_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){

        //全部通过拦截器验证，已登录管理员账号
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    //递归查询本节点id及孩子节点的id
    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){

        //全部通过拦截器验证，已登录管理员账号
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}
