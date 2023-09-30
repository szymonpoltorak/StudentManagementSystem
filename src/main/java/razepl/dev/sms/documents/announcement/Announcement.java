package razepl.dev.sms.documents.announcement;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "announcements")
public class Announcement {
    @Id
    private String id;

    private String title;

    private String content;

    private LocalDateTime dateTime;

    private String authorName;
}
