package org.kurron.bare.metal.producer

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate

import java.util.concurrent.ThreadLocalRandom

/**
 * Handles command-line arguments.
 */
@Slf4j
class CustomApplicationRunner implements ApplicationRunner {

    /**
     * Handles AMQP communications.
     */
    @Autowired
    private MongoTemplate theTemplate

    @Autowired
    private ConfigurableApplicationContext theContext

    @Autowired
    private ApplicationProperties theConfiguration

    private static UUID generateModelID() {
        UUID.randomUUID()
    }

    private static Date generateTimeStamp() {
        Calendar.getInstance( TimeZone.getTimeZone('UTC') ).time
    }

    private static void randomize(byte[] buffer) {
        ThreadLocalRandom.current().nextBytes(buffer)
    }

    private static Model createModel( byte[] payload ) {
        new Model( primaryKey: generateModelID(), timestamp: generateTimeStamp(),  randomBytes: payload )
    }

    @Override
    void run(ApplicationArguments arguments) {

        def messageCount = Optional.ofNullable(arguments.getOptionValues('number-of-messages')).orElse(['100'])
        def messageSize = Optional.ofNullable(arguments.getOptionValues('payload-size')).orElse(['1024'])

        def numberOfMessages = messageCount.first().toInteger()
        def payloadSize = messageSize.first().toInteger()

        log.info "Inserting ${numberOfMessages} messages with a binary payload size of ${payloadSize} to the database"


        def messages = (1..numberOfMessages).collect {
            def buffer = new byte[payloadSize]
            randomize( buffer )
            createModel( buffer )
        }

        log.info "Created ${messages.size()} messages. Sending them to stream."

        long start = System.currentTimeMillis()
        long completed = messages.parallelStream()
                                 .map({ theTemplate.insert( it ) } )
                                 .count()
        long stop = System.currentTimeMillis()

        long duration = stop - start
        log.info('Inserted {} messages in {} milliseconds', completed, duration )

        log.info 'Insertions complete'
        theContext.close()
    }
}
