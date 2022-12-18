package com.tass.project_tasc.controllers.admins;

import com.tass.project_tasc.controllers.BaseController;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.request.BrandRequest;
import com.tass.project_tasc.model.request.CategoryRequest;
import com.tass.project_tasc.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(path = "/api/admins/brands")
public class BrandController extends BaseController {
    @Autowired
    BrandService brandService;
    @GetMapping("")
    public ResponseEntity<BaseResponse> finAllBrand() throws ApiException {
        return createdResponse(brandService.findAllBrand());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findBrandById(@PathVariable Long id)throws ApiException {
        return createdResponse(brandService.findBrandById(id));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> createBrand(@RequestBody BrandRequest brandRequest, Principal principal)throws ApiException {
        return createdResponse(brandService.createBrand(brandRequest, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateBrand(@RequestBody BrandRequest brandRequest, Principal principal, @PathVariable Long id)throws ApiException {
        return createdResponse(brandService.updateBrand(brandRequest, principal, id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteBrand(@PathVariable Long id)throws ApiException {
        return createdResponse(brandService.deleteBrand(id));
    }
    @PutMapping("/active/{id}")
    public ResponseEntity<BaseResponse> activeBrand(@PathVariable Long id)throws ApiException {
        return createdResponse(brandService.activeBrand(id));
    }
}
