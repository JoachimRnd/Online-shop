package be.vinci.pae.api.filters;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Singleton
@Provider
@AuthorizeAdmin
public class AuthorizationAdminRequestFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final JWTVerifier jwtVerifier =
      JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();

  @Inject
  private DAOUser dataService;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String token = requestContext.getHeaderString("Authorization");
    if (token == null) {
      requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
          .entity("A token is needed to access this resource").build());
    } else {
      DecodedJWT decodedToken = null;
      try {
        decodedToken = this.jwtVerifier.verify(token);
      } catch (Exception e) {
        throw new WebApplicationException("Malformed token", e, Status.UNAUTHORIZED);
      }
      UserDTO user = this.dataService.getUserById(decodedToken.getClaim("user").asInt());
      if (user == null) {
        throw new WebApplicationException("Unknow user", Status.UNAUTHORIZED);
      }
      if (user.getUserType() != 0) {
        throw new WebApplicationException("Not admin", Status.UNAUTHORIZED);
      }
      requestContext.setProperty("user", user);
    }
  }

}
