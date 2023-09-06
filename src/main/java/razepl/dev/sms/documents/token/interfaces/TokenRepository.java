package razepl.dev.sms.documents.token.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import razepl.dev.sms.documents.token.JwtToken;

import java.util.List;
import java.util.Optional;

/**
 * A repository for JwtTokens.
 * It extends {@link MongoRepository}.
 */
@Repository
public interface TokenRepository extends MongoRepository<JwtToken, String> {
    /**
     * Method to find token object inside database by the given token string
     *
     * @param jwtToken token string
     * @return {@link Optional} of {@link JwtToken} instance
     */
    Optional<JwtToken> findByToken(String jwtToken);

    /**
     * Method used to return the list of all tokens that user have based on his id.
     *
     * @param id the id of the user
     * @return List of {@link JwtToken} of the user
     */
    @Query("""
            {
                'user.userId': ?0,
                $or: [
                    {
                        'isExpired': false
                    },
                    {
                        'isRevoked': false
                    }
                ]
            }
            """)
    List<JwtToken> findAllValidTokensByUserId(String id);
}
