package boardProject.board.auth.repository;

import boardProject.board.auth.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);
}
