//package boardProject.board.auth.repository;
//
//import boardProject.board.auth.entity.Session;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Repository
//public interface SessionRepository extends JpaRepository<Session, Long> {
//    Optional<Session> findBySessionId(String sessionId);
//
//    void deleteBySessionId(String sessionId);
//    void deleteByExpiredDateTimeBefore(LocalDateTime now);
//
//    @Modifying
//    @Query("DELETE FROM Session s WHERE s.expiredDateTime < :now")
//    void deleteExpiredSessions(@Param("now") LocalDateTime now);
//}
