package razepl.dev.sms.documents.announcement.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.documents.announcement.Announcement;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {
    @Mapping(source = "id", target = "announcementId")
    AnnouncementDto toDto(Announcement announcement);
}
