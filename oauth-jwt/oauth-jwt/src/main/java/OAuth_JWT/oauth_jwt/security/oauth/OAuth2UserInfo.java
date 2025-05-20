package OAuth_JWT.oauth_jwt.security.oauth;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes(){
        return this.attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getProvider();

    public abstract String getEmail();
}
