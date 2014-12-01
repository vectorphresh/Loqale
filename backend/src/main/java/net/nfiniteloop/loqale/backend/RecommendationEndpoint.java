package net.nfiniteloop.loqale.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * Created by vaek on 11/17/14.
 */
@Api(name = "recommendations", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.loqale.nfiniteloop.net", ownerName = "backend.loqale.nfiniteloop.net", packagePath=""))
public class RecommendationEndpoint {
}
