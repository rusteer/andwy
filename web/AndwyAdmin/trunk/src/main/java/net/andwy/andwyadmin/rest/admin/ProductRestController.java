package net.andwy.andwyadmin.rest.admin;
import java.util.List;
import javax.validation.Validator;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.rest.AbstractController;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.admin.BuildService;
import net.andwy.andwyadmin.service.admin.PackageService;
import net.andwy.andwyadmin.service.admin.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = "/api/v1/product")
public class ProductRestController extends AbstractController {
    @Autowired
    private ProductService service;
    @Autowired
    private PackageService pkgService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private Validator validator;
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Product entity, UriComponentsBuilder uriBuilder) {
        handleCreate(entity);
        service.save(entity);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Product task = service.get(id);
        if (task == null) { return new ResponseEntity<Product>(HttpStatus.NOT_FOUND); }
        return new ResponseEntity<Product>(task, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Product> list() {
        return service.getAll();
    }
    /**
     * Update entity
     * @param entity
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody Product entity) {
        handleUpdate(entity);
        Product oldData = service.get(entity.getId());
        String versionDate = oldData.getVersionDate();
        long versionCount = oldData.getVersionCount();
        service.save(entity);
        if (!versionDate.equals(entity.getVersionDate()) || versionCount != entity.getVersionCount()) {
            List<Package> list = pkgService.getListByProductId(entity.getId());
            for (Package pkg : list) {
                if (pkg.getUid() == null || pkg.getUid().length() == 0) {
                    pkg.setUid(Util.generateUUID());
                }
                pkg.setBuildStatus("等待打包");
                pkg.setNeedBuild("Y");
                pkgService.save(pkg);
            }
        }
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
