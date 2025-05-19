package com.Globetrek.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {

    private Integer userId;
    private Integer logId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeId)) return false;
        LikeId that = (LikeId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(logId, that.logId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, logId);
    }
}
