package com.tass.project_tasc.services;

import com.tass.project_tasc.database.entities.*;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import com.tass.project_tasc.database.repository.*;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.ERROR;
import com.tass.project_tasc.model.dto.ProductInfo;
import com.tass.project_tasc.model.request.ProductRequest;
import com.tass.project_tasc.model.response.SearchProductResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BrandRepository brandRepository;

    public BaseResponse findAllProduct(Specification<Product> specification, Integer page, Integer pageSize) throws ApiException {
        return new BaseResponse(200, "SUCCESS", productRepository.findAll(specification, PageRequest.of(page, pageSize, Sort.by("updatedAt").descending())));
    }


    public BaseResponse createProduct(ProductRequest productRequest, Principal principal) throws ApiException {
        validateRequestCreateException(productRequest);
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        Product product = new Product();
        product.setName(productRequest.getName());

        Optional<Category> optionalCategory = categoryRepository.findById(productRequest.getCategory().getId());
        if (optionalCategory.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Category not found!");
        }
        product.setCategory(optionalCategory.get());
        Optional<Brand> optionalBrand = brandRepository.findById(productRequest.getBrand().getId());
        if (optionalBrand.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Brand not found!");
        }
        product.setBrand(optionalBrand.get());
        Optional<Size> optionalSize = sizeRepository.findById(productRequest.getSize().getId());
        if (optionalSize.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Size not found!");
        }
        product.setSize(optionalSize.get());
        product.setDescription(productRequest.getDescription());
        product.setDetail(productRequest.getDetail());
        product.setImages(productRequest.getImages());
        Optional<Product> optionalProductBarcode = productRepository.findByBarcode(productRequest.getBarcode());
        if (optionalProductBarcode.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Barcode already exist!");
        }
        product.setBarcode(productRequest.getBarcode());
        product.setPrice(productRequest.getPrice());
        product.setSold(0);
        product.setQuantity(productRequest.getQuantity());
        product.setStatus(productRequest.getStatus());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreatedBy(user.get().getUsername());
        product.setUpdatedBy(user.get().getUsername());
        productRepository.save(product);
        return new BaseResponse(200, "Create New Success!", product);
    }

    public BaseResponse updateProduct(ProductRequest productRequest, Principal principal, Long id) throws ApiException {
        validateRequestCreateException(productRequest);
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        if (productOptional.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productRequest.getCategory().getId());
        if (optionalCategory.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Category not found!");
        }

        Optional<Brand> optionalBrand = brandRepository.findById(productRequest.getBrand().getId());
        if (optionalBrand.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Brand not found!");
        }

        Optional<Size> optionalSize = sizeRepository.findById(productRequest.getSize().getId());
        if (optionalSize.isEmpty()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Size not found!");
        }

        Product existProduct = productOptional.get();
        existProduct.setName(productRequest.getName());
        existProduct.setCategory(optionalCategory.get());
        existProduct.setBrand(optionalBrand.get());
        existProduct.setSize(optionalSize.get());
        existProduct.setDescription(productRequest.getDescription());
        Optional<Product> optionalProductBarcode = productRepository.findByBarcode(productRequest.getBarcode());
        if (optionalProductBarcode.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Barcode already exist!");
        }
        existProduct.setBarcode(productRequest.getBarcode());
        existProduct.setDetail(productRequest.getDetail());
        existProduct.setImages(productRequest.getImages());
        existProduct.setPrice(productRequest.getPrice());
        existProduct.setSold(productRequest.getSold());
        existProduct.setQuantity(productRequest.getQuantity());
        existProduct.setStatus(productRequest.getStatus());
        existProduct.setCreatedAt(LocalDateTime.now());
        existProduct.setUpdatedAt(LocalDateTime.now());
        existProduct.setCreatedBy(user.get().getUsername());
        existProduct.setUpdatedBy(user.get().getUsername());
        productRepository.save(existProduct);
        return new BaseResponse(200, "Update Product Success!", existProduct);
    }

    public BaseResponse findById(Long id) throws ApiException {
        SearchProductResponse response = new SearchProductResponse();
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            response.setCode(ERROR.SYSTEM_ERROR.code);
        }
        Product product = productOptional.get();
        ProductInfo productInfo = new ProductInfo(product);
        response.setData(productInfo);
        return response;
    }

    public BaseResponse deleteProduct(Long id) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Product not found!");
        }
        Product existProduct = optionalProduct.get();
        existProduct.setStatus(ProductStatus.DELETED);
        productRepository.save(existProduct);
        return new BaseResponse(200, "Deleted!");

    }

    public BaseResponse activeProduct(Long id) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Product not found!");
        }
        Product existProduct = optionalProduct.get();
        existProduct.setStatus(ProductStatus.ACTIVE);
        productRepository.save(existProduct);
        return new BaseResponse(200, "Active!");

    }

    private void validateRequestCreateException(ProductRequest productRequest) throws ApiException {

        if (StringUtils.isBlank(productRequest.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "name is blank");
        }

        if (StringUtils.isBlank(productRequest.getDescription())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Description is blank");
        }

        if (StringUtils.isBlank(productRequest.getImages())) {
            throw new ApiException(ERROR.INVALID_PARAM, "image is Blank");
        }
        if (StringUtils.isBlank(productRequest.getDetail())) {
            throw new ApiException(ERROR.INVALID_PARAM, "detail is Blank");
        }
        if (StringUtils.isBlank(productRequest.getDescription())) {
            throw new ApiException(ERROR.INVALID_PARAM, "description is Blank");
        }
        if (StringUtils.isBlank(productRequest.getCategory().getId().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "category is Blank");
        }
        if (StringUtils.isBlank(productRequest.getBrand().getId().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "brand is Blank");
        }
        if (StringUtils.isBlank(productRequest.getSize().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Size is Blank");
        }
        if (StringUtils.isBlank(productRequest.getPrice().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Price is Blank");
        }

    }


}
