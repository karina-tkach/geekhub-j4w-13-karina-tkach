package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordToken {
    private int id;
    private String token;
    private User user;
    private OffsetDateTime expireTime;
    private boolean isUsed;
}
