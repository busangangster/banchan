package com.__105.Banchan.notice.service;

import com.__105.Banchan.notice.dto.*;
import com.__105.Banchan.notice.entitiy.Notice;
import com.__105.Banchan.notice.entitiy.NoticeComment;
import com.__105.Banchan.notice.entitiy.NoticeImage;
import com.__105.Banchan.notice.repository.NoticeCommentRepository;
import com.__105.Banchan.notice.repository.NoticeImageRepository;
import com.__105.Banchan.notice.repository.NoticeRepository;
import com.__105.Banchan.notice.repository.NoticeSpecification;
import com.__105.Banchan.user.entity.Apartment;
import com.__105.Banchan.user.entity.User;
import com.__105.Banchan.user.entity.UserApartment;
import com.__105.Banchan.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final NoticeCommentRepository noticeCommentRepository;
    private final NoticeImageRepository noticeImageRepository;

    @Override
    public void registerNotice(NoticePostRequest requestDto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Apartment apt = user.getUserApartments()
                .stream()
                .findFirst()
                .orElse(null)
                .getApartment();

        Notice notice = Notice.builder()
                .user(user)
                .apartment(apt)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        noticeRepository.save(notice);
    }

    @Override
    public NoticeDetailResponse detailNotice(Long noticeId, String username, boolean isAdmin, boolean isViewed) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("Not found Notice"));

        if (!isViewed) {
            notice.increaseViews();
        }

        NoticeDetailResponse responseDto = new NoticeDetailResponse();
        responseDto.setId(noticeId);
        responseDto.setUsername(notice.getUser().getUsername());
        responseDto.setApt(notice.getApartment());
        responseDto.setTitle(notice.getTitle());
        responseDto.setContent(notice.getContent());
        responseDto.setCreatedAt(notice.getCreatedAt());
        responseDto.setViews(notice.getViews());
        responseDto.setAdmin(isAdmin);
        responseDto.setWriter(notice.getUser().getUsername().equals(username));

        return responseDto;
    }

    @Override
    public void deleteNotice(Long noticeId, boolean isAdmin, String username) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("Not found Notice"));

        if (!notice.getUser().getUsername().equals(username) && !isAdmin) {
            throw new RuntimeException("Unable to delete because you are not the author or administrator");
        }

        List<NoticeComment> comments = noticeCommentRepository.findAllByNotice(notice);
        List<NoticeImage> images = noticeImageRepository.findAllByNotice(notice);

        if(!comments.isEmpty()) {
            noticeCommentRepository.deleteAll(comments);
        }

        if (!images.isEmpty()) {
            noticeImageRepository.deleteAll(images);
        }

        noticeRepository.delete(notice);
    }

    @Override
    public void updateNotice(Long noticeId, NoticePostRequest updateRequestDTO, boolean isAdmin, String username) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(()-> new RuntimeException("Not found Notice"));

        if (!notice.getUser().getUsername().equals(username) && !isAdmin) {
            throw new RuntimeException("Unable to update because you are not the author or administrator");
        }

        notice = notice.toBuilder()
                .title(updateRequestDTO.getTitle())
                .content(updateRequestDTO.getContent())
                .build();

        noticeRepository.save(notice);
    }

    @Override
    public Page<NoticeListResponse> getListNotice(SearchCondition requestDTO, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Set<UserApartment> ua = user.getUserApartments();

        if (ua.isEmpty()) {
            throw new RuntimeException("User has no Apartment");
        }

        String aptCode = ua.stream().findFirst().orElseThrow(() -> new RuntimeException("aptCode is invalid"))
                .getApartment().getCode();

        Specification<Notice> spec = Specification.where(null);
        spec = spec.and(NoticeSpecification.whereApt(aptCode));

        if (requestDTO.getKeyword() != null && !requestDTO.getKeyword().isEmpty()) {
            spec = spec.and(NoticeSpecification.containsKeyword(requestDTO.getKeyword()));
        }

        Sort sort = Sort.by(Sort.Direction.fromString(requestDTO.getSortDirection()), requestDTO.getSortBy());
        Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize(), sort);

        Page<Notice> noticePage = noticeRepository.findAll(spec, pageable);

        List<NoticeListResponse> noticeList = noticePage.getContent().stream().map(notice ->
                NoticeListResponse.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .username(notice.getUser().getUsername())
                        .createdAt(notice.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

        return new PageImpl<>(noticeList, pageable, noticePage.getTotalElements());
    }

    @Override
    public void registerNoticeComment(Long noticeId, NoticeCommentRequest requestDto, String username) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("Not found Notice"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        NoticeComment noticeComment = NoticeComment.builder()
                .notice(notice)
                .user(user)
                .content(requestDto.getContent())
                .build();

        noticeCommentRepository.save(noticeComment);
    }

    @Override
    public List<NoticeCommentResponse> getListNoticeComment(Long noticeId, String username, boolean isAdmin) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("Not found Notice"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        List<NoticeComment> comments = noticeCommentRepository.findAllByNotice(notice);

        return comments.stream().map(comment -> {
            NoticeCommentResponse commentResponse = new NoticeCommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setUsername(comment.getUser().getUsername());
            commentResponse.setContent(comment.getContent());
            commentResponse.setCreatedAt(comment.getCreatedAt());
            commentResponse.setAdmin(isAdmin);
            commentResponse.setWriter(comment.getUser().getUsername().equals(username));
            return commentResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteNoticeComment(Long commentId, boolean isAdmin, String username) {
        NoticeComment noticeComment = noticeCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Not found Notice"));

        if(!noticeComment.getUser().getUsername().equals(username) && !isAdmin) {
            throw new RuntimeException("Unable to delete because you are not the author or administrator");
        }

        noticeCommentRepository.delete(noticeComment);
    }

    @Override
    public void updateNoticeComment(Long commentId, NoticeCommentRequest requestDto, String username, boolean isAdmin) {
        NoticeComment noticeComment = noticeCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Not found Notice"));

        if(!noticeComment.getUser().getUsername().equals(username) && !isAdmin) {
            throw new RuntimeException("Unable to update because you are not the author or administrator");
        }

        noticeComment = noticeComment.toBuilder()
                .content(requestDto.getContent())
                .build();

        noticeCommentRepository.save(noticeComment);
    }

}
