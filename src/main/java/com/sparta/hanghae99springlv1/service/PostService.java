package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.PostRequestDto;
import com.sparta.hanghae99springlv1.dto.PostResponseDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.*;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.repository.LikeRepository;
import com.sparta.hanghae99springlv1.repository.PostRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;

    // 전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> viewAllPost() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            postList.add(new PostResponseDto(post, getReplyList(post.getId())));
        }
        return postList;
    }

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        // 오청받은 DTO로 DB에 저장할 객체 만들기
        Post post = postRepository.saveAndFlush(new Post(requestDto, user));
        return new PostResponseDto(post, new ArrayList<>());
    }

    // 선택한 게시글 조회
    public PostResponseDto viewSelectPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
        return new PostResponseDto(post, getReplyList(postId));
    }

    // 선택한 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {

        // 게시글이 존재하는지 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 사용자 권한이 USER이고, 자기가 작성한 게시글이 아니라면 오류 발생
        if (user.getRole() == UserRoleEnum.USER && !post.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("현재 사용자가 작성한 게시글이 아닙니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post, getReplyList(postId));
    }

    // 게시글 삭제
    @Transactional
    public Message deletePost(Long postId, User user) {

        // 게시글이 존재하는지 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 사용자 권한이 USER이고, 자기가 작성한 게시글이 아니라면 오류 발생
        if (user.getRole() == UserRoleEnum.USER && !post.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("현재 사용자가 작성한 게시글이 아닙니다.");
        }

        // 게시글에 달린 댓글의 좋아요 삭제
        // 1. 게시글에 달린 댓글 Id(replyId) 조회
        List<Long> replyIdList = replyRepository.findAllByPost_IdOrderByCreateAtDesc(postId)
                .stream().map(Reply::getId).toList();
        // 2. 게시글에 달린 댓글의 like 삭제
        for (Long replyId : replyIdList) {
            likeRepository.deleteAllByReplyId(replyId);
        }

        // 게시글 좋아요 삭제
        likeRepository.deleteAllByPostId(postId);

        // 게시글에 달린 댓글 전체 삭제
        replyRepository.deleteAllByPost_Id(postId);

        // 게시글 삭제
        postRepository.deleteById(postId);
        return CustomStatus.DeletePostSuccess.toMessage();
    }

    private List<ReplyResponseDto> getReplyList(Long postId) {
        List<Reply> replyList = replyRepository.findAllByPost_IdOrderByCreateAtDesc(postId);
        List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
        for (Reply reply : replyList) {
            replyResponseDtoList.add(new ReplyResponseDto(reply));
        }
        return replyResponseDtoList;
    }
}
