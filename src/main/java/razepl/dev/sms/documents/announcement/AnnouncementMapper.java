package razepl.dev.sms.documents.announcement;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {
    AnnouncementDto toDto(Announcement announcement);
}
