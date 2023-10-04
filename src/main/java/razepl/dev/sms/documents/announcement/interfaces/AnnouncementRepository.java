package razepl.dev.sms.documents.announcement.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.sms.documents.announcement.Announcement;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String> {
    Page<Announcement> findAnnouncementsBy(Pageable pageable);
}
