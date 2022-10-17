package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.config.seas.SsoSeasPropertiesConfig;
import com.pe.pcm.resource.token.CommunityManagerSeasTokenResource;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static org.springframework.util.StringUtils.hasLength;

/**
 * @author Kiran Reddy.
 */
@Controller
@RequestMapping(path = "pcm")
@Profile(SSO_SSP_SEAS)
@Api(tags = {"Login Redirect (SSP)"}, hidden = true)
public class SspLoginContextResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerSeasTokenResource.class);

    private final SsoSeasPropertiesConfig ssoSeasPropertiesConfig;

    @Autowired
    public SspLoginContextResource(SsoSeasPropertiesConfig ssoSeasPropertiesConfig) {
        this.ssoSeasPropertiesConfig = ssoSeasPropertiesConfig;
    }

    @GetMapping(path = "login")
    public String sspLoginRedirect(HttpServletRequest request) {
        AtomicReference<String> seasToken = new AtomicReference<>("");
        Cookie[] cookies = request.getCookies();
        LOGGER.debug("Reading cookies.");
        if (cookies != null && cookies.length > 0) {
            Arrays.stream(cookies)
                    .forEach(c -> {
                        LOGGER.debug("Cookie Name: {}", c.getName());
                        if (c.getName().trim().equals(ssoSeasPropertiesConfig.getSsp().getTokenCookieName())) {
                            seasToken.set(c.getValue());
                        }
                    });
        }
        String user = hasLength(request.getHeader(ssoSeasPropertiesConfig.getSsp().getUserHeaderName())) ? request.getHeader(ssoSeasPropertiesConfig.getSsp().getUserHeaderName()) : "";
        return "redirect:/#/login?user=" + user.trim() + "&SSOTOKEN=" + seasToken.get().trim();
    }
}
