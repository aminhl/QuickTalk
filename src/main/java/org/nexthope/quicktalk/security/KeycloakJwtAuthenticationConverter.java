package org.nexthope.quicktalk.security;

import lombok.NonNull;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @NonNull
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        return new JwtAuthenticationToken(
                jwt,
                Stream.concat(new JwtGrantedAuthoritiesConverter().convert(jwt).stream(), extractResourceRole(jwt).stream())
                        .collect(Collectors.toSet())
        );
    }

    private Collection<? extends GrantedAuthority> extractResourceRole(Jwt jwt) {
        Map<String, Object> resourceAccess = MapUtils.emptyIfNull(jwt.getClaim("resource_access"));
        Map<String, List<String>> eternal = (Map<String, List<String>>) resourceAccess.get("account");
        List<String> roles = eternal.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                .collect(Collectors.toSet());
    }
}
