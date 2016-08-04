package net.andwy.andwyadmin.web.client;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.andwy.andwyadmin.entity.client.Category;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.client.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "/client/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        boolean result = true;
        Long id = Long.valueOf(request.getParameter("id"));
        Category category = id == 0 ? new Category() : categoryService.get(id);
        category.setName(request.getParameter("name"));
        category.setIconUrl(request.getParameter("iconUrl"));
        Date date = new Date();
        category.setUpdateTime(date);
        if (category.getId() == null) {
            category.setUid(Util.generateUUID());
            category.setCreateTime(date);
        }
        category.setStatus("E".equals(request.getParameter("status")) ? "E" : "D");
        category = categoryService.save(category);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        File imageFolder = new File(categoryService.getIconPath(category));
        if (!imageFolder.exists()) imageFolder.mkdirs();
        for (Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            if (mf.getSize() > 0) {
                File uploadFile = new File(imageFolder.getAbsolutePath() + "/icon.png");
                try {
                    FileCopyUtils.copy(mf.getBytes(), uploadFile);
                } catch (IOException e) {
                    request.setAttribute("reason", Util.getStack(e));
                    result = false;
                    e.printStackTrace();
                }
                break;
            }
        }
        request.setAttribute("result", result);
        return "category/result";
    }
}
