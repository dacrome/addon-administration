package org.osiam.addons.administration

import org.osiam.addons.administration.pages.Login
import org.osiam.addons.administration.pages.Start

class LoginIT extends IntegrationTest {

    def 'when not logged in redirect to osiam login'() {
        when:
        via Start
        at Login

        then:
        title == 'Please login'
    }
}
