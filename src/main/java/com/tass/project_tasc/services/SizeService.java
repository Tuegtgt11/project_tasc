package com.tass.project_tasc.services;

import com.tass.project_tasc.database.entities.Size;
import com.tass.project_tasc.database.entities.User;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import com.tass.project_tasc.database.repository.SizeRepository;
import com.tass.project_tasc.database.repository.UserRepository;
import com.tass.project_tasc.model.ApiException;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.ERROR;
import com.tass.project_tasc.model.request.SizeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SizeService {
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    UserRepository userRepository;
    public BaseResponse findAllSize() throws ApiException{
        return new BaseResponse(200, "Success!", sizeRepository.findAll());
    }
    public BaseResponse findSizeById(Long id) throws ApiException {
        return new BaseResponse(200, "Success!",sizeRepository.findById(id));
    }

    public BaseResponse createSize(SizeRequest sizeRequest, Principal principal) throws ApiException{
        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
        Size size = new Size();
        size.setName(sizeRequest.getName());
        size.setWeight(sizeRequest.getWeight());
        size.setCreatedAt(LocalDateTime.now());
        size.setUpdatedAt(LocalDateTime.now());
        size.setCreatedBy(user.get().getUsername());
        size.setCreatedBy(user.get().getUsername());
        sizeRepository.save(size);
        return new BaseResponse(200, "Cuccess!", size);
    }

//    public BaseResponse updateSize(SizeRequest sizeRequest, Principal principal, Long id) throws ApiException {
//        Optional<Size> optionalSize = sizeRepository.findById(id);
//        Optional<User> user = userRepository.findByUsernameAndStatus(principal.getName(), UserStatus.ACTIVE);
//        if (optionalSize.isEmpty()){
//            throw new ApiException(ERROR.SYSTEM_ERROR, "Size not found!");
//        }
//        Size existSize = optionalSize.get();
//        existSize.setName(sizeRequest.getName());
//        existSize.setWeight(sizeRequest.getWeight());
//        existSize.setCreatedAt(LocalDateTime.now());
//        existSize.setUpdatedAt(LocalDateTime.now());
//        existSize.setCreatedBy(user.get().getUsername());
//        existSize.setCreatedBy(user.get().getUsername());
//        sizeRepository.save(existSize);
//        return new BaseResponse(200 , "Cuccess!", existSize);
//    }
//    public BaseResponse deleteSize(Long id) throws ApiException {
//        Optional<Size> sizeOptional = sizeRepository.findById(id);
//        if (sizeOptional.isEmpty()){
//            throw new ApiException(ERROR.SYSTEM_ERROR);
//        }
//         sizeRepository.delete(sizeOptional.get());
//        return new BaseResponse(200, "Success");
//    }
}
