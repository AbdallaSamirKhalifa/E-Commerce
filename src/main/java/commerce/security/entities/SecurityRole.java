package commerce.security.entities;

import commerce.entities.Role;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class SecurityRole implements GrantedAuthority {
    private final Role role;

    @Override
    public @Nullable String getAuthority() {
        return role.getName();
    }
}
