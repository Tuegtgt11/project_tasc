package com.tass.project_tasc.controllers.admins;

import com.tass.project_tasc.controllers.BaseController;
import com.tass.project_tasc.database.entities.Brand;
import com.tass.project_tasc.database.entities.Category;
import com.tass.project_tasc.database.entities.Product;
import com.tass.project_tasc.database.entities.Size;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.request.ProductRequest;
import com.tass.project_tasc.services.ProductService;
import com.tass.project_tasc.spec.Specifications;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(path = "/api/admins/products")
public class ProductsController extends BaseController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<BaseResponse> findAllProduct(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "barcode", required = false) String barcode,
                                                       @RequestParam(value = "size", required = false) Size size,
                                                       @RequestParam(value = "price", required = false) BigDecimal price,
                                                       @RequestParam(value = "from", required = false) BigDecimal from,
                                                       @RequestParam(value = "to", required = false) BigDecimal to,
                                                       @RequestParam(value = "status", required = false) ProductStatus status,
                                                       @RequestParam(value = "brand", required = false) Brand brand,
                                                       @RequestParam(value = "category", required = false) Category category,
                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) throws ApiException {
        Specification<Product> specification = Specifications.productSpec(name, barcode, size, price,from, to, status, brand, category, page, pageSize);
        return createdResponse(productService.findAllProduct(specification, page, pageSize));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable Long id) throws ApiException {
        return createdResponse(productService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> createProduct(@RequestBody ProductRequest productRequest, Principal principal) throws ApiException{
        return createdResponse(productService.createProduct(productRequest, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody ProductRequest productRequest, Principal principal, @PathVariable Long id) throws ApiException{
        return createdResponse(productService.updateProduct(productRequest, principal, id));
    }
    @PutMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable Long id) throws ApiException{
        return createdResponse(productService.deleteProduct(id));
    }
    @PutMapping("/active/{id}")
    public ResponseEntity<BaseResponse> activeProduct(@PathVariable Long id) throws ApiException {
        return createdResponse(productService.activeProduct(id));
    }

}
