package razepl.dev.sms.documents.user.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.sms.api.auth.data.RegisterRequest;
import razepl.dev.sms.documents.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest registerRequest, String password);
}
