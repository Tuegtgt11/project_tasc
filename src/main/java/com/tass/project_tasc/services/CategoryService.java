package com.tass.project_tasc.services;

import com.tass.project_tasc.database.entities.Category;
import com.tass.project_tasc.database.entities.Product;
import com.tass.project_tasc.database.entities.User;
import com.tass.project_tasc.database.entities.myenums.CategoryStatus;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import com.tass.project_tasc.database.repository.CategoryRepository;
import com.tass.project_tasc.database.repository.UserRepository;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.ERROR;
import com.tass.project_tasc.model.request.CategoryRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public BaseResponse findAllCategory() throws ApiException {
        return new BaseResponse(200, "SUCCESS!", categoryRepository.findAll());
    }

    public BaseResponse findCategoryById(Long id) throws ApiException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Category not found!");
        }
        return new BaseResponse(200, "SUCCESS", optionalCategory.get());
    }

    public BaseResponse createCategory(CategoryRequest categoryRequest, Principal principal) throws ApiException {
        validateRequestCreateException(categoryRequest);
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        Category category = new Category();
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryRequest.getName());
        if (optionalCategory.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM, "Category Name already exist!");
        }
        category.setName(categoryRequest.getName());
        category.setIcon(categoryRequest.getIcon());
        category.setStatus(categoryRequest.getStatus());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setCreatedBy(user.get().getUsername());
        category.setUpdatedBy(user.get().getUsername());
        categoryRepository.save(category);
        return new BaseResponse(200, "Create new Category Success!", category);
    }

    public BaseResponse updateCategory(CategoryRequest categoryRequest, Principal principal, Long id) throws ApiException {
        validateRequestCreateException(categoryRequest);
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Category not found!");
        }

        Category existCategory = optionalCategory.get();
        existCategory.setName(categoryRequest.getName());
        existCategory.setIcon(categoryRequest.getIcon());
        existCategory.setStatus(categoryRequest.getStatus());
        existCategory.setCreatedAt(LocalDateTime.now());
        existCategory.setUpdatedAt(LocalDateTime.now());
        existCategory.setCreatedBy(user.get().getUsername());
        existCategory.setUpdatedBy(user.get().getUsername());
        categoryRepository.save(existCategory);
        return new BaseResponse(200, "Update Category Success!", existCategory);
    }

    public BaseResponse deleteCategory(Long id) throws ApiException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Category not found!");
        }
        Category existCategory = optionalCategory.get();
        existCategory.setStatus(CategoryStatus.DELETED);
        categoryRepository.save(existCategory);
        return new BaseResponse(200, "Deleted!");

    }

    public BaseResponse activeCategory(Long id) throws ApiException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Category not found!");
        }
        Category existCategory = optionalCategory.get();
        existCategory.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(existCategory);
        return new BaseResponse(200, "Active!");

    }

    private void validateRequestCreateException(CategoryRequest categoryRequest) throws ApiException {

        if (StringUtils.isBlank(categoryRequest.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Name is blank!");
        }

        if (StringUtils.isBlank(categoryRequest.getIcon())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Description is blank!");
        }

        if (StringUtils.isBlank(categoryRequest.getStatus().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Status is Blank!");
        }

    }

}
