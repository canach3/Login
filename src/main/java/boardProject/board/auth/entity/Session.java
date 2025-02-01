package boardProject.board.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "session")
@Getter
@Setter
public class Session {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_info_id")
    private long id;

    @Column(unique = true)
    private String sessionId;

    @Column
    private long memberId;

    @Column
    private LocalDateTime expiredDateTime;


}
