package razepl.dev.sms.documents.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import razepl.dev.sms.documents.user.interfaces.ServiceUser;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

import static razepl.dev.sms.documents.user.constants.UserValidation.DATE_PATTERN;
import static razepl.dev.sms.documents.user.constants.UserValidation.NAME_MAX_LENGTH;
import static razepl.dev.sms.documents.user.constants.UserValidation.NAME_MIN_LENGTH;
import static razepl.dev.sms.documents.user.constants.UserValidation.NAME_PATTERN;
import static razepl.dev.sms.documents.user.constants.UserValidation.USER_PACKAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.DATE_NULL_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.EMAIL_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.EMAIL_NULL_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.NAME_NULL_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.NAME_PATTERN_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.NAME_SIZE_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.PASSWORD_NULL_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.SURNAME_NULL_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.SURNAME_PATTERN_MESSAGE;
import static razepl.dev.sms.documents.user.constants.UserValidationMessages.SURNAME_SIZE_MESSAGE;

/**
 * This class represents a user in the system.
 * It implements the ServiceUser interface which specifies the behavior of a user in a service.
 * It also implements the UserDetails interface which provides core user information.
 * The class has several annotations that specify its behavior and constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User implements ServiceUser {
    @Serial
    private static final long serialVersionUID = 4637945829853929607L;

    @NotNull(message = DATE_NULL_MESSAGE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    @NotNull(message = NAME_NULL_MESSAGE)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
    private String name;

    @NotNull(message = SURNAME_NULL_MESSAGE)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = SURNAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = SURNAME_PATTERN_MESSAGE)
    private String surname;

    @NotNull(message = EMAIL_NULL_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    private String email;

    @NotNull(message = PASSWORD_NULL_MESSAGE)
    private String password;

    private String location;

    @Id
    private String userId;

    @Override
    public final int getAge() {
        return Period.between(LocalDate.now(), this.dateOfBirth).getYears();
    }

    @Override
    public final String getFullName() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public final String getUsername() {
        return email;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new NotSerializableException("razepl.dev.sms.documents.user.User");
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new NotSerializableException("razepl.dev.sms.documents.user.User");
    }
}
