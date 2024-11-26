package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.CommentDTO;
import com.example.e_voting_system.Model.DTO.CommentResponseDTO;
import com.example.e_voting_system.Model.Entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    // Map Comment to CommentResponseDTO
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "userProfilePicture", source = "user.profilePicture")
    CommentResponseDTO toCommentResponseDTO(Comment comment);

    // Map CommentDTO to Comment (for creation)
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "post", ignore = true) // Post is handled separately
    @Mapping(target = "user", ignore = true) // User is handled separately
    Comment toComment(CommentDTO commentDTO);


}

