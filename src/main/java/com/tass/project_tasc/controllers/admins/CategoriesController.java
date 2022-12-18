package com.tass.project_tasc.controllers.admins;

import com.tass.project_tasc.controllers.BaseController;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.request.CategoryRequest;
import com.tass.project_tasc.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(path = "/api/admins/category")
public class CategoriesController extends BaseController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<BaseResponse> finAllCategory() throws ApiException{
        return createdResponse(categoryService.findAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findCategoryById(@PathVariable Long id)throws ApiException{
        return createdResponse(categoryService.findCategoryById(id));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> createCategory(@RequestBody CategoryRequest categoryRequest, Principal principal)throws ApiException{
        return createdResponse(categoryService.createCategory(categoryRequest, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateCategory(@RequestBody CategoryRequest categoryRequest, Principal principal, @PathVariable Long id)throws ApiException{
        return createdResponse(categoryService.updateCategory(categoryRequest, principal, id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable Long id)throws ApiException{
        return createdResponse(categoryService.deleteCategory(id));
    }
    @PutMapping("/active/{id}")
    public ResponseEntity<BaseResponse> activeCategory(@PathVariable Long id)throws ApiException{
        return createdResponse(categoryService.activeCategory(id));
    }

}
