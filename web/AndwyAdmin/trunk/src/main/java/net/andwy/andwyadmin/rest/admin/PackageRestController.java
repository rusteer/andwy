package net.andwy.andwyadmin.rest.admin;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Validator;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.rest.AbstractController;
import net.andwy.andwyadmin.service.admin.BuildService;
import net.andwy.andwyadmin.service.admin.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = "/api/v1/package")
public class PackageRestController extends AbstractController {
    @Autowired
    private PackageService packageService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private Validator validator;
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Package entity, UriComponentsBuilder uriBuilder) {
        handleCreate(entity);
        save(entity);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        packageService.delete(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Package task = packageService.get(id);
        if (task == null) { return new ResponseEntity<Package>(HttpStatus.NOT_FOUND); }
        return new ResponseEntity<Package>(task, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Package> list(@RequestParam("batchId") Long batchId) {
        if (batchId == null || batchId <= 0) return new ArrayList<Package>();
        return packageService.getAllByBatchId(batchId);
    }
    /**
     * Update entity
     * @param entity
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody Package entity) {
        handleUpdate(entity);
        save(entity);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    private void save(Package entity) {
        Package result = packageService.save(entity);
    }
}