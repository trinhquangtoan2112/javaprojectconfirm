package vn.toan.testfullstep.model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("RedisToken")
public class RedisToken implements Serializable {

    private String id;
    private String accessToken;
    private String refreshToken;

}
