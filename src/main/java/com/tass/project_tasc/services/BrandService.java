package com.tass.project_tasc.services;

import com.tass.project_tasc.database.entities.Brand;
import com.tass.project_tasc.database.entities.User;
import com.tass.project_tasc.database.entities.myenums.BrandStatus;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import com.tass.project_tasc.database.repository.BrandRepository;
import com.tass.project_tasc.database.repository.UserRepository;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.ERROR;
import com.tass.project_tasc.model.request.BrandRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    UserRepository userRepository;

    public BaseResponse findAllBrand() throws ApiException {
        return new BaseResponse(200, "SUCCESS!", brandRepository.findAll());
    }

    public BaseResponse createBrand(BrandRequest brandRequest, Principal principal) throws ApiException {
        validateRequestCreateException(brandRequest);
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setImage(brandRequest.getImage());
        brand.setStatus(BrandStatus.ACTIVE);
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setCreatedBy(user.get().getUsername());
        brand.setUpdatedBy(user.get().getUsername());
        brandRepository.save(brand);
        return new BaseResponse(200, "Create New Brand Success!", brand);
    }

    public BaseResponse updateBrand(BrandRequest brandRequest, Principal principal, Long id) throws ApiException {
        validateRequestCreateException(brandRequest);
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        if (optionalBrand.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Brand not found!");
        }
        Brand existBrand = optionalBrand.get();
        existBrand.setName(brandRequest.getName());
        existBrand.setImage(brandRequest.getImage());
        existBrand.setStatus(brandRequest.getStatus());
        existBrand.setCreatedAt(LocalDateTime.now());
        existBrand.setUpdatedAt(LocalDateTime.now());
        existBrand.setCreatedBy(user.get().getUsername());
        existBrand.setUpdatedBy(user.get().getUsername());
        brandRepository.save(existBrand);
        return new BaseResponse(200, "Update brand success!", existBrand);
    }

    public BaseResponse findBrandById(Long id) throws ApiException {
        return new BaseResponse(200, "SUCCESS!", brandRepository.findById(id));
    }
    public BaseResponse deleteBrand(Long id) throws ApiException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Brand not found!");
        }
        Brand existBrand = optionalBrand.get();
        existBrand.setStatus(BrandStatus.DELETED);
        brandRepository.save(existBrand);
        return  new BaseResponse(200 ,"DELETED!");
    }
    public BaseResponse activeBrand(Long id) throws ApiException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isEmpty()) {
            throw new ApiException(ERROR.SYSTEM_ERROR, "Brand not found!");
        }
        Brand existBrand = optionalBrand.get();
        existBrand.setStatus(BrandStatus.ACTIVE);
        brandRepository.save(existBrand);
        return  new BaseResponse(200 ,"DELETED!");
    }
    public void validateRequestCreateException(BrandRequest brandRequest) throws ApiException {
        if (StringUtils.isBlank(brandRequest.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "name is blank");
        }
        if (StringUtils.isBlank(brandRequest.getImage())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Image is blank");
        }
    }
}
