
package com.collabnex.domain.message;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class ThreadParticipantId implements Serializable {
    private Long threadId;
    private Long userId;
}
