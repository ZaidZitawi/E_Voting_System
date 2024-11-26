package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.LikeDTO;
import com.example.e_voting_system.Model.DTO.LikeResponseDTO;
import com.example.e_voting_system.Model.Entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    // Mapping Like to LikeResponseDTO
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.profilePicture", target = "userProfilePicture")
    LikeResponseDTO toLikeResponseDTO(Like like);

    // Mapping Like to LikeDTO
    @Mapping(source = "post.postId", target = "postId")
    @Mapping(source = "user.userId", target = "userId")
    LikeDTO toLikeDTO(Like like);

    // Mapping LikeDTO back to Like entity
    @Mapping(source = "postId", target = "post.postId")
    @Mapping(source = "userId", target = "user.userId")
    Like toLike(LikeDTO likeDTO);
}
