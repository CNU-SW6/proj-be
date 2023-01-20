package cnu.swabe.v0.service;

import cnu.swabe.v0.domain.Like;
import cnu.swabe.v0.dto.LikeDTO;
import cnu.swabe.v0.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public Like addLike(LikeDTO likeDTO) {
        return likeRepository.save(likeDTO);
    }
}
