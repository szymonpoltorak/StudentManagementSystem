package razepl.dev.sms.documents.announcement;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "announcements")
public class Announcement implements Comparable<Announcement> {
    @Id
    private String id;

    private String title;

    private String content;

    private LocalDate date;

    private String time;

    private String authorName;

    @Override
    public final int compareTo(Announcement announcement) {
        if (!date.equals(announcement.date)) {
            return date.compareTo(announcement.date);
        }
        return time.compareTo(announcement.time);
    }
}
