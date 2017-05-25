package org.kurron.bare.metal.producer

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * MongoDB document that holds different types of data that can be used for benchmarking.
 */
@Document
class Model {

    @Id
    UUID primaryKey
    Date timestamp
    byte[] randomBytes
}
