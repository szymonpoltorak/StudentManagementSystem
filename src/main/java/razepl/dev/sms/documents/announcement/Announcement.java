package razepl.dev.sms.documents.announcement;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import razepl.dev.sms.api.annoucement.data.UpdateRequest;
import razepl.dev.sms.api.annoucement.interfaces.Updateable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ToString
@EqualsAndHashCode
@Getter
@Builder
@Document(collection = "announcements")
public class Announcement implements Comparable<Announcement>, Updateable<UpdateRequest> {
    @Id
    private String id;

    private String title;

    private String content;

    private LocalDate date;

    private String time;

    private String authorName;

    @Override
    public final void update(UpdateRequest updateRequest) {
        this.content = updateRequest.content();
        this.time = updateRequest.title();
        this.date = LocalDate.now();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm", Locale.UK));
    }

    @Override
    public final int compareTo(Announcement announcement) {
        if (!date.equals(announcement.date)) {
            return date.compareTo(announcement.date);
        }
        return time.compareTo(announcement.time);
    }
}
