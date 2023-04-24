package com.sparta.hanghae99springlv1.service;

import com.sparta.hanghae99springlv1.dto.PostRequestDto;
import com.sparta.hanghae99springlv1.dto.PostResponseDto;
import com.sparta.hanghae99springlv1.dto.ReplyResponseDto;
import com.sparta.hanghae99springlv1.entity.Post;
import com.sparta.hanghae99springlv1.entity.Reply;
import com.sparta.hanghae99springlv1.entity.User;
import com.sparta.hanghae99springlv1.entity.UserRoleEnum;
import com.sparta.hanghae99springlv1.message.Message;
import com.sparta.hanghae99springlv1.repository.PostRepository;
import com.sparta.hanghae99springlv1.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

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
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        return new PostResponseDto(post, getReplyList(postId));
    }

    // 선택한 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {
        // 사용자 권한 가져와서
        UserRoleEnum userRoleEnum = user.getRole();

        // ADMIN 이면 모든 게시글 수정 가능
        Post post;
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            post = postRepository.findById(postId)
                    .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        } else {
            // USER 이면 자기 게시글 수정 가능, 현재 유저가 작성한 게시글이 맞는지 검증
            post = postRepository.findByIdAndUser_Id(postId, user.getId())
                    .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 게시글이 아닙니다."));
        }

        post.update(requestDto, user);

        return new PostResponseDto(post, getReplyList(postId));
    }

    // 게시글 삭제
    @Transactional
    public Message deletePost(Long postId, User user) {
        // 사용자 권한 가져와서
        UserRoleEnum userRoleEnum = user.getRole();

        // ADMIN 이면 모든 게시글 삭제 가능
        Post post;
        if (userRoleEnum == UserRoleEnum.ADMIN) {
            post = postRepository.findById(postId)
                    .orElseThrow(() -> new NullPointerException("게시글이 존재하지 않습니다."));
        } else {
            // USER 이면 자기 게시글 삭제 가능, 현재 유저가 작성한 게시글이 맞는지 검증
            post = postRepository.findByIdAndUser_Id(postId, user.getId())
                    .orElseThrow(() -> new NullPointerException("현재 사용자가 작성한 게시글이 아닙니다."));
        }

        replyRepository.deleteAllByPost_Id(postId);
        postRepository.deleteById(postId);
        return new Message("게시글 삭제 성공", 200);
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
