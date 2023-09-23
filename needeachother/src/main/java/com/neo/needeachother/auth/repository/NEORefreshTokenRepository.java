package com.neo.needeachother.auth.repository;

import com.neo.needeachother.auth.dto.NEORefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class NEORefreshTokenRepository {

    private final RedisTemplate redisTemplate;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    /**
     * {@code NEORefreshToken}을 레디스에 저장합니다. <br>
     * {@code UUID}로 생성된 랜덤한 값({@code refreshToken})이 키이며, 이메일 값이 밸류입니다. <br>
     * @param refreshToken 헤더에 포함될 UUID와 Email을 담은 객체
     */
    public void save(final NEORefreshToken refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getEmail());
        redisTemplate.expire(refreshToken.getEmail(), refreshTokenExpirationPeriod, TimeUnit.MILLISECONDS);
    }

    /**
     * 헤더에 담긴, UUID로 만들어진 RefreshToken을 기반으로 레디스에 존재하는지 찾아냅니다.<br>
     * 존재한다면, {@code NEORefreshToken}으로 생성해 반환합니다. <br>
     * @param refreshTokenUUID 헤더에 포함되는 UUID로 만들어진 RefreshToken
     * @return Optional<NEORefreshToken>
     */
    public Optional<NEORefreshToken> findByUUIDToken(final String refreshTokenUUID) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String email = valueOperations.get(refreshTokenUUID);

        if (Objects.isNull(email)) {
            return Optional.empty();
        }

        return Optional.of(new NEORefreshToken(refreshTokenUUID, email));
    }
}
