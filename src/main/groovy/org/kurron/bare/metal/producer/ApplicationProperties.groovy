package org.kurron.bare.metal.producer

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Holds application properties.
 */
@ConfigurationProperties( value = 'producer', ignoreUnknownFields = false )
class ApplicationProperties {

}
